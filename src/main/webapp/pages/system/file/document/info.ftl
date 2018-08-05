<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<table class="infoTable" style="word-break:break-all">
		<tr>
			<td class="title">上传文件名：</td>
			<td class="content">${document.name}</td>
			<td class="title">上传者：</td>
			<td class="content">${document.uploaded.userName}</td>
		</tr>
		<tr>
			<td class="title">上传时间：</td>
			<td class="content">${document.uploadOn?string("yyyy-MM-dd hh:mm")}</td>
			<td class="title">文件路径：</td>
			<td class="content">${document.path?if_exists}</td>
		</tr>
		<#assign kind = RequestParameters["kind"]?default("")/>
		<#if kind?exists && kind == "std">
			<tr>
				<td class="title">接收学生：</td>
				<td class="content"><#list document.studentTypes as stdType><@i18nName stdType/><#if stdType_has_next>，</#if></#list></td>
				<td class="title">学生所在院系：</td>
				<td class="content"><#list document.departs as department><@i18nName department/><#if department_has_next>，</#if></#list></td>
			</tr>
		</#if>
	</table>
	<script>
		var bar = new ToolBar("bar", "查看上传文件信息", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addBack("<@msg.message key="action.back"/>");
	</script>
</body>
<#include "/templates/foot.ftl"/>