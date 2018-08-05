<#include "/templates/head.ftl"/>
<@table.table width="100%" sortable="true" id="listTable">
   <@table.thead>
     <@table.selectAllTd id="attendWarnId"/>
     <@table.sortTd width="10%" name="attr.stdNo" id="attendWarn.std.code"/>
     <@table.sortTd width="10%" name="attr.personName" id="attendWarn.std.name"/>
     <@table.sortTd width="13%" text="院系" id="attendWarn.std.department.name"/>
     <@table.sortTd width="13%" text="学生类别" id="attendWarn.std.type.name"/>
     <@table.td text="班级"/>
     <@table.sortTd width="5%" text="总课时" id="attendWarn.zks"/>
     <@table.sortTd width="5%" text="应出勤" id="attendWarn.ycq"/>
     <@table.sortTd width="6%" text="累计缺勤 " id="attendWarn.ljqq"/>
	 <@table.sortTd width="6%" text="累计迟到" id="attendWarn.ljcd"/>
	 <@table.sortTd width="6%" text="累计课时" id="attendWarn.ljks"/>
   </@>
   <@table.tbody datas=attendWarns;attendWarn>
    <@table.selectTd id="attendWarnId" value=attendWarn.id/>
    <td><A href="studentDetailByManager.do?method=detail&stdId=${attendWarn.std.id}" target="blank">${attendWarn.std.code}</A></td>
    <td><@i18nName attendWarn.std/></td>
    <td><@i18nName attendWarn.std.department/></td>
    <td><@i18nName attendWarn.std.type/></td>
    <td>
    	<#list attendWarn.std.adminClasses! as adminClass>
      		<@i18nName adminClass!/>
    	</#list>
    </td>
    <td>${(attendWarn.zks)!}</td>
    <td>${(attendWarn.ycq)!}</td>
    <td>${(attendWarn.ljqq)!}</td>
    <td>${(attendWarn.ljcd)!}</td>
    <td>${(attendWarn.ljks)!}</td>
   </@>
</@>
<#include "/templates/foot.ftl"/>