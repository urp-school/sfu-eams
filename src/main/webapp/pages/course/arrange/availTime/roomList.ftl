<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
   <table  width="100%">
    <tr>
      <td  class="infoTitle" align="left" valign="bottom">
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B><@bean.message key="entity.classroom" /> <@bean.message key="common.list"/></B>
      </td>
    <tr>
      <td  colspan="8" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
  </table>
   <@table.table width="100%" id="roomListTable">
     <@table.thead>
      <@table.td width="15%" text=""/>
      <@table.td name="attr.infoname"/>
     </@>
     <@table.tbody simplePageBar=true datas=classroomList;classroom,classroom_index>
      <td>${classroom_index + 1}</td>
      <td class="padding" style="height:20px;width:100%" <#if classroom_index=0>id="defaultRoom"</#if>
        onclick="javascript:setSelectedRow(roomListTable,this);availTimeInfo('${classroom.id}')" 
        onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
        <@i18nName classroom/>
      </td>
    </tr>
	</@>
   </@>
  <script language="javascript">
    function pageGo(pageNo){
       self.parent.pageGo(pageNo);
    }
     function availTimeInfo(classroomId){
       self.parent.availTimeInfo(classroomId);
     }
   <#if classroomList?size != 0> 
    defaultRoom.onclick();
   <#else>
   parent.availTimeFrame.window.location="roomAvailTime.do?method=roomAvailTimeInfo"
   </#if>
  </script>
  
  </body>
<#include "/templates/foot.ftl"/>