<#include "/templates/head.ftl"/>
 <body >
 <table id="gpBar"></table>
 <script>
  var bar = new ToolBar("gpBar","双语教学统计结果",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("<@msg.message key="action.print"/>","print()");
 </script>
 <@table.table id="statTable" sortable="true" width="100%">
 <@table.thead>
      <@table.sortTd width="20%" id="what.name" name="entity.department"/>
      <@table.sortTd width="10%" id="countors[0]" text="双语课程"/>
      <@table.sortTd width="10%" id="countors[1]" text="总课程"/>
      <td width="10%">比例比</td>
  </@>
  <#assign sum0 = 0/>
  <#assign sum1 = 0/>
  <@table.tbody datas=stat.items;statItem>
      <td>${statItem.what.name}</td>
      <td><#assign sum0 = sum0 + statItem.countors[0]?default(0)/>${statItem.countors[0]}</td>
      <td><#assign sum1 = sum1 + statItem.countors[1]?default(0)/>${statItem.countors[1]}</td>
      <td>${((statItem.countors[0]/(statItem.countors[1]))*100)?string("#0.00")}%</td>
  </@>
  <tr class="darkColumn" style="text-align:center; font-weight: bold">
  	<td>汇总</td>
  	<td>${sum0}</td>
  	<td>${sum1}</td>
  	<td><#if (sum0 + sum1)!=0>${((sum0 / sum1) * 100)?string("##.##")}%<#else>--%</#if></td>
  </tr>
 </@>
</body>
<#include "/templates/foot.ftl"/> 