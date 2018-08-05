<#include "/templates/head.ftl"/>
 <body>
 <table id="teachProjectTemplate"></table>
 <@table.table width="100%" sortable="true" id="sortTable">
   <@table.thead>
          <@table.selectAllTd id="teachProjectTemplateId"/>
          <td width="20%">项目类别</td>
          <td width="20%">文档名称</td>
          <td width="60%">文档描述</td>
   </@>
   <@table.tbody datas=teachProjectTemplates;teachProjectTemplate>
      <@table.selectTd id="teachProjectTemplateId" value=teachProjectTemplate.id/>
      <td>${(teachProjectTemplate.teachProjectType.name)?if_exists}</td>
      <td><a href='teachProjectApply.do?method=download&teachProjectTemplateId=${(teachProjectTemplate.id)?if_exists}'>${(teachProjectTemplate.fileName)?if_exists}</a></td>
	  <td>${(teachProjectTemplate.describe)?if_exists}</td>
   </@>
 </@>
 <script>
    var bar = new ToolBar('teachProjectTemplate','&nbsp;项目模板列表',null,true,true);
    bar.addBack();
 </script>

 </body>
 <#include "/templates/foot.ftl"/>