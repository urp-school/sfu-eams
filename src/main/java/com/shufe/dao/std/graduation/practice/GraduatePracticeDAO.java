package com.shufe.dao.std.graduation.practice;

import java.util.List;

import com.shufe.model.std.graduation.practice.GraduatePractice;

public interface GraduatePracticeDAO {
	
	/**
	 * 根据条件得到实习的属性列表, iscount 判断是否需要添加属性对应的行求和
	 * @param graduatePractice
	 * @param departIdSeq
	 * @param stdTypeIdSeq
	 * @param properties
	 * @param isCount
	 * @return
	 */
	public List getPropertyOfPractices(GraduatePractice graduatePractice,
			String departIdSeq, String stdTypeIdSeq, String properties,
			Boolean isCount);
}
