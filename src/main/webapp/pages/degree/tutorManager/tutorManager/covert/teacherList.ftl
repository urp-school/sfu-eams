<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
	<@getMessage/>
  <@table.table width="100%" id="listTable">
    <@table.thead>
      <@table.selectAllTd id="teacherId"/>
      <@table.td width="10%" name="teacher.code"/>
      <@table.td width="10%" name="attr.personName"/>
      <@table.td width="10%" name="common.gender"/>
      <@table.td width="20%" name="entity.department"/>
      <@table.td width="10%" name="common.phoneOfCorporation"/>
  	  <@table.td width="20%" name="common.teacherTitle"/>
    </@>
    <@table.tbody datas=teacherList;teacher>
      <@table.selectTd id="teacherId" value=teacher.id/>
      <td>${teacher.code?if_exists}</td>
      <td><@i18nName teacher/></td>
      <td><@i18nName teacher.gender/></td>
      <td><@i18nName teacher.department/></td>
      <td>${teacher.phoneOfCorporation?if_exists}</td>
      <td><@i18nName teacher.title?if_exists/></td>
    </@>
  </@>
</body>
<#include "/templates/foot.ftl"/>