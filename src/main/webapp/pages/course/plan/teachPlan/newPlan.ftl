<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<script src='dwr/interface/teachPlanService.js'></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/course/TeachPlan.js"></script> 
<body>
	<#assign labInfo>培养计划基本信息</#assign>
	<#include "/templates/back.ftl"/>
    <table width="100%"  class="formTable">
      	<form action="teachPlan.do?method=save" name="planForm" method="post" onsubmit="return false;">
       	<tr class="darkColumn">
         	<td align="left" colspan="4"><B>培养计划基本信息</B></td>
       	</tr>
	   	<tr>
	     	<td id="f_enrollTurn" class="title"><@bean.message key="attr.enrollTurn"/><font color="red">*</font>:</td>
	     	<td class="brightStyle">	     
		      	<input type="hidden" name="teachPlan.id" value="${teachPlan.id?if_exists}"/>
		      	<input type="text" maxlength="7" name="teachPlan.enrollTurn" value="${teachPlan.enrollTurn?default("-1")}" size="7">
	     	</td>
	   	 	<td  id="f_department" class="title"><@bean.message key="common.college"/><font color="red">*</font>:</td>
	     	<td class="brightStyle"> 
		        <select id="department" name="teachPlan.department.id" style="width:155px;">
		           <option value="${(teachPlan.department.id)?if_exists}"><@bean.message key="common.selectPlease"/></option>
		        </select>
	     	</td>
	   	</tr>
	   	<tr>
    	 	<td  id="f_studentType" class="title"><@bean.message key="entity.studentType"/><font color="red">*</font>:</td>
         	<td class="brightStyle">
	            <select id="stdTypeOfSpeciality" name="teachPlan.stdType.id" style="width:100px;">
	              <option value="${teachPlan.stdType?if_exists.id?if_exists}"></option>
	            </select>
         	</td>
         	<td  class="title"><@bean.message key="entity.speciality"/>:</td>
	     	<td align="left" id="f_speciality" class="brightStyle">
		        <select id="speciality" name="teachPlan.speciality.id" style="width:155px;">
		           <option value="${teachPlan.speciality?if_exists.id?if_exists}"><@bean.message key="common.selectPlease"/></option>
		        </select>
		    </td>
       	</tr>
	   	<tr>
         	<td  id="f_termsCount"  class="title">学期数<font color="red">*</font>:</td>
         	<td class="brightStyle"><input type="text" name="teachPlan.termsCount" value="${teachPlan.termsCount?if_exists}" maxLength="2" style="width:30px"/>正整数(最多两位)</td>	   
	     	<td  class="title"><@bean.message key="entity.specialityAspect"/>&nbsp;&nbsp;:</td>
	     	<td align="left" id="f_specialityAspect" class="brightStyle">
	        	<select id="specialityAspect" name="teachPlan.aspect.id" style="width:200px;">
	         		<option value="${teachPlan.aspect?if_exists.id?if_exists}"><@bean.message key="common.selectPlease"/></option>
	        	</select>
	     	</td>
	   	</tr>
	   	<tr>
         	<td class="title" id="f_credit">总学分<font color="red">*</font>:</td>
         	<td class="brightStyle"><input name="teachPlan.credit" size=4 value="${teachPlan.credit?if_exists}" maxlength="3"/></td>
         	<td class="title" id="f_creditHour">总学时<font color="red">*</font>:</td>
         	<td class="brightStyle"><input name="teachPlan.creditHour" size=4 value="${teachPlan.creditHour?if_exists}" maxlength="4"></td>
       	</tr>
	   	<tr>
         	<td class="title" id="f_teacherNames">指导老师:</td>
         	<td colspan="3" class="brightStyle">
              	<textarea name="teachPlan.teacherNames" cols="40" rows="2"/>${teachPlan.teacherNames?if_exists}</textarea>
         	</td>
	   	</tr>
	   	<tr>
         	<td  class="title" id="f_remark"><@bean.message key="attr.remark"/>:</td>
         	<td colspan="3" class="brightStyle">
             	<textarea name="teachPlan.remark" cols="40" rows="2" maxlength="100"/>${teachPlan.remark?if_exists}</textarea>
         	</td>
	   	</tr>
	   	<tr align="center"  class="darkColumn">
	     	<td colspan="4">
           		<input type="button" onClick='save(this.form)' value="<@bean.message key="system.button.submit"/>" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
           		<input type="button" onClick='reset()' value="<@bean.message key="system.button.reset"/>" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
	     	</td>
	   	</tr>
	   	</form> 
	</table>
  	<script language="javascript">
     	function reset() {
       	   	document.classroomForm.reset();
      	}
         
     	function save(form) {
       		var collision=0;
	       	var a_fields = {
	         	'teachPlan.stdType.id':{'l':'<@bean.message key="entity.studentType"/>', 'r':true, 't':'f_studentType'},
	         	'teachPlan.department.id':{'l':'<@bean.message key="entity.college"/>','r':true,'t':'f_department'},
	         	'teachPlan.credit':{'l':'总学分','r':true,'t':'f_credit','f':'unsignedReal'},
	         	'teachPlan.creditHour':{'l':'总学时','r':true,'t':'f_creditHour','f':'unsigned'},
	         	'teachPlan.termsCount':{'l':'学期数','r':true,'t':'f_termsCount','f':'unsigned'},
	         	'teachPlan.enrollTurn':{'l':'所在年级','r':true, 't':'f_enrollTurn','f':'yearMonth'},
	         	'teachPlan.teacherNames':{'l':'指导老师', 'r':false, 't':'f_teacherNames', 'mx':100},
	         	'teachPlan.remark':{'l':'备注', 'r':false, 't':'f_remark', 'mx':100}
	       	};
	       	
	       	var v = new validator(form, a_fields, null);
	       	if (v.exec()) {
	         	DWREngine.setAsync(false);
	         	checkExist(form)
	         	if(collision == 1) {
	         		return;
	         	}
	         	form.action = "teachPlan.do?method=save";
	         	form.submit();
	     	}
	   	}
 	</script>
</body>
<#include "/templates/stdTypeDepart3Select.ftl"/>
<#include "/templates/foot.ftl"/>