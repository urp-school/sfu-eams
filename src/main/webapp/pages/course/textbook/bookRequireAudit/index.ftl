<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/Selector.js"></script>
<script language="JavaScript" src="<@bean.message key="menu.js.url"/>"></script>

<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="bookRequirementBar"></table>
 <table  class="frameTable_title">
      <tr>
       <td id="viewTD1">
          <font color="blue">教材需求</font> 
       </td>
       <td class="separator">|</td>
      
      <form name="taskForm" target="teachTaskListFrame" method="post" method="bookRequirement.do?method=index" onsubmit="return false;">
      <#include "/pages/course/calendar.ftl"/>
       </form>
      </tr>
     </table>
     

<table class="frameTable">
   	<tr>
    	<form name="requireSearchForm" action="bookRequirement.do?method=requirementList" method="post" target="contentFrame">
    	<td style="width:160px"  class="frameTable_view"><#include "../requireSearchTable.ftl"/></td>
    	</form>
	    <td valign="top">  
	     	<iframe src="#" id="contentFrame" name="contentFrame" scrolling="no" marginwidth="0" marginheight="0" frameborder="0"  height="100%" width="100%"></iframe>
		</td>
   </tr>
</table>
<script language="javascript">
	var bar=new ToolBar('bookRequirementBar','教材需求审核',null,true,true);
	bar.setMessage('<@getMessage/>');
	bar.addHelp("<@msg.message key="action.help"/>");
	
   	function searchRequires(pageNo,pageSize,orderBy){
		var form =document.requireSearchForm;          
		form.action="bookRequireAudit.do?method=search";
		goToPage(form,pageNo,pageSize,orderBy);
   	}
 	searchRequires();
</script>
</body>
<#include "/templates/foot.ftl"/>