//$Id: CodeFixture.java,v 1.1 2007-2-4 下午08:24:02 chaostone Exp $
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

package com.shufe.web.action.system.codeGen;

import java.util.HashMap;
import java.util.Map;

public class CodeFixture {

	private Map params = new HashMap();

	private String script;

	public Map getParams() {
		return params;
	}

	public String getScript() {
		return script;
	}

	public void setParams(Map params) {
		this.params = params;
	}

	public void setScript(String script) {
		this.script = script;
	}

}
