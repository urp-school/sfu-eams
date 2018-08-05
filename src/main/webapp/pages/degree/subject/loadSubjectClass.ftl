<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <body> 
<#assign labInfo> </#assign>
<#include "/templates/back.ftl"/> 
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <tr>
    <td colspan="5" align="center" height="30"></td>
   </tr>   
   <tr>
    <td>
     <table width="70%" align="center" border="0" >
	   <form name="pageGoForm" action="subjectClass.do?method=addSubjectClassOperation" method="post" onsubmit="return false;">
	   <tr>
	    <td>	    
	    <table width="100%" align="center" class="listTable">
		   <tr class="darkColumn">
		     <td align="center" colspan="2">
		      <#if result.modify?exists >
		     	<@bean.message key="filed.modifySubject" />
		     	<#assign modify=true>
		      <#else>
   		         <@bean.message key="filed.addSubject" />
   		         <#assign modify=false>
		      </#if>	
		     </td>
		   </tr>			   
		   <tr>
		     <td class="grayStyle" width="30%" id="f_subjectCode">
		      &nbsp;<@bean.message key="filed.subjectKindCode" />：
		     </td>
		     <td class="brightStyle">&nbsp;
				<input type="text" name="subjectClass.subjectCode" maxlength="32" <#if modify== true>readOnly style="background-color:#CCCCCC"</#if> value="${result.subjectClass?if_exists.subjectCode?if_exists}"></td>
		   </tr>	
		   <tr>
		     <td class="grayStyle" width="25%" id="f_name">
		      &nbsp;<@bean.message key="filed.subjectCN" />：
		     </td>
		     <td class="brightStyle">&nbsp;
				<input type="text" name="subjectClass.name" maxlength="30" value="${result.subjectClass?if_exists.name?if_exists}"> </td>   
		     </td>
		   </tr>
		   <tr>
		     <td class="grayStyle" width="25%" id="f_department">
		      &nbsp;<@bean.message key="filed.subjectEN" />：
		     </td>
		     <td class="brightStyle">&nbsp;
				<input type="text" name="subjectClass.nameEn" maxlength="30" value="${result.subjectClass?if_exists.nameEn?if_exists}"> </td>   
		     </td>
		   </tr>		   
		   <tr class="darkColumn" align="center">
		     <td colspan="2">
			   <input type="button" value="<@bean.message key="system.button.submit"/>" name="button1" onClick="doAction(this.form)" class="buttonStyle"/>&nbsp;
		       <input type="reset"  name="reset1" value="<@bean.message key="system.button.reset"/>" class="buttonStyle"/>
		     </td>
		   </tr>
		 </table>		   
	     </td>
	   </tr>
	   </form>     
     </table>
    </td>
   </tr>
  </table>
 </body>
 <script language="javascript" >   
    function doAction(form){
 	<#if result.modify?exists >
		  form.action="subjectClass.do?method=modifySubjectClassOperation"   		 
	</#if>	   
     var a_fields = {
         'subjectClass.subjectCode':{'l':'<@bean.message key="filed.subjectKindCode" />', 'r':true, 't':'f_subjectCode', 'mx':31},
         'subjectClass.name':{'l':'<@bean.message key="filed.subjectCN" />', 'r':true, 't':'f_name'}         
     };

     var v = new validator(form , a_fields, null);
     if (v.exec()) {
        form.submit();
     }  
    }
 </script>
<#include "/templates/foot.ftl"/>