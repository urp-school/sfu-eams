<#include "../stdSelfEvaluate.ftl"/><!--学生自评表-->
 <#if flag?exists&&flag=="admin">
  <#include "../thesisAnnotateBook.ftl"/><!--论文评阅书-->
  </#if>
 <#if flag?exists&&flag=="admin"> 
 	<#include "../selfEvaluateforTeacher.ftl"/><!--老师对学生的自评表的评价-->
  </#if>
 <#if flag?exists&&flag=="admin">
 <#include "../doctorEvaluateAdvice.ftl"/><!--博士学位专家意见-->
 </#if>   
  <#if flag?exists&&flag=="admin">
   <#include "../totleEvalueAdvice.ftl"/><!--论文总体评价书-->
  </#if>
