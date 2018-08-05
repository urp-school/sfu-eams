<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<body>
	<table id="bar"></table>
	<table class="frameTable" width="100%">
		<tr valign="top">
			<td class="frameTable_view" width="22%" style="font-size:10pt"><#include "menu.ftl"/></td>
			<td><iframe name="pageFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe></td>
		</tr>
	</table>
	<form method="post" action="" name="actionForm"></form>
	<script>
		var bar = new ToolBar("bar", " 辅导员所带班级与学生", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addHelp("<@msg.message key="action.help"/>");
		
		var defaultPage = "${RequestParameters["indexPage"]?default("searchAdminClass")}";
		var form = document.actionForm;
		function selectMenu(td, indexPage) {
            clearSelected(menuTable,td);
            setSelectedRow(menuTable,td);
            if (indexPage == null || indexPage == "") {
            	indexPage = defaultPage;
            }
            form.action = "instructorStd.do?method=" + indexPage;
            form.target = "pageFrame";
            addInput(form, "indexPage", indexPage, "hidden");
            form.submit();
		}
		
		$(defaultPage).onclick();
	</script>
</body>
<#include "/templates/foot.ftl"/>