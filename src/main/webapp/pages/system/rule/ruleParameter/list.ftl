<#include "/templates/head.ftl"/>
 <body>
 <table id="param"></table>
 <@table.table width="100%" id="sortTable">
   <@table.thead>
          <@table.selectAllTd id="paramId"/>
          <td width="15%">参数名称</td>
          <td width="15%">参数标题</td>
          <td width="15%">参数描述</td>
          <td width="10%">参数类型</td>
          <td width="20%">上级参数</td>
          <td width="15%">规则名称</td>  
   		  </@>
   <@table.tbody datas=ruleParams?if_exists;param>
      <@table.selectTd id="paramId" value=param.id/>
      <td>${(param.name)?if_exists}</td>
      <td>${(param.title)?if_exists}</td>
      <td>${(param.description)?if_exists}</td>            
	  <td>${(param.type)?if_exists}</td>
	  <td>${(param.parent.name)?if_exists}</td>
	  <td>${(param.rule.name)?if_exists}</td>
   </@>
 </@>
 
  <@htm.actionForm name="actionForm" action="ruleParameter.do" entity="param">
  <input type="hidden" value="${ruleId?if_exists}" name="ruleId"/>
  <input type="hidden" value="${ruleId?if_exists}" name="params"/>
  </@>
 <script>
    var bar = new ToolBar('param','&nbsp;规则参数列表',null,true,true);
    var form =document.actionForm;
    bar.setMessage('<@getMessage/>');
    bar.addItem("<@bean.message key="action.add"/>","add()");
    bar.addItem("<@bean.message key="action.modify"/>","edit()");
    bar.addItem("<@bean.message key="action.delete"/>","remove()");
    bar.addBack();
 </script>
 </body>
 <#include "/templates/foot.ftl"/>