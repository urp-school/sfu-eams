<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	学生选课学分汇总
	<@table.table width="100%" align="center">
		<@table.thead>
			<@table.td name="attr.stdNo" width="10%"/>
			<@table.td name="attr.personName" width="14%"/>
			<@table.td name="entity.college" width="20%"/>
			<@table.td name="entity.speciality"/>
			<td width="15%">选课学年学期</td>
			<td width="15%">总学分</td>
		</@>
		<#assign total=0 />
		<@table.tbody datas=stdCreditTotalList; stdCreditTotal>
		    <td>${stdCreditTotal[0].code}</td>
		    <td><a href="studentDetailByManager.do?method=detail&stdId=${stdCreditTotal[0].id}"><@i18nName stdCreditTotal[0]?if_exists/></a></td>
		    <td><@i18nName stdCreditTotal[0].department?if_exists/></td>
		    <td><@i18nName stdCreditTotal[0].firstMajor?if_exists/></td>
		    <td align="center">${stdCreditTotal[1].year}&nbsp;${stdCreditTotal[1].term}</td>
		    <td align="center">${stdCreditTotal[2]}</td>
		    <#assign total = total + stdCreditTotal[2] />
		</@>
	</@>
	选课总学分: ${total} <br>
	学生选课明细
	<@table.table id="listTable" width="100%">
		<@table.thead>
		   <td>学年学期</td>
		   <@table.sortTd id="courseTake.task.seqNo" name="attr.taskNo"/>
		   <@table.sortTd id="courseTake.task.course.code" name="attr.courseNo"/>
		   <@table.sortTd id="courseTake.task.course.name" name="attr.courseName"/>
		   <@table.sortTd id="courseTake.task.courseType.name" name="entity.courseType"/>
		   <@table.sortTd id="courseTake.task.credit" name="attr.credit" width="5%"/>
		   <@table.td text="修读类别"/>
		  </@>
		  <@table.tbody datas=courseTakeList?sort_by(["task","calendar","start"]);take>
			<td align="center">${take.task.calendar.year}&nbsp;${take.task.calendar.term}</td>
		    <td>${take.task.seqNo}</td>
		    <td>${take.task.course.code}</td>
		    <td><@i18nName take.task.course/></td>
		    <td><@i18nName take.task.courseType/></td>
		    <td>${take.task.course.credits}</td>
		    <#-- 3和4是重修和免修不免试id -->
		    <td <#if take.courseTakeType.id?string=='4' ||take.courseTakeType.id?string=='3' > style="color:red"</#if>><@i18nName take.courseTakeType/></td>
		  </@>
	</@>

	<script>
		var bar = new ToolBar("bar", "学生选课学分汇总与选课明细", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addBack();
		
		
	</script>
</body>
<#include "/templates/foot.ftl"/>