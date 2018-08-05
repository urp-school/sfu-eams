package com.ekingstar.security.concurrent.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ekingstar.security.Authentication;
import com.ekingstar.security.concurrent.SessionInfo;
import com.ekingstar.security.concurrent.SessionRegistry;

public class SessionRegistryImpl implements SessionRegistry {

	protected static final Logger logger = LoggerFactory.getLogger(SessionRegistryImpl.class);

	// <principal:Object,SessionIdSet>
	protected Map principals = Collections.synchronizedMap(new HashMap());
	// <sessionId:Object,SessionInfo>
	protected Map sessionIds = Collections.synchronizedMap(new HashMap());

	public List getSessionInfos() {
		return new ArrayList(sessionIds.values());
	}

	public boolean isRegisted(Object principal) {
		Set sessionsUsedByPrincipal = (Set) principals.get(principal);
		return (null != sessionsUsedByPrincipal && !sessionsUsedByPrincipal.isEmpty());
	}

	public List getSessionInfos(Object principal, boolean includeExpiredSessions) {
		Set sessionsUsedByPrincipal = (Set) principals.get(principal);
		if (sessionsUsedByPrincipal == null) {
			return null;
		}
		List list = new ArrayList();
		synchronized (sessionsUsedByPrincipal) {
			for (Iterator iter = sessionsUsedByPrincipal.iterator(); iter.hasNext();) {
				String sessionId = (String) iter.next();
				SessionInfo sessionInfo = getSessionInfo(sessionId);
				if (sessionInfo == null) {
					continue;
				}
				if (includeExpiredSessions || !sessionInfo.isExpired()) {
					list.add(sessionInfo);
				}
			}
		}

		return list;
	}

	public SessionInfo getSessionInfo(String sessionId) {
		return (SessionInfo) sessionIds.get(sessionId);
	}

	public void refreshLastRequest(String sessionId) {
		SessionInfo info = getSessionInfo(sessionId);
		if (info != null) {
			info.refreshLastRequest();
		}
	}

	public synchronized void register(String sessionId, Authentication authentication) {
		Object principal = authentication.getPrincipal();
		logger.debug("Registering session {} for {}", sessionId, principal);
		SessionInfo existed = getSessionInfo(sessionId);
		if (null != existed) {
			existed.addRemark(" expired with replacement.");
			remove(sessionId);
		}
		sessionIds.put(sessionId, new SessionInfo(principal, authentication.getDetails(),
				sessionId, new Date()));

		Set sessionsUsedByPrincipal = (Set) principals.get(principal);

		if (sessionsUsedByPrincipal == null) {
			sessionsUsedByPrincipal = Collections.synchronizedSet(new HashSet(4));
			principals.put(principal, sessionsUsedByPrincipal);
		}

		sessionsUsedByPrincipal.add(sessionId);
	}

	public void remove(String sessionId) {
		SessionInfo info = getSessionInfo(sessionId);
		if (info == null) {
			return;
		}
		logger.debug("Removing session {} from set of registered sessions", sessionId);
		sessionIds.remove(sessionId);
		Set sessionsUsedByPrincipal = (Set) principals.get(info.getPrincipal());

		if (sessionsUsedByPrincipal == null) {
			return;
		}

		synchronized (sessionsUsedByPrincipal) {
			sessionsUsedByPrincipal.remove(sessionId);
			// No need to keep object in principals Map anymore
			if (sessionsUsedByPrincipal.size() == 0) {
				logger.debug("Removing principal {} from registry", info.getPrincipal());
				principals.remove(info.getPrincipal());
			}
		}
	}

    public int count() {
       return sessionIds.size();
    }
    
}
