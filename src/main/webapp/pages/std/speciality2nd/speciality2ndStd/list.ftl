<#include "/templates/head.ftl"/>
 <body>
 <table id="myBar"></table>
 <@table.table width="100%" sortable="true" id="sortTable">
   <@table.thead>
     <@table.selectAllTd id="studentId"/>
     <@table.sortTd width="8%" id="student.code" name="attr.stdNo"/>
     <@table.sortTd width="8%" id="student.name" name="attr.personName"/>
     <@table.sortTd width="5%" id="student.basicInfo.gender.id" name="entity.gender"/>
     <@table.sortTd width="8%" id="student.enrollYear" name="attr.enrollTurn"/>
     <@table.sortTd width="13%" id="student.secondMajor.name" name="entity.speciality"/>
   	     <@table.sortTd width="20%" id="student.secondAspect.name" text="方向"/>
   	     <td width="20%">班级</td>
   </@>
   <@table.tbody datas=students;std>
    <@table.selectTd  type="checkbox" id="studentId" value="${std.id}"/>
    <td><A href="studentDetailByManager.do?method=detail&stdId=${std.id}" target="_self" >${std.code}</A></td>
    <td><A href="#" onclick="if(typeof stdIdAction !='undefined') stdIdAction('${std.id}');" title="${stdNameTitle?if_exists}">${std.name} </a></td>
    <td><@i18nName std.basicInfo?if_exists.gender?if_exists/></td>
    <td>${std.enrollYear}</td>
    <td><@i18nName std.secondMajor?if_exists/></td>
	<td><@i18nName std.secondAspect?if_exists/></td>
	<td>${(std.secondMajorClass.name)?default("")}
   </@>
 </@>
  <@htm.actionForm name="actionForm" action="speciality2ndStd.do" entity="student"></@>
 <script>
   var bar = new ToolBar('myBar','&nbsp;辅修专业学生信息',null,true,true);
   var form =document.actionForm;
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message key="action.new"/>","form.target='';add()");
   bar.addItem("<@msg.message key="action.edit"/>","form.target='';edit()");
   bar.addItem("设为不就读", "form.target='';multiAction('settingUnstudyForm')");
   bar.addItem("<@msg.message key="action.delete"/>","form.target='';remove()","delete.gif","清除二专业属性和班级信息");
   bar.addItem("<@msg.message key="action.export"/>","form.target='';exportData()");
   bar.addItem("<@msg.message key="action.sendMsg"/>", "form.target='_blank';multiAction('sendMessage')", "inbox.gif");

   function exportData(){
      addInput(form,"keys",'code,name,basicInfo.gender.name,department.name,secondMajor.department.name,secondMajor.name,secondAspect.name,secondMajorClass.name,isSecondMajorStudy,isSecondMajorThesisNeed,std.basicInfo.homeAddress,std.basicInfo.phone,std.basicInfo.idCard');
      addInput(form,"titles",'学号,姓名,性别,院系,第二专业院系,第二专业,第二专业方向,第二专业班级,是否就读,是否写论文,联系地址,联系电话,身份证号');
      form.action="speciality2ndStd.do?method=export";
      addHiddens(form,queryStr);
      if(confirm("是否导出全部${totalSize}条记录?")){
         form.submit();
      }
   }
 </script>

 </body>
 <#include "/templates/foot.ftl"/>