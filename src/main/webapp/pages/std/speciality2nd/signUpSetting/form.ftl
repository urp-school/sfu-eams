<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@msg.message key="validator.js.url" />"></script>
 <body>
     <table id="myBar" width="100%"></table>
     <table width="80%" align="center" class="formTable">    
      <form name="commonForm" method="post" action="" onsubmit="return false;">
       <input type="hidden" name="params" value="&signUpSetting.calendar.id=${signUpSetting.calendar.id}"/>
	   <tr>
	    <td class="title" id="f_name"><font color="red">*</font><@msg.message key="attr.name"/>：</td>
	    <td ><input type="text" name="signUpSetting.name" size="30" maxlength="30" value="${signUpSetting.name?if_exists}"/></td>
	    <td class="title" id="f_status"><font color="red">*</font>是否开放：</td>
	    <td ><@htm.radio2 name="signUpSetting.isOpen" value=signUpSetting.isOpen?default(false)/></td>
	   </tr>
	   <tr>
	    <td class="title" id="f_status"><font color="red">*</font>是否开放录取查询：</td>
	    <td ><@htm.radio2 name="signUpSetting.isOpenMatriculateResult" value=signUpSetting.isOpenMatriculateResult?default(false)/></td>
	    <td class="title" id="f_minGPA"><font color="red">*</font><@msg.message key="attr.requiredGrade"/>：</td>
	    <td ><input type="text" name="signUpSetting.minGPA" size="4" maxlength="4" value="${signUpSetting.minGPA?if_exists}"/></td>
	   </tr>
	   <tr>
	    <td class="title"  id="f_beginOn"><font color="red">*</font><@msg.message key="attr.startDate"/>：</td>
	    <td ><input type="text" name="signUpSetting.beginOn" onfocus="calendar();adaptFrameSize();" size="10" value="<#if signUpSetting.beginOn?exists>${signUpSetting.beginOn?string("yyyy-MM-dd")}</#if>" maxlength="10"/></td>
	    <td class="title"  id="f_endOn"><font color="red">*</font><@msg.message key="attr.finishDate"/>：</td>
	    <td ><input type="text" name="signUpSetting.endOn" onfocus="calendar();adaptFrameSize();" size="10" value="<#if signUpSetting.endOn?exists>${signUpSetting.endOn?if_exists}</#if>" maxlength="10"/></td>
	   </tr>
	   <tr>
	     <td class="title" id="f_enrollTurn"><font color="red">*</font><@msg.message key="info.secondSpecialitySignUpSetting.matriculateEnrollYearAndSequence"/>：</td>
	     <td ><input type="text" name="signUpSetting.enrollTurn" value="${signUpSetting.enrollTurn?if_exists}" maxlength="7"/></td>
	     <td class="title"  id="f_choiceCount"><font color="red">*</font><@msg.message key="info.secondSpecialitySignUpSetting.signUpCount"/>：</td>
	     <td ><input type="text" name="signUpSetting.choiceCount" size="4" maxlength="1" value="${signUpSetting.choiceCount?if_exists}"/></td>
	   </tr>
	   <tr>
	    <td class="title"  id="f_registerOn"><font color="red">*</font><@msg.message key="info.secondSpecialitySignUpSetting.registerDate"/>：</td>
	    <td ><input type="text" name="signUpSetting.registerOn" size="10" value="${signUpSetting.registerOn?if_exists}" maxlength="15"/> (年月日 时间)可以包含文字</td>
	    <td class="title"  id="f_registerAt"><font color="red">*</font><@msg.message key="info.secondSpecialitySignUpSetting.registerAddress"/>：</td>
	    <td ><input name="signUpSetting.registerAt" maxlength="5" value="${signUpSetting.registerAt?if_exists}"/></td>
	   </tr>
	   <tr>
	    <td class="title" id="f_isRestrictSubjectCategory"><font color="red">*</font>是否限制学科门类：</td>
	    <td colspan="3"><@htm.radio2 name="signUpSetting.isRestrictSubjectCategory" value=signUpSetting.isRestrictSubjectCategory?default(false)/></td>
	   </tr>
	   <tr class="darkColumn">
	     <td colspan="4" align="center">
	       <input type="hidden" name="signUpSetting.id" value="${signUpSetting.id?default('')}" />
	       <input type="hidden" name="signUpSetting.calendar.id" value="${signUpSetting.calendar.id}"/>
	       <button onclick="save()"><@msg.message key="system.button.submit" /></button>
	       <input type="reset" value="<@msg.message key="system.button.reset" />" name="reset1"  class="buttonStyle" />
	     </td>
	   </tr>   
      </form>
     </table>
     <br>
     <br>
     <br>
     <br>
  <script language="javascript" >
    var form=document.commonForm;
    function save(){   
     var fields = {
         'signUpSetting.name':{'l':'<@msg.message key="attr.name"/>', 'r':true, 't':'f_name'},
         'signUpSetting.minGPA':{'l':'<@msg.message key="attr.requiredGrade"/>', 'r':true, 't':'f_minGPA', 'f':'unsignedReal'},
         'signUpSetting.beginOn':{'l':'<@msg.message key="attr.startDate"/>', 'r':true, 't':'f_beginOn'},
         'signUpSetting.endOn':{'l':'<@msg.message key="attr.finishDate"/>', 'r':true, 't':'f_endOn'},
         'signUpSetting.enrollTurn':{'l':'<@msg.message key="info.secondSpecialitySignUpSetting.matriculateEnrollYearAndSequence"/>', 'r':true, 't':'f_enrollTurn', 'f':'yearMonth'},
         'signUpSetting.registerOn':{'l':'<@msg.message key="info.secondSpecialitySignUpSetting.registerDate"/>', 'r':true, 't':'f_registerOn'},
         'signUpSetting.registerAt':{'l':'<@msg.message key="info.secondSpecialitySignUpSetting.registerAddress"/>', 'r':true, 't':'f_registerAt', 'mx':50},
         'signUpSetting.choiceCount':{'l':'<@msg.message key="info.secondSpecialitySignUpSetting.signUpCount"/>', 'r':true, 't':'f_choiceCount', 'f':'unsigned'}
     };
     var v = new validator(form, fields, null);
     if (v.exec()) {
     	if (form['signUpSetting.beginOn'].value > form['signUpSetting.endOn'].value) {
     		alert("<@msg.message key="attr.startDate"/>不能超过<@msg.message key="attr.finishDate"/>！");
     		return;
     	}
        form.action="speciality2ndSignUpSetting.do?method=save";
        form.submit();
     }
   }
   var bar = new ToolBar("myBar","报名参数设置",null,true,true);
   bar.addItem("<@msg.message key="action.save"/>","save()");
   bar.addBack("<@msg.message key="action.back"/>", "back()");
  </script>
 </body>
<#include "/templates/foot.ftl"/>