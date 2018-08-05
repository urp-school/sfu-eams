<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<#if (courseTakes?size!=0)>
	<div align="center"><@msg.message key="attr.stdNo"/>:${std.code} <@msg.message key="attr.personName"/>:${std.name} <@msg.message key="entity.department"/>:<@i18nName std.department?if_exists/> <@msg.message key="entity.speciality"/>:<@i18nName std.firstMajor?if_exists/> <@msg.message key="entity.specialityAspect"/>:<@i18nName std.firstAspect?if_exists/></div>
	</#if>
	<@table.table width="100%" sortable="true" id="courseTake">
		<@table.thead>
		   	<@table.sortTd id="courseTake.task.seqNo" name="attr.taskNo" width="8%"/>
		   	<@table.sortTd id="courseTake.task.course.name" name="attr.courseName" width="20%"/>
		   	<@table.sortTd id="courseTake.task.courseType.name" name="entity.courseType" width="13%"/>
		   	<@table.td name="task.arrangeInfo"/>
		   	<@table.sortTd id="courseTake.task.course.credits" name="attr.credit" width="5%"/>
		   	<@table.sortTd id="courseTake.task.calendar.year" name="attr.year2year"/>
		   	<@table.sortTd id="courseTake.task.calendar.term" name="field.teacherEvaluate.term"/>
		   	<@table.td text="修读类别"/>
		</@>
	  	<@table.tbody datas=courseTakes;courseTake>
		    <td>${courseTake.task.seqNo}</td>
		    <td><@i18nName courseTake.task.course/></td>
		    <td><@i18nName courseTake.task.courseType/></td>
		    <td align="left">${courseTake.task.arrangeInfo.digest(courseTake.task.calendar,Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],":teacher2 :day :units :room")}</td>
		    <td>${courseTake.task.course.credits}</td>
		    <td>${courseTake.task.calendar.year}</td>
		    <td>${courseTake.task.calendar.term}</td>
		    <#-- 3和4是重修和免修不免试id -->
		    <td <#if courseTake.courseTakeType.id?string='4' || courseTake.courseTakeType.id?string='3'>style="color:red"</#if>><@i18nName courseTake.courseTakeType/></td>
		</@>
	</@>
	<script>
		var bar = new ToolBar("bar", "${std.name}学生的上课记录", null, true, true);
		bar.addPrint("<@msg.message key="action.print"/>");
		bar.addBack("<@msg.message key="action.back"/>");
	</script>
<#include "/templates/foot.ftl"/>