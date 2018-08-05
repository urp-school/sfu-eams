<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<body>
	<table id="bar"></table>
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <form name="commonForm" action="studentOperation.do" method="post" action="" onsubmit="return false;">
   <tr>
    <td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff" height="30">
     <B><@bean.message key="std.update"/></B>
    </td>
   </tr>   
   <tr>
    <td>
     <table width="80%" align="center" class="listTable">
       <#--学籍基本信息-->
       <#include "studentInfo.ftl">
	   <#--学籍详细信息-->
       <#include "studentStatusInfo.ftl">
	   <#--学生基本信息-->
	   <#include "studentBasicInfo.ftl">
	   <#--留学生信息-->
	   <#include "abroadStudentInfo.ftl">
	   <tr class="darkColumn">
	     <td colspan="2" align="center">
	       <input type="hidden" name="method" value="update"/>
	       <input type="hidden" name="student.id" maxlength="32" value="${(result.student.id)?default('')}"/>
	       <input type="hidden" name="params" value="${(RequestParameters['params'])?default('')}"/>
	       <input type="button" value="<@bean.message key="system.button.submit"/>" name="button1" onClick="doAction(this.form)" class="buttonStyle"/>&nbsp;
	       <input type="reset" value="<@bean.message key="system.button.reset"/>" name="reset1" class="buttonStyle"/>
	     </td>
	   </tr>
     </table>
    </td>
   </tr>
   </form>
  </table>
  <br>
  <script language="javascript" >
  	var bar = new ToolBar("bar", "<@msg.message key="std.update"/>", null, true, true);
  	bar.setMessage('<@getMessage/>');
  	bar.addBack();

    function doAction(form){

     var studentInfo_fields = {
         'student.code':{'l':'<@bean.message key="attr.stdNo"/>', 'r':true, 't':'f_stdCode'},
         'student.name':{'l':'<@bean.message key="attr.personName"/>', 'r':true, 't':'f_name'},
         'student.enrollYear':{'l':'<@bean.message key="filed.enrollYearAndSequence"/>', 'r':true, 't':'f_enrollYear', 'f':'yearMonth'},
         'student.department.id':{'l':'<@bean.message key="entity.college"/>', 'r':true, 't':'f_department'},
         'student.type.id':{'l':'<@bean.message key="entity.studentType"/>', 'r':true, 't':'f_studentType'},
         //'student.active':{'l':'<@bean.message key="entity.isStudentStatusAvailable"/>', 'r':true, 't':'f_isStudentStatusAvailable'},
         'statusInfo.enrollMode.id':{'l':'<@bean.message key="entity.enrollMode"/>', 'r':true, 't':'f_enrollMode'},
         'statusInfo.enrollDate':{'l':'<@bean.message key="std.enrollDate"/>', 'r':true, 't':'f_enrollDate', 'f':'date'},
         'statusInfo.leaveDate':{'l':'<@bean.message key="std.leaveDate"/>', 'r':false, 'f':'date', 't':'f_leaveDate'},
         'student.state.id':{'l':'<@bean.message key="entity.studentState"/>', 'r':true, 't':'f_studentState'},
         'basicInfo.gender.id':{'l':'<@bean.message key="attr.gender"/>', 'r':true, 't':'f_gender'},
         'basicInfo.birthday':{'l':'<@bean.message key="attr.birthday"/>', 'r':false, 'f':'date', 't':'f_birthday'}
     };
     var v = new validator(form, studentInfo_fields, null);
     if (!(/^(\d{4})[\-]\d+$/).test(form['student.enrollYear'].value)) {
        alert("所在年级格式错误！");
        form['student.enrollYear'].focus();
        return;
     }
     if (v.exec()) {
        form['adminClassIds'].value=getAllOptionValue(form.adminClass);
        form.submit();
     }
   }
  </script>
 </body>
<#include "/templates/foot.ftl"/>