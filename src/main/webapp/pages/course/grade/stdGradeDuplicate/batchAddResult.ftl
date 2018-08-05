<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" >
 <table id="myBar" width="100%"> </table>
 <div align="center"><h3><@i18nName calendar.studentType/> ${calendar.year} ${calendar.term} <@i18nName gradeType/>添加结果</h3></div>
 <@table.table width="100%">
   <@table.thead>
     <@table.selectAllTd id="courseGradeId"/>
     <@table.td width="10%" name="attr.stdNo"/>
     <@table.td width="8%" name="attr.personName"/>
     <@table.td width="8%" name="attr.taskNo"/>
     <@table.td width="8%" name="attr.courseNo"/>
     <@table.td width="20%" name="entity.course"/>
     <@table.td width="15%" name="entity.courseType"/>
     <@table.td width="5%"text="成绩"/>
   </@>
   <@table.tbody datas=grades;grade>
    <@table.selectTd  type="checkbox" id="courseGradeId" value="${grade.id}"/>
    <td>${grade.std.code}</td>
    <td><@i18nName grade.std/></td>
    <td>${grade.taskSeqNo?if_exists}</td>
    <td>${grade.course.code}</td>
    <td><@i18nName grade.course?if_exists/></td>
    <td><@i18nName grade.courseType?if_exists/></td>
    <td>${grade.getScoreDisplay(gradeType)}</td
   </@>
 </@>
 <form name="actionForm" method="post" action="stdGradeDuplicate.do?method=batchAdd" onsubmit="return false;">
  <input type="hidden" value="${RequestParameters['courseGrade.calendar.studentType.id']?if_exists}" name="calendar.studentType.id"/>
  <input type="hidden" value="${RequestParameters['courseGrade.calendar.year']?if_exists}" name="calendar.year"/>
  <input type="hidden" value="${RequestParameters['courseGrade.calendar.term']?if_exists}" name="calendar.term"/>
  <input type="hidden" value="${RequestParameters['gradeTypeId']?if_exists}" name="gradeTypeId"/>
  <input type="hidden" value="${RequestParameters['markStyleId']?if_exists}" name="markStyleId"/>
 </form>
  <script>
    var bar = new ToolBar("myBar","成绩添加结果（实践）",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("继续添加","document.actionForm.submit()");
    bar.addPrint("<@msg.message key="action.print"/>");
    bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
  </script>
 </body>
<#include "/templates/foot.ftl"/>
