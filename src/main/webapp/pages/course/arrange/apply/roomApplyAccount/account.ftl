<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
	<table>
		<tr style="height:29px">
			<td><font color="red"><#if RequestParameters['year']?exists>${RequestParameters['year']}<#else>${year}</#if></font>年借用次数合计<font color="red">${usingCounts}</font>次，分类统计结果如下：</td>
		</tr>
	</table>
	<br>
	<table width="99%" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr class="tdhead">
			<td><li>按星期统计教室借用率</td>
		</tr>
		<tr>
			<td>
				<#include "weeksAccount.ftl"/>
			</td>
		</tr>
	<table>
	<br>
	<table width="99%" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr class="tdhead">
			<td><li>按地点统计教室借用率</td>
		</tr>
		<tr>
			<td>
				<#include "placesAccount.ftl"/>
			</td>
		</tr>
	<table>
	<br>
	<table width="99%" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr class="tdhead">
			<td><li>按活动类型统计教室借用率</td>
		</tr>
		<tr>
			<td>
				<#include "activitiesAccount.ftl"/>
			</td>
		</tr>
	<table>
	<br><br>
</body>
<#include "/templates/foot.ftl"/>