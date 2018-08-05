<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<form name="pageGoForm" action="#" method="post" onsubmit="return false;">
  <#assign class="brightStyle" >
  <#assign message><#if thesisAnnotate?if_exists.isDoubleBlind?if_exists><#if thesisAnnotate.isDoubleBlind>双盲已选中!  </#if><#else>双盲未选中!  </#if><#if thesisAnnotate?if_exists.departmentValidate?if_exists><#if thesisAnnotate.departmentValidate><@bean.message key="filed.submitByDepartment" />!  </#if><#else><@bean.message key="filed.noSubmitByDepartment" />!  </#if><#if thesisAnnotate?if_exists.tutorValidate?if_exists><#if thesisAnnotate.tutorValidate><@bean.message key="filed.submitByTutor" />!  </#if><#else><@bean.message key="filed.noSubmitByTutor" />!  </#if><@html.errors/></#assign>
  <table id="backBar"></table>
	<script>
   		var bar = new ToolBar('backBar','${student.name?if_exists}的论文评阅书',null,true,true);
   		bar.setMessage('${message}');
   		bar.addBack('<@bean.message key="action.back"/>');
	</script>
  <#include "stdSelfEvaluate.ftl"/><!--学生自评表-->
  <br>
 <#list thesisAnnotate.annotateBooks?if_exists?sort_by("serial") as book>
  		<#assign idSeq=book_index+1>
  		<#assign annotateBook=book>
 	  <h3 align="center">
        <B><@bean.message key="filed.annotateThesisBook" />${idSeq}</B>
      </h3>
 	 <#include "selfEvaluateforTeacher.ftl"/><!--老师对学生的自评表的评价-->
	 <#if studentFlag?exists&&studentFlag=="doctor">
	 <#include "doctorEvaluateAdvice.ftl"/><!--博士学位专家意见-->
	 </#if>
	 <#if flag?exists&&flag=="admin"> 
	  <#include "thesisAnnotateBook.ftl"/><!--论文评阅书-->
	 </#if>
     <br>  
 </#list>
</form> 
</body>
<#include "/templates/foot.ftl"/>