<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="bar"></table>
  <script>
    var bar = new ToolBar("bar","计划审核标准",null,true,true);
    bar.addItem("<@msg.message key="action.add"/>","add()");
    bar.addItem("<@msg.message key="action.edit"/>","edit()");
    bar.addItem("<@msg.message key="action.delete"/>","remove()");
  </script>
     <table width="90%" align="center" class="listTable">
       <form name="listForm" action="" onsubmit="return false;">
	   <tr align="center" class="darkColumn">
	     <td align="center" width="5%"><input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('auditStandardId'),event);"></td>
	     <td width="12%"><@bean.message key="attr.name"/></td>	     
	     <td width="10%"><@bean.message key="entity.studentType"/></td>
	     <td><@bean.message key="attr.graduate.disauditCourseType"/></td>
	     <td>多出学分可冲抵任意选修课的课程类别</td>
	   </tr>	   
	   <#list (result.auditStandardList.items)?if_exists as auditStandard>
	   <#if auditStandard_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if auditStandard_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
	    <td align="center"><input type="checkBox" name="auditStandardId" value="${auditStandard.id}"></td>
	    <td>&nbsp;${auditStandard.name}</td>
	    <td>&nbsp;<@i18nName auditStandard.studentType?if_exists/></td>
	    <#assign courseTypeDescriptionsValue = "" />
	    <#assign courseTypeIdValue = "," />
	    <#list auditStandard.disauditCourseTypeSet?if_exists as courseType>	   
	   		<#if courseType_has_next>
			    <#assign courseTypeIdValue = courseTypeIdValue + courseType.id?string + ","/>
			    <#if courseType.name?exists>
			    <#assign courseTypeDescriptionsValue = courseTypeDescriptionsValue + courseType.name?string + "," />
		    	</#if>
		   	<#else>
			   	<#assign courseTypeIdValue = courseTypeIdValue + courseType.id?string + ","/>
			    <#if courseType.name?exists>
			    <#assign courseTypeDescriptionsValue = courseTypeDescriptionsValue + courseType.name?string />
			    </#if>
	   		</#if>
	   </#list>
	   <td>&nbsp;${courseTypeDescriptionsValue}</td>
	   <#assign courseTypeDescriptionsValue = "" />
	    <#assign courseTypeIdValue = "," />
	    <#list auditStandard.convertableCourseTypeSet?if_exists as courseType>	   
	   		<#if courseType_has_next>
			    <#assign courseTypeIdValue = courseTypeIdValue + courseType.id?string + ","/>
			    <#if courseType.name?exists>
			    <#assign courseTypeDescriptionsValue = courseTypeDescriptionsValue + courseType.name?string + "," />
		    	</#if>
		   	<#else>
			   	<#assign courseTypeIdValue = courseTypeIdValue + courseType.id?string + ","/>
			    <#if courseType.name?exists>
			    <#assign courseTypeDescriptionsValue = courseTypeDescriptionsValue + courseType.name?string />
			    </#if>
	   		</#if>
	   </#list>
	   <td>&nbsp;${courseTypeDescriptionsValue}</td>
	   </tr>
	   </#list>
	   </form>
	   <#assign paginationName="auditStandardList" />
	   <#include "/templates/pageBar.ftl"/>
     </table>  
     <form name="pageGoForm" method="post" action="auditStandardManager.do">
     <input type="hidden" name="pageNo" value="1" />
     </form>
  <form name="resetForm" method="post" action="auditStandardManager.do"> 
    <input type="hidden" name="method" value="manager" />
    <input type="hidden" name="pageNo" value="1" />
  </form>
 </body>
 <script>
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("auditStandardId")));
    }
    function search(){
       document.pageGoForm.submit();
    }
    function add(){
       self.location='auditStandardOperation.do?method=addForm';
    }
    function edit(){
      gotoWithSingleParam("auditStandardOperation.do?method=updateForm","auditStandardId");
    }
    function remove(){
    	var ids = getSelectIds("auditStandardId");
    	if (ids == null || ids == "") {
    		alert("请至少选择一条要删除的记录！");
    		return;
    	}
      confirmWithParam("auditStandardOperation.do?method=delete","auditStandardIds");
    }
    function pageGo(pageNo){
       document.pageGoForm.pageNo.value=pageNo;
       search();      
    }
    function pageGoWithSize(pageNo,pageSize){
       var form = document.pageGoForm;
       form.action="auditStandardManager.do?method=manager";
       form.pageNo.value = pageNo;
       form.action+="&pageSize="+pageSize;
       form.submit();
    }
 </script>
<#include "/templates/foot.ftl"/>