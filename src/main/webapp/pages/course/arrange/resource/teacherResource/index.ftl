<#include "/templates/head.ftl"/>
 
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<form name="resourceForm" method="post">
<table id="bar"></table>
<#include "../calendarForm.ftl">
  <table class="frameTable" height="85%">
    <input type="hidden" name="teacher.id" value=""/>
    <input type="hidden" name="teacher.name" value=""/> 
    <input type="hidden" name="teacher.code" value=""/>
    <input type="hidden" name="teacher.department.id" value=""/>
    <input type="hidden" name="teacher.isTeaching" value=""/>
    </form>
    <tr>
     <td class="frameTable_view" style="width:160px">
     <#include "searchForm.ftl"/>
     </td>
     <td class="frameTable_content">
	     <iframe  src="#"
	     id="contentFrame" name="contentFrame"
	     marginwidth="0" marginheight="0" scrolling="no"
	     frameborder="0"  height="100%" width="100%">
	     </iframe>
     </td>
  </tr>
</table>
<script language="JavaScript" type="text/JavaScript" src="scripts/course/Resource.js"></script>
<script>
  	resourceType="teacher";
  	var bar = new ToolBar("bar", "<@msg.message key="info.occupyQuery.teacher"/>", null, true ,true);
  	bar.addHelp("<@msg.message key="action.help"/>");
  	function search(){
  	 populateParams('teacher');
  	 searchResource();
  	}
  	search();
</script>
</body>
<script language="JavaScript" type="text/JavaScript" src="scripts/viewSelect.js"></script>
<#include "/templates/foot.ftl"/>