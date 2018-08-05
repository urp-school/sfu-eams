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
	   <form name="pageGoForm" action="secondSubject.do?method=addSecondSubjectOperation" method="post" onsubmit="return false;">   
	   <tr>
	    <td>	    
	    <table width="100%" align="center" class="listTable">
		   <tr class="darkColumn">
		     <td align="center" colspan="2">
		      <#if result.modify?exists >
		     	<@bean.message key="filed.modifySecondSubject"/>
		     	<#assign modify=true>
		      <#else>
   		         <@bean.message key="filed.addSecondSubject"/>
   		        <#assign modify=false>
		      </#if>	
		     </td>
		   </tr>		   
		   <tr>
		     <td class="grayStyle" width="30%" id="f_firstSubject">
		      &nbsp;<@bean.message key="filed.subjectKind"/>：
		     </td>
		     <td class="brightStyle">&nbsp;
		     	<select id="subjectClass" name="firstSubject.subjectClass.subjectCode" style="width:100px;">
		     		<option value='${result.secondSubject?if_exists.firstSubject?if_exists.subjectClass?if_exists.subjectCode?if_exists}'><@bean.message key="common.selectPlease"/></option>	         	 
		     	</select>
		   </tr>	
		   <tr>
		     <td class="grayStyle" width="30%" id="f_firstSubject">
		      &nbsp;<@bean.message key="filed.firstSubject"/>：
		     </td>
		     <td class="brightStyle">&nbsp;
		     	<select id="firstSubject" name="secondSubject.firstSubject.firstCode" style="width:100px;">
		     		<option value='${result.secondSubject?if_exists.firstSubject?if_exists.firstCode?if_exists}'><@bean.message key="common.selectPlease"/></option>	         	 
		     	</select>
		   </tr>		   	   			   
		   <tr>
		     <td class="grayStyle" width="40%" id="f_departmenty">
		      &nbsp;<@bean.message key="filed.secondSubjectCode"/>：
		     </td>
		     <td class="brightStyle">&nbsp;
				<input type="text" name="secondSubject.secondCode" maxlength="32" value="${result.secondSubject?if_exists.secondCode?if_exists}"></td>
		   </tr>	
		   <tr>
		     <td class="grayStyle" width="40%" id="f_department">
		      &nbsp;<@bean.message key="filed.secondSubjectCN"/>：
		     </td>
		     <td class="brightStyle">&nbsp;
				<input type="text" name="secondSubject.name" maxlength="30" value="${result.secondSubject?if_exists.name?if_exists}"> </td>   
		     </td>
		   </tr>
		   <tr>
		     <td class="grayStyle" width="40%" id="f_department">
		      &nbsp;<@bean.message key="filed.secondSubjectEN"/>：
		     </td>
		     <td class="brightStyle">&nbsp;
				<input type="text" name="secondSubject.nameEn" maxlength="30" value="${result.secondSubject?if_exists.nameEn?if_exists}"> </td>   
		     </td>
		   </tr>
		   <tr>
		     <td class="grayStyle" width="40%" id="f_department">
		      &nbsp;<@bean.message key="filed.masterSetTime"/>：
		     </td>
		     <td class="brightStyle">&nbsp;
				<input type="text" name="secondSubject.doctorDate" maxlength="10" value="${result.secondSubject?if_exists.doctorDate?if_exists}"> </td>   
		     </td>
		   </tr>
		   <tr>
		     <td class="grayStyle" width="40%" id="f_department">
		      &nbsp;<@bean.message key="filed.tutorSetTime"/>：
		     </td>
		     <td class="brightStyle">&nbsp;
				<input type="text" name="secondSubject.masterDate" maxlength="10" value="${result.secondSubject?if_exists.masterDate?if_exists}"> </td>   
		     </td>
		   </tr>		   		   
		   <tr>
		     <td class="grayStyle" width="40%" id="f_department">
		      &nbsp;<@bean.message key="filed.isFirstSet"/>：
		     </td>
		     <td class="brightStyle">&nbsp;
				<input type="radio" name="secondSubject.isFreedom" value="1"
					<#if result.secondSubject?if_exists.isFreedom?exists && result.secondSubject.isFreedom>
						checked
					</#if>			
				><@bean.message key="filed.yes"/>&nbsp;
				<input type="radio" name="secondSubject.isFreedom" value="0" 
					<#if !result.secondSubject?exists>
						checked
					<#elseif result.secondSubject.isFreedom?exists && !result.secondSubject.isFreedom>	
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
		  form.action="secondSubject.do?method=modifySecondSubjectOperation"   		 
	</#if>	 
	 form.submit();     
    }
 </script>
<#include "/templates/foot.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/CascadeSelect.js"></script> 
<#include "cascadeMenu.ftl"/>