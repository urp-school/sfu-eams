<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" >
  <table id="bar"></table>
  <center>
 	<table class="listTable" width="60%">
 		<tr>
 			<td width="50%"><@msg.message key="attr.stdNo"/></td>
 			<td>${std.code}</td>
 		</tr>
 		<tr>
 			<td><@msg.message key="attr.personName"/></td>
 			<td><@i18nName std/></td>
 		</tr>
 		<tr>
 			<td><@msg.message key="entity.adminClass"/></td>
 			<td><@i18nName std.firstMajorClass?if_exists/></td>
 		</tr>
 		<tr>
 			<td><@msg.message key="entity.specialityAspect"/></td>
 			<td><@i18nName std.firstAspect?if_exists/></td>
 		</tr>
 	</table>
 	<hr>
 	<@table.table width="90%">
 	  <@table.thead>
 	    <@table.td name="attr.taskNo"/>
 	    <@table.td name="attr.courseNo"/>
 	    <@table.td name="attr.courseName"/>
 	    <@table.td text="学年度"/>
 	    <@table.td text="学期"/>
 	    <@table.td text="成绩"/>
 	  </@>
 	  <@table.tbody datas=courseGrades?sort_by(["calendar","start"]);grade>
	 		<td>${grade.taskSeqNo?if_exists}</td>
	 		<td>${grade.course.code}</td>
	 		<td align="left"><@i18nName grade.course/></td>
	 		<td>${grade.calendar.year}</td>
	 		<td>${grade.calendar.term}</td>
	 		<td>${grade.scoreDisplay}</td>
 	  </@>
    </@>
 	</center>
 	<script>
		var bar = new ToolBar("bar","学生个人成绩单",null,true,true);
	    bar.addPrint("<@msg.message key="action.print"/>");
	    bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
 	</script>
 </body>
<#include "/templates/foot.ftl"/>