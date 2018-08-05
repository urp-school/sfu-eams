<#include "/templates/head.ftl"/>
<#include "/pages/system/calendar/timeFunction.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table width="85%" align="center" class="listTable">
	   <tr align="center" class="darkColumn">
	     <td width="10%"><@bean.message key="attr.courseNo"/></td>
	     <td width="20%"><@bean.message key="attr.courseName"/></td>
	     <td width="14%"><@bean.message key="entity.courseType"/></td>
	     <td width="11%"><@bean.message key="attr.year2year"/></td>
	     <td width="10%"><@bean.message key="attr.term"/></td>
	     <td width="10%"><@bean.message key="entity.teacher"/></td>
	     <td width="19%"><@bean.message key="entity.teachClass"/></td>
	   </tr>
	   <#assign teachTask >${result.teachTask}</#assign>
	   <tr class="brightStyle">
	    <td>&nbsp;${ result.teachTask.course.code?if_exists}</td>
	    <td>&nbsp;<@i18nName result.teachTask.course?if_exists/></td>
	    <td>&nbsp;<@i18nName result.teachTask.courseType?if_exists/></td>
	    <td>&nbsp;${result.teachTask.calendar.year}</td>
	    <td>&nbsp;${result.teachTask.calendar.term}</td>
	    <td>&nbsp;<@getTeacherNames result.teachTask.arrangeInfo.teachers?if_exists/><#--<@eraseComma teachTask.arrangeInfo.teacherNames?if_exists/>--></td>
	    <td>&nbsp;<@i18nName result.teachTask.teachClass?if_exists/></td>
	   </tr>
     </table>
	<br>
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <tr>
    <td>
     <table width="85%" align="center" class="listTable">
	   <tr align="center" class="darkColumn">
	     <td><@bean.message key="info.duty.dutyDate"/></td>
	     <td>起始小节</td>
	     <td>结束小节</td>
	     <td><@bean.message key="info.duty.timeBeginHour"/></td>
	     <td><@bean.message key="info.duty.timeEndHour"/></td>
	     <td><@bean.message key="action.modify"/></td>
	     <td><@bean.message key="action.delete"/></td>
	   </tr>	   
	   <#list result.dutyRecordDetailBatchList?if_exists as dutyRecordDetailBatch>
	   <#if dutyRecordDetailBatch_index%2==1 ><#assign class="grayStyle"/></#if>
	   <#if dutyRecordDetailBatch_index%2==0 ><#assign class="brightStyle"/></#if>
	   <form name="commonForm${dutyRecordDetailBatch_index}" action="dutyRecordManager.do" method="post" onsubmit="return false;">
	   <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
	    <#if dutyRecordDetailBatch?exists>
	   		<td>&nbsp;<#if dutyRecordDetailBatch['dutyDate']?exists>${dutyRecordDetailBatch['dutyDate']?string("yyyy-MM-dd")}</#if></td>
	   		<input type="hidden" name="recordDetail.dutyDate" value="${(dutyRecordDetailBatch['dutyDate']?string("yyyy-MM-dd"))?if_exists}"/>
	   		<td>&nbsp;<@i18nName dutyRecordDetailBatch['beginUnit']?if_exists/></td>
	   		<input type="hidden" name="recordDetail.beginUnit.id" value="${dutyRecordDetailBatch['beginUnit']?if_exists.id}"/>
	   		<td>&nbsp;<@i18nName dutyRecordDetailBatch['endUnit']?if_exists/></td>
	   		<input type="hidden" name="recordDetail.endUnit.id" value="${dutyRecordDetailBatch['endUnit']?if_exists.id}"/>
	   		<td>&nbsp;<@getTimeStr dutyRecordDetailBatch['beginUnit']?if_exists.startTime?if_exists/></td>
	   		<td>&nbsp;<@getTimeStr dutyRecordDetailBatch['endUnit']?if_exists.finishTime?if_exists/></td>
	   
	   	<#else>
	   		<td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
	   	</#if>
	    <td width="20%" align="center"><input type="button" onClick="doAction(this.form)" name="button${dutyRecordDetailBatch_index}" value="<@bean.message key="system.button.modify"/>" class="buttonStyle"></td>
	    <td width="20%" align="center"><input type="button" onClick="deleteAction(this.form)" name="button${dutyRecordDetailBatch_index}" value="<@bean.message key="system.button.del"/>" class="buttonStyle"></td>
	   </tr>
	   <input type="hidden" name="teachTaskId" value="${result.teachTaskId}"/>
	   <input type="hidden" name="method" value="updateDutyRecordForm"/>
	   </form>
	   </#list>
     </table>
    </td>
   </tr>
  </table> 
 </body> 
 <script>
    function doAction(form){
          form.submit();
    }
    
    function deleteAction(form){
    	if(confirm("<@bean.message key="info.duty.deleteConfirm"/>")){
	    	form.method.value="deleteDutyRecord";
	    	form.submit();
	    }
    }
    
    function dealAction(form){
       var a_fields = {
         'grade':{'l':'<@bean.message key="attr.secondSpecialityGrade"/>', 'r':false, 't':'f_grade', 'f':'unsigned'}
       };
     
       var v = new validator(form, a_fields, null);
       if (v.exec()) {
          form.submit();
       }
    }
    function doAdd(form){
       var a_fields = {
         'grade':{'l':'<@bean.message key="attr.secondSpecialityGrade"/>', 'r':true, 't':'f_grade', 'f':'unsigned'}
       };
     
       var v = new validator(form, a_fields, null); 
       if (v.exec()) {
          form.submit();
       }
    }
 </script>
<#include "/templates/foot.ftl"/>