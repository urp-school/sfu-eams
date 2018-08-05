package com.shufe.web;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class I18nFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String requestLocale = req.getParameter("request_locale");
		if (null == requestLocale || requestLocale.length() < 1) {
			chain.doFilter(request, response);
		} else {
			String key = "org.apache.struts.action.LOCALE";
			HttpSession session = req.getSession(true);
			Object sessionLocale = session.getAttribute(key);
			session.setAttribute(key, toLocale(requestLocale));
			try {
				chain.doFilter(request, response);
			} finally {
				try {
					if (null == sessionLocale) {
						session.removeAttribute(key);
					} else {
						session.setAttribute(key, sessionLocale);
					}
				} catch (Exception e) {
				}
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	private static Locale toLocale(String localeStr) {
		if ((localeStr == null) || (localeStr.trim().length() == 0)
				|| ("_".equals(localeStr)))
			return Locale.getDefault();

		int index = localeStr.indexOf('_');
		if (index < 0)
			return new Locale(localeStr);

		String language = localeStr.substring(0, index);
		if (index == localeStr.length())
			return new Locale(language);

		localeStr = localeStr.substring(index + 1);
		index = localeStr.indexOf('_');
		if (index < 0)
			return new Locale(language, localeStr);

		String country = localeStr.substring(0, index);
		if (index == localeStr.length())
			return new Locale(language, country);

		localeStr = localeStr.substring(index + 1);
		return new Locale(language, country, localeStr);
	}

}
