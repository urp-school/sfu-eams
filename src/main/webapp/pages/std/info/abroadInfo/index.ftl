<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<table class="frameTable" width="100%" align="center">
		<tr valign="top"><#include "/pages/components/initAspectSelectData.ftl"/>
			<td align="center" width="168px" class="frameTable_view">
				<form method="post" action="" name="searchForm" onsubmit="return false;">
					<#include "/pages/components/stdSearchTable.ftl"/>
				</form>
			</td>
			<td><iframe name="displayFrame" src="#" width="100%" frameborder="0" scrolling="1"></iframe></td>
		</tr>
	</table>
	<script>
		var bar = new ToolBar("bar", "设置签证到期时间", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("统计到期时间", "statDeadline()");
		bar.addHelp("<@msg.message key="action.help"/>");
		
		var form = document.searchForm;
		search();
		function search() {
			form.action = "abroadInfo.do?method=search";
			form.target = "displayFrame";
			form.submit();
		}
		
		function statDeadline() {
			form.action = "abroadInfo.do?method=statDeadline";
			form.target = "_self";
			form.submit();
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>