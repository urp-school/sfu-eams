<#include "/templates/head.ftl"/>
 <body>
  <table id="bar"></table>
  <@table.table id="listTable" width="70%" align="center">
    <@table.thead>
     <@table.td name="attr.year2year"/>
     <@table.td name="attr.term"/>
     <@table.td name="std.regist.registerAt"/>
    </@>
    <@table.tbody datas=registers?sort_by(["calendar","start"]);register>
     <td>${register.calendar.year}</td>
     <td>${register.calendar.term}</td>
     <td>${register.registerAt?string("yyyy-MM-dd hh:mm:ss")}</td>
    </@>
  </@>
 <script>
   var bar = new ToolBar("bar","<@msg.message key="std.regist.title"/>",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addPrint("<@msg.message key="action.print"/>");
   bar.addBack("<@msg.message key="action.back"/>");
 </script>
 </body>
<#include "/templates/foot.ftl"/>