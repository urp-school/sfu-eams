<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
	<table id="backBar"></table>
    <@table.table align="center" width="100%" sortable="true" id="listTable">
        <@table.thead>
            <@table.selectAllTd id="instructModulusId"/>
            <@table.sortTd name="instructWorkload.studentType" id="instructModulus.studentType.name"/>
            <@table.sortTd name="instructWorkload.modulus"  id="instructModulus.modulusValue"/>
            <@table.sortTd text="毕业系数类别" id="instructModulus.itemType"/>
            <@table.sortTd name="instructWorkload.createDepart" id="instructModulus.department.name" />
            <@table.td name="attr.remark"/>
        </@>
        <@table.tbody datas=instructModuli;instructModulus>
            <@table.selectTd id="instructModulusId" value=instructModulus.id/>
            <td>${(instructModulus.studentType.name)?default('')}</td>
            <td>${(instructModulus.modulusValue?string("##.00"))?default('')}</td>
            <td><#if instructModulus.itemType=='thesis'>毕业指导<#elseif instructModulus.itemType=='practice'>实习指导<#else>平时指导</#if></td>
            <td>${(instructModulus.department.name)?default('')}</td>
            <td>${(instructModulus.remark)?default('')}</td>
        </@>
    </@>
    <@htm.actionForm method="post" entity="instructModulus" action="instructModulus.do" name="graduateModuleForm"/>
    <script>
        var bar = new ToolBar('backBar','<@bean.message key="instructWorkload.modulusList"/>',null,true,true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("<@msg.message key="action.add"/>","add()");
        bar.addItem("<@msg.message key="action.edit"/>","edit()");
        bar.addItem("<@msg.message key="action.delete"/>","remove()");
    </script>
</body>
<#include "/templates/foot.ftl"/>