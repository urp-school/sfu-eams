<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<table class="frameTable" width="100%">
		<tr>
			<form method="post" action="" name="actionForm">
			<td class="frameTable_view" width="20%"><#include "/pages/components/specialityAspectSearchTable.ftl"/></td>
			</form>
			<td><iframe name="pageFrame" src="#" width="100%" height="100%" frameborder="0" scrolling="no"></iframe></td>
		</tr>
	</table>
	<script>
		var bar = new ToolBar("bar", "专业方向基础信息查询", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addHelp("<@msg.message key="action.help"/>");
		
		var form = document.actionForm;
		function searchSpecialityAspect() {
			form.action = "specialityAspectSearch.do?method=search";
			form.target = "pageFrame";
			form.submit();
		}
    	searchSpecialityAspect();
	</script>
    <#include "/templates/stdTypeDepart2Select.ftl"/>
</body>
<#include "/templates/foot.ftl"/>