<#include "/templates/head.ftl"/>
<body >
  	<table id="taskBar"></table>
  	<#include "/pages/course/arrange/exam/examArrange/arrangeExamList.ftl"/>
  	<form name="actionForm" method="post" onsubmit="return false;" action="">
   		<input type="hidden" name="examType.id" value="${RequestParameters['examType.id']}">
  	</form>
  	<script>
     	var bar = new ToolBar('taskBar', '排考结果列表', null, true, true);
    	bar.setMessage('<@getMessage/>');
    	bar.addHelp("<@msg.message key="action.help"/>");
  	</script>
</body> 
<#include "/templates/foot.ftl"/> 