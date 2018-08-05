<#include "/templates/head.ftl"/>
<#include "/pages/evaluate/ealuateJs.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 	<script languae="javascript">
		function loadCourses(){
		    var teachCalendarId = document.evaluateForm.teachCalendarId.value;
			var collegeId = document.evaluateForm['evaluateResults.department.id'].value;
			var url="questionnaireStatisticAction.do?method=getCourses&collegeId=" +collegeId+"&teachCalendarId="+teachCalendarId;
	        window.open(url, '', 'scrollbars=yes,left=0,top=0,width=500,height=550,status=yes');
			}
		function setTeachTaskIdAndDescriptions(ids, descriptions){      
       		document.evaluateForm['evaluateResults.teachTask.id'].value = ids;
	   		document.evaluateForm['courseName'].value = descriptions.replace(/,/gi, "\n");
   	 		}
    	function setQuestionIdAndDescriptions(ids, descriptions,mark){      
       		document.evaluateForm['evaluateResults.question.questionId'].value = ids;
	   		document.evaluateForm['questionName'].value = descriptions.replace(/,/gi, "\n");
	   		document.evaluateForm['questionMark'].value = mark;
    		}
    	function loadQuestion(){
    	    var taskId = document.evaluateForm['evaluateResults.teachTask.id'].value;
    	    if(""==taskId){
    	    	alert("请先选择教学任务");
    	    	return;
    	    }
    		var url="questionnaireStatisticAction.do?method=getQuestions&taskId="+taskId;
	        window.open(url, '', 'scrollbars=yes,left=0,top=0,width=500,height=550,status=yes');
    	}
    	function doAction(form){
    		var a_fields = {
         	'courseName':{'l':'<@bean.message key="field.select.course"/>', 'r':true, 't':'f_teachTaskId'},
         	'evaluateResults.teacher.id':{'l':'<@bean.message key="field.questionnaireStatistic.selectTecher"/>', 'r':true, 't':'f_teacher'},
         	'questionName':{'l':'<@bean.message key="field.questionnaire.selectQuestion"/>', 'r':true, 't':'f_question'},
         	'evaluateResults.score':{'l':'<@bean.message key="field.questionnaireStatistic.questionMark"/>', 'r':true, 't':'f_score','f':'real','mx':4}
     		};
     		var v = new validator(form, a_fields, null);
     		var questionmark=new Number(document.evaluateForm['questionMark'].value);
     		var score = new Number(document.evaluateForm['evaluateResults.score'].value);
     		if (v.exec()) {
     			if(score>questionmark){
     				alert("这个问题的分值为"+questionmark+",得分不能大于这个值")
     				return;
     			}
     			form.action ="questionnaireStatisticAction.do?method=doSave";
        		form.submit();
     		}
    	}
    	function setTeacherIdAndDescriptions(ids,descriptions){
       		document.evaluateForm['evaluateResults.teacher.id'].value = ids;
	   		document.evaluateForm['teacherName'].value = descriptions.replace(/,/gi, "\n");
    	}
    function doButtonEstate(inputId,isOpen){
    	var input = document.getElementById(inputId);
    	input.disabled=isOpen;
    }	
	</script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="backBar" width="100%"></table>
	<script>
   		var bar = new ToolBar('backBar','<@bean.message key="field.questionnaireStatistic.addEvaluateResult"/>',null,true,true);
   		bar.setMessage('<@getMessage/>');
	</script>
  <table cellpadding="0" cellspacing="0" align="center" width="100%" border="0">
	<tr>
	<td>
	<form name="evaluateForm" method="post" action="" onsubmit="return false;">
	<input type="hidden" name="doSave" value="save">
	<table width="100%" align="center" class="listTable">
		<tr>
			<td align="center" class="darkColumn" colspan="2"><@bean.message key="field.questionnaireStatistic.addEvaluateResult"/><font color="red"><@bean.message key="field.questionnaireStatistic.addEvaluateResultOneQuestion"/></font>
			</td>
		</tr>
		<tr>
			<td align="center" class="grayStyle" width="30%" id="f_remark">录入的学年度学期:
			</td>
			<td align="left" class="brightStyle">
				<select id="teachCalendarId" name="teachCalendarId" style="width="300px;" disabled onchange="doButtonEstate('teachCalendarId',true)">
					<#list teachCalendars?sort_by("start")?if_exists as teachCalendar>
						<#if nearTeachCalendar.id?string==teachCalendar.id?string>
							<option value="${teachCalendar.id}" selected>${teachCalendar.studentType.name}/${teachCalendar.year}/${teachCalendar.term}</option>
						<#else>
							<option value="${teachCalendar.id}">${teachCalendar.studentType.name}/${teachCalendar.year}/${teachCalendar.term}</option>
						</#if>
					</#list>
				</select>
				<input type="button" name="button1" value="切换学期" onclick="doButtonEstate('teachCalendarId',false)" class="buttonStyle">
			</td>
		</tr>
		<tr>
			<td align="center" class="grayStyle" width="30%">开课院系
			</td>
			<td align="left" class="brightStyle">
				<select name="evaluateResults.department.id" style="width:200px;">
					<#list collegeList?if_exists as college>
						<option value="${college.id}">${college.name}</option>
					</#list>
				</select>
			</td>
		</tr>
		<tr>
			<td align="center" class="grayStyle" width="30%" id="f_teachTaskId">
				<@bean.message key="field.select.course"/>:<font color="red">*</font>
			</td>
			<td align="left" class="brightStyle">
				<input type="hidden" name="evaluateResults.teachTask.id">
				<input type="text" name="courseName" maxlength="20" style="width:200px;" onfocus="this.blur();">
				<input type="button" name="buttonCollege" value="<@bean.message key="field.select.course"/>" class="buttonStyle" onclick="loadCourses()">
			</td>
		</tr>
		<tr>
			<td align="center" class="grayStyle" width="30%" id="f_teacher">
				<@bean.message key="field.questionnaireStatistic.selectTecher"/>:<font color="red">*</font>
			</td>
			<td align="left" class="brightStyle">
				<select id="teacherId" name="evaluateResults.teacher.id" style="width:200px;">
					<option value="">先选择课程 再选择教师</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="center" class="grayStyle" width="30%" id="f_question">
				<@bean.message key="field.questionnaire.selectQuestion"/>:<font color="red">*</font>
			</td>
			<td align="left" class="brightStyle">
				<input type="hidden" name="evaluateResults.question.questionId">
				<input type="hidden" name="questionMark">
				<input type="text" maxlength="20" style="width:300px;" name="questionName" readonly="true">
				<input type="button" name="buttonQuestion" value="<@bean.message key="field.questionnaire.selectQuestion"/>" class="buttonStyle" onclick="loadQuestion()">
			</td>
		</tr>
		<tr>
			<td align="center" class="grayStyle" width="30%" id="f_score">
				<@bean.message key="field.questionnaireStatistic.questionMark"/>:<font color="red">*</font>
			</td>
			<td align="left" class="brightStyle">
				<input type="text" name="evaluateResults.score" style="width:200px;">
			</td>
		</tr>
		<tr>
			<td align="center" class="darkColumn" colspan="2">
				<input type="button" value="<@bean.message key="action.add"/>" class="buttonStyle" onclick="doAction(this.form)">
				<input type="reset"  name="reset1" value="<@bean.message key="system.button.reset"/>" class="buttonStyle" />
			</td>
		</tr>
		</table>
		</form>
		</td>
		</tr>
	</table>
</body>
<#include "/templates/foot.ftl"/>