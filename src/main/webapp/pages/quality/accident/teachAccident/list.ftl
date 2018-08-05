<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="myBar" width="100%"></table> 
  <#include "../teachAccidentSearch/listTable.ftl"/>
  <@htm.actionForm name="actionForm" entity="teachAccident" action="teachAccident.do">
    <input name="task.calendar.id" value="${RequestParameters['teachAccident.task.calendar.id']}" type="hidden"/>
  </@>
  <script>
    var bar = new ToolBar("myBar","教学事故列表",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("<@msg.message key="action.info"/>",'info()');
    bar.addItem("<@msg.message key="action.edit"/>",'edit()');
    bar.addItem("<@msg.message key="action.add"/>",'add()');
    bar.addItem("<@msg.message key="action.delete"/>",'remove()');
  </script>
</body>
 <#include "/templates/foot.ftl"/>