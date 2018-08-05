<#include "/templates/head.ftl"/>
<body>
    <table width="100%" height="100%" style="padding: 0px; border-spacing: 0px" cellspacing="0" cellpadding="0">
        <tr height="2%" valign="top">
            <td style="padding: 0px; border-spacing: 0px">
				<table id="bar"></table>
            </td>
        </tr>
        <tr height="2%" valign="top">
            <td style="padding: 0px; border-spacing: 0px">
				<table class="frameTable_title">
					<form method="post" name="actionForm" action="">
					<tr>
						<td></td>
						<#include "/pages/course/calendar.ftl"/>
						<input type="hidden" name="evaluateResult.teachCalendar.id" value="${calendar.id}"/>
					</tr>
				</table>
            </td>
        </tr>
        <tr valign="top">
            <td style="padding: 0px; border-spacing: 0px">
				<table class="frameTable" width="100%" height="100%">
					<tr valign="top">
						<td class="frameTable_view" width="22%"><#include "searchForm.ftl"/></td>
					</form>
						<td><iframe name="resultFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe></td>
					</tr>
				</table>
            </td>
        </tr>
    </table>
	<script>
		var bar = new ToolBar("bar", "维护评教结果", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addHelp("<@msg.message key="action.help"/>");
		
		var form = document.actionForm;
		function search() {
			form.action = "evaluateResult.do?method=search";
			form.target = "resultFrame";
			form.submit();
		}
		search();
	</script>
</body>
<#include "/templates/foot.ftl"/>