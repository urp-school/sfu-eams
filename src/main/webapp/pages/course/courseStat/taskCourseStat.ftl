<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
  	<@table.table width="100%" sortable="true" id="listTable">
  	  <@table.thead>		
  	  	<td><b>院系</b></td>
  		<td><b>课程数</b></td>
        </@>
   	  <@table.tbody datas=courses;course>
   		 <td>${course[0].name}</td>
   		 <td><a href="courseStat.do?method=taskCourseList&teachTask.teachClass.stdType.id=${RequestParameters['teachTask.teachClass.stdType.id']}&teachTask.arrangeInfo.teachDepart.id=${course[0].id}&beginYear=${RequestParameters['beginYear']}&beginTerm=${RequestParameters['beginTerm']}&endYear=${RequestParameters['endYear']}&endTerm=${RequestParameters['endTerm']}&teachTask.arrangeInfo.teachDepart.id=${RequestParameters['teachTask.arrangeInfo.teachDepart.id']}&teachTask.courseType.id=${RequestParameters['teachTask.courseType.id']}">${course[1]}</a></td>
   	  </@>
  	</@>
 </body>
 <#include "/templates/foot.ftl"/>