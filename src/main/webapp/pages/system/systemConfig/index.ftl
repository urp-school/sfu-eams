<#include "/templates/head.ftl"/>
 <body>
 <table id="userBar" width="100%"></table>
 <table class="formTable"  width="90%" align="center">
 <form name="systemConfigForm" method="post" action="" onsubmit="return false;">
 <tr><td class="title">系统名称:</td><td>${systemConfig.systemName}</td></tr>
 <tr><td class="title">开发商:</td><td>${systemConfig.company}</td></tr> 
 <tr><td class="title">开发者:</td><td>${systemConfig.developers}</td></tr> 
 </table>
 <@table.table id="listTable" align="center" width="90%">
   <@table.thead>
     <@table.td text="参数名称" width="30%"/>
     <@table.td text="参数值" width="50%"/>
     <@table.td text="说明" width="20%"/>
   </@>
   <@table.tbody datas=systemConfigItems;param>
     <td style="text-align:left">${param.name}</td>
     <td><input name="configItem${param.id}.value" value="${param.value?default("")}" maxlength="300" style="width:100%"/></td>
     <td>${param.description?default("")}</td>
   </@>
 </@>
 </form>
<script>
   var bar = new ToolBar('userBar','&nbsp;系统参数',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message key="action.save"/>","save()","save.gif");
   
   function save(){
      var form = document.systemConfigForm;
      form.action="systemConfig.do?method=save";
      form.submit();
   }
</script>
<#include "/templates/foot.ftl"/>