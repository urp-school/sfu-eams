<#include "/templates/head.ftl"/>
<body>
<table id="bar"></table>
 <@table.table width="100%" align="center">
   <@table.thead>
     <@table.td name="std.awardsPunishs.personal.rewards.name"/>
     <@table.td name="std.awardsPunishs.personal.rewards.type"/>
     <@table.td name="std.awardsPunishs.personal.rewards.presentOn"/>
     <@table.td name="std.awardsPunishs.personal.rewards.presentDepartment"/>
     <@table.td name="std.awardsPunishs.personal.rewards.validity"/>
   </@>
   <@table.tbody datas=awards;award>
     <td><@i18nName award/></td>
     <td><@i18nName award.type/></td>
     <td>${award.presentOn}</td>
     <td>${award.depart}</td>
     <td><#if award.isValid?default(true)><@msg.message key="entity.available"/><#else><@msg.message key="entity.unavailable"/></#if><#if !award.isValid?default(true)><#if award.withdrawOn?exists>&nbsp;<@msg.message key="std.awardsPunishs.personal.rewards.withdrawOn"/>：${award.withdrawOn?string("yyyy-MM-dd")}</#if></#if></td>
   </@>
 </@>
 </body>
 <script>
   var bar=new ToolBar("bar","<@msg.message key="std.awardsPunishs.personal.rewards.title"/>",null,true,true);
   bar.addPrint("<@msg.message key="action.print"/>");
</script>
<#include "/templates/foot.ftl"/>