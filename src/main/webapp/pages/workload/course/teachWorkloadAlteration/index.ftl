<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<table class="frameTable" width="100%">
		<form method="post" action="" name="actionForm">
			<tr class="frameTable_view">
				<td></td>
				<#include "/pages/course/calendar.ftl"/>
			</tr>
	</table>
	<table class="frameTable" width="100%">
		<tr valign="top">
			<td class="frameTable_view" width="22%"><#include "searchForm.ftl"/></td>
			<td><iframe name="pageIframe" src="#" width="100%" frameborder="0" scrolling="no"></iframe></td>
		</tr>
		</form>
	</table>
	<script>
		var bar = new ToolBar("bar", "工作量修改日志", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addBack("<@msg.message key="action.back"/>");
		
		var form = document.actionForm;
		form["calendar.studentType.id"].value = "${RequestParameters["calendar.studentType.id"]?default("")}";
		form["calendar.year"].value = "${RequestParameters["calendar.year"]?default("")}";
		form["calendar.term"].value = "${RequestParameters["calendar.term"]?default("")}";
		
		function search() {
			form.action = "teachWorkloadAlteration.do?method=search";
			form.target = "pageIframe";
			form.submit();
		}
		
		search();
		
		function formReset() {
			form.reset();
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>