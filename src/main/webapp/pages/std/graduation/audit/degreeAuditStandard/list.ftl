<#include "/templates/head.ftl"/>
<body >
<table id="myBar" width="100%"></table>
<@table.table id="listTable" sortable="true" width="100%">
 <@table.thead>
      <@table.selectAllTd id="standardId"/>
      <@table.td text="名称"/>
      <@table.sortTd id="standard.stdType" width="20%" name="entity.studentType"/>
      <@table.sortTd id="standard.majorType.id" width="20%" text="专业类别"/>
  </@>
  <@table.tbody datas=standards;standard>
      <@table.selectTd id="standardId" value=standard.id/>
      <td><A href="#" onclick="info(${standard.id})">${standard.name}</A></td>
      <td><@i18nName standard.stdType/></td>
      <td><@i18nName standard.majorType/></td>
  </@>
</@>
<@htm.actionForm name="actionForm" action="degreeAuditStandard.do" entity="standard"></@>
<script>
 var bar = new ToolBar("myBar","学位审核标准列表",null,true,true);
 bar.setMessage('<@getMessage/>');
 bar.addItem("<@msg.message key="action.info"/>","info()", "detail.gif");
 bar.addItem("<@msg.message key="action.edit"/>","ruleConfig()");
 bar.addItem("<@msg.message key="action.new"/>","ruleConfig()");
 bar.addItem("<@msg.message key="action.delete"/>","remove()","delete.gif");
 
 function ruleConfig(){
    var selectId = getSelectIds("standardId");
    form.action = "degreeAuditStandard.do?method=ruleConfig_1st";
    addHiddens(form,resultQueryStr);
    addInput(form,'standardId',selectId,"hidden");
    addParamsInput(form,resultQueryStr);
    form.submit();
 }
</script>
</body>
<#include "/templates/foot.ftl"/> 