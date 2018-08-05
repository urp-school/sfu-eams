<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
<#assign tableWidth="90%"/>
<#if RequestParameters['moduleName']?exists>
<#assign moduleName=RequestParameters['moduleName']>
<#else>
<#assign moduleName="StudentManager">
</#if>
<#assign formName="pageGoForm"/>
<#assign formAction="dutyRecordSearch.do"/>
<#assign tableTitle="考勤查询项"/>
<#assign isCalendarNeed=false/>
<#assign select2ndSpeciality=true/>
<#assign showInput></#assign>
<#assign hiddenInput></#assign>
<#assign resetFormHiddenInput></#assign>
<#assign method=RequestParameters.method/>
  
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <tr><td>
	<table id="searchTopBar" width="90%" align="center"></table>
	</td></tr>
	<tr><td>
	<#include "/pages/selector/stdSearchForm.ftl"/>
	</td></tr>
	<#if studentList?exists&&RequestParameters['searchFalg']?default('')=="search">
	<script>MM_changeSearchBarStyle('searchBar');</script>
   <tr>
    <td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B><@bean.message key="page.studentDutyRecordSearch.title"/><@bean.message key="std.stdList"/></B>
    </td>
   </tr>   
   <tr>
    <td>
     <table width="90%" align="center" class="listTable">
       <form name="listForm" action="" onsubmit="return false;">
	   <tr align="center" class="darkColumn">	     
	     <td width="15%"><@bean.message key="attr.personName"/></td>
	     <td width="10%"><@bean.message key="attr.stdNo"/></td>
	     <td width="25%"><@bean.message key="entity.college"/></td>
	     <td width="25%"><@bean.message key="entity.studentType"/></td>
	     <td><@bean.message key="filed.enrollYearAndSequence"/></td>
	   </tr>	   
	   <#list studentList?if_exists as student>
	   <#if student_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if student_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">	    
	    <td>
	     <a href="dutyRecordSearch.do?method=recordByStudentForm&stdId=${student.id}">
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
	   		<#include "/templates/newPageBar.ftl"/>
     </table>
     </#if>
    </td>
   </tr>
  </table>

 </body>
 <script>
 	var bar = new ToolBar('searchTopBar','学生考勤记录查询',null,true,true);
	bar.setMessage('<@getMessage/>');
	bar.addItem("搜索栏","MM_changeSearchBarStyle('searchBar');","search.gif");
	
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("stdId")));
    }
    
    <#-->
    function search(){
		document.pageGoForm.submit();
	}
    
    function search_bak(){
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
          document.pageGoForm.submit();
       }
    }
    -->
    
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