<#include "/templates/head.ftl"/>
 <body >
 <table id="gpBar"></table>
 <script>
  var bar = new ToolBar("gpBar","统计结果",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("<@msg.message key="action.print"/>","print()");
 </script>
 <@table.table id="statTable" width="100%">
 <@table.thead>
      <td width="20%" id="what.name">教师学位</td>
      <td width="10%" id="countors[0]">数量</td>
      <td width="10%" >百分比</td>
  </@>
  <#assign overall=0>
  <#list stats as statItem>
  <#assign overall=overall+statItem.countors[0]?default(0)>
  </#list>
  <@table.tbody datas=stats;statItem>
      <td>${(statItem.what.name)?default("无")}</td>
      <td>${statItem.countors[0]}</td>
      <td>${(((statItem.countors[0]/(overall*1.0))*10000)?int/100)}%</td>
  </@>
  <tr class="darkColumn" style="text-align:center; font-weight: bold">
   	  <td>合计</td>
   	  <td>${overall}</td>
   	  <td></td>
  </tr>
  </@>
  <#list 1..5 as i><br></#list>
</body>
<#include "/templates/foot.ftl"/> 