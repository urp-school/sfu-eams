<#include "/templates/head.ftl"/>
<body > 
<script language="JavaScript" type="text/JavaScript" src="<@msg.message key="validator.js.url" />"></script>
<#assign labInfo>复制进度基本信息</#assign>
<#include "/templates/back.ftl"/>
<table width="100%" align="center" class="formTable">
  <form name="conditionForm" method="post" action="" onsubmit="return false;">
	   <tr class="darkColumn" align="center">
	     <td colspan="2">进度基本信息</td>
	   </tr>
	   <#if copyType=="single">
	   <tr>
	     <td class="title" width="25%">
	      学生类别<font color="red">*</font>：
	     </td>
	      <td>
	      <@htm.i18nSelect datas=stdTypeList selected="${(schedule.studentType.id)?default('')?string}" name="schedule.studentType.id"  style="width:150px;" /></td>
	     </td>
	   </tr>
	   </#if>
	    <tr>
	     <td class="title">
	       入学年份<font color="red">*</font>：
	     </td>
	     <td>
		    <input type="text" id="enrollYear" maxlength="7" name="schedule.enrollYear" value="${(schedule.enrollYear)?if_exists}"/>格式为2006-1 前为年份 后为月份          
	     </td>
	   </tr>
	   <#if copyType=="single">
	   <tr>
	   	<td class="title">
	      &nbsp;学制<font color="red">*</font>：
	     </td>
	     <td>
	     	<select name="schedule.studyLength" style="width:150px">
	     		<option value="1" <#if schedule.studyLength?string=="1">selected</#if>>1</option>
	     		<option value="1.5" <#if schedule.studyLength?string=="1.5">selected</#if>>1.5</option>
	     		<option value="2" <#if schedule.studyLength?string=="2">selected</#if>>2</option>
	     		<option value="2.5" <#if schedule.studyLength?string=="2.5">selected</#if>>2.5</option>
	     		<option value="3" <#if schedule.studyLength?string=="3">selected</#if>>3</option>
	     		<option value="3.5" <#if schedule.studyLength?string=="3.5">selected</#if>>3.5</option>
	     		<option value="4" <#if schedule.studyLength?string=="4">selected</#if>>4</option>
	     		<option value="4.5" <#if schedule.studyLength?default("0")=="4.5">selected</#if>>4.5</option>
	     		<option value="5" <#if schedule.studyLength?default("0")=="5">selected</#if>>5</option>
	     		<option value="5.5" <#if schedule.studyLength?default("0")=="5.5">selected</#if>>5.5</option>
	     		<option value="6" <#if schedule.studyLength?default("0")=="6">selected</#if>>6</option>
	     	</select>年
	     </td>
	   </tr>
	   </#if>
	    <tr>
	     <td class="title">
	      &nbsp;进度备注：
	     </td>
	     <td><textarea id="remark" name="schedule.remark" cols="30" rows="3">${(schedule.remark)?if_exists}</textarea>
	     </td>
	   </tr>	   	   
	   <tr class="darkColumn" align="center">
	     <td colspan="2">
	       <input type="hidden" name="<#if copyType=="single">scheduleId<#else>scheduleIds</#if>" value="${scheduleIds?if_exists}">
		   <button name="button1" onClick="doAction(this.form)" class="buttonStyle"><@msg.message key="system.button.submit"/></button>
	     </td>
	   </tr>
	   </form>
   </table>
 </body>
 <script language="javascript" >
     function doAction(form){
     	var errors ="";
        var enrollYear = document.getElementById("enrollYear");
        if(!/^\d{4}\-\d/.test(enrollYear.value)){
        	errors+="入学年份必须符合yyyy-d表示\n";
        }
        var remark = document.getElementById("enrollYear");
        if(remark.value.length>200){
        	errors+="进度备注不能超过200个字\n";
        }
        if(""!=errors){
        	alert(errors);
        	return;
        }
        setSearchParams(parent.document.searchForm,form);
     	form.action="thesisSchedule.do?method=<#if copyType=="single">copy<#else>batchCopy</#if>";
        form.submit();
    }
 </script>
<#include "/templates/foot.ftl"/>