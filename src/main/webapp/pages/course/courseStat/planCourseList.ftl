<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
  	<@table.table width="100%" sortable="true" id="listTable">
  	  <@table.thead>
  	    <@table.selectAllTd id="planCourse"/>
  	    <@table.sortTd id="planCourse.teachDepart.id" name="attr.teachDepart"/>
  	  	<@table.sortTd id="planCourse.course.code" name="attr.courseNo"/>
  		<@table.sortTd id="planCourse.course.name" name="attr.courseName"/>
  		<@table.sortTd id="planCourse.course.credits" name="attr.credit"/>
  		<@table.sortTd id="planCourse.course.weekHour" text="周课时"/>
        <@table.sortTd id="planCourse.course.extInfo.period" text="学时"/>
      </@>
   	  <@table.tbody datas=planCourses;planCourse>
   	     <@table.selectTd id="planCourse" value="${planCourse[0].id},${planCourse[0].id}"/>
   		 <td>${planCourse[0].name}</td>
   		 <td>${planCourse[1].code}</td>
   		 <td>${planCourse[1].name}</td>
   		 <td>${planCourse[2]}</td>
   		 <td>${planCourse[3]}</td>
   		 <td>${planCourse[4]}</td>
   	  </@>
  	</@>
 </body>
 <#include "/templates/foot.ftl"/>