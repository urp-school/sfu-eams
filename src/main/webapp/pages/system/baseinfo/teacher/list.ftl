<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<script language="JavaScript" type="text/JavaScript" src="scripts/system/BaseInfo.js"></script>
  <@getMessage/>
  <@table.table width="100%"  id="listTable" sortable="true">
    <@table.thead>
       <@table.td text=""/>
       <@table.sortTd width="10%" name="teacher.code" id="teacher.code"/>
       <@table.sortTd width="10%" name="attr.personName" id="teacher.name"/>
       <@table.sortTd width="10%" name="common.gender" id="teacher.gender.name"/>
       <@table.sortTd width="20%" name="entity.department" id="teacher.department.name"/>
       <@table.sortTd width="10%" name="common.phoneOfCorporation" id="teacher.addressInfo.phoneOfCorporation"/>
  	   <@table.sortTd width="15%" name="common.teacherTitle" id="teacher.title"/>
       <@table.sortTd width="15%" name="entity.teacherType" id="teacher.teacherType.name"/>	
    </@>
    <@table.tbody datas=teachers;teacher>
       <@table.selectTd type="radio" id="teacherId" value="${teacher.id}"/>
       <td><a href="teacher.do?method=info&teacher.id=${teacher.id}">${teacher.code?if_exists}</a></td>
       <td>${teacher.name?if_exists}</td>
       <td><@i18nName teacher.gender/></td>
       <td><@i18nName teacher.department/> </td>
       <td>${teacher.addressInfo?if_exists.phoneOfCorporation?if_exists}  </td> 
       <td><@i18nName teacher.title?if_exists/></td> 	 
       <td><@i18nName teacher.teacherType?if_exists/></td>   
    </@>
   </@>
  <script language="javascript">
   type="teacher";
   defaultOrderBy="${RequestParameters['orderBy']?default('null')}";
  </script>
  </body>
<#include "/templates/foot.ftl"/>