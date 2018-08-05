<#include "/templates/head.ftl"/>
<body>
<table id="filePrintApplicationBar"></table>
<@table.table id="listTable" width="100%" sortable="true">
  <@table.thead>
      <@table.selectAllTd id="filePrintMaterialId"/>   
      <td width="15%">耗材代码</td>
      <td width="15%">耗材名称</td>
      <td width="15%">耗材数量</td>
      <td width="10%">单价</td>
      <td width="15%">总费用</td>
      <td width="15%">备注</td>
    </@>
    <@table.tbody datas=filePrintMaterials;filePrintMaterial>
     <@table.selectTd id="filePrintMaterialId" value=filePrintMaterial.id/>
         <input type="hidden" name="${filePrintMaterial.id}" id="${filePrintMaterial.id}" />
     </td>
     <td>${(filePrintMaterial.materialCode)?if_exists}</td>
     <td>${(filePrintMaterial.materialName)?if_exists}</td>
     <td>${(filePrintMaterial.value)?if_exists}</td>
     <td>${(filePrintMaterial.payedOne)?if_exists}</td>
     <td>${(filePrintMaterial.payed)?if_exists}</td>
     <td>${(filePrintMaterial.remark)?if_exists}</td>
    </@>
  </@>
  </body>
  <@htm.actionForm name="actionForm" entity="filePrintMaterial" action="filePrintManage.do">
  <input type="hidden" value="${filePrintApplicationId}" name="filePrintApplicationId"/>
  </@>
<script>
    var bar=new ToolBar("filePrintApplicationBar","请印耗材列表",null,true,true);
  	bar.setMessage('<@getMessage />');
    bar.addItem("<@bean.message key="action.add"/>","add()");
  	bar.addItem("<@bean.message key="action.modify"/>","edit()");
  	bar.addItem("<@bean.message key="action.delete"/>","remove()");
</script>
<#include "/templates/foot.ftl"/>