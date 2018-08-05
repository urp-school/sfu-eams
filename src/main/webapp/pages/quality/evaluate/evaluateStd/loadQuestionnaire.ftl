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
   var bar = new ToolBar('backBar','<#if "update"==evaluateState><@msg.message key="evaluate.updateTitle"/><#else><@msg.message key="evaluate.doTitle"/></#if>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack('<@bean.message key="action.back"/>');
</script>
	<#if questionnaire.title?exists>
	<pre>
	${questionnaire.title?if_exists}
	</pre>
	</#if>
	
	<#if (questionnaire.optionGroup.options)?exists>
	    <#assign options=(questionnaire.optionGroup.options)?sort_by("proportion")?reverse>
	</#if>
  
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
					<#if options?exists>
						<#list options as option>
					     	<input type="radio" name="select${question.id}" value="${option.id}" <#if "update"==evaluateState&&(questionResultMap[question.id?string])?default("0")?string==option.id?string>checked</#if>>${option.name}&nbsp;
				    	</#list>
				    </#if>
			</td>
		</tr>
			</#list>
		<tr>
			<td align="center" class="grayStyle" id="f_teacher">
				<@bean.message key="field.select.evaluateRemark"/><font color="red">*<font>
			</td>
			<td colspan="2" align="left" class="brightStyle">
					<@msg.message key="attr.courseName"/>：<font color="blue"><@i18nName (teachTask.course)?if_exists/></font>
					<br>
					<@msg.message key="course.teacher"/>：<#list teacherList as teacher><@i18nName teacher/><#if (teacherList?size>1)>,</#if></#list>
			</td>
		</tr>
        <tr>
			<td align="center" class="grayStyle" id="f_textOpinion">
				评教意见部分<br><#if teacherList?size==1>(针对教师)<#else>(针对课程)</#if>(200字以内)
			</td>
			<td colspan="2" align="left" class="brightStyle">
				<textarea name="textOpinion" cols="80" rows="4"></textarea>
			</td>
        </tr> 
		<tr>
			<td colspan="4" align="center" class="darkColumn">
				<input type="hidden" name="taskId" value="${teachTask.id}">
				<input type="hidden" name="params" value="&calendar.id=${RequestParameters['calendar.id']}"/>
				<input type="hidden" name="result.id" value="${(evaluateResult.id)?if_exists}">
				<input type="hidden" name="teacherIds" value="<#list teacherList as teacher><#if teacher_has_next>${teacher.id},<#else>${teacher.id}</#if></#list>">
				<button id="updateButton"  name="evaluateQuestionnaire" onClick="doAction(document.questionnaireForm)"><@bean.message key="system.button.submit"/></button>
			</td>
		</tr>
		</form>
	</table>
</body>
<script language="javascript">
	function doAction(form){
		var errors ="";
	     var errorstemp="";
	     <#list questionList as question>
	     	var value1=getRadioValue(document.getElementsByName("select${question.id}"));
	     	if(value1==""){
	     		errorstemp+="${question_index+1},";
	     	}
	     </#list>
	     if(errorstemp!=""){
	     	errorstemp="你第"+errorstemp.substring(0,errorstemp.lastIndexOf(","))+"题没有选择答案，请全部选择以后再点击提交";
	     	errors+="\n"+errorstemp;
	     }
	     if(errors!=""){
	     alert(errors);
	     return;
	     }
	 	if(confirm("<@bean.message key="field.select.selectSubmit"/>")==true){
	 		var buttonUpdate = document.getElementById("updateButton");
	    	form.action="evaluateStd.do?method=save";
	    	if(form['textOpinion'].value==""){
	    	   if(!confirm("你的意见部分还没有填,不想提些说写什么吗？\n点击[取消]填写意见,[确定]忽略该步骤,提交问卷")){
	    	      return;
	    	   }
	    	}
	    	if(form['textOpinion'].value.length>200){
	    	   alert("你的意见部分字数过多,请缩减后提交.");
	    	   return;
	    	}
	    	
	    	form.submit();
	 		buttonUpdate.innerHTML="数据提交中,请耐心等待....";
	 		buttonUpdate.onclick=alertWait;
	 	}
	}
	function alertWait(){
	  alert('数据提交中,请耐心等待....');
	}
</script>
<#include "/templates/foot.ftl"/>