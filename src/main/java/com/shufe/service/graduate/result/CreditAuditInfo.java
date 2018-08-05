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
 * @author yang
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * yang                 2005-11-9           Created
 *  
 ********************************************************************************/
package com.shufe.service.graduate.result;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ekingstar.commons.collection.predicates.NotZeroNumberPredicate;

/**
 * 学分审核结果
 * 
 * @see TeachPlanAuditResult
 * @see CourseGroupAuditResult
 * @author Administrator
 */
public class CreditAuditInfo {

  public CreditAuditInfo() {
    super();
  }

  public CreditAuditInfo(Float required, Float completed) {
    this.required = required;
    this.completed = completed;
  }

  /**
   * 应修学分
   */
  private Float required;

  /**
   * 实修学分
   */
  private Float completed;

  /**
   * 转换学分（例如：非任选课多出的学分可以转换冲抵任选课的学分）
   */
  private Float converted;

  /**
   * @return 返回 converted.
   */
  public Float getConverted() {
    return converted;
  }

  /**
   * @param converted
   *          要设置的 converted.
   */
  public void setConverted(Float converted) {
    this.converted = converted;
  }

  public Float getCompleted() {
    return completed;
  }

  public void setCompleted(Float completed) {
    this.completed = completed;
  }

  public Boolean getIsPass() {
    return java.lang.Boolean
        .valueOf((required != null ? required.floatValue() : 0.0F) <= (completed != null ? completed
            .floatValue() : 0.0F) + (converted != null ? converted.floatValue() : 0.0F));
  }

  public Float getRequired() {
    return required;
  }

  public void setRequired(Float required) {
    this.required = required;
  }

  /**
   * 添加完成的学分
   * 
   * @param credit
   */
  public void addCompletedCredit(Float credit) {
    if (null != credit) {
      if (null == completed) {
        completed = credit;
      } else {
        completed = new Float(completed.floatValue() + (credit == null ? 0f : credit.floatValue()));
      }
    }
  }

  /**
   * 添加待完成的学分
   * 
   * @param credit
   */
  public void addRequiredCredit(Float credit) {
    if (null != credit) {
      if (null == required) {
        required = credit;
      } else {
        required = new Float(required.floatValue() + credit.floatValue());
      }
    }
  }

  /**
   * 添加有其他转化而来的学分
   * 
   * @param credit
   */
  public void addConvertedCredit(Float credit) {
    if (null == credit) return;
    if (!NotZeroNumberPredicate.getInstance().evaluate(credit)) return;
    if (null == converted) {
      converted = credit;
    } else {
      converted = new Float(converted.floatValue() + (credit == null ? 0f : credit.floatValue()));
    }
  }

  public String toString() {
    return new ToStringBuilder(this).append("required", this.required).append("isPass", this.getIsPass())
        .append("completed", this.completed).append("converted", this.converted).toString();
  }

  /**
   * 是否所修学分超出所需的学分
   * 
   * @return
   */
  public Boolean hasMoreCreditToConvert() {
    return Boolean.valueOf(((null == required) ? 0 : required.floatValue()) < (((null == completed) ? 0
        : completed.floatValue())));
  }

  /**
   * 获取所修超出所需的学分
   * 
   * @return
   */
  public Float getMoreCreditToConvert() {
    if (Boolean.TRUE.equals(this.hasMoreCreditToConvert())) {
      return new Float((this.completed == null ? 0 : this.completed.floatValue())
          - (this.required == null ? 0 : this.required.floatValue()));
    } else {
      return null;
    }
  }

  /**
   * 获取所修学分和所需学分的差值
   * 
   * @param returnNegative
   *          是否返回负数
   * @return
   */
  public Float getCreditToComplete(Boolean returnNegative) {
    float needToComplete = (this.required == null ? 0 : this.required.floatValue())
        - (this.converted == null ? 0 : this.converted.floatValue())
        - (this.completed == null ? 0 : this.completed.floatValue());
    if (needToComplete < 0) {
      if (Boolean.TRUE.equals(returnNegative)) {
        return new Float(needToComplete);
      } else {
        return new Float(0);
      }
    } else {
      return new Float(needToComplete);
    }
  }

}
