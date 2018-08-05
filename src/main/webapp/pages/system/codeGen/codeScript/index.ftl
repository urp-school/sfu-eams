<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="myBar" width="100%"></table>
  <table class="frameTable">
   <tr valign="top">
    <td width="20%" class="frameTable_view">
        <#include "searchForm.ftl"/>
        <table><tr height="400px"><td></td></tr></table>
    </td>
    <td valign="top">
        <iframe  src="#" id="contentListFrame" name="contentListFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe> 
    </td>
   </tr>
  </table>
  <script language="javascript">
    var bar = new ToolBar("myBar","基础代码生成规则管理",null,true,true);
    bar.addItem("<@msg.message key="action.new"/>",add,'new.gif');
    bar.addItem("<@msg.message key="action.edit"/>","submitAction('edit')",'update.gif');
    bar.addItem("<@msg.message key="action.delete"/>","submitAction('remove')",'delete.gif');
    bar.addHelp("<@msg.message key="action.help"/>");
    
    var form = document.searchForm;
    action="codeScript.do";
    function searchCodeScript(pageNo,pageSize,orderBy){
       form.action=action+"?method=search";
       goToPage(form,pageNo,pageSize,orderBy);
    }
    function add(){
       form.action=action+"?method=edit";
       addParamsInput(form,contentListFrame.queryStr);
       form.submit();
    }
    function remove(){
       addParamsInput(form,contentListFrame.queryStr);
       submitId(form,"codeScriptId",false,action+"?method=remove");
    }
    function submitAction(method){
       addParamsInput(form,contentListFrame.queryStr);
       var ids =getRadioValue(contentListFrame.document.getElementsByName("codeScriptId"));
       if(""==ids || isMultiId(ids)){
          alert("请选择一个");
          return;
       }
       if(method=="remove"){
          if(!confirm("确认删除操作?")) return;
       }
       form.action=action+"?method="+method+"&codeScriptId="+ids;
       form.submit();
    }
    searchCodeScript();
  </script>  
  </body>
<#include "/templates/foot.ftl"/>