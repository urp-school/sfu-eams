<#include "/templates/head.ftl"/>
<BODY >
  <@getMessage/>
  <@table.table width="100%"  id="listTable" sortable="true">
    <@table.thead>
       <@table.selectAllTd id="otherExamSignUpSettingId"/>
       <@table.sortTd name="attr.code" id="setting.examCategory.code"/>
       <@table.sortTd name="attr.infoname" id="setting.examCategory.name"/>
       <@table.sortTd text="英文名" id="setting.examCategory.engName"/>
       <@table.sortTd text="报名要求" id="setting.superCategory.name"/>
       <@table.sortTd text="开始时间" id="setting.startAt"/>
       <@table.sortTd text="截至时间" id="setting.endAt" />
       <@table.sortTd text="是否开放" id="setting.isOpen" />
    </@>
    <@table.tbody datas=settings;setting>
       <@table.selectTd id="otherExamSignUpSettingId" value="${setting.id}"/>
       <td>&nbsp;${setting.examCategory.code}</td>
       <td>&nbsp;${setting.examCategory.name}</td>
       <td>&nbsp;${setting.examCategory.engName?default("")}</td>
       <td>&nbsp;${(setting.superCategory.name)?default("")}</td>
       <td>&nbsp;${(setting.startAt?string("yyyy-MM-dd"))?if_exists}</td>
       <td>&nbsp;${(setting.endAt?string("yyyy-MM-dd"))?if_exists}</td>
       <td>&nbsp;${setting.isOpen?string("是","否")}</td>       
    </@>
   </@>
   <table height="300px"><tr><td colspan="2"></td></tr></table>
 </body>
<#include "/templates/foot.ftl"/>