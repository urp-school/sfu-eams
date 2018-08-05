<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
  	<@table.table width="100%" >
  		<@table.thead>
  			<td><b>是否计工作量</b></td>
  			<td><b>是否支付报酬</b></td>
  			<td><b>总工作量</b></td>
   		</@>
   		<@table.tbody datas=workloadDepartStats;loadStat>
   			    <td align="center">${loadStat[0]?string("是","否")}</td>
   				<td align="center">${loadStat[1]?string("是","否")}</td>
   				<td align="center">${loadStat[2]}</td>
  		</@>
  	</@>
 </body>
 <#include "/templates/foot.ftl"/>