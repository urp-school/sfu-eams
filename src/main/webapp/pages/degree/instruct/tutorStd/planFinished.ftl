 <table id="bar"></table>
 <#include "/pages/graduate/auditResultDetailForStudent.ftl"/>
 <script>
   var bar = new ToolBar("bar","学生培养计划完成情况",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addPrint("<@msg.message key="action.print"/>");
 </script>
