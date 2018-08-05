//$Id: ScriptCodeGenerator.java,v 1.1 2007-2-4 上午08:43:07 chaostone Exp $
/*
 *
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved.
 * 
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended 
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source 
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-2-4         Created
 *  
 ********************************************************************************/

package com.shufe.service.system.codeGen.impl;

import java.util.Iterator;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import bsh.Interpreter;

import com.ekingstar.commons.model.Entity;
import com.shufe.model.system.codeGen.CodeScript;
import com.shufe.service.system.codeGen.CodeGenerator;
import com.shufe.web.action.system.codeGen.CodeFixture;

public class ScriptCodeGenerator implements CodeGenerator {

	protected Interpreter interpreter = new Interpreter();

	protected String script = null;

	public void setUp() throws Exception {
	}

	public String gen(Entity entity, CodeScript codeScript) {
		try {
			interpreter.set("entity", entity);
			setUp();
			return (String) interpreter.eval(getScript());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String test(CodeFixture fixture, CodeScript codeScript) {
		try {
			Class codeClass = Class.forName(codeScript.getCodeClassName());
			Entity entity = (Entity) codeClass.newInstance();
			PropertyUtils.getProperty(entity, codeScript.getAttr());
			if (null != fixture) {
				for (Iterator iter = fixture.getParams().keySet().iterator(); iter
						.hasNext();) {
					String param = (String) iter.next();
					interpreter.set(param, fixture.getParams().get(param));
				}
				if (StringUtils.isNotEmpty(fixture.getScript())) {
					interpreter.eval(fixture.getScript());
				}
			}
			setScript(codeScript.getScript());
			return gen(entity, codeScript);
		} catch (Exception e) {
			return ExceptionUtils.getFullStackTrace(e);
		}
	}

	public boolean isValidCode(String code) {
		return (StringUtils.isNotEmpty(code))
				&& (code.length() <= CodeGenerator.MAX_LENGTH)
				&& !CodeGenerator.MARK.equals(code);
	}

}
