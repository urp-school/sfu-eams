<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <@table.table id="gradegradeLogList" width="100%" sortable="true">
        <@table.thead>
            <@table.selectAllTd id="gradeLogId"/>
            <@table.sortTd text="编号" width="10%" id="gradeLog.id"/>
            <@table.sortTd text="学生" width="10%" id="gradeLog.stdCode"/>
            <@table.sortTd text="教学日历" width="15%" id="gradeLog.calendarValue"/>
            <@table.sortTd text="课程序号" width="8%" id="gradeLog.taskSeqNo"/>
            <@table.sortTd text="任务课程" width="12%" id="gradeLog.courseCode"/>
            <@table.sortTd text="记录状态" width="8%" id="gradeLog.status"/>
            <@table.sortTd text="记录时间" width="10%" id="gradeLog.generateAt"/>
            <@table.sortTd text="记录操作人" width="9%" id="gradeLog.userCode"/>
            <@table.td text="详细描述/说明"/>
        </@>
        <#include "declareParams.ftl"/>
        <@table.tbody datas=gradeLogs;gradeLog>
            <@table.selectTd id="gradeLogId" value=gradeLog.id/>
            <td>#${gradeLog.id?string("000000000000000000")}</td>
            <#attempt><td>${gradeLog.student.name}（${gradeLog.student.code}）<#recover><td title="该学生已被删除或已不存在。">${gradeLog.stdName}（${gradeLog.stdCode}）<span style="color:red"> ×</span></#attempt></td>
            <#assign calendarValue = gradeLog.calendarValue?split(" ")/>
            <#assign stdTypeValue><#attempt>${gradeLog.stdType.name}<#recover>${gradeLog.stdTypeName}(<span style="color:red" title="学生类别已被删除或已不存在。">×</span>)</#attempt></#assign>
            <#attempt><td>${stdTypeValue}&nbsp;${gradeLog.calendar.year}&nbsp;${gradeLog.calendar.term}<#recover><td title="该教学日历已被删除或已不存在。">${stdTypeValue}&nbsp;${calendarValue[1]}&nbsp;${calendarValue[2]}<span style="color:red"> ×</span></#attempt></td>
            <#attempt><td>${gradeLog.taskSeqNo}<#recover><#attempt><#assign courseTemp = gradeLog.course.code/><td title="该成绩的课程没有对应的教学任务">----<span style="color:Brown"> △</span><#recover><td title="该教学任务可能已被删除，或已不存在。">----<span style="color:red"> ×</span></#attempt></#attempt></td>
            <#attempt><td>${gradeLog.course.name}（${gradeLog.course.code}）<#recover><td title="该课程已被删除或已不存在。">${gradeLog.courseName}（${gradeLog.courseCode}）<span style="color:red"> ×</span></#attempt></td>
            <td>${(gradeLog.status == 0)?string(START, (gradeLog.status == 1)?string(ADD, (gradeLog.status == 2)?string(EDIT, REMOVE)))}</td>
            <td>${(gradeLog.generateAt)?string("yyyy-MM-dd HH:mm:ss.SSS")}</td>
            <#attempt><td>${gradeLog.generateBy.userName}（${gradeLog.generateBy.name}）<#recover><td title="该操作人或用户已被删除或已不存在。">${gradeLog.userName}（${gradeLog.userCode}）<span style="color:red"> ×</span></#attempt></td>
            <#assign contextValue><@getContext gradeLog.context/></#assign>
            <td title="${(contextValue?trim?replace("<br>", "&#13;"))?default("")}" nowrap><span style="display:block;width:150px;overflow:hidden;text-overflow:ellipsis">${(contextValue?trim?replace("<br>", "\t\t"))?default("")}</span></td>
        </@>
    </@>
    <@htm.actionForm name="actionForm" action="gradeLog.do" entity="gradeLog" onsubmit="return false;"></@>
    <script>
        var bar = new ToolBar("bar", "成绩日志记录", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("查看", "info()", "detail.gif");
        bar.addItem("删除", "remove()");
    </script>
</body>
<#include "/templates/foot.ftl"/>
