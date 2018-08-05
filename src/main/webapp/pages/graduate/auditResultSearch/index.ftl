<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<body>
	<table id="bar" width="100%"></table>
	<table class="frameTable">
		<tr valign="top">
			<td class="frameTable_view" width="22%"><#include "indexContent.ftl"/><#include "searchForm.ftl"/></td>
			<td><iframe name="pageIFrame" src="#" width="100%" scrolling="no" frameborder="0"></iframe></td>
		</tr>
	</table>
	<script>
		var bar = new ToolBar("bar", "培养计划完成情况", null ,true, true);
		bar.setMessage('<@getMessage/>');
		bar.addHelp("<@msg.message key="action.help"/>");
		
		search();
	</script>
</body>
<#include "/templates/foot.ftl"/>