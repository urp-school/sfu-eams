<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table cellpadding="0" cellspacing="0" width="100%" border="0">   
   <#if result.student?exists> 
   <tr>
    <td>
     <table width="90%" align="center" class="listTable">
       <form name="listForm" action="" onSubmit="return false;">
	   <tr align="center" class="darkColumn">
	     <td><@bean.message key="attr.taskNo"/></td>
	     <td><@bean.message key="attr.courseNo"/></td>
	     <td><@bean.message key="attr.courseName"/></td>
	     <td><@bean.message key="entity.courseType"/></td>
	     <td>考勤</td>
	     <td>出勤</td>
	     <td>缺勤</td>
	     <td>迟到</td>
	     <td>早退</td>
	     <td>请假</td>
	     <td><@bean.message key="attr.graduate.attendClassRate"/></td>
	     <td>缺勤率</td>
	     <td>修读类别</td>
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
	   <#list (result.recordList?sort_by(["teachTask","course","code"]))?if_exists as record>
	   <#assign withdraw = false />
	   <#if !record.getCourseTakeType(true)?exists><#assign withdraw = true /></#if>
	   <#if record_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if record_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
	    <td>&nbsp;${record.teachTask.seqNo?if_exists}</td>
	    <td>&nbsp;${record.teachTask.course?if_exists.code?if_exists}</td>
	    <td>	    
	     <a href="javascript:popupCommonWindow('dutyRecordManager.do?method=recordUpdateForm&recordId=${record.id}&dutyCount=${record.dutyCount?default(0)}&totalCount=${record.totalCount?default(0)}&teachTaskId=${record.teachTask.id}&stdId=${record.student.id}','recordDetailWin');" >
	      &nbsp;<@i18nName record.teachTask.course?if_exists/>
	     </a>
	    </td>
	    <td>&nbsp;<@i18nName record.teachTask.courseType?if_exists/></td>
	    <td>&nbsp;${record.totalCount?default(0)}<#if !withdraw><#assign totalCount = totalCount + record.totalCount?default(0) /></#if></td>
	    <td>&nbsp;${record.dutyCount?default(0)}<#if !withdraw><#assign totalDuty = totalDuty + record.dutyCount?default(0) /></#if></td>
	    <td>&nbsp;${record.absenteeismCount?default(0)}<#if !withdraw><#assign totalAbsenteeism = totalAbsenteeism + record.absenteeismCount?default(0) /></#if></td>
	    <td>&nbsp;${record.lateCount?default(0)}<#if !withdraw><#assign totalLateCount = totalLateCount + record.lateCount?default(0) /></#if></td>
	    <td>&nbsp;${record.leaveEarlyCount?default(0)}<#if !withdraw><#assign totalLeaveEarlyCount = totalLeaveEarlyCount + record.leaveEarlyCount?default(0) /></#if></td>
	    <td>&nbsp;${record.askedForLeaveCount?default(0)}<#if !withdraw><#assign totalAskedForLeaveCount = totalAskedForLeaveCount + record.askedForLeaveCount?default(0) /></#if></td>
	    <td>&nbsp;${record.dutyRatio?default(0)?string.percent}</td>
	    <td>&nbsp;${record.absenteeismRatio?default(0)?string.percent}</td>
	    <td>&nbsp;<@i18nName record.getCourseTakeType(false) /></td>
	    <#if !withdraw>
	    <#assign totalDutyCount = totalDutyCount + record.getDutyCount(true)?default(0) />
	    <#assign totalAbsenteeismCount = totalAbsenteeismCount + record.getAbsenteeismCount(true)?default(0) />
	    </#if>
	   </tr>
	   </#list>
	   <tr class="darkColumn" >
	    <#if totalDuty = 0 ><#assign totalRatio = 0 /><#assign totalAbsenteeismRatio = 0 /><#else><#assign totalRatio = totalDutyCount?default(0)/totalCount?default(0) /><#assign totalAbsenteeismRatio = totalAbsenteeismCount/totalCount?default(0) /></#if>
	    <td colspan="4" align="center">合计(退课课程考勤不计入合计)</td>
	    <td>&nbsp;${totalCount?default(0)}</td>
	    <td>&nbsp;${totalDuty?default(0)}</td>
	    <td>&nbsp;${totalAbsenteeism?default(0)}</td>
	    <td>&nbsp;${totalLateCount?default(0)}</td>
	    <td>&nbsp;${totalLeaveEarlyCount?default(0)}</td>
	    <td>&nbsp;${totalAskedForLeaveCount?default(0)}</td>
	    <td>&nbsp;${totalRatio?string.percent}</td>
	    <td>&nbsp;${totalAbsenteeismRatio?string.percent}</td>
	    <td>&nbsp;</td>
	   </tr>
	   </form>
     </table>
    </td>
   </tr>
   </#if>
  </table>
 </body>
<#include "/templates/foot.ftl"/>