<#include "/templates/head.ftl"/>
 <body >
 <table id="gpBar"></table>
 <script>
  var bar = new ToolBar("gpBar","统计结果",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("<@msg.message key="action.print"/>","print()");
 </script>
 <#assign entityNames={'courseType':'课程类别名称','teachDepart':'开课院系名称'}>
 <@table.table id="statTable" sortable="true" width="100%">
 <@table.thead>
      <@table.sortTd width="20%" id="what.name" text=entityNames[RequestParameters['kind']]/>
      <td width="10%">总任务数</td>
      <@table.sortTd width="10%" id="countors[0]" text="确认数目"/>
      <@table.sortTd width="10%" id="countors[1]" text="未确认数目"/>
      <td width="10%">确认比</td>
  </@>
  <#assign sum0 = 0/>
  <#assign sum1 = 0/>
  <@table.tbody datas=stats;statItem>
      <td>${statItem.what.name}</td>
      <td>${statItem.countors[0]+statItem.countors[1]}</td>
      <td><#assign sum0 = sum0 + statItem.countors[0]?default(0)/>${statItem.countors[0]}</td>
      <td><#assign sum1 = sum1 + statItem.countors[1]?default(0)/>${statItem.countors[1]}</td>
      <td>${((statItem.countors[0]/(statItem.countors[0]+statItem.countors[1]))*100)?string("##.##")}%</td>
  </@>
  <tr class="darkColumn" style="text-align:center; font-weight: bold">
  	<td>汇总</td>
  	<td>${sum0 + sum1}</td>
  	<td>${sum0}</td>
  	<td>${sum1}</td>
  	<td><#if (sum0 + sum1)!=0>${((sum0 / (sum0 + sum1)) * 100)?string("##.##")}%<#else>--%</#if></td>
  </tr>
 </@>
</body>
<#include "/templates/foot.ftl"/> 