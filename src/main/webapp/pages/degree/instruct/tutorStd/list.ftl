<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<@table.table width="100%" sortable="true" id="sortTable">
	   	<@table.thead>
	     	<@table.selectAllTd id="stdId"/>
	     	<@table.sortTd id="std.code" name="attr.stdNo"/>
	     	<@table.sortTd id="std.name" name="attr.personName"/>
	     	<@table.sortTd id="std.basicInfo.gender.id" name="entity.gender"/>
	     	<@table.sortTd id="std.enrollYear" name="attr.enrollTurn"/>
	     	<@table.sortTd id="std.type.name" name="entity.studentType"/>
	     	<@table.sortTd id="std.department.name" name="entity.college"/>
	     	<@table.sortTd id="std.firstMajor.name" name="entity.speciality"/>
	     	<@table.sortTd id="std.firstAspect.name" name="entity.specialityAspect"/>
	   	</@>
	   	<@table.tbody datas=students;std>
	    	<@table.selectTd id="stdId" value=std.id/>
	    	<td><A href="#" onclick="stdIdAction('${std.id}')">${std.code}</A></td>
        	<td>${std.name}</td>
        	<td><@i18nName (std.basicInfo.gender)?if_exists/></td>
        	<td>${std.enrollYear}</td>
        	<td><@i18nName (std.type)?if_exists/></td>
        	<td><@i18nName std.department/></td>
        	<td><@i18nName std.firstMajor?if_exists/></td>
			<td><@i18nName std.firstAspect?if_exists/></td> 
	   	</@>
    </@>
	<br><br>
	<form name="actionForm" action="" method="post" onsubmit="return false;"></form>
	<script>
		var bar = new ToolBar("bar", "我的学生", null, true, true);
		bar.addItem("<@msg.message key="info.studentInfo"/>", "stdIdAction()", "detail.gif");
		bar.addItem("<@msg.message key="field.feeDetail.student"/><@msg.message key="filed.sogType.grade"/>", "stdGrade()", "detail.gif");
		bar.addItem("<@msg.message key="field.feeDetail.student"/>上课记录", "courseTake()", "detail.gif");
		bar.addItem("查看计划完成情况", "planFinished()", "detail.gif");
		
		var form = document.actionForm;
		<#--查看学生信息-->
		function stdIdAction(stdId) {
			submitForm(stdId, "studentDetailByManager.do?method=detail");
		}
		<#--查看学生的成绩-->
		function stdGrade(stdId) {
			submitForm(stdId, "tutorStd.do?method=stdGrade");
		}
		<#--查看学生的上课记录-->
		function courseTake(stdId) {
			submitForm(stdId, "tutorStd.do?method=courseTake");
		}
		<#--查看学生培养计划完成情况-->
		function planFinished(stdId) {
			submitForm(stdId, "tutorStd.do?method=planFinished");
		}		
		<#--判断是否选择了学生-->
		function isNotEmptyStdId(stdId) {
			if (stdId == null || stdId == "") {
				alert("请选择要查看的学生！");
				return false;
			} else if (stdId.indexOf(',') >= 0) {
				alert("请选择一个要查看的学生！");
				return false;
			}
			return true;
		}
		
		function submitForm(stdId, actionContents) {
			if (stdId == null || stdId == "") {
				stdId = getSelectId("stdId");
			}
			if (isNotEmptyStdId(stdId)) {
				form.action = actionContents;
				addInput(form, "stdId", stdId, "hidden");
				form.submit();
			}
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>