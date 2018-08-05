<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<table class="infoTable">
		<tr>
			<td class="title"><@msg.message key="attr.taskNo"/>：</td>
			<td class="content">${alteration.task.seqNo}</td>
			<td class="title"><@msg.message key="attr.courseNo"/>：</td>
			<td class="content">${alteration.task.course.code}</td>
		</tr>
		<tr>
			<td class="title"><@msg.message key="attr.courseName"/>：</td>
			<td class="content"><@i18nName alteration.task.course/></td>
			<td class="title">调课时间：</td>
			<td class="content">${alteration.alterationAt?string("yyyy-MM-dd HH:mm:ss")}</td>
		</tr>
		<tr>
			<td class="title">调课人：</td>
			<td class="content">${alteration.alterBy.name}</td>
			<td class="title">访问路径：</td>
			<td class="content">${(alteration.alterFrom)?html}</td>
		</tr>
		<tr>
			<td class="title"><@msg.message key="entity.teacher"/>：</td>
			<td class="content" colspan="3">${(alteration.task.teacherNames)?if_exists}</td>
		</tr>
		<tr>
			<td class="title">调课前信息：</td>
			<td class="content" colspan="3">${alteration.alterationBefore}</td>
		</tr>
		<tr>
			<td class="title">调课后信息：</td>
			<td class="content" colspan="3">${alteration.alterationAfter}</td>
		</tr>
	</table>
	<script>
		var bar = new ToolBar("bar", "查看调课变动详细信息", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addBack("<@msg.message key="action.back"/>");
	</script>
</body>
<#include "/templates/foot.ftl"/>