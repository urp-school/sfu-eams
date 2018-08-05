<#include "/templates/head.ftl"/>
 <body>
 <table id="teachProjectTemplate"></table>
 <@table.table width="100%" sortable="true" id="sortTable">
   <@table.thead>
          <@table.selectAllTd id="teachProjectTemplateId"/>
          <td width="30%">文档名称</td>
          <td width="70%">文档描述</td>
   </@>
   <@table.tbody datas=teachProjectTemplates;teachProjectTemplate>
      <@table.selectTd id="teachProjectTemplateId" value=teachProjectTemplate.id/>
      <td><a href='teachProjectTemplate.do?method=download&teachProjectTemplateId=${(teachProjectTemplate.id)?if_exists}'>${(teachProjectTemplate.fileName)?if_exists}</a></td>
	  <td>${(teachProjectTemplate.describe)?if_exists}</td>
   </@>
 </@>
  <@htm.actionForm name="actionForm" action="teachProjectTemplate.do" entity="teachProjectTemplate">
  <input type="hidden" value="${teachProjectTypeId}" name="teachProjectTypeId"/>
  </@>
 <script>
    var bar = new ToolBar('teachProjectTemplate','&nbsp;项目模板列表',null,true,true);
    var form =document.actionForm;
    bar.setMessage('<@getMessage/>');
    bar.addItem("<@bean.message key="action.add"/>","add()");
    bar.addItem("<@bean.message key="action.modify"/>","edit()");
    bar.addItem("<@bean.message key="action.delete"/>","remove()");
    bar.addBack();
 </script>

 </body>
 <#include "/templates/foot.ftl"/>