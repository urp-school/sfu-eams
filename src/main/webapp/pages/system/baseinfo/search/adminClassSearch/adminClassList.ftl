    <@table.table width="100%" sortable="true" id="listTable">
        ${extraHTML?if_exists}
        <@table.thead>
            <@table.selectAllTd id="adminClassId"/>
            <@table.sortTd name="attr.code" id="adminClass.code"/>
            <@table.sortTd name="attr.infoname" id="adminClass.name"/>
            <@table.sortTd name="entity.speciality" id="adminClass.speciality.name"/>
            <@table.sortTd name="entity.specialityAspect" id="adminClass.aspect.name"/>
            <@table.sortTd name="entity.studentType" id="adminClass.stdType.name"/>
            <@table.sortTd text="在校人数" id="adminClass.actualStdCount"/>
            <@table.sortTd text="学籍有效人数" id="adminClass.stdCount"/>
        </@>
        <@table.tbody datas=adminClasses;adminClass>
            <@table.selectTd id="adminClassId" value=adminClass.id/>
            <td>${adminClass.code}</td>
            <td><a href="javascript:displayInfo(${adminClass.id})">${adminClass.name}</a></td>
            <td><@i18nName adminClass.speciality?if_exists/></td>
            <td><@i18nName adminClass.aspect?if_exists/></td>
            <td><@i18nName adminClass.stdType/></td>
            <td>${adminClass.actualStdCount}</td>
            <td>${adminClass.stdCount}</td>
        </@>
    </@>
