<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table cellpadding="0" cellspacing="0" border="0" width="100%" >
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
     <form name="pageGoForm" method="post" action="" onsubmit="return false;">
     <table width="80%" align="center" class="listTable">
      <tr> 
       <td colspan="2" align="center" class="darkColumn"><B><@bean.message key="info.passportDeadlineManager.studentInfoSearch"/></B></td>
      </tr>
	  <tr>
	     <td class="grayStyle" width="25%" id="f_belongToYear">
	      &nbsp;<@bean.message key="filed.enrollYearAndSequence"/>：
	     </td>
	     <td class="brightStyle">
	       &nbsp;<@bean.message key="filed.enrollYear"/><input type="text" name="belongToYear" maxlength="4" size="4" value="${RequestParameters.belongToYear?if_exists}">/
	       &nbsp;<@bean.message key="filed.sequence"/><input type="text" name="sequence" maxlength="2" size="2" value="${RequestParameters.sequence?if_exists}"/>
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
	  <#assign moduleName=RequestParameters['moduleName']/>
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
	  <#--选择学生类别-->
      <#assign studentTypeId = "student.type.id"/>
	  <#assign studentTypeDescriptions = "studentTypeDescriptions"/>
	  <#include "/pages/selector/studentTypeSelectorBarWithAuthority.ftl"/>
	  <tr>
	   <td colspan="2" align="center" class="darkColumn">
	    <input type="hidden" name="pageNo" value="1"/>
	    <input type="hidden" name="method" value="${RequestParameters.method}"/>
	    <input type="hidden" name="moduleName" value="${RequestParameters.moduleName}"/>
	    <input type="button" onClick="search()" value="<@bean.message key="system.button.submit"/>" name="button1" class="buttonStyle"/>&nbsp;
	    <input type="reset" onClick="document.resetForm.submit()" value="<@bean.message key="system.button.reset"/>" name="reset1"  class="buttonStyle"/>
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
     <B><@bean.message key="std.stdList"/></B>
    </td>
   </tr>   
   <tr>
    <td>
     <table width="80%" align="center" class="listTable">
       <form name="listForm" onSubmit="return false;">
	   <tr align="center" class="darkColumn">
	     <td align="center"><input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('stdId'),event);"></td>
	     <td width="15%"><@bean.message key="attr.personName"/></td>
	     <td width="10%"><@bean.message key="attr.stdNo"/></td>
	     <td width="25%"><@bean.message key="entity.college"/></td>
	     <td width="25%"><@bean.message key="entity.studentType"/></td>
	     <td><@bean.message key="filed.enrollYearAndSequence"/></td>
	   </tr>	   
	   <#list (result.studentList.items)?if_exists as student>
	   <#if student_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if student_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
	    <td align="center"><input type="checkBox" name="stdId" value="${student.id}"></td>
	    <td>
	     <a href="studentDetailByManager.do?method=detail&stdId=${student.id}">
	      &nbsp;<@i18nName student?if_exists/>
	     </a>
	    </td>
	    <td>&nbsp;${student.code}</td>
	    <td>&nbsp;<@i18nName student.department?if_exists/></td>
	    <td>&nbsp;<@i18nName student.type?if_exists/></td>
	    <td align="center">${student.enrollYear}</td>
	   </tr>
	   </#list>
	   </form>
	   <#assign paginationName="studentList"/>
	   <#include "/templates/pageBar.ftl"/>
     </table>
    </td>
   </tr>
  </table>  
  
  <form name="resetForm" method="post"> 
    <input type="hidden" name="method" value="${RequestParameters.method}"/>
    <input type="hidden" name="moduleName" value="${RequestParameters.moduleName}"/>
    <input type="hidden" name="pageNo" value="1"/>
  </form>
 </body>
 <script>
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("stdId")));
    }
    
    function search(){
       var a_fields = {
         'belongToYear':{'l':'<@bean.message key="adminClass.enrollYear"/>', 'r':false, 't':'f_belongToYear', 'f':'unsigned', 'mn':4},
         'sequence':{'l':'<@bean.message key="attr.enrollTurn"/>', 'r':false, 't':'f_belongToYear', 'f':'unsigned'}
       };
     
       var v = new validator(document.pageGoForm, a_fields, null);
       
       if (v.exec()) {
          if (!(document.pageGoForm.belongToYear.value=="" && document.pageGoForm.sequence.value=="")) {
              if (document.pageGoForm.belongToYear.value!="" && document.pageGoForm.sequence.value!=""){
                  document.pageGoForm['student.enrollYear'].value = 
           		  	document.pageGoForm.belongToYear.value + "-" + document.pageGoForm.sequence.value;
              } else {
                  alert("<@bean.message key="filed.unIntegrity"/>!");
                  return false;
              }
          }
          var href = self.location.href;
          var url = href.split("?");
          var action = url[0].split("/");
          document.pageGoForm.action = action[action.length-1];
          document.pageGoForm.submit();
       }
    }
    
    function pageGo(pageNo){
       document.pageGoForm.pageNo.value=pageNo;
       search();
    }
    
    function pageGoWithSize(pageNo,pageSize){
       var form = document.pageGoForm;
       form.pageNo.value = pageNo;
       form.action+="?pageSize="+pageSize;
       form.submit();
    }
 </script>
<#include "/templates/foot.ftl"/>