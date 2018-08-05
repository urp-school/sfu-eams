<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <table class="infoTable">
        <tr>
            <td class="title">学生</td>
            <td class="content">${substitutionCourse.std.name}（${substitutionCourse.std.code}）</td>
            <td class="title">年级</td>
            <td class="content">${substitutionCourse.std.enrollYear}</td>
        </tr>
        <tr>
            <td class="title">专业</td>
            <td class="content"><#if substitutionCourse.std.firstMajor?exists>${substitutionCourse.std.firstMajor.name}（${substitutionCourse.std.firstMajor.code}）</#if></td>
            <td class="title">院系</td>
            <td class="content"><#if substitutionCourse.std.department?exists>${substitutionCourse.std.department.name}（${substitutionCourse.std.department.code}）</#if></td>
        </tr>
        <tr>
            <td class="title">专业方向</td>
            <td class="content"><#if substitutionCourse.std.firstAspect?exists>${substitutionCourse.std.firstAspect.name}（${substitutionCourse.std.firstAspect.code}）</#if></td>
            <td class="title">操作人</td>
            <td class="content">${substitutionCourse.operateBy.userName}（${substitutionCourse.operateBy.name}）</td>
        </tr>
        <tr>
            <td class="title">制定时间</td>
            <td class="content">${substitutionCourse.createAt?string("yyyy-MM-dd HH:mm:ss")}</td>
            <td class="title">修改时间</td>
            <td class="content">${(substitutionCourse.modifyAt?string("yyyy-MM-dd HH:mm:ss"))?if_exists}</td>
        </tr>
        <tr>
            <td class="title">备注：</td>
            <td class="content" colspan="3">${substitutionCourse.remark?if_exists?html}</td>
        </tr>
    </table>
    <table class="infoTable">
        <tr>
            <td class="content" style="text-align:center;"><#list substitutionCourse.origins as origin>${origin.name}（${origin.code}）【${origin.credits?if_exists}分：${origin.extInfo.courseType.name?if_exists}】<#if origin_has_next>, </#if></#list></td>
            <td class="title" style="text-align:center;font-weight:bold;width:10%">替换</td>
            <td class="content" style="text-align:center;"><#list substitutionCourse.substitutes as substitute>${substitute.name}（${substitute.code}）【${substitute.credits?if_exists}分：${substitute.extInfo.courseType.name?if_exists}】<#if substitute_has_next>, </#if></#list></td>
        </tr>
    </table>
    <script>
        var bar = new ToolBar("bar", "个人替代详细信息", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
    </script>
</body>
<#include "/templates/foot.ftl"/>