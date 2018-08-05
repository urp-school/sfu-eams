<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <table class="infoTable" width="100%">
        <#if (plan.std)?exists>
        <tr>
            <td class="title">学生学号</td>
            <td class="content">${plan.std.code}</td>
            <td class="title">学生姓名</td>
            <td class="content">${plan.std.name}</td>
        </tr>
        </#if>
        <tr>
            <td class="title">所在年级</td>
            <td class="content">${plan.enrollTurn}</td>
            <td class="title">学生类别</td>
            <td class="content">${plan.stdType.name}</td>
        </tr>
        <tr>
            <td class="title">院系</td>
            <td class="content">${plan.department.name}（${plan.department.code}）</td>
            <td class="title">专业</td>
            <td class="content"><#if plan.speciality?exists>${plan.speciality.name}（${plan.speciality.code}）</#if></td>
        </tr>
        <tr>
            <td class="title">专业方向</td>
            <td class="content"><#if plan.aspect?exists>${plan.aspect.name}（${plan.aspect.code}）</#if></td>
            <td class="title">开课数/总课数</td>
            <#assign resultValue = (detailMap?keys?size)?default(0)/>
            <td class="content">${resultValue}/${plan.planCourses?size}</td>
        </tr>
        <#list plan.courseGroups?sort_by(["courseType", "code"]) as group>
        <tr>
            <td class="darkColumn" colspan="4" style="text-align:left;font-weight:bold">${group.courseType.name}</td>
        </tr>
            <#if !group.planCourses?exists || group.planCourses?size == 0>
        <tr>
            <td class="content" colspan="4">无课程</td>
        </tr>
            <#else>
                <#list group.planCourses as planCourse>
        <tr>
            <td class="title" colspan="2">${planCourse.course.name}（${planCourse.course.code}）</td>
            <td class="content" colspan="2"><#if !detailMap[planCourse.course.id?string]?exists || detailMap[planCourse.course.id?string]?int == 0>未开课<#else><font color="blue">已开课</font></#if></td>
        </tr>
                </#list>
            </#if>
        </#list>
    </table>
    <script>
        var bar = new ToolBar("bar", "统计结果详情<font color=\"blue\"><#if (plan.std)?exists>（个人）<#else>（专业）</#if></font>", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
    </script>
</body>
<#include "/templates/foot.ftl"/>