<#include "/templates/head.ftl"/>
<body>
<table id="alterationBar"></table>
 <@table.table align="center" width="100%">
  <@table.thead>
     <td><@msg.message key="std.studentStatusAlteration.type"/></td>
     <td><@msg.message key="std.studentStatusAlteration.on"/></td>
     <td><@msg.message key="std.studentStatusAlteration.reason"/></td>
     <td><@msg.message key="attr.remark"/></td>
   </@>
   <@table.tbody datas=alterations?sort_by("alterBeginOn");record>
    <td><@i18nName record.mode/></td>
    <td>${record.alterBeginOn}</td>
    <td><@i18nName record.reason?if_exists/></td>
    <td>${record.remark?if_exists}</td>
   </@>
 </@>
</body>
<script>
	var bar = new ToolBar('alterationBar','<@msg.message key="std.studentStatusAlteration.title"/>',null,true,true);
	bar.setMessage('<@getMessage/>');
	bar.addPrint("<@msg.message key="action.print"/>");
</script>
<#include "/templates/foot.ftl"/>