<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" onload="f_frameStyleResize(self);f_frameStyleResize(parent)">
  <@getMessage/>
  <@table.table width="100%"  id="listTable">
    <@table.thead>
       <@table.td width="5%"text=""/>
       <@table.td width="10%" name="teacher.code" id="teacher.code"/>
       <@table.td width="10%" name="attr.personName" id="teacher.name"/>
       <@table.td width="10%" name="common.gender" id="teacher.gender.name"/>
       <@table.td width="20%" name="entity.department" id="teacher.department.name"/>
  	   <@table.td width="20%" name="common.teacherTitle" id="teacher.title"/>	
    </@>
    <@table.tbody datas=teachers?sort_by("name");teacher>
       <@table.selectTd type="radio" id="teacherId" value="${teacher.id}"/>
       <td>&nbsp;${teacher.code?if_exists}</td>
       <td>&nbsp;${teacher.name?if_exists}</td>
       <td>&nbsp;<@i18nName teacher.gender/></td>
       <td>&nbsp;<@i18nName teacher.department/> </td>
       <td>&nbsp;<@i18nName teacher.title?if_exists/></td> 	    
    </@>
   </@>
  </body>
<#include "/templates/foot.ftl"/>