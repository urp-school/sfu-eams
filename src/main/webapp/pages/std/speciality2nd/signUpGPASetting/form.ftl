<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@msg.message key="validator.js.url" />"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table width="100%" id="myBar" border="0"></table>
  <table width="60%" border="0" class="formTable" align="center">
   <form name="actionForm" method="post" action="speciality2ndSignUpGPASetting.do?method=save" onsubmit="return false;">
   <input type="hidden" name="signUpGPASetting.id" value="${signUpGPASetting.id?default("")}"/>
   <tr>
    <td class="title" id="f_fromRank"><font color="red">*</font>从志愿</td>
    <td><input name="signUpGPASetting.fromRank" maxlength="7" value="${signUpGPASetting.fromRank?default('')}"/></td>
   </tr>
   <tr>
    <td class="title" id="f_toRank"><font color="red">*</font>到志愿</td>
    <td><input name="signUpGPASetting.toRank" maxlength="7" value="${signUpGPASetting.toRank?default('')}"/></td>
   </tr>
   <tr>
    <td class="title" id="f_GPAGap"><font color="red">*</font>平均绩点级差</td>
    <td><input name="signUpGPASetting.GPAGap" value="${signUpGPASetting.GPAGap?default('')}" maxlength="10"/></td>
   </tr>
   </form>
 </table>
 </body>
 <script>
    var bar= new ToolBar("myBar","<@bean.message key="info.secondSpecialitySignUpSetting.gradeSetting"/>",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("<@msg.message key="action.save"/>",'save()');
    bar.addBack("<@msg.message key="action.back"/>");
    var form=document.actionForm;
    function save(){
       var a_fields = {
         'signUpGPASetting.fromRank':{'l':'<@bean.message key="attr.secondSpecialityGrade"/>', 'r':true, 't':'f_fromRank', 'f':'unsigned'},
         'signUpGPASetting.toRank':{'l':'<@bean.message key="attr.secondSpecialityGrade"/>', 'r':true, 't':'f_toRank', 'f':'unsigned'},
         'signUpGPASetting.GPAGap':{'l':'<@bean.message key="attr.secondSpecialityGrade"/>', 'r':true, 't':'f_GPAGap', 'f':'unsignedReal'}
       };
     
       var v = new validator(form, a_fields, null);       
       if (v.exec()) {
          form.submit();          
       }       
    }
 </script>
<#include "/templates/foot.ftl"/>