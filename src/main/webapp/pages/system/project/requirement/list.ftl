<#include "/templates/head.ftl"/>
<#include "code.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
  <@getMessage/>
  <@table.table width="100%" sortable="true" id="sortTable">
    <@table.thead>
      <@table.selectAllTd id="requireId"/>
      <td id="require.module" class="tableHeaderSort" width="10%">模块</td>
      <td id="require.content" class="tableHeaderSort" width="50%">内容</td>
      <td id="require.fromUser" class="tableHeaderSort" width="10%">建议人</td>
      <td id="require.priority" class="tableHeaderSort" width="5%">优先级</td>
      <td id="require.status" class="tableHeaderSort" width="10%">状态</td>
      <td id="require.planCompleteOn" class="tableHeaderSort" width="12%">计划时间</td>
    </@>
    <@table.tbody datas=requires;require>
     <@table.selectTd type="checkBox" id="requireId" value="${require.id?if_exists}"/>
     <td>${require.module}</td>
     <td>
       <a href="requirement.do?method=info&requirement.id=${require.id}">${require.content?if_exists}</a>
     </td>
     <td>${require.fromUser}</td>
     <td>${priorityMap[require.priority?string]}</td>
     <td>${statusMap[require.status?string]}</td>
     <td>${(require.planCompleteOn?string("yyyy-MM-dd"))?if_exists}</td>       
    </tr>
    </@>
  </@>
  </body>
<#include "/templates/foot.ftl"/>