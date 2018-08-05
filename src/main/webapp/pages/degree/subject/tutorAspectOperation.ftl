<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <body> 
<#assign labInfo> <@bean.message key="filed.tutorAndMaster" /></#assign>
<#include "/templates/back.ftl"/>  
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <tr>
    <td colspan="5" align="center" height="30"></td>
   </tr>   
   <tr>
    <td>
     <table width="70%" align="center" border="0" >
	   <form name="pageGoForm" action="secondSubject.do?method=addSecondSubjectOperation" method="post" onsubmit="return false;">   
	   <input type="hidden" name="isNew" value="${result.isNew?if_exists}">
	   <tr>
	    <td>	    
	    <table width="100%" align="center" class="listTable">
		   <tr class="darkColumn">
		     <td align="center" colspan="2">
		      <#if result.isNew='true' >
		     	<@bean.message key="filed.addTutorAndmaster" />
		     	<#assign modify=false>
		      <#else>
   		         <@bean.message key="filed.modifyTutorAndmaster" />
   		        <#assign modify=true>
		      </#if>	
		     </td>
		   </tr>
		   <tr>
		     <td class="grayStyle" width="15%" id="f_thirdCode">
		      &nbsp;<@bean.message key="filed.tutorAndMasterCode" />：
		     </td>
		     <td class="brightStyle">
		       <#if modify>
			   		<input type="hidden" name="thirdSubject.thirdCode" value="${result.thirdSubject?if_exists.thirdCode?if_exists}">		       
		       	    ${result.thirdSubject?if_exists.thirdCode?if_exists}
		       <#else>
			   		<input type="text" name="thirdSubject.thirdCode" maxlength="32" value="${result.thirdSubject?if_exists.thirdCode?if_exists}">
			   </#if>
		     </td>
		   </tr>			   		   
		   <tr>
		     <td class="grayStyle" width="15%" id="f_subjectClass">
		      &nbsp;<@bean.message key="filed.subjectKind" />：
		     </td>
		     <td class="brightStyle">
		           <select id="subjectClass" name="thirdSubject.secondSubject.firstSubject.subjectClass.subjectCode"  style="width:100px;" >
		         	  <option value="${result.thirdSubject?if_exists.secondSubject?if_exists.firstSubject?if_exists.subjectClass?if_exists.subjectCode?if_exists}"><@bean.message key="common.selectPlease" />...</option>
		           </select>     
		     </td>
		   </tr>	
		   <tr>
		     <td class="grayStyle" width="15%" id="f_firstSubject">
		      &nbsp;<@bean.message key="filed.firstSubject" />：
		     </td>
		     <td class="brightStyle">
		           <select id="firstSubject" name="thirdSubject.secondSubject.firstSubject.firstCode"  style="width:100px;" >
		         	  <option value="${result.thirdSubject?if_exists.secondSubject?if_exists.firstSubject?if_exists.firstCode?if_exists}"><@bean.message key="common.selectPlease" />...</option>
		           </select>     
		     </td>		     
		   </tr>		   	   			   
		   <tr>
		     <td class="grayStyle" width="15%" id="f_secondSubject">
		      &nbsp;<@bean.message key="filed.secondSubject" />：
		     </td>
		     <td class="brightStyle">
		           <select id="secondSubject" name="thirdSubject.secondSubject.secondCode"  style="width:100px;" >
		         	  <option value="${result.thirdSubject?if_exists.secondSubject?if_exists.secondCode?if_exists}"><@bean.message key="common.selectPlease" />...</option>
		           </select>     
		     </td>
		   </tr>	
		   <tr>
		     <td class="grayStyle" width="20%" id="f_name">
		      &nbsp;<@bean.message key="filed.tutorAndMasterCN" />：
		     </td>
		     <td class="brightStyle">
				<input type="text" name="thirdSubject.name" maxlength="30" value="${result.thirdSubject?if_exists.name?if_exists}"> </td>   
		     </td>
		   </tr>
		   <tr>
		     <td class="grayStyle" width="25%" id="f_nameEn">
		      &nbsp;<@bean.message key="filed.tutorAndMasterEN" />：
		     </td>
		     <td class="brightStyle">
				<input type="text" name="thirdSubject.nameEn" maxlength="30" value="${result.thirdSubject?if_exists.nameEn?if_exists}"> </td>   
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
    
     var a_fields = {
        <#if result.isNew='true' >
         'thirdSubject.thirdCode':{'l':'<@bean.message key="filed.tutorAndMasterCode" />', 'r':true, 't':'f_thirdCode','mx':16},
        </#if>  
         'thirdSubject.secondSubject.firstSubject.subjectClass.subjectCode':{'l':'<@bean.message key="filed.subjectKind" />', 'r':true, 't':'f_subjectClass'},
         'thirdSubject.secondSubject.firstSubject.firstCode':{'l':'<@bean.message key="filed.firstSubject" />', 'r':true, 't':'f_firstSubject'},
         'thirdSubject.secondSubject.secondCode':{'l':'<@bean.message key="entity.specialityAspect" />', 'r':true, 't':'f_secondSubject'},
         'thirdSubject.name':{'l':'<@bean.message key="filed.tutorAndMasterCN" />', 'r':true, 't':'f_name'}           
     };

     var v = new validator(form , a_fields, null);
     if (v.exec()) {
		 form.action="tutorAspect.do?method=thirdSubjectOperation&isNew="+document.pageGoForm.isNew.value;   		 
		 form.submit();     
      }
    
   }
 </script>
<#include "/templates/foot.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/CascadeSelect.js"></script> 
<#include "cascade3Menu.ftl"/>