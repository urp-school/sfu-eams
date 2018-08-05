<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<script type='text/javascript' src='dwr/interface/studentDAO.js'></script>
<script src='dwr/interface/teachTaskService.js'></script>
<script src='dwr/interface/courseTakeDAO.js'></script>
<body>
	<table id="bar"></table>
	<table class="formTable" width="100%">
		<form method="post" action="" name="actionForm">
		<input type="hidden" name="courseGrade.id" value=""/>
		<tr>
			<td class="darkColumn" colspan="6" style="text-align:center; font-weight:bold">基本信息</td>
		</tr>
		<tr>
			<td class="title" id="f_studentType"><@bean.message key="entity.studentType"/>：</td>
			<td>
	          	<select id="stdType" name="calendar.studentType.id" style="width:120px;">
	            	<option value="${RequestParameters['courseGrade.calendar.studentType.id']?if_exists}"><@bean.message key="filed.choose"/></option>
	          	</select>	 
			</td>
			<td class="title" id="f_year"><@bean.message key="attr.year2year"/>：</td>
			<td>
	          	<select id="year" name="calendar.year" style="width:120px;">
	            	<option value="${RequestParameters['courseGrade.calendar.year']?if_exists}"><@bean.message key="filed.choose"/></option>
	          	</select>	 
			</td>
			<td class="title" id="f_term"><@bean.message key="attr.term"/>：</td>
			<td>
	          	<select id="term" name="calendar.term" style="width:120px;">
	            	<option value="${RequestParameters['courseGrade.calendar.term']?if_exists}"><@bean.message key="filed.choose"/></option>
	          	</select>	 
			</td>
		</tr>
		<tr>
			<td class="title" id="f_course"><@msg.message key="attr.taskNo"/>：</td>
			<td><input type="text" name="courseGrade.taskSeqNo" value="" style="width:120px" onblur="getTeachTaskInfo(event)"/></td>
			<input type="hidden" id="c_taskId" name="courseGrade.task.id" value=""/>
			<td class="title"><@msg.message key="attr.courseNo"/>：</td>
			<td id="c_courseNo"></td>
			<td class="title"><@msg.message key="attr.courseName"/>：</td>
			<td id="c_courseName"></td>
		</tr>
		<tr>
			<td class="title" id="f_student"><@msg.message key="std.code"/>：</td>
			<td><input type="text" name="courseGrade.std.code" value="" style="width:120px" maxlength="35" onblur="getStudentInfo(event)"/></td>
			<input type="hidden" id="c_stdId" name="courseGrade.std.id" value=""/>
			<td class="title"><@msg.message key="attr.studentName"/>：</td>
			<td id="c_stdName"></td>
			<td class="title"><@msg.message key="entity.speciality"/>：</td>
			<td id="c_speciality"></td>
		</tr>
		<tr>
			<td class="title"><@msg.message key="std.specialityType"/>：</td>
			<td>
				<select name="courseGrade.majorType.id" style="width:120px">
					<option value="1"><@msg.message key="entity.firstSpeciality"/></option>
					<option value="2"><@msg.message key="entity.secondSpeciality"/></option>
				</select>
			</td>
			<td class="title"><@msg.message key="entity.markStyle"/>：</td>
			<td id="markStyleValue"></td>
			<td class="title"><@msg.message key="grade.scorePrecision"/>:</td>
	      	<td>
	           <select name="precision">
	 	          <option value="0"><@msg.message key="grade.precision0"/></option>
	              <option value="1"><@msg.message key="grade.precision1"/></option>
	 	 	   </select>
	 	    </td>
		</tr>
		<tr>
			<td class="darkColumn" colspan="6" style="text-align:center; font-weight:bold">各阶段考试成绩</td>
		</tr>
		<tr id="gradeInputTr">
			<td colspan="6">
			<#list markStyles as markStyle>
				<#assign gradeConverterConfig = (converter.getConfig(markStyle))?if_exists/>
				<#assign configs = (gradeConverterConfig.converters)?if_exists/>
				<#if !gradeConverterConfig?exists || configs?size == 0>
					<div id="markStyleDiv${markStyle.id}">
					<table class="formTable" width="100%"><tr>
						<#list 0..(gradeTypes?size - 1) as k>
							<td class="title" id="f_gradeType${gradeTypes[k].id}${markStyle.id}"><@i18nName gradeTypes[k]/>：</td>
							<td><input type="text" name="gradeType${gradeTypes[k].id}${markStyle.id}" value="" maxlength="5" style="width:120px"/></td>
							<input type="hidden" name="examGradeId${gradeTypes[k].id}${markStyle.id}" value=""/>
						</#list>
					</tr></table>
					</div>
				<#else>
					<div id="markStyleDiv${markStyle.id}">
					<table class="formTable" width="100%"><tr>
						<#list 0..(gradeTypes?size - 1) as k>
							<td class="title" id="f_gradeType${gradeTypes[k].id}${markStyle.id}"><@i18nName gradeTypes[k]/>：</td>
							<td>
								<select name="gradeType${gradeTypes[k].id}${markStyle.id}" style="width:120px">
						           		<option>...</option>
									<#list configs as converter>
						           		<option value="${converter.defaultScore}">${converter.grade}</option>
						           	</#list>
								</select>
								<input type="hidden" name="examGradeId${gradeTypes[k].id}${markStyle.id}" value=""/>
							</td>
						</#list>
					</tr>
					</table>
					</div>
				</#if>
			</#list>
			</td>
		<tr>
			<td class="darkColumn" colspan="6" style="text-align:center">
				<button onclick="saveAddGrade()">保存</button>&nbsp;
				<button onclick="formReset()">重置</button>
			</td>
		</tr>
		</form>
	</table>
	<#include "/templates/calendarSelect.ftl"/>
	<script>
		var bar = new ToolBar("bar", "学生成绩添加（按课程序号－单个学生）", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addClose("<@msg.message key="action.close"/>");
		
		function displayMarkStyle(value) {
			<#list markStyles as markStyle>
				$("markStyleDiv${markStyle.id}").style.display = (value == null || value == "") ? <#if markStyle.id == 1>"block"<#else>"none"</#if> : (value == ${markStyle.id} ? "block" : "none");
			</#list>
		}
		displayMarkStyle();
		
		var form = document.actionForm;
		var params = "";
	    <#list RequestParameters?keys as key>
	    	params += "&${key}=${RequestParameters[key]}";
	    </#list>
		var isValid = true;
	    var messages = {
	     	'inputError':'请正确输入学号和课程序号。',
	     	'stdNoCourse':'该学生不在上课名单中。',
	     	'stdBeenCourse':'该学生已经有成绩了。',
	     	'stdNoGrade':'该学生当前还没有成绩。'
	    };
	     
		form["calendar.studentType.id"].value = "${RequestParameters["courseGrade.calendar.studentType.id"]?default("")}";
		form["calendar.year"].value = "${RequestParameters["courseGrade.calendar.year"]?default("")}";
		form["calendar.term"].value = "${RequestParameters["courseGrade.calendar.term"]?default("")}";
		
		var no_std = "没有该学号对应的学生";
		function getStudentInfo(event) {
     		var stdCode = form['courseGrade.std.code'].value;
     		if (stdCode == "") {
     			clear();
       			$('message').innerHTML = "<font color='red'>请输入学号</font>";
       			$('message').style.display = "block";
     		} else {
       			studentDAO.getBasicInfoName(setStdInfo, stdCode);
     		}
     		
		}
		
  		function setStdInfo(data){
    		if (null == data) {
       			clear();
       			$('message').innerHTML = no_std;
       			$('message').style.display = "block";
       			form["courseGrade.std.code"].value = "";
			} else {
        		$('c_stdName').innerHTML = data.name;
		        $('c_stdId').value = data.id;
		        $('c_speciality').innerHTML = data['firstMajor.name'];
		        $('message').innerHTML = "";
		        $('message').style.display = "none";
				checkStdCourseValidator();
    		}
  		}
  		
		function clear() {
       		$('c_stdName').innerHTML = "";
       		$('c_stdId').value = "";
       		$('c_speciality').innerHTML = "";
       		$('c_taskId').value = "";
  		}

	     function getTeachTaskInfo(event){
	        var input = getEventTarget(event);
	        if (input.value != "") {
		        $('message').innerHTML = "";
		        $('message').style.display = "none";
		        teachTaskService.getTeachTaskDWR(setTeachTaskData, form['courseGrade.taskSeqNo'].value, form["calendar.studentType.id"].value, form["calendar.year"].value, form["calendar.term"].value);
	         } else {
	           	$("message").innerHTML = "<font color='red'>请输入课程序号</font>";
	           	$('message').style.display = "block";
	           	$('c_taskId').value = "";
	           	$('c_courseNo').innerHTML = "";
	           	$('c_courseName').innerHTML = "";
	         }
	     }
	     
	     function setTeachTaskData(data){
	        if (null == data) {
	           	$("message").innerHTML = "<font color='red'>该学期没有该课程或者不唯一</font>";
	           	$('message').style.display = "block";
	           	$('c_taskId').value = "";
	           	$('c_courseNo').innerHTML = "";
	           	$('c_courseName').innerHTML = "";
				$("markStyleValue").innerHTML = "";
	           	form["courseGrade.taskSeqNo"].value = "";
	           	form['precision'].value = "";
	        } else {
		        $('message').innerHTML = "";
		        $('message').style.display = "none";
		        $('c_taskId').value = data.id;
	           	$('c_courseNo').innerHTML = data["course.code"];
	           	$('c_courseName').innerHTML = data["course.name"];
	           	form['precision'].value = data["task.gradeState.precision"];
				$("markStyleValue").innerHTML = data["task.gradeState.markStyle.name"];
				var gradeMarkStyle = data["task.gradeState.markStyle.id"];
				displayMarkStyle(gradeMarkStyle);
	        	checkStdCourseValidator();
	        }
	     }
	     
	     function checkStdCourseValidator() {
	     	var taskId = form["courseGrade.task.id"].value;
	     	var stdId = form["courseGrade.std.id"].value;
	     	if (null != taskId && "" != taskId && null != stdId && "" != stdId) {
	     		courseTakeDAO.stdCourseValidator(setStdCourseValidator, taskId, stdId);
	     	}
	     }
	     
	     function setStdCourseValidator(data) {
	     	if (data != null) {
	     		var message = data["message"];
	     		if (null != message && "" != message) {
	     			$("message").innerHTML = messages[message];
	     			if (message == "inputError") {
	     				isValid = false;
	     			} else {
	     				isValid = true;
	     			}
	     		} else {
	     			$("message").innerHTML = "";
	     			isValid = true;
	     		}
				form["courseGrade.id"].value = data["id"] == null ? "" : data["id"];
				<#list markStyles as markStyle>
	     			<#list 0..(gradeTypes?size - 1) as k>
	     				form["gradeType${gradeTypes[k].id}${markStyle.id}"].value = data["gradeType${gradeTypes[k].id}${markStyle.id}"] == null ? "" : data["gradeType${gradeTypes[k].id}${markStyle.id}"];
	     				form["examGradeId${gradeTypes[k].id}${markStyle.id}"].value = data["examGradeId${gradeTypes[k].id}${markStyle.id}"] == null ? "" : data["examGradeId${gradeTypes[k].id}${markStyle.id}"];
	     			</#list>
     			</#list>
	     	}
	     	if (null == $("message").innerHTML || "" == $("message").innerHTML) {
	     		$("message").style.display = "none";
	     	} else {
	     		$("message").style.display = "block";
	     	}
	     }
	     
	     function saveAddGrade() {
	     	var a_fields = {
	     		'courseGrade.taskSeqNo':{'l':'<@msg.message key="attr.taskNo"/>', 'r':true, 't':'f_course'},
	     		'courseGrade.std.code':{'l':'<@msg.message key="std.code"/>', 'r':true, 't':'f_student'},
	     		<#list markStyles as markStyle>
	     			<#assign gradeConverterConfig = (converter.getConfig(markStyle))?if_exists/>
					<#assign configs = (gradeConverterConfig.converters)?if_exists/>
		     		<#list 0..(gradeTypes?size - 1) as k>
		     			'gradeType${gradeTypes[k].id}${markStyle.id}':{'l':'<@i18nName gradeTypes[k]/>', 'r':false, 't':'f_gradeType${gradeTypes[k].id}${markStyle.id}'<#if !gradeConverterConfig?exists || configs?size == 0>, 'f':'unsignedReal'</#if>}<#if (k_has_next) || (markStyle_has_next)>,</#if>
		     		</#list>
	     		</#list>
	     	};
	     	var v = new validator(form, a_fields, null);
	     	if (v.exec()) {
	     		var message = $("message").innerHTML;
	     		if (isValid == false) {
		     		alert(message.substr(0 , message.length - 1) + "，所以不能添加。");
		     		return;
		     	}
	     		form.action = "stdGrade.do?method=saveAddGrade";
	     		addInput(form, "params", params, "hidden");
	     		form.submit();
	     	}
	     }
	     
	     function formReset() {
	     	form.reset();
	     	clear();
	     	$("message").innerHTML = "";
	     	$("message").style.display = "none";
           	$('c_courseNo').innerHTML = "";
           	$('c_courseName').innerHTML = "";
           	form["courseGrade.taskSeqNo"].value = "";
	     }
	</script>
</body>
<#include "/templates/foot.ftl"/>