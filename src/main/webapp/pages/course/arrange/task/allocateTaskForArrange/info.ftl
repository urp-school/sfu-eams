<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <#assign allocations = tasks/>
    <#include "listContent.ftl"/>
    <form method="post" action="" name="actionForm" onsubmit="return false;">
        <input type="hidden" name="taskInId" value="${(taskIn.id)?if_exists}"/>
        <input type="hidden" name="calendarId" value="${RequestParameters['calendarId']?if_exists}"/>
        <input type="hidden" name="taskIds" value=""/>
        <input type="hidden" name="departmentId" value="${department.id}"/>
    </form>
    <script>
        var bar = new ToolBar("bar", "<font color=\"blue\">${(taskIn.department.name)?default(department.name)}</font>的归属教学任务(${totalSize?default(0)})", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("添加任务", "taskInner()", "new.gif", "添加未归属任务");
        bar.addItem("取消归属", "taskOutter()", "delete.gif", "取消所选已归属的任务");
        
        var form = document.actionForm;
        
        function enterQuery(event) {
            if (portableEvent(event).keyCode == 13) {
                document.taskListForm.target = "pageIframe";
                document.taskListForm.submit();
            }
        }
        
        function taskInner() {
            form.action = "allocateTaskForArrange.do?method=search";
            form.target = "_self";
            form.submit();
        }
        
        function taskOutter() {
            var taskIds = getSelectIds("taskId");
            if (isEmpty(form["taskInId"].value) || isEmpty(taskIds)) {
                alert("请选择要取消归属的任务，或没有可取消的归属任务。");
                return;
            }
            if (confirm("确定要取消吗？")) {
                form.action = "allocateTaskForArrange.do?method=taskOutter";
                form["taskIds"].value = taskIds;
                addInput(form, "params", "&primaryKey=${RequestParameters["primaryKey"]?default("")}&teachCalendarId=${RequestParameters['calendarId']}&departmentId=${(taskIn.department.id)?default(department.id)}", "hidden");
                form.target = "_parent";
                form.submit();
            }
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>