//$Id: QuestionResult.java,v 1.1 2007-6-2 19:01:07 Administrator Exp $
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
 * @author Administrator
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chenweixiong              2007-6-2         Created
 *  
 ********************************************************************************/

package com.shufe.model.quality.evaluate;

import com.ekingstar.commons.model.pojo.LongIdObject;

/**
 * 问题评教结果
 * 
 * @author chaostone
 * 
 */
public class QuestionResult extends LongIdObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8274084420600077801L;

	/** 问题类别 */
	private QuestionType questionType = new QuestionType();

	/** 问题 */
	private Question question = new Question();

	/** 问题选项 */
	private Option option = new Option();

	/** 得分 */
	private Float score;

	/** 评教结果 */
	private EvaluateResult result = new EvaluateResult();

	public QuestionResult() {

	}

	public QuestionResult(Question question, Option option) {
		this.questionType = question.getType();
		this.question = question;
		this.option = option;
		this.score = new Float(question.getScore().floatValue()
				* option.getProportion().floatValue());
	}

	/**
	 * @return Returns the option.
	 */
	public Option getOption() {
		return option;
	}

	/**
	 * @param option
	 *            The option to set.
	 */
	public void setOption(Option option) {
		this.option = option;
	}

	/**
	 * @return Returns the question.
	 */
	public Question getQuestion() {
		return question;
	}

	/**
	 * @param question
	 *            The question to set.
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}

	/**
	 * @return Returns the questionType.
	 */
	public QuestionType getQuestionType() {
		return questionType;
	}

	/**
	 * @param questionType
	 *            The questionType to set.
	 */
	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	/**
	 * @return Returns the score.
	 */
	public Float getScore() {
		return score;
	}

	/**
	 * @param score
	 *            The score to set.
	 */
	public void setScore(Float score) {
		this.score = score;
	}

	/**
	 * @return Returns the result.
	 */
	public EvaluateResult getResult() {
		return result;
	}

	/**
	 * @param result
	 *            The result to set.
	 */
	public void setResult(EvaluateResult result) {
		this.result = result;
	}
}
