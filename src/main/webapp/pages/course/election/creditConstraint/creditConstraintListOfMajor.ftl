 <@table.table width="100%" id="listTable" sortable="true">
  <@table.thead>
   <@table.selectAllTd id="creditConstraintId"/>
      <td width="7%">所在年级</td>
      <@table.sortTd width="8%" id="creditConstraint.stdType.name" name="entity.studentType"/>
      <@table.sortTd width="13%" id="creditConstraint.depart.name" name="entity.college"/>
      <@table.sortTd width="15%" id="creditConstraint.speciality" name="entity.speciality"/>
      <@table.sortTd width="20%" id="creditConstraint.aspect" name="entity.specialityAspect"/>
      <@table.sortTd width="8%" id="creditConstraint.maxCredit" name="attr.maxCredit"/>
      <@table.sortTd width="8%" id="creditConstraint.minCredit" name="attr.minCredit"/>
    </@>
    <@table.tbody datas=creditConstrains;creditConstraint>
      <@table.selectTd type="checkbox" id="creditConstraintId" value="${creditConstraint.id}"/>
      <td>${creditConstraint.enrollTurn}</td>
      <td><@i18nName creditConstraint.stdType/></td>
      <td><@i18nName creditConstraint.depart/></td>
      <td><@i18nName creditConstraint.speciality?if_exists/></td>
      <td><@i18nName creditConstraint.aspect?if_exists/></td>
      <td id="max${creditConstraint.id}">${creditConstraint.maxCredit}</td>
      <td id="min${creditConstraint.id}">${creditConstraint.minCredit}</td>
    </@>
	</@>
