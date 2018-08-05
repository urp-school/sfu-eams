<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/course/Resource.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="bar"></table>
    <table class="frameTable_title">
	 <form name="resourceForm" method="post" action="" onsubmit="return false;">
     <tr>
      <td id="viewTD0" class="transfer" style="width:100px" >
      	  <font color="blue"><@bean.message key="action.advancedQuery"/></font>
      </td> 
      <td>|</td>
      <#include "/pages/course/calendar.ftl"/>
      </tr>
  </table>
  <table class="frameTable" height="85%" >
    <tr>
     <td class="frameTable_view" style="width:160px">   
     <input type="hidden" name="calendar.id" value="${calendar.id}"/>
     <#assign stdTypeList=calendarStdTypes/>
     <#include "/pages/components/initAspectSelectData.ftl"/>
     <#include "/pages/components/adminClassSearchTable.ftl"/> 
     </form>
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
<script>
	var bar = new ToolBar("bar", "<@msg.message key="info.occupyQuery.class"/>", null, true, true);
	bar.addHelp("<@msg.message key="action.help"/>");
   	resourceType="class";
   	function searchClass(){
     	searchResource();
   	}
   	searchClass();
</script>
</body>
<#include "/templates/foot.ftl"/>