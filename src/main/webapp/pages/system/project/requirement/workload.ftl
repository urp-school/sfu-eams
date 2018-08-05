<#include "/templates/head.ftl"/>
<body  LEFTMARGIN="0" TOPMARGIN="0">
<#assign labInfo>工作量信息</#assign>
<#include "/templates/back.ftl"/>
<#include "code.ftl"/>
  <table width="100%"  class="formTable">
    <tr>
      <td  id="f_module" class="darkColumn">选择需求个数:</td>
      <td>${requires?size}</td>
      <td  id="f_developers" class="darkColumn">总工作量<font color="red">*</font>:</td>
      <td>${workload?string("##.##")}</td>
    </tr>
  </table>
  <@table.table width="100%" id="listTable">
    <@table.thead>
      <td id="require.module"  width="10%">模块</td>
      <td id="require.content"  width="50%">内容</td>
      <td id="require.fromUser"  width="10%">建议人</td>
      <td id="require.priority"  width="8%">优先级</td>
      <td id="require.status"  width="10%">状态</td>
      <td id="require.planCompleteOn"  width="12%">工作量</td>
    </@>
    <@table.tbody datas=requires;require>
     <td>${require.module}</td>
     <td><a href="requirement.do?method=info&requirement.id=${require.id}">${require.content?if_exists}</a></td>
     <td>${require.fromUser}</td>
     <td>${priorityMap[require.priority?string]}</td>
     <td>${statusMap[require.status?string]}</td>
     <td><#if require.workload?exists>${require.workload?string("##.##")}</#if></td>       
    </@>
   </@>
  <script language="javascript" > 
     function reset(){
         document.requireForm.reset();
     }
     function save(form,params){
         var a_fields = {
         'require.module':{'l':'模块名称', 'r':true, 't':'f_module'},
         'require.content':{'l':'意见内容', 'r':true, 't':'f_content'},
         'require.developers':{'l':'负责人', 'r':true, 't':'f_developers'},
         'require.fromUser':{'l':'建议人', 'r':true, 't':'f_fromUser'}
     };
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
        form.action="requirement.do?method=save"
        form.submit();
     }
   }
 </script>
 </body> 
</html>