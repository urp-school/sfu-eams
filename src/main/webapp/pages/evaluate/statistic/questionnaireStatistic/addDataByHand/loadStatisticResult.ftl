<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <#include "/pages/evaluate/ealuateJs.ftl"/>
 	<script languae="javascript">
		function loadCourses(){
			var collegeId = document.evaluateForm['questionnaireStatistic.openPart.id'].value;
			var teachCalendarId = document.evaluateForm.teachCalendarId.value;
			var url="questionnaireStatisticAction.do?method=getCourses&collegeId="+collegeId+"&teachCalendarId="+teachCalendarId;
	        window.open(url, '', 'scrollbars=yes,left=0,top=0,width=500,height=550,status=yes');
			}
		function setTeachTaskIdAndDescriptions(ids, descriptions){      
       		document.evaluateForm['questionnaireStatistic.teachTask.id'].value = ids;
	   		document.evaluateForm['courseName'].value = descriptions.replace(/,/gi, "\n");
   	 		}
    	
    	function doAction(form){
    		var a_fields = {
         	'courseName':{'l':'<@bean.message key="field.select.course"/>', 'r':true, 't':'f_teachTaskId'},
         	'questionnaireStatistic.teacher.id':{'l':'<@bean.message key="field.questionnaireStatistic.selectTecher"/>', 'r':true, 't':'f_teacher'},
         	'totleEvaluate':{'l':'<@bean.message key="field.questionnaireStatistic.totleEvaluate"/>', 'r':true, 't':'f_questionMark','f':'real','mx':'3'}
     		};
     		var v = new validator(form, a_fields, null);
     		if (v.exec()) {
     		 var mark = new Number(document.evaluateForm.totleEvaluate.value);
     		 if(mark>100){
     		 	alert("百分制不能大于100分");
     		 	return;
     		 }
     		form.action ="questionnaireStatisticAction.do?method=doSaveStatisticResults";
        	form.submit();
     		}
    	}
    	function setTeacherIdAndDescriptions(ids,descriptions){
       		document.evaluateForm['questionnaireStatistic.teacher.id'].value = ids;
	   		document.evaluateForm['teacherName'].value = descriptions.replace(/,/gi, "\n");
    	}
    	function addOption(selectId,data){
    		var select = document.getElementById(selectId);
    		DWRUtil.removeAllOptions(selectId);
    		var datas = data.split(",");
    		select.add(new Option(datas[1],datas[0]));
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
			<td align="center" class="darkColumn" colspan="2"><@bean.message key="field.questionnaireStatistic.addEvaluateResult"/><font color="red"><@bean.message key="field.questionnaireStatistic.addEvaluateResultLastResult"/></font>
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
			<td align="center" class="grayStyle" width="30%">开课院系:
			</td>
			<td align="left" class="brightStyle">
				<select id="collegeId" name="questionnaireStatistic.openPart.id" style="width:200px;">
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
				<input type="hidden" name="questionnaireStatistic.teachTask.id">
				<input type="text" name="courseName" style="width:300px;" onfocus="this.blur();">
				<input type="button" name="buttonCollege" value="<@bean.message key="field.select.course"/>" class="buttonStyle" onclick="loadCourses()">
			</td>
		</tr>
		<tr>
			<td align="center" class="grayStyle" width="30%" id="f_teacher">
				<@bean.message key="field.questionnaireStatistic.selectTecher"/>:<font color="red">*</font>
			</td>
			<td align="left" class="brightStyle">
				<select id="teacherId" name="questionnaireStatistic.teacher.id" style="width:200px;">
					<option value="">先选择课程 再选择教师</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="center" class="grayStyle" width="30%">制作问卷部门:<font color="red">*</font>
			</td>
			<td align="left" class="brightStyle">
				<select id="departmentId" name="departmentId" style="width:300px;">
					<option value="">请先选择课程</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="center" class="grayStyle" width="30%" id="f_questionMark">
				<@bean.message key="field.questionnaireStatistic.totleEvaluate"/>:<font color="red">*</font>
			</td>
			<td align="left" class="brightStyle">
				<input type="text" name="totleEvaluate" style="width:200px;">
				<font color="red"><@bean.message key="field.questionnaireStatistic.addEvaluateResultLastResultRemark"/></font>
			</td>
		</tr>
		<tr>
			<td align="center" class="darkColumn" colspan="2">
				<input type="button" value="<@bean.message key="system.button.add"/>" class="buttonStyle" onclick="doAction(this.form)">
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