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
<table id="bar"></table>
		<form action="attendReportWithSchool.do" name="codeForm" method="post" onsubmit="return false;">
	<@table.table id="studentTable" sortable="true" width="100%">
		<@table.thead>
			<@table.selectAllTd id="stdId"/>
			<@table.sortTd name="std.code" id="student.code"/>
			<@table.sortTd name="attr.personName" id="student.name"/>
			<@table.sortTd name="common.college" id="student.department.name"/>
			<@table.sortTd name="entity.studentType" id="student.stdType.name"/>
			<@table.td name="entity.adminClass"/>
		</@>
		<#if students??>
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
		</#if>
	</@>
	<#assign filterKeys = ["calendarId"]/>
        <input type="hidden" name="calendarId" value="${RequestParameters["calendarId"]}"/>
        
	</form>
	<script>
		var bar = new ToolBar("bar", " 所带的学生", null, true, true);
		bar.addItem('查看考勤报表','reportStatic()');
		bar.setMessage('<@getMessage/>');
		
	
			
		function reportStatic(form){
			var form=document.codeForm;
			var id = getSelectIds("stdId");
       		if(id=="") {alert("请选择一个学生");return;}
       		if(isMultiId(id)) {alert("<@bean.message key="common.singleSelectPlease" />。");return;}
			form.action="attendReportWithStudent.do?method=setReportDate&stuIds="+id;
			form.target="contentFrame";
			form.submit();
		}
		
		function showStat(form){
			var stuIds = getSelectIds("stdId");
       		if(stuIds=="") {alert("请选择一个学生");return;}
       		if(isMultiId(stuIds)) {alert("<@bean.message key="common.singleSelectPlease" />。");return;}
       		form.action = "attendReportWithStudent.do?method=showReport&stuIds="+stuIds;
       		form.target = "contentFrame";
			form.submit();
		}
	
		
		
		
		
	</script>
<#include "/templates/foot.ftl"/>