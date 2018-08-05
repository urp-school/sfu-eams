<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <table class="infoTable" width="100%">
        <tr>
            <td class="title">日志编号：</td>
            <td>#${gradeLog.id?string("000000000000000000")}</td>
            <td class="title">学生：</td>
            <td><#attempt>${gradeLog.student.name}（${gradeLog.student.code}）<#recover>${gradeLog.stdName}（${gradeLog.stdCode}）<br><span style="color:red">该学生已被删除或已不存在。</span></#attempt></td>
        </tr>
        <#include "declareParams.ftl"/>
        <tr>
        <#assign stdTypeValue><#attempt><#assign testValue = gradeLog.stdType.name/><#recover><br><span style="color:red">学生类别已被删除或已不存在。</span></#attempt></#assign>
        <#assign calendarValue = gradeLog.calendarValue?split(" ")/>
            <td class="title">教学日历：</td>
            <td>${calendarValue[0]}&nbsp;<#attempt>${gradeLog.calendar.year}&nbsp;${gradeLog.calendar.term}${stdTypeValue}<#recover>${calendarValue[1]}&nbsp;${calendarValue[2]}${stdTypeValue}<br><span style="color:red">该教学日历已被删除或已不存在。</span></#attempt></td>
            <td class="title" title="即，记录生成原因。">记录状态：</td>
            <td>${(gradeLog.status == 0)?string(START, (gradeLog.status == 1)?string(ADD, (gradeLog.status == 2)?string(EDIT, REMOVE)))}</td>
        </tr>
        <tr>
            <td class="title">任务序号：</td>
            <td><#attempt>${gradeLog.taskSeqNo}<#recover><#attempt><#assign courseTemp = gradeLog.course.code/>----<br><span style="color:Brown">该成绩的课程没有对应的教学任务。</span><#recover>----<br><span style="color:red">该教学任务可能已被删除，或已不存在。</span></#attempt></#attempt></td>
            <td class="title">教学课程：</td>
            <td><#attempt>${gradeLog.course.name}（${gradeLog.course.code}）<#recover>${gradeLog.courseName}（${gradeLog.courseCode}）<br><span style="color:red">该课程已被删除或已不存在。</span></#attempt></td>
        </tr>
        <tr>
            <td class="title">记录时间：</td>
            <td>${(gradeLog.generateAt)?string("yyyy-MM-dd HH:mm:ss.SSS")}</td>
            <td class="title">记录操作人：</td>
            <td><#attempt>${gradeLog.generateBy.userName}（${gradeLog.generateBy.name}）<#recover>${gradeLog.userName}（${gradeLog.userCode}）<br><span style="color:red">该课程已被删除或已不存在。</span></#attempt></td>
        </tr>
        <tr>
            <td class="title">详细描述/说明：</td>
            <#assign contextValue><@getContext gradeLog.context/></#assign>
            <td colspan="3">${(contextValue)?default("")?trim}</td>
        </tr>
    </table>
    <script>
        var bar = new ToolBar("bar", "<font color=\"blue\">#${gradeLog.id?string("000000000000000000")}</font>详细成绩日志", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
    </script>
</body>
<#include "/templates/foot.ftl"/>