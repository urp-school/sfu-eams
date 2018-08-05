<#include "/templates/head.ftl"/>
 <body>
 <table id="stdUserBar"></table>
   <table class="frameTable">
   <tr valign="top">
    <td width="20%" class="frameTable_view"><#include "searchForm.ftl"/></td>
    <td>
    <iframe  src="#" id="contentFrame" name="contentFrame" 
      marginwidth="0" marginheight="0"
      scrolling="no"  frameborder="0"  height="100%" width="100%"></iframe>
    </td>
   </tr>
  </table>
 </body>
  <script>
   var form = document.stdSearchForm;
   var action="assistant.do";
   function search(){
      form.submit();
   }
   search();
   var bar = new ToolBar('stdUserBar','&nbsp;学生助教管理',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@bean.message key="action.help"/>");
 </script>
<#include "/templates/foot.ftl"/>