<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <script language="javaScript">
 	function doAction(form){
     var a_fields = {
          		'teacherName':{'l':'<@bean.message key="field.configRationWorkload.selectTeacher"/>','r':true, 't':'f_teacherId'},
          		'workloadValue':{'l':'<@bean.message key="field.configRationWorkload.selectTeacher"/>','r':true, 't':'f_value','f':'unsigned','mx':10}
          };
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
     	form.action="configRationWorkloadAction.do?method=doModifyForTeacher";
        form.submit();
     }
   }
   function point(){
   		var id =document.listForm.collegeId.value
   		var url="configRationWorkloadAction.do?method=doGetTeacher&collegeId="+id;
   		window.open(url, '', 'scrollbars=yes,left=0,top=0,width=500,height=550,status=yes');
   }
   function setTeacherIdAndDescriptions(ids,descriptions){
 		document.listForm['teacherId'].value = ids;
	   	document.listForm['teacherName'].value = descriptions.replace(/,/gi, "\n");
 	}
 </script>
  <body>
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
  <tr>
  	<td>
  		<#assign labInfo><@bean.message key="field.configRationWorkload.sigleTeacher"/></#assign>
  		<#include "/templates/help.ftl"/>
  	</td>
  </tr>
  <tr>
  	<td>
  		<form name="listForm" method="post" action="" onsubmit="return false;">
  		<table width="100%" align="center" class="listTable">
  			<tr class="darkColumn" align="center">
  				<td colspan="2">
  					<@bean.message key="field.configRationWorkload.sigleTeacher"/>
  				</td>
  			</tr>
  			<tr>
  				<td class="grayStyle" align="center" id="f_collegeId">
  					<@bean.message key="field.configRationWorkload.selectCollege"/>:
  				</td>
  				<td class="brightStyle" align="left">
  					<select name="collegeId" style="width:300px;">
  						<#list result.collegeList?if_exists as college>
  							<option value="${college.id}">${college.name}</option>
  						</#list>
  					</select>
  				</td>
  			</tr>
  			<tr>
  				<td class="grayStyle" align="center" id="f_teacherId">
  					<@bean.message key="field.configRationWorkload.selectTeacher"/><font color="red">*</font>:
  				</td>
  				<td class="brightStyle" align="left">
  					<input type="hidden" name="teacherId">
  					<input type="text" name="teacherName" style="width:300px;" readonly="true">
  					<input type="button" value="<@bean.message key="field.configRationWorkload.selectTeacher"/>" name="button1" onClick="point()" class="buttonStyle" />
  				</td>
  			</tr>
  			<tr>
  				<td class="grayStyle" align="center" id="f_value">
  					<@bean.message key="rationWorkload.value"/><font color="red">*</font>:
  				</td>
  				<td class="brightStyle" align="left">
  					<input type="text" name="workloadValue" style="width;300px;">
  				</td>
  			</tr>
  			<tr>
  				<td class="darkColumn" align="center" colspan="2">
  					<input type="button" value="<@bean.message key="system.button.submit"/>" name="button1" onClick="doAction(this.form)" class="buttonStyle" />&nbsp;
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