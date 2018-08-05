<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<script src='dwr/interface/courseDao.js'></script>
<body   valign="top">
<table id="examinationPaperFormBar"></table>
<script>
  var bar= new ToolBar("examinationPaperFormBar","试卷维护",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addBack();
  
  function checkCourse(){
  	  var valueCode = document.getElementById("unExaminationPaper.course.code").value;
  	  if(""==valueCode) {
  	  	alert("请输入课程代码");
  	  	return;
  	  }
  	  
	  courseDao.getCourseByCode(afterCheckCourse,valueCode);
  }
  function afterCheckCourse(data){
  	  if(data==null){
  	  	alert("该课程不存在！");
  	  	document.getElementById("unExaminationPaper.course.code").value="${(examinationPaper.course.code)?if_exists}";
  	  	document.getElementById("examinationPaper.course.name").innerHTML="${(examinationPaper.course.name)?if_exists}";
  	  	document.getElementById("examinationPaper.course.id").value="${(examinationPaper.course.id)?if_exists}";
  	  	document.getElementById("unExaminationPaper.course.code").focus();
  	    document.getElementById("unExaminationPaper.course.code").select();
  	  }else{
	    document.getElementById("examinationPaper.course.name").innerHTML=data['name'];
	    document.getElementById("examinationPaper.course.id").value="" + data['id'];
  	  }
  }  

</script>
<table width="100%" valign="top" class="formTable">
       <form name="examinationPaperForm"  action="examinationPaper.do?method=save" method="post" enctype="multipart/form-data" onsubmit="return false;">
	   <tr class="darkColumn">
	     <td align="center" colspan="2">试卷基本信息</td>
	   </tr>
	   <tr>
	 	   <td class="title" width="16%" id="f_name">&nbsp;<@bean.message key="attr.yearAndTerm"/>:</td> 	     
	 	   <td class="content"><#if (examinationPaper.calendar)?exists>${(examinationPaper.calendar.year)?if_exists}&nbsp;${(examinationPaper.calendar.term)?if_exists}<#else>${(calendar.year)?if_exists}&nbsp;${(calendar.term)?if_exists}</#if></td>
	 	   <input type="hidden" value="${(calendar.id)?if_exists}" name="examinationPaper.calendar.id"/>
	 	   <input type="hidden" value="${(calendar.id)?if_exists}" name="calendar.id"/>
	   </tr>
	   
	   <tr>
	     <td class="title">&nbsp;课程代码:</td>
	     <td class="brightStyle">
	      <input type="text" name="unExaminationPaper.course.code" value="${(examinationPaper.course.code)?if_exists}"  onChange="checkCourse()"/>
	      <input type="hidden" name="examinationPaper.course.id" value="${(examinationPaper.course.id)?if_exists}"/>
	      <span id="examinationPaper.course.name">${(examinationPaper.course.name)?if_exists}</span>
         </td>
	   </tr>
	   
	   <tr>
    	 <td class="title" width="25%" id="f_auditState">&nbsp;试卷文档</td>
	     <td>
	     	<input type="file" name="formFile" size=50 class="buttonStyle" value=''>&nbsp;<br>
	     	<a href='examinationPaper.do?method=download&examinationPaperId=${(examinationPaper.id)?if_exists}'>${(examinationPaper.fileName)?if_exists}</a>
	     </td>
	   </tr>
	   
	   <tr>
	     <td class="title">&nbsp;文档说明:</td>
	     <td class="brightStyle" colspan="3">
	      <textarea name="examinationPaper.remark" cols="25" style="width:100%" rows="10">${(examinationPaper.remark)?if_exists}</textarea>
         </td>
	   </tr>
	  
	   <tr class="darkColumn">
	   	 <td colspan="4" align="center">
	   	    <input type="hidden" name="examinationPaper.id" value="${(examinationPaper.id)?if_exists}">
	     	<input type="button" value="<@bean.message key="action.submit"/>" onclick="this.form.submit()" class="buttonStyle"/>
         	<input type="reset" value="<@bean.message key="system.button.reset"/>" class="buttonStyle"/>   
         </td>
	   </tr>
       </form>
	  </table>
 </body>