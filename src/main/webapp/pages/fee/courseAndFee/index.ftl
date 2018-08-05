<#include "/templates/head.ftl"/>
<body>
 <table id="stdUserBar"></table>
 <table class="frameTable_title">
	  <tr>
	  <td>&nbsp;</td>
	  <form name="calendarForm" method="post" target="contentFrame" action="courseAndFee.do?method=index">
	  <#include "/pages/course/calendar.ftl"/>
	  </form>
	  </tr>
 </table>
   <table class="frameTable">
   <tr>
    <td width="20%" class="frameTable_view"><#include "searchForm.ftl"/></td>
    <td valign="top">
    <iframe src="#" id="contentFrame" name="contentFrame"
      marginwidth="0" marginheight="0"
      scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
    </td>
   </tr>
  </table>
  <script>
   var bar = new ToolBar('stdUserBar','&nbsp;选课与缴费',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
   
    function searchStd(){
       document.stdUserSearchForm.submit();
    }
    searchStd();
 </script>
</body>
<#include "/templates/foot.ftl"/>