<#include "/templates/head.ftl"/>
<BODY>
<table id="toolbar" ></table>
  <@getMessage/>
  <@table.table id="listTable" width="100%" sortable="true">
    <@table.thead>
    	
        <@table.sortTd text="课程代码" id="newCourse.course.code"/>
        <@table.sortTd text="课程名称" id="newCourse.course.name"/>
    </@>
     <#if newCourses??>
     <@table.tbody datas=newCourses;newCourse>        
        <td>${(newCourse.course.code)?if_exists}</td>
        <td><a href="courseSearch.do?method=info&type=course&id=${(newCourse.course.id)?if_exists}">${(newCourse.course.name)?if_exists}</a></td>
     </@>
     </#if>
   </@> 
  <script language="javascript">
   bar= new ToolBar("toolbar","新开课程列表");
   bar.addBack();
   defaultOrderBy="${RequestParameters['orderBy']?default('null')}"; 
  </script>
</body>
<#include "/templates/foot.ftl"/>