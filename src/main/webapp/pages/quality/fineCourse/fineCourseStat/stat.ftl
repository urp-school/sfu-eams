<#include "/templates/head.ftl"/>
 <body >
 <table id="gpBar"></table>
 <script>
  var bar = new ToolBar("gpBar","统计结果",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("<@msg.message key="action.print"/>","print()");
 </script>
  <h3 align="center">精品课程统计结果</h3>
 <@table.table id="statTable"   width="100%">
 <@table.thead>
      <td width="20%">年度</td>
      <td width="10%">合计</td>
      <#list levels as level>
      <td>${level.name}</td>
      </#list>
  </@>
  <#assign overall=0>
  <@table.tbody datas=stats;statItem>
      <td><A href="fineCourseStat.do?method=list&fineCourse.passedYear=${statItem[0]}" title="点击查看详情">${statItem[0]}</A></td>
      <td><#assign all=0><#list levels as level><#assign all=all+statItem[level_index+1]></#list><#assign overall=overall+all>${all}</td>
      <#list levels as level>
      <td><#if statItem[level_index+1]?default(0)!=0><A href="fineCourseStat.do?method=list&fineCourse.passedYear=${statItem[0]}&fineCourse.level.id=${level.id}"  title="点击查看详情">${statItem[level_index+1]}</A></#if></td>
      </#list>
  </@>
  <tr align="center">
    <td>合计</td>
    <td>${overall}</td>
    <#list levels as level>
      <#assign columnSum=0>
      <#list stats as stat>
      <#assign columnSum=columnSum+stat[level_index+1]?default(0)>
     </#list>
     <td><#if columnSum!=0>${columnSum}</#if></td>
   </#list>
  </tr>
 </@>
</body>
<#include "/templates/foot.ftl"/> 