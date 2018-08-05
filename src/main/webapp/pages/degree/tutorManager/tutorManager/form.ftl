<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<body  LEFTMARGIN="0" TOPMARGIN="0">
<#assign labInfo><@bean.message key="page.teacherForm.label" /></#assign>     
<#include "/templates/back.ftl"/>  
    <#assign tutor = result.tutor?if_exists>
    <form action="tutorStd.do?method=saveOrUpdateTutor" name="tutorForm" method="post" onsubmit="return false;">  
    <input type="hidden" name="isTutor" value="${RequestParameters.isTutor?if_exists}"> 
    <input type="hidden" name="tutorId" value="${result.tutor?if_exists.id?if_exists}">      
    <#if RequestParameters.isTutor?exists>
    	<#assign tutorFlag = true>
    <#else>	
	    <#assign tutorFlag = false>
    </#if>  
 
    <#include "tutorBaseInfo.ftl"/>           
    <#include "tutorAddressInfo.ftl"/>  
    <#include "tutorDegreeInfo.ftl"/> 
  
	 <table width="90%" align="center" class="listTable">
	   <tr class="grayStyle">
          <td colspan="4"><input type="checkbox" name="otherDisplay" onClick="displayDiv(event,'otherInfo')"/> 其他信息</td>
       </tr>
     </table>
       
       <div id="otherInfo" style="display:none">
       <table  width="90%" align="center" class="listTable">
       <tr>
	     <td width="15%" align="center" class="grayStyle"><@bean.message key="teacher.dateOfJoin"/>: </td>
	     <td width="35%" class="brightStyle">
	         <input type="text" name="tutor.dateOfJoin" maxlength="10" <#if tutorFlag>disabled</#if>
	                value="${tutor.dateOfJoin?if_exists}" onfocus='calendar()'>
	      </td>
         <td width="50%"align="center" colspan="2"  class="grayStyle">
                 <input type="checkbox" name="tutor.isTeaching" <#if tutorFlag>disabled</#if>
	       	      <#if tutor.isTeaching?if_exists == true>
           	        checked
	              </#if> />
                <@bean.message key="teacher.isTeaching"/> 
                <input type="checkbox" name="tutor.isEngageFormRetire" <#if tutorFlag>disabled</#if>
	       	      <#if tutor.isEngageFormRetire?if_exists == true>
           	        checked
	              </#if> />
                <@bean.message key="teacher.isEngageFormRetire"/> 
                 <input type="checkbox" name="tutor.isConcurrent" <#if tutorFlag>disabled</#if>
	       	      <#if tutor.isConcurrent?if_exists == true>
           	        checked
	              </#if> />
                <@bean.message key="teacher.isConcurrent"/>                                 
          </td>	     
	   </tr>	   
	   <tr>
	     <td align="center"  class="grayStyle">  <@bean.message key="attr.createAt" />: </td>
	     <td class="brightStyle">
	      <input type="hidden" name="createAt"  id="createAt" readonly="true" 
	       value="${(tutor.createAt?string("yyyy-MM-dd"))?if_exists}" /> 
	       ${(tutor.createAt?string("yyyy-MM-dd"))?if_exists}
	     </td>
	     <td rowspan="2" align="center" id="f_remark"  class="grayStyle"><@bean.message key="common.remark" />:</td>
	     <td rowspan="2" class="brightStyle">
	       <textarea name="tutor.remark" <#if tutorFlag>disabled</#if>
              cols="20" rows="2">${tutor.remark?if_exists}</textarea>
	      </td>
	   </tr>
	   <tr>
	     <td align="center"  class="grayStyle">  <@bean.message key="attr.modifyAt" />:</td>
	     <td class="brightStyle">
 	       <input type="hidden" name="modifyAt" id="modifyAt" readonly="true"
	       value="${(tutor.modifyAt?string("yyyy-MM-dd"))?if_exists}" /> 
	       ${(tutor.modifyAt?string("yyyy-MM-dd"))?if_exists}
	     </td>
	   </tr>	   
       </table>
       </div>    	   
      <table  width="90%" align="center" border="1" class="listTable">
	   <tr align="center">
	     <td colspan="5" class="darkColumn">
           <input type="hidden"  name="dateEstablished" value="" /><!-- JUST TRICK FOR UNIFORM-->	     
           <input type="hidden"  name="isNew" value="${result.isNew?if_exists}">          
           <input type="button" onClick='save(this.form)' value="<@bean.message key="system.button.submit"/>"  class="buttonStyle"/>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;  
           <input type="reset"  value="<@bean.message key="system.button.reset"/>"  class="buttonStyle"/>                  
	     </td>
	   </tr>
	   </form>
     </table>
   
 <script language="javascript" > 
     function cancel(){          
       	   document.tutorForm.action="tutorManage.do?method=index";
       	   document.tutorForm.submit();
     }

    function save(form){
     var a_fields = {
         'tutor.code':{'l':'<@bean.message key="teacher.code" />', 'r':true, 't':'f_code'},         
         'tutor.name':{'l':'<@bean.message key="attr.personName" />', 'r':true, 't':'f_name'},
         'tutor.addressInfo.email':{'l':'<@bean.message key="common.email"/>','r':false,'t':'f_email','f':'email'},
         'tutor.addressInfo.postCodeOfFamily':{'l':'<@bean.message key="common.postCodeOfFamily" />','r':false,'t':'f_postCodeOfFamily','f':'integer'},
         'tutor.addressInfo.postCodeOfCorporation':{'l':'<@bean.message key="common.postCodeOfCorporation" />','r':false,'t':'f_postCodeOfCorporation','f':'integer'},
         'tutor.addressInfo.mobilePhone':{'l':'<@bean.message key="common.mobilePhone" />','r':false,'t':'f_mobilePhone','f':'integer'}
     };
    
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
        form.action=merge("tutorManager.do?method=saveOrUpdateTutor");
        form.submit();
     }
     
   }
   function displayDiv(event,divId){
       var div = document.getElementById(divId);
       var thisCheck = getEventTarget(event);
       if (thisCheck.checked==true){
          div.style.display="block";
          f_frameStyleResize(self)
       }
       else{
         div.style.display="none";
         f_frameStyleResize(self)  
       }
   }  
  function merge(action){
	 if (document.tutorForm.isTutor.value != ''){
	 	var mergeAction = action.substr(0,action.indexOf('.')) +'_tutor'+action.substr(action.indexOf('.'));
	 	return mergeAction;
	 }
	 return action;
  }   
 </script>
 </body> 
<#include "/templates/foot.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/CascadeSelect.js"></script> 
<#include "/templates/depart3CascadeSelect.ftl"/> 
  