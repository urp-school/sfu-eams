<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="bar"></table>
<script>
 	var bar = new ToolBar("bar", "<@msg.message key="info.studentClassManager.classInfSearch"/>", null, true, true);
 	bar.addItem("搜索栏", "MM_changeSearchBarStyle('searchBar')", "search.gif");
 	bar.addBack();
</script>
     <div id="searchBar" style="position:relative; visibility: hidden; display:none;"> 
     <form name="pageGoForm" method="post" action="studentClassOperation.do" onsubmit="return false;">
    <input type="hidden" name="pageNo" value="1"/>
     <table width="80%" align="center" class="listTable">
      <tr>
       <td colspan="2" align="center" class="darkColumn"><B><@bean.message key="info.studentClassManager.classInfSearch"/></B></td>
      </tr>
	  <tr>
	     <td class="grayStyle" width="25%" id="f_belongToYear">
	      &nbsp;<@bean.message key="filed.enrollYearAndSequence"/>：
	     </td>
	     <td class="brightStyle">
	       &nbsp;<@bean.message key="filed.enrollYear"/><input type="text" name="belongToYear" maxlength="4" size="4" value="${RequestParameters.belongToYear?if_exists}">
	       &nbsp;<@bean.message key="filed.sequence"/><input type="text" name="sequence" maxlength="2" size="2" value="${RequestParameters.sequence?if_exists}">
	       <input type="hidden" name="adminClass.enrollYear">
         </td>
	  </tr>
	  <script>
	      var selectedBelongToYear = document.pageGoForm.belongToYear.value;
	      var selectedSequence = document.pageGoForm.sequence.value;
	      if (selectedBelongToYear!="" && selectedSequence!=""){
	         document.pageGoForm['adminClass.enrollYear'].value = selectedBelongToYear + "-" + selectedSequence;
	      }	      
	  </script>
	  <#assign moduleName="AdminClassManager"/>
	  <#assign studentTypeId = "adminClass.stdType.id"/>
	  <#--选择部门-->
	  <#assign departmentId = "adminClass.department.id"/>
	  <#assign departmentDescriptions = "departmentDescriptions"/>
	  <#include "/pages/selector/singleDepartmentSelectorBarWithAuthority.ftl"/>
      <#--选择专业-->
      <#assign specialityId = "adminClass.speciality.id"/>
	  <#assign specialityDescriptions = "specialityDescriptions"/>
	  <#include "/pages/selector/singleSpecialitySelectorBarWithDepartment.ftl"/>
	  <#--选择专业方向-->
	  <#assign specialityAspectId = "adminClass.aspect.id"/>
	  <#assign specialityAspectDescriptions = "specialityAspectDescriptions"/>
	  <#include "/pages/selector/specialityAspectSelectorBarWithSpeciality.ftl"/>
	  <#--选择学生类别-->
      
	  <#assign studentTypeDescriptions = "studentTypeDescriptions"/>
	  <#include "/pages/selector/studentTypeSelectorBarWithAuthority.ftl"/>
	  <tr>
	   <td colspan="2" align="center" class="darkColumn">
	    <input type="hidden" name="flag" value="search"/>
	    <input type="hidden" name="stdId" value="${RequestParameters.stdId}"/>
	    <input type="hidden" name="method" value="${RequestParameters.method}"/>
	    <input type="button" onClick="search()" value="<@bean.message key="system.button.submit"/>" name="button1" class="buttonStyle"/>&nbsp;
	    <input type="button" onClick="document.resetForm.submit()" value="<@bean.message key="system.button.reset"/>" name="reset1"  class="buttonStyle"/>
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
     <B><@bean.message key="info.studentClassManager.classList"/></B>
    </td>
   </tr>   
   <tr>
    <td>
     <table width="80%" align="center" class="listTable">
       <tr>
	     <td colspan="2" class="grayStyle" width="25%" id="f_status">
	      &nbsp;<@bean.message key="info.studentRecordManager.editStatusStudentName"/>：
	     </td>
	     <td colspan="4" class="brightStyle">
	      <#list result.nameList?if_exists?sort_by("code") as name>
	        ${name["name"]}(<@bean.message key="attr.stdNo"/>
	        :${name["code"]})<#if (name_index+1)%2==1>,&nbsp;</#if>
	        <#if (name_index+1)%2==0><br></#if>
	      </#list>
	     </td>
	   </tr>
       <form name="assignForm" action="studentClassOperation.do" method="post" onSubmit="return false;">
	   <tr align="center" class="darkColumn">
	     <td align="center"></td>
	     <td width="25%"><@bean.message key="info.studentClassManager.className"/></td>
	     <td width="20%"><@bean.message key="info.studentClassManager.classId"/></td>
	     <td width="25%"><@bean.message key="entity.college"/></td>
	     <td width="25%"><@bean.message key="entity.studentType"/></td>
	   </tr>	   
	   <#list (adminClassList?sort_by("code"))?if_exists as adminClass>
	   <#if adminClass_index%2==1 ><#assign class="grayStyle"></#if>
	   <#if adminClass_index%2==0 ><#assign class="brightStyle"></#if>
	   <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
	    <td align="center"><input type="radio" name="classId" value="${adminClass.id}"/></td>
	    <td>
	    	<a href="studentClassOperation.do?method=detail&classId=${adminClass.id}">
	    	&nbsp;<@i18nName adminClass?if_exists/>
	    	</a>
	    </td>
	    <td>&nbsp;${adminClass.code}</td>
	    <td>&nbsp;<@i18nName adminClass.department?if_exists/></td>
	    <td>&nbsp;<@i18nName adminClass.stdType?if_exists/></td>
	   </tr>
	   </#list>
		<#include "/templates/newPageBar.ftl"/>
	   <tr>
	    <td colspan="4" align="center" class="darkColumn">
	     <input type="hidden" name="method" value="assignStudentClass"/>
	     <input type="hidden" name="stdId" value="${RequestParameters.stdId}"/>
	     <input type="button" onClick="confirmForm()" value="<@bean.message key="info.studentClassManager.selectStudentIntoClass"/>" name="button5" class="buttonStyle"/>&nbsp;
        </td>
        <td colspan="2" align="center" class="darkColumn">
        <input type="button" onClick="confirmCancel()" value="<@bean.message key="info.studentClassManager.cancelStudentFromClass"/>" name="button6" class="buttonStyle"/>&nbsp;
        </td>
	   </tr>
	   </form>
     </table>
    </td>
   </tr>
  </table>
  
  <form name="resetForm" method="post" action="studentClassOperation.do"> 
    <input type="hidden" name="stdId" value="${RequestParameters.stdId}"/>
    <input type="hidden" name="method" value="${RequestParameters.method}"/>
    <input type="hidden" name="pageNo" value="1"/>
  </form>
 </body>
 <script>
   function search(){
       var a_fields = {
         'belongToYear':{'l':'<@bean.message key="adminClass.enrollYear"/>', 'r':false, 't':'f_belongToYear', 'f':'unsigned', 'mn':4},
         'sequence':{'l':'<@bean.message key="attr.enrollTurn"/>', 'r':false, 't':'f_belongToYear', 'f':'unsigned'}
       };
     
       var v = new validator(document.pageGoForm, a_fields, null);
       
       if (v.exec()) {
          if (!(document.pageGoForm.belongToYear.value=="" && document.pageGoForm.sequence.value=="")) {
              if (document.pageGoForm.belongToYear.value!="" && document.pageGoForm.sequence.value!=""){
                  document.pageGoForm['adminClass.enrollYear'].value = 
           		  	document.pageGoForm.belongToYear.value + "-" + document.pageGoForm.sequence.value;
              } else {
                  alert("<@bean.message key="filed.unIntegrity"/>!");
                  return false;
              }
          }
          if (document.pageGoForm['adminClass.enrollYear'].value=="" && 
              document.pageGoForm['adminClass.department.id'].value=="" &&
              document.pageGoForm['adminClass.stdType.id'].value==""){
              document.pageGoForm.flag.value = "null";
          }
          document.pageGoForm.submit();
       }
    }
    
    function confirmForm(){
        var classId = document.all["classId"];
        var flag = false;
        if(classId!=null){
        if(classId.length==null){
        	if (classId.checked == true){
          	      flag = true;          	      
          	 }
        }
        for (var i=0; i<classId.length; i++){
           if (classId[i].checked == true){
                flag = true;
                break;
           }
        }
        if (flag == true){
           assignForm.submit();
        } else {
           alert("<@bean.message key="info.studentClassManager.selectClass"/>");
        }
        }
   }
   
   function confirmCancel(){
   		if(confirm("<@bean.message key="info.studentClassManager.cancelStudentClass"/>")){
   			document.assignForm.method.value="resetStudentClass";
   			assignForm.submit();
   		}
   }
   
   function pageGo(pageNo){
       document.pageGoForm.pageNo.value=pageNo;       
       search();
    }
 </script>
<#include "/templates/foot.ftl"/>