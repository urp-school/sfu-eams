<#include "/templates/head.ftl"/>
<link href="${static_base}/css/tab.css" rel="stylesheet" type="text/css">
<BODY>
  <table  width="100%" id="myBar"></table>
  <table  class="frameTable_title">
      <tr>
       <td  style="width:50px" >
          <font color="blue"><@bean.message key="action.advancedQuery"/></font>
       </td>
       <td>|</td>
      <form name="searchForm" target="contentListFrame" method="post" action="taskAlterRequest.do?method=index" onsubmit="return false;">
      <input type="hidden" name="taskAlterRequest.task.calendar.id" value="${calendar.id}" />
      <#include "/pages/course/calendar.ftl"/>
     </tr>
  </table>

  <table width="100%"  class="frameTable"  height="85%">
    <tr>     
     <td valign="top"  style="width:160px" class="frameTable_view">
     <#include "searchTable.ftl"/>
     </td>
     </form>
     <td valign="top">
     <iframe  src="#"
     id="contentListFrame" name="contentListFrame" 
     marginwidth="0" marginheight="0" scrolling="no"
     frameborder="0"  height="100%" width="100%">
     </iframe>
     </td>
    </tr>
  <table>
 <script>
   var bar =new ToolBar("myBar","教学变更请求管理",null,true,true);
   bar.addHelp("<@msg.message key="action.help"/>");

   var form=document.searchForm;
   var action="taskAlterRequest.do";
   
   function search(pageNo,pageSize,orderBy){
	  form.action=action+"?method=search";
	  if(null==orderBy)
	    orderBy="taskAlterRequest.time desc";
	  goToPage(form,pageNo,pageSize,orderBy);
   }
   search();
 </script>
 </body>
<#include "/templates/foot.ftl"/> 
  