<#include "/templates/head.ftl"/>
<body>
 <table id="patternParamBar"></table>
<@table.table id="listTable" width="100%" sortable="true">
  <@table.thead>
      <@table.selectAllTd id="patternParamId"/>   
      <@table.sortTd  width="10%" id="patternParam.name" text="名称" />
      <@table.sortTd  width="10%" id="patternParam.description" text="描述" />
      <@table.sortTd  width="10%" id="patternParam.multiValue" text="是否多值" />
      <@table.sortTd  width="10%" id="patternParam.type" text="类型" />
      <@table.sortTd  width="10%" id="patternParam.editor.source" text="引用类型" />
      <@table.sortTd  width="10%" id="patternParam.editor.idProperty" text="值属性" />
      <@table.sortTd  width="10%" id="patternParam.editor.properties" text="显示属性" />
    </@>
    <@table.tbody datas=patternParams;patternParam>
     <@table.selectTd id="patternParamId" value=patternParam.id/>
         <input type="hidden" name="${patternParam.id}" id="${patternParam.id}" />
     </td>
     <td>${(patternParam.name)?if_exists}</td>
     <td>${patternParam.description?default("")}</td>
     <td>${(patternParam.multiValue)?string("是","否")}</td>
     <td>${(patternParam.type)?if_exists}</td>
     <td>${(patternParam.editor.source)?if_exists}</td>
     <td>${(patternParam.editor.idProperty)?if_exists}</td>
     <td>${(patternParam.editor.properties)?if_exists}</td>
    </@>
  </@>
  </body>
 <@htm.actionForm name="patternParamForm" entity="patternParam" action="patternParam.do"/>
  <script>
   var bar = new ToolBar('patternParamBar','<@msg.message key="info.module.list"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message "action.new"/>","add()");
   bar.addItem("<@msg.message "action.edit"/>","edit()");
   bar.addItem("<@msg.message "action.delete"/>","remove()");
  </script>
  </body>
 <#include "/templates/foot.ftl"/>