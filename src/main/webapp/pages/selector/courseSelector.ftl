<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/Selector.js"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0"  >
     <table width="100%" align="center" class="listTable">
       <form name="listForm" ation="" onsubmit="return false;">
	   <tr align="center" class="darkColumn">
	     <td align="center">
	     <#if RequestParameters['multi']?exists && RequestParameters['multi']=='false'>
	      <#assign checkType="radio"/>
	     <#else>
	      <#assign checkType="checkbox"/>
	      <input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('courseId'),event)">
	     </#if>
         </td>
	     <td><@bean.message key="attr.courseNo"/></td>
	     <td><@bean.message key="attr.name"/></td>	     
	     <td><@bean.message key="attr.grade"/></td>
	     <td><@bean.message key="attr.creditHour"/></td>
	     <td>周课时</td>
	     <td><@bean.message key="entity.studentType"/></td>
	     <td><@bean.message key="entity.department"/></td>
	   </tr>
	   <#list courseList?if_exists as course>
	   <#if course_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if course_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}" onclick="onRowChange(event)">
	    <td width="3%" align="center" bgcolor="#E3EBFF">
	     <input type="${checkType}" name="courseId" value="${course.id}">
	    </td>
	    <td width="10%">&nbsp;${course.code}</td>
	    <td width="30%">&nbsp;<@i18nName course/></td>
	    <td width="6%" align="center">${course.credits}</td>
	    <td width="6%" align="center">${course.extInfo.period}</td>
	    <td width="8%" align="center">${course.weekHour?if_exists}</td>
	    <script>
	       detailArray['${course.id}'] = {'name':'<@i18nName course/>','credit':'${course.credits}','creditHour':'${course.extInfo.period}','weekHour':'${course.weekHour?if_exists}','departId':'${(course.extInfo.department.id)?if_exists}','departName':'${(course.extInfo.department.name)?if_exists}'};
	    </script>
	    <td width="10%" align="center"><@i18nName course.stdType/></td>
	    <td width="15%" align="center"><@i18nName (course.extInfo.department)?if_exists/></td>
	   </tr>
	   </#list>
	   </form>
	   <#include "/templates/newPageBar.ftl"/>	       
     </table>
     
    <table width="100%" align="center" class="listTable" onkeydown="quickSearch()">
	  <form name="searchForm" method="post" action="courseSelector.do?method=search" onsubmit="return false;">
	   <tr class="grayStyle">
	   <td>
	     <input type="button" onClick="parent.select(getIds('courseId'),getNames(getIds('courseId')),getValuesOf('credit',getIds('courseId')),getValuesOf('creditHour',getIds('courseId')),getValuesOf('weekHour',getIds('courseId')),getValuesOf('departId',getIds('courseId')),getValuesOf('departName',getIds('courseId')))"
	    value="<@bean.message key="action.confirm"/>" class="buttonStyle"/>
	   </td>
	   <td>
	   <#if RequestParameters['course.stdType.id']?exists>
		   <#assign defaultStdTypeId>${RequestParameters['course.stdType.id']}</#assign>
	   <#else>
		   <#assign defaultStdTypeId>"0"</#assign>
	   </#if>
	   <select name="course.stdType.id">
	       <option value=""></option>
	       <#list stdTypeList as stdType>
	       <option value="${stdType.id}" <#if stdType.id?string ==defaultStdTypeId >selected</#if> >
	          <@i18nName stdType/>
	       </option>
	       </#list>
	   </select>
	   </td>
	   <td>&nbsp;<@bean.message key="attr.id"/></td>
	   <td>
	      <input type="text" name="course.code" maxlength="32"value="${RequestParameters['course.code']?if_exists}" 
	      style="width:60px"/>
	   </td>
	   <td class="grayStyle">&nbsp;<@bean.message key="attr.name"/></td>
	   <td class="brightStyle">
	      <input type="text" name="course.name" maxlength="20" style="width:100px" value="${RequestParameters['course.name']?if_exists}"/>
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