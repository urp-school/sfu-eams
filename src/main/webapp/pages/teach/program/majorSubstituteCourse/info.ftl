<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <table class="infoTable">
        <tr>
            <td class="title">学生类别</td>
            <td class="content">${majorCourse.stdType.name}（${majorCourse.stdType.code}）</td>
            <td class="title">年级</td>
            <td class="content">${majorCourse.enrollTurn}</td>
        </tr>
        <tr>
            <td class="title">专业</td>
            <td class="content"><#if majorCourse.major?exists>${majorCourse.major.name}（${majorCourse.major.code}）</#if></td>
            <td class="title">院系</td>
            <td class="content"><#if majorCourse.department?exists>${majorCourse.department.name}（${majorCourse.department.code}）</#if></td>
        </tr>
        <tr>
            <td class="title">专业方向</td>
            <td class="content"><#if majorCourse.majorField?exists>${majorCourse.majorField.name}（${majorCourse.majorField.code}）</#if></td>
            <td class="title">操作人</td>
            <td class="content">${majorCourse.operateBy.userName}（${majorCourse.operateBy.name}）</td>
        </tr>
        <tr>
            <td class="title">制定时间</td>
            <td class="content">${majorCourse.createAt?string("yyyy-MM-dd HH:mm:ss")}</td>
            <td class="title">修改时间</td>
            <td class="content">${(majorCourse.modifyAt?string("yyyy-MM-dd HH:mm:ss"))?if_exists}</td>
        </tr>
        <tr>
            <td class="title">备注：</td>
            <td class="content" colspan="3">${majorCourse.remark?if_exists?html}</td>
        </tr>
    </table>
    <table class="infoTable">
        <tr>
            <td class="content" style="text-align:center;"><#list majorCourse.origins as origin>${origin.name}（${origin.code}）<#if origin_has_next>, </#if></#list></td>
            <td class="title" style="text-align:center;font-weight:bold;width:10%">替换</td>
            <td class="content" style="text-align:center;"><#list majorCourse.substitutes as substitute>${substitute.name}（${substitute.code}）<#if substitute_has_next>, </#if></#list></td>
        </tr>
    </table>
    <script>
        var bar = new ToolBar("bar", "专业替代详细信息", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
    </script>
</body>
<#include "/templates/foot.ftl"/>