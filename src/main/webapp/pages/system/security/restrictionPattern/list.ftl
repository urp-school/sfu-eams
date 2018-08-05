<#include "/templates/head.ftl"/>
<body>
 <table id="restrictionPatternBar"></table>
<@table.table id="listTable" width="100%" sortable="true">
  <@table.thead>
      <@table.selectAllTd id="restrictionPatternId"/>   
      <@table.sortTd  width="10%" id="restrictionPattern.name" text="名称" />
      <@table.sortTd  width="10%" id="restrictionPattern.description" text="描述" />
    </@>
    <@table.tbody datas=restrictionPatterns;restrictionPattern>
     <@table.selectTd id="restrictionPatternId" value=restrictionPattern.id/>
         <input type="hidden" name="${restrictionPattern.id}" id="${restrictionPattern.id}" />
     </td>
     <td><a href="restrictionPattern.do?method=info&restrictionPattern.id=${restrictionPattern.id}">${(restrictionPattern.name)?if_exists}</a></td>
     <td>&nbsp;${restrictionPattern.description?default("")}</td>
    </@>
  </@>
  </body>
 <@htm.actionForm name="restrictionPatternForm" entity="restrictionPattern" action="restrictionPattern.do"/>
  <script>
   var bar = new ToolBar('restrictionPatternBar','<@msg.message key="info.module.list"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message "action.add"/>","add()");
   bar.addItem("<@msg.message "action.edit"/>","edit()");
   bar.addItem("<@msg.message "action.export"/>","exportData()");
  </script>
  </body>
 <#include "/templates/foot.ftl"/>