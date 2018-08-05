<table width="100%">
	<tr>
		<td align="left" valign="bottom"><img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;<B>详细查询(模糊输入)</B></td>
	</tr>
	<tr>
	    <td colspan="8" style="font-size:0px">
			<img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
		</td>
	</tr>
</table>
<table width="100%" onkeypress="DWRUtil.onReturn(event, search)">
	<input type="hidden" name="pageNo" value="1"/>
	<tr>
		<td width="40%"><@bean.message key="attr.stdNo"/>:</td>
	    <td><input type="text" name="attendWarn.std.code" maxlength="32" size="10" value="${RequestParameters['student.code']?if_exists}" style="width:100px"/></td>
	</tr>
    <tr>
		<td><@msg.message key="attr.personName"/>:</td>
		<td><input type="text" name="attendWarn.std.name" maxlength="20" size="10" value="${RequestParameters['student.name']?if_exists}" style="width:100px"/></td>
	</tr>
	<tr>
	    <td>年级:</td>
	    <td><input type="text" name="attendWarn.std.enrollYear" maxlength="7" id='student.enrollYear' style="width:100px;"></td>
	</tr>
    <tr> 
	    <td><@bean.message key="entity.studentType"/>:</td>
	    <td>
			<select id="stdTypeOfSpeciality" name="attendWarn.std.type.id" style="width:100px;">
				<option value="${RequestParameters['student.type.id']?if_exists}"><@bean.message key="filed.choose"/></option>
			</select>
		</td>
	</tr>
	<tr>
		<td><@bean.message key="common.college"/>:</td>
		<td>
			<select id="department" name="department.id"  style="width:100px;">
				<option value=""><@bean.message key="filed.choose"/>...</option>
			</select>
		</td>
	</tr>
	<tr>
		<td><@bean.message key="entity.speciality"/>:</td>
		<td>
			<select id="speciality" name="speciality.id" style="width:100px;">
				<option value=""><@bean.message key="filed.choose"/>...</option>
			</select>
		</td>
	</tr>
	<#--
	<tr>
		<td><@bean.message key="entity.specialityAspect"/>:</td>
		<td>
			<select id="specialityAspect" name="specialityAspect.id" style="width:100px;">
				<option value=""><@bean.message key="filed.choose"/>...</option>
			</select>
		</td>
	</tr>	
	-->
	<tr>
		<td style="width:60px;"><@bean.message key="attr.year2year"/>:</td>
		<td style="width:100px;">
			<select id="year" name="attendWarn.calendar.year" style="width:100px;">
				<option value=""></option>
			</select>
		</td>
	</tr>
	<tr>
	    <td style="width:50px;"><@bean.message key="attr.term"/>:</td>
	    <td style="width:50px;">
			<select id="term" name="attendWarn.calendar.term" style="width:100px;">
				<option value=""></option>
			</select>
	   </td>
	</tr>
	<#--
	<tr>
		<td>专业类别:</td>
		<td>
			<select name="majorType.id" onchange="changeSpecialityType(event)" style="width:100px">
		        <option value="1">第一专业</option>
		        <option value="2">第二专业</option>
			</select>
		</td>
	</tr>
	-->
	<tr>
	   	<td>行政班:</td>
	   	<td>
			<select id="adminClass" name="adminClass.id" style="width:100px;">
				<option value="">...</option>
			</select>
		</td>
	</tr>
	<tr>
		<td>预警值:</td>
		<td>
			<input type="text" name="yjz" maxlength="6" value="" style="width:100px;"/>
		</td>
	</tr>
	<tr align="center">
		<td colspan="2">
			<button onClick="search(1)" style="width:60px"><@bean.message key="action.query"/></button>
		</td>
	</tr>
</table> 
<#include "../attendWarn/stdTypeDepart3Select.ftl"/>
<script src='dwr/interface/calendarDAO.js'></script>
<script src='scripts/common/CalendarSelect.js'></script>
<script src='dwr/interface/adminClassDAO.js'></script>
<script>
    var stdTypeArray = new Array();
    <#list stdTypeList as stdType>
    stdTypeArray[${stdType_index}]={'id':'${stdType.id?if_exists}','name':'<@i18nName stdType/>'};
    </#list>
    var dd = new CalendarSelect("stdTypeOfSpeciality","year","term",false,true,false);
    dd.init(stdTypeArray);
    <#--
    sds.firstSpeciality=1;    
    function changeSpecialityType(event){
       var select = getEventTarget(event);
       sds.firstSpeciality=select.value;
       fireChange($("department"));
    }-->
    
    function setAdminClassOptions(data){
       document.getElementById("adminClass").length=0;
       DWRUtil.addOptions('adminClass',[{'id':'','name':'...'}],'id','name');
       for(var i=0;i<data.length;i++){
          DWRUtil.addOptions('adminClass',[{'id':data[i][0],'name':data[i][1]}],'id','name');
       }
    }
    function getAdminClass(){    	
    	var enrollYear = document.getElementById("student.enrollYear").value;
    	var stdTypeId = document.getElementById("stdTypeOfSpeciality").value;
    	var departId = document.getElementById("department").value;
    	var specialityId = document.getElementById("speciality").value;
    	<#--var aspectId = document.getElementById("specialityAspect").value;-->
		adminClassDAO.getAdminClassIdAndNames(setAdminClassOptions,enrollYear,stdTypeId,departId,specialityId,'');
    }    
    <#--
    document.getElementById("student.enrollYear").onblur=getAdminClass();
    document.getElementById("speciality").onchange=getAdminClass();
        
    document.getElementById("stdTypeOfSpeciality").onchange=getAdminClass();
    document.getElementById("department").onchange=getAdminClass();
    document.getElementById("specialityAspect").onchange=getAdminClass();
    
    function notifyAspectChange(event){
    	getAdminClass();
    }  -->  
</script>