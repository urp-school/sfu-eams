<#include "/templates/head.ftl"/>
<body>
<table id="bar"></table>
 <@table.table width="100%" align="center">
   <@table.thead>
     <@table.td name="std.awardsPunishs.personal.punishments.name"/>
     <@table.td name="std.awardsPunishs.personal.punishments.type"/>
     <@table.td name="std.awardsPunishs.personal.punishments.presentOn"/>
     <@table.td name="std.awardsPunishs.personal.punishments.presentDepartment"/>
     <@table.td name="std.awardsPunishs.personal.punishments.validity"/>
   </@>
   <@table.tbody datas=punishs;punish>
     <td><@i18nName punish/></td>
     <td><@i18nName punish.type/></td>
     <td>${punish.presentOn}</td>
     <td>${punish.depart}</td>
     <td><#if punish.isValid?default(true)><@msg.message key="entity.available"/><#else><@msg.message key="entity.unavailable"/></#if><#if !punish.isValid?default(true)><#if punish.withdrawOn?exists>&nbsp;<@msg.message key="std.awardsPunishs.personal.punishments.withdrawOn"/>：${award.withdrawOn?string("yyyy-MM-dd")}</#if></#if></td>
   </@>
 </@>
 <script>
   var bar=new ToolBar("bar","<@msg.message key="std.awardsPunishs.personal.punishments.title"/>",null,true,true);
   bar.addPrint("<@msg.message key="action.print"/>");
</script>
<#include "/templates/foot.ftl"/>