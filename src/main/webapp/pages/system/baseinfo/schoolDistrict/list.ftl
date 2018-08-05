<#include "/templates/head.ftl"/>
<BODY >
<script language="JavaScript" type="text/JavaScript" src="scripts/system/BaseInfo.js"></script>
  <@getMessage/>
  <@table.table width="100%" id="listTable" sortable="true">
    <@table.thead>
       <@table.td text=""/>
       <@table.sortTd width="10%" name="attr.code" id="schoolDistrict.code"/>
       <@table.sortTd width="10%" name="attr.infoname" id="schoolDistrict.name"/>
       <@table.sortTd width="10%" name="attr.engName" id="schoolDistrict.engName"/>
       <@table.sortTd width="10%" name="common.abbreviation" id="schoolDistrict.abbreviation"/>
       <@table.sortTd width="10%" name="attr.modifyAt" id="schoolDistrict.modifyAt"/>
    </@>
    <@table.tbody datas=schoolDistricts;district>
       <@table.selectTd type="radio" id="schoolDistrictId" value="${district.id}"/>
       <td>${district.code?if_exists}</td>
       <td><a href="schoolDistrict.do?method=info&schoolDistrict.id=${district.id}">${district.name?if_exists}</a></td>
       <td>${district.engName?if_exists}</td>
       <td>${district.abbreviation?if_exists}</td>
       <td>${(district.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>
    </@>
  </@>
  <script language="javascript">
   type="schoolDistrict";
   defaultOrderBy="${RequestParameters['orderBy']?default('null')}";
  </script>
  </body>
<#include "/templates/foot.ftl"/>