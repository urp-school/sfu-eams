 <@table.table id="listTable" style="width:100%" sortable="true">
  <@table.thead>
    <@table.selectAllTd id="adminClassId"/>
    <@table.sortTd name="attr.enrollTurn" id="adminClass.enrollYear" style="width:10%"/>
    <@table.sortTd name="attr.infoname" id="adminClass.name" style="width:20%"/>
    <@table.sortTd name="entity.studentType" id="adminClass.stdType.name" style="width:10%"/>
    <@table.sortTd name="entity.speciality" id="adminClass.speciality.name" style="width:15%"/>
    <@table.sortTd name="entity.specialityAspect" id="adminClass.aspect.name" style="width:20%"/>
  </@table.thead>
  <@table.tbody datas=adminClasses;data>
	 <@table.selectTd id="adminClassId" value=data.id/>
	 <td>${data.enrollYear}</td>
	 <td><a href="#" onclick="if(typeof adminClassIdAction!='undefined')adminClassIdAction(${data.id})"><@i18nName data/></a></td>
     <td><@i18nName data.stdType/></td>	
     <td><@i18nName data.speciality?if_exists/></td>
     <td><@i18nName data.aspect?if_exists/></td>
  </@table.tbody>
 </@table.table>