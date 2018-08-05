 <#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"]>
 <#macro sortTd id extra...>
   <td <#list extra?keys as attr>${attr}="${extra[attr]?html}"</#list> id="${id}" class="tableHeaderSort"><#if extra['name']?exists><@bean.message key="${extra['name']}"/><#else>${extra['text']}</#if></td>
 </#macro>
 <#macro td extra...>
   <td <#list extra?keys as attr>${attr}="${extra[attr]?html}"</#list>><#if extra['name']?exists><@bean.message key="${extra['name']}"/><#else>${extra['text']}</#if></td>
 </#macro>
 <#macro thead extra...>
   <tr align="center" class="darkColumn" <#if (extra?size!=0)><#list extra?keys as attr>${attr}="${extra[attr]?html}"</#list></#if>><#nested></tr>
 </#macro>
 <#macro  tr class extra...>
   <tr class="${class}"<#if (extra?size!=0)> <#list extra?keys as attr>${attr}="${extra[attr]?html}"</#list></#if> align="center" onmouseover="swapOverTR(this,this.className)"onmouseout="swapOutTR(this)" onclick="onRowChange(event)"><#nested></tr>
 </#macro>
 <#macro selectAllTd id extra...>
   <td class="select" <#if (extra?size!=0)><#list extra?keys as attr>${attr}="${extra[attr]?html}"</#list></#if>><input type="checkBox" id="${id}Box" class="box" onClick="toggleCheckBox(document.getElementsByName('${id}'),event);"></td>
 </#macro>
  <#macro selectTd id value extra...>
   <td class="select"><input class="box" name="${id}" value="${value}"<#if (extra?size!=0)><#list extra?keys as attr><#if attr != "type"> ${attr}="${extra[attr]}"</#if></#list></#if> type="${extra['type']?default("checkbox")}"><#nested></td>
 </#macro>
 
 <#macro table extra...>
   <#if !(tableClass?exists)><#assign tableClass="listTable"/></#if>
   <table class=${tableClass} <#if (extra?size!=0)><#list extra?keys as attr>${attr}="${extra[attr]?html}" </#list></#if>>
   <#nested>
   <#if thisPageSize?exists || extra['sortable']?exists>
	   <form  name="queryForm" action="" method="post" onsubmit="return false;">
	      <#list RequestParameters?keys as key>
	      <input type="hidden" name="${key}" value="${RequestParameters[key]}" />
	      </#list>
	   </form>
	   <script>
	   <#if extra['sortable']?exists>
	    <#assign sortTableId=extra['id']/>
	    <#assign headIndex=extra['headIndex']?default("0")/>
	    <#include "/templates/initSortTable.ftl"/>
		orderBy=function(what){
		    goToPage(queryForm,1,${pageSize?default("null")},what);
		}
	   </#if>
	   //addInput(queryForm,'params',"");
	   var queryStr=getInputParams(queryForm,null,false);
	   refreshQuery=function (){queryForm.submit();}
	   var orderByStr='${RequestParameters['orderBy']?default('')}';
	   <#if thisPageSize?exists>
	    function pageGoWithSize(pageNo,pageSize){
		  goToPage(queryForm,pageNo,pageSize,orderByStr);
	    }
	   </#if>
	   queryForm.action=self.location.protocol+'//'+self.location.host+self.location.pathname;
	   </script>
  </#if>
  </table>
</#macro>

 <#macro tbody datas extra...>
    <tbody>
    <#list datas as data>
	  <#if data_index%2==1 ><#assign class="grayStyle" ></#if>
	  <#if data_index%2==0 ><#assign class="brightStyle" ></#if>
	   <@tr class="${class}"><#nested data,data_index></@tr>
    </#list>
    <#if thisPageSize?exists>
        <#assign simplePageBar = extra['simplePageBar']?if_exists/>
        <#if simplePageBar?exists && simplePageBar>
            <#include "/templates/simplePageBar.ftl"/>
        <#else>
            <#include "/templates/newPageBar.ftl"/>
        </#if>
    </#if>
  </tbody>
</#macro>