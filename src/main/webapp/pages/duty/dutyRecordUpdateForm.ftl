<#include "/templates/head.ftl"/>
<#include "/pages/system/calendar/timeFunction.ftl"/>
<#include "/pages/duty/checkRecordDetail.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <tr>
    <td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B><@bean.message key="info.duty.studentList"/></B>
    </td>
   </tr>
   <tr>
   	 <table width="90%" align="center" class="listTable">
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
	    <td>&nbsp;${result.teachTask.calendar.year?if_exists}</td>
	    <td>&nbsp;${result.teachTask.calendar.term?if_exists}</td>
	    <td>&nbsp;<@getTeacherNames result.teachTask.arrangeInfo.teachers?if_exists/></td>
	    <td>&nbsp;<@i18nName result.teachTask.teachClass?if_exists/></td>	    
	   </tr>
     </table>
   </tr>
   <tr>
   <td>
   &nbsp;
   </td>
   </tr>
   <div id="commonFormDiv" style="display:block">
   <tr>
    <td>
     <table width="90%" align="center" class="listTable">
       <form name="commonForm" method="post" action="inputDutyRecord.do" onsubmit="return false;">
       <tr class="brightStyle" >
	    <td align="center" colspan="3">
	      <@bean.message key="info.duty.dutyDate"/>
	      <input type="text" id="dutyDate" name="recordDetail.dutyDate" onfocus="calendar()" size="10" value="${dutyDate?if_exists}<#--><#if dutyDate?exists>${dutyDate?string("yyyy-MM-dd")}</#if>-->"/>
	      <input type="hidden" id="oldDutyDate" name="oldDutyDate" value="${dutyDate?if_exists}<#--><#if dutyDate?exists>${dutyDate?string("yyyy-MM-dd")}</#if>-->"/>
	      <#--
	      <@bean.message key="info.duty.timeBeginHour"/>
	      <input type="text" name="timeBegin" value="${timeBegin}" size="5"/>
	      <input type="hidden" name="oldTimeBegin" value="${timeBegin}" size="5"/>
	      <@bean.message key="info.duty.timeBeginHour"/>
	      <input type="text" name="timeEnd" value="${timeEnd}" size="5"/>
	      <input type="hidden" name="oldTimeEnd" value="${timeEnd}" size="5"/>
	      -->
	      <#-->
	      <@bean.message key="info.duty.timeBeginHour"/>
	      <select name="timeBeginHour" style="width:60px;" onfocus="this.blur();">
	       <option value="00">00</option>
	       <#list 01..23 as i>
	       <option value="${i}">${i}</option>
	       </#list>
	      </select>：
	      <select name="timeBeginMinute" style="width:60px;" onfocus="this.blur();">
	       <option value="00">00</option>
	       <#list 01..59 as i>
	        <#if i%5==0>
	         <option value="${i?string("00")}">${i?string("00")}</option>
	        </#if>
	       </#list>
	      </select>
	      <input type="hidden" name="oldTimeBegin" value="${timeBegin}" />
	      <@bean.message key="info.duty.timeEndHour"/>
	      <select name="timeEndHour" style="width:60px;" onfocus="this.blur();">
	       <option value="00">00</option>
	       <#list 01..23 as i>
	       <option value="${i}">${i}</option>
	       </#list>
	      </select>：
	      <select name="timeEndMinute" style="width:60px;" onfocus="this.blur();">
	       <option value="00">00</option>
	       <#list 01..59 as i>
	        <#if i%5==0>
	         <option value="${i?string("00")}">${i?string("00")}</option>
	        </#if>
	       </#list>
	      </select>
	      <input type="hidden" name="oldTimeEnd" value="${timeEnd}" />
	      -->
	      <#--
	      <script>
	      	document.getElementByName("recordDetail.dutyDate").value="${dutyDate}";
	      	var timeBegin = ${timeBegin}.split(":");
	      	document.getElementByName("timeBeginHour").value=timeBegin[0];
	      	document.getElementByName("timeBeginMinute").value=timeBegin[1];
	      	var timeEnd = ${timeEnd}.split(":");
	      	document.getElementByName("timeEndHour").value=timeEnd[0];
	      	document.getElementByName("timeEndMinute").value=timeEnd[1];
	      </script>
	      -->
	      <#-->
	      <script>
	      	document.getElementById("timeBeginHour").value="${timeBeginHour[0..0]}" +"<#if timeBeginHour?length==2>${timeBeginHour[1..1]}</#if>";
	      	document.getElementById("timeBeginMinute").value="${timeBeginMinute[0..0]}" +"<#if timeBeginMinute?length==2>${timeBeginMinute[1..1]}</#if>";	      	
	      	document.getElementById("timeEndHour").value="${timeEndHour[0..0]}" +"<#if timeEndHour?length==2>${timeEndHour[1..1]?if_exists}</#if>";
	      	document.getElementById("timeEndMinute").value="${timeEndMinute[0..0]}" +"<#if timeEndMinute?length==2>${timeEndMinute[1..1]}</#if>";
	      </script>
	      -->
			开始小节<select id="beginUnit" name="recordDetail.beginUnit.id" style="width:60px;" onchange="changeBeginTime();" ></select>开始时间：<span id="beginTime" ></span>&nbsp;
			<input type="hidden" name="oldBeginUnit" value="${RequestParameters['recordDetail.beginUnit.id']}" />
			结束小节<select id="endUnit" name="recordDetail.endUnit.id" style="width:60px;" onchange="changeEndTime();" ></select>结束时间：<span id="endTime" ></span>
			<input type="hidden" name="oldEndUnit" value="${RequestParameters['recordDetail.endUnit.id']}" />
	    </td>
	   </tr>
	   <script>
	   		var dutyStatusArray=new Array();
	   </script>
	   <#macro dutyStatusSelectOption dutyStatusList dutyRecordDetail='null'>
	   	<#list dutyStatusList?sort_by("id")?if_exists as dutyStatus>
	   		<option value="${dutyStatus.id}" style="background-color:#FFFFFF" <#if dutyRecordDetail!='null'&&dutyRecordDetail.dutyStatus.id?exists&&dutyRecordDetail.dutyStatus.id==dutyStatus.id>selected</#if>><@i18nName dutyStatus/></option>
	   	</#list>
	   </#macro>
	   <tr align="center" class="darkColumn">
	     <td align="center">
	     <select name="totalDutyStatus"  style="width:60px" onchange="dutyStatusChange()">
	       <option value="" style="background-color:#B0B0B0"></option>
           <@dutyStatusSelectOption dutyStatusList=result.dutyStatusList/><#-->${dutyStatusSelectOption}-->
         </select></td>
	     <td width="45%"><@bean.message key="attr.personName"/></td>
	     <td width="50%"><@bean.message key="attr.stdNo"/></td>
	   </tr>
	   <#assign targetstdId = ","/>
	   <#if studentDutyRecordDetailsList?exists && studentDutyRecordDetailsList?size!=0>
	   <#list (studentDutyRecordDetailsList)?if_exists?sort_by(["dutyRecord","student","code"]) as dutyRecordDetail>
	   <#if dutyRecordDetail_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if dutyRecordDetail_index%2==0 ><#assign class="brightStyle" ></#if>
	   <#assign targetstdId = targetstdId + dutyRecordDetail.dutyRecord.student.id + ","/>
	   <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
	    <td align="center"><#--><input type="checkBox" name="ids" value="${dutyRecordDetail.dutyRecord.student.id}" <#if dutyRecordDetail.dutyStatus==true>checked</#if>>-->
	    	<script>
	   			dutyStatusArray.push("dutyStatus${dutyRecordDetail.dutyRecord.student.id}");
	   		</script>
	    	<select name="dutyStatus${dutyRecordDetail.dutyRecord.student.id}"  style="width:60px" onchange="document.getElementById('totalDutyStatus')[0].selected=true;" >
	    		<option value="" style="background-color:#B0B0B0"></option>
           		<@dutyStatusSelectOption dutyStatusList=result.dutyStatusList dutyRecordDetail=dutyRecordDetail/><#-->${dutyStatusSelectOption}-->
        	</select>
        </td>
	    <td>&nbsp;<@i18nName dutyRecordDetail.dutyRecord.student?if_exists/></td>
	    <td>&nbsp;${dutyRecordDetail.dutyRecord.student.code}</td>
	   </tr>
	   </#list>
	   <tr>
	    <td colspan="3" align="center" class="darkColumn">
	     <input type="hidden" name="courseTypeId" value="${RequestParameters["courseTypeId"]?if_exists}" />
	     <input type="hidden" name="year" value="${RequestParameters['year']?if_exists}" />
	     <input type="hidden" name="term" value="${RequestParameters['term']?if_exists}" />
	     <input type="hidden" name="studentTypeId" value="${RequestParameters["studentTypeId"]?if_exists}" />
	     <input type="hidden" name="studentTypeDescriptions" value="${RequestParameters["studentTypeDescriptions"]?if_exists}" />
	     <input type="hidden" name="departmentId" value="${RequestParameters["departmentId"]?if_exists}" />
	     <input type="hidden" name="departmentDescriptions" value="${RequestParameters["departmentDescriptions"]?if_exists}" />
	     <input type="hidden" name="recordDetail.timeBegin" />
	     <input type="hidden" name="recordDetail.timeEnd" />
	     <input type="hidden" name="targetstdId" />
	     <input type="hidden" name="stdId" value="${targetstdId}" />
	     <input type="hidden" name="from" value="${RequestParameters["from"]?if_exists}"/>
	     <input type="hidden" name="method" value="updateDutyRecordDetails" />
	     <input type="hidden" name="actionName" />
	     <input type="hidden" name="teachTaskId" value="${RequestParameters["teachTaskId"]}" />
	     <input type="button" onClick="search(this.form)" value="<@bean.message key="system.button.submit" />" name="button1" class="buttonStyle" />&nbsp;
	     <input type="button"  onclick="this.form.reset();resetForm();" value="<@bean.message key="system.button.reset" />" name="reset1"  class="buttonStyle" />
        </td>
	   </tr>
	   </#if>
	   </form>
     </table>
    </td>
   </tr>
   </div>
   <div id="chooseDiv" style="display:none">
   <tr>
   <br>
   </tr>   
   <tr>
   <table width="60%" align="center" class="listTable">
   <tr class="darkColumn" align="center">
   <td colspan="2" align="center">
   		<@bean.message key="info.duty.dutyExist"/>
   </td>
   </tr>
   <#-->
   <tr class="brightStyle">
   		<td align="center"><input type="radio" name="chooseId" value="back" checked></td>
	    <td width="90%"><@bean.message key="attr.backPage"/></td>
   </tr>
   <tr class="brightStyle">
   		<td align="center"><input type="radio" name="chooseId" value="history" ></td>
	    <td width="90%"><@bean.message key="action.goback"/></td>
   </tr>
   <tr class="brightStyle">
   		<td align="center"><input type="radio" name="chooseId" value="modify" ></td>
	    <td width="90%"><@bean.message key="action.modify"/></td>
   </tr>  
   -->
   <tr class="darkColumn" align="center">
   <td colspan="2" align="center">
   		<input type="button" onClick="choose()" value="<@bean.message key="system.button.confirm" />" name="chooseButton" class="buttonStyle" />&nbsp;
   </td>
   </tr>
   </table>
   </tr>
   </div>
  </table><br><br>
 </body>
 <script> 
	
	function choose(){
		document.getElementById("commonFormDiv").style.display = "block";
   		document.getElementById("chooseDiv").style.display = "none";
	}
	
	function dutyStatusChange(){
 		var totalDutyStatusValue = document.getElementById("totalDutyStatus").value;
 		if(totalDutyStatusValue==""){totalDutyStatusValue=0;}
 		for(i=0;i<dutyStatusArray.length;i++){
 			document.getElementById(dutyStatusArray[i])[totalDutyStatusValue].selected=true;
 		} 		
 	}
 	
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("ids")));
    }

    function search(form){
        form.targetstdId.value = getIds();
        
        if (form["recordDetail.dutyDate"].value == ""){
            alert("<@bean.message key="info.duty.selectDate"/>");
            return false;
        }
        <#-->
        var location = self.location.href;
        var url = location.split("/");
        var action = url[url.length-1].split("?");
        form.action = action[0];       
        form.actionName.value = action[0];
        form.submit();
        -->
        if((document.getElementById("dutyDate").value!=document.getElementById("oldDutyDate").value)||(document.getElementById("beginUnit").value!=document.getElementById("oldBeginUnit").value)||(document.getElementById("endUnit").value!=document.getElementById("oldEndUnit").value)){
        checkRecordDetail();
        }else{<#-->alert((document.getElementById("dutyDate").value+"="+document.getElementById("oldDutyDate").value)+";"+(document.getElementById("beginUnit").value+"="+document.getElementById("oldBeginUnit").value)+";"+(document.getElementById("endUnit").value+"="+document.getElementById("oldEndUnit").value));-->
        var location = self.location.href;
    	var url = location.split("/");
    	var action = url[url.length-1].split("?");
        document.commonForm.action = action[0];
        document.commonForm.actionName.value = action[0];
        document.commonForm.submit();
        }
    }
    
    var courseUnitMap= new Object();    
    <#list result?if_exists.teachTask?if_exists.calendar?if_exists.timeSetting?if_exists.courseUnits?if_exists?sort_by("index") as courseUnit>    	
    	courseUnitMap[${courseUnit.id}]={'id':'${courseUnit.id}','name':'<@i18nName courseUnit/>','index':${courseUnit.index},'startTime':'<@getTimeStr courseUnit.startTime?if_exists/>','finishTime':'<@getTimeStr courseUnit.finishTime?if_exists/>'};
    </#list>    
    DWRUtil.removeAllOptions('beginUnit');
    DWRUtil.addOptions('beginUnit',courseUnitMap,'id','name');   
    DWRUtil.removeAllOptions('endUnit');
    DWRUtil.addOptions('endUnit',courseUnitMap,'id','name');
    
    <#-->function changeBeginTime(){DWRUtil.setValue('beginTime',courseUnitMap[DWRUtil.getValue('beginUnit')].startTime);var tempFlag=false;for(var key in courseUnitMap){if(courseUnitMap[key].index==courseUnitMap[DWRUtil.getValue('beginUnit')].index+1){DWRUtil.setValue('endUnit',key);tempFlag=true;}else{continue;}}if(!tempFlag){DWRUtil.setValue('endUnit',DWRUtil.getValue('beginUnit'))}changeEndTime();}-->
    function changeBeginTime(){DWRUtil.setValue('beginTime',courseUnitMap[DWRUtil.getValue('beginUnit')].startTime);DWRUtil.setValue('endUnit',DWRUtil.getValue('beginUnit'));changeEndTime();}
    function changeEndTime(){DWRUtil.setValue('endTime',courseUnitMap[DWRUtil.getValue('endUnit')].finishTime);}    
    
    function resetForm(){
    	DWRUtil.setValue('beginUnit',${RequestParameters['recordDetail.beginUnit.id']});    
	    changeBeginTime();
	    DWRUtil.setValue('endUnit',${RequestParameters['recordDetail.endUnit.id']});	
	    changeEndTime();
    }
    
    resetForm();
    
 </script>
<#include "/templates/foot.ftl"/>