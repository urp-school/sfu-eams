<@getMessage/>
<@table.table width="100%" sortable="true" id="listTable">
   <@table.thead>
     <@table.selectAllTd id="otherGradeId"/>
     <@table.sortTd width="10%" name="attr.stdNo" id="otherGrade.std.code"/>
     <@table.sortTd width="8%"  name="attr.personName"  id="otherGrade.std.name"/>
     <@table.sortTd width="15%" text="考试类别" id="otherGrade.category.name"/>
     <@table.sortTd width="6%" text="成绩"  id="otherGrade.score"/>
	 <@table.sortTd width="6%" text="是否通过"  id="otherGrade.isPass"/>
     <@table.sortTd width="17%" name="entity.department"  id="otherGrade.std.department"/>
     <@table.sortTd width="14%" name="entity.speciality"  id="otherGrade.std.firstMajor"/>
	 <@table.sortTd width="12%" text="学年学期"  id="otherGrade.calendar.start"/>
   </@>
   <@table.tbody datas=otherGrades;grade>
    <@table.selectTd  id="otherGradeId" value="${grade.id}"/>
    <td><A href="studentDetailByManager.do?method=detail&stdId=${grade.std.id}">${grade.std.code}</A></td>
    <td>${grade.std.name}</td>
    <td><@i18nName grade.category/></td>
    <td><#if grade.isPass>${grade.scoreDisplay}<#else><font color="red">${grade.scoreDisplay}</font></#if></td>
    <td>${grade.isPass?default(false)?string("是","<font color='red'>否</font>")}</td>
    <td><@i18nName grade.std.department/></td>
    <td><@i18nName grade.std.firstMajor?if_exists/></td>
    <td>${grade.calendar.year} ${grade.calendar.term}</td>
   </@>
 </@>