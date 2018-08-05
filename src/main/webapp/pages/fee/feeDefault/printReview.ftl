<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
    <table id="bar"></table>
    <#assign indexStyle = RequestParameters['index']?default('')/>
    <@table.table width="100%" align="center" id="listTable" sortable="true">
        <@table.thead>
            <@table.td name="attr.index"/>
            <@table.td name="entity.studentType"/>
            <@table.td name="entity.department"/>
            <@table.td name="entity.speciality"/>
            <@table.td text="收费类别"/>
            <@table.td text="默认金额"/>
            <@table.td name="common.remark"/>
        </@>
        <@table.tbody datas=feeDefaults;feeDefault, feeDefault_index>
            <tr>
               <td align="right">${feeDefault_index + 1}</td>
               <td><@i18nName feeDefault.studentType?if_exists/></td>
               <td><@i18nName feeDefault.department?if_exists/></td>
               <td><@i18nName feeDefault.speciality?if_exists/></td>
               <td><@i18nName feeDefault.type?if_exists/></td>
               <td>${feeDefault.value}</td>
               <td>${feeDefault.remark?default('')}</td>
            </tr>
        </@>
    </@>
    <form method="post" action="" onsubmit="return false;" name="actionForm"></form>
    <script>
        var bar = new ToolBar("bar", "打印收费配置", null, true, true);
        bar.addPrint("<@msg.message key="action.print"/>");
    </script>
</BODY>
<#include "/templates/foot.ftl"/>