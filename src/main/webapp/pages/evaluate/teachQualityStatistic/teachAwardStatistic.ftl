<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
	<h1 align="center">教学成果各个部门各个层次分布</h1>
	<table align="center" width="100%" class="listTable">
		<tr class="darkColumn">
			<td align="center" rowspan="2">部门</td>
			<td align="center" colspan="3">国家级</td>
			<td align="center" colspan="3">市级</td>
			<td align="center" colspan="3">校级</td>
		</tr>
		<tr class="darkColumn">
			<#list 1..3 as i>
				<td align="center">一等奖</td>
				<td align="center">二等奖</td>
				<td align="center">三等奖</td>
			</#list>
		</tr>
			<#assign x1=0>
			<#assign x2=0>
			<#assign x3=0>
			<#assign x4=0>
			<#assign x5=0>
			<#assign x6=0>
			<#assign x7=0>
			<#assign x8=0>
			<#assign x9=0>
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
		   		<#assign x1=x1+data>
		   		<#elseif data_index==2>
		   		${data?if_exists}
		   		<#assign x2=x2+data>
		   		<#elseif data_index==3>
		   		${data?if_exists}
		   		<#assign x3=x3+data>
		   		<#elseif data_index==4>
		   		${data?if_exists}
		   		<#assign x4=x4+data>
		   		<#elseif data_index==5>
		   		${data?if_exists}
		   		<#assign x5=x5+data>
		   		<#elseif data_index==6>
		   		${data?if_exists}
		   		<#assign x6=x6+data>
		   		<#elseif data_index==7>
		   		${data?if_exists}
		   		<#assign x7=x7+data>
		   		<#elseif data_index==8>
		   		${data?if_exists}
		   		<#assign x8=x8+data>
		   		<#elseif data_index==9>
		   		${data?if_exists}
		   		<#assign x9=x9+data>
		   		</#if>
		   	</td>
		   </#list>
		   
		</tr>
		</#list>
		<tr class="grayStyle">
			<td align="center">合计</td>
			<td align="center">${x1}</td>
			<td align="center">${x2}</td>
			<td align="center">${x3}</td>
			<td align="center">${x4}</td>
			<td align="center">${x5}</td>
			<td align="center">${x6}</td>
			<td align="center">${x7}</td>
			<td align="center">${x8}</td>
			<td align="center">${x9}</td>
		</tr>
	    <tr class="darkColumn">
			<td colspan="11" height="20px;">
			</td>
		</tr>
	</table>
</body>
<#include "/templates/foot.ftl"/>