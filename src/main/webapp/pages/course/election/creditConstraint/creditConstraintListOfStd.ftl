 <@table.table width="100%" id="listTable" sortable="true">
  <@table.thead>
    <@table.selectAllTd id="creditConstraintId"/>
    <@table.sortTd  id="creditConstraint.std.code" name="attr.stdNo" width="8%" />
    <@table.sortTd  id="creditConstraint.std.name" name="attr.personName" width="5%"/>
    <@table.sortTd  id="creditConstraint.std.type" name="entity.studentType" width="8%"/>
    <@table.sortTd  id="creditConstraint.std.firstMajor" name="entity.speciality" width="12%"/>
    <@table.td name="entity.specialityAspect" width="12%"/>
    <@table.sortTd text="上限" width="3%" id="creditConstraint.maxCredit"/>
    <@table.sortTd text="下限" width="3%" id="creditConstraint.minCredit"/>
    <@table.sortTd text="已选" width="3%" id="creditConstraint.electedCredit"/>
    <@table.sortTd text="奖励" width="3%" id="creditConstraint.awardedCredit"/>
    <@table.sortTd text="绩点" width="3%" id="creditConstraint.GPA"/>
   </@>
   <@table.tbody datas=creditConstrains;creditConstraint>
      <@table.selectTd type="checkbox" id="creditConstraintId" value=creditConstraint.id/>
      <td>${creditConstraint.std.code}</td>
      <td><@i18nName creditConstraint.std/></td>
      <td><@i18nName creditConstraint.std.type/></td>
      <td><@i18nName creditConstraint.std.firstMajor?if_exists/></td>
      <td><@i18nName creditConstraint.std.firstAspect?if_exists/></td>
      <td id="max${creditConstraint.id}">${creditConstraint.maxCredit}</td>
      <td id="min${creditConstraint.id}">${creditConstraint.minCredit}</td>
      <td>${creditConstraint.electedCredit?if_exists}</td>
      <td>${creditConstraint.awardedCredit?if_exists}</td>
      <td><#if creditConstraint.GPA?exists>${creditConstraint.GPA?string("##.##")}</#if></td>
    </@>
  </@>