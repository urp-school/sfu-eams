<#include "/templates/head.ftl"/>
<body>
	<table id="bar" width="100%"></table>
	<@table.table id="tuitionFeeId" sortable="true" width="100%">
		<@table.thead>
			<@table.selectAllTd id="stdId"/>
			<@table.sortTd name="attr.stdNo" id="std.code"/>
			<@table.sortTd name="attr.studentName" id="std.name"/>
			<@table.sortTd name="entity.studentType" id="std.type.name"/>
			<@table.sortTd name="common.college" id="std.department.name"/>
			<@table.sortTd name="entity.speciality" id="std.firstMajor.name"/>
			<@table.sortTd name="entity.specialityAspect" id="std.firstAspect.name"/>
		</@>
		<@table.tbody datas=students;std>
			<@table.selectTd id="stdId"  value=std.id/>
			<td>${std.code}</td>
			<td>${std.name}</td>
			<td><@i18nName (std.type)?if_exists/></td>
			<td><@i18nName (std.department)?if_exists/></td>
			<td><@i18nName (std.firstMajor)?if_exists/></td>
			<td><@i18nName (std.firstAspect)?if_exists/></td>
		</@>
	</@>
	<form method="post" action="" name="actionForm" onsubmit="return false;">
		
	</form>
	<script>
		var bar = new ToolBar("bar", "无学费学生记录", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("计算学费", "statFee()");
		bar.addItem("设置学费", "settingFee()");
		
		var isSelected = false;
		var recordSize = ${totalSize};
		var toSize = 1000;
		function statFee() {
			var ids = getSelectIds("stdId");
			if (ids == null || ids == "") {
				if (recordSize > toSize) {
					alert("全部计算或设置的学生记录不能超过" + toSize + "条（包含）。\n\n解决方法：\n1. 把要操作的记录限制在" + toSize + "条之下；\n2. 选择要操作的学生记录后进行。");
					return;
				} else if (confirm("您要计算下面查出的所有学生（共" + recordSize + "条）学费吗？")) {
					isSelected = false;
				} else {
					alert("如果不想全部计算学生记录，则可以进行选择学生记录后进行该操作。");
					return;
				}
			} else {
				if (confirm("您是否想计算"+countId(ids) +"位学生的学费？")) {
					isSelected = true;
				} else {
					return;
				}
			}
			
			parent.form.action = "tuitionFee.do?method=statFee";
			var remark = prompt("请输入备注内容。\n若内容为空或取消输入，则认为你不需要备注。", "");
			parent.form["remark"].value = (remark == null || remark == "" ? "" : remark);
			parent.form["tuition_exists"].value = "";
			parent.form["tuitionIds"].value = "";
			if (isSelected) {
				addInput(parent.form, "stdIds", ids, "hidden");
			} else {
				addInput(parent.form, "stdIds", ",", "hidden");
			}
			parent.form.submit();
		}
		
		function settingFee() {
			var ids = getSelectIds("stdId");
			if (ids == null || ids == "") {
				if (recordSize > toSize) {
					alert("全部计算或设置的学生记录不能超过" + toSize + "条（包含）。\n\n解决方法：\n1. 把要操作的记录限制在" + toSize + "条之下；\n2. 选择要操作的学生记录后进行。");
					return;
				} else if (confirm("您要设置下面查出的所有学生（共" + recordSize + "条）学费吗？")) {
					isSelected = false;
				} else {
					alert("如果不想全部设置学生记录，则可以进行选择学生记录后进行该操作。");
					return;
				}
			} else {
				if (confirm("您要设置"+countId(ids) +"位学生的学费？")) {
					isSelected = true;
				} else {
					return;
				}
			}
			var fee = prompt("请输入学费，格式为“0.##”，单位：元（RMB）。","0.00");
			if (!/^\d*\.?\d*$/.test(fee)) {
            	alert("您输入的学费格式不正确，不能设置。");
            	return;
            } else if (parseFloat(fee) > 1000000) {
            	alert("您输入的学费不能超过1,000,000.00元（RMB），不能设置。");
            	return;
            }
			
			parent.form.action = "tuitionFee.do?method=settingFee";
			var remark = prompt("请输入备注内容。\n若内容为空或取消输入，则认为你不需要备注。", "");
			parent.form["remark"].value = (remark == null || remark == "" ? "" : remark);
			parent.form["tuition_exists"].value = "";
			parent.form["tuitionIds"].value = "";
			parent.form["fee"].value = fee;
			if (isSelected) {
				addInput(parent.form, "stdIds", ids, "hidden");
			} else {
				addInput(parent.form, "stdIds", ",", "hidden");
			}
			parent.form.submit();
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>
