<#include "/templates/head.ftl"/>
<BODY>
<script language="JavaScript" type="text/JavaScript" src="scripts/system/BaseInfo.js"></script>
  <@getMessage/>
  <@table.table id="listTable" width="100%" sortable="true">
    <@table.thead>
    	<@table.td text=""/>
        <@table.sortTd name="attr.code" id="course.code"/>
        <@table.sortTd name="attr.infoname" id="course.name"/>
        <@table.sortTd name="common.courseLength" id="course.extInfo.period"/>
        <@table.sortTd text="周课时" id="course.weekHour"/>
        <@table.sortTd name="common.grade" id="course.credits"/>
        <@table.sortTd name="course.stdType" id="course.stdType.name"/>
        <@table.sortTd name="course.department" id="course.extInfo.department.name"/>
        <@table.sortTd name="attr.engName" id="course.engName"/>
     </@>
     <@table.tbody datas=courses;course>
        <@table.selectTd id="courseId" value="${course.id}" type="radio"/>
        <td>${course.code}</td>
        <td><a href="http://webapp.urp.sfu.edu.cn/edu/course/site/${course.id}" target="_blank">${course.name}</a></td>
        <td>${(course.extInfo.period)?if_exists}</td>
        <td>${course.weekHour?if_exists}</td>
        <td>${course.credits?if_exists}</td>
        <td><@i18nName course.stdType/></td>
        <td><@i18nName course.extInfo.department?if_exists/></td>
        <td>${course.engName?if_exists}</td>
     </@>
   </@>
  <script language="javascript">
   type="course";
   defaultOrderBy="${RequestParameters['orderBy']?default('null')}";
  </script>
</body>
<#include "/templates/foot.ftl"/>