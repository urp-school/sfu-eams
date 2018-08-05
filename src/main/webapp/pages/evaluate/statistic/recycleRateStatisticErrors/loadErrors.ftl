<#include "/templates/head.ftl"/>
<BODY topmargin=0 leftmargin=0>							
	<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
	    <tr >
	    <td align="center">
	    <#if studentTypeList?exists>
	    	<@bean.message key="field.questionnaireStatistic.errorStudentType"/>：
	    <font color="red" size="5">
	    <#list studentTypeList?if_exists as errorsname>
	       	<#if errorsname_index==studentTypeList?size-1>${errorsname}<#else>${errorsname},</#if>
	      </#list>
	     </#if> 
	     </font>  
	      <span class="contentTableTitleTextStyle">
	       <font color="red"><@html.errors /></font>
	      </span><br><br>
	      [<a href="javascript:history.back();"><@bean.message key="field.questionnarieStatistic.backHistoryPage"/></a>]     
	    </td>
	    <tr>
	  </table>
</body>
<#include "/templates/foot.ftl"/>