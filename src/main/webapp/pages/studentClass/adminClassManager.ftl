<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body leftmargin="0" topmargin="0">
<#assign tableWidth="90%" />
<#if RequestParameters['moduleName']?exists>
<#assign moduleName=RequestParameters['moduleName']>
<#else>
<#assign moduleName="AdminClassManager">
</#if>
<#assign formName="pageGoForm" />
<#assign formAction="adminClassManager.do" />
<#assign tableTitle="班级信息搜索项" />
<#assign isCalendarNeed=false />
<#assign showInput></#assign>
<#assign hiddenInput></#assign>
<#assign resetFormHiddenInput></#assign>
<#assign method=RequestParameters.method />
<table id="myBar"></table>
<script>
    var bar =new ToolBar("myBar","班级信息维护",null,true,true);
    bar.addItem("搜索栏","MM_changeSearchBarStyle('searchBar');","search.gif");
	var extendMenu1=bar.addMenu("打印名单","printClass();","print.gif");
	extendMenu1.addItem("导出名单","exportStd();","excel.png");
	//bar.addItem("自动分班","gotoWithParam('studentClassOperation.do?method=autoSplitClassForm','classId')");
	//bar.addItem("手功分班","gotoWithParam('studentClassOperation.do?method=splitClassForm','classId')",'download.gif');
	bar.addItem("维护班级学生","gotoWithSingleParam('studentClassOperation.do?method=setClassStudentForm','classId')");
	bar.addItem("计算班级人数",batchUpdateStdCount,'update.gif');
	
	var extendMenu=bar.addMenu("导入导出",null,'list.gif');
	extendMenu.addItem("导入学生名单","importClass();","excel.png");
	extendMenu.addItem("下载模板",downloadTemplate,'download.gif');
	extendMenu.addItem("导出学生列表","exportClass();","excel.png");

	function batchUpdateStdCount(){
	   var classIds = getIds();
	   if(classIds=="")
	     window.alert('请选择班级!');
	   else{
	       if(!confirm("系统将计算选定班级的实际在校人数和学籍有效人数，点击[确定]继续")){return;}
	       var form = document.pageGoForm;
	       form.action="adminClassManager.do?method=batchUpdateStdCount&adminClassIds=" +classIds;
	       if(form['pageNo']){
	       	form['pageNo'].value=getPageNo();
	       }else{
	       	addInput(form,'pageNo',getPageNo());		
	       }
	       if(form['pageSize']){
	       	form['pageSize'].value=getPageSize();
	       }else{addInput(form,'pageSize',getPageSize());}
	       setSearchParams(form,form,null);
	   	   form.submit();
	   }
	}
	//导出学生列表
	function exportClass(){
		if(getIds()!=""){
	 		location.href="./studentFile.do?method=exportAdminClassStudentExcel&ids="+getIds()+getInputParams(document.${formName},null);
	 	}else{
	 		alert("请选择!");
	 	}
	}
	//导出班级学生名单
  	function exportStd() {
  		 var ids =   getIds();
  		 if(ids==""){
	        window.alert('请选择班级!');return;
	     }
  		 var form = document.pageGoForm;
  		 form.action = "adminClassManager.do?method=export&isOnCompus=1";
  		 addInput(form, "ids", ids, "hidden");
  		 addInput(form, "template", "adminClassStudent.xls", "hidden");
  		 form.submit();
  	}
	function importClass(){
		popupCommonWindow("studentFile.do?method=importAdminClassStudentExcelForm", "studentFileImportWin", 450, 200);
	}
	function downloadTemplate(){
		location.href="./dataTemplate.do?method=download&document.id=2";
	}
</script>
	<table cellpadding="0" cellspacing="0" width="100%" border="0">
	<tr><td><#assign tableWidth="100%"/>
		<#include "/pages/selector/adminClassSearchForm.ftl"/>
	</td></tr>
	<#if adminClassList?exists&&RequestParameters['searchFalg']?default('')=="search" >
	<script>MM_changeSearchBarStyle('searchBar');</script>
   	<tr><td>
		<table width="100%" align="center" class="listTable">
       	<form name="listForm" action="" onSubmit="return false;">
	   	<tr align="center" class="darkColumn">
	     	<td align="center"><input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('ids'),event);"></td>
	     	<td width="25%"><@bean.message key="info.studentClassManager.className"/></td>
	     	<td width="10%"><@bean.message key="info.studentClassManager.classId"/></td>
	     	<td width="25%"><@bean.message key="entity.college"/></td>
	     	<td width="15%"><@bean.message key="entity.studentType"/></td>
	     	<td width="25%">计划/实际在校/学籍有效（人数）</td>
	   	</tr>	   
	   	<#list adminClassList?sort_by("code")?if_exists as adminClass>
	   	<#if adminClass_index%2==1 ><#assign class="grayStyle" ></#if>
	   	<#if adminClass_index%2==0 ><#assign class="brightStyle" ></#if>
	   	<tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
	    	<td align="center"><input type="checkBox" name="ids" value="${adminClass.id}" /></td>
	    	<td>
	     		<a href="studentClassOperation.do?method=detail&classId=${adminClass.id}">
	      		&nbsp;<@i18nName adminClass?if_exists/>
	     		</a>
	    	</td>
		    <td>&nbsp;${adminClass.code}</td>
		    <td>&nbsp;<@i18nName adminClass.department?if_exists/></td>
		    <td>&nbsp;<@i18nName adminClass.stdType?if_exists/></td>
		    <td>&nbsp;${(adminClass.planStdCount)?default(0)}/${(adminClass.actualStdCount)?default(0)}/${(adminClass.stdCount)?default(0)}</td>
	   	</tr>
	   	</#list>
	   	</form>
	   	<#assign paginationName="adminClassList" />
	   	<#include "/templates/newPageBar.ftl"/>
     	</table>
     	</#if>
	</td>
	</tr>
</table>  
</body>
<script>
	function batchPrintClass(){
 		if(confirm('')){
 			location.href="./adminClassManager.do?method=batchPrintClass"+getInputParams(document.${formName},null);
 		}
 	}
 	function printClass(){
 		if(getIds()!=""){
 			location.href="./adminClassManager.do?method=printClass&ids="+getIds()+getInputParams(document.${formName},null);
 		}else{
 			alert("请选择!");
 		}
 	}
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
       form.action+="?pageSize="+pageSize;
       form.submit();
    }
</script>
<#include "/templates/foot.ftl"/>