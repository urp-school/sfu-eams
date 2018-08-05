<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body leftmargin="0" topmargin="0" >
<table align="center" cellpadding="0" cellspacing="0" width="100%" border="0">
	<tr>
		<td><br>
			<table width="90%" align="center" class="listTable">
		   	<tr align="center" class="darkColumn">
		   	 	<td><@bean.message key="attr.taskNo"/></td>
		     	<td><@bean.message key="attr.courseNo"/></td>
		     	<td><@bean.message key="attr.courseName"/></td>
		     	<td><@bean.message key="entity.courseType"/></td>
		     	<td><@msg.message key="attendance.total"/></td>
		     	<td><@msg.message key="attendance.presence"/></td>
		     	<td><@msg.message key="attendance.absent"/></td>
		     	<td><@msg.message key="attendance.beLateFor"/></td>
		     	<td><@msg.message key="attendance.leaveEarly"/></td>
		     	<td><@msg.message key="attendance.leave"/></td>
		     	<td><@bean.message key="attendance.presenceRate"/></td>
		     	<td><@msg.message key="attendance.absentRate"/></td>
		   	</tr>
		   	<#assign totalRatio = 0 />
		   	<#assign totalAbsenteeismRatio = 0 />
		   	<#assign totalCount = 0 />
		   	<#assign totalDuty = 0 />
		   	<#assign totalAbsenteeism = 0 />
		   	<#assign totalLateCount = 0 />
		   	<#assign totalLeaveEarlyCount = 0 />
		   	<#assign totalAskedForLeaveCount = 0 />
		   	<#assign totalDutyCount = 0 />
		   	<#assign totalAbsenteeismCount = 0 />
		   	<#assign listIndex = 0 />
	   		<#list (result.recordList?sort_by("dutyRatio"))?if_exists as record>
	   		<#assign disCountType = ",-1,3,4," />
	   		<#if !record.isCourseTakeTypeIn(disCountType)>
		   		<#if listIndex%2==1 ><#assign class="grayStyle" ></#if>
		   		<#if listIndex%2==0 ><#assign class="brightStyle" ></#if>
	   			<tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
				    <td>&nbsp;${ record.teachTask.seqNo?if_exists}</td>
				    <td>&nbsp;${ record.teachTask.course.code?if_exists}</td>
				    <td>
				     	<a href="javascript:popupCommonWindow('studentDutyRecord.do?method=recordDetail&dutyRecordId=${record.id}','recordDetailWin', 350, 200);" title="<@msg.message key="attendance.toViewDetailTip"/>">&nbsp;<@i18nName (record.teachTask.course)?if_exists/></a>
				    </td>
				    <td>&nbsp;<@i18nName (record.teachTask.courseType)?if_exists/></td>	    
				    <td>&nbsp;${record.totalCount?default(0)}<#assign totalCount = totalCount + record.totalCount?default(0) /></td>
				    <td>&nbsp;${record.dutyCount?default(0)}<#assign totalDuty = totalDuty + record.dutyCount?default(0) /></td>
				    <td>&nbsp;${record.absenteeismCount?default(0)}<#assign totalAbsenteeism = totalAbsenteeism + record.absenteeismCount?default(0) /></td>
				    <td>&nbsp;${record.lateCount?default(0)}<#assign totalLateCount = totalLateCount + record.lateCount?default(0) /></td>
				    <td>&nbsp;${record.leaveEarlyCount?default(0)}<#assign totalLeaveEarlyCount = totalLeaveEarlyCount + record.leaveEarlyCount?default(0) /></td>
				    <td>&nbsp;${record.askedForLeaveCount?default(0)}<#assign totalAskedForLeaveCount = totalAskedForLeaveCount + record.askedForLeaveCount?default(0) /></td>
				    <td>&nbsp;${record.dutyRatio?default(0)?string.percent}</td>
				    <td>&nbsp;${record.absenteeismRatio?default(0)?string.percent}</td>
				    <#assign totalDutyCount = totalDutyCount + record.getDutyCount(true)?default(0) />
				    <#assign totalAbsenteeismCount = totalAbsenteeismCount + record.getAbsenteeismCount(true)?default(0) />
	   			</tr>
	   			<#assign listIndex = listIndex + 1 />
	   		</#if>	   
	   		</#list>
	   		<#if result.recordList?exists && result.recordList?size!=0>
	   			<tr class="darkColumn" >
	    			<#if totalDuty = 0 ><#assign totalRatio = 0 /><#assign totalAbsenteeismRatio = 0 /><#else><#assign totalRatio = totalDutyCount?default(0)/totalCount?default(0) /><#assign totalAbsenteeismRatio = totalAbsenteeismCount/totalCount?default(0) /></#if>
				    <td colspan="4" align="center"><@msg.message key="attendance.sum"/></td>
				    <td>&nbsp;${totalCount?default(0)}</td>
				    <td>&nbsp;${totalDuty?default(0)}</td>
				    <td>&nbsp;${totalAbsenteeism?default(0)}</td>
				    <td>&nbsp;${totalLateCount?default(0)}</td>
				    <td>&nbsp;${totalLeaveEarlyCount?default(0)}</td>
				    <td>&nbsp;${totalAskedForLeaveCount?default(0)}</td>
				    <td>&nbsp;${totalRatio?string.percent}</td>
				    <td>&nbsp;${totalAbsenteeismRatio?string.percent}</td>
	   			</tr>
	   		</#if>
     		</table>
    	</td>
   	</tr>
</table> 
</body>
<#include "/templates/foot.ftl"/>