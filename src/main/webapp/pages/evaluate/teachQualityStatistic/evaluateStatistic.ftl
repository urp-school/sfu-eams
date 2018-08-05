<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
	<table align="center" width="100%" class="listTable">
		<tr class="darkColumn">
			<td align="center" rowspan="2">部门</td>
			<td align="center" rowspan="2">年度</td>
			<td align="center" colspan="2">优秀</td>
			<td align="center" colspan="2">良好</td>
			<td align="center" colspan="2">中等</td>
			<td align="center" colspan="2">及格</td>
		</tr>
		<tr class="darkColumn">
			<#list 1..4 as i>
				<td align="center">人次</td>
				<td align="center">比例</td>
			</#list>
		</tr>
		<#list result.teachQualityList as teachQuality>
		<#if teachQuality_index%2==1 ><#assign class="grayStyle" ></#if>
	   	<#if teachQuality_index%2==0 ><#assign class="brightStyle" ></#if>
		<tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
		   <#list teachQuality?if_exists as data>
		   <td align="center">
		   		<#if data_index==0>
		   			${data?if_exists}
		   		<#elseif data_index==1>
		   			${data?if_exists}
		   		<#elseif data_index!=0&&data_index%2==0>
		   			${data?if_exists}
		   		<#else>
		   			${data?if_exists?string("####0.0")}%
		   		</#if>
		   	</td>
		   </#list>
		   
		</tr>
		</#list>
		<tr class="darkColumn">
			<td colspan="4" height="20px;">
			</td>
		</tr>
	</table>
</body>
<#include "/templates/foot.ftl"/>