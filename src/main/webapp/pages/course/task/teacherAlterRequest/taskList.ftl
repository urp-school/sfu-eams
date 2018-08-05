<#include "/templates/head.ftl"/>
<body  >   
<table id="teacherTaskBar"></table>
 <@table.table width="100%" id="taskListTable">
  <@table.thead>
    <@table.td text=""/>
    <@table.td width="8%" name="attr.taskNo" />
    <@table.td width="8%" name="attr.courseNo"/>
    <@table.td width="15%" name="attr.courseName"/>
    <@table.td width="20%" name="entity.teachClass" />
    <@table.td width="25%" text="课程安排"/>
   </@>
   <@table.tbody datas=taskList?sort_by(["course","name"]);task>
     <@table.selectTd id="taskId" type="radio"  value=task.id />
      <td>${task.seqNo}</td>
      <td>${task.course.code}</td>
      <td><A href="teachTaskSearch.do?method=info&task.id=${task.id}" ><@i18nName task.course?if_exists/></a></td>
      <td><#if task.requirement.isGuaPai><@msg.message key="attr.GP"/><#else>${task.teachClass.name?html}</#if></td>
      <td>${task.arrangeInfo.digest(task.calendar)}</td>
      <input type="hidden" id="${task.id}" value="<#if task.isConfirm == true>${task.seqNo}<#else>0</#if>"/>
    </@>
   </@>
  <#include "alterRequestForm.ftl"/>
  <script>
   var bar = new ToolBar('teacherTaskBar','教学任务列表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("新增变更申请","javascript:newRequest()",'new.gif'); 
   function newRequest(){
     var taskId = getSelectId("taskId");
     if(""==taskId){alert("请选择任务");return;}
     var form=document.alterForm;
     addInput(form,"taskAlterRequest.task.id",taskId);
     addInput(form,"taskAlterRequest.teacher.id",parent.document.searchForm["taskAlterRequest.teacher.id"].value);
     displayRequest();
   }
  </script>
</body>    
<#include "/templates/foot.ftl"/>    
