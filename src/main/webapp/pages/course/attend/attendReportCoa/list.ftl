<#include "/templates/head.ftl"/>
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
	<#assign filterKeys = ["calendarId"]/>
	<form method="post" action="" name="tempForm">
        <input type="hidden" name="calendarId" value="${RequestParameters["calendarId"]}"/>
        <#--下面几个教学日历的参数是给下面showDetial()方法使用，跟其它的方法不冲突-->
        <#list RequestParameters?keys as key>
            <#if key?starts_with("calendar") && !filterKeys?seq_contains(key)>
        <input type="hidden" name="${key}" value="${RequestParameters[key]}"/>
            </#if>
        </#list>
        <input type="hidden" name="stuIds" value=""/>
	</form>
	<script>
		var bar = new ToolBar("bar", " 辅导员所带的学生", null, true, true);
		bar.addItem('查看出勤情况','showReport()');
		bar.addItem('查看学生考勤明细','showDetial()');
		bar.setMessage('<@getMessage/>');
		
		var form=document.tempForm;
		
		function initData() {
            form["stuIds"].value = "";
		}
	
		function showReport(){
            initData();
			var stuIds = getSelectIds("stdId");
       		if(stuIds=="") {alert("<@bean.message key="prompt.task.selector" />");return;}
       		form.action = "attendReportCoa.do?method=showReport";
       		form.target = "_blank";
       		form["stuIds"].value = stuIds;
       		form.submit();
		}
		
		function showDetial(){
			initData();
			form.action = "attendReportCoa.do?method=showDetailList";
            form.target = "_blank";
			form.submit();
		}
		
		
		
	</script>
<#include "/templates/foot.ftl"/>