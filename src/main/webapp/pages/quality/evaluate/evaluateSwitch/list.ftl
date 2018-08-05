<#include "/templates/head.ftl"/>
<BODY>
 <table id="backBar" width="100%"></table>
 <@table.table   width="100%" id="listTable" sortable="true">
	<@table.thead>
      <@table.selectAllTd id="switchId"/>
      <@table.sortTd  text="学生类别" id="switch.calendar.studentType.name"/>
      <@table.sortTd  text="学年度" id="switch.calendar.year"/>
      <@table.sortTd  text="学期" id="switch.calendar.term"/>
      <@table.sortTd  text="是否开放"  id="switch.isOpen"/>
      <@table.sortTd  text="开始时间"  id="switch.openAt"/>
      <@table.sortTd  text="结束时间"  id="switch.closeAt"/>
    </@>
    <@table.tbody datas=switchs; switch>
    	<@table.selectTd id="switchId" value=switch.id/>
    	<td><@i18nName switch.calendar.studentType/></td>
    	<td><A href="#" onclick="info(${switch.id})">${switch.calendar.year}</A></td>
    	<td>${switch.calendar.term}</td>
    	<td>${switch.isOpen?string("开放","关闭")}</td>
    	<td><#if switch.openAt?exists>${switch.openAt?string("yyyy-MM-dd-HH:mm")}</#if></td>
    	<td><#if switch.closeAt?exists>${switch.closeAt?string("yyyy-MM-dd-HH:mm")}</#if></td>
    </@>
</@>
<@htm.actionForm name="actionForm" entity="switch" action="evaluateSwitch.do"/>
<script>
	var bar = new ToolBar('backBar','查询结果列表',null,true,true);
   	bar.setMessage('<@getMessage/>');
   	bar.addItem("<@msg.message key="action.info"/>","info()", "detail.gif");
   	bar.addItem("<@msg.message key="action.new"/>","add()");
   	bar.addItem("<@msg.message key="action.edit"/>","edit()");
   	bar.addItem("<@msg.message key="action.delete"/>","remove()"); 
</script>
</body>
<#include "/templates/foot.ftl"/>