<#include "/templates/head.ftl"/>
<body>
    <table id="bar" width="100%"></table>
    <@table.table id="labRequireTable" width="100%" sortable="true">
        <@table.thead>
            <@table.selectAllTd id="labRequireId"/>
            <@table.sortTd text="课程序号" id="labRequire.task.seqNo" width="10%"/>
            <@table.sortTd text="课程代码" id="labRequire.task.course.code" width="10%"/>
            <@table.sortTd text="课程名称" id="labRequire.task.course.name" width="15%"/>
            <@table.sortTd text="教学班" id="labRequire.task.teachClass.name" width="25%"/>
            <@table.sortTd text="实验室要求" id="labRequire.overallUnit,labRequire.propExperimental,labRequire.classroomType.name,labRequire.timeDescrition,labRequire.experimentRequirement,labRequire.projectDescrition"/>
        </@>
        <@table.tbody datas=labRequires;labRequire>
            <@table.selectTd id="labRequireId" value=labRequire.id/>
            <td><a href="#" onclick="info('${labRequire.id}')">${labRequire.task.seqNo}</a></td>
            <td>${labRequire.task.course.code}</td>
            <td>${labRequire.task.course.name?html}</td>
            <td><#list labRequire.task.teachClass.adminClasses?sort_by("name") as adminClass>${adminClass.name?html}<#if adminClass_has_next>，</#if></#list></td>
            <td>计划实验总学时：${labRequire.overallUnit?html}<br>实验占总成绩的比例：${labRequire.propExperimental?html}<br>实验地点：${labRequire.classroomType.name}<br>实验上机时间：${labRequire.timeDescrition?html}<br>实验所需软件及环境要求：${labRequire.experimentRequirement?html}<br>实验项目安排：${labRequire.projectDescrition?html}</td>
        </@>
    </@>
    <form method="post" action="" name="actionForm" onsubmit="return false;">
    	<input type="hidden" name="calendarId" value="${RequestParameters["calendar.id"]?default(RequestParameters["calendarId"]?if_exists)}"/>
        <input type="hidden" name="labRequireId" value=""/>
        <input type="hidden" name="labRequireIds" value=""/>
    </form>
    <script>
        var bar = new ToolBar("bar", "实验室要求配置结果列表", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("添加", "add()");
        bar.addItem("修改", "edit()");
        bar.addItem("查看", "info()" );
        bar.addItem("删除", "remove()");
        bar.addItem("附件", "fileDown()", "download.gif");
        
        var form = document.actionForm;
        
        function initData() {
            form["labRequireId"].value = form["labRequireIds"].value = "";
        }
        
        function add() {
            initData();
            form.action = "laboratoryRequirement.do?method=teachTaskSearch";
            form.target = "_self";
            form.submit();
        }
        
        function edit() {
            initData();
            var labRequireId = getSelectId("labRequireId");
            if (isEmpty(labRequireId) || isMultiId(labRequireId)) {
                alert("请选择一条要配置实验室的教学任务。");
                return;
            }
            form.action = "laboratoryRequirement.do?method=edit";
            form["labRequireId"].value = labRequireId;
            form.target = "_self";
            form.submit();
        }
        
        function info(objectId) {
            initData();
            var labRequireId = objectId;
            if (isEmpty(labRequireId)) {
                labRequireId = getSelectId("labRequireId");
            }
            if (isEmpty(labRequireId) || isMultiId(labRequireId)) {
                alert("请选择一条要配置实验室的教学任务。");
                return;
            }
            form.action = "laboratoryRequirement.do?method=info";
            form["labRequireId"].value = labRequireId;
            form.target = "_blank";
            form.submit();
        }
        
        function remove() {
            initData();
            var labRequireIds = getSelectId("labRequireId");
            if (isEmpty(labRequireIds)) {
                alert("请选择要删除的记录。");
                return;
            }
            if (confirm("确认要删除所选记录吗？")) {
                form.action = "laboratoryRequirement.do?method=remove";
                form["labRequireIds"].value = labRequireIds;
                form.target = "_self";
                form.submit();
            }
        }
        
        function fileDown() {
            initData();
            form.action = "dataTemplate.do?method=download&document.id=17";
            form.target = "_self";
            form.submit();
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>


