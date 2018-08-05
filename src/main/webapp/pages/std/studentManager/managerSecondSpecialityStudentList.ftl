<#include "/templates/head.ftl"/>
<script language="JavaScript" src="<@bean.message key="menu.js.url"/>"></script>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
<#assign tableWidth="90%"/>
<#if RequestParameters['moduleName']?exists>
<#assign moduleName=RequestParameters['moduleName']>
<#else>
<#assign moduleName="SearchStudent">
</#if>
<#assign formName="pageGoForm"/>
<#assign formAction="studentManager.do"/>
<#assign tableTitle="学籍查询项"/>
<#assign isCalendarNeed=false/>
<#assign isSecondSpecialityNeed=true/>
<#assign isFirstSpecialityNeed=true/>
<#assign tableWidth="90%"/>
<#assign method=RequestParameters.method/>
 <table cellpadding="0" cellspacing="0" border="0" width="100%" >
   <tr>
    <td>
	<table id="topBar" width="90%" align="center"></table>
	</td>
   </tr>
    <tr>
    <td>
    <#assign hiddenInput>
       <input type="hidden" name="student.active" value="1"/>
       <input type="hidden" name="student.inSchool" value="1"/>
    </#assign>
	<#include "/pages/selector/stdSearchForm.ftl"/>
	</td>
   </tr>
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <tr>
    <td colspan="4" align="center" height="30">
      
    </td>
   </tr>
   <#assign paginationName="studentList"/>
   <#if RequestParameters['searchFalg']?default('')=="search" >
   <script>MM_changeSearchBarStyle('searchBar');</script>
   <tr>
    <td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B><@bean.message key="std.stdList"/></B>
    </td>
   </tr>   
   <tr>
    <td>
     <table width="90%" align="center" class="listTable">
       <form name="listForm" action="" onSubmit="return false;">
       <#assign collegeShow = false/>
       <#assign secondMajorShow = true/>
       <#assign secondAspectShow = true/>
       <#assign isSecondMajorStudyShow = true/>
       <#assign adminClassShow = true/>
       <#assign majorTypeId = true/>
       <#assign isSecondMajorThesisNeedShow = true/>
       <#include "/pages/std/stdList.ftl"/>
	   </form>
	   <#include "/templates/newPageBar.ftl"/>
     </table>
    </td>
   </tr>
   </#if>
  </table> 
 </body>
 <script>
 	function doAction(){
 		gotoWithSingleParam("studentManager.do?method=updateSecondSpecialityStudentForm","stdIds");
	}
	
	function doAddition(){
		location.href="./studentManager.do?method=addSecondSpecialityStudentForm";
	}
	
	function exportData(){//location.href="./studentFile.do?method=exportStudentExcel&ids="+getIds();		
		var form = document.pageGoForm;
		var method = form.all.method;
		var oldMethod = method.value;
		method.value = "exportSecondSpecialityStudentExcel";
		var params = getInputParams(form);
		method.value = oldMethod;
		location.href="./studentFile.do?ids="+getIds()+params;
    }
 
	var bar=new ToolBar('topBar','双专业学籍信息维护',null,true,true);
	bar.setMessage('<@getMessage/>');
	bar.addItem("搜索栏","MM_changeSearchBarStyle('searchBar');","search.gif");
	bar.addItem("新增双专业学生信息",doAddition);
	bar.addItem("修改双专业学籍信息",doAction);
	bar.addItem("导出双专业学籍信息",exportData,'excel.png');
	
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("stdId")));
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