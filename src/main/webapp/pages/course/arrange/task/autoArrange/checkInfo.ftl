<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<#assign labInfo><font color="red"><@bean.message key="error.arrange.validate"/></font></#assign>  
<#include "/templates/back.ftl"/>
<#list result.arrangeValidateMessages?keys as keyProperty>
  <table width="100%" border="0" class="listTable" align="center">
    <tr>
    <td class="darkColumn" colspan="4"><@bean.message key="${keyProperty}" /></td>
    </tr>
    <tr align="center" class="darkColumn">
      <td width="5%"><@bean.message key="attr.taskNo" /></td>
      <td width="10%"><@bean.message key="attr.infoname" /></td>
      <td width="20%"><@bean.message key="attr.failureInfo" /></td>
      <td width="10%"><@bean.message key="attr.failureValue" /></td>
    </tr>
    <#list result.arrangeValidateMessages[keyProperty] as message>
   	  <#if message_index%2==1 ><#assign class="grayStyle" ></#if>
	  <#if message_index%2==0 ><#assign class="brightStyle" ></#if>
     <tr class="${class}" align="center" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)" >
      <td>&nbsp;${message.id}</td>
      <td>&nbsp;${message.info?if_exists}</td>
      <td>&nbsp;<@bean.message key="${message.key}" /></td>
      <td>&nbsp;${message.message?if_exists}</td>
     </tr>
	</#list>
  </table>
</#list>
</body>
<#include "/templates/foot.ftl"/>  
