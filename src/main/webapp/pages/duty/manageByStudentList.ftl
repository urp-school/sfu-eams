<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
<#assign tableWidth="90%" />
<#if RequestParameters['moduleName']?exists>
<#assign moduleName=RequestParameters['moduleName']>
<#else>
<#assign moduleName="StudentManager">
</#if>
<#assign formName="pageGoForm" />
<#assign formAction="dutyRecordSearch.do" />
<#assign tableTitle="学籍查询项" />
<#assign isCalendarNeed=false />
<#assign select2ndSpeciality=true />
<#assign showInput></#assign>
<#assign hiddenInput></#assign>
<#assign resetFormHiddenInput></#assign>
<#assign method=RequestParameters.method />
  
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <tr><td>
	<table id="searchTopBar" width="90%" align="center"></table>
	</td></tr>
	<tr><td>
	<#include "/pages/selector/stdSearchForm.ftl"/>
	</td></tr>
	<#if studentList?exists&&RequestParameters['searchFalg']?default('')=="search" >
	<script>MM_changeSearchBarStyle('searchBar');</script>
   <tr>
    <td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B><@bean.message key="page.studentDutyRecordManage.title"/><@bean.message key="std.stdList"/></B>
    </td>
   </tr>   
   <tr>
    <td>
     <table width="90%" align="center" class="listTable">
       <form name="listForm" action="" onSubmit="return false;">
	   <tr align="center" class="darkColumn">	     
	     <td align="center"><input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('stdId'),event);"></td>
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
	    <td align="center"><input type="checkBox" name="stdId" value="${student.id}"></td>
	    <td>
	     <a href="dutyRecordSearch.do?method=manageByStudentForm&stdId=${student.id}">
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
	   <#assign paginationName="studentList" />
	  <#include "/templates/newPageBar.ftl"/>
     </table>
     </#if>
    </td>
   </tr>
  </table>  
  <div id="maintainDiv" style="display:none;width:350px;height:110px;position:absolute;top:27px;right:20px;border:solid;border-width:1px; ">  	
  	<div style="display:block;width:100%;height:100%;position:absolute;<#-->border:solid;border-width:1px; -->">
     <iframe  src="#"
     id="adminClassListFrame" name="adminClassListFrame" 
     marginwidth="0" marginheight="0" scrolling="auto"
     frameborder="0"  height="100%" width="100%">
     </iframe>
     </div>
     <div style="display:block;width:100%;height:100%;position:absolute; ">
     <table width="100%" align="center" class="listTable">
     	<tr align="center" class="darkColumn">
     		<td width="50%">开始日期</td>
	     	<td width="50%">结束日期</td>
     	</tr>
     	<tr align="center" class="brightStyle">
     		<td><input type="text" name="beginDate" onfocus="calendar()" size="10"/></td>
	     	<td><input type="text" name="endDate" onfocus="calendar()" size="10"/></td>
     	</tr>
     	<tr align="center" class="brightStyle">
     		<td colspan="2"><input type="button" name="button_d1" value="提交" class="buttonStyle" onclick="submitDiv();"/>&nbsp;&nbsp;
	     	<input type="button" name="button_d2" value="取消" class="buttonStyle" onclick="resetDiv();"/></td>
     	</tr>
     </table>
     </div>
  </div>
<#include "/pages/duty/dutyRuleInf.ftl"/>
<form method="post" aciton="" name="actionForm" onsubmit="return false;"></form>
 </body>
 <script>
 	var form = document.actionForm;
 	function submitDiv(){
 		var bDate, eDate;
	 	if ((bDate = document.all.beginDate.value) == "" || (eDate = document.all.endDate.value) == "") {
	 		alert("请输入开始日期和结束日期!");
	 	} else {
	 		if (bDate > eDate) {
	 			alert("开始日期不能超过结束日期！");
	 			return;
	 		}
	 		location.href="./dutyRecordSearch.do?method=manageByStudentDateForm&ids="+getIds()+"&beginDate="+document.all.beginDate.value+"&endDate="+document.all.endDate.value;
	 	}
    }
 	
 	function resetDiv(){document.getElementById("maintainDiv").style.display="none";document.all.beginDate.value="";document.all.endDate.value="";}
 
 	function maintain(){
 		if(getIds()==""){
 			alert("请选择学生!");
 		}else{
 			document.getElementById("maintainDiv").style.display="block";
 		}
 	}
 
 	var bar = new ToolBar('searchTopBar','学生考勤记录维护',null,true,true);
	bar.setMessage('<@getMessage/>');
	bar.addItem("选中学生时段考勤维护","maintain();");
	bar.addItem("搜索栏","MM_changeSearchBarStyle('searchBar');","search.gif");
	
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