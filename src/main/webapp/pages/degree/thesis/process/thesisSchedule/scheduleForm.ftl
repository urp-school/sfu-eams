<#include "/templates/head.ftl"/>
<body > 
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<#assign labInfo><#if schedule.id?exists>修改进度基本信息<#else>添加进度基本信息</#if></#assign>
<#include "/templates/back.ftl"/>
<table width="100%" align="center" class="formTable">
  <form name="conditionForm" method="post" action="" onsubmit="return false;">
	   <tr class="darkColumn" align="center">
	     <td colspan="2">进度基本信息</td>
	   </tr>
	   <tr>
	     <td class="title" width="25%"> <font color="red">*</font>学生类别：</td>
	      <td>
	      <@htm.i18nSelect datas=stdTypeList selected="${(schedule.studentType.id)?default('')?string}" name="schedule.studentType.id"  style="width:150px;" /></td>
	     </td>
	   </tr>
	    <tr>
	     <td class="title" id="f_enrollYear"><font color="red">*</font>入学年份：</td>
	     <td>
		    <input type="text" id="enrollYear" maxlength="7" name="schedule.enrollYear" value="${schedule?if_exists.enrollYear?if_exists}"/>格式为2006-1 前为年份 后为月份          
	     </td>
	   </tr>
	   <tr>
	   	<td class="title"><font color="red">*</font>&nbsp;学制：</td>
	     <td>
	     	<select name="schedule.studyLength" style="width:150px">
	     		<option value="1" <#if schedule.studyLength?default("0")=="1">selected</#if>>1</option>
	     		<option value="1.5" <#if schedule.studyLength?default("0")=="1.5">selected</#if>>1.5</option>
	     		<option value="2" <#if schedule.studyLength?default("0")=="2">selected</#if>>2</option>
	     		<option value="2.5" <#if schedule.studyLength?default("0")=="2.5">selected</#if>>2.5</option>
	     		<option value="3" <#if schedule.studyLength?default("0")=="3">selected</#if>>3</option>
	     		<option value="3.5" <#if schedule.studyLength?default("0")=="3.5">selected</#if>>3.5</option>
	     		<option value="4" <#if schedule.studyLength?default("0")=="4">selected</#if>>4</option>
	     		<option value="4.5" <#if schedule.studyLength?default("0")=="4.5">selected</#if>>4.5</option>
	     		<option value="5" <#if schedule.studyLength?default("0")=="5">selected</#if>>5</option>
	     		<option value="5.5" <#if schedule.studyLength?default("0")=="5.5">selected</#if>>5.5</option>
	     		<option value="6" <#if schedule.studyLength?default("0")=="6">selected</#if>>6</option>
	     	</select>年
	     </td>
	   </tr>
	    <tr>
	     <td class="title" id="f_remark">&nbsp;进度备注：</td>
	     <td><textarea id="remark" name="schedule.remark" value="${schedule?if_exists.remark?if_exists}" cols="30" rows="3">${schedule.remark?if_exists}</textarea>
	     </td>
	   </tr>	   	   
	   <tr class="darkColumn" align="center">
	     <td colspan="2">
	       <input type="hidden" name="schedule.id" value="${schedule?if_exists.id?if_exists}">
		   <button name="button1" onClick="doAction(this.form)" class="buttonStyle"><@bean.message key="system.button.submit"/></button>
	     </td>
	   </tr>
	   </form>
   </table>
 </body>
 <script language="javascript" >
     function doAction(form){
     	 var a_fields = {
         'schedule.enrollYear':{'l':'所在年级', 'r':true, 't':'f_enrollYear','f':'yearMonth'},
         'schedule.remark':{'l':'<@msg.message key="attr.remark"/>', 'r':false, 't':'f_remark','mx':100}
         }
         var v = new validator(form , a_fields, null);
         if (v.exec()) {
	        setSearchParams(parent.document.searchForm,form);
	     	form.action="thesisSchedule.do?method=save";
	        form.submit();
	     }
    }
 </script>
<#include "/templates/foot.ftl"/>