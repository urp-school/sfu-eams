<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="electRecordBar"></table>
 <table class="frameTable_title" width="100%" >
  <tr>
   <td>详细查询</td>
   <td>|</td>
  <form name="courseTakeForm" action="courseTakeSearch.do?method=index" target="takeListFrame" method="post" onsubmit="return false;">
  <input type="hidden" name="courseTake.task.calendar.id" value="${calendar.id}"/>
  <#include "/pages/course/calendar.ftl"/>
  </tr>
 </table>
  <table width="100%" colspacing="0" class="frameTable" height="85%">
    <tr>
     <td valign="top" style="width:160px" class="frameTable_view">
     <#include "searchForm.ftl"/>
     </form>
     </td>
     <td valign="top">
     <iframe src="#"
     id="takeListFrame" name="takeListFrame"
     marginwidth="0" marginheight="0" scrolling="no"
     frameborder="0" height="100%" width="100%"></iframe>
     </td>
    </tr>
  <table>
 <script>
  var bar =new ToolBar("electRecordBar","上课名单查询",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addHelp("<@msg.message key="action.help"/>");
  function search(pageNo,pageSize,orderBy){
   		var courseTakeForm =document.courseTakeForm;
   		courseTakeForm.action="courseTakeSearch.do?method=search";
        goToPage(courseTakeForm,pageNo,pageSize,orderBy);
  }
  search();
 </script>
 </body>
<#include "/templates/foot.ftl"/> 
  