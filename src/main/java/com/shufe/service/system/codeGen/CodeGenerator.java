//$Id: CodeGenerator.java,v 1.1 2007-2-4 上午08:29:17 chaostone Exp $
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

package com.shufe.service.system.codeGen;

import com.ekingstar.commons.model.Entity;
import com.shufe.model.system.codeGen.CodeScript;
import com.shufe.web.action.system.codeGen.CodeFixture;

/**
 * 代码生成器
 * 
 * @author chaostone
 * 
 */
public interface CodeGenerator {

	public static final int MAX_LENGTH = 50;

	public static final String MARK = "******";

	/**
	 * 根据实体类的信息,生成一个代码
	 * 
	 * @param entity
	 * @param codeScript 指定的代码生成脚本，如果为null则根据类全名进行查找
	 * @return
	 */
	public String gen(Entity entity, CodeScript codeScript);

	/**
	 * 测试脚本
	 * 
	 * @param fixture
	 * @param script
	 * @return
	 */
	public String test(CodeFixture fixture, CodeScript codeScript);

	/**
	 * 判断是否是合法编码
	 * 
	 * @param code
	 * @return
	 */
	public boolean isValidCode(String code);
}
