<#include "/templates/head.ftl"/>
 <body>
  <table id="bar"></table>
  <@table.table id="listTable" width="80%" align="center">
    <@table.thead>
     <@table.td text="学年"/>
     <@table.td text="学期"/>
     <@table.td text="注册时间"/>
     <@table.td text="备注"/>
    </@>
    <@table.tbody datas=registers?sort_by(["calendar","start"]);register>
     <td>${register.calendar.year}</td>
     <td>${register.calendar.term}</td>
     <td>${register.registerAt?string("yyyy-MM-dd hh:mm:ss")}</td>
     <td>${register.remark?default("")}</td>
    </@>
  </@>
 <script>
   var bar = new ToolBar("bar","${register.std.name}的注册信息",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addPrint("<@msg.message key="action.print"/>");
   bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
 </script>
 </body>
<#include "/templates/foot.ftl"/>