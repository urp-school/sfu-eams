<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
    <@getMessage/>
	<@table.table width="100%" sortable="true" id="listTable">
       <@table.thead>
         <td></td>
         <@table.sortTd name="attr.code" id="press.code" width="10%"/>
         <@table.sortTd  name="attr.infoname" id="press.name" width="20%"/>
         <@table.sortTd  name="attr.engName" id="press.engName"  width="30%"/>
	     <@table.sortTd  name="entity.pressLevel" id="press.level"/>
	     <@table.sortTd  name="attr.modifyAt" id="press.modifyAt"/>
	     <@table.sortTd  name="attr.state" id="press.state"/>
	   </@>
	   <@table.tbody datas=presses;press>
         <@table.selectTd type="radio" id="pressId" value="${press.id?if_exists}"/>
	    <td>&nbsp;${press.code?if_exists}</td>
	    <td>&nbsp;${press.name?if_exists}</td>
	    <td>&nbsp;${press.engName?if_exists}</td>
	    <td>&nbsp;<@i18nName press.level?if_exists/></td>
	    <td>${(press.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>
	    <td>
	       <#if press.state?if_exists == true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if>
	    </td>
	   </@>
	  </@>
  </body>
<#include "/templates/foot.ftl"/>