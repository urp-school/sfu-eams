<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<#include "listTable.ftl"/>
	<br><br><br><br>
	<form method="post" action="" name="actionForm" onsubmit="return false;"></form>
	<script>
		var bar = new ToolBar("bar", "学生签证信息列表", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("<@msg.message key="action.edit"/>", "editVisa()");
		bar.addItem("<@msg.message key="action.info"/>", "stdIdAction()", "detail.gif");
		
		var form = document.actionForm;
		function editVisa() {
			var id = getSelectId("stdId");
			if (id == null || id == "") {
				alert("请选择要修改签证的学生！");
				return;
			} else if (id.indexOf(',') >= 0) {
				alert("请选择一个要修改签证的学生！");
				return;
			}
			addInput(form, "stdId", id, "hidden");
			form.action = "abroadInfo.do?method=edit";
			form.target = "displayFrame";
			form.submit();
		}
		
		function stdIdAction(stdId) {
			form.action = "abroadInfo.do?method=info";
			if (null == stdId || "" == stdId) {
				submitId(form, "stdId", false);
			} else {
				addInput(form, "stdId", stdId, "hidden");
				form.target = "displayFrame";
				form.submit();
			}
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>