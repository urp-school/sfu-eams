<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<script language="JavaScript" type="text/JavaScript" src="scripts/system/BaseInfo.js"></script>
  <@getMessage/>
  <@table.table width="100%"  id="listTable" sortable="true">
    <@table.thead>
       <@table.td text=""/>
       <@table.sortTd width="10%" name="attr.code" id="specialityAspect.code"/>
       <@table.sortTd width="30%" name="attr.infoname" id="specialityAspect.name"/>
       <@table.sortTd width="10%" name="entity.studentType" id="specialityAspect.speciality.stdType.name"/>
       <@table.sortTd width="20%" name="entity.speciality" id="specialityAspect.speciality.name"/>
       <@table.sortTd width="30%" name="entity.department" id="specialityAspect.speciality.department.name"/>
    </@>
    <@table.tbody datas =specialityAspects;specialityAspect>
       <@table.selectTd type="radio" id="specialityAspectId" value="${specialityAspect.id}"/>
       <td>${specialityAspect.code}</td>
       <td><a href="specialityAspect.do?method=info&specialityAspect.id=${specialityAspect.id}"><@i18nName specialityAspect/></a></td>
       <td><@i18nName specialityAspect.speciality.stdType?if_exists/></td>
       <td><@i18nName specialityAspect.speciality/></td>
       <td><@i18nName specialityAspect.speciality.department/></td>
    </@>
  </@>
  <script language="javascript">
   type="specialityAspect";
   defaultOrderBy="${RequestParameters['orderBy']?default('null')}";
  </script>
  </body>
<#include "/templates/foot.ftl"/>