<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	 <@table.table width="100%" sortable="true" id="sortTable">
		<@table.thead>
			<@table.selectAllTd id="stdId"/>
			<@table.sortTd id="std.code" name="attr.stdNo" width="10%"/>
			<@table.sortTd id="std.name" name="attr.personName" width="14%"/>
			<@table.sortTd id="std.department.name" name="entity.college" width="20%"/>
			<@table.sortTd id="std.firstMajor.name" name="entity.speciality"/>
			<@table.sortTd id="std.enrollYear" name="filed.enrollYearAndSequence" width="15%"/>
			<@table.sortTd id="std.graduateAuditStatus" name="common.status" width="12%"/>
		</@>
		<@table.tbody datas=studentList; student>
			<@table.selectTd id="stdId" value=student.id/>
		    <td>${student.code}</td>
		    <td><a href="studentDetailByManager.do?method=detail&stdId=${student.id}"><@i18nName student?if_exists/></a></td>
		    <td><@i18nName student.department?if_exists/></td>
		    <td><@i18nName student.firstMajor?if_exists/></td>
		    <td align="center">${student.enrollYear}</td>
		    <td>
			    <a href="javascript:showDetail(${student.id});" title="查看培养计划完成情况">
				    <#if resultMap[student.id?string].graduateAuditStatus?exists&&(resultMap[student.id?string].graduateAuditStatus?string=="true")>
				    	<@bean.message key="attr.graduate.outsideExam.auditPass"/>
				    <#elseif resultMap[student.id?string].graduateAuditStatus?exists&&(resultMap[student.id?string].graduateAuditStatus?string=="false")>
				    	<font color="red"><@bean.message key="attr.graduate.outsideExam.noAuditPass"/></font>
				    <#else>
				    	<@bean.message key="attr.graduate.outsideExam.nullAuditPass"/>
				    </#if>
			    </a>
		    </td>
		</@>
	</@>
	<form method="post" action="" name="actionForm">
		<input type="hidden" name="actionPath" value="studentAuditManager"/>
		<input type="hidden" name="auditStandardId" value="${RequestParameters['auditStandardId']}"/>
		<input type="hidden" name="auditTerm" value="${RequestParameters['auditTerm']?default('')}"/>
	</form>
	<script>
		var bar = new ToolBar("bar", "<@bean.message key="std.stdList"/>", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem('导出','exportData()');
		//var menu = bar.addMenu('确认状态',null);
   		//menu.addItem('审核确认',"batchAuditAffirm('true')");
		//menu.addItem('取消确认',"batchAuditAffirm('false')");
		bar.addItem('审核选中学生', 'batchAutoAudit()');
		bar.addItem('审核通过选中学生', 'batchAudit()');
		bar.addItem('撤销通过选中学生', 'batchDisAudit()');
		bar.addItem('批量审核条件内学生', 'batchAuditWithCondition()');
		
		<#include "graduateListScript.ftl"/>
	</script>
</body>
<#include "/templates/foot.ftl"/>