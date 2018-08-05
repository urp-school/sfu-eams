<#include "/templates/head.ftl"/>
 <body >
 <table id="gpBar"></table>
 <script>
  var bar = new ToolBar("gpBar","统计结果",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("<@msg.message key="action.print"/>","print()");
  orderBy=function(what){
     self.location="teacherStat.do?method=statByGraduateSchool&orderBy="+what;
  }
 </script>
 <@table.table id="statTable" width="100%" sortable="true">
 <@table.thead>
      <@table.sortTd width="20%" id="what.name" text="毕业学校"/>
      <@table.sortTd width="10%" id="countors[0]" text="数量"/>
      <td width="10%" >百分比</td>
  </@>
  <#assign overall=0>
  <#list stats as statItem>
  <#assign overall=overall+statItem.countors[0]?default(0)>
  </#list>
  <@table.tbody datas=stats;statItem>
    <#assign systemSchool=false>
     <#if statItem.what?exists><#if statItem.what.id=systemConfig.school.id> <#assign systemSchool=true></#if></#if>
      <td <#if systemSchool>style="background-color:yellow"</#if>>${(statItem.what.name)?default("无")}</td>
      <td <#if systemSchool>style="background-color:yellow"</#if>>${statItem.countors[0]}</td>
      <td <#if systemSchool>style="background-color:yellow"</#if>>${(((statItem.countors[0]/(overall*1.0))*10000)?int/100)}%</td>
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