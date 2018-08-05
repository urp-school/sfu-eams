<#include "/templates/head.ftl"/>
<BODY >
<table  width="100%" height="20" border="0" cellpadding="0" cellspacing="0">
  <tr> 
    <td align="center">     
       	<#assign stdTypeName><@i18nName student.type/></#assign>
       	<#assign schoolingLength><#if student.schoolingLength?exists>${student.schoolingLength}<#else><@msg.message key="common.noSetting"/></#if></#assign>
      <span class="contentTableTitleTextStyle">
       	<font color="red"><@bean.message key="thesis.noRecordTip" arg0=stdTypeName arg1=student.enrollYear arg2=schoolingLength/></font>
      </span> 
    </td>
  </tr>
</table>
</body>
<#include "/templates/foot.ftl"/>