<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
 <body>
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <#if result.auditResult?exists> 
   <tr>
    <td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B><@bean.message key="attr.graduate.lookGraduateAuditStandard"/></B>
    </td>
   </tr>  
   <tr>
    <td>
     <table width="80%" align="center" class="listTable">
	   <tr class="darkColumn">
	     <td align="center" colspan="2"><@bean.message key="attr.graduate.auditResult"/></td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%">&nbsp;<@bean.message key="attr.studentName"/></td>
	     <td class="brightStyle">&nbsp;<@i18nName result.auditResult?if_exists/></td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%">&nbsp;<@bean.message key="attr.graduate.auditType"/></td>
	     <td class="brightStyle">&nbsp;<@i18nName result.auditResult.auditStandard.auditType?if_exists/></td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%">&nbsp;<@bean.message key="entity.college"/></td>
	     <td class="brightStyle">&nbsp;<@i18nName result.auditResult.auditStandard.department?if_exists/></td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%">&nbsp;<@bean.message key="entity.speciality"/></td>
	     <td class="brightStyle">
	      &nbsp;<@i18nName result.auditResult.student?if_exists.firstMajor?if_exists/>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%">&nbsp;<@bean.message key="entity.specialityAspect"/></td>
	     <td class="brightStyle">
	      &nbsp;<@i18nName result.auditResult.student?if_exists.firstAspect?if_exists/>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%">&nbsp;<@bean.message key="entity.studentType"/></td>
	     <td class="brightStyle">&nbsp;<@i18nName result.auditResult.student?if_exists.type?if_exists/></td>
	   </tr>
       <tr>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="filed.averageScoreNod"/></td>
	    <td class="brightStyle">
	    <#if result.auditResult.auditStandard.averageScore?exists>
	    	<#assign requiredAverageScore = result.auditResult.auditStandard.averageScore />
	    <#else>
	    	<#assign requiredAverageScore = -1 />
	    </#if>
	    <#if result.auditResult.averageScore?exists>
	    	<#assign averageScore = result.auditResult.averageScore />
	    <#else>
	    	<#assign averageScore = 0 />
	    </#if>
	    <#if (averageScore<requiredAverageScore) >
	    	<font color="red">&nbsp;${averageScore?string.number}</font>
	    <#else>
	    	&nbsp;${averageScore?string.number}
	    </#if>
	    </td>
	   </tr>
	   <tr>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="entity.cultivatePlan"/></td>
	    <td class="brightStyle">&nbsp;<#if result.auditResult.isTeachPlanCompleted==true><@bean.message key="attr.graduate.finish"/><#else><font color="red"><@bean.message key="attr.graduate.noFinish"/></font></#if></td>
	   </tr>
	   <#if result.auditResult.isSecondSchemeCompleted?exists>
	   <tr>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="entity.secondSpeciality"/><@bean.message key="entity.cultivatePlan"/></td>
	    <td class="brightStyle">&nbsp;<#if result.auditResult.isTeachPlanCompleted==true><@bean.message key="attr.graduate.finish"/><#else><font color="red"><@bean.message key="attr.graduate.noFinish"/></font></#if></td>
	   </tr>
	   </#if>
	   <tr>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="attr.graduate.discourseScore"/></td>
	    <td class="brightStyle">&nbsp;${(result.auditResult.discourseScore?string.number)?if_exists}</td>
	   </tr>
	   <tr>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="attr.graduate.integrativeScore"/></td>
	    <td class="brightStyle">&nbsp;${(result.auditResult.integrationScore?string.number)?if_exists}</td>
	   </tr>  
	   <tr>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="attr.graduate.attendClassRate"/></td>
	    <td class="brightStyle">
	    <#if result.auditResult.auditStandard.dutyRation?exists>
	    	<#assign requiredDutyRation = result.auditResult.auditStandard.dutyRation />
	    <#else>
	    	<#assign requiredDutyRation = -1 />
	    </#if>
	    <#if result.auditResult.dutyRation?exists>
	    	<#assign dutyRation = result.auditResult.dutyRation />
	    <#else>
	    	<#assign dutyRation = 0 />
	    </#if>
	    <#if (dutyRation<requiredDutyRation) >
	    	<font color="red">&nbsp;${dutyRation?string.number}％</font>
	    <#else>
	    	&nbsp;${dutyRation?string.number}％
	    </#if>
	    </td>
	   </tr>
	   <tr>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="attr.graduate.totleCreditReStudy"/></td>
	    <td class="brightStyle">&nbsp;${(result.auditResult.repeatCreditHour?string.number)?if_exists}</td>
	   </tr>
	   <tr>
	    <td class="grayStyle" width="25%" id="f_publishedDiscourseCount">&nbsp;<@bean.message key="attr.graduate.articleNumber"/></td>
	    <td class="brightStyle">&nbsp;${(result.auditResult.publishedDiscourseCount?string.number)?if_exists}</td>
	   </tr>
	   <tr>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="info.studentRecordManager.studentPunishmentType"/></td>
	    <td class="brightStyle">
	      <#if result.auditResult.auditStandard.punishmentType?exists&&result.auditResult.auditStandard.punishmentType.code?exists>
	      <#assign punishmentTypeLimit = result.auditResult.auditStandard.punishmentType.code?number/>
	      <#else>
	      <#assign punishmentTypeLimit = -1/>
	      </#if>
	      <#list result.auditResult.punishmentSet?if_exists as punishmentRecord>
	       <#if (punishmentRecord.recordType.code?number < punishmentTypeLimit) >
	       &nbsp;<@i18nName punishmentRecord.recordType/>
	       <#else>
	       &nbsp;<font color="red"><@i18nName punishmentRecord.recordType/></font>
	       </#if>
	      </#list>
	    </td>
	   </tr>
	   <tr>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="attr.remark"/></td>
	    <td class="brightStyle">&nbsp;${result.auditResult.remark?if_exists}</td>
	   </tr>
    <tr>
	 <td class="grayStyle" width="25%" id="f_status">&nbsp;<@bean.message key="attr.graduate.isPass"/></td>
	 <td class="brightStyle">
	    <#if result.auditResult.status?exists>
		    <#if result.auditResult.status?string=="true">
		    	<#assign auditResultStatus="true">
		    <#elseif result.auditResult.status?string=="false">
		    	<#assign auditResultStatus="false">
		    <#else>
		    	<#assign auditResultStatus="null">
		    </#if>
		<#else>
			<#assign auditResultStatus="null">
	    </#if>
         <#if auditResultStatus=="true">&nbsp;<@bean.message key="attr.graduate.pass"/></#if>
	     <#if auditResultStatus=="false">&nbsp;<font color="red"><@bean.message key="attr.graduate.noPass"/></font></#if>
        </td>
	 </tr>
	 <#else>
   <tr>
    <td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <br>
     <B><font color="red"><@bean.message key="attr.graduate.outsideExam.findNoYourAudit"/></font></B>
    </td>
   </tr>
   </#if>
	 </table>
    </td>
   </tr>
  </table>
 </body>
<#include "/templates/foot.ftl"/>