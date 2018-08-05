<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<@table.table id="studentTable" sortable="true" width="100%">
		<@table.thead>
			<@table.selectAllTd id="stdId"/>
			<@table.sortTd name="std.code" id="student.code"/>
			<@table.sortTd name="attr.personName" id="student.name"/>
			<@table.sortTd name="common.college" id="student.department.name"/>
			<@table.sortTd name="entity.studentType" id="student.stdType.name"/>
			<@table.td name="entity.adminClass"/>
		</@>
		<@table.tbody datas=students; student>
			<@table.selectTd id="stdId" value=student.id/>
			<td><a href="instructorStd.do?method=studentInfo&stdId=${student.id}">${student.code}</a></td>
			<td><@i18nName student/></td>
			<td><@i18nName student.department?if_exists/></td>
			<td><@i18nName student.type?if_exists/></td>
			<td width="25%"><#list student.adminClasses?if_exists?sort_by("code") as adminClass>
		    	<#if adminClass_has_next >
	    		<@i18nName adminClass /><#if (adminClass_index+1)%2==1><br></#if>
	        	<#if (adminClass_index+1)%2==0><br></#if>
	        	<#else>
	        	<@i18nName adminClass /><br>
	        	</#if>
		    </#list>
		    </td>
		</@>
	</@>
	<form method="post" action="" name="actionForm"></form>
	<script>
		var bar = new ToolBar("bar", " 辅导员所带的学生", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("<@msg.message key="action.look"/>", "info()");
		bar.addItem("查看考勤", "recordByStudentId()", "detail.gif");
		bar.addItem("<@msg.message key="field.feeDetail.student"/><@msg.message key="filed.sogType.grade"/>", "stdGrade()", "detail.gif");
		bar.addItem("上课记录", "courseTake()", "detail.gif");
		var menu=bar.addMenu("考试安排","examTable('1')","detail.gif");
        menu.addItem("期末考试","examTable('1')","detail.gif");
        menu.addItem("补缓考试","examTable('2')","detail.gif");
		menu.addItem("补考","examTable('3')","detail.gif");
		menu.addItem("缓考","examTable('4')","detail.gif");
		menu.addItem("平时考试","examTable('5')","detail.gif");
		bar.addItem("查看计划完成情况", "planFinished()", "detail.gif");
		bar.addPrint("<@msg.message key="action.print"/>");
		
		var form = document.actionForm;
		
		function info() {
			form.action = "instructorStd.do?method=studentInfo";
			submitId(form, "stdId", false);
		}
		
		function stdGrade(stdId) {
			submitForm(stdId, "instructorStd.do?method=stdGrade");
		}
		
		function courseTake(stdId) {
			submitForm(stdId, "instructorStd.do?method=courseTake");
		}
		
		function examTable(examTypeId,stdId) {
			submitForm(stdId, "instructorStd.do?method=examTable&examType.id=" + examTypeId);
		}
		
		function planFinished(stdId) {
			submitForm(stdId, "instructorStd.do?method=planFinished");
		}
		
		function recordByStudentId(stdId) {
			submitForm(stdId, "instructorStd.do?method=recordByStudentId");
		}
		
		function submitForm(stdId, actionContents) {
			if (stdId == null || stdId == "") {
				stdId = getSelectId("stdId");
			}
			form.action = actionContents;
			submitId(form, "stdId", false);
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>