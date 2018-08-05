<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="myBar" width="100%"></table>
 <#list stdGPs as stdGP>
 <#include "stdGradeStat.ftl"/>
 </#list>
  <script>
    var bar = new ToolBar("myBar","学生在校成绩统计",null,true,true);
    bar.addPrint("<@msg.message key="action.print"/>");
    bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
  </script>
</body>
<#include "/templates/foot.ftl"/>
