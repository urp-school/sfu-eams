<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <@table.table id="teachPlanCourseStatResult" width="100%" sortable="true">
        <@table.thead>
            <@table.selectAllTd id="planCourseId"/>
            <@table.sortTd text="课程代码" id="planCourse.course.code" width="15%"/>
            <@table.sortTd text="课程名称" id="planCourse.course.name" width="15%"/>
            <@table.sortTd text="初设学分" id="planCourse.course.name"/>
            <@table.sortTd text="初设周课时" id="planCourse.course.name"/>
            <@table.sortTd text="初设学时" id="planCourse.course.name"/>
            <@table.sortTd text="培养计划数" width="15%" id="count(*)"/>
        </@>
        <@table.tbody datas=results;result>
            <@table.selectTd id="courseId" value=result[0].id/>
            <td>${result[0].code?if_exists}</td>
            <td title="${result[0].name?if_exists}" nowrap><span style="display:block;width:150px;overflow:hidden;text-overflow:ellipsis">${result[0].name}</span></td>
            <td>${result[0].credits?if_exists}</td>
            <td>${result[0].weekHour?if_exists}</td>
            <td>${(result[0].extInfo.period)?if_exists}</td>
            <#assign planCount = (result[1])?default(0)/>
            <td><#if planCount == 0>0<#else><A href="#" onclick="infoData('${result[0].id}')">${(result[1])?default(0)}</A></#if></td>
        </@>
    </@>
    <@htm.actionForm name="actionFrom" action="teachPlanCourseStat.do" entity="course" onsubmit="return false;">
        <input type="hidden" name="params" value="<#list RequestParameters?keys as key>&${key}=${RequestParameters[key]}</#list>"/>
    </@>
    <script>
        var bar = new ToolBar("bar", "查询统计结果", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("查看", "infoData()", "detail.gif");
        bar.addItem("删除", "toRemove()", "delete.gif");
        
        function infoData(id) {
            var courseId = id;
            if (null == courseId) {
                courseId = getSelectId("courseId");
            }
            if (isEmpty(courseId) || isMultiId(courseId)) {
                alert("请选择一个要查看的记录。");
                return;
            }
            form.action = "teachPlanCourseStat.do?method=info";
            addInput(form, "courseId", courseId, "hidden");
            form.submit();
        }
        
        function toRemove() {
            var courseId = getSelectId("courseId");
            if (isEmpty(courseId) || isMultiId(courseId)) {
                alert("请选择一个要操作的记录。");
                return;
            }
            if (confirm("是否要批量从这门课所在的培养计划中删除？\n点击“确定”可以进一步选择培养计划，以从中删除这门课程。")) {
                form.action = "teachPlanCourseStat.do?method=toRemove";
                addInput(form, "courseId", courseId, "hidden");
                form.submit();
            }
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>
