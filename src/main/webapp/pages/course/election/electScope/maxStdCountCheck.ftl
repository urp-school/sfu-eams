<#include "/templates/head.ftl"/>
<body >
<table id="electionTaskBar"></table>
 <@table.table id="listTable">
  <@table.thead>
    <@table.selectAllTd id="taskId" />
    <@table.td name="attr.taskNo"/>
    <@table.td name="attr.courseName"/>
    <@table.td text="授课教师"/>
    <@table.td text="人数上限"/>
    <@table.td text="教室容纳听课人数"/>
    <@table.td text="上课教室"/>
  </@>
  <@table.tbody datas=tasks;task>
    <@table.selectTd id="taskId" type="checkbox" value="${task.id}"/>
    <td>${task.seqNo}</td>
    <td><@i18nName task.course/></td>
    <td><@getTeacherNames task.arrangeInfo.teachers/></td>
    <td>${task.electInfo.maxStdCount}</td>
    <td>${capacityMap[task.id?string]}</td>
    <td><@getBeanListNames task.arrangeInfo.getCourseArrangedRooms()/></td>
  </@>
 </@>
 <form name="actionForm" action="electScope.do?method=setMaxStdCountByRoomCapacity" method="post" onsubmit="return false;">
   <input name="params" type="hidden" value="&calendar.id=${RequestParameters['calendar.id']}">
 </form>
  <script>
   var bar = new ToolBar('electionTaskBar','选课人数上限和教室容量不一致的教学任务',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("根据教室设置上限","setMaxStdCountByRoomCapacity()",'update.gif','按照规则给每个任务设置选课范围,人数,是否允许退课');
   bar.addBack();
   
   function setMaxStdCountByRoomCapacity(){
      submitId(document.actionForm,"taskId",true,null,"是否将选定的任务按照上课教室容量设置选课人数上限?");
   }
  </script>
</body> 
<#include "/templates/foot.ftl"/> 