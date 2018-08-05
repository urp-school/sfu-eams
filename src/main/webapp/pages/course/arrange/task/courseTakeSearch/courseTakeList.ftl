<@table.table id="listTable" sortable="true" width="100%">
	  <@table.thead>
	   <@table.selectAllTd id="courseTakeId"/>
	   <@table.sortTd id="student.code" name="std.code"/>
	   <@table.sortTd id="courseTake.student.name" name="attr.personName"/>
	   <@table.sortTd id="courseTake.task.seqNo" name="attr.taskNo"/>
	   <@table.sortTd id="courseTake.task.course.code" name="attr.courseNo"/>
	   <@table.sortTd id="courseTake.task.course.name" name="attr.courseName"/>
	   <@table.sortTd id="courseTake.task.courseType.name" name="entity.courseType"/>
	   <@table.td name="entity.adminClass"/>
	   <@table.td name="task.arrangeInfo"/>
	   <@table.sortTd id="courseTake.task.course.credits" name="attr.credit" width="5%"/>
	   <@table.td text="修读类别" width="8%"/>
	  </@>
	  <@table.tbody datas=courseTakes;take>
	    <@table.selectTd id="courseTakeId" type="checkbox" value=take.id/>
	    <td>${take.student.code}</td>
	    <td>${take.student.name?if_exists}</td>
	    <td><A href="teachTaskSearch.do?method=info&taskId=${take.task.course.id}">${take.task.seqNo}</A></td>
	    <td><A href="javascript:courseInfo('id', ${take.task.id})">${take.task.course.code}</A></td>
	    <td><@i18nName take.task.course/></td>
	    <td><@i18nName take.task.courseType/></td>
	    <td><@i18nName (take.student.firstMajorClass)?if_exists/></td>
	    <td align="left">${take.task.arrangeInfo.digest(take.task.calendar,Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],":teacher2 :day :units :room")}</td>
	    <td>${take.task.course.credits}</td>
	    <#-- 3和4是重修和免修不免试id -->
	    <td <#if take.courseTakeType.id?string=='4' ||take.courseTakeType.id?string=='3'> style="color:red"</#if>><@i18nName take.courseTakeType/></td>
	  </@>
  </@>
