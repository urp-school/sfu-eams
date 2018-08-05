<@table.table width="100%" id="listTable" sortable="true">
  <@table.thead>
      <@table.selectAllTd id="studyProductId"/>
      <@table.sortTd name="attr.stdNo" id="studyProduct.student.code"/>
      <@table.sortTd name="attr.personName" id="studyProduct.student.name" width="8%"/>
      <@table.sortTd text="论文名称" id="studyProduct.name" style="width:25%"/>
      <@table.sortTd text="刊物名称" id="studyProduct.publicationName" style="width=25%"/>
      <@table.sortTd width="15%" text="刊物等级" id="studyProduct.publicationLevel.name"/>
      <@table.sortTd text="通过审核" id="studyProduct.isPassCheck"  width="10%"/>
      <@table.td text="是否获奖" width="8%"/>
    </@>
    <@table.tbody datas=studyProducts;studyProduct>
       <@table.selectTd id="studyProductId" value=studyProduct.id/>
       <td>${studyProduct.student.code}</td>
       <td><A href="#" onclick="info(${studyProduct.id})"><@i18nName (studyProduct.student)?if_exists/></A></td>
       <td>${studyProduct.name?if_exists}</td>
       <td>${studyProduct.publicationName?if_exists}</td>
       <td><@i18nName (studyProduct.publicationLevel)?if_exists/></td>
       <td><#if studyProduct.isPassCheck?default(false)==true>通过审核<#else>未通过</#if>
       <td>${studyProduct.isAwarded?string("是","否")}</td>
     </@>
</@>