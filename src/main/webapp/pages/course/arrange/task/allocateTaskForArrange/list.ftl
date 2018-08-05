<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <#include "listContent.ftl"/>
    <form method="post" action="" name="actionForm" onsubmit="return false;">
        <input type="hidden" name="taskInId" value="${(RequestParameters["taskInId"])?if_exists}"/>
        <input type="hidden" name="calendarId" value="${RequestParameters['calendarId']?if_exists}"/>
        <input type="hidden" name="taskIds" value=""/>
        <input type="hidden" name="departmentId" value="${(RequestParameters["departmentId"])?if_exists}"/>
    </form>
    <script>
        var bar = new ToolBar("bar", "未归属的教学任务(${totalSize?default(0)})", null, true, true);
        bar.setMessage('<@getMessage/>');
        <#if RequestParameters["departmentId"]?default("") == "">
        var menu1 = bar.addMenu("指定所归属院系...", null, "new.gif");
            <#list departments?sort_by("name") as department>
        menu1.addItem("${department.name}", "taskInner(${department.id})", "new.gif");
            </#list>
        <#else>
        bar.addItem("指定<font color=\"blue\">${department.name}</font>归属院系", "taskInner(${department.id})", "new.gif", "指定${department.name}归属院系");
        </#if>
        
        var form = document.actionForm;
        
        function enterQuery(event) {
            if (portableEvent(event).keyCode == 13) {
                document.taskListForm.target = "pageIframe";
                document.taskListForm.submit();
            }
        }
        
        function taskInner(departmentId) {
            var taskIds = getSelectIds("taskId");
            if (isEmpty(taskIds)) {
                alert("请选择要指定归属的任务。");
                return;
            }
            if (confirm("确定要指定吗？")) {
                form.action = "allocateTaskForArrange.do?method=taskInner";
                form["taskIds"].value = taskIds;
                form["departmentId"].value = departmentId;
                form.target = "_parent";
                addInput(form, "params", "&primaryKey=${RequestParameters["primaryKey"]?default("")}&teachCalendarId=${RequestParameters['calendarId']}&departmentId=<#if (department.id)?exists>${(taskIn.department.id)?default(department.id)}"<#else>" + departmentId</#if>, "hidden");
                form.submit();
            }
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>