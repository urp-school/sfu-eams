<#include "/templates/head.ftl"/>
<BODY>
	<table id="bar" width="100%"></table>
	<@table.table align="center" sortable="true" id="studentsId">
		<@table.thead>
			<@table.selectAllTd id="stdId"/>
			<@table.sortTd name="attr.stdNo" id="student.code"/>
			<@table.sortTd name="attr.personName" id="student.name"/>
			<@table.sortTd name="filed.enrollYearAndSequence" id="student.enrollYear"/>
			<@table.sortTd text="双专业" id="student.secondMajor.name"/>
			<@table.sortTd text="双专业方向" id="student.secondAspect.name"/>
			<@table.sortTd text="双专业是否就读" id="student.isSecondMajorStudy.name"/>
			<@table.sortTd text="双专业是否写论文" id="student.isSecondMajorThesisNeed.name"/>
			<@table.td text="班级"/>
			<@table.sortTd text="学籍状态" id="student.state.name"/>
			<@table.sortTd text="学籍有效性" id="student.active"/>
			<@table.sortTd name="common.status" id="student.secondGraduateAuditStatus"/>
		</@>
		<@table.tbody datas=studentList;student>
			<@table.selectTd id="stdId" value=student.id/>
		    <td>${student.code}</td>
		    <td width="8%"><a href="studentDetailByManager.do?method=detail&stdId=${student.id}"><@i18nName student?if_exists/></a></td>
		    <td>${student.enrollYear}</td>
		    <td><@i18nName student.secondMajor?if_exists/></td>
		    <td><@i18nName student.secondAspect?if_exists/></td>
		    <td><#if student.isSecondMajorStudy?exists>${student.isSecondMajorStudy?string("在读","不在读")}</#if></td>
		    <td><#if student.isSecondMajorThesisNeed?exists>${student.isSecondMajorThesisNeed?string("需要","不需要")}</#if></td>
		    <td><#if is2ndSpeciality?exists><#list student.getAdminClassSet(is2ndSpeciality)?if_exists as adminClass><@i18nName adminClass/></#list><#else><#list student.getAdminClassSet(is2ndSpeciality)?if_exists as adminClass><@i18nName adminClass/></#list></#if></td>
		    <td><@i18nName student.state?if_exists/></td>
		    <td>${student.isValid?string("有效","无效")}</td>
		    <td align="center" width="11%">
			    <a href="javascript:showDetail(${student.id});" title="查看培养计划完成情况">
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
	<@htm.actionForm name="actionForm" action="secondAuditResultSearch.do" entity="std"></@>
	<script>
		var bar = new ToolBar("bar", "双专业培养计划完成情况", null, true, true);
		bar.setMessage('<@getMessage/>');
		var menu = bar.addMenu("未完成课程", null, "list.gif");
		menu.addItem("全部学期", "maintainAll()");
		menu.addItem("指定学期", "maintain()");
		
		function maintainAll() {
			form.action = "secondAuditResultSearch.do?method=batchDetail";
			addInput(form, "termsShow", "true", "hidden");
			submitId(form, "stdId", true);
		}
		
		function maintain() {
			form.action = "secondAuditResultSearch.do?method=batchDetail";
			addInput(form, "termsShow", "true", "hidden");
			addInput(form, "calendar.studentType.id", DWRUtil.getValue(parent.$('stdType')), "hidden");
			addInput(form, "calendar.year", DWRUtil.getValue(parent.$('year')), "hidden");
			addInput(form, "calendar.term", DWRUtil.getValue(parent.$('term')), "hidden");
			addInput(form, "omitSmallTerm", DWRUtil.getValue(parent.$('omitSmallTerm')), "hidden");
			submitId(form, "stdId", true);
		}
	
	    function showDetail(studentId,auditStandardId,auditTerm){
	    	window.open('secondAuditResultSearch.do?method=detail&student.id=' + studentId + '&showBackward=false');
	    }
	    
	</script>
</body>
<#include "/templates/foot.ftl"/>