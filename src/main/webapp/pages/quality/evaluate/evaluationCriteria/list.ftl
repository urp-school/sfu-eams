<#include "/templates/head.ftl"/>
 <body>
  <table id="bar" width="100%"></table>
   <@table.table width="100%" id="listTable">
	 <@table.thead>
	   <@table.selectAllTd id="evaluationCriteriaId"/>
	   <@table.td width="10%" id="evaluationCriteria.name" text="名称" />
	   <@table.td width="80%" text="对应内容"/>
	 </@>
	 <@table.tbody datas=evaluationCriterias;evaluationCriteria>
	    <@table.selectTd id="evaluationCriteriaId" value=evaluationCriteria.id/>
	    <td>${evaluationCriteria.name}</td>
	    <td><#list evaluationCriteria.criteriaItems?sort_by("min") as item>${item.name}[${item.min}~${item.max})&nbsp;&nbsp;</#list></td>
	 </@>
   </@>
 <@htm.actionForm name="actionForm" action="evaluationCriteria.do" entity="evaluationCriteria"/>
<script>
   var bar = new ToolBar('bar','评价标准列表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message key="action.add"/>","add()");
   bar.addItem("<@msg.message key="action.edit"/>","edit()");
   bar.addItem("<@msg.message key="action.delete"/>","remove()");
   function query(){
      var form=document.searchForm;
      form.action="evaluationCriteria.do?method=search";
      form.submit();
   }
</script>
 </body>
<#include "/templates/foot.ftl"/>