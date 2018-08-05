<#include "/templates/head.ftl"/>
<#include "../processManageMacro.ftl">
<BODY topmargin="0" leftmargin="0">
  <table id="backBar" width="100%"></table>
	<script>
	   var bar = new ToolBar('backBar','论文进度环节展示',null,true,true);
	   bar.setMessage('<@getMessage/>');
	   bar.addHelp("<@msg.message key="action.help"/>");
	   bar.addBack();
   </script>
<@table.table align="center" width="90%">
	<div align="center"><h2>${thesisManage.student.name}论文与学位申请情况</h2></div>
	<tr class="grayStyle">
		<td>学生类别:${schedule.studentType.name}</td>
		<td>入学年份:${schedule.enrollYear}</td>
		<td>学制:${schedule.studyLength}</td>
	</tr>
</@>
   	<#assign index=1>
   	<@processTable id="01" href="thesisTopicOpen_tutor.do?method=index" personType="admin"/>
   	<@processTable id="02" href="#" personType="admin"/>
   	<@processTable id="03" href="preAnswer_tutor.do?method=index" personType="admin"/>
   	<@processTable id="04" href="annotateTutor.do?method=doDefault" personType="admin"/>
   	<@processTable id="05" href="#" personType="admin"/>
   	<@processTable id="06" href="#" personType="admin"/>
</body>
<#include "/templates/foot.ftl"/>