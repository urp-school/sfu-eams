 <table cellpadding="0" cellspacing="0" border="0" width="100%" >
   <tr>
    <td>
     <table width="80%" align="center">
      <TR>
       <a href="javascript:MM_changeSearchBarStyle('searchBar');">
       <TD>
         <span class="searchPicStyle"></span>        
       </TD>
       </a>
      </TR>
     </table>
     <div id="searchBar" style="position:relative; visibility: hidden; display:none;"> 
     <form name="pageGoForm" method="post" action="${pageGoFormAction}" onsubmit="return false;">
     <table width="80%" align="center" class="listTable">
      <tr> 
       <td colspan="2" align="center" class="darkColumn"><B>asdf<@bean.message key="info.passportDeadlineManager.studentInfoSearch"/></B></td>
      </tr>
      <tr>
	   <td class="grayStyle" width="25%" id="f_belongToYear">&nbsp;<@bean.message key="attr.stdNo"/>：</td>
	   <td class="brightStyle">
	    <input type="text" name="student.code" maxlength="32" value="<#if RequestParameters['student.code']?exists>${RequestParameters['student.code']}<#--><#elseif RequestParameters['stdId']?exists>${RequestParameters['stdId']}--></#if>" />
       </td>
	  </tr>
	  <tr>
	   <td class="grayStyle" width="25%" id="f_belongToYear">&nbsp;<@bean.message key="attr.personName"/>：</td>
	   <td class="brightStyle">
	    <input type="text" name="student.name" maxlength="20" value="${RequestParameters['student.name']?if_exists}" />
       </td>
	  </tr>
	  <tr>
	   <td class="grayStyle" width="25%" id="f_belongToYear">&nbsp;<@bean.message key="filed.enrollYearAndSequence"/>：</td>
	   <td class="brightStyle">	   
	    <input type="text" name="student.enrollYear" maxlength="7" size="6" value="${RequestParameters['student.enrollYear']?if_exists}">
       </td>
	  </tr>
	  
	  <#if moduleName=="StudentAuditManager">
	  <tr>
	     <td class="grayStyle" width="25%" id="f_belongToYear">
	      &nbsp;<@bean.message key="attr.graduate.auditStatus"/>：
	     </td>
	     <td class="brightStyle">
	     <#if result.studenGraduateAuditStatus?exists>
	     	<#assign studentGraduateAuditStatus = result.studenGraduateAuditStatus />
	     <#else>
	     	<#assign studentGraduateAuditStatus = "" />
	     </#if>
	      &nbsp;<input type="checkBox" name="studentGraduateAuditStatusCheckbox" value="1" <#if (studentGraduateAuditStatus?index_of(',1,')>=0)>checked</#if>/><@bean.message key="attr.graduate.outsideExam.auditPass"/>
	       <input type="checkBox" name="studentGraduateAuditStatusCheckbox" value="0" <#if (studentGraduateAuditStatus?index_of(',0,')>=0)>checked</#if>/><@bean.message key="attr.graduate.outsideExam.noAuditPass"/>	       
	       <input type="checkBox" name="studentGraduateAuditStatusCheckbox" value="null" <#if (studentGraduateAuditStatus?index_of(',null,')>=0)>checked</#if>/><@bean.message key="attr.graduate.outsideExam.nullAuditPass"/>	       
	     </td>
	  </tr>
	  </#if>
	  
	  <#--选择部门-->
	  <#assign departmentId = "student.department.id" />
	  <#assign departmentDescriptions = "departmentDescriptions" />
	  <#include "/pages/selector/singleDepartmentSelectorBarWithAuthority.ftl" />
      <#--选择专业-->
      <#assign specialityId = "student.firstMajor.id" />
	  <#assign specialityDescriptions = "specialityDescriptions" />
	  <#include "/pages/selector/singleSpecialitySelectorBarWithDepartment.ftl" />
	  <#--选择专业方向-->
	  <#assign specialityAspectId = "student.firstAspect.id" />
	  <#assign specialityAspectDescriptions = "specialityAspectDescriptions" />
	  <#include "/pages/selector/specialityAspectSelectorBarWithSpeciality.ftl" />
	  <#--选择学生类别-->
      <#assign studentTypeId = "student.type.id" />
	  <#assign studentTypeDescriptions = "studentTypeDescriptions" />
	  <#include "/pages/selector/studentTypeSelectorBarWithAuthority.ftl" />
	  <tr>
	   <td colspan="2" align="center" class="darkColumn">
	    <input type="hidden" name="pageNo" value="1" />
	    <input type="hidden" name="method" value="${RequestParameters.method}" />
	    <input type="hidden" name="moduleName" value="${moduleName}" />
	    <input type="button" onClick="search()" value="<@bean.message key="system.button.submit" />" name="button1" class="buttonStyle" />&nbsp;
	    <input type="reset" onClick="document.resetForm.submit()" value="<@bean.message key="system.button.reset" />" name="reset1"  class="buttonStyle" />
       </td>
	  </tr>
     </table>
     </div>
    </td>
   </tr>
  </table>
  </form>
  <form name="resetForm" method="post" action="dutyRecordSearch.do"> 
    <input type="hidden" name="method" value="${RequestParameters.method}" />
    <input type="hidden" name="firstMethod" value="${RequestParameters.method?if_exists}" />
    <input type="hidden" name="moduleName" value="${moduleName}" />
    <input type="hidden" name="pageNo" value="1" />
  </form>
