<#include "/templates/head.ftl"/>
<body>
	<table id="bar" width="100%"></table>
	<@table.table id="tuitionFeeId" sortable="true" width="100%">
		<@table.thead>
			<@table.selectAllTd id="tuitionId"/>
			<@table.sortTd name="attr.stdNo" id="tuition.std.code"/>
			<@table.sortTd name="attr.studentName" id="tuition.std.name"/>
			<@table.sortTd name="entity.studentType" id="tuition.std.type.name"/>
			<@table.sortTd name="common.college" id="tuition.std.department.name"/>
			<@table.sortTd name="entity.speciality" id="tuition.std.firstMajor.name"/>
			<@table.sortTd name="entity.specialityAspect" id="tuition.std.firstAspect.name"/>
			<@table.sortTd text="学费" id="tuition.fee"/>
			<@table.sortTd name="fee.isCompleted" id="tuition.isCompleted"/>
			<@table.sortTd name="attr.remark" id="tuition.remark"/>
		</@>
		<@table.tbody datas=tuitions;tuition>
			<@table.selectTd id="tuitionId"  value=tuition.id/>
			<td>${tuition.std.code}</td>
			<td>${tuition.std.name}</td>
			<td><@i18nName (tuition.std.type)?if_exists/></td>
			<td><@i18nName (tuition.std.department)?if_exists/></td>
			<td><@i18nName (tuition.std.firstMajor)?if_exists/></td>
			<td><@i18nName (tuition.std.firstAspect)?if_exists/></td>
			<td>${(tuition.fee?string("0.00"))?default("0.00")}</td>
			<td><#if (tuition.isCompleted)?exists><#if tuition.isCompleted><font color="green"><@msg.message key="attr.finished"/></font><#else><font color="red"><@msg.message key="attr.unfinished"/></font></#if></#if></td>
			<td>${(tuition.remark?html)?default(" ")}</td>
		</@>
	</@>
	<script>
		var bar = new ToolBar("bar", "有学费学生记录", null, true, true);
		bar.setMessage('<@getMessage/>');
		var menuBar = bar.addMenu("完成设置");
		menuBar.addItem("<@msg.message key="attr.finished"/>", "settingCompleted(true)");
		menuBar.addItem("<@msg.message key="attr.unfinished"/>", "settingCompleted(false)");
		bar.addItem("重算学费", "reStatFee()");
		bar.addItem("重设学费", "reSettingFee()");
		bar.addItem("导出学费", "exportData()");
		bar.addItem("无学费名单", "notExistsSearch()");
		
		function  settingCompleted(isCompleted) {
			var tuitionIds = getSelectIds("tuitionId");
			if (tuitionIds == "" || tuitionIds == null) {
				alert("请选择您要操作的记录。");
				return;
			}
			if (isCompleted == "" || isCompleted == null) {
				isCompleted = false;
			}
			parent.form.action = "tuitionFee.do?method=settingCompleted&isCompleted=" + isCompleted;
			parent.form["tuition_exists"].value = "";
			addInput(parent.form, "tuitionIds", tuitionIds, "hidden");
			addInput(parent.form, "params", queryStr, "hidden");
			parent.form.submit();
		}
		
		function notExistsSearch() {
			parent.form.action = "tuitionFee.do?method=search";
			parent.form["tuition_exists"].value = 1111;
			parent.form.submit();
		}
		
		var isSelected = false;
		var recordSize = ${totalSize};
		var toSize = 1000;
		function reStatFee() {
			var ids = getSelectIds("tuitionId");
			if (ids == null || ids == "") {
				if (confirm("您要重新计算下面所有学生的学费吗？\n“是”要，“否”不要。")) {
					if (recordSize > toSize) {
						alert("全部重新计算或重新设置的学生记录不能超过" + toSize + "条（包含）。\n\n解决方法：\n1. 把要操作的记录限制在" + toSize + "条之下；\n2. 选择要操作的学生记录后进行。");
						return;
					} else if (confirm("这样会把已经计算的" + recordSize + "位学生学费全部覆盖，要继续吗？")) {
						isSelected = false;
					} else {
						alert("如果不想全部重新计算学生记录，则可以进行选择学生记录后进行该操作。");
						return;
					}
				} else {
					return;
				}
			} else {
				if (confirm("您要重新计算学生"+countId(ids) +"位学生的学费吗？")) {
					if (confirm("这样会把已经计算的学生学费覆盖，要继续吗？")) {
						isSelected = true;
					} else {
						return;
					}
				} else {
					return;
				}
			}
			
			parent.form.action = "tuitionFee.do?method=statFee";
			var remark = prompt("请输入备注内容。\n若内容为空或取消输入，则认为你不需要备注。", "");
			parent.form["remark"].value = (remark == null || remark == "" ? "" : remark);
			parent.form["tuition_exists"].value = "";
			parent.form["stdIds"].value = "";
			if (isSelected) {
				addInput(parent.form, "tuitionIds", ids, "hidden");
			} else {
				addInput(parent.form, "tuitionIds", ",", "hidden");
			}
			parent.form.submit();
		}
		
		function reSettingFee() {
			var ids = getSelectIds("tuitionId");
			if (ids == null || ids == "") {
				if (confirm("您要重新设置下面所有学生的学费吗？\n“是”要，“否”不要。")) {
					if (recordSize > toSize) {
						alert("全部重新计算或重新设置的学生记录不能超过" + toSize + "条（包含）。\n\n解决方法：\n1. 把要操作的记录限制在" + toSize + "条之下；\n2. 选择要操作的学生记录后进行。");
						return;
					} else if (confirm("这样会把已经设置的" + recordSize + "位学生学费全部覆盖，要继续吗？")) {
						isSelected = false;
					} else {
						alert("如果不想全部重新设置学生记录，则可以进行选择学生记录后进行该操作。");
						return;
					}
				} else {
					return;
				}
			} else {
				if (confirm("您要重新设置"+countId(ids) +"位学生的学费吗？")) {
					if (confirm("这样会把已经设置的学生学费覆盖，要继续吗？")) {
						isSelected = true;
					} else {
						return;
					}
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
			parent.form["fee"].value = fee;
			parent.form["stdIds"].value = "";
			if (isSelected) {
				addInput(parent.form, "tuitionIds", ids, "hidden");
			} else {
				addInput(parent.form, "tuitionIds", ",", "hidden");
			}
			parent.form.submit();
		}
		
		var listSize = ${tuitions?size};
		var FIXSIZE = 5000;
		function exportData() {
			if (listSize > FIXSIZE) {
				alert("学费导出不能超过" + FIXSIZE + "条记录。");
				return;
			}
			parent.form.action = "tuitionFee.do?method=export";
			addInput(parent.form, "titles", "学号,姓名,学生类别,院系,专业,专业方向,学年度,学期,学费,备注");
			addInput(parent.form, "keys", "std.code,std.name,std.type.code,std.deparment.code,std.firstMajor.code,std.firstAspect.code,calendar.year,calendar.term,fee,remark");
			parent.form["stdIds"].value = "";
			parent.form.submit();
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>
