<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <#if errorMessage == "FFFF">
        <#assign messageInfo = "所选的教学任务不是同一个课程代码。"/>
        <#assign ideaInfo = "选择相同课程代码的教学任务。"/>
    <#elseif errorMessage == "0001">
        <#assign messageInfo = ""/>
    <#elseif errorMessage == "0002">
        <#assign messageInfo = ""/>
    </#if>
    <table>
        <tr>
            <td>失败原因：<font color="red">${messageInfo}</font></td>
        </tr>
        <tr>
            <td>处理方法：<font color="blue">${ideaInfo}</font></td>
        </tr>
    </table>
    <script>
        var bar = new ToolBar("bar", "分组失败", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
    </script>
</body>
<#include "/templates/foot.ftl"/>