<#include "/templates/head.ftl"/>
 <body>
 <table id="stdUserBar"></table>
   <table class="frameTable">
   <tr>
    <form name="searchForm" method="post" target="contentFrame" action="" onsubmit="return false;">
    <td width="22%"  class="frameTable_view"><#include "searchTable.ftl"/></td>
    </form>
    <td valign="top">
    <iframe src="#" id="contentFrame" name="contentFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
    </td>
   </tr>
  </table>
 </body>
  <script>
   var form = document.searchForm;
   var action="courseArrangeSwitch.do";
   function search(pageNo,pageSize,orderBy){     
       form.action=action+"?method=search";
       goToPage(form,pageNo,pageSize,orderBy);
   }
   search();
   var bar = new ToolBar('stdUserBar','&nbsp;排课发布信息',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack("<@bean.message key="action.back"/>");       
 </script>
</html>