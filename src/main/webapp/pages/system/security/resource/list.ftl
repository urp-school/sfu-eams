<#include "/templates/head.ftl"/>
<body>
 <table id="resourceBar"></table>
<@table.table id="listTable" width="100%" sortable="true">
  <@table.thead>
      <@table.selectAllTd id="resourceId"/>   
      <@table.sortTd  width="10%" id="resource.title" text="标题" />
      <@table.sortTd  width="10%" id="resource.name" text="名称" />
      <@table.sortTd  width="10%" id="resource.description" text="描述" />
      <@table.sortTd  width="10%" id="resource.enabled" text="状态" />
    </@>
    <@table.tbody datas=resources;resource>
     <@table.selectTd id="resourceId" value=resource.id/>
         <input type="hidden" name="${resource.id}" id="${resource.id}" />
     </td>
     <td><a href="resource.do?method=info&resource.id=${resource.id}">${(resource.title)?if_exists}</a></td>
     <td>${(resource.name)?if_exists}</td>
     <td>&nbsp;${resource.description?default("")}</td>
     <td><#if resource.enabled><@msg.message key="action.activate" /><#else><font color="red"><@msg.message key="action.freeze"/></font></#if></td>
    </@>
  </@>
  </body>
 <@htm.actionForm name="resourceForm" entity="resource" action="resource.do"/>
  <script>
   function activate(enabled){
       addInput( document.resourceForm,"enabled",enabled);
       multiAction("activate","确定操作?");
   }
   function exportData(){
       addInput(form,"titles","标题,名称,描述,状态");
       addInput(form,"keys","title,name,description,enabled");
       exportList();
   }
   function preview(){
      window.open(action+"?method=preview");
   }
   var bar = new ToolBar('resourceBar','<@msg.message key="info.module.list"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message "action.freeze"/>","activate(0)");
   bar.addItem("<@msg.message "action.activate"/>","activate(1)");
   bar.addItem("<@msg.message "action.add"/>","add()");
   bar.addItem("<@msg.message "action.edit"/>","edit()");
   bar.addItem("<@msg.message "action.delete"/>","multiAction('remove')",'delete.gif');
   bar.addItem("<@msg.message "action.export"/>","exportData()");
  </script>
  </body>
 <#include "/templates/foot.ftl"/>