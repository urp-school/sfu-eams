<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<table class="frameTable" width="100%">
		<tr valign="top">
			<form method="post" action="" name="actionForm" onsubmit="return false;">
			<td class="frameTable_view" width="20%">
				<#include "searchForm.ftl"/>
                <table><tr height="350px"><td></td></tr></table>
			</td>
			</form>
			<td>
				<iframe name="pageIFrame" src="#" frameborder="0" width="100%" scrolling="no"></iframe>
			</td>
		</tr>
	</table>
	<script>
		var bar = new ToolBar("bar", "部门信息查询", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addHelp("<@msg.message key="action.help"/>");
		
		var form = document.actionForm;
		
		function search() {
			form.action = "departmentSearch.do?method=search";
			form.target = "pageIFrame";
			form.submit();
		}
		
		search();
	</script>
</body>
<#include "/templates/foot.ftl"/>