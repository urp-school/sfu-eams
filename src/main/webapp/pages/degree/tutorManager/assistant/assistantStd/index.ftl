<#include "/templates/head.ftl"/>
<body>
	<table id="assistantBar"></table>
	<script> 
	var bar = new ToolBar("assistantBar","<@msg.message key="std.assistant.title"/>",null,true,true);
	bar.setMessage('<@getMessage/>');
	bar.addHelp("<@msg.message key="action.help"/>");
	</script>
	<@table.table width="100%">
	   	<@table.thead>
	   		<@table.td name="std.assistant.startOn" width="15%"/>
	   		<@table.td name="std.assistant.endOn" width="15%"/>
	   		<@table.td name="std.assistant.doneWork" width="50%"/>
	   		<@table.td name="std.assistant.period" width="5%"/>
	   		<@table.td name="std.assistant.tutorAffirmed"/>
	   	</@>
	   	<@table.tbody datas=assistants;assistant>
	    	<td>&nbsp;${assistant.startTime}</td>
	    	<td>&nbsp;${assistant.endTime}</td>
	    	<td align="center">${assistant.job?if_exists}</td>
	    	<td align="center">${assistant.period?if_exists}</td>
	    	<td align="center"><#if (assistant.isConfirm)?exists><#if assistant.isConfirm><@msg.message key="common.yes"/><#else><@msg.message key="common.no"/></#if></#if></td>
	   	</@>
	</@>
</body>
<#include "/templates/foot.ftl"/>