<#include "/templates/head.ftl"/>
<body>
    <table id="bar" width="100%"></table>
    <@table.table id="evaluateTeacherStat" width="100%" sortable="true">
        <@table.thead>
            <@table.selectAllTd id="evaluateTeacherId"/>
            <@table.sortTd name="teacher.code" id="evaluateTeacher.teacher.code"/>
            <@table.sortTd name="attr.personName" id="evaluateTeacher.teacher.name"/>
            <@table.sortTd name="attr.courseNo" id="evaluateTeacher.course.code"/>
            <@table.sortTd name="course.titleName" id="evaluateTeacher.course.name"/>
            <@table.sortTd text="个人得分" id="evaluateTeacher.sumScore"/>
            <@table.sortTd text="全校排名" id="evaluateTeacher.rank"/>
            <@table.sortTd text="院系排名" id="evaluateTeacher.departRank"/>
        </@>
        <@table.tbody datas=evaluateTeachers;evaluateTeacher>
            <@table.selectTd id="evaluateTeacherId" value=evaluateTeacher.id/>
            <td>${evaluateTeacher.teacher.code}</td>
            <td><@i18nName evaluateTeacher.teacher/></td>
            <td>${evaluateTeacher.course.code}</td>
            <td><@i18nName evaluateTeacher.course/></td>
            <td>${(evaluateTeacher.sumScore?string("0.00"))?if_exists}</td>
            <td>${(evaluateTeacher.rank)?if_exists}</td>
            <td>${(evaluateTeacher.departRank)?if_exists}</td>
        </@>
    </@>
    <@htm.actionForm name="actionForm" action="evaluateDetailStat.do" entity="evaluateTeacher" onsubmit="return false;">
        <input type="hidden" name="calendar.id" value="${RequestParameters["evaluateTeacher.calendar.id"]}"/>
        <input type="hidden" name="departmentId" value="${RequestParameters["evaluateTeacher.department.id"]?if_exists}"/>
        <input type="hidden" name="questionnaireId" value="${RequestParameters["questionnaireId"]?if_exists}"/>
    </@>
    <script>
        var bar = new ToolBar("bar", "评教统计情况列表", null, true, true);
        bar.setMessage('<@getMessage/>');
        var menu1 = bar.addMenu("查看详情", "info()", "detail.gif");
        menu1.addItem("教师历史评教", "teacherHistory()", "detail.gif");
        <#if RequestParameters["evaluateTeacher.department.id"]?default("") != "">
        menu1.addItem("学院历史评教", "departmentHistory()", "detail.gif");
        menu1.addItem("学院本次评教", "departmentHistoryDetail()", "detail.gif");
        menu1.addItem("学院分项汇总", "departmentGroupItemInfo()", "detail.gif");
        </#if>
        <#if RequestParameters["questionnaireId"]?default("") != "">
        bar.addItem("全校分类评教", "questionnaireCollege()", "detail.gif");
        </#if>
        bar.addItem("<@msg.message key="action.export"/>", "exportData()");
        
        function teacherHistory() {
            form.target = "_self";
            submitId(form, "evaluateTeacherId", false, "evaluateDetailStat.do?method=teacherHistory");
        }
        
        function departmentHistory() {
            form.action = "evaluateDetailStat.do?method=departmentHistory";
            form.target = "_blank";
            form.submit();
        }
        
        function departmentHistoryDetail() {
            form.action = "evaluateDetailStat.do?method=departmentHistoryDetail";
            form.target = "_blank";
            form.submit();
        }
        
        function departmentGroupItemInfo() {
            form.action = "evaluateDetailStat.do?method=departmentGroupItemInfo";
            form.target = "_self";
            form.submit();
        }
        
        function questionnaireCollege() {
            form.action = "evaluateDetailStat.do?method=questionnaireCollege";
            form.target = "_blank";
            form.submit();
        }
        
        function exportData() {
            form.target = "_self";
            addInput(form, "fileName", "评教汇总表", "hidden");
            addInput(form, "titles", "教师所在院系,全校排名,学院排名,教师工号,教师姓名,教师类别,课程代码,课程名称,人数,总分", "hidden");
            addInput(form, "keys", "teacher.department.name,rank,departRank,teacher.code,teacher.name,teacher.teacherType.name,course.code,course.name,validTickets,sumScore", "hidden");
            exportList();
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>