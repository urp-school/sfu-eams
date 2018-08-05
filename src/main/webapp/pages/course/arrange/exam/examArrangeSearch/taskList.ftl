<#include "/templates/head.ftl"/>
<body >
  <table id="taskBar"></table>
<#include "/pages/course/arrange/exam/examArrange/unarrangeExamList.ftl"/>
 	<form name="actionForm" method="post" action="" onsubmit="return false;"></form>
  <script>
     var bar=new ToolBar('taskBar','<@bean.message key="entity.teachTask"/> <@bean.message key="entity.list"/>',null,true,false);
     bar.setMessage('<@getMessage/>');
     bar.addHelp("<@msg.message key="action.help"/>");
</script>
</body> 
<#include "/templates/foot.ftl"/> 