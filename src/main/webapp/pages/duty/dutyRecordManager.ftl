<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
<#assign tableWidth="90%" />
<#if RequestParameters['moduleName']?exists>
<#assign moduleName=RequestParameters['moduleName']>
<#else>
<#assign moduleName="DutyRecordManager">
</#if>
<#assign formName="pageGoForm" />
<#assign formAction="dutyRecordManager.do" />
<#assign tableTitle="考勤查询项" />
<#assign isCalendarNeed=true />
<#assign showInput>
<tr>
 	<td class="grayStyle" id="f_courseTakeTypeString">
  		&nbsp;导出考勤数据的修读类别：
 	</td>
 	<td colSpan="3" class="brightStyle">
    <#if result.courseTakeTypeString?exists>
    <#assign courseTakeTypeString = result.courseTakeTypeString />
    <#elseif RequestParameters.courseTakeTypeString?exists>
    <#assign courseTakeTypeString = RequestParameters.courseTakeTypeString />
    <#else>
    <#assign courseTakeTypeString = "" />
    </#if>
  	&nbsp;<input type="checkBox" name="courseTakeTypeCheckbox" value="null" <#if (courseTakeTypeString=="")||(courseTakeTypeString?index_of(',null,')>=0)>checked</#if>/>退课
  	<input type="checkBox" name="courseTakeTypeCheckbox" value="1" <#if (courseTakeTypeString=="")||(courseTakeTypeString?index_of(',1,')>=0)>checked</#if>/>指定
   	<input type="checkBox" name="courseTakeTypeCheckbox" value="2" <#if (courseTakeTypeString=="")||(courseTakeTypeString?index_of(',2,')>=0)>checked</#if>/>选修
   	<input type="checkBox" name="courseTakeTypeCheckbox" value="3" <#if (courseTakeTypeString=="")||(courseTakeTypeString?index_of(',3,')>=0)>checked</#if>/>重修
   	<input type="checkBox" name="courseTakeTypeCheckbox" value="4" <#if (courseTakeTypeString=="")||(courseTakeTypeString?index_of(',4,')>=0)>checked</#if>/>免修不免试
 	</td>
</tr>
</#assign>
<#assign hiddenInput>
	<input type="hidden" name="teachTaskId" />
	<input type="hidden" name="courseTakeTypeString" />
</#assign>
<#assign resetFormHiddenInput></#assign>
<#assign functionSearch>
	function search(){
       	if (doValidate()) {
       		var form=document.${formName};
       		form.method.value="${RequestParameters.method}";
        	form.teachTaskId.value="";
        	form.courseTakeTypeString.value = ","+getCheckBoxValue(document.getElementsByName("courseTakeTypeCheckbox"))+",";
       		addAllParams(form);
          	form.submit();
        }
    }
</#assign>
<#assign method=RequestParameters.method />
<script>
	function getIds(){
       return(getCheckBoxValue(document.getElementsByName("ids")));
    }
    
    function exportDutyRecordExcel(){
    	if(getIds()==""){
    		alert("请选择教学任务！");
    		return;
    	}
    	location.href="./studentFile.do?method=exportDutyRecordExcel&ids="+getIds()+"&courseTakeTypeString=,"+getCheckBoxValue(document.getElementsByName("courseTakeTypeCheckbox"))+",";
    }
</script>
 
 <table cellpadding="0" cellspacing="0" border="0" width="100%" >
  
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <tr><td>
	<table id="topBar" width="90%" align="center"></table>
	</td></tr>
	<tr><td>
	<#include "/pages/duty/teachTaskSearchForm.ftl"/>
	</td></tr>
	<#if teachTaskList?exists&&RequestParameters['searchFalg']?default('')=="search" >
	<script>MM_changeSearchBarStyle('searchBar');</script>   
   <tr>
    <td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B><@bean.message key="page.dutyRecordManage.title"/><@bean.message key="filed.courseList"/></B>
    </td>
   </tr>
   <tr>
    <td>
     <table width="90%" align="center" class="listTable">
       <form name="listForm" action="" onSubmit="return false;">
	   <tr align="center" class="darkColumn">
	     <td align="center" width="25px;"><input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('ids'),event);"></td>
	     <td><@bean.message key="attr.taskNo"/></td>
	     <td><@bean.message key="attr.courseNo"/></td>
	     <td><@bean.message key="attr.courseName"/></td>
	     <td><@bean.message key="entity.courseType"/></td>
	     <td><@bean.message key="attr.year2year"/></td>
	     <td><@bean.message key="attr.term"/></td>
	     <td><@bean.message key="entity.teacher"/></td>
	     <td><@bean.message key="entity.teachClass"/></td>
	     <td><@bean.message key="action.modify"/></td>
	     <td><@bean.message key="action.maintain"/></td>	     
	   </tr>	   
	   <#list (teachTaskList?sort_by("seqNo"))?if_exists as teachTask>
	   <#if teachTask_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if teachTask_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
	    <td align="center"><input type="checkBox" name="ids" value="${teachTask.id}"></td>
	    <td>&nbsp;${ teachTask.seqNo?if_exists}</td>
	    <td>&nbsp;${ teachTask.course.code?if_exists}</td>
	    <td>&nbsp;<@i18nName teachTask.course?if_exists/></td>
	    <td>&nbsp;<@i18nName teachTask.courseType?if_exists/></td>
	    <td>&nbsp;${teachTask.calendar.year}</td>
	    <td>&nbsp;${teachTask.calendar.term}</td>
	    <td>&nbsp;<@getTeacherNames teachTask.arrangeInfo.teachers/><#--<@eraseComma teachTask.arrangeInfo.teacherNames?if_exists/>--></td>
	    <td>&nbsp;<@i18nName teachTask.teachClass?if_exists/></td>
	    <td align="center">
	     <a href="javascript:modify('${teachTask.id}')" >
	      &lt;&lt;
	     </a>
	    </td>
	    <td align="center">
	     <a href="javascript:maintain('${teachTask.id}')" >
	      &lt;&lt;
	     </a>
	    </td>	    
	   </tr>
	   </#list>
	   </form>
	   <#if teachTaskList?exists>
		    <#include "/templates/newPageBar.ftl"/>
	   </#if>
     </table>
     </#if>
    </td>
   </tr>
  </table>  
  <#include "/pages/duty/dutyRuleInf.ftl"/>
  

 </body>
 <script>
    var bar=new ToolBar('topBar','考勤记录维护',null,true,true);
	bar.setMessage('<@getMessage/>');
	bar.addItem("导出考勤数据",exportDutyRecordExcel,'excel.png');
	bar.addItem("搜索栏","MM_changeSearchBarStyle('searchBar');","search.gif");
	
    function pageGo(pageNo){
    	form.action ="${formAction?string}?method=${method?string}&pageSize="+pageSize;
       document.pageGoForm.pageNo.value=pageNo;
       document.pageGoForm.submit();
    }
    
    function pageGoWithSize(pageNo,pageSize){
       var form = document.pageGoForm;
       form.pageNo.value = pageNo;
       form.action ="${formAction?string}?method=${method?string}&pageSize="+pageSize;
       form.submit();
    }
    
    function submitResetForm(){
          var location = self.location.href;
          var action = location.split("/");
          document.resetForm.action = action[action.length-1];
          document.resetForm.submit();
    }
    
    function modify(teachTaskId){
        document.pageGoForm.method.value = "modifyRecordByTeachTask";
        document.pageGoForm.teachTaskId.value = teachTaskId;
        var location = self.location.href;
        var url = location.split("/");
        var action = url[url.length-1].split("?");
        document.pageGoForm.action = action[0];
        document.pageGoForm.target = "_blank";
        document.pageGoForm.submit();
    }
    
    function maintain(teachTaskId){
        document.pageGoForm.method.value = "maintainRecordByTeachTask";
        document.pageGoForm.teachTaskId.value = teachTaskId;
        var location = self.location.href;
        var url = location.split("/");
        var action = url[url.length-1].split("?");
        document.pageGoForm.action = action[0];
        document.pageGoForm.target = "_blank";
        document.pageGoForm.submit();
    }    
    
 </script>
<#include "/templates/foot.ftl"/>