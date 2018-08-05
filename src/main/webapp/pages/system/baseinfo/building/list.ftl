<#include "/templates/head.ftl"/>
<BODY>
  <script language="JavaScript" type="text/JavaScript" src="scripts/system/BaseInfo.js"></script>
  <@getMessage/>
  <@table.table width="100%"  id="listTable" sortable="true">
    <@table.thead>
       <@table.td text=""/>
       <@table.sortTd name="attr.code" id="building.code"/>
       <@table.sortTd name="attr.infoname" id="building.name"/>
       <@table.sortTd name="common.schoolDistrict" id="building.schoolDistrict.name"/>
       <@table.sortTd name="common.abbreviation" width="10%" id="building.abbreviation"/>
       <@table.sortTd name="attr.engName" width="10%" id="building.engName" />
       <@table.sortTd name="attr.modifyAt" width="10%" id="building.modifyAt" />
    </@>
    <@table.tbody datas=buildings;building>
       <@table.selectTd id="buildingId" value=building.id type="radio"/>
       <td>${building.code}</td>
       <td><a href="building.do?method=info&building.id=${building.id}">${building.name?if_exists}</a></td>
       <td><@i18nName building.schoolDistrict/></td>
       <td>${building.abbreviation?if_exists}</td>
       <td>${building.engName?if_exists}</td>
       <td>${(building.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>
    </@>
   </@>
  <script language="javascript">
   type="building";
   defaultOrderBy="${RequestParameters['orderBy']?default('null')}";
  </script>
</body>
<#include "/templates/foot.ftl"/>