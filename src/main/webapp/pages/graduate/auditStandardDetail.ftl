<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
 <body>
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <tr>
    <td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B><@bean.message key="attr.graduate.lookGraduateAuditStandard"/></B>
    </td>
   </tr>  
   <#if result.auditStandard?exists> 
   <tr>
    <td>
     <table width="80%" align="center" class="listTable">
	   <tr class="darkColumn">
	     <td align="center" colspan="2"><@bean.message key="attr.graduate.graduateAuditStandardDetailInfo"/></td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%">&nbsp;<@bean.message key="attr.graduate.auditType"/></td>
	     <td class="brightStyle">&nbsp;<@i18nName result.auditStandard.auditType?if_exists/></td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%">&nbsp;<@bean.message key="entity.college"/></td>
	     <td class="brightStyle">&nbsp;<@i18nName result.auditStandard.department?if_exists/></td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%">&nbsp;<@bean.message key="entity.speciality"/></td>
	     <td class="brightStyle">
	      <#if result.auditStandard.speciality?exists>
	       &nbsp;<@i18nName result.auditStandard.speciality?if_exists/>
	      </#if>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%">&nbsp;<@bean.message key="entity.specialityAspect"/></td>
	     <td class="brightStyle">&nbsp;
	      <#if result.auditStandard.specialityAspect?exists>
	       &nbsp;<@i18nName result.auditStandard.specialityAspect?if_exists/>
	      </#if>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%">&nbsp;<@bean.message key="entity.studentType"/></td>
	     <td class="brightStyle">&nbsp;<@i18nName result.auditStandard.studentType?if_exists/></td>
	   </tr>
       <tr>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="filed.averageScoreNod"/></td>
	    <td class="brightStyle">
	    <#if result.auditStandard.averageScore?exists>
	    &nbsp;${result.auditStandard.averageScore?string.number}
	    </#if>
	    </td>
	   </tr>
	   <tr>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="entity.cultivatePlan"/></td>
	    <td class="brightStyle">&nbsp;<#assign isTeachPlanCompleted=(result.auditStandard.isTeachPlanCompleted?if_exists?string)><#if isTeachPlanCompleted=="true"><@bean.message key="attr.graduate.finishRequired"/><#else><@bean.message key="attr.graduate.finishNoRequired"/></#if></td>
	   </tr>
	   <tr>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="attr.graduate.discourseScore"/></td>
	    <td class="brightStyle">&nbsp;${(result.auditStandard.discourseScore?string.number)?if_exists}</td>
	   </tr>
	   <tr>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="attr.graduate.integrativeScore"/></td>
	    <td class="brightStyle">&nbsp;${(result.auditStandard.integrationScore?string.number)?if_exists}</td>
	   </tr>  
	   <tr>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="attr.graduate.attendClassRate"/></td>
	    <td class="brightStyle">&nbsp;${(result.auditStandard.dutyRation?string.number)?if_exists}％</td>
	   </tr>
	   <tr>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="attr.graduate.totleCreditReStudy"/></td>
	    <td class="brightStyle">&nbsp;${(result.auditStandard.repeatCreditHour?string.number)?if_exists}</td>
	   </tr>
	   <tr>
	    <td class="grayStyle" width="25%" id="f_publishedDiscourseCount">&nbsp;<@bean.message key="attr.graduate.articleNumber"/></td>
	    <td class="brightStyle">&nbsp;${(result.auditStandard.publishedDiscourseCount?string.number)?if_exists}</td>
	   </tr>
	   <tr>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="info.studentRecordManager.studentPunishmentType"/></td>
	    <td class="brightStyle">
	      <#if result.auditStandard.punishmentType?exists>
	       &nbsp;<@i18nName result.auditStandard.punishmentType?if_exists/>
	      </#if>
	    </td>
	   </tr>
	   <tr>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="attr.remark"/></td>
	    <td class="brightStyle">&nbsp;${result.auditStandard.remark?if_exists}</td>
	   </tr>
     </table>
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
 </body>
<#include "/templates/foot.ftl"/>