<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <@table.table id="modifyInfo" width="100%">
        <@table.thead>
            <@table.td text="名称"/>
            <@table.td text="代码"/>
            <@table.td text="操作"/>
        </@>
        <@table.tbody datas=alteration.getContents("planCourses");content>
            <td>${content[0]}</td>
            <td>${content[1]}</td>
            <td>${content[2]}</td>
        </@>
    </@>
    <#assign status><#if alteration.happenStatus == 1><font color="blue">新建</font><#elseif alteration.happenStatus == 2>调整/修改<#else><font color="red">删除</font></#if></#assign>
    <script>
        var bar = new ToolBar("bar", "课程 － 培养计划日志详细信息（${status?js_string}）", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
    </script>
</body>
<#include "/templates/foot.ftl"/>