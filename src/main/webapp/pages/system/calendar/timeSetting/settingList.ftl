  <table width="100%" class="listTable" id="settingListTable" >
   <tr  class="darkColumn" style="font-size:12px" >
     <td>各类时间设置</td>
   </tr>
   <#list settingList as setting>
   <tr  height="20px"  class="infoTitle">         
     <td class="padding"  style="width:120px" bgcolor="#ffffff"  <#if setting_index=0>id="defaultSetting"</#if>
       onclick="clearSelected(settingListTable,this);setSelectedRow(settingListTable,this);loadSetting('${setting.id}')"
       onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
      ${setting.name}
     </td>
   </tr>
   </#list>
  </table>