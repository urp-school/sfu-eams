<#include "/templates/head.ftl"/>
<BODY>
  <table id="ruleBar"></table>
  <@table.table width="100%"  id="listTable" sortable="true">
    <@table.thead>
       <@table.td text=""/>
       <@table.sortTd name="attr.infoname" id="rule.name"/>
       <@table.sortTd text="适用业务" id="rule.business"/>
       <@table.sortTd text="管理容器" width="10%" id="rule.factory"/>
       <@table.sortTd text="服务名" width="20%" id="rule.serviceName" />
       <@table.sortTd name="attr.modifyAt" width="10%" id="rule.updatedAt" />
       <@table.sortTd name="attr.state" width="10%" id="rule.enabled" />
    </@>
    <@table.tbody datas=rules;rule>
       <@table.selectTd id="ruleId" value=rule.id type="radio"/>
       <td><a href="rule.do?method=info&ruleId=${rule.id}">${rule.name?if_exists}</a></td>
       <td>${rule.business?if_exists}</td>
       <td>${rule.factory?if_exists}</td>
       <td>${rule.serviceName?if_exists}</td>
       <td>${(rule.updatedAt?string("yyyy-MM-dd"))?if_exists}</td>
       <td>${rule.enabled?string("启用","禁用")?if_exists}</td>
    </@>
   </@>
   <@htm.actionForm name="actionForm" entity="rule" action="rule.do">
   </@>
  <script language="javascript">
    var bar=new ToolBar("ruleBar","规则列表",null,true,true);
  	bar.setMessage('<@getMessage />');
  	bar.addItem("<@bean.message key="action.info"/>","info()");
    bar.addItem("<@bean.message key="action.add"/>","add()");
  	bar.addItem("<@bean.message key="action.modify"/>","edit()");
  	bar.addItem("<@bean.message key="action.delete"/>","remove()");
  	bar.addItem("管理规则参数","ruleParameter()");
  	
  	function ruleParameter(){
		var ids = getSelectIds("ruleId");
	 	if (ids == null || ids == "") {
	 		alert("你没有选择要操作的记录！");
	 		return;
	 	}
	 	
	    form.action = "ruleParameter.do?method=search";
	    addParamsInput(form, resultQueryStr);
	    submitId(form, 'ruleId', false);
   	}
  </script>
</body>
<#include "/templates/foot.ftl"/>