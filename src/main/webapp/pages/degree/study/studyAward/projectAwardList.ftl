<@table.table   width="100%" id="listTable" sortable="true">
	<@table.thead>
      <@table.selectAllTd id="studyAwardId"/>
      <@table.sortTd  name="attr.personName" id="studyAward.student.name"/>
      <@table.sortTd  text="项目名称" id="studyAward.project.name"/>
      <@table.sortTd  text="获奖名称"  id="studyAward.awardName"/>
      <@table.sortTd  text="获奖等级"  id="studyAward.type.name"/>
      <@table.sortTd  text="获奖时间"  id="studyAward.awardedOn"/>
      <@table.sortTd  text="颁奖单位"  id="studyAward.departmentName"/>
      <@table.sortTd  text="是否通过审核"  id="studyAward.isPassCheck"/>
    </@>
    <@table.tbody datas=studyAwards;studyAward>
      <@table.selectTd id="studyAwardId" value="${studyAward.id}"/>
  		<td>${studyAward.student?if_exists.name?if_exists}</td>
  		<td>${studyAward.project?if_exists.name?if_exists}</td>
  		<td>${studyAward.awardName?if_exists}</td>
  		<td>${studyAward.type?if_exists.name?if_exists}</td>
  		<td>${studyAward.awardedOn?string("yyyy-MM-dd")}</td>
        <td>${studyAward.departmentName?if_exists}</td>
        <td><#if studyAward.isPassCheck==true>通过审核<#else>未通过</#if></td>
      </@>
    </@>