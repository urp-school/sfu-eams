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
	   <form name="pageGoForm" action="firstSubject.do?method=addFirstSubjectOperation" method="post" onsubmit="return fales;">   
	   <tr>
	    <td>	    
	    <table width="100%" align="center" class="listTable">
		   <tr class="darkColumn">
		     <td align="center" colspan="2">
		      <#if result.modify?exists >
		     	<@bean.message key="filed.modifyFirstSubject"/>
		     	<#assign modify=true>
		      <#else>
   		         <@bean.message key="filed.addfirstSubject"/>
   		         <#assign modify=false>
		      </#if>	
		     </td>
		   </tr>
		   <tr>
		     <td class="grayStyle" width="30%" id="f_subjectCode">
		      &nbsp;<@bean.message key="filed.subjectKind"/>：
		     </td>
		     <td class="brightStyle">&nbsp;
		     	<select name="firstSubject.subjectClass.subjectCode" style="width:100px;">
		     		<option value=''><@bean.message key="common.selectPlease"/></option>
		         	 <#list result.subjectClassList?if_exists as subjectClass>
		         	  	<option value="${subjectClass.subjectCode}"
		         	  		<#if result.firstSubject?exists && result.firstSubject.subjectClass.subjectCode?exists  && result.firstSubject.subjectClass.subjectCode==subjectClass.subjectCode>
		         	  			selected
		         	  		</#if>
		         	  	>${subjectClass.name?if_exists}</option>
		         	 </#list>
		     	</select>
		   </tr>		   			   
		   <tr>
		     <td class="grayStyle" width="40%" id="f_firstCode">
		      &nbsp;<@bean.message key="filed.firstSubjectCode"/>：
		     </td>
		     <td class="brightStyle">&nbsp;
				<input type="text" name="firstSubject.firstCode" maxlength="32" <#if modify== true>readOnly style="background-color:#CCCCCC"</#if> value="${result.firstSubject?if_exists.firstCode?if_exists}"></td>
		   </tr>	
		   <tr>
		     <td class="grayStyle" width="40%" id="f_name">
		      &nbsp;<@bean.message key="filed.firstSubjectCn"/>：
		     </td>
		     <td class="brightStyle">&nbsp;
				<input type="text" name="firstSubject.name" maxlength="30" value="${result.firstSubject?if_exists.name?if_exists}"> </td>   
		     </td>
		   </tr>
		   <tr>
		     <td class="grayStyle" width="40%" id="f_department">
		      &nbsp;<@bean.message key="filed.firstSubjectEn"/>：
		     </td>
		     <td class="brightStyle">&nbsp;
				<input type="text" name="firstSubject.nameEn" maxlength="30" value="${result.firstSubject?if_exists.nameEn?if_exists}"> </td>   
		     </td>
		   </tr>
		   <tr>
		     <td class="grayStyle" width="40%" id="f_department">
		      &nbsp;<@bean.message key="filed.isMaster"/>：
		     </td>
		     <td class="brightStyle">&nbsp;
				<input type="radio" name="firstSubject.doctorFirst" value="1"
					<#if result.firstSubject?exists && result.firstSubject.doctorFirst?exists && result.firstSubject.doctorFirst>
						checked
					</#if>			
				><@bean.message key="filed.yes"/>&nbsp;
				<input type="radio" name="firstSubject.doctorFirst" value="0" 
					<#if result.firstSubject?exists && result.firstSubject.doctorFirst?exists && !result.firstSubject.doctorFirst>
						checked
					<#elseif !result.firstSubject?exists>	
						checked
					</#if>					
				><@bean.message key="filed.not"/>   
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
		  form.action="firstSubject.do?method=modifyfirstSubjectOperation"   		 
	</#if>
     var a_fields = {
         'firstSubject.subjectClass.subjectCode':{'l':'<@bean.message key="filed.subjectKindCode"/>', 'r':true, 't':'f_subjectCode'},
         'firstSubject.firstCode':{'l':'<@bean.message key="filed.firstSubjectCode"/>', 'r':true, 't':'f_firstCode', 'mx':31},
		 'firstSubject.name':{'l':'<@bean.message key="filed.firstSubjectCn"/>', 'r':true, 't':'f_name'}                    
     };

     var v = new validator(form , a_fields, null);
     if (v.exec()) {
        form.submit();<@bean.message key="filed.firstSubjectCode"/> }    
    }
 </script>
<#include "/templates/foot.ftl"/>