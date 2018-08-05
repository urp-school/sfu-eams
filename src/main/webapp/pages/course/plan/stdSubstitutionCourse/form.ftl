<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<script src='dwr/interface/courseDao.js'></script>
<script src='dwr/interface/studentService.js'></script>
<body   valign="top">
<table id="courseFormBar"></table>
<script>
  var bar= new ToolBar("courseFormBar","学生替代课程维护",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addBack();
  
    
  function checkCode(){
  	  var valueCode = document.getElementById("unSubstitutionCourse.std.code").value;
  	  if(""==valueCode) {
  	  	alert("请输入学号");
  	  	return;
  	  }
  	  studentService.getStd(afterCheck,valueCode);
  }
  function afterCheck(data){
  	  if(data==null){
  	  	alert("该学号学生不存在！");
  	  	document.getElementById("unSubstitutionCourse.std.code").value="${(substitutionCourse.std.code)?if_exists}";
  	    document.getElementById("substitutionCourse.std.name").innerHTML="${(substitutionCourse.std.name)?if_exists}";
  	    document.getElementById("substitutionCourse.std.id").value="${(substitutionCourse.std.id)?if_exists}";
  	    document.getElementById("unSubstitutionCourse.std.code").focus();
  	    document.getElementById("unSubstitutionCourse.std.code").select();
  	  }else{
  	    document.getElementById("substitutionCourse.std.name").innerHTML=data['name'];
  	    document.getElementById("substitutionCourse.std.id").value="" + data['id'];
  	  }
  }
  
  function checkCourse(type){
  	  var valueCode = "";
	  if("origin"==type){
	  	valueCode = document.getElementById("unSubstitutionCourse.origin.code").value;
	  }else if("substitute"==type){
	  	valueCode = document.getElementById("unSubstitutionCourse.substitute.code").value;
	  }
  	  if(""==valueCode) {
  	  	alert("请输入课程代码");
  	  	return;
  	  }
  	  if("origin"==type){
	  	courseDao.getCourseByCode(afterCheckOriginCourse,valueCode);
	  }else if("substitute"==type){
	  	courseDao.getCourseByCode(afterCheckSubstituteCourse,valueCode);
	  }
  }
  function afterCheckOriginCourse(data){
  	  if(data==null){
  	  	alert("该课程不存在！");
  	  	document.getElementById("unSubstitutionCourse.origin.code").value="${(substitutionCourse.origin.code)?if_exists}";
  	  	document.getElementById("substitutionCourse.origin.name").innerHTML="${(substitutionCourse.origin.name)?if_exists}";
  	  	document.getElementById("substitutionCourse.origin.id").value="${(substitutionCourse.origin.id)?if_exists}";
  	  	document.getElementById("unSubstitutionCourse.origin.code").focus();
  	    document.getElementById("unSubstitutionCourse.origin.code").select();
  	  }else{
	    document.getElementById("substitutionCourse.origin.name").innerHTML=data['name'];
	    document.getElementById("substitutionCourse.origin.id").value="" + data['id'];
  	  }
  }  
  function afterCheckSubstituteCourse(data){
  	  if(data==null){
  	  	alert("该课程不存在！");
  	  	document.getElementById("unSubstitutionCourse.substitute.code").value="${(substitutionCourse.substitute.code)?if_exists}";
  	  	document.getElementById("substitutionCourse.substitute.name").innerHTML="${(substitutionCourse.substitute.name)?if_exists}";
  	  	document.getElementById("substitutionCourse.substitute.id").value="${(substitutionCourse.substitute.id)?if_exists}";
  	  	document.getElementById("unSubstitutionCourse.substitute.code").focus();
  	    document.getElementById("unSubstitutionCourse.substitute.code").select();
  	  }else{
  	    document.getElementById("substitutionCourse.substitute.name").innerHTML=data['name'];
  	    document.getElementById("substitutionCourse.substitute.id").value="" + data['id'];
  	  }
  }
</script>
<table width="100%" valign="top" class="formTable">
       <form name="stdSubstitutionCourseForm"  action="stdSubstitutionCourse.do?method=save" method="post" onsubmit="return false;">
	   <tr class="darkColumn">
	     <td align="center" colspan="4">可代替课程基本信息</td>
	   </tr>
	   
	   <tr>
	     <td class="grayStyle" width="23%">&nbsp;学生学号<font color="red">*</font>  <div id="checkMessage"></div></td>
	     <td class="brightStyle" id="f_planCourseId" colspan="3">
	      <input type="text" name="unSubstitutionCourse.std.code" value="${(substitutionCourse.std.code)?if_exists}" size="20" onChange="checkCode()" maxlength="20"/>
	      <input type="hidden" name="substitutionCourse.std.id" value="${(substitutionCourse.std.id)?if_exists}"/>
	      <span id="substitutionCourse.std.name">${(substitutionCourse.std.name)?if_exists}</span>
	    </td>
	   </tr>
	   
	   <tr>
	     <td class="grayStyle" width="23%">&nbsp;<@msg.message key="attr.courseName"/>代码<font color="red">*</font></td>
	     <td class="brightStyle" id="f_originId" colspan="3">
	      <input type="text" name="unSubstitutionCourse.origin.code" value="${(substitutionCourse.origin.code)?if_exists}" size="20" onChange="checkCourse('origin')" maxlength="20"/>
	      <input type="hidden" name="substitutionCourse.origin.id" value="${(substitutionCourse.origin.id)?if_exists}"/>
	      <span id="substitutionCourse.origin.name">${(substitutionCourse.origin.name)?if_exists}</span>
	    </td>
	   </tr>

	   <tr>
	     <td class="grayStyle">&nbsp;替代课程代码<font color="red">*</font></td>
	     <td class="brightStyle" id="f_substituteId" colspan="3">
	      <input type="text" name="unSubstitutionCourse.substitute.code" value="${(substitutionCourse.substitute.code)?if_exists}" onChange="checkCourse('substitute')" maxlength="20"/>
	      <input type="hidden" name="substitutionCourse.substitute.id" value="${(substitutionCourse.substitute.id)?if_exists}"/>
	      <span id="substitutionCourse.substitute.name">${(substitutionCourse.substitute.name)?if_exists}</span>
         </td>
	   </tr>
	   <tr>
	     <td class="grayStyle">&nbsp;<@bean.message key="attr.remark"/></td>
	     <td class="brightStyle" colspan="3">
	      <textarea name="substitutionCourse.remark" cols="25" rows="2">${(substitutionCourse.remark)?if_exists}</textarea>
         </td>
	   </tr>
	  
	   <tr class="darkColumn">
	   	 <td colspan="4" align="center">
	   	    <input type="hidden" name="substitutionCourse.id" value="${(substitutionCourse.id)?if_exists}">
	     	<input type="button" value="<@bean.message key="action.submit"/>" onclick="this.form.submit()" class="buttonStyle"/>
         	<input type="reset" value="<@bean.message key="system.button.reset"/>" class="buttonStyle"/>   
         </td>
	   </tr>
       </form>
	  </table>
	  
  <table id="groupBar"></table>  
 </body>