<@table.table width="100%" sortable="true" id="listTable">
   <@table.thead>
     <@table.selectAllTd id="moralGradeId"/>
     <@table.sortTd width="10%" name="attr.stdNo" id="moralGrade.std.code"/>
     <@table.sortTd width="8%"  name="attr.personName" id="moralGrade.std.name"/>
     <@table.td width="8%"  text="班级"/>
     <@table.sortTd width="5%"text="成绩" id="moralGrade.score"/>
	 <@table.sortTd width="15%" text="学年学期" id="moralGrade.calendar.start"/>
	 <@table.sortTd width="15%" text="成绩状态" id="moralGrade.status"/>
   </@>
   <@table.tbody datas=moralGrades;grade>
    <@table.selectTd  type="checkbox" id="moralGradeId" value="${grade.id}"/>
    <td><A href="studentDetailByManager.do?method=detail&stdId=${grade.std.id}" target="blank" >${grade.std.code}</A></td>
    <td><A href="#" onclick="gradeInfo('${grade.id}');" title="查看成绩详情">${grade.std.name}</a></td>
    <td>${(grade.std.firstMajorClass.name)?default('')}</td>
    <td><#if grade.isPass>${(grade.score?string("#.##"))?if_exists}<#else><font color="red">${(grade.score?string("#.##"))?if_exists}</font></#if></td>
    <td>${grade.calendar.year} ${grade.calendar.term}</td>
    <td><#if grade.isPublished>已发布<#elseif grade.isConfirmed>录入确认<#else>新增</#if></td>
   </@>
 </@>