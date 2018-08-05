<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" >
  <table id="gradeListBar" width="100%"> </table>
  <#include "gradeListTable.ftl"/>
  <@htm.actionForm name="actionForm" action="moralGrade.do" entity="moralGrade"/>
  <script>
    var bar = new ToolBar("gradeListBar","成绩查询结果",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("发布","publish(2)");
    bar.addItem("取消发布","publish(1)");
    bar.addPrint("<@msg.message key="action.print"/>");
    function publish(status){
       addInput(document.actionForm,"status",status);
       multiAction('updateStatus');
    }
  </script>
 </body>
<#include "/templates/foot.ftl"/>
