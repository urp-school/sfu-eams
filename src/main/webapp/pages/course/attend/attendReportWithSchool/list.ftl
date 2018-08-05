<#include "/templates/head.ftl"/>
 <style  type="text/css">
#doAddDiv{
position: absolute; 
z-index: 9999; 
width: 300; 
height: 200; 
left:50%; 
display:none; 
background-color:white;
}
</style>
<script language="JavaScript" type="text/JavaScript" src="scripts/My97DatePicker/WdatePicker.js"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/My97DatePicker/config.js"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/My97DatePicker/calendar.js"></script>
		<form action="attendReportWithSchool.do" name="codeForm" method="post" onsubmit="return false;">
<table id="bar"></table>
	
	<@table.table id="departmentTable" sortable="true" width="100%">
		<@table.thead>
			<@table.selectAllTd id="stdId" width="5%"/>
			<@table.sortTd text="代码" id="department.code" width="10%"/>
			<@table.sortTd text="名称" id="department.name" width="10%"/>
			<@table.sortTd text="英文名" id="department.engName" width="20%"/>
			<@table.sortTd text="简称" id="department.abbreviation" width="10%"/>
			<@table.sortTd text="是否开课" id="department.isTeaching" width="15%"/>
			<@table.sortTd text="是否院系" id="department.isCollege" width="10%"/>
		</@>
		<@table.tbody datas=departments; department>
			<form method="post" action="" name="stuForm" id="stuForm">
			<@table.selectTd id="stdId" value=department.id/>
			<td>${department.code!}</td>
			<td>${department.name!}</td>
			<td>${department.engName!}</td>
			<td>${department.abbreviation!}</td>
			<td>
				<#if department.isTeaching>
				是
				<#else>
				否
				</#if>
			</td>
		    <td>
			    <#if department.isCollege>
				是
				<#else>
				否
				</#if>
			</td>
		    </form>
		</@>
		</form>
	</@>
	<script>
		var bar = new ToolBar("bar", "院系列表", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem('查看考勤报表','reportStatic()');
			
		function reportStatic(form){
			var form=document.codeForm;
			form.action="attendReportWithSchool.do?method=setReportDate";
			form.target="contentFrame";
			form.submit();
		}
		
		function showStat(form){
			form.action="attendReportWithSchool.do?method=showReport";
			form.target="contentFrame";
			form.submit();
		}
	</script>
<#include "/templates/foot.ftl"/>