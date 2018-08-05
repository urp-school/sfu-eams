<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
       <table class="listTable" align="center" width="100%" align="center" >
		<tr class="darkColumn">
			<td  align="center">部门</td>
			<td align="center">学年度</td>
			<td align="center">总工作量</td>
			<td align="center">平均工作量</td>
		</tr>
		<#list result.teachQualityList as teachQuality>
		<#if teachQuality_index%2==1 ><#assign class="grayStyle" ></#if>
	   	<#if teachQuality_index%2==0 ><#assign class="brightStyle" ></#if>
		<tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
		   <#list teachQuality as data>
		   <td align="center">
		   	<#if data_index==0>
		   		${data?if_exists}
		   	<#elseif data_index==1>
		   		${data?if_exists}
		   	<#else>
		   		${data?if_exists?string("###0.0")}
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