<#include "/templates/head.ftl"/>
 <body>
  <table id="bar" width="100%"></table>
   <@table.table width="100%" id="listTable" sortable="true">
	 <@table.thead>
	   <@table.selectAllTd id="optionGroupId"/>
	   <@table.sortTd id="optionGroup.name" name="attr.name"/>
	   <@table.sortTd id="optionGroup.depart.name" text="制作部门"/>
	   <@table.td width="60%" text="选项"/>
	 </@>
	 <@table.tbody datas=optionGroups;optionGroup>
	    <@table.selectTd id="optionGroupId" value=optionGroup.id/>
	    <td>${optionGroup.name}</td>
	    <td><@i18nName optionGroup.depart/></td>
	    <td><#list optionGroup.options?sort_by("proportion")?reverse as option>${option.name}(${option.proportion?default(0)})&nbsp;</#list></td>
	 </@>
   </@>
 <@htm.actionForm name="actionForm" action="optionGroup.do" entity="optionGroup"/>
<script>
   var bar = new ToolBar('bar','选项组列表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message key="action.add"/>","add()");
   bar.addItem("<@msg.message key="action.edit"/>","edit()");
   bar.addItem("<@msg.message key="action.delete"/>","remove()");
</script>
 </body>
<#include "/templates/foot.ftl"/>