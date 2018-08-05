<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
<#assign tableWidth="90%" />
<#if RequestParameters['moduleName']?exists>
<#assign moduleName=RequestParameters['moduleName']>
<#else>
<#assign moduleName="SearchStudent">
</#if>
<#assign formName="pageGoForm" />
<#assign formAction="searchStudent.do" />
<#assign tableTitle="学籍查询项" />
<#assign isCalendarNeed=false />
<#assign stateStatusShow=true />
<#assign showInput></#assign>
<#assign hiddenInput></#assign>
<#assign resetFormHiddenInput></#assign>
<#assign method=RequestParameters.method />
 
<#assign showInput>
	<tr>
		<td class="grayStyle">&nbsp;培养方式：</td>
		<td><@htm.i18nSelect datas=educationModes selected=RequestParameters["student.studentStatusInfo.educationMode.id"]?default("") name="student.studentStatusInfo.educationMode.id"><option value="">...</option></@></td>
		<td class="grayStyle">&nbsp;是否在校：</td>
		<td>
            <select id="stdInSchool" name="inSchool" style="width:100px">
                <option value="">...</option>
                <option value="1">是</option>
                <option value="0">否</option>
            </select>
        </td>
        <script>document.getElementById("stdInSchool").value = "${RequestParameters["inSchool"]?default("")}";</script>
	</tr>
</#assign>
 <table cellpadding="0" cellspacing="0" border="0" width="100%" >
   <tr>
    <td>
	<table id="topBar" width="90%" align="center"></table>
	</td>
   </tr>
    <tr>
    <td>
	<#include "/pages/selector/stdSearchForm.ftl"/>
	</td>
   </tr>
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <#if studentList?exists&&RequestParameters['searchFalg']?default('')=="search" >
   <script>MM_changeSearchBarStyle('searchBar');</script>
   <tr>
    <td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B><@bean.message key="std.stdList"/></B>
    </td>
   </tr>   
   <tr>
    <td>
     <table width="90%" align="center" class="listTable">
       <form name="listForm" onSubmit="return false;">
       <#assign checkboxNeed=false />
       <#include "stdList.ftl"/>
	   </form>
	   <#include "/templates/newPageBar.ftl"/>
     </table>
    </td>
   </tr>
   </#if>
  </table> 
 </body>
 <script>
	var bar=new ToolBar('topBar','学籍信息查询',null,true,true);
	bar.setMessage('<@getMessage/>');
	bar.addItem("搜索栏","MM_changeSearchBarStyle('searchBar');","search.gif");
  	bar.addItem("导出学籍数据","exportData()",'excel.png');
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
       form.action="searchStudent.do?pageSize="+pageSize+"&orderBy=student.code asc";
       form.submit();
    }
 	function exportData(){
 	    <#if totalSize?exists>
 	    var form = document.pageGoForm;
 	    form.target = "";
 	    form.action ="searchStudent.do?method=export";
 	    var ids = getSelectIds("stdId");
 	    if (ids == "") {
 	    	if(${totalSize}>10000){
 	          alert("导出数据超过一万，系统不允许导出");return;
 	        }
 	    	if (!confirm("你要全部导出所查询的${totalSize}个学生记录吗？")) {
 	    		return;
 	    	}
 	    } else {
    		alert("要导出你选择的学生记录。");
 	    }
 	    addInput(form, "ids", ids);
 	    form.submit();
 	    <#else>
 	    alert("请首先查询后，再行导出");
 	    </#if>
    }
 </script>
<#include "/templates/foot.ftl"/>