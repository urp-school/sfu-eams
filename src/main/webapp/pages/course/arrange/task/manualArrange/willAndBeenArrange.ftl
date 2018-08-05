<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <@table.table id="willAndBeenArrange" width="100%" align="center">
        <@table.thead>
            <@table.selectAllTd id="taskId" rowspan="2"/>
            <@table.td text="课程序号" rowspan="2"/>
            <@table.td text="课程代码" rowspan="2"/>
            <@table.td text="课程名称" width="20%" rowspan="2"/>
            <@table.td text="教学班" width="20%" rowspan="2"/>
            <@table.td text="教学任务" colspan="4"/>
            <@table.td text="实排结果" colspan="4"/>
        </@>
        <@table.thead>
            <@table.td text="起始周"/>
            <@table.td text="周课时"/>
            <@table.td text="周数"/>
            <@table.td text="总课时"/>
            <@table.td text="起始周"/>
            <@table.td text="周课时"/>
            <@table.td text="周数"/>
            <@table.td text="总课时"/>
        </@>
        <@table.tbody datas=tasks;task>
            <@table.selectTd id="taskId" value=task.id/>
            <td>${task.seqNo}</td>
            <td>${task.course.code}</td>
            <td><A href="teachTask.do?method=info&task.id=${task.id}" title="<@bean.message key="info.task.info"/>">${task.course.name}</A></td>
            <td title="${task.teachClass.name?html?replace(",", ", ")}" nowrap><span style="display:block;width:120px;overflow:hidden;text-overflow:ellipsis;">${task.teachClass.name?html}</span></td>
            <td<#if task.arrangeInfo.weekStart != task.activityStartWeek - task.calendar.weekStart + 1> style="color:Teal;font-weight:bold"</#if>>${task.arrangeInfo.weekStart}</td>
            <td<#if task.arrangeInfo.weekUnits != task.activityWeekUnits> style="color:Teal;font-weight:bold"</#if>>${task.arrangeInfo.weekUnits}</td>
            <td<#if task.arrangeInfo.weeks != task.activityWeeks> style="color:Teal;font-weight:bold"</#if>>${task.arrangeInfo.weeks}</td>
            <td<#if task.arrangeInfo.weeks * task.arrangeInfo.weekUnits != task.activityWeekUnits * task.activityWeeks> style="color:Teal;font-weight:bold"</#if>>${task.arrangeInfo.weeks * task.arrangeInfo.weekUnits}</td>
            <td<#if task.arrangeInfo.weekStart != task.activityStartWeek - task.calendar.weekStart + 1> style="color:Crimson;font-weight:bold"</#if>>${task.activityStartWeek - task.calendar.weekStart + 1}</td>
            <td<#if task.arrangeInfo.weekUnits != task.activityWeekUnits> style="color:Crimson;font-weight:bold"</#if>>${task.activityWeekUnits}</td>
            <td<#if task.arrangeInfo.weeks != task.activityWeeks> style="color:Crimson;font-weight:bold"</#if>>${task.activityWeeks}</td>
            <td<#if task.arrangeInfo.weeks * task.arrangeInfo.weekUnits != task.activityWeekUnits * task.activityWeeks> style="color:Crimson;font-weight:bold" id="totalTime${task.id}"</#if>>${task.activityWeekUnits * task.activityWeeks}</td>
        </@>
    </@>
    <form name="taskListForm" action="" method="post" onsubmit="return false;">
        <input type="hidden" name="task.calendar.id" value="${RequestParameters['task.calendar.id']}"/>
        <input type="hidden" name="task.arrangeInfo.isArrangeComplete" value="${RequestParameters['task.arrangeInfo.isArrangeComplete']}"/>
        <input type="hidden" name="params" value="<#list RequestParameters?keys as key>&${key}=${RequestParameters[key]}</#list>"/>
    </form>
    <script>
        var bar = new ToolBar("bar", "排课课时核对（有问题的共 ${tasks?size} 条）", null, true ,true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("任务同步", "updateTeachTask()");
        bar.addItem("手动调整", "adjust()");
        bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
        
        var form = document.taskListForm;
        function adjust(){
	        var taskId = getSelectId("taskId");
	        if(taskId.indexOf(",")!=-1){alert("请选择一个进行调整");return;}
	        if(taskId=="") {alert("<@bean.message key="prompt.task.selector" />");return;}
	        form.action="manualArrange.do?method=manualArrange&task.id="+taskId;
	        form.submit();
	    }
	    
	    function updateTeachTask() {
	       var taskIds = getSelectId("taskId");
	       if (isEmpty(taskIds)) {
	           alert("请选择要操作的记录。");
	           return;
	       }
	       var taskIdList = taskIds.split(",");
	       for (var i = 0; i < taskIdList.length; i++) {
	           try {
                if (isNotEmpty($("totalTime" + taskIdList[i]).innerHTML)) {
                    alert("实排结果的总课时与教学任务的不同，不能同步。");
                    return;
                }
	           } catch(e) {
	               ;
	           }
	       }
	       if (confirm("要将排课结果同步于任务吗？")) {
               form.action = "manualArrange.do?method=updateTeachTask";
               addInput(form, "taskIds", taskIds, "hidden");
               form.submit();
	       }
	    }
    </script>
</body>
<#include "/templates/foot.ftl"/>