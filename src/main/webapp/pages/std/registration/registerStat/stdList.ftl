<#include "/templates/head.ftl"/>
 <body >  
 <table id="bar"></table>
 <#include "/pages/components/stdList1stTable.ftl"/>
 <script>
   var bar = new ToolBar("bar","没有注册学生列表",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addPrint("<@msg.message key="action.print"/>");
 </script>
 </body>
<#include "/templates/foot.ftl"/> 