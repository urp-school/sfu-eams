<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<table class="frameTable" width="100%" cellpadding="0" cellspacing="0">
		<tr valign="top">
		<form method="post" action="" name="actionForm" onsubmit="return false;">
			<td class="frameTable_view" width="20%"><#include "searchForm.ftl"/></td>
		</form>
			<td><iframe name="pageFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe></td>
		</tr>
	</table>
	<script>
		var bar = new ToolBar("bar", "成绩记录方式设置", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addHelp("<@msg.message key="action.help"/>");
		
		var form = document.actionForm;
		
		function search() {
			form.action = "markStyleConfig.do?method=search";
			form.target = "pageFrame";
			form.submit();
		}
		
		search();
	</script>
</body>
<#include "/templates/foot.ftl"/>