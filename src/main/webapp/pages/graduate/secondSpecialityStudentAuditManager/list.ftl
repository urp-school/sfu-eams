<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<@table.table align="center" sortable="true" id="studentsId" width="100%">
		<@table.thead>
			<@table.selectAllTd id="stdId"/>
			<@table.sortTd  id="std.name" name="attr.personName"/>
			<@table.sortTd  id="std.code" name="attr.stdNo"/>
			<@table.sortTd  id="std.secondMajor.name" text="双专业"/>
			<@table.sortTd  id="std.secondAspect.name" text="双专业方向"/>
			<@table.sortTd  id="std.enrollYear" name="filed.enrollYearAndSequence"/>
			<@table.sortTd  id="std.secondGraduateAuditStatus" name="common.status"/>
		</@>
		<@table.tbody datas=studentList;student>
			<@table.selectTd id="stdId" value=student.id/>
		    <td><a href="studentDetailByManager.do?method=detail&stdId=${student.id}"><@i18nName student?if_exists/></a></td>
		    <td>&nbsp;${student.code}</td>
		    <td>&nbsp;<@i18nName student.secondMajor?if_exists/></td>
		    <td>&nbsp;<@i18nName student.secondAspect?if_exists/></td>
		    <td align="center">${student.enrollYear}</td>
		    <td>
		    <a href="studentAuditOperation.do?method=detail&stdId=${student.id}&majorTypeId=2" title="查看培养计划完成情况">
		    <#if student.secondGraduateAuditStatus?exists&&(student.secondGraduateAuditStatus?string=="true")>
		    	<@bean.message key="attr.graduate.outsideExam.auditPass"/>
		    <#elseif student.secondGraduateAuditStatus?exists&&(student.secondGraduateAuditStatus?string=="false")>
		    	<font color="red"><@bean.message key="attr.graduate.outsideExam.noAuditPass"/></font>
		    <#else>
		    	<@bean.message key="attr.graduate.outsideExam.nullAuditPass"/>
		    </#if>
		    </a>
		    </td>
		</@>
	</@>
	<form method="post" action="" name="actionForm">
		<input type="hidden" name="actionPath" value="secondSpecialityStudentAuditManager"/>
		<input type="hidden" name="auditStandardId" value="${RequestParameters['auditStandardId']}"/>
		<input type="hidden" name="auditTerm" value="${RequestParameters['auditTerm']?default('')}"/>
  	</form>
	<script>
		var bar = new ToolBar("bar", "<@bean.message key="std.stdList"/>", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem('自动审核选中学生', 'batchAutoAudit()');
		bar.addItem('审核通过选中学生', 'batchAudit()');
		bar.addItem('撤销通过选中学生', 'batchDisAudit()');
		bar.addItem('批量审核条件内学生', 'batchAuditWithCondition()');
		
		<#include "graduateListScript.ftl"/>
	</script>
</body>
<#include "/templates/foot.ftl"/>