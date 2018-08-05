<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<script language="JavaScript" type="text/JavaScript" src="scripts/system/BaseInfo.js"></script>
  <@getMessage/>
<@table.table id="listTable" sortable="true" width="100%">
  <@table.thead>
    <@table.td text=""/>
    <@table.sortTd width="10%" name="attr.code" id="studentType.code"/>
    <@table.sortTd width="20%" name="attr.infoname" id="studentType.name"/>
    <@table.sortTd width="20%" name="attr.parentStudentType" id="studentType.superType"/>
    <@table.sortTd width="10%" name="common.abbreviation" id="studentType.abbreviation"/>
    <@table.sortTd width="20%" name="attr.engName" id="studentType.engName"/>
    <@table.sortTd width="15%" name="attr.modifyAt" id="studentType.modifyAt"/>
  </@>
  <@table.tbody datas=studentTypes;stdType>
    <@table.selectTd id="studentTypeId"	value=stdType.id type="radio"/>
    <td>${stdType.code?if_exists}</td>
    <td><a href="studentType.do?method=info&studentType.id=${stdType.id}">${stdType.name?if_exists}</a></td>
    <td><@i18nName stdType.superType?if_exists/></td>
    <td>${stdType.abbreviation?if_exists}</td>
    <td>${stdType.engName?if_exists}</td>
    <td>${(stdType.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>          
  </@>
</@>
  <script language="javascript">
   type="studentType";
   defaultOrderBy="${RequestParameters['orderBy']?default('null')}";
  </script>
  </body>
<#include "/templates/foot.ftl"/>