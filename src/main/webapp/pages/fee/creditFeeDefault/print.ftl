<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
    <table id="bar"></table>
    <@table.table align="center" width="100%" id="creditFeeDefault">
        <@table.thead>
            <@table.td name="attr.index"/>
            <@table.td name="entity.courseType"/>
            <@table.td name="entity.studentType"/>
            <@table.td text="学费（分/元）"/>
        </@>
        <@table.tbody datas=creditFeeDefaults;creditFeeDefault, creditFeeDefault_index>
            <td align="right">${creditFeeDefault_index + 1}</td>
            <td><@i18nName creditFeeDefault.courseType?if_exists/></td>
            <td><@i18nName creditFeeDefault.stdType?if_exists/></td>
            <td>${(creditFeeDefault.value?string("0.00"))?default("0.00")}</td>
        </@>
    </@>
    <script>
        var form = document.actionForm;

        var bar = new ToolBar("bar", "学分收费标准", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addPrint("<@msg.message key="action.print"/>");
        bar.addClose("<@msg.message key="action.close"/>");
    </script>
<#include "/templates/foot.ftl"/>