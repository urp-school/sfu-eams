<#include "/templates/head.ftl"/>
 <body >
 <table id="gpBar"></table>
 <script>
  var bar = new ToolBar("gpBar","统计结果",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("<@msg.message key="action.print"/>","print()");
 </script>
 <#assign entityNames={'class':'班级名称','teacher':'教师姓名','courseType':'课程类别名称','teachDepart':'开课院系名称','depart':'上课院系名称'}>
 <#assign entityNames=entityNames+{'studentType':'学生类别'}>
 <#assign statName=entityNames[RequestParameters['kind']]>
 <@table.table id="statTable" sortable="true" width="100%">
 <@table.thead>
      <@table.sortTd width="20%" id="what.name" text=statName/>
      <@table.sortTd width="10%" id="countors[0]" text="任务数量"/>
      <@table.sortTd width="10%" id="countors[1]" text="周课时"/>
      <@table.sortTd width="10%" id="countors[2]" text="总课时"/>
      <@table.sortTd width="10%" id="countors[3]" text="总学分"/>
  </@>
  <#assign sum0 = 0/>
  <#assign sum1 = 0/>
  <#assign sum2 = 0/>
  <#assign sum3 = 0/>
  <@table.tbody datas=stats;statItem>
      <td>${statItem.what.name}</td>
      <td><#assign sum0 = sum0 + statItem.countors[0]?default(0)/>${statItem.countors[0]}</td>
      <td><#assign sum1 = sum1 + statItem.countors[1]?default(0)/>${statItem.countors[1]}</td>
      <td><#assign sum2 = sum2 + statItem.countors[2]?default(0)/>${statItem.countors[2]}</td>
      <td><#assign sum3 = sum3 + statItem.countors[3]?default(0)/>${statItem.countors[3]}</td>
  </@>
  <tr class="darkColumn" style="text-align:center; font-weight: bold">
  	<td>汇总</td>
  	<td>${sum0}</td>
  	<td>${sum1}</td>
  	<td>${sum2}</td>
  	<td>${sum3}</td>
  </tr>
 </@>
<#list 1..3 as i><br></#list>
</body>
<#include "/templates/foot.ftl"/> 