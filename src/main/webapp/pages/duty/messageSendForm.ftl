<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
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
         <TD WIDTH="85%"><FONT COLOR="red"><B><@bean.message key="field.feeDetail.messageSend"/></B></FONT></TD>
        </TR>
       </TABLE>
      </TD>
     </TR>
    </TABLE>
    <TABLE WIDTH="100%" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0">
     <TR> 
      <TD HEIGHT="100%" VALIGN="TOP">       
       <table width="85%" align="center" class="listTable">
        <form name="commonForm" action="dutyRecordManager.do" method="post" target="main" onsubmit="return false;">
        <tr>
	     <td class="grayStyle" width="25%" id="f_title">&nbsp;<@bean.message key="entity.title"/>：<font color="red">*</font></td>
	     <td class="brightStyle">
	      <input type="text" name="title" value="<@bean.message key="entity.examNotify"/>" />
	     </td>
	    </tr>
	    <tr>
	     <td class="grayStyle" width="25%" id="f_body">&nbsp;<@bean.message key="entity.content"/>：<font color="red">*</font></td>
	     <td class="brightStyle">
	      <#assign courseName><@i18nName result.teachTask.course?if_exists/></#assign>
	      <textarea name="body" cols="25" rows="8"><@bean.message key="info.duty.low" arg0="${courseName}"/></textarea>
	     </td>
	    </tr>
        <tr>
         <td colspan="4" align="center" class="darkColumn"> 
           <input type="hidden" name="method" value="sendMessage" />
           <input type="hidden" name="actionName" />
           <input type="hidden" name="stdId" value="${RequestParameters["stdId"]}" />
           <input type="hidden" name="teachTaskId" value="${RequestParameters["teachTaskId"]}" />
           <input type="button" value="<@bean.message key="system.button.submit" />" name="button1" class="buttonStyle"
                  onClick="sendMessage(this.form);"  />&nbsp;
	       <input type="reset" value="<@bean.message key="system.button.reset" />" name="reset1"  class="buttonStyle" />
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
</BODY>
<script>
   function sendMessage(form){
   
       var a_fields = {
         'title':{'l':'<@bean.message key="entity.title"/>', 'r':true, 't':'f_title', 'mx':10},
         'body':{'l':'<@bean.message key="entity.content"/>', 'r':true, 't':'f_body', 'mx':100}
      };
     
      var v = new validator(form, a_fields, null);
      if (v.exec()) {
          var location = self.location.href;
          var url = location.split("/");
          var action = url[url.length-1].split("?");
          commonForm.action = action[0];
          commonForm.actionName.value = action[0];
          commonForm.submit();
          window.close();
      }
    }
</script>
<#include "/templates/foot.ftl"/>