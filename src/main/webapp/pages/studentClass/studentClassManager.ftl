<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/prompt.js"></script>  
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<#assign tableWidth="90%" />
<#if RequestParameters['moduleName']?exists>
<#assign moduleName=RequestParameters['moduleName']>
<#else>
<#assign moduleName="SearchStudent">
</#if>
<#assign formName="pageGoForm" />
<#assign formAction="studentClassManager.do"/>
<#assign tableTitle="学籍查询项" />
<#assign isCalendarNeed=false/>
<#assign select2ndSpeciality=true/>
<#assign showInput></#assign>
<#assign hiddenInput></#assign>
<#assign resetFormHiddenInput></#assign>
<#assign method=RequestParameters.method/>
<table id="topBar" width="90%" align="center"></table>
<script>
  var bar=new ToolBar('topBar','学生班级维护',null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("搜索栏","MM_changeSearchBarStyle('searchBar');","search.gif");
  bar.addItem('添加学生班级','gotoWithParam("studentClassOperation.do?method=assignStudentClassForm","stdId")');
  bar.addItem("设置学生班级","maintainStudentClass()",'action.gif');
  function maintainStudentClass(){
	gotoWithSingleParam('studentClassOperation.do?method=maintainStudentClassForm','stdIds');
  }
</script>
 <table cellpadding="0" cellspacing="0" border="0" width="100%" >
	<tr><td>
	<#assign hiddenInput>
	   <input type="hidden" name="student.active" value="1"/>
	   <input type="hidden" name="student.inSchool" value="1"/>
	</#assign>
	<#include "/pages/selector/stdSearchForm.ftl"/>
	</td></tr>
	<#if studentList?exists&&RequestParameters['searchFalg']?default('')=="search" >
	<script>MM_changeSearchBarStyle('searchBar');</script>
 <div id="toolTipLayer" style="position:absolute; visibility: hidden"></div>
 <script>initToolTips()</script>
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <tr>
    <td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B><@bean.message key="std.stdList"/></B>
    </td>
   </tr>   
   <tr>
    <td>
     <table width="90%" align="center" class="listTable">
       <form name="listForm" action="" onSubmit="return false;">
	   <tr align="center" class="darkColumn">
	     <td width="5%" align="center"><input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('ids'),event);"></td>
	     <td width="15%"><@bean.message key="attr.personName"/></td>
	     <td width="15%"><@bean.message key="attr.stdNo"/></td>
	     <td width="15%"><@bean.message key="entity.college"/></td>
	     <td width="15%"><@bean.message key="entity.studentType"/></td>
	     <td><@bean.message key="entity.adminClass"/></td>
	   </tr>	   
	   <#list studentList?if_exists as student>
	   <#if student_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if student_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
	    <td align="center"><input type="checkBox" name="ids" value="${student.id}"></td>
	    <td><a href="studentDetailByManager.do?method=detail&stdId=${student.id}">&nbsp;<@i18nName student?if_exists/></a></td>
	    <td>&nbsp;${student.code}</td>
	    <td>&nbsp;<@i18nName student.department?if_exists/></td>
	    <td>&nbsp;<@i18nName student.type?if_exists/></td>
	    <td align="center">
		    <#list student.adminClasses?if_exists?sort_by("code") as adminClass>
		    	<#if adminClass_has_next >
	    		<@i18nName adminClass />(${adminClass["code"]})<#if (adminClass_index+1)%2==1>,&nbsp;</#if>
	        	<#if (adminClass_index+1)%2==0><br></#if>
	        	<#else>
	        	<@i18nName adminClass />(${adminClass["code"]})&nbsp;
	        	</#if>
		    </#list>
	    </td>
	   </tr>
	   </#list>
	   </form>
	   <#include "/templates/newPageBar.ftl"/>
     </table>
     </#if>
    </td>
   </tr>
  </table>  
</body>
<script>      
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("ids")));
    }
    
    function pageGo(pageNo){
       document.pageGoForm.pageNo.value=pageNo;
       search();
    }
    
    function pageGoWithSize(pageNo,pageSize){
       var form = document.pageGoForm;
       form.pageNo.value = pageNo;
       form.action="studentClassManager.do?method=studentClassManager&pageSize="+pageSize;
       form.submit();
    }
</script>
<#include "/templates/foot.ftl"/>