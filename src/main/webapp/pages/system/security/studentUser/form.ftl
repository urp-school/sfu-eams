<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
 <script language="JavaScript" type="text/JavaScript" src="scripts/common/md5.js"></script>
 <body>
 <table id="stdUserBar"></table>
     <table width="70%"  class="formTable" align="center">
	   <form name="stdUserForm" action="stdUser.do?method=save" method="post" onsubmit="return false;">
	   <@searchParams/>	
	   <tr class="darkColumn">
	     <td  colspan="2"><@bean.message key="info.user.std"/></td>
	   </tr>
	   <tr>
	     <td class="title" width="25%" id="f_name"><font color="red">*</font><@bean.message key="attr.name"/>：</td>
	     <td >${user.name}</td>
	   </tr>
	   <tr>
	     <td class="title" width="25%" id="f_password"><font color="red">*</font><@bean.message key="attr.password"/>：</td>
         <td><input type="password" name="password" value="" id="firstPassword" maxlength="20"/><input type="hidden" name="user.password" value=""/></td>
       </tr>
       <tr>
         <td class="title" width="25%" id="f_password1"><font color="red">*</font><@bean.message key="attr.passwordConfirm"/>：</td>
	     <td >   <input type="password" name="repeatPassword" value="" id="secondPassword" maxlength="20"/></td>	     
	   </tr>
	   <tr>
	     <td class="title" width="25%" id="f_email"><font color="red">*</font><@bean.message key="attr.email"/>：</td>
         <td >   <input type="text" name="user.email" value="${user.email?if_exists}" style="width:200px" maxlength="50"/></td>
	   </tr>
	   <tr class="darkColumn" align="center">
	     <td colspan="2"  >
	       <input type="hidden" name="user.id" value="${user.id}" />
	       <input type="button" value="<@bean.message key="action.submit"/>" name="button1" onClick="save(this.form)" class="buttonStyle" />&nbsp;
	       <input type="reset"  name="reset1" value="<@bean.message key="action.reset"/>" class="buttonStyle" />
	     </td>
	   </tr>
  		 </form>
     </table>
  <script language="javascript" >
   function save(form){
     var a_fields = {
         'password':{'l':'<@bean.message key="attr.password"/>', 'r':true, 'f':'alphanum', 't':'f_password'},
         'user.email':{'l':'<@bean.message key="attr.email" />', 'r':true, 'f':'email', 't':'f_email'},  
         'repeatPassword':{'l':'<@bean.message key="attr.passwordConfirm"/>','r':true,'f':'alphanum', 't':'f_password1'}
     };
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
       if(document.stdUserForm.firstPassword.value!=document.stdUserForm.secondPassword.value) {
         document.stdUserForm.firstPassword.select();
         document.stdUserForm.secondPassword.value="";
         alert("<@bean.message key="info.password.notSame"/>");
         return;
       }else{
          form['user.password'].value=hex_md5(document.stdUserForm.firstPassword.value);
          form.submit();
       }
     }
   }
   var bar = new ToolBar('stdUserBar','<@bean.message key="info.userUpdate.std"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack("<@bean.message key="action.back"/>");
  </script>
 </body>
<#include "/templates/foot.ftl"/>