<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/common/ieemu.js"></script> 
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
<#assign labInfo><@bean.message key="info.availTime.room"/></#assign>  
<#include "/templates/help.ftl"/>
   <table  class="frameTable_title">
    <form name="roomSearchForm" method="post">
    <input type="hidden" name="pageNo" value="1"/>
    <input type="hidden" name="pageSize" value="20"/>
    <input type="hidden" name="params" value=""/>
    <tr align="left" style="font-size: 10pt;" onKeyDown="javascript:enterQuery(event)">
      <td id="name" >
         <@bean.message key="attr.infoname"/>:
        <input type="text" name="classroom.name" maxlength="20" style="width:60px;"/>
         <@bean.message key="common.classroomConfigType"/>:
         <select name="classroom.configType.id" style="width:100px;">
         <option value=""><@bean.message key="common.all"/></option>
         <#list result.classroomConfigTypeList as configType>
         <option value="${configType.id}"><@i18nName configType/></option>
         </#list>
         </select>
     	   <@bean.message key="common.schoolDistrict"/>:
 	       <select  id="district" name="classroom.schoolDistrict.id" style="width:100px;">
	           <option value=""><@bean.message key="common.all"/></option>
	       </select>
       <@bean.message key="common.building"/>:
 	     <select id="building" name="classroom.building.id" style="width:120px;">
	         <option value=""><@bean.message key="common.all"/></option>
         </select>
 	       <@bean.message key="entity.department"/>:
 	       <select name="depart.id" style="width:100px;">
           <option value=""><@bean.message key="common.all"/></option>
           <#list result.departList as depart>
           <option value="${depart.id}"><@i18nName depart/></option>
           </#list>
           </select>
 	    </td>
      <td>
    	  <input type="button" onclick="pageGo(1);"  value="<@bean.message key="action.query"/>" class="buttonStyle"/>
      </td>
    </tr>
    </form>
    </table>
    <table class="frameTable" width="100%" height="85%">
   	<tr>
		<td width="15%" class="frameTable_view">
		  <iframe  src="roomAvailTime.do?method=roomList&pageNo=1&pageSize=20" id="roomListFrame" name="roomListFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
		</td>
		<td colspan="3" valign="top">
		  <iframe  src="roomAvailTime.do?method=roomAvailTimeInfo" id="availTimeFrame" name="availTimeFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
		</td>
	</tr>
</table>
<script>
    function pageGo(pageNo){
       var form = document.roomSearchForm;
       form.pageNo.value = pageNo;
       form.target="roomListFrame";
       form.action="roomAvailTime.do?method=roomList";
       addInput(form,"classroom.schoolDistrict.id",$("district").value);
       addInput(form,"classroom.building.id",$("building").value);
       form.submit();
    }
     function availTimeInfo(roomId){
       var form = document.roomSearchForm;
       form.target="availTimeFrame"
       form.action="roomAvailTime.do?method=roomAvailTimeInfo&classroom.id=" + roomId
       form.submit();
     }
     function edit(){
       var form = document.roomSearchForm;
       form.target="availTimeFrame"
       form.action="roomAvailTime.do?method=roomAvailTimeInfo&classroom.id=" + roomId
       form.submit();
     }
     function enterQuery(event) {
        if (portableEvent(event).keyCode == 13) {
            pageGo(1);
        }
     }
</script>
</body>
<script language="JavaScript" type="text/JavaScript" src="scripts/CascadeSelect.js"></script>
<#include "/templates/districtBuildingSelect.ftl"/>
<#include "/templates/foot.ftl"/>