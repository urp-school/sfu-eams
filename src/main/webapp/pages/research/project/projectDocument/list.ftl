<#include "/templates/head.ftl"/>
 <body>
 <table id="projectDocument"></table>
 <@table.table width="100%" sortable="true" id="sortTable">
   <@table.thead>
          <@table.selectAllTd id="projectDocumentId"/>
          <td width="30%">文档名称</td>
          <td width="70%">文档描述</td>
   </@>
   <@table.tbody datas=projectDocuments?if_exists;projectDocument>
      <@table.selectTd id="projectDocumentId" value=projectDocument.id/>
      <td><a href='projectDocument.do?method=download&projectDocumentId=${(projectDocument.id)?if_exists}'>${(projectDocument.fileName)?if_exists}</a></td>
	  <td>${(projectDocument.describe)?if_exists}</td>
   </@>
 </@>
  <@htm.actionForm name="actionForm" action="projectDocument.do" entity="projectDocument">
  <input type="hidden" value="${teachProjectId?if_exists}" name="teachProjectId"/>
  </@>
 <script>
    var bar = new ToolBar('projectDocument','&nbsp;项目文档列表',null,true,true);
    var form =document.actionForm;
    bar.setMessage('<@getMessage/>');
    bar.addItem("<@bean.message key="action.add"/>","add()");
    bar.addItem("<@bean.message key="action.modify"/>","edit()");
    bar.addItem("<@bean.message key="action.delete"/>","remove()");
    bar.addBack();
 </script>

 </body>
 <#include "/templates/foot.ftl"/>