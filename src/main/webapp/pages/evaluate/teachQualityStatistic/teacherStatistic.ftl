<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
	<h1 align="center">各部门教师分布情况</h1>
	<table align="center" width="100%" class="listTable">
		<tr class="darkColumn">
			<td  align="center">部门</td>
			<td align="center">教授</td>
			<td align="center">副教授</td>
			<td align="center">讲师</td>
		</tr>
		<#list result.studentOrTeacherList as student>
		<#if student_index%2==1 ><#assign class="grayStyle" ></#if>
	   	<#if student_index%2==0 ><#assign class="brightStyle" ></#if>
		<tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
		   
		   <#list student as data>
		   <td align="center">
		   		${data?if_exists}
		   	</td>
		   </#list>
		   
		</tr>
		</#list>
	</table>
</body>
<#include "/templates/foot.ftl"/>