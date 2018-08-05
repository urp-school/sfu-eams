<#include "/templates/head.ftl"/>
<table id="bar"></table>
	
	<@table.table id="studentTable" sortable="true" width="100%">
		<@table.thead>
			<@table.selectAllTd id="stdId" width="5%"/>
			<@table.sortTd text="学号" id="student.code" width="10%"/>
			<@table.sortTd text="姓名" id="student.name" width="10%"/>
			<@table.sortTd text="班级" id="student.department.name" width="20%"/>
			<@table.sortTd text="辅导员" id="student.stdType.name" width="10%"/>
			<@table.sortTd text="考勤年月" id="student.stdType.name" width="15%"/>
			<@table.sortTd text="月缺勤率" id="student.stdType.name" width="10%"/>
			<@table.sortTd text="学期缺勤率" id="student.stdType.name" width="10%"/>
		</@>
		<@table.tbody datas=attendStaticReports; attendStaticReport>
			<form method="post" action="" name="stuForm" id="stuForm">
			<@table.selectTd id="stdId" value=attendStaticReport.id/>
			<td>${attendStaticReport.student.code!}</td>
			<td>${attendStaticReport.student.name!}</td>
			<td>
				<#list attendStaticReport.student.adminClasses?if_exists?sort_by("code") as adminClass>
					${adminClass.name}
				</#list>
			</td>
		    <td>
		    	${(attendStaticReport.teacher.name)!}
			</td>
		    <td>${attendStaticReport.attendYear!}年-${attendStaticReport.attendMonth!}月</td>
		    <td>${attendStaticReport.monthAbesence!}</td>
		    <td>${attendStaticReport.termAbesence!}</td>
		    </form>
		</@>
		<form method="post" action=""  name="actionForm" >
		</form>
	</@>
	<script>
		var bar = new ToolBar("bar", "学生考勤报表", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem('统计','reportStatic()');
	
		function reportStatic(){
			var form=document.actionForm;
			form.action="attendReportFunc.do?method=selectTerm";
			form.submit();
		}
		
		
		
		
	</script>
<#include "/templates/foot.ftl"/>