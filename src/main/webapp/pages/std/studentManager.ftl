<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body leftmargin="0" topmargin="0">
<#assign tableWidth="90%" />
<#if RequestParameters['moduleName']?exists>
	<#assign moduleName=RequestParameters['moduleName']>
<#else>
	<#assign moduleName="StudentManager">
</#if>
<#assign formName="pageGoForm" />
<#assign formAction="studentManager.do" />
<#assign tableTitle="学籍查询项" />
<#assign isCalendarNeed=false />
<#assign stateStatusShow=true />
<#assign showInput></#assign>
<#assign hiddenInput></#assign>
<#assign resetFormHiddenInput></#assign>
<#assign method=RequestParameters.method />
<table cellpadding="0" cellspacing="0" border="0" width="100%" >
<table id="topBar" width="90%" align="center"></table>
<#assign showInput>
	<tr>
		<td class="grayStyle">&nbsp;培养方式：</td>
		<td><@htm.i18nSelect datas=educationModes selected=RequestParameters["student.studentStatusInfo.educationMode.id"]?default("") name="student.studentStatusInfo.educationMode.id"><option value="">...</option></@></td>
		<td class="grayStyle"></td>
		<td></td>
	</tr>
</#assign>
	<table cellpadding="0" cellspacing="0" width="100%" border="0">
	<tr><td>
		<#include "/pages/selector/stdSearchForm.ftl"/>
	</td></tr>
	<#if studentList?exists&&RequestParameters['searchFalg']?default('')=="search" >
	<script>MM_changeSearchBarStyle('searchBar');</script>
	<tr><td>
 		<table width="90%" align="center" class="listTable">
			<form name="listForm" onSubmit="return false;">
				<#include "stdList.ftl"/>
			</form>
			<#include "/templates/newPageBar.ftl"/>
		</table>
	</td></tr>
	</#if>
</table>  
<form name="actionForm" action="" method="post" onsubmit="return false;">
	<@searchParams/>
</form>
</body>
<script>
  	var bar=new ToolBar('topBar','学籍信息维护',null,true,true);
  	bar.setMessage('<@getMessage/>');
  	bar.addItem("搜索栏", "MM_changeSearchBarStyle('searchBar')", "search.gif");
  	bar.addItem("新增学籍信息", "Go('studentOperation.do?method=loadAddForm')", 'new.gif');
  	bar.addItem("修改学籍信息", "gotoWithSingleParam('studentOperation.do?method=loadUpdateForm', 'stdId')", 'update.gif');
  	<#--暂且不开放
  	bar.addItem("删除学生", "remove()");
  	bar.addItem("修改学号", "editCode()");
  	-->
  	bar.addItem("预览学籍表", "printReview()");
  	var extendMenu=bar.addMenu("导入导出",null,'list.gif');
  	extendMenu.addItem("上传学籍数据","importData()",'action.gif');
  	extendMenu.addItem("下载模板","downloadTemplate()",'download.gif');
  	extendMenu.addItem("导出学籍数据","exportData()",'excel.png');
  	
    function pageGo(pageNo){
       document.pageGoForm.pageNo.value=pageNo;
       search();
    }
    
    function pageGoWithSize(pageNo,pageSize){
       var form = document.pageGoForm;
       form.pageNo.value = pageNo;
       form.action = "studentManager.do?pageSize="+pageSize;
       form.target = "";
       form.submit();
    }
    function getIds(){
      return getSelectIds("stdId");
    }
	function importData(){
		popupCommonWindow('studentFile.do?method=importForm&templateDocumentId=1&importTitle=Student Infomation Import','studentFileImportWin');
    }
    
    var totalSize = ${totalSize?default(-1)};
 	function exportData(){
 	    if (totalSize >= 0) {
	 	    var form = document.pageGoForm;
	 	    form.target = "";
	 	    form.action ="searchStudent.do?method=export";
	 	    var ids = getSelectIds("stdId");
	 	    if (ids == "") {
	 	    	if(totalSize > 10000){
	 	          	alert("系统不允许导出超过一万条的数据！");
	 	          	return;
	 	        }
	 	    	if (!confirm("你要全部导出所查询的" + (totalSize < 0 ? "" : totalSize + "个") + "学生记录吗？")) {
	 	    		return;
	 	    	}
	 	    } else {
	    		alert("要导出你选择的学生记录。");
	 	    }
	 	    addInput(form, "ids", ids);
	 	    form.submit();
 	    } else {
 	    	alert("请首先查询后，再行导出");
 	    }
    }
    
    function downloadTemplate(){
    	location.href = "./dataTemplate.do?method=download&document.id=1";
    }
    
    function printReview() {
    	var stdIds = getIds();
    	if (stdIds == "") {
    		alert("请至少选择一个学生！");
    		return;
    	}
    	var form = document.actionForm;
    	addInput(form, "stdIds", stdIds, "hidden");
    	form.action = "studentManager.do?method=printReview";
    	form.target = "_blank";
    	form.submit();
    }
</script> 
<#include "/templates/foot.ftl"/>