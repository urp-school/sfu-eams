<#include "/templates/head.ftl"/>
<body>
	<table id="bar" width="100%"></table>
	<table class="frameTable_title" width="100%">
		<form method="post" action="" name="actionForm">
		<tr>
			<td></td>
			<#include "/pages/course/calendar.ftl"/>	
		</tr>
		</form>
	</table>
	<table class="frameTable" width="100%">
		<tr valign="top">
		
			<td><iframe name="iframePage" src="#" scrolling="no" frameborder="0" width="100%"></iframe></td>
		</tr>
	</table>
	<script>
		var bar = new ToolBar("bar", "转专业", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addHelp("<@msg.message key="action.help"/>");
		
		var form = document.actionForm;
		function search() {
			form.action = "specialityAlerationAudit.do?method=planInfo";
			form.target ="iframePage";
			form.submit();
		}
		
		
		search();
	</script>
</body>
<#include "/templates/foot.ftl"/>