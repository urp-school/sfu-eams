<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@msg.message key="validator.js.url" />"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/common/md5.js"></script>
<body LEFTMARGIN="0" TOPMARGIN="0" scroll=no>
<center>
 <TABLE WIDTH="50%" BORDER="0" ALIGN="center" CELLPADDING="0" CELLSPACING="0">
  <TR> 
   <TD HEIGHT="100%" VALIGN="TOP" BACKGROUND="images/loginForm/ifr_mainBg_0.gif">
	<TABLE WIDTH="100%" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0">
     <TR>
      <TD BACKGROUND="images/loginForm/leftItem_001.gif" STYLE="background-repeat:no-repeat ">              
       <TABLE WIDTH="100%" ALIGN="CENTER" BORDER="0" CELLPADDING="0" CELLSPACING="0">
        <TR> 
         <TD WIDTH="15%" HEIGHT="42">&nbsp;</TD>
         <TD WIDTH="85%"><FONT COLOR="red"><B><@msg.message key="field.user.modifyPassword"/></B></FONT></TD>
        </TR>
       </TABLE>
      </TD>
     </TR>
    </TABLE>
    <TABLE WIDTH="100%" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0">
     <TR> 
      <TD HEIGHT="100%" VALIGN="TOP">       
       <TABLE WIDTH="100%" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0">
        <form name="commonForm" action="password.do?method=updateUserAccount" method="post" onsubmit="return false;">
         <input type="hidden" name="user.id" value="${user.id}"/> 
        <tr>
         <td>&nbsp;</td>
         <td id="f_newPassword"  valign="top" width="40%"><@msg.message key="field.user.newPassword"/>:</td>
         <td class="text1"><input type="password" name="password" maxLength="64"/></td>
        </tr>
        <tr> 
         <td>&nbsp;</td>
         <td id="f_repeatedPassword"  valign="top" width="40%"><@msg.message key="field.user.repeatPassword"/>:</td>
         <td class="text1"><input type="password" name="repeatedPassword" maxLength="64"/></td>
        </tr>
        <tr> 
         <td>&nbsp;</td>
         <td id="f_email"  valign="top" width="40%"><@msg.message key="attr.email"/>:</td>
         <td class="text1"><input type="text" name="email"  value="${user.email}" maxLength="100"/></td>
        </tr>
        <tr><td colspan="3" height="5"></td></tr>
        <tr>
         <td colspan="3" align="center"> 
          <input type="hidden" name="method" value="change"/>
	       <input type="button" value="<@msg.message key="system.button.submit" />" name="button1" onClick="doAction(this.form)" class="buttonStyle" />&nbsp;
	       <input type="reset" value="<@msg.message key="system.button.reset" />" name="reset1"  class="buttonStyle" />
         </td>
        </tr>
        </form>
       </TABLE>
      </TD>
     </TR>          
    </TABLE>
   </TD>
  </TR>
 </TABLE>
</center>
<script>
   function doAction(form){
     var a_fields = {         
         'password':{'l':'<@msg.message key="field.user.newPassword"/>', 'r':true, 't':'f_newPassword'},
         'repeatedPassword':{'l':'<@msg.message key="field.user.repeatPassword"/>', 'r':true, 't':'f_repeatedPassword'},
         'email':{'l':'<@msg.message key="attr.email"/>', 'r':true, 'f':'email', 't':'f_email'}
     };

     var v = new validator(document.commonForm , a_fields, null);
     if (v.exec()) {
        if(form['password'].value!=form['repeatedPassword'].value){alert("新密码与重复输入的不相同");return;}
        else{
           form['password'].value=hex_md5(form['password'].value);
           document.commonForm.submit();
           window.close();
        }
     }
   }
</script> 
</body>
<#include "/templates/foot.ftl"/>