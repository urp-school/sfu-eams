<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
 <@getMessage/>
  <@table.table width="100%" align="left" id="roleListTable" sortable="true">
 	   <@table.thead>
	     <@table.selectAllTd id="roleId"/>
	     <@table.sortTd id="role.name" width="15%" name="attr.name"/>
   	     <@table.sortTd width="15%" id="role.creator.name" name="attr.creator"/>
  	     <@table.sortTd width="15%" id="role.modifyAt" name="attr.dateLastModified"/>
  	     <@table.sortTd width="15%" id="role.remark" text="适用身份"/>
  	     <@table.sortTd width="15%" id="role.enabled" name="attr.status"/>
	   </@>
	   <@table.tbody datas=roles;role>
        <@table.selectTd type="checkbox" id="roleId" value="${role.id}"/>
	    <td ><A href="role.do?method=info&roleId=${role.id}" >${role.name} </a></td>
        <td >${role.creator.name}</td>        
        <td >${role.modifyAt?string("yyyy-MM-dd")}</td>
        <td ><#if role.category?exists>${role.category.name}</#if></td>
        <td ><#if role.enabled><@msg.message key="action.activate" /><#else><font color="red"><@msg.message key="action.freeze"/></font></#if></td>
	   </@>
    </@>
 </body>
<#include "/templates/foot.ftl"/>