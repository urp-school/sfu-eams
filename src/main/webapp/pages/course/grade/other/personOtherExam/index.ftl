<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="myBar1" width="100%"></table>
<@table.table width="100%">
  <@table.thead>
    <@table.td name="exam.type"/>
    <@table.td name="std.SignUpTime"/>
    <@table.td name="attr.yearTerm"/>
  </@>
  <@table.tbody datas=signUps?sort_by("id")?reverse;signUp>
    <td><@i18nName signUp.category/></td>
    <td>${signUp.signUpAt?string("yyyy-MM-dd HH:mm")}</td>
    <td>${signUp.calendar.year} ${signUp.calendar.term}</td>
  </@>
</@>
<br>
<div align="center"><h2>历次考试成绩</h2></div>
<@table.table width="100%">
  <@table.thead>
    <@table.td name="exam.type"/>
    <@table.td name="std.score"/>
    <@table.td name="attr.yearTerm"/>
    <@table.td name="attr.graduate.isPass"/>
  </@>
  <@table.tbody datas=grades;grade>
    <td><@i18nName grade.category/></td>
    <td><#if grade.isPass>${grade.scoreDisplay}<#else><font color="red">${grade.scoreDisplay}</font></#if></td>
    <td>${grade.calendar.year} ${grade.calendar.term}</td>
    <td><#assign pass = grade.isPass?default(false)/><#if pass><@msg.message key="common.yes"/><#else><font color='red'><@msg.message key="common.no"/></font></#if></td>
  </@>
</@>
<script>

  var bar1= new ToolBar("myBar1","<@msg.message key="std.otherExamList"/>",null,true,true);
  bar1.setMessage('<@getMessage/>');
  bar1.addItem("<@msg.message key="std.signUp"/>","signUp()");
  bar1.addPrint("<@msg.message key="action.print"/>");
  function signUp(){
     self.location="personOtherExam.do?method=signUp";
  }
</script>
</body>
<#include "/templates/foot.ftl"/>