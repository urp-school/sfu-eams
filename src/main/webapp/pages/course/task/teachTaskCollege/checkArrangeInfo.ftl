<#include "/templates/head.ftl"/>
<body >
 <table id="arrangeInfoBar" width="100%"></table>
 <table width="100%" class="listTable" >
    <form name="taskListForm"  method="post" action="?method=selfAdjustArrangeInfo" onsubmit="return false;">
     <input type="hidden" name="taskIds" value=""/>
    </form>
    <tr align="center" class="darkColumn">
      <td align="center">
        <input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('taskId'),event);">
      </td>
      <td width="8%"><@bean.message key="attr.taskNo"/></td>
      <td width="20%"><@bean.message key="attr.courseName"/></td>
      <td width="20%"><@bean.message key="entity.teachClass"/></td>
      <td width="10%"><@bean.message key="entity.teacher"/></td>
      <td width="4%"><@bean.message key="attr.stdNum"/></td>
      <td width="4%"><@bean.message key="attr.credit"/></td>
      <td width="4%">周时</td>
      <td width="4%">周数</td>
      <td width="4%"><@bean.message key="attr.creditHour"/></td>
    </tr>
    <#list tasks as task>
   	  <#if task_index%2==1><#assign class="grayStyle"></#if>
	  <#if task_index%2==0><#assign class="brightStyle"></#if>
     <tr class="${class}" align="center" 
       onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)"
       onclick="onRowChange(event)" >
      <td  class="select"><input type="checkBox" name="taskId" value="${task.id}"></td>
      <td><A href="courseTable.do?method=taskTable&task.id=${task.id}" title="查看课程安排">${task.seqNo?if_exists}</A></td>
      <td><A href="?method=info&task.id=${task.id}" title="<@bean.message key="info.task.info"/>"><@i18nName task.course/></A></td>
      <td>${task.teachClass.name?html}</td>
      <td><@getTeacherNames task.arrangeInfo.teachers/></td>
      <td><A href="?method=printStdListForDuty&teachTaskIds=${task.id}" title="查看学生名单" target="_blank">${task.teachClass.planStdCount}</A></td>
      <td>${task.course.credits}</td>
      <td>${task.arrangeInfo.weekUnits}</td>
      <td>${task.arrangeInfo.weeks}</td>
      <td><input type="hidden" name="${task.id}" id="${task.id}" value="<#if task.isConfirm == true>1 <#else>0</#if>"/>
       ${task.arrangeInfo.overallUnits}
      </td>
    </tr>
	</#list>
	<script>
	  function selfAdjustArrangeInfo(){
	     var taskIds = getSelectIds("taskId");
	     if(''==taskIds) {alert("请选择教学任务进行校正课时");return;}
	     else{
	        if(confirm("确定要将总课时设置为:周课时*周数?")){
	           document.taskListForm['taskIds'].value=taskIds;
  	           document.taskListForm.submit();
  	        }
	     }
	  }
	  var bar = new ToolBar("arrangeInfoBar","总课时与周课时*周数不吻合的教学任务",null,true,true);
	  bar.addItem("根据周课时*周数,调整总课时","selfAdjustArrangeInfo()");
	  
	  
	</script>
</body> 
<#include "/templates/foot.ftl"/> 