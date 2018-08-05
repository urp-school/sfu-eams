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
         addInput(form,"noRoomTaskIds",noRoomTaskIdSeq);
         form.submit();
      }
   }
</script>
<body >
  <#assign total_turn_std={} />
  <#list timeUnits as unit >
    <#assign total_turn_std=total_turn_std + {unit_index?string:0} />
  </#list>
  <table id="taskBar"></table>
  <@table.table width="100%">
    <@table.thead>
	    <td></td>
	    <@table.td name="attr.teachDepart"/>
	    <@table.td name="attr.taskNo"/>
	    <@table.td name="attr.courseNo"/>
	    <@table.td name="attr.courseName"/>
	    <#list timeUnits as unit><td>${unit.firstDay?date} <@i18nName weeks[unit.weekId-1]/><br><@getTimeStr unit.startTime/>-<@getTimeStr unit.endTime/></td></#list>
    </@>
    <@table.tbody datas=tasks?sort_by(["arrangeInfo","teachDepart","name"]);task,task_index>
      <td>${task_index+1}</td>
      <td><@i18nName task.arrangeInfo.teachDepart/></td>
      <td>${task.seqNo}</td>
      <td>${task.course.code}</td>
      <td><@i18nName task.course/></td>
      <#list timeUnits as unit>
        <#assign activities =task.arrangeInfo.getExamActivities(examType,unit)>
        <td <#if activities?size!=0>class="arranged"</#if>>
            <#assign thisTurnStd=0>
            <#list  activities as activity>
             <#if activity.room?exists>
              <@i18nName activity.room/>
             <#else>没有分配教室(${activityTakeMap[activity.id?string]}人)</#if>
             <#assign thisTurnStd=thisTurnStd+activityTakeMap[activity.id?string]>
            </#list>
         </td>
         <#assign current_std=total_turn_std[unit_index?string]+ thisTurnStd/>
         <#assign total_turn_std=total_turn_std + {unit_index?string:current_std}/>
      </#list>
    </@>
    <tr>
    <td></td><td></td><td></td><td></td><td></td>
    <#list timeUnits as unit><td align="center">${total_turn_std[unit_index?string]}</td></#list>
    </tr>
  </@>
<script>
     var bar=new ToolBar('taskBar','排考结果',null,true,true);
     bar.setMessage('<@getMessage/>');
     bar.addPrint("<@msg.message key="action.print"/>");
     bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
</script>
</body> 
<#include "/templates/foot.ftl"/> 