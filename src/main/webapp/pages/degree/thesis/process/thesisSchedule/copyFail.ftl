<#include "/templates/head.ftl"/>
<body >
<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','复制失败',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack('<@bean.message key="action.back"/>');
</script>
<table class="settingTable">
		<#if copySchedules?exists>
	 	<tr>
	 		<td>复制成功</td>
	 		<td>
	 			<#list copySchedules as schedule>
	 				${schedule.studentType.name}/${schedule.enrollYear}/${schedule.studyLength}<br>
	 			</#list>
	 		</td>
	 	</tr>
	 	</#if>
	 	<tr>
	 		<td>复制失败</td>
	 		<td>
	 			${copyErrors}
	 		</td>
	 	</tr>
	 	<tr>
	 		<td>失败原因</td>
	 		<td>已经存在你要复制的进度信息 请核对 学生类别/所在年级/学制
	 		</td>
	 	</tr>
</table>
</body>
<#include "/templates/foot.ftl"/>