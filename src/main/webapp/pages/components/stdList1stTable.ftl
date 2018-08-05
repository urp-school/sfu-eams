 <@table.table width="100%" sortable="true" id="sortTable">
	   <@table.thead>
	     <@table.selectAllTd id="stdId"/>
	     <@table.sortTd width="10%" id="std.code" name="attr.stdNo"/>
	     <@table.sortTd width="8%" id="std.name" name="attr.personName"/>
	     <@table.sortTd width="5%" id="std.basicInfo.gender.id" name="entity.gender"/>
	     <@table.sortTd width="8%" id="std.enrollYear" name="attr.enrollTurn"/>
	     <@table.sortTd width="15%"id="std.department.name" name="entity.college"/>
	     <@table.sortTd width="15%" id="std.firstMajor.name" name="entity.speciality"/>
	     <@table.sortTd width="15%" id="std.firstAspect.name" name="entity.specialityAspect"/>
	   </@>
	   <@table.tbody datas=students;std>
	    <@table.selectTd  type="checkbox" id="stdId" value="${std.id}"/>
	    <td><A href="studentDetailByManager.do?method=detail&stdId=${std.id}" target="blank">${std.code}</A></td>
        <td><A href="#" onclick="if(typeof stdIdAction !='undefined') stdIdAction('${std.id}');" title="${stdNameTitle?if_exists}">${std.name}</a></td>
        <td><@i18nName std.basicInfo?if_exists.gender?if_exists/></td>
        <td>${std.enrollYear}</td>
        <td><@i18nName std.department/></td>
        <td><@i18nName std.firstMajor?if_exists/></td>
		<td><@i18nName std.firstAspect?if_exists/></td> 
	   </@>
     </@>