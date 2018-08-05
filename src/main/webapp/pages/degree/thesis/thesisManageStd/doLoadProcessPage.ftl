<#include "/templates/head.ftl"/>
<#include "../processManageMacro.ftl"/>
<BODY topmargin=0 leftmargin=0>
  <table id="backBar" width="100%"></table>
	<script>
	   var bar = new ToolBar('backBar','<@msg.message key="thesis.title"/>',null,true,true);
	   bar.setMessage('<@getMessage/>');
	   bar.addHelp("<@msg.message key="action.help"/>");
   </script>
<@table.table align="center" width="90%">
	<div align="center"><h2><@msg.message key="thesis.mainTitle"/></h2></div>
	<tr class="grayStyle">
		<td><@msg.message key="entity.studentType"/>:<@i18nName schedule.studentType/></td>
		<td><@msg.message key="std.enrollYear"/>:${schedule.enrollYear}</td>
		<td><@msg.message key="std.eduLength"/>:${schedule.studyLength}</td>
	</tr>
</@>
   	<#assign index=1>
   	<@processTable id="01" href="loadThesisTopic_std.do?method=doLoadThesisTopic" personType="std"/>
   	<@processTable id="02" href="#" stdFileDisplay="true" personType="std"/>
   	<@processTable id="04" href="preAnswer_std.do?method=answer" personType="std"/>
   	<@processTable id="03" href="annotateStd.do?method=doLoad" personType="std"/>
   	<@processTable id="05" href="formalAnswerStd.do?method=doApply" personType="std"/>
   	<@processTable id="07" href="degreeApplyStd.do?method=degreeApply" personType="std"/>
<form name="conditionForm" method="post" action="" onsubmit="return false;">
</form>
</body>
<#include "/templates/foot.ftl"/>