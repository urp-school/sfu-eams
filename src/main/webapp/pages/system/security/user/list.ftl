<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<@getMessage/>
 <@table.table width="100%" id="listTable" sortable="true">
   <@table.thead>
     <@table.selectAllTd id="userId"/>
     <@table.sortTd width="10%" id="user.name" name="attr.loginName"/>
     <@table.sortTd width="10%" id="user.userName" name="attr.personName"/>
     <@table.sortTd width="30%" id="user.email" name="attr.email" />
   	 <@table.sortTd width="10%" id="user.creator.userName" name="attr.creator" />
  	 <@table.sortTd width="15%" id="user.modifyAt" name="attr.dateLastModified" />
   	 <@table.sortTd width="10%" id="user.state" name="attr.status" />
   </@>
   <@table.tbody datas=users;user>
     <@table.selectTd id="userId" value="${user.id}"/>
     <td><A href="user.do?method=info&user.id=${user.id}" >&nbsp;${user.name} </a></td>
     <td>${user.userName?default("")}</td>
     <td>${user.email}</td>
     <td>${(user.creator.userName)?if_exists}</td>
     <td>${user.modifyAt?string("yyyy-MM-dd")}</td>
     <td>
      <#if user.enabled ==true><@msg.message key="action.activate" /></#if>
      <#if user.enabled ==false><@msg.message key="action.freeze" /></#if>
     </td>
   </@>
 </@>
 </body>
<#include "/templates/foot.ftl"/>