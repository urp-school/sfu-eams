<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<form name="pageGoForm" action="#" method="post" onsubmit="return false;">
  <#assign class=" " >
  <#assign message><#if thesisAnnotate?if_exists.isDoubleBlind?if_exists><#if thesisAnnotate.isDoubleBlind>双盲已选中!  </#if><#else>双盲未选中!  </#if><@html.errors/></#assign>
  <table id="backBar"></table>
	<script>
   		var bar = new ToolBar('backBar','${student.name?if_exists}的论文评阅书',null,true,true);
   		bar.setMessage('${message}');
   		bar.addBack('<@bean.message key="action.back"/>');
	</script>
  <#include "info/selfEvaluateInfo.ftl"/><!--学生自评表-->
  <br>
 <#list thesisAnnotate.annotateBooks?if_exists?sort_by("serial") as book>
  		<#assign idSeq=book_index+1>
  		<#assign annotateBook=book>
 	  <h3 align="center">
        <B><@bean.message key="filed.annotateThesisBook" />${idSeq}</B>
      </h3>
 	 <#include "info/selfEvaluateforTeacherInfo.ftl"/><!--老师对学生的自评表的评价-->
	 <#if studentFlag?exists&&studentFlag=="doctor">
	 <#include "info/doctorEvaluateAdviceInfo.ftl"/><!--博士学位专家意见-->
	 </#if>
	 <#if flag?exists&&flag=="admin"> 
	  <#include "info/thesisAnnotateBookInfo.ftl"/><!--论文评阅书-->
	 </#if>
     <br>  
 </#list>
</form> 
  <#if (thesisAnnotate.annotateBooks)?exists>
  <table class="listTable" width="100%">
  		<tr class="darkColumn">
  			<td align="center" colspan="5">论文评阅书成绩</td>
  		</tr>
		<tr class="grayStyle">
			<td>论文编号</td>
			<td>成绩</td>
			<td>平均成绩</td>
			<td>学位水平</td>
			<td>是否同意答辩</td>
		</tr>
   	<#list thesisAnnotate.annotateBooks?if_exists?sort_by("serial") as book>
   		<#if book_index%2==0><#assign class="brightStyle"><#else><#assign class="grayStyle"></#if>
		<tr class="${class}">
			<td>${book.serial?if_exists}</td>
			<td><#if book.evaluateIdea?if_exists.mark?exists>${book.evaluateIdea.mark?string("##0.0")}</#if></td>
			<td><#if thesisAnnotate.avgMark?exists>${thesisAnnotate.avgMark?string("##0.0")}</#if></td>
			<td><#if book.evaluateIdea?if_exists.learningLevel?exists><#if book.evaluateIdea.learningLevel=="A">优秀<#elseif book.evaluateIdea.learningLevel=="B">良好<#elseif book.evaluateIdea.learningLevel=="C">一般<#elseif book.evaluateIdea.learningLevel=="D">不及格</#if></#if></td>
			<td><#if book.isReply?exists><#if book.isReply=="A">同意答辩<#elseif book.isReply=="M">修改后答辩<#elseif book.isReply=="D">不同意答辩</#if></#if></td>
		</tr>
 	</#list>
 	<tr class="darkColumn">
 		<td  colspan="5">
 			<#if !stdDegree?exists>
	 			<#if studentFlag?exists>
 					<#assign stdDegree=studentFlag/>
 				<#else>
 					<#assign stdDegree=""/>
	 			</#if>
 			</#if>
 			注意：您必须完成表格（<#if "master"==stdDegree>S1-S7<#elseif "doctor"==stdDegree>B1-B9<#else>T1-T7</#if>）的填写、审核和提交，才能看到评审结果，双盲评审期为1个月，请耐心等待
 		</td>
 	</tr>
 </table>
 </#if>
</body>
<#include "/templates/foot.ftl"/>