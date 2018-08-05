<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
	<h1 align="center">优秀教材各级别分布</h1>
	<table align="center" width="100%" class="listTable">
		<tr class="darkColumn">
			<td align="center" >级别</td>
			<td align="center" >一等奖</td>
			<td align="center" >二等奖</td>
			<td align="center" >三等奖</td>
		</tr>
		<#list result.teachQualityList as teachQuality>
		<#if teachQuality_index%2==1 ><#assign class="grayStyle" ></#if>
	   	<#if teachQuality_index%2==0 ><#assign class="brightStyle" ></#if>
		<tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
		   <#list teachQuality as data>
		   <td align="center">
		   		${data?if_exists}
		   	</td>
		   </#list>
		   
		</tr>
		</#list>
		<tr class="darkColumn">
			<td colspan="11" height="20px;">
			</td>
		</tr>
	</table>
</body>
<#include "/templates/foot.ftl"/>