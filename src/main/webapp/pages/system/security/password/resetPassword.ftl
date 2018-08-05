<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/common/Validator.js"></script>
<body LEFTMARGIN="0" TOPMARGIN="0" scroll=no>
 <TABLE WIDTH="100%" BORDER="0" ALIGN="LEFT" CELLPADDING="0" CELLSPACING="0">
  <TR> 
   <TD HEIGHT="100%" VALIGN="TOP" BACKGROUND="images/loginForm/ifr_mainBg_0.gif">
	<TABLE WIDTH="100%" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0">
     <TR>
      <TD BACKGROUND="images/loginForm/leftItem_001.gif" STYLE="background-repeat:no-repeat ">              
       <TABLE WIDTH="100%" BORDER="0" CELLPADDING="0" CELLSPACING="0">
        <TR> 
         <TD WIDTH="15%" HEIGHT="42">&nbsp;</TD>
         <TD WIDTH="85%"><FONT COLOR="red"><@msg.message key="field.user.suggestive"/></FONT></TD>
        </TR>
       </TABLE>
      </TD>
     </TR>
    </TABLE>
    <TABLE WIDTH="100%" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0">
     <TR> 
      <TD HEIGHT="100%" VALIGN="TOP">       
       <TABLE WIDTH="100%" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0">
        <form name="commonForm" action="password.do?method=sendPassword" method="post">
        <tr><td colspan="3"><div class="message fade-ffff00"  id="error"></div></td></tr>
        <tr> 
         <td>&nbsp;</td>
         <td id="f_name"  valign="top" width="30%"><@msg.message key="field.user.loginName"/>:</td>
         <td class="text1"><input type="text" name="name" value="${RequestParameters['loginName']?if_exists}" maxLength="64"/></td>
        </tr>
        <tr><td>&nbsp;</td>
         <td id="f_email"  valign="top" width="30%"><@msg.message key="attr.email"/>:</td>
         <td class="text1"><input type="text" name="email" maxLength="100"/></td>
        </tr>
        <tr><td colspan="3">&nbsp;</td></tr>
        <tr>
         <td colspan="3" align="right"> 
          <input type="button" class="buttonStyle" name="button1" value="<@msg.message key="action.submit"/>" onClick="submitForm()" class="form1">
          <input type="reset" class="buttonStyle" name="reset" value="<@msg.message key="action.reset"/>" class="form1">
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
<script>
   function submitForm(){
     var a_fields = {
         'name':{'l':'<@msg.message key="field.user.loginName"/>', 'r':true, 't':'f_name'},
          'email':{'l':'<@msg.message key="attr.email"/>', 'r':true, 't':'f_email','f':'email'}
     };

     var v = new validator(document.commonForm , a_fields, null);
     if (v.exec()) {
        document.commonForm.submit();
     }
   }
</script> 
</BODY>
</body>
<#include "/templates/foot.ftl"/>