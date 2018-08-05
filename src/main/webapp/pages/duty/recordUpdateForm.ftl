<#include "/templates/head.ftl"/>
<#include "/pages/system/calendar/timeFunction.ftl"/>
<#macro dutyStatusSelectOption dutyStatusList dutyRecordDetail='null'>
	<#list dutyStatusList?sort_by("id")?if_exists as dutyStatus>
		<option value="${dutyStatus.id}" style="background-color:#FFFFFF" <#if dutyRecordDetail!='null'&&dutyRecordDetail.dutyStatus.id?exists&&dutyRecordDetail.dutyStatus.id==dutyStatus.id>selected</#if>><@i18nName dutyStatus/></option>
   	</#list>
</#macro>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<body leftmargin="0" topmargin="0" style="overflow:auto;">
<script>
	<#if (result.recordDetailList?size)< 8 >
		window.self.resizeTo(400, ${(result.recordDetailList?size)*30+150});
		window.self.moveTo((screen.width-400)/3, (screen.height-${(result.recordDetailList?size)*30+150})/5);
	<#else>
		window.self.resizeTo(400, 8*30+150);
		window.self.moveTo((screen.width-400)/3, (screen.height-(8*30+150))/5);
	</#if>
</script>
<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
	<tr> 
		<td height="100%" valign="top" background="images/loginForm/ifr_mainBg_0.gif">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td background="images/loginForm/leftItem_001.gif" style="background-repeat:no-repeat ">              
       			<table width="100%" border="0" cellpadding="0" cellspacing="0">
        		<tr> 
		         	<td width="15%" height="42">&nbsp;</td>
		         	<td width="85%"><font color="red"><B><@i18nName result.dutyRecord.teachTask.course?if_exists/>&nbsp;<@bean.message key="info.duty.editDutyRecord"/></B></font></td>
        		</tr>
       			</table>
      			</td>
     		</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr> 
				<td height="100%" valign="top">       
				<table width="85%" align="center" class="listTable">
		        <form name="commonForm" action="dutyRecordManager.do" method="post" target="main" onsubmit="return false;">
				<tr align="center" class="darkColumn">
					<td width="25%"><@bean.message key="info.duty.dutyDate"/></td>
					<td width="20%"><@bean.message key="info.duty.timeBeginHour"/></td>
			     	<td width="20%"><@bean.message key="info.duty.timeEndHour"/></td>
			     	<td width="35%"><@bean.message key="info.duty.dutyStatus"/></td>
				</tr>
			    <#assign recordDetailIds = "," />
		        <#list (result.recordDetailList?sort_by("dutyDate"))?if_exists as recordDetail>
			        <#if recordDetail_index%2==1 ><#assign class="grayStyle" ></#if>
				    <#if recordDetail_index%2==0 ><#assign class="brightStyle" ></#if>
			    	<tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
			     		<td><#if recordDetail.dutyDate?exists>${recordDetail.dutyDate?string("yyyy-MM-dd")}</#if></td>
			     		<td><@getTimeStr recordDetail.beginTime?if_exists/></td>
				     	<td><@getTimeStr recordDetail.endTime?if_exists/></td>
				     	<td>	     
				      		<#assign recordDetailIds = recordDetailIds + recordDetail.id + "," />
				      		<select name="dutyStatus${recordDetail.id}"  style="width:60px"  >
				      			<option value="" style="background-color:#FFFFFF"></option>
			           			<@dutyStatusSelectOption dutyStatusList=result.dutyStatusList dutyRecordDetail=recordDetail/><#-->${dutyStatusSelectOption}-->
			          		</select>
			     		</td>
		        	</tr>
		        </#list>
		        <tr>
		         	<td colspan="4" align="center" class="darkColumn"> 
		           		<input type="hidden" name="method" value="updateRecord" />
			           	<input type="hidden" name="recordDetailIds" value="${recordDetailIds}" />
			           	<input type="hidden" name="teachTaskId" value="${RequestParameters["teachTaskId"]}" />
			           	<input type="hidden" name="recordId" value="${RequestParameters["recordId"]}"/>
			           	<input type="hidden" name="totalCount" value="${RequestParameters["totalCount"]}"/>
			           	<input type="hidden" name="stdId" value="${RequestParameters["stdId"]}"/>
			           	<input type="hidden" name="actionName" />
			           	<input type="hidden" name="from" value="${from?if_exists}"/>
			           	<input type="button" value="<@bean.message key="system.button.submit" />" name="button1" class="buttonStyle" onClick="updateRecord();"  />&nbsp;
			       		<input type="reset" value="<@bean.message key="system.button.reset" />" name="reset1"  class="buttonStyle" />
		         	</td>
		        </tr>
		        </form>
				</table>
		      	</td>
		     </tr>          
		</table>
   		</td>
  	</tr>
</table>
</body>
<script>
   function updateRecord(){
        var action = getAction(self.location.href);
        commonForm.action = action;
        commonForm.actionName.value=action;
        if(action=="dutyRecordManagerWithTeacher.do"){commonForm.target="teachTaskListFrame";}
        commonForm.submit();
        window.close();
    }
    function getAction(location){
    	var actionAll = location.split("/");
    	var action = actionAll[actionAll.length-1].split("?");
    	return action[0];
    }
</script>
<#include "/templates/foot.ftl"/>