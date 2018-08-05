<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<table class="frameTable" width="100%">
		<tr valign="top">
			<form method="post" action="" name="actionForm">
			<td class="frameTable_view" width="20%">
			    <#include "/pages/components/courseSearchTable.ftl"/>
			    <table><tr height="400px"><td></td></tr></table>
		    </td>
			</form>
			<td><iframe name="pageFrame" src="#" width="100%" height="100%" frameborder="0" scrolling="no"></iframe></td>
		</tr>
	</table>
	<script>
		var bar = new ToolBar("bar", "课程基础信息查询", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addHelp("<@msg.message key="action.help"/>");
		
		var form = document.actionForm;
		function searchCourse() {
			form.action = "courseSearch.do?method=search";
			form.target = "pageFrame";
			form.submit();
		}
    	searchCourse();
	</script>
</body>
<#include "/templates/foot.ftl"/>