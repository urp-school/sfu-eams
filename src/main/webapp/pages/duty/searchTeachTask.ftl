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
<#assign formAction="inputDutyRecord.do"/>
<#assign tableTitle="考勤查询项"/>
<#assign isCalendarNeed=true/>
<#assign showInput></#assign>
<#assign hiddenInput><input type="hidden" name="teachTaskId"/></#assign>
<#assign resetFormHiddenInput></#assign>
<#assign functionSearch>
	function search(){
       	if (doValidate()) {
       		var form=document.${formName};
       		form.method.value="${RequestParameters.method}";
        	form.teachTaskId.value="";
       		addAllParams(form);
          	form.submit();
        }
    }
</#assign>
<#assign method=RequestParameters.method/>
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <tr><td>
	<table id="topBar" width="90%" align="center"></table>
	</td></tr>
	<tr><td>
	<#include "/pages/duty/teachTaskSearchForm.ftl"/>
	</td></tr>
	<#if teachTaskList?exists&&RequestParameters['searchFalg']?default('')=="search">
	<script>MM_changeSearchBarStyle('searchBar');</script>
   <tr>
    <td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B><@bean.message key="page.dutyRecordInput.title"/><@bean.message key="filed.courseList"/></B>
    </td>
   </tr>
   <tr>
    <td>
     <table width="90%" align="center" class="listTable">
       <form name="listForm" action="" onsubmit="return false;">
	   <tr align="center" class="darkColumn">
	     <td width="10%"><@msg.message key="attr.taskNo"/></td>
	   	 <td width="10%"><@bean.message key="attr.courseNo"/></td>
	     <td width="20%"><@bean.message key="attr.courseName"/></td>
	     <td width="14%"><@bean.message key="entity.courseType"/></td>
	     <td width="10%"><@bean.message key="entity.teacher"/></td>
	     <td width="19%"><@bean.message key="entity.teachClass"/></td>
	     <td align="center"><@bean.message key="action.input"/></td>
	     <td width="10%" align="center">课表录入维护</td>
	   </tr>	   
	   <#list (teachTaskList?sort_by("seqNo"))?if_exists as teachTask>
	   <#if teachTask_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if teachTask_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
	    <td>&nbsp;${ teachTask.seqNo?if_exists}</td>
	    <td>&nbsp;${ teachTask.course.code?if_exists}</td>
	    <td>&nbsp;<@i18nName teachTask.course?if_exists/></td>
	    <td>&nbsp;<@i18nName teachTask.courseType?if_exists/></td>
	    <td>&nbsp;<@getTeacherNames teachTask.arrangeInfo.teachers/><#--<@eraseComma teachTask.arrangeInfo.teacherNames?if_exists/>--></td>
	    <td>&nbsp;<@i18nName teachTask.teachClass?if_exists/></td>
	    <td align="center">
	     <a href="javascript:inputForm('${teachTask.id}')" >
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
    var bar=new ToolBar('topBar','考勤记录录入',null,true,true);
	bar.setMessage('<@getMessage/>');
	bar.addItem("搜索栏","MM_changeSearchBarStyle('searchBar');","search.gif");
	
    function pageGo(pageNo){
       document.pageGoForm.pageNo.value=pageNo;
       document.pageGoForm.submit();
    }
    
    function pageGoWithSize(pageNo,pageSize){
       var form = document.pageGoForm;
       form.pageNo.value = pageNo;
       form.action+="?pageSize="+pageSize;
       form.submit();
    }
    
    function inputForm(teachTaskId){
        document.pageGoForm.method.value = "inputForm";
        document.pageGoForm.teachTaskId.value = teachTaskId;
        document.pageGoForm.target = "_blank";
        document.pageGoForm.submit();
    }
    
    function maintain(teachTaskId){
    	document.pageGoForm.method.value = "maintainRecordByTeachTaskForm";
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