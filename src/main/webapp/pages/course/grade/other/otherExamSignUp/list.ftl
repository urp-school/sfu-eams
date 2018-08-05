<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" >
  <table id="gradeListBar" width="100%"> </table>
  <@table.table id="listTable" sortable="true">
    <@table.thead>
      <@table.selectAllTd id="otherExamSignUpId"/>
      <@table.sortTd name="attr.stdNo" id="signUp.std.code"/>
      <@table.sortTd name="attr.personName" id="signUp.std.name"/>
      <@table.sortTd text="学生类别" id="signUp.std.type.name"/>
      <@table.sortTd text="考试类别" id="signUp.category.name"/>
      <@table.sortTd text="报名时间" id="signUp.signUpAt"/>
      <@table.sortTd text="学年学期" id="signUp.calendar.start"/>
    </@>
    <@table.tbody datas=otherExamSignUps;signup>
      <@table.selectTd id="otherExamSignUpId" value=signup.id />
      <td>${signup.std.code}</td>
      <td><@i18nName signup.std/></td>
      <td><@i18nName signup.std.type/></td>
      <td><@i18nName signup.category/></td>
      <td>${signup.signUpAt?string("yyyy-MM-dd")}</td>
      <td>${signup.calendar.year} ${signup.calendar.term}</td>
    </@>
  </@>
  <@htm.actionForm name="actionForm" action="otherExamSignUp.do" entity="otherExamSignUp" onsubmit="return false;"></@>
  <script>
    var bar = new ToolBar("gradeListBar","报名查询结果",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("<@msg.message key="action.new"/>", "add()");
    bar.addItem("<@msg.message key="action.delete"/>", "remove()");
    bar.addItem("<@msg.message key="action.export"/>","exportData()");
    bar.addPrint("<@msg.message key="action.print"/>");

    keys="calendar.year,calendar.term,std.code,std.name,std.basicInfo.gender.name,std.basicInfo.idCard,category.name,std.type.name,signUpAt,std.department.name,std.firstMajor.name,std.firstMajorClass.name";
    titles="学年度,学期,<@msg.message key="attr.stdNo"/>,<@msg.message key="attr.personName"/>,性别,身份证号,考试类别,<@msg.message key="entity.studentType"/>,报名时间,<@msg.message key="entity.department"/>,<@msg.message key="entity.speciality"/>,班级";
    function exportData(){
       addInput(form,"keys",keys);
       addInput(form,"titles",titles);
       exportList();
    }
  </script>
 </body>
<#include "/templates/foot.ftl"/>
