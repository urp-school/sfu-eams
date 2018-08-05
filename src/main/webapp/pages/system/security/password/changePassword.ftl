<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@msg.message key="validator.js.url" />"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/common/md5.js"></script>

<body>
   <TABLE WIDTH="60%" align="center" class="formTable">
     <TR  >
      <TD colspan="2" valign="bottom" BACKGROUND="images/loginForm/leftItem_001.gif" STYLE="background-repeat:no-repeat ">
       <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           &nbsp;<FONT COLOR="red"><B><@msg.message key="field.user.modifyPassword"/></B></FONT>
      </TD>
     </TR>
    <form name="commonForm" action="password.do?method=saveChange" method="post" onsubmit="return false;">
    <tr> 
     <td id="f_oldPassword" class="title" width="40%"><@msg.message key="field.user.oldPassword"/>:</td>
     <td class="text1"><input type="password" name="oldPassword" maxLength="64"/><input type="hidden" name="oldPassword_encoded" value="${user.password}"></td>
    </tr>
    <tr> 
     <td id="f_newPassword" class="title"><@msg.message key="field.user.newPassword"/>:</td>
     <td class="text1"><input type="password" name="password"  maxLength="64"/></td>
    </tr>
    <tr> 
     <td id="f_repeatedPassword" class="title"><@msg.message key="field.user.repeatPassword"/>:</td>
     <td class="text1"><input type="password" name="repeatedPassword" maxLength="64"/></td>
    </tr>
    <tr> 
     <td id="f_email"  class="title" width="40%"><@msg.message key="attr.email"/>:</td>
     <td class="text1"><input type="text" name="email"  value="${user.email?default('')}" maxLength="100"/></td>
    </tr>
    <tr>
     <td colspan="2" align="center" class="darkColumn"> 
      <input type="hidden" name="method" value="change" />
       <input type="button" value="<@msg.message key="system.button.submit" />" name="button1" onClick="doAction(this.form)" class="buttonStyle"/>&nbsp;
       <input type="reset" value="<@msg.message key="system.button.reset" />" name="reset1"  class="buttonStyle" />
     </td>
    </tr>
    </form>
   </TABLE>
<script>
   function doAction(form){
     var a_fields = {
         'oldPassword':{'l':'<@msg.message key="field.user.oldPassword"/>', 'r':true, 't':'f_oldPassword'},
         'password':{'l':'<@msg.message key="field.user.newPassword"/>', 'r':true, 't':'f_newPassword'},
         'repeatedPassword':{'l':'<@msg.message key="field.user.repeatPassword"/>', 'r':true, 't':'f_repeatedPassword'},
         'email':{'l':'<@msg.message key="attr.email"/>', 'r':true, 'f':'email', 't':'f_email'}
     };

     var v = new validator(document.commonForm , a_fields, null);
     if (v.exec()) {
        if(form['oldPassword_encoded'].value!=hex_md5(form.oldPassword.value)) {alert ("旧密码输入不正确.");return;}
        if(form['password'].value!=form['repeatedPassword'].value){alert("新密码与重复输入的不相同");return;}
        else{
           form['password'].value=hex_md5(form['password'].value);
           form['password'].value=form['password'].value;
           document.commonForm.submit();
        }
     }
   }
</script> 
</body>
<#include "/templates/foot.ftl"/>