<#include "/templates/head.ftl"/>
<body>
    <table id="bar" width="100%"></table>
    <@table.table id="multiRequireTable" width="100%" sortable="true">
        <@table.thead>
            <@table.selectAllTd id="multiRequireId"/>
            <@table.sortTd text="课程序号" id="multiRequire.task.seqNo" width="10%"/>
            <@table.sortTd text="课程代码" id="multiRequire.task.course.code" width="10%"/>
            <@table.sortTd text="课程名称" id="multiRequire.task.course.name" width="10%"/>
            <@table.sortTd text="教学班" id="multiRequire.task.teachClass.name" width="25%"/>
            <@table.sortTd text="多媒体教室要求" id="multiRequire.addressRequirement,multiRequire.environmentRequirement"/>
        </@>
        <@table.tbody datas=multiRequires;multiRequire>
            <@table.selectTd id="multiRequireId" value=multiRequire.id/>
            <td><a href="#" onclick="info('${multiRequire.id}')">${multiRequire.task.seqNo}</a></td>
            <td>${multiRequire.task.course.code}</td>
            <td>${multiRequire.task.course.name?html}</td>
            <td><#list multiRequire.task.teachClass.adminClasses?sort_by("name") as adminClass>${adminClass.name?html}<#if adminClass_has_next>，</#if></#list></td>
            <td>${multiRequire.addressRequirement?html}<br>${multiRequire.environmentRequirement?html}</td>
        </@>
    </@>
    <form method="post" action="" name="actionForm" onsubmit="return false;">
        <input type="hidden" name="calendarId" value="${RequestParameters["calendar.id"]?default(RequestParameters["calendarId"]?if_exists)}"/>
        <input type="hidden" name="multiRequireId" value=""/>
        <input type="hidden" name="multiRequireIds" value=""/>
    </form>
    <script>
        var bar = new ToolBar("bar", "多媒体教室要求配置结果列表", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("添加", "add()");
        bar.addItem("修改", "edit()");
        bar.addItem("查看", "info()" );
        bar.addItem("删除", "remove()");
        bar.addItem("附件", "fileDown()", "download.gif");
        
        var form = document.actionForm;
        
        function initData() {
            form["multiRequireId"].value = form["multiRequireIds"].value = "";
        }
        
        function add() {
            initData();
            form.action = "multimediaRequirement.do?method=teachTaskSearch";
            form.target = "_self";
            form.submit();
        }
        
        function edit() {
            initData();
            var multiRequireId = getSelectId("multiRequireId");
            if (isEmpty(multiRequireId) || isMultiId(multiRequireId)) {
                alert("请选择一条要配置的教学任务。");
                return;
            }
            form.action = "multimediaRequirement.do?method=edit";
            form["multiRequireId"].value = multiRequireId;
            form.target = "_self";
            form.submit();
        }
        
        function info(objectId) {
            initData();
            var multiRequireId = objectId;
            if (isEmpty(multiRequireId)) {
                multiRequireId = getSelectId("multiRequireId");
            }
            if (isEmpty(multiRequireId) || isMultiId(multiRequireId)) {
                alert("请选择一条要配置的教学任务。");
                return;
            }
            form.action = "multimediaRequirement.do?method=info";
            form["multiRequireId"].value = multiRequireId;
            form.target = "_blank";
            form.submit();
        }
        
        function remove() {
            initData();
            var multiRequireIds = getSelectId("multiRequireId");
            if (isEmpty(multiRequireIds)) {
                alert("请选择要删除的记录。");
                return;
            }
            if (confirm("确认要删除所选记录吗？")) {
                form.action = "multimediaRequirement.do?method=remove";
                form["multiRequireIds"].value = multiRequireIds;
                form.target = "_self";
                form.submit();
            }
        }
        
        function fileDown() {
            initData();
            form.action = "dataTemplate.do?method=download&document.id=16";
            form.target = "_self";
            form.submit();
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>