<table width="100%">
	<tr class="infoTitle" style="text-valign:top;font-size:9pt;text-align:left;font-weight:bold">
		<td colspan="2"><img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;详细查询(模糊输入)</td>
	</tr>
	<tr class="font-size:0pt">
		<td colspan="2"><img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="texttop"/></td>
	</tr>
	<#include "/pages/components/initAspectSelectData.ftl"/>
	<tr>
		<td width="40%"><@bean.message key="attr.enrollTurn"/>:</td>
		<td><input name="adminClass.enrollYear" type="text" value="" style="width:100px" maxlength="7"/></td>
	</tr>
	<tr>
		<td><@bean.message key="attr.name"/>:</td>
		<td><input name="adminClass.name" type="text" value="" style="width:100px" maxlength="20"/></td>
	</tr>
	<tr>
		<td><@bean.message key="entity.studentType"/>:</td>
		<td>
			<select name="adminClass.stdType.id" id="class_stdTypeOfSpeciality" style="width:100px;">
	         	<option value=""><@bean.message key="common.selectPlease"/></option>
	        </select>
		</td>
	</tr>
	<tr>
		<td><@bean.message key="entity.college"/>:</td>
		<td>
	        <select id="class_department" name="adminClass.department.id" style="width:100px;">
	           <option value=""><@bean.message key="common.selectPlease"/></option>
	        </select>
		</td>
	</tr>
	<tr>
		<td><@bean.message key="entity.speciality"/>:</td>
		<td>
	        <select id="class_speciality" name="adminClass.speciality.id" style="width:100px;">
           		<option value=""><@bean.message key="common.selectPlease"/></option>
        	</select>
		</td>
	</tr>
	<tr>
		<td><@bean.message key="entity.specialityAspect"/>:</td>
		<td>
	        <select id="class_specialityAspect" name="adminClass.aspect.id" style="width:100px;">
         		<option value=""><@bean.message key="common.selectPlease"/></option>
        	</select>
		</td>
	</tr>
	<tr>
		<td>专业类别:</td>
		<td>
	        <select name="majorTypeId" onchange="changeClassSpecialityType(event)" style="width:100px;">
		        <option value="1">第一专业</option>
		        <option value="2">第二专业</option>
      		</select>
		</td>
	</tr>
	<tr>
		<td><@msg.message key="attr.state"/>:</td>
		<td>
			<select  name="adminClass.state" style="width:100px;">
		   		<option value="1"><@bean.message key="common.enabled"/></option>
		   		<option value="0"><@bean.message key="common.disabled" /></option>
	   		</select>
	   	</td>
	</tr>
   	<tr height="50px">
   		<td colspan="2" align="center">
   			<button onclick="search();"><@bean.message key="action.query"/></button>
   			<button onclick="this.form.reset()"><@bean.message key="action.reset"/></button>
   		</td>
   	</tr>
</table>
<script>
    var classSelect = new StdTypeDepart3Select("class_stdTypeOfSpeciality","class_department","class_speciality","class_specialityAspect",true,true,true,true);
    classSelect.init(stdTypeArray,departArray);
    classSelect.firstSpeciality=1;
    function changeClassSpecialityType(event){
       var select = getEventTarget(event);
       classSelect.firstSpeciality=select.value;
       fireChange($("class_department"));
    }
</script>
