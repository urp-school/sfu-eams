<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
   	<@table.table width="100%" sortable="true" id="listTable">
  		<@table.thead>
  			<@table.sortTd text="登录名" id="onlineRecord.name"/>
  			<@table.sortTd text="姓名" id="onlineRecord.userName"/>
  			<@table.sortTd text="登录次数" id="count(onlineRecord.name)"/>
  		</@>
   		<#assign total=0>
   		<@table.tbody datas=loginCountStats;logonStat>
   			<td>${logonStat[0]}</td>
   			<td>${logonStat[1]}</td>
   			<td><#if logonStat[2]!=0><A href="onlineRecord.do?method=search&onlineRecord.name=${logonStat[0]}&startTime=${RequestParameters['startTime']}&endTime=${RequestParameters['endTime']}" >${logonStat[2]}</A></#if></td>
   			<#assign total=total+logonStat[2]>
   		</@>
  	</@>
 </body>
 <#include "/templates/foot.ftl"/>