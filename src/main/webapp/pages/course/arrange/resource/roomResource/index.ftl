<#include "/templates/head.ftl"/>

<BODY LEFTMARGIN="0" TOPMARGIN="0" >
<form name="resourceForm" method="post">
<#assign labInfo><@bean.message key="info.occupyQuery.room"/></#assign>
<#include "/templates/help.ftl"/> 
<#include "../calendarForm.ftl">
  <table class="frameTable" height="85%">
    <input type="hidden" name="classroom.name" value=""/> 
    <input type="hidden" name="classroom.configType.id" value=""/>
    <input type="hidden" name="classroom.schoolDistrict.id" value=""/>
    <input type="hidden" name="classroom.building.id" value=""/>
    <input type="hidden" name="depart.id" value=""/>
    </form>
    <tr>
     <td class="frameTable_view" style="width:160px">
     <#include "searchForm.ftl"/> 
     </td>
     <td class="frameTable_content">
	     <iframe  src="roomResource.do?method=search"
	     id="contentFrame" name="contentFrame"
	     marginwidth="0" marginheight="0" scrolling="no"
	     frameborder="0" height="100%" width="100%">
	     </iframe>
     </td>
  </tr>
</table>
<script language="JavaScript" type="text/JavaScript" src="scripts/course/Resource.js"></script>
<script>
   resourceType="room";
</script>
<script language="JavaScript" type="text/JavaScript" src="scripts/viewSelect.js"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/CascadeSelect.js"></script> 
<#include "/templates/districtBuildingSelect.ftl"/>
</body>
<#include "/templates/foot.ftl"/> 