<#include "/pages/course/grade/gp/stdGP/stdGradeStat.ftl"/>
<@table.table id="gradeTable" align="center" width="90%">
  	<@table.thead>
	   	<@table.td name="attr.courseNo"/>
	   	<@table.td name="attr.taskNo"/>
	   	<@table.td name="attr.courseName"/>
	   	<@table.td name="entity.courseType"/>
	   	<@table.td name="attr.credit"/>
	   	<#list gradeTypes as gradeType>
	    	<#assign gradeTypeName><@i18nName gradeType/></#assign>
	    	<@table.td text=gradeTypeName/>
	   	</#list>
	   	<@table.td name="grade.finalScore"/>
	   	<@table.td name="attr.gradePoint"/>
   		<@table.td name="attr.yearTerm"/>
  	</@>
  	<@table.tbody datas=grades;grade>
	   	<td>${grade.course.code}</td>
	   	<td>${grade.taskSeqNo?if_exists}</td>
	   	<td><@i18nName grade.course/></td>
	   	<td><@i18nName grade.courseType/></td>
	   	<td>${grade.credit}</td>
	   	<#list gradeTypes as gradeType>
	    <td>${grade.getPublishedScoreDisplay(gradeType)}</td>
	    </#list>
	   	<td>${grade.getScoreDisplay(FINAL)}</td>
	   	<td>${grade.GP}</td>
	   	<td>${grade.calendar.year} ${grade.calendar.term}</td>
  	</@>
</@>
