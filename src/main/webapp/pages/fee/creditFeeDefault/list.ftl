<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
    <table id="bar"></table>
    <@table.table align="center" width="100%" sortable="true" id="creditFeeDefault">
        <@table.thead>
            <@table.selectAllTd id="creditFeeDefaultId"/>
            <@table.sortTd name="entity.studentType" id="creditFeeDefault.stdType.code"/>
            <@table.sortTd name="entity.courseType" id="creditFeeDefault.courseType.code"/>
            <@table.sortTd text="学费（分/元）" id="creditFeeDefault.value"/>
        </@>
        <@table.tbody datas=creditFeeDefaults;creditFeeDefault>
            <@table.selectTd id="creditFeeDefaultId" value=creditFeeDefault.id/>
            <td><@i18nName creditFeeDefault.stdType?if_exists/></td>
            <td><@i18nName creditFeeDefault.courseType?if_exists/></td>
            <td>${(creditFeeDefault.value?string("0.00"))?default("0.00")}</td>
        </@>
    </@>
    <@htm.actionForm  action="creditFeeDefault.do" name="actionForm" entity="creditFeeDefault"/>
    <script>
        var form = document.actionForm;

        var bar = new ToolBar("bar", "学分收费标准", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("<@msg.message key="action.new"/>", "form.target='';add()");
        bar.addItem("<@msg.message key="action.edit"/>", "form.target='';edit()");
        bar.addItem("<@msg.message key="action.delete"/>", "form.target='';remove()");
        bar.addItem("打印预览", "print()");
        
        function print() {
            form.action = "creditFeeDefault.do?method=print";
            form.target = "_blank";
            form.submit();
        }
    </script>
<#include "/templates/foot.ftl"/>