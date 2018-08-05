<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
<script language="JavaScript" type="text/JavaScript" src="scripts/system/BaseInfo.js"></script>
  <@getMessage/>
  <@table.table width="100%" id="listTable" sortable="true">
    <@table.thead>
       <@table.td text=""/>
       <@table.sortTd width="5%" name="attr.code" id="department.code"/>
       <@table.sortTd width="20%" name="attr.infoname" id="department.name"/>
       <@table.sortTd width="20%" name="attr.engName"  id="department.engName"/>
       <@table.sortTd width="10%" name="common.abbreviation" id="department.abbreviation"/>
       <@table.sortTd width="10%" name="department.isTeaching" id="department.isTeaching"/>
       <@table.sortTd width="10%" name="department.isCollege" id="department.isCollege"/>
    </@>
    <@table.tbody datas=departments;department>
       <@table.selectTd type="radio" id="departmentId" value=department.id/>
       <td>${department.code?if_exists}</td>
       <td><a href="department.do?method=info&department.id=${department.id}">${department.name?if_exists}</a></td>
       <td>${department.engName?if_exists}</td>
       <td>${department.abbreviation?if_exists}</td>
       <td><#if department.isTeaching?if_exists == true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if></td>
       <td><#if department.isCollege?if_exists == true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if></td>
    </@>
  </@>
  <script language="javascript">
   type="department";
   defaultOrderBy="${RequestParameters['orderBy']?default('null')}";
  </script>
</body>
<#include "/templates/foot.ftl"/>