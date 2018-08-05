<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<script language="JavaScript" type="text/JavaScript" src="scripts/system/BaseInfo.js"></script>
  <@getMessage/>
  <@table.table width="100%"  id="listTable" sortable="true">
    <@table.thead>
       <@table.td text=""/>
       <@table.sortTd width="10%" name="attr.code" id="speciality.code"/>
       <@table.sortTd width="20%" name="attr.infoname" id="speciality.name"/>
       <@table.sortTd width="15%" name="entity.studentType" id="speciality.stdType.name"/>    
       <@table.sortTd width="20%" name="entity.department" id="speciality.department.name"/>
       <@table.sortTd width="20%" name="attr.engName" id="speciality.engName"/>
       <@table.sortTd width="15%" name="filed.subjectKind" id="speciality.subjectCategory.name"/>
    </@>
    <@table.tbody datas=specialities;speciality>
       <@table.selectTd  id="specialityId" value="${speciality.id}" type="radio"/>
       <td>&nbsp;${speciality.code}</td>
       <td><a href="speciality.do?method=info&speciality.id=${speciality.id}">&nbsp;<@i18nName speciality/></a></td>
       <td>&nbsp;<@i18nName speciality.stdType?if_exists/></td>
       <td>&nbsp;<@i18nName speciality.department/></td>
       <td>&nbsp;${speciality.engName?if_exists}</td>
       <td>&nbsp;${(speciality.subjectCategory.name)?if_exists}</td>
    </@>
  </@>
  <script language="javascript">
   type="speciality";
   defaultOrderBy="${RequestParameters['orderBy']?default('null')}";
  </script>
  </body>
<#include "/templates/foot.ftl"/>