<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<@table.table id="adminClassTable" sortable="true" width="100%">
		<@table.thead>
			<@table.selectAllTd id="adminClassId"/>
			<@table.sortTd name="std.adminClass.baseInfo.code" id="adminClass.code"/>
			<@table.sortTd name="std.adminClass.baseInfo.name" id="adminClass.name"/>
			<@table.sortTd name="common.college" id="adminClass.department.name"/>
			<@table.sortTd name="entity.studentType" id="adminClass.stdType.name"/>
			<@table.td text="计划/在校/学籍有效"/>
		</@>
		<@table.tbody datas=adminClasses; adminClass>
			<@table.selectTd id="adminClassId" value=adminClass.id/>
			<td><a href="instructorStd.do?method=adminClassInfo&adminClassId=${adminClass.id}">${adminClass.code}</a></td>
			<td><@i18nName adminClass/></td>
			<td><@i18nName adminClass.department/></td>
			<td><@i18nName adminClass.stdType/></td>
			<td>${(adminClass.planStdCount)?default(0)}/${(adminClass.actualStdCount)?default(0)}/${(adminClass.stdCount)?default(0)}</td>
		</@>
	</@>
	<form method="post" action="" name="actionForm">
	<input type="hidden" name="majorTypeId" value="${RequestParameters['majorTypeId']?default("1")}"/>
	</form>
	<script>
		var bar = new ToolBar("bar", " 辅导员所带的班级", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("查看班级学生", "searchStudentByAdminClass()");
		bar.addItem("行政班成绩表", "printMultiStdGrade()");
		bar.addItem("打印班级绩点", "printMultiStdGP()");
		bar.addItem("<@msg.message key="action.look"/>", "info()");
		
		var form = document.actionForm;
		
		function info() {
			form.action = "instructorStd.do?method=adminClassInfo";
			form.target = "_self";
			submitId(form, "adminClassId", false);
		}
		
		function printMultiStdGP() {
			form.action = "instructorStd.do?method=printMultiStdGP";
			form.target = "_blank";
			submitId(form, "adminClassId", true);
		}
		function printMultiStdGrade(){
       		form.action = "instructorStd.do?method=selectCalendar";
       		form.target = "_blank";
       		submitId(form, "adminClassId", false);
        }
        function searchStudentByAdminClass(){
       		form.action = "instructorStd.do?method=searchStudentByAdminClass";
       		form.target = "_blank";
       		submitId(form, "adminClassId", false);
        }
	</script>
</body>
<#include "/templates/foot.ftl"/>