<#include "/templates/head.ftl"/>
<#include "/pages/system/calendar/timeFunction.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="alterationBar" width="100%"></table>
  <table cellpadding="0" cellspacing="0" width="100%" border="0">      
   <tr>
    <td>
     <table width="95%" align="center" class="listTable">
	   <tr align="center" class="darkColumn">
	   	 <td width="8%"><@msg.message key="attr.stdNo"/></td>
	   	 <td width="10%">学生姓名</td>
	   	 <td width="5%"><@msg.message key="attr.taskNo"/></td>
	     <td width="5%"><@msg.message key="attr.courseNo"/></td>
	     <td width="15%"><@msg.message key="attr.courseName"/></td>
	     <td width="10%"><@bean.message key="info.duty.dutyDate"/></td>
	     <td width="8%">起始小节</td>
	     <td width="8%">结束小节</td>
	     <td width="8%"><@bean.message key="info.duty.timeBeginHour"/></td>
	     <td width="8%"><@bean.message key="info.duty.timeEndHour"/></td>
	     <td width="10%">考勤状态</td>
	     <td width="10%">修读类别</td>
	     <td width="10%"><@bean.message key="action.modify"/></td>
	     <td width="10%"><@bean.message key="action.delete"/></td>
	   </tr>	   
	   <#list result.recordDetailList?if_exists as recordDetail >
	   <#if recordDetail_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if recordDetail_index%2==0 ><#assign class="brightStyle" ></#if>	   
	   <form name="commonForm${recordDetail_index}" action="dutyRecordManager.do" method="post">
	   <tr align="center" class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
	    <#if recordDetail?exists>
	   		<td>${recordDetail.dutyRecord.student.code}</td>
	   		<td><@i18nName recordDetail.dutyRecord.student /></td>
	   		<td>${recordDetail.dutyRecord.teachTask.seqNo}</td>
	     	<td>${recordDetail.dutyRecord.teachTask.course.code}</td>
	     	<td><@i18nName recordDetail.dutyRecord.teachTask.course /></td>
	   		<td><#if recordDetail['dutyDate']?exists>${recordDetail['dutyDate']?string("yyyy-MM-dd")}</#if></td>
	   		<input type="hidden" name="recordDetail.dutyDate" value="${(recordDetail['dutyDate']?string("yyyy-MM-dd"))?if_exists}" />
	   		<td><@i18nName recordDetail['beginUnit']?if_exists /></td>
	   		<input type="hidden" name="recordDetail.beginUnit.id" value="${recordDetail['beginUnit']?if_exists.id}" />
	   		<td><@i18nName recordDetail['endUnit']?if_exists /></td>
	   		<input type="hidden" name="recordDetail.endUnit.id" value="${recordDetail['endUnit']?if_exists.id}" />
	   		<td><@getTimeStr recordDetail['beginUnit']?if_exists.startTime?if_exists /></td>
	   		<td><@getTimeStr recordDetail['endUnit']?if_exists.finishTime?if_exists /></td>
	   		<td><select name="recordDetail.dutyStatus.id" style="width:100%;border:0 solid #000000;" >			
				<#list result.dutyStatusList?if_exists?sort_by("code") as dutyStatus>
	   				<option value="${dutyStatus.id}" <#if dutyStatus.id==recordDetail.dutyStatus.id>selected</#if>><@i18nName dutyStatus/></option>
	   			</#list>
				</select>
			</td>	   
			<td>&nbsp;<@i18nName recordDetail.dutyRecord.getCourseTakeType(false) /></td>
	   	<#else>
	   		<td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
	   	</#if>
	    <td width="20%" align="center"><input type="button" onClick="doAction(this.form)" name="button${recordDetail_index}" value="<@bean.message key="system.button.modify"/>" class="buttonStyle"></td>
	    <td width="20%" align="center"><input type="button" onClick="deleteAction(this.form)" name="button${recordDetail_index}" value="<@bean.message key="system.button.del"/>" class="buttonStyle"></td>
	   </tr>
	   <input type="hidden" name="recordDetail.id" value="${recordDetail.id}" />	   
	   <input type="hidden" name="method" value="updateDutyRecordDetail" /> 
	   </form>
	   </#list>	   
	   <input type="hidden" name="params" value="&ids=${RequestParameters['ids']?if_exists}&beginDate=${RequestParameters['beginDate']?if_exists}&endDate=${RequestParameters['endDate']?if_exists}" /> 
     </table>
    </td>
   </tr>
  </table> 
 </body> 
 <script>
 	var bar = new ToolBar('alterationBar','学生时段考勤维护',null,true,true);
	bar.setMessage('<@getMessage/>');
 	var flag=true;
    function doAction(form){
    	  if(flag){
    	  form.method.value="updateDutyRecordDetail";	    	
    	  form.appendChild(document.all.params);
          form.submit();
          flag=false;
          setTimeout('flag=true',3000); 
    	  }else{
    	  	alert("正在操作，请稍候！");
    	  }
    }
    
    function deleteAction(form){
    	if(flag){
    	if(confirm("<@bean.message key="info.duty.deleteConfirm"/>")){
	    	form.method.value="deleteRecordDetail";	    	
	    	form.appendChild(document.all.params);
	    	form.submit();
	    	flag=false;
          	setTimeout('flag=true',3000); 
	    }}else{
    	  	alert("正在操作，请稍候！");
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