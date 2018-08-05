package com.ekingstar.security.concurrent;

import java.util.List;

import com.ekingstar.security.Authentication;

public interface SessionRegistry {
	
	public void register(String sessionId, Authentication authentication);

	public void remove(String sessionId);

	/**
	 * 查询在线记录
	 * 
	 * @return
	 */
	public List getSessionInfos();

	public List getSessionInfos(Object principal, boolean includeExpiredSessions);

	public SessionInfo getSessionInfo(String sessionId);

	public boolean isRegisted(Object principal);

	public void refreshLastRequest(String sessionId);

    public int count();
}
