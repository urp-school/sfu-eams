<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<script language="javascript">
function mergeTableTd(tableId,index){
		var rowsArray = document.getElementById(tableId).rows;
		var value=rowsArray[1].cells[index];
		for(var i=2;i<rowsArray.length-2;i++){
			nextTd=rowsArray[i].cells[index];
			if(nextTd.innerHTML==value.innerHTML){
				rowsArray[i].removeChild(nextTd);
				var rowspanValue= new Number(value.rowSpan);
				rowspanValue++;
				value.rowSpan=rowspanValue;
			}else{
				value=nextTd;
			}
		}
}
</script>
<BODY LEFTMARGIN="0" TOPMARGIN="0" onload="mergeTableTd('tableName',0)">
<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','问卷查看页面',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack('<@bean.message key="action.back"/>');
</script>
<#if questionnaire.title?exists>
<pre>
${questionnaire.title?if_exists}
</pre>
</#if>
<div align="center">问卷描述：${questionnaire.description?if_exists}&nbsp;&nbsp;&nbsp;问卷创建部门:${questionnaire.depart.name}&nbsp;&nbsp;&nbsp;</div>
	<table id="tableName" align="center" class="listTable" width="100%">
		<form name="questionnaireForm" method="post" action="" onsubmit="return false;">
		<tr align="center" class="darkColumn">
			<td><@bean.message key="field.question.questionType"/></td>
			<td><@bean.message key="field.question.questionContext"/></td>
			<td><@bean.message key="field.questionnaire.selectOption"/></td>
		</tr>
		<#list questionList?if_exists as question>
			<#if question_index%2==1 ><#assign class="grayStyle" ></#if>
	   		<#if question_index%2==0 ><#assign class="brightStyle" ></#if>
	   	<tr class="${class}"  align="center">
			<td width="100" align="center">
				${question.type.name}
			</td>
			<td width="400" align="left">
				${question_index+1}: ${question.content}				
			</td>
			<td width="300" align="center">
					<#if (questionnaire.optionGroup.options)?exists>
						<#list (questionnaire.optionGroup.options)?sort_by("proportion")?reverse as option>
					     	<input type="radio" name="select${question.id}" value="${option.id}">${option.name}&nbsp;
				    	</#list>
				    </#if>
			</td>
		</tr>
			</#list>
		<tr class="darkColumn">
			<td height="25px;" colspan="3"></td>
		</tr>
		</form>
	</table>
</body>
<#include "/templates/foot.ftl"/>