<@table.table width="100%" sortable="true" id="listTable">
   <@table.thead>
     <@table.selectAllTd id="courseGradeId"/>
     <@table.sortTd width="10%" name="attr.stdNo" id="courseGrade.std.code"/>
     <@table.sortTd width="10%" name="attr.personName" id="courseGrade.std.name"/>
     <@table.sortTd width="8%" name="attr.taskNo" id="courseGrade.taskSeqNo"/>
     <@table.sortTd width="8%" name="attr.courseNo" id="courseGrade.course.code"/>
     <@table.sortTd name="entity.course" id="courseGrade.course.name"/>
     <@table.sortTd name="entity.courseType" id="courseGrade.courseType.name"/>
     <@table.sortTd width="5%"text="成绩" id="courseGrade.score"/>
     <@table.sortTd width="5%" name="attr.credit" id="courseGrade.credit"/>
	 <@table.sortTd width="5%" text="绩点" id="courseGrade.GP"/>
	 <@table.sortTd width="10%" text="学年学期" id="courseGrade.calendar.start"/>
   </@>
   <@table.tbody datas=courseGrades;grade>
    <@table.selectTd id="courseGradeId" value=grade.id/>
    <td><A href="studentDetailByManager.do?method=detail&stdId=${grade.std.id}" target="blank">${grade.std.code}</A></td>
    <td title="<@i18nName grade.std/>" nowrap><span style="display:block;width:100px;overflow:hidden;text-overflow:ellipsis"><A href="#" onclick="gradeInfo('${grade.id}');" title="<@i18nName grade.std/>"><@i18nName grade.std/></a></span></td>
    <td>${grade.taskSeqNo?if_exists}</td>
    <td>${grade.course.code}</td>
    <td title="<@i18nName grade.course?if_exists/>" nowrap><span style="display:block;width:120px;overflow:hidden;text-overflow:ellipsis"><@i18nName grade.course?if_exists/></span></td>
    <td title="<@i18nName grade.courseType?if_exists/>" nowrap><span style="display:block;width:120px;overflow:hidden;text-overflow:ellipsis"><@i18nName grade.courseType?if_exists/></span></td>
    <td><#if grade.isPass>${grade.scoreDisplay}<#else><font color="red">${grade.scoreDisplay}</font></#if></td>
    <td>${(grade.credit)?if_exists}</td>
    <td><#if grade.isPass>${(grade.GP?string("#.##"))?if_exists}<#else><font color="red">${(grade.GP?string("#.##"))?if_exists}</font></#if></td>
    <td>${grade.calendar.year} ${grade.calendar.term}</td>
   </@>
</@>