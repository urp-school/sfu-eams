package com.ekingstar.eams;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StaticResourceListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("static_base",
				System.getProperty("static_base"));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
