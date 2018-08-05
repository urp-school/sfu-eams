<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <table class="infoTable" width="100%">
        <tr>
            <td class="title">课程代码</td>
            <td class="content">${course.code}</td>
            <td class="title">课程名称</td>
            <td class="content">${course.name}</td>
        </tr>
        <tr>
            <td class="title">初设学分</td>
            <td class="content">${course.credits?default(0)}</td>
            <td class="title">初设周课时</td>
            <td class="content">${course.weekHour?default(0)}</td>
        </tr>
        <tr>
            <td class="title">初设学时</td>
            <td class="content">${course.extInfo.period?default(0)}</td>
            <td class="title">计划数</td>
            <td class="content" style="font-family:宋体,MiscFixed">${planCount[0] + planCount[1]}（专业：${planCount[0]}/个人：${planCount[1]}）</td>
        </tr>
        <tr>
            <td class="darkColumn" colspan="4" style="text-align:left;font-weight:bold">对应的培养计划详情</td>
        </tr>
        <#list results as plan>
        <tr>
            <td class="title" colspan="4" style="text-align:left;background-color:LightSteelBlue;">${plan_index + 1}、<span style="font-weight:bold;color:${(plan.std)?exists?string("red", "blue")}"><#if (plan.std)?exists>个人培养计划<#else>专业培养计划</#if></span></td>
        </tr>
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
            <td class="title">所设学期</td>
            <td class="content">${plan.getPlanCourse(course).termSeq}</td>
        </tr>
        <tr>
            <td class="title">所属课程组</td>
            <td class="content">${plan.getPlanCourse(course).courseGroup.courseType.name}</td>
            <td class="title">开课院系</td>
            <td class="content">${plan.getPlanCourse(course).teachDepart.name}</td>
        </tr>
        </#list>
    </table>
    <script>
        var bar = new ToolBar("bar", "统计结果详情", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
    </script>
</body>
<#include "/templates/foot.ftl"/>