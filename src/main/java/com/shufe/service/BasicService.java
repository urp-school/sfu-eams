package com.shufe.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ekingstar.commons.utils.persistence.UtilDao;
import com.ekingstar.commons.utils.persistence.UtilService;


public abstract class BasicService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	protected UtilDao utilDao;

	protected UtilService utilService;

	public void debug(Object debubObj) {
		if (logger.isDebugEnabled()) {
			if (debubObj != null) {
				logger.debug("the debubObj is {}",debubObj);
			} else {
				logger.debug("the object is null");
			}
		}
	}

	public void info(Object infoObj) {
		if (logger.isInfoEnabled()) {
			if (infoObj != null) {
				logger.info("the infoObj is {}",infoObj);
			} else {
				logger.info("the object is null");
			}

		}
	}

	public void error(Object errorObj) {
		if (logger.isErrorEnabled()) {
			if (errorObj != null) {
				logger.error("the errorObj is {}",errorObj);
			} else {
				logger.error("the object is null");
			}

		}
	}

	/**
	 * @return Returns the utilDao.
	 */
	public UtilDao getUtilDao() {
		return utilDao;
	}

	/**
	 * @param utilDao
	 *            The utilDao to set.
	 */
	public void setUtilDao(UtilDao utilDao) {
		this.utilDao = utilDao;
	}

	/**
	 * @return Returns the utilService.
	 */
	public UtilService getUtilService() {
		return utilService;
	}

	/**
	 * @param utilService
	 *            The utilService to set.
	 */
	public void setUtilService(UtilService utilService) {
		this.utilService = utilService;
	}

}
