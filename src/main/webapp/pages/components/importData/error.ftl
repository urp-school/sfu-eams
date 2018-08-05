<#include "/templates/head.ftl"/>
<body>
<table class="infoTable" align="center" width="100%">
   <tr><td colspan="2" align="center">导入结果</td></tr>
   <tr><td class="title">成功：</td><td>${importer.success}</td></tr>
   <#if (importer.fail>0)>
   <tr><td class="title">失败：</td><td>${importer.fail}</td></tr>
   </#if>
</table>
<table id="myBar"></table>
   <@table.table id="listTable" width="100%">
     <@table.thead>
       <@table.td text="错误序号" width="10%"/>
       <@table.td text="行号" width="10%"/>
       <@table.td text="错误内容" width="40%"/>
       <@table.td text="错误值"/>
     </@>
     <@table.tbody datas=importResult.errs;message,message_index>
       <td>${message_index+1}</td>
       <td>${message.index}</td>
       <td><#if message.message?starts_with("error")><@msg.message key=message.message/><#else>${message.message}</#if></td>
       <td><#list message.values as value>${value?default("")}</#list></td>
     </@>
     <tr>
       <td colspan="4" align="center">请将所有的输入数据(包括日期,数字等)在导入文件中以文字格式存放。</td>
     </tr>
   </@>
<script>
   var bar = new ToolBar("myBar","导入数据格式有误,错误${importResult.errs?size}个",null,true,true);
   bar.addPrint("<@msg.message key="action.print"/>");
   bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
</script>
</body>
<#include "/templates/foot.ftl"/>