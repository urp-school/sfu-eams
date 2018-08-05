//$Id: InstructWorkload.java,v 1.3 2006/08/25 06:48:40 cwx Exp $
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
 * @author hj
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2005-11-21         Created
 *  
 ********************************************************************************/

package com.shufe.model.workload.instruct;

import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.workload.Workload;

/**
 * 指导学生工作量
 * 
 * @author chaostone
 * 
 */
public class InstructWorkload extends Workload {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7936311916968747944L;
	private InstructModulus modulus = new InstructModulus(); // 院系系数

	public InstructWorkload() {
		super();
	}

	public InstructWorkload(Teacher teacher) {
		super(teacher);
		this.isTeachWorkload = Boolean.FALSE;
	}

	public InstructModulus getModulus() {
		return modulus;
	}

	public void setModulus(InstructModulus modulus) {
		this.modulus = modulus;
	}
	
}
