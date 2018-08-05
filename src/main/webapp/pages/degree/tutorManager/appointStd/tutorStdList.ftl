<#include "/templates/head.ftl"/>
<body >
    <table id="appointStd"></table>
    <script> 

    var bar = new ToolBar("appointStd","带学生查询",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("取消指定",'cannelAppointStd(document.listForm)');  
    </script>
     <@table.table width="100%" align="center" sortable="true" id="listTable">
	<@table.thead>
		<@table.selectAllTd id="stdId"/>
		<@table.sortTd id="student.code" name="attr.stdNo"/>
		<@table.sortTd id="student.name" name="attr.personName"/>
		<@table.sortTd id="student.enrollYear" name="filed.enrollYearAndSequence"/>
		<@table.sortTd id="student.type.name" name="entity.studentType"/>
		<@table.sortTd id="student.department.name" name="entity.college"/>
		<@table.sortTd id="student.teacher.name" text="导师姓名"/>
		<td>班级名称</td>
	</@>
	<@table.tbody datas=stdPage; student>
		<@table.selectTd id="stdId" value=student.id/>
		<td>${student.code}</td>
		<td><a href="studentDetailByManager.do?method=detail&stdId=${student.id}"><@i18nName student?if_exists/></a></td>
		<td>${student.enrollYear}</td>
		<td><@i18nName student.type?if_exists/></td>
		<td><@i18nName student.department?if_exists/></td>
		<td><@i18nName student.teacher?if_exists/></td>
		<td>
		<#list student.adminClasses?if_exists as adminClass>
			${adminClass.name}<#if adminClass_has_next>,</#if>
		</#list>
		</td>
	</@>
</@>
<form name="listForm" method="post" action="" onsubmit="return false;">
</form>
</body>     
 <script>
    var form = document.listForm;
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("stdId")));
    }
orderBy =function(what){
		parent.search(1,${pageSize?default(20)},what);
	}
	function pageGoWithSize(pageNo,pageSize){
       parent.search(pageNo,pageSize,'${RequestParameters['orderBy']?default('null')}');
    }
	function cannelAppointStd(form){
	   var ids = getSelectIds("stdId");
       	if (ids == null || ids == "") {
       		alert("请先选择学生!");
       		return;
       	}
       form.action="appointStd.do?method=unSetStdtoTutor&stdIds=" + ids;
       form.target="_parent";
       form.submit(); 
	}   
 </script>        
<#include "/templates/foot.ftl"/>