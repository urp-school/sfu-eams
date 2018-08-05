<#include "/templates/head.ftl"/>
<script type='text/javascript' src='dwr/interface/studentDAO.js'></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<body>
 <table id="bar"></table>
 <table class="formTable" align="center" width="80%">
   <form name="actionForm" method="post" action="" onSubmit="return false;">
 	<tr>
  	    <input type="hidden" name="otherExamSignUp.id" value="${(otherExamSignUp.id)?default('')}">
  	    <input type="hidden" id="stdId" name="otherExamSignUp.std.id" value="${(otherExamSignUp.std.id)?default('')}">  
        <td class="title" align="center" width="15%"><@msg.message key="attr.stdNo"/><font color="red">*</font>:</td>
        <td width="35%"><input type="text" name="otherExamSignUp.std.code" maxlength="32" TABINDEX="1" style="width:90px">&nbsp;<button accesskey="S" name="button1"  onclick="getStd()"><@msg.message key="action.query"/>(<u>S</u>)</button></td>
        <td class="title" align="center" width="15%"><@msg.message key="attr.personName"/>:</td>
  		<td id="name" width="35%"><@i18nName (otherExamSignUp.std)?if_exists/></td>
  	</tr>
  	<tr>
        <td class="title" align="center"><@msg.message key="entity.speciality"/>:</td>
  		<td id="speciality"><@i18nName (otherExamSignUp.std.firstMajor)?if_exists/></td>
        <td class="title" align="center">班级:</td>
  		<td id="adminClass"><@getBeanListNames (otherExamSignUp.std.adminClasses)?if_exists/></td>
  	</tr>
  	<tr>
        <td class="title" align="center"><@msg.message key="entity.studentType"/>:</td>
  		<td id="studentType"><@i18nName (otherExamSignUp.std.type)?if_exists/></td>
        <td class="title" align="center"><@msg.message key="entity.department"/>:</td>
  		<td id="department"><@i18nName (otherExamSignUp.std.department)?if_exists/></td>
  	</tr>
  	<tr>
  		<td class="title"><@msg.message key="entity.examType"/>:</td>
  		<td colspan="3"><@htm.i18nSelect datas=otherExamCategories selected="" name="otherExamSignUp.category.id" style="width:200px;"/></td>
  	</tr>
  	<tr>
  	  <td class="title" align="right" style="width:80px;">学年学期:</td>
	  <td align="bottom" colspan="3">
	      <select id="stdType" name="calendar.studentType.id" style="width:100px;">
	        <option value="${studentType.id}"></option>
	      </select>
	      <select id="year" name="calendar.year"  style="width:100px;">
	        <option value="${calendar.year}"></option>
	      </select>
	      <@bean.message key="attr.term"/>
	     <select id="term" name="calendar.term" style="width:60px;">
	        <option value="${calendar.term}"></option>
	      </select>
	   </td>
   </tr>
   <#include "/templates/calendarSelect.ftl"/>
	<tr>
		<td class="darkColumn" colspan="4" align="center">
         <button accesskey="S" name="button1"  onclick="save(this.form);"><@msg.message key="action.save"/>(<u>S</u>)</button>
		</td>
	</tr>
  	</form>
 </table>
 <#list 1..5 as i><br></#list>
 </body>
 <script language="javascript" >
   	var bar =new ToolBar("bar","其他考试报名",null,true,true);
   	bar.setMessage('<@getMessage/>');
   	bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
   
    var form = document.actionForm;
  	<#assign stdCode = RequestParameters["otherExamSignUp.std.code"]?default('')/>
    <#if (stdCode?length!=0)>
    	form["otherExamSignUp.std.code"].value = "${RequestParameters["otherExamSignUp.std.code"]?default("")}";
    	getStd();
    	form["otherExamSignUp.category.id"].value = "${RequestParameters["otherExamSignUp.category.id"]?default("")}";
    	$("stdType").value = "${RequestParameters["calendar.studentType.id"]?default("")}";
    	$("year").value = "${RequestParameters["calendar.year"]?default("")}";
    	$("term").value = "${RequestParameters["calendar.term"]?default("")}";
    </#if>
    
  // 查找学生
  function getStd(){
     var stdCode=form['otherExamSignUp.std.code'].value;
     if(stdCode=="") {
      	alert("请输入学号！");
      	clear();
     }
     else{
       	studentDAO.getBasicInfoName(setStdInfo,stdCode);
     }
  }
  
  // 清除设定的信息
  function clear(){
       $('name').innerHTML="";
       $('speciality').innerHTML="";
       $('adminClass').innerHTML="";
       $('studentType').innerHTML='';
       $('department').innerHTML="";
       $('stdId').value="";
  }
  
  var msg_outof_shool="该同学已经离开学校了.";
  var no_std="没有该学号对应的学生";
  
  function setStdInfo(data){
     if(null==data){
       clear();
       $('message').innerHTML=no_std;
     }else{
        $('name').innerHTML=data.name;
        $('stdId').value=data.id;
        $('department').innerHTML=data['department.name'];
        $('speciality').innerHTML=data['firstMajor.name'];
        $('studentType').innerHTML=data['type.name'];
        $('adminClass').innerHTML=data['adminClasses.adminClass.name'];
        if(!data.state||!data.isInSchoolStatus)$("message").innerHTML=msg_outof_shool;
        else{
           $("message").innerHTML='';
        }
     }
  }
  
  function save(form){
	  if(form['otherExamSignUp.std.id'].value == ""){
	     alert("没有学生不能进行提交!");
	     return;
	  }
	  form.action = "otherExamSignUp.do?method=save"
	  form.submit();
   }
 </script>
<#include "/templates/foot.ftl"/>