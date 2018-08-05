<#include "/templates/head.ftl"/>
<script language="javascript">
    window.name="list";
</script>
<BODY>
	<table id="backBar" width="100%"></table>
    <@table.table align="center" width="100%">
        <@table.thead>
            <@table.td name="instructWorkload.studentType"/>
            <@table.td name="instructWorkload.modulus"/>
            <@table.td text="毕业系数类别"/>
            <@table.td name="instructWorkload.createDepart"/>
            <@table.td name="attr.remark"/>
        </@>
        <@table.tbody datas=instructModuli;instructModulus>
            <td>${(instructModulus.studentType.name)?default('')}</td>
            <td>${(instructModulus.modulusValue?string("##.00"))?default('')}</td>
            <td><#if instructModulus.itemType=='thesis'>论文指导<#elseif instructModulus.itemType=='practice'>实习指导<#else>平时指导</#if></td>
            <td>${(instructModulus.department.name)?default('')}</td>
            <td>${(instructModulus.remark)?default('')}</td>
        </@>
    </@>
	<script>
	   var bar = new ToolBar('backBar','<@bean.message key="instructWorkload.modulusList"/>',null,true,true);
	   bar.addClose("<@msg.message key="action.close"/>");
	</script>
</body>
<#include "/templates/foot.ftl"/>