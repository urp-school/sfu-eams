<#include "/templates/head.ftl"/>
<body>
<table id="filePrintApplicationBar"></table>
<@table.table id="listTable" width="100%" sortable="true">
  <@table.thead>
      <@table.selectAllTd id="filePrintApplicationId"/>   
      <td width="10%">编号</td>
      <td width="15%">请印人代码/名称</td>
      <td width="25%">请印内容</td>
      <td width="13%">审核状态</td>
      <td width="10%">是否完成</td>
      <td width="5%">印量</td>
      <td width="7%">总费用</td>
      <td width="15%">经办人</td>
    </@>
    <@table.tbody datas=filePrintApplicationList;filePrintApplication>
     <@table.selectTd id="filePrintApplicationId" value=filePrintApplication.id/>
         <input type="hidden" name="${filePrintApplication.id}" id="${filePrintApplication.id}" />
     </td>
     <td>${(filePrintApplication.id)?if_exists}</td>
     <td><@i18nName (filePrintApplication.applyBy)?if_exists/>/${(filePrintApplication.applyBy.userName)?if_exists}</td>
     <td><a href='filePrintApply.do?method=download&filePrintApplicationId=${(filePrintApplication.id)?if_exists}'>${(filePrintApplication.fileName)?if_exists}</a></td>
     <td>${(filePrintApplication.auditState?string("审核通过","审核未通过"))?default("待审核")}</td>
     <td>${(filePrintApplication.filePrintState?string("已完成","未完成"))?default("")}</td>
     <td>${(filePrintApplication.value)?if_exists}</td>
     <td>${(filePrintApplication.payed)?if_exists}</td>
     <td><@i18nName (filePrintApplication.managerBy)?if_exists/></td>
    </@>
  </@>
  </body>
  <@htm.actionForm name="actionForm" entity="filePrintApplication" action="filePrintApply.do">
  	<input type="hidden" value="${calendar.id}" name="calendar.id"/>
  </@>
<script>
    var bar=new ToolBar("filePrintApplicationBar","请印申请列表",null,true,true);
  	bar.setMessage('<@getMessage />');
    bar.addItem("<@bean.message key="action.add"/>","add()");
  	bar.addItem("<@bean.message key="action.modify"/>","edit()");
  	bar.addItem("<@bean.message key="action.delete"/>","remove()");
</script>
<#include "/templates/foot.ftl"/>