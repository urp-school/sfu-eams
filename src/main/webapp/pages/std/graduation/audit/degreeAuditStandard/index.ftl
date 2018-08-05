<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="myBar"></table>
  <table class="frameTable">
   <tr>
    <td  style="width:160px" class="frameTable_view"><#include "searchForm.ftl"/></td>
    <td valign="top">
	 <iframe  src="#" 
	     id="contentListFrame" name="contentListFrame" 
	     marginwidth="0" marginheight="0" scrolling="no"
	     frameborder="0"  height="100%" width="100%">
	  </iframe> 
    </td>
   </tr>
  </table>
  <script language="javascript">
    var bar = new ToolBar('myBar','学位审核标准管理',null,true,true);
    bar.addHelp('<@msg.message key="action.help"/>');
    var form = document.searchForm;
    action="degreeAuditStandard.do";
    function search(pageNo,pageSize,orderBy){
       form.action=action+"?method=search";
       goToPage(form,pageNo,pageSize,orderBy);
    }
    search();
  </script>  
  </body>
<#include "/templates/foot.ftl"/>