<#include "/templates/head.ftl"/>
<body>
<div align="center"><h3>毕业实习带队教师一览表</h3></div>
<@table.table width="100%">
	<form name="listForm" method="post" action="" onsubmit="return false;">
		<input type="hidden" name="practice.student.type.id" value="${RequestParameters['practice.student.type.id']?if_exists}">
		<input type="hidden" name="practice.teachCalendar.year" value="${RequestParameters['practice.teachCalendar.year']?if_exists}">
		<input type="hidden" name="practice.teachCalendar.term" value="${RequestParameters['practice.teachCalendar.term']?if_exists}">
		<tr class="" onKeyDown="javascript:enterQuery()">
			<@table.td text="<input name='practice.teacher.department.name' style='width:100%'>"/>
			<@table.td text="<input name='practice.teacher.name' style='width:100%'>"/>
			<@table.td text="<input name='practice.teacher.teacherType.name' style='width:100%'>"/>
			<@table.td text="" />
		</tr>	
	</form>
	<@table.thead>
		<@table.sortTd text="所在院系" id="practice.teacher.department.name"/>
		<@table.sortTd text="姓名" id="practice.teacher.name"/>
		<@table.sortTd text="职称" id="practice.teacher.teacherType.name"/>
		<@table.td text="带学生人数"/>
	</@>
	<@table.tbody teacherStats;stat>
		<td>${(stat.what.department.name)?default('')}</td>
		<td>${(stat.what.name)?default('')}</td>
		<td><@i18nName (stat.what.title)?if_exists/></td>
		<td>${stat.countors[0]}</td>
	</@>
</@>

<script>
var form = document.listForm;
orderBy =function(what){
	pageGo(1,${pageSize},what);
}
function pageGo(pageNumeber,pageSize,orderBy){
   goToPage(form,pageNo,pageSize,orderBy);
}
 function enterQuery() {
       if (window.event.keyCode == 13)query();
    }
    function query(){
        form.submit();
    }
</script>
</body>
<#include "/templates/foot.ftl"/>