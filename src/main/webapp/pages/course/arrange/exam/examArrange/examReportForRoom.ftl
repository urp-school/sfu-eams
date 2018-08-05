<#include "/templates/head.ftl"/>
<#include "/pages/system/calendar/timeFunction.ftl"/>
<style>
.arranged{
 background-color:pink;
}
</style>
<script>
   var noRoomTaskIds=[];
   <#list timeUnits as unit>
      noRoomTaskIds[${unit_index}]=new Object();
      noRoomTaskIds[${unit_index}].taskIds=new Array();
      noRoomTaskIds[${unit_index}].year=${unit.year};
      noRoomTaskIds[${unit_index}].validWeeksNum=${unit.validWeeksNum};
      noRoomTaskIds[${unit_index}].weekId=${unit.weekId};
      noRoomTaskIds[${unit_index}].startUnit=${unit.startUnit};
      noRoomTaskIds[${unit_index}].endUnit=${unit.endUnit};
      noRoomTaskIds[${unit_index}].startTime=${unit.startTime};
      noRoomTaskIds[${unit_index}].endTime=${unit.endTime};
      noRoomTaskIds[${unit_index}].stdCount=0;//没有分配考场的学生数目
   </#list>
   function addTaskId(index,taskId,stdCount){
       var taskIds =noRoomTaskIds[index].taskIds;
       for(var i=0;i<taskIds.length;i++){ 
          if(taskIds[i]==taskId)return;
       }
       taskIds[taskIds.length]=taskId;
       noRoomTaskIds[index].stdCount+=stdCount;
   }
   function allocateFreeRoom(index){
      if(noRoomTaskIds[index].taskIds.length==0){
         alert("时间段的任务都分配了考试教室,无需再分配");return;
      }else{
         var taskIdSeq = getSelectIds("taskId");
         if(""==taskIdSeq){alert("请选择教学任务!");return;}
         var form=document.actionForm;
         addInput(form,"timeUnit.year",noRoomTaskIds[index].year);
         addInput(form,"timeUnit.validWeeksNum",noRoomTaskIds[index].validWeeksNum);
         addInput(form,"timeUnit.weekId",noRoomTaskIds[index].weekId);
         addInput(form,"timeUnit.startUnit",noRoomTaskIds[index].startUnit);
         addInput(form,"timeUnit.endUnit",noRoomTaskIds[index].endUnit);
         addInput(form,"timeUnit.startTime",noRoomTaskIds[index].startTime);
         addInput(form,"timeUnit.endTime",noRoomTaskIds[index].endTime);
         addInput(form,"stdCount",noRoomTaskIds[index].stdCount);
         var noRoomTaskIdSeq=",";
         for(var i=0;i<noRoomTaskIds[index].taskIds.length;i++){
           noRoomTaskIdSeq+=noRoomTaskIds[index].taskIds[i]+",";
         }
         var taskIds = taskIdSeq.split(",");
         var newTaskIds=",";
         for(var i=0;i<taskIds.length;i++){
            if(noRoomTaskIdSeq.indexOf(","+taskIds[i]+",")!=-1){
                newTaskIds+=taskIds[i]+",";
            }
         }
         if(newTaskIds==','){alert("这些任务无须安排教室");return;}
         addInput(form,"noRoomTaskIds",newTaskIds);
         form.submit();
      }
   }
</script>
<body >
  <table id="taskBar"></table>
  <@table.table width="100%">
    <@table.thead>
	    <@table.selectAllTd id="taskId"/>
	    <td></td>
	    <@table.td name="attr.teachDepart"/>
	    <@table.td name="attr.taskNo"/>
	    <@table.td name="attr.courseNo"/>
	    <@table.td name="attr.courseName"/>
	    <#list timeUnits as unit><td>${unit.firstDay?date} <@i18nName weeks[unit.weekId-1]/><br><@getTimeStr unit.startTime/>-<@getTimeStr unit.endTime/></td></#list>
    </@>
    <tr>
     <td colspan="6" align="center">选择没有安排教室教学任务，进行分配教室。</td>
    <#list timeUnits as unit><td align="center"><button onclick="allocateFreeRoom(${unit_index})" title="为没有教室的分配教室">分配空闲教室</button></td></#list>
    </tr>
    <@table.tbody datas=tasks?sort_by(["arrangeInfo","teachDepart","name"]);task,task_index>
      <@table.selectTd id="taskId" value=task.id/>
      <td>${task_index+1}</td>
      <td><@i18nName task.arrangeInfo.teachDepart/></td>
      <td>${task.seqNo}</td>
      <td>${task.course.code}</td>
      <td><@i18nName task.course/></td>
      <#list timeUnits as unit>
        <#assign activities =task.arrangeInfo.getExamActivities(examType,unit)>
        <td <#if activities?size!=0>class="arranged"</#if>>
            <#list  activities as activity>
             <#if activity.room?exists>
              <@i18nName activity.room/>
             <#else>没有分配教室(${activityTakeMap[activity.id?string]}人)<script>addTaskId(${unit_index},'${task.id}',${activityTakeMap[activity.id?string]});</script></#if></#list></td></#list>
    </@>
    <tr>
     <td colspan="6" align="center">选择没有安排教室教学任务，进行分配教室。</td>
    <#list timeUnits as unit><td align="center"><button onclick="allocateFreeRoom(${unit_index})" title="为没有教室的分配教室">分配空闲教室</button></td></#list>
    </tr>
  </@>
<form name="actionForm" method="post" action="examArrange.do?method=allocateRoomSetting">
 <input name="examType.id" value="${RequestParameters['examType.id']}" type="hidden"/>
 <input name="taskIds" value="${RequestParameters['taskIds']}" type="hidden"/>
</form>
<script>
     var bar=new ToolBar('taskBar','排考结果',null,true,true);
     bar.setMessage('<@getMessage/>');
     bar.addPrint("<@msg.message key="action.print"/>");
     bar.addBack("<@msg.message key="action.back"/>");
</script>
</body> 
<#include "/templates/foot.ftl"/> 