<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
    <@getMessage/>
	<@table.table width="100%" sortable="true" id="listTable">
       <@table.thead>
         <@table.selectAllTd id="baseCodeId"/>
         <@table.sortTd name="attr.code" id="baseCode.code"/>
         <@table.sortTd  name="attr.infoname" id="baseCode.name" />
         <@table.sortTd  name="attr.engName" id="baseCode.engName"/>
	     <@table.sortTd text="必修课" id="baseCode.isCompulsory"/>
	     <@table.sortTd text="学位课" id="baseCode.isDegree"/>
	     <@table.sortTd text="模块课" id="baseCode.isModuleType"/>
	     <@table.sortTd text="实践课" id="baseCode.isPractice"/>
	     <@table.sortTd text="显示优先级" id="baseCode.priority"/>
	     <@table.sortTd  name="attr.modifyAt" id="baseCode.modifyAt"/>
	     <@table.sortTd  name="attr.state" id="baseCode.state"/>
	   </@>
	   <@table.tbody datas=baseCodes;baseCode>
         <@table.selectTd id="baseCodeId" value=baseCode.id/>
	    <td>${baseCode.code?if_exists}</td>
	    <td>${baseCode.name?if_exists}</td>
	    <td>${baseCode.engName?if_exists}</td>
	    <td>${baseCode.isCompulsory?string("是","否")}</td>
	    <td>${baseCode.isDegree?string("是","否")}</td>
	    <td>${baseCode.isModuleType?string("是","否")}</td>
	    <td>${baseCode.isPractice?string("是","否")}</td>
	    <td>${baseCode.priority?default("")}</td>
	    <td>${(baseCode.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>
	    <td>
	       <#if baseCode.state?if_exists == true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if>
	    </td>

	   </@>
	  </@>
  </body>
<#include "/templates/foot.ftl"/>