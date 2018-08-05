<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="stdBar" width="100%"></table>
  <#assign courseTakes = courseTakes?sort_by(["student","code"])>
  <#include "../courseTakeSearch/courseTakeList.ftl"/>
  <form name="actionForm" method="post"></form>
  <script language="JavaScript" type="text/JavaScript" src="scripts/course/CourseTake.js"></script>
  <script>
     var bar=new ToolBar('stdBar','选课冲突学生名单(${courseTakes?size})',null,true,false);
     bar.setMessage('<@getMessage/>');
    var menu =bar.addMenu("修改修读类别...");
    <#list courseTakeTypes as type>
    menu.addItem("<@i18nName type/>","editCourseTakeType(${type.id})");
    </#list>
    bar.addItem("发送消息","sendMessage()","inbox.gif");
    bar.addItem("退课","withdraw()","delete.gif","减少教学班实际人数,降低学生已选学分,更新选中状态");
    bar.addItem("<@msg.message key="action.print"/>","print()");
</script>
</body>
<#include "/templates/foot.ftl"/>

