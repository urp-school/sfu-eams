<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<table class="frameTable" width="100%">
		<tr valign="top">
			<form method="post" action="" name="actionForm">
				<td width="20%" class="frameTable_view">
				    <#include "searchForm.ftl"/>
				    <table><tr height="400px"><td></td></tr></table>
			    </td>
			</form>
			<td>
				<iframe name="pageIFrame" src="" frameborder="0" scrolling="no" width="100%"></iframe>
			</td>
		</tr>
	</table>
	<script>
		var bar = new ToolBar("bar", "学生类别查询", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addHelp("<@msg.message key="action.help"/>");
		
		var form = document.actionForm;
		
		function search() {
			form.action = "studentTypeSearch.do?method=search";
			form.target = "pageIFrame";
			form.submit();
		}
		
		search();
	</script>
</body>
<#include "/templates/foot.ftl"/>