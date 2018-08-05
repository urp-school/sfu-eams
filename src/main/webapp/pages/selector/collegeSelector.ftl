<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/Selector.js"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" >
     <table width="100%" align="center" class="listTable">
       <form name="listForm" action="" onSubmit="return false;">
	   <tr align="center" class="darkColumn">
  	     <td align="center">
	     <#if RequestParameters['multi']?exists && RequestParameters['multi']=='false'>
	      <#assign checkType="radio"/>
	     <#else>
	     <#assign checkType="checkbox"/>
	      <input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('departmentId'),event);">
	     </#if>
         </td>
	     <td width="30%"><@bean.message key="attr.code"/></td>
	     <td width="60%"><@bean.message key="attr.collegeName"/></td>
	   </tr>
	   <#list result.departmentList.items?if_exists as depart>
	   <#if depart_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if depart_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}" onclick="onRowChange(event)">
	    <td width="5%" class="select">
	     <input type="${checkType}" name="departmentId" value="${depart.id}" maxlength="32"/>
	    </td>
	    <td>${depart.code}</td>
	    <td width="40%">&nbsp;<@i18nName depart/></td>
	   	<script>
	       detailArray['${depart.id}'] = {'name':'<@i18nName depart/>'};
	    </script>
	   </tr>
	   </#list>
	   </form>
	   <#assign paginationName="departmentList"/>
	   <#include "/templates/pageBar.ftl"/>	
     </table>
    <table width="100%" align="center" class="listTable" onkeydown="quickSearch()">
	  <form name="searchForm" method="post" action="collegeSelector.do?method=search" onsubmit="return false;">
	   <tr>
	   <td class="brightStyle">
	      <input type="button" onClick="parent.select(getIds('departmentId'),getNames(getIds('departmentId')))"
	      value="<@bean.message key="action.confirm"/>" class="buttonStyle"/></td>
	   <td  class="grayStyle">&nbsp;<@bean.message key="attr.id"/></td>
	   <td  class="brightStyle">
	      <input type="text" name="department.code" maxlength="32" value="${RequestParameters['department.code']?if_exists}" style="width:60px"/>
	   </td>
	   <td class="grayStyle">&nbsp;院系名称</td>
	   <td class="brightStyle">
	      <input type="text" name="department.name" maxlength="20" style="width:100px" value="${RequestParameters['department.name']?if_exists}"/>
	      <input type="button" onClick="search()" value="查询" name="button1" class="buttonStyle"/>
	      <input type="hidden" name="pageNo" value="${RequestParameters['pageNo']}"/>
          <input type="hidden" name="pageSize" value="${RequestParameters['pageSize']}"/>
          <input type="hidden" name="multi" value="${RequestParameters['multi']}"/>
	   </td>
	   </tr>
	  </form>    
     </table>  
  <script>
    function search(){
       document.searchForm.pageNo.value=1;
       document.searchForm.submit();
    }
    function quickSearch(){
       if (window.event.keyCode == 13){
         search();
       }
    }
    function pageGo(pageNo){
       document.searchForm.pageNo.value=pageNo;
       document.searchForm.submit();
    }
  </script>
 </body>
<#include "/templates/foot.ftl"/>