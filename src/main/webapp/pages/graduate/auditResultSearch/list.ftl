<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<BODY>
	<table id="bar" width="100%"></table>
	<@table.table id="auditResult" width="100%" sortable="true">
		<@table.thead>
			<@table.selectAllTd id="stdId"/>
			<@table.sortTd name="attr.stdNo" id="student.code" width="10%"/>
			<@table.sortTd name="attr.personName" id="student.name" width="10%"/>
			<@table.sortTd name="entity.college" id="student.department.name"/>
			<@table.sortTd name="entity.speciality" id="student.firstMajor"/>
			<@table.sortTd name="filed.enrollYearAndSequence" id="student.enrollYear" width="12%"/>
			<@table.sortTd name="common.status" id="student.graduateAuditStatus" width="15%"/>
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
	<@htm.actionForm name="actionForm" action="auditResultSearch.do" entity="std"></@>
	<script>
		var bar = new ToolBar("bar", "培养计划完成情况", null, true, true);
		bar.setMessage('<@getMessage/>');
		var menu = bar.addMenu("未完成课程",null,'list.gif');
		menu.addItem("全部学期","maintainAll();");
		menu.addItem("指定学期","maintain();");
		
		function maintainAll() {
			form.action = "auditResultSearch.do?method=batchDetail";
			addInput(form, "termsShow", "true", "hidden");
			submitId(form, "stdId", true);
		}
		
		function maintain() {
			form.action = "auditResultSearch.do?method=batchDetail";
			addInput(form, "termsShow", "true", "hidden");
			addInput(form, "calendar.year", DWRUtil.getValue(parent.$('year')), "hidden");
			addInput(form, "calendar.term", DWRUtil.getValue(parent.$('term')), "hidden");
			addInput(form, "omitSmallTerm", DWRUtil.getValue(parent.$('omitSmallTerm')), "hidden");
			submitId(form, "stdId", true);
		}
	    
	    function showDetail(studentId,auditStandardId,auditTerm){
	    	window.open('auditResultSearch.do?method=detail&student.id='+studentId+'&showBackward=false');
	    }
	    
</script>
</body>
<#include "/templates/foot.ftl"/>