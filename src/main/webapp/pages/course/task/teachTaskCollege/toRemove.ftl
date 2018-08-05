<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <p>在删除任务中，包含班级在培养计划中出现的仅有课程，如果删除可能会出现班级课程丢失。</p>
    <p>强制操作可以点击“删除”，否则点击“后退”。</p>
    <hr>
    <li>下面是可删除的教学任务记录：
    <@table.table width="100%" id="listTable1">
        <@table.thead>
            <@table.td id="task.seqNo" width="8%" name="attr.taskNo"/>
            <@table.td id="task.course.code" name="attr.courseNo"/>
            <@table.td id="task.course.name" width="20%" name="attr.courseName"/>
            <@table.td id="task.courseType.name" width="10%" name="entity.courseType"/>
            <@table.td id="task.teachClass.name" width="20%" name="entity.teachClass"/>
            <@table.td width="10%" name="entity.teacher"/>
            <@table.td width="4%" name="teachTask.planStudents" id="task.teachClass.planStdCount"/>
            <@table.td width="4%" id="task.course.credits" name="attr.credit"/>
            <@table.td width="4%" id="task.arrangeInfo.weekUnits" name="teachTask.weeksPerHour"/>
            <@table.td width="4%" id="task.arrangeInfo.weeks" name="attr.weeks"/>
            <@table.td width="4%" id="task.arrangeInfo.overallUnits" name="attr.creditHour"/>
        </@>
        <@table.tbody datas=tasks;task>
            <td>${task.seqNo?if_exists}</td>
            <td>${task.course.code}</td>
            <td><@i18nName task.course/></td>
            <td><@i18nName task.courseType/></td>
            <td title="${task.teachClass.name?html}" nowrap><span style="display:block;width:150px;overflow:hidden;text-overflow:ellipsis;"><#if task.teachClass.gender?exists>(<@i18nName task.teachClass.gender/>)</#if>${task.teachClass.name?html}</span></td>
            <td><@getTeacherNames task.arrangeInfo.teachers/></td>
            <td>${task.teachClass.planStdCount}</td>
            <td>${task.course.credits}</td>
            <td>${task.arrangeInfo.weekUnits}</td>
            <td>${task.arrangeInfo.weeks}</td>
            <td>${task.arrangeInfo.overallUnits}</td>
        </@>
    </@>
    <hr>
    <li>删除后可能会出现班级课程丢失的教学任务：
    <@table.table width="100%" id="listTable2">
        <@table.thead>
            <@table.td id="task.seqNo" width="8%" name="attr.taskNo"/>
            <@table.td id="task.course.code" name="attr.courseNo" width="10%"/>
            <@table.td id="task.course.name" width="20%" name="attr.courseName"/>
            <@table.td id="task.courseType.name" name="entity.courseType"/>
            <@table.td id="task.teachClass.name" width="20%" name="entity.teachClass"/>
            <@table.td name="entity.teacher"/>
        </@>
        <@table.tbody datas=problemTasks;task>
            <td>${task.seqNo?if_exists}</td>
            <td>${task.course.code}</td>
            <td><@i18nName task.course/></td>
            <td><@i18nName task.courseType/></td>
            <td title="${task.teachClass.name?html}" nowrap><span style="display:block;width:150px;overflow:hidden;text-overflow:ellipsis;"><#if task.teachClass.gender?exists>(<@i18nName task.teachClass.gender/>)</#if>${task.teachClass.name?html}</span></td>
            <td><@getTeacherNames task.arrangeInfo.teachers/></td>
        </@>
    </@>
    <form name="actionForm" action="" method="post" onsubmit="return false;"></form>
    <script>
        var bar = new ToolBar("bar", "教学任务删除确认", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addItem(" 删除下面的任务", "removeTeachTask()");
        bar.addBack(" <@msg.message key="action.back"/>");
        
        $("message").innerHTML = "删除警告";
        
        var form = document.actionForm;
        var taskIds = "${RequestParameters["taskIds"]?if_exists}";
        function removeTeachTask(){
           if(confirm("确定要删除下面的任务吗?")) {
               form.action = "?method=remove";
               addInput(form, "taskIds", taskIds, "hidden");
               addInput(form, "isRemove", "true", "hidden");
               addInput(form, "toBeText", "${RequestParameters["toBeText"]?if_exists}", "hidden");
               addInput(form, "params", "<#list RequestParameters?keys as key>&${key}=${RequestParameters[key]}</#list>", "hidden");
               form.submit();
           }
       }
    </script>
</body>
<#include "/templates/foot.ftl"/>