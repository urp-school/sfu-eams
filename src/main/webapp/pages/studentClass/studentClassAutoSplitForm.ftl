<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@msg.message key="validator.js.url"/>"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table cellpadding="0" cellspacing="0" border="0" width="100%">
   <tr>
    <td>
     <table width="80%" align="center">
      <TR>
       <TD>
        <a href="#" onClick="MM_changeSearchBarStyle('searchBar')">
         <span class="searchPicStyle"></span>
        </a>
       </TD>
      </TR>
     </table>
     <div id="searchBar" style="position:relative; visibility: hidden; display:none;"> 
     <form name="pageGoForm" method="post" action="studentClassOperation.do" onsubmit="return false;">
     <table width="80%" align="center" class="listTable">
      <tr> 
       <td colspan="2" align="center" class="darkColumn"><B><@msg.message key="info.studentRecordManager.studentSearch"/></B></td>
      </tr>
	  <tr>
	     <td class="grayStyle" width="25%" id="f_belongToYear">
	      &nbsp;<@msg.message key="filed.enrollYearAndSequence"/>：
	     </td>
	     <td class="brightStyle">
	       &nbsp;<@msg.message key="filed.enrollYear"/><input type="text" name="belongToYear" maxlength="4" size="4" value="${RequestParameters.belongToYear?if_exists}">
	       &nbsp;<@msg.message key="filed.sequence"/><input type="text" name="sequence" maxlength="2" size="2" value="${RequestParameters.sequence?if_exists}">
	       <input type="hidden" name="student.enrollYear">
         </td>
	  </tr>
	  <script>
	      var selectedBelongToYear = document.pageGoForm.belongToYear.value;
	      var selectedSequence = document.pageGoForm.sequence.value;
	      if (selectedBelongToYear!="" && selectedSequence!=""){
	         document.pageGoForm['student.enrollYear'].value = selectedBelongToYear + "-" + selectedSequence;
	      }	      
	  </script>
	  <#assign moduleName="AdminClassManager"/>
	  <#--选择部门-->
	  <#assign departmentId = "student.department.id"/>
	  <#assign departmentDescriptions = "departmentDescriptions"/>
	  <#include "/pages/selector/singleDepartmentSelectorBarWithAuthority.ftl"/>
      <#--选择专业-->
      <#assign specialityId = "student.firstMajor.id"/>
	  <#assign specialityDescriptions = "specialityDescriptions"/>
	  <#include "/pages/selector/singleSpecialitySelectorBarWithDepartment.ftl"/>
	  <#--选择专业方向-->
	  <#assign specialityAspectId = "student.firstAspect.id"/>
	  <#assign specialityAspectDescriptions = "specialityAspectDescriptions"/>
	  <#include "/pages/selector/specialityAspectSelectorBarWithSpeciality.ftl"/>
	  <#--选择第二专业方向-->
	  <#assign secondSpecialityAspectId = "student.secondAspect.id"/>
	  <#assign secondSpecialityAspectDescriptions = "secondAspectSpecialityAspectDescriptions"/>
	  <#include "/pages/selector/secondSpecialityAspectSelectorBar.ftl"/>
	  <#--选择学生类别-->
      <#assign studentTypeId = "student.type.id"/>
	  <#assign studentTypeDescriptions = "studentTypeDescriptions"/>
	  <#include "/pages/selector/studentTypeSelectorBarWithAuthority.ftl"/>
	  <tr>
	   <td colspan="2" align="center" class="darkColumn">
	    <input type="hidden" name="classId" value="${RequestParameters.classId}"/>
	    <input type="hidden" name="method" value="${RequestParameters.method}"/>
	    <input type="hidden" name="searchFalg" value="search"/>
	    <input type="hidden" name="pageNo" value="1"/>
	    <input type="button" onClick="search()" value="<@msg.message key="system.button.submit"/>" name="button1" class="buttonStyle"/>&nbsp;
	    <input type="reset" onClick="document.resetForm.submit()" value="<@msg.message key="system.button.reset"/>" name="reset1"  class="buttonStyle"/>
       </td>
	  </tr>
     </table>
     </div>
    </td>
   </tr>   
  </table>
  </form>
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <tr>
    <td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B><@msg.message key="info.studentClassManager.autoSplit"/></B>
    </td>
   </tr>   
   <tr>
    <td>
     <table width="80%" align="center" class="listTable">
       <tr>
	     <td colspan="2" class="grayStyle">
	      &nbsp;<@msg.message key="info.studentClassManager.autoSplitClass"/>：
	     </td>
	     <td colspan="4" class="brightStyle">
	      <#list result.adminClassList?if_exists?sort_by("code") as adminClass>
	      	<#if adminClass_has_next ><@i18nName adminClass?if_exists/>(${adminClass["code"]}),&nbsp;
	        <#else>
	        	<@i18nName adminClass?if_exists/>(${adminClass["code"]})
	        </#if>
	        <#if (adminClass_index+1)%2==0><br></#if>
	      </#list>
	     </td>
	   </tr>
       <form name="splitForm" action="studentClassOperation.do" method="post" onsubmit="return false;">
	   <tr align="center" class="darkColumn">
	     <td align="center"><input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('ids'),event);"></td>
	     <td width="15%"><@msg.message key="attr.personName"/></td>
	     <td width="10%"><@msg.message key="attr.stdNo"/></td>
	     <td width="25%"><@msg.message key="entity.college"/></td>
	     <td width="25%"><@msg.message key="entity.speciality"/></td>
	     <td><@msg.message key="filed.enrollYearAndSequence"/></td>
	   </tr>	   
	   <#list result.studentList?if_exists.items?if_exists as student>
	   <#if student_index%2==1 ><#assign class="grayStyle"></#if>
	   <#if student_index%2==0 ><#assign class="brightStyle"></#if>
	   <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
	    <td align="center"><input type="checkBox" name="ids" value="${student.id}"></td>
	    <td>
	     <a href="studentDetailByManager.do?method=detail&stdId=${student.id}">
	      &nbsp;<@i18nName student?if_exists/>
	     </a>
	    </td>
	    <td>&nbsp;${student.code}</td>
	    <td>&nbsp;<@i18nName student.department?if_exists/></td>
	    <td>&nbsp;<@i18nName student.firstMajor?if_exists/></td>
	    <td align="center">${student.enrollYear}</td>
	   </tr>
	   </#list>
	   <#assign paginationName="studentList"/>
	   <#if result[paginationName]?exists&&RequestParameters['searchFalg']?default('')=="search">
	   <#include "/templates/pageBar.ftl"/>
	   </#if>
	   <tr>
	    <td colspan="6" align="center" class="darkColumn">
	     <input type="hidden" name="method" value="autoSplitClass"/>
	     <input type="hidden" name="stdId"/>
	     <input type="hidden" name="classId" value="${RequestParameters.classId}"/>
	     <input type="button" onClick="confirmForm()" value="<@msg.message key="info.ClassManager.selectStudentIntoClass"/>" name="button1" class="buttonStyle"/>&nbsp;
        </td>
	   </tr>
	   </form>
	   <tr> 
       	<td colspan=6" class="brightStyle"><@msg.message key="info.explanation.front"/><@msg.message key="info.studentClassManager.autoSplitWarning"/></td>
   	   </tr>
     </table>
    </td>
   </tr>
  </table>  
  
  <form name="resetForm" method="post" action="studentClassOperation.do"> 
    <input type="hidden" name="classId" value="${RequestParameters.classId}"/>
    <input type="hidden" name="method" value="${RequestParameters.method}"/>
  </form>
 </body>
 <script>
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("ids")));
    }
    
    function pageGo(pageNo){
       document.pageGoForm.pageNo.value=pageNo;
       document.pageGoForm.submit();
    }
    
    function pageGoWithSize(pageNo,pageSize){
       var form = document.pageGoForm;
       form.pageNo.value = pageNo;
       form.action+="?pageSize="+pageSize;
       form.submit();
    }
    
    function search(){
       var a_fields = {
         'belongToYear':{'l':'<@msg.message key="adminClass.enrollYear"/>', 'r':false, 't':'f_belongToYear', 'f':'unsigned', 'mn':4},
         'sequence':{'l':'<@msg.message key="attr.enrollTurn"/>', 'r':false, 't':'f_belongToYear', 'f':'unsigned'}
       };
     
       var v = new validator(document.pageGoForm, a_fields, null);
       
       if (v.exec()) {
          if (!(document.pageGoForm.belongToYear.value=="" && document.pageGoForm.sequence.value=="")) {
              if (document.pageGoForm.belongToYear.value!="" && document.pageGoForm.sequence.value!=""){
                  document.pageGoForm['student.enrollYear'].value = 
           		  	document.pageGoForm.belongToYear.value + "-" + document.pageGoForm.sequence.value;
              } else {
                  alert("<@msg.message key="filed.unIntegrity"/>!");
                  return false;
              }
          }
          document.pageGoForm.submit();
       }
    }
    
    function confirmForm(){
         if (getIds() == ""){
            alert("<@msg.message key="info.studentClassManager.selectStudent"/>!");
            return false;
         }else{
            document.splitForm.stdId.value = getIds();
            splitForm.submit();
         }
    }
 </script>
<#include "/templates/foot.ftl"/>