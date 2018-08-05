<@table.table sortable="true" id="sortTable" width="800">
	<@table.thead>
		<@table.selectAllTd id="stdId"/>
	    <@table.sortTd id="std.code" name="attr.stdNo"/>
	    <@table.sortTd id="std.name" name="attr.personName"/>
	    <@table.sortTd id="std.basicInfo.gender.id" name="entity.gender"/>
	    <@table.sortTd id="std.enrollYear" name="attr.enrollTurn"/>
	    <@table.sortTd id="std.department.name" name="entity.college"/>
	    <@table.sortTd id="std.firstMajor.name" name="entity.speciality"/>
   	    <@table.sortTd id="std.firstAspect.name" name="entity.specialityAspect"/>
   	    <@table.sortTd id="std.abroadStudentInfo.resideCaedDeadline" name="info.passportDeadline.resideCaedDeadline"/>
   	    <@table.sortTd id="std.abroadStudentInfo.passportDeadline" name="info.passportDeadline.passportDeadline"/>
   	    <@table.sortTd id="std.abroadStudentInfo.visaDeadline" name="info.passportDeadline.visaDeadline"/>
	</@>
	<@table.tbody datas=students;std>
		<@table.selectTd id="stdId" value=std.id/>
	    <td width="60"><A href="studentDetailByManager.do?method=detail&stdId=${std.id}" target="blank" >${std.code}</A></td>
        <td width="70"><A href="#" onclick="if(typeof stdIdAction !='undefined') stdIdAction('${std.id}');" title="${stdNameTitle?if_exists}">${std.name}</a></td>
        <td width="30"><@i18nName std.basicInfo?if_exists.gender?if_exists/></td>
        <td width="60">${std.enrollYear}</td>
        <td width="70"><@i18nName std.department/></td>
        <td width="70"><@i18nName std.firstMajor?if_exists/></td>
		<td width="70"><@i18nName std.firstAspect?if_exists/></td>
		<td width="100">${(std.abroadStudentInfo.resideCaedDeadline?string("yyyy-MM-dd"))?default('')}</td>
		<td width="100">${(std.abroadStudentInfo.passportDeadline?string("yyyy-MM-dd"))?default('')}</td>
		<td width="100">${(std.abroadStudentInfo.visaDeadline?string("yyyy-MM-dd"))?default('')}</td>
	</@>
</@>