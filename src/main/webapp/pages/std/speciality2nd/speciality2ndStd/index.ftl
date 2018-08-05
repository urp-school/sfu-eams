<#include "/templates/head.ftl"/>
<body>
 <table id="stdUserBar"></table>
   <table class="frameTable">
   <tr>
    <form name="searchForm" method="post" target="contentFrame" action="" onsubmit="return false;">
    <#include "/pages/components/initAspectSelectData.ftl"/>
    <td width="22%"  class="frameTable_view"><#include "searchForm.ftl"/></td>
    </form>
    <td valign="top">
    <iframe src="#" id="contentFrame" name="contentFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
    </td>
   </tr>
  </table>
  <script>
   var bar = new ToolBar('stdUserBar','&nbsp;辅修专业学生信息',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@bean.message key="action.help"/>");
   
   var form = document.searchForm;
   var action="speciality2ndStd.do";
   function search(pageNo,pageSize,orderBy){
       form.action=action+"?method=search&orderBy=student.code asc";
       goToPage(form,pageNo,pageSize,orderBy);
   }
   search();
 </script>
</body>
<#include "/templates/foot.ftl"/>