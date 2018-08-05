<#include "/templates/head.ftl"/>
<body>
    <table id="bar" width="100%"></table>
    <@table.table id="teachTaskTable" width="100%" sortable="true" headIndex="1">
    <form method="post" action="" name="actionForm" onsubmit="return false;">
        <input type="hidden" name="calendar.id" value="${RequestParameters["calendarId"]}"/>
        <input type="hidden" name="taskId" value=""/>
        <tr onKeyDown="search(event)">
            <td><img src="${static_base}/images/action/search.png"  align="top" onClick="search()"/></td>
            <td><input type="text" name="task.seqNo" value="${RequestParameters["task.seqNo"]?if_exists}" maxlength="30" style="width:100%"/></td>
            <td><input type="text" name="task.course.code" value="${RequestParameters["task.course.code"]?if_exists}" maxlength="30" style="width:100%"/></td>
            <td><input type="text" name="task.course.name" value="${RequestParameters["task.course.name"]?if_exists}" maxlength="30" style="width:100%"/></td>
            <td><input type="text" name="className" value="${RequestParameters["className"]?if_exists}" maxlength="30" style="width:100%"/></td>
        </tr>
    </form>
        <@table.thead>
            <@table.td text=""/>
            <@table.sortTd text="课程序号" id="task.seqNo" width="15%"/>
            <@table.sortTd text="课程代码" id="task.course.code" width="15%"/>
            <@table.sortTd text="课程名称" id="task.course.name" width="15%"/>
            <@table.sortTd text="教学班" id="task.teachClass.name"/>
        </@>
        <@table.tbody datas=tasks;task>
            <@table.selectTd id="taskId" value=task.id type="radio"/>
            <td>${task.seqNo}</td>
            <td>${task.course.code}</td>
            <td>${task.course.name?html}</td>
            <td><#list task.teachClass.adminClasses?sort_by("name") as adminClass>${adminClass.name?html}<#if adminClass_has_next>，</#if></#list></td>
        </@>
    </@>
    <script>
        var bar = new ToolBar("bar", "<span style=\"color:blue\">第一步：</span>选择要配置实验室的教学任务（共2步）", null, true, true);
        bar.addItem("添加", "add()");
        bar.addItem("返回列表", "toBack()", "backward.gif");
        
        var form = document.actionForm;
        
        function initData() {
            form["taskId"].value = "";
        }
        
        function search(event) {
            initData();
            if (!isEmpty(event)) {
                if (portableEvent(event).keyCode != 13) {
                    return;
                }
            }
            form.action = "laboratoryRequirement.do?method=teachTaskSearch";
            form.target = "_self";
            form.submit();
        }
        
        function add() {
            initData();
            var taskId = getSelectId("taskId");
            if (isEmpty(taskId) || isMultiId(taskId)) {
                alert("请选择一条要配置的教学任务。");
                return;
            }
            form.action = "laboratoryRequirement.do?method=edit";
            form["taskId"].value = taskId;
            form.target = "_self";
            form.submit();
        }
        
        function toBack() {
            initData();
            form.action = "laboratoryRequirement.do?method=search";
            form.target = "_self";
            form.submit();
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>