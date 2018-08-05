<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="myBar" width="100%"></table>
   <table  class="frameTable">
   <tr>
    <td style="width:160px" class="frameTable_view">
     <form name="searchForm" target="contentListFrame" method="post" action="" onsubmit="return false;">
	  <#include "/pages/components/teacherSearchTable.ftl">
     </form>
    </td>
    <td valign="top">
	  <iframe  src="#" 
	     id="contentListFrame" name="contentListFrame" 
	     marginwidth="0" marginheight="0" scrolling="no"
	     frameborder="0"  height="450" width="100%">
	  </iframe>
    </td>
   </tr>
  </table>
  <script language="JavaScript" type="text/JavaScript" src="scripts/system/BaseInfo.js"></script>
  <script language="javascript">
    type="teacher";
    keys="";
    titles="";
    search();
    searchTeacher=search;
    var bar = new ToolBar("myBar","<@bean.message key="page.teacherListFrame.label" />",null,true,true);  
  </script>    
  </body>
<#include "/templates/foot.ftl"/>