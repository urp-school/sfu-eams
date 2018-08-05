<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
    <@getMessage/>
	<@table.table width="100%" sortable="true" id="listTable">
       <@table.thead>
         <@table.selectAllTd id="baseCodeId"/>
         <@table.sortTd name="attr.code" id="baseCode.code"/>
         <@table.sortTd name="attr.infoname" id="baseCode.name" />
         <@table.sortTd name="attr.engName" id="baseCode.engName"/>
	     <@table.sortTd text="是否在校" id="baseCode.inSchool"/>
	     <@table.sortTd name="attr.modifyAt" id="baseCode.modifyAt"/>
	     <@table.sortTd name="attr.state" id="baseCode.state"/>
	   </@>
	   <@table.tbody datas=baseCodes;baseCode>
         <@table.selectTd id="baseCodeId" value=baseCode.id/>
	    <td>&nbsp;${baseCode.code?if_exists}</td>
	    <td>&nbsp;${baseCode.name?if_exists}</td>
	    <td>&nbsp;${baseCode.engName?if_exists}</td>
	    <td>&nbsp;${baseCode.inSchool?string("是","否")}</td>
	    <td>${(baseCode.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>
	    <td>
	       <#if baseCode.state?if_exists == true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if>
	    </td>
	   </@>
	  </@>
  </body>
<#include "/templates/foot.ftl"/>