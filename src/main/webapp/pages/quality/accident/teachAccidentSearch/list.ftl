<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="myBar" width="100%"></table> 
  <#include "listTable.ftl"/>
  <@htm.actionForm name="actionForm" entity="teachAccident" action="teachAccidentSearch.do">
  </@>
  <script>
    var bar = new ToolBar("myBar","教学事故列表",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("<@msg.message key="action.info"/>",'info()');
  </script>
</body>
 <#include "/templates/foot.ftl"/>