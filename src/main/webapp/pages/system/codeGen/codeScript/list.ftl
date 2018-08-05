<#include "/templates/head.ftl"/>
<BODY >
  <@getMessage/>
  <@table.table width="100%"  id="listTable" sortable="true">
    <@table.thead>
       <@table.td text=""/>
       <@table.sortTd text="编码对象" id="codeScript.codeName"/>
       <@table.sortTd text="编码属性" id="codeScript.attr"/>
       <@table.sortTd text="规则描述" width="30%" id="codeScript.description"/>
       <@table.sortTd text="创建时间" id="codeScript.createAt"/>
       <@table.sortTd text="修改时间" id="codeScript.modifyAt"/>
    </@>
    <@table.tbody datas=codeScripts;codeScript>
       <@table.selectTd id="codeScriptId" value="${codeScript.id}" type="radio"/>
       <td><A href="codeScript.do?method=info&codeScriptId=${codeScript.id}">${codeScript.codeName}</A></td>
       <td>${codeScript.attr}</td>
       <td>${codeScript.description?if_exists}</td>
       <td>${(codeScript.createAt?string("yyyy-MM-dd"))?if_exists}</td>
       <td>${(codeScript.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>  
    </@>
   </@>
  </body>
<#include "/templates/foot.ftl"/>