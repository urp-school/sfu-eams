<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<table class="formTable" width="100%" align="center">
	<form method="post" action="stdPractice.do?method=save" name="practiceForm" onsubmit="return false;">
	    <tr>
	        <td class="darkColumn" align="center" style="font-size:16px; font-weight:bold" colspan="4">我 的 毕 业 实 习</td>
	    </tr>
	    <tr>
	    	<td class="title" width="15%">学生学号：</td>
	    	<td width="30%">&nbsp;&nbsp;${student.code}</td>
	    	<td class="title" width="15%">学生姓名：</td>
	    	<td id="stdName" width="30%">&nbsp;&nbsp;${student.name}</td>
	    </tr>
	    <input type="hidden" name="graduatePractice.majorType.id" value="${(!student.secondMajor?exists || !student.isSecondMajorStudy?default(false))?string("1", "2")}"/>
		<tr>
			<td class="title" id="f_company"><font color="red">*</font>实习单位:</td>
			<td>&nbsp;&nbsp;<input type="text" name="graduatePractice.practiceCompany" maxlength="30" value="${(graduatePractice.practiceCompany)?if_exists}" style="width:200px"/></td>
	    	<td class="title" width="15%" id="f_practiceSource">实习方式：</td>
	    	<td width="30%">&nbsp;<@htm.i18nSelect datas=practiceSources selected="${(graduatePractice.practiceSource.id)?default('')?string}" name="graduatePractice.practiceSource.id" style="width:100px;"/></td>
		</tr>
	    <tr>
	    	<td class="title" width="15%" id="f_isBase">是否实习基地：</td>
	    	<td width="30%">&nbsp;<@htm.radio2 name="graduatePractice.isPractictBase" value=(graduatePractice.isPractictBase)?default(false)/></td>
	    	<td class="title" width="15%">指导教师：</td>
	    	<td width="30%">&nbsp;
	       <select id="teachDepartment" style="width:150px">
	         <#if (graduatePractice.student.teacher.id)?exists><#assign departmentId = student.teacher.department.id/><#else><#assign departmentId = student.department.id/></#if>
   	         	<option value="${departmentId}"></option>
	       	</select>
   	       	<select id="teacher" name="graduatePractice.student.teacher.id" style="width:150px">
   	         	<option value="${(student.teacher.id)?if_exists}"></option>
	       	</select>
	     </td>
	    </tr>
		<tr>
		 	<td class="title" id="f_practiceDesc">实习描述<font color="red">*</font>： </td>
		 	<td colspan="3">&nbsp;&nbsp;<textarea name="graduatePractice.practiceDesc" colspan="50" rowspan="3" style="width:300px;">${(graduatePractice.practiceDesc)?if_exists}</textarea></td>
		</tr>
	   	<tr class="darkColumn" align="center">
	     	<td colspan="4">
	       		<input type="hidden" name="graduatePractice.id" value="${(graduatePractice.id)?if_exists}"/>
	       		<input type="hidden" name="graduatePractice.teachCalendar.id" value="${graduatePractice.teachCalendar.id}"/>
		      	<button name="button1" onClick="save(this.form)">保存</button>
	     	</td>
		</tr>
	</form>
</table>
<#assign departmentList = teachDepartList/>
<#include "/templates/departTeacher2Select.ftl"/>
<script>
 	var form =document.practiceForm;
	function save() {
	    form.action="stdPractice.do?method=save";
		var a_fields = {
		    'graduatePractice.practiceCompany':{'l':'实习单位', 'r':true, 't':'f_company','mx':100},
	        'graduatePractice.practiceSource.id':{'l':'实习方式', 'r':true, 't':'f_practiceSource'},
	        'graduatePractice.isPractictBase':{'l':'是否实习基地', 'r':true, 't':'f_isBase'},
	        'graduatePractice.practiceDesc':{'l':'实习描述', 'r':true, 't':'f_practiceDesc','mx':200}
	    };
	    var v = new validator(form , a_fields, null);
	    if (v.exec()) {
			form.submit();
		}
	}
</script>