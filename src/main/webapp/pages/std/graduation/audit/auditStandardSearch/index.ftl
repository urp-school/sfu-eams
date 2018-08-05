<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr valign="top">
			<iframe name="pageIFrame" src="#" frameborder="0" width="100%" scrolling="no"></iframe>
		</tr>
	</table>
	<form method="post" action="" name="actionForm"></form>
	<script>
		var bar = new ToolBar("bar", "<@bean.message key="attr.graduate.graduateAuditStandardList"/>", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("<@msg.message key="action.refresh"/>", "search()");
		
		var form = document.actionForm;
		function search() {
			form.action = "auditStandardSearch.do?method=search";
			form.target = "pageIFrame";
			form.submit();
		}
		
		search();
	</script>
</body>
<#include "/templates/foot.ftl"/>