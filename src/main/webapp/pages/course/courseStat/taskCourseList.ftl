<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','课程列表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack();
</script>   
  	<@table.table width="100%" sortable="true" id="listTable">
  	  <@table.thead>
  		<@table.sortTd width="20%" text="课程代码"  id="teachTask.course.code"/>
  		<@table.sortTd width="40%" text="课程名称" id="teachTask.course.name"/>
  		<@table.sortTd width="10%" text="学分" id="teachTask.course.credits"/>
  		<@table.sortTd width="10%" text="周课时" id="teachTask.arrangeInfo.weekUnits"/>
 		<@table.sortTd width="10%" text="学时" id="teachTask.arrangeInfo.overallUnits"/>
       </@>
   	  <@table.tbody datas=courses;course>
   		 <td>${course[0]}</td>
   		 <td>${course[1]}</td>
   		 <td>${course[2]}</td>
   		 <td>${course[3]}</td>
   		 <td>${course[4]}</td>
  	  </@>
  	</@>
 </body>
 <#include "/templates/foot.ftl"/>