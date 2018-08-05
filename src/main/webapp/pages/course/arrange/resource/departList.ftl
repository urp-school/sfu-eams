 <div style="display: none;" id="view2">
   <table  width="100%">
    <tr>
      <td  class="infoTitle" align="left" valign="bottom">
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B><@bean.message key="info.collegeList"/></B>
      </td>
    <tr>
      <td  colspan="8" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>    
  </table>
   <table width="100%" border="0" class="listTable" >
   <form name="departListForm" method="post" target="contentFrame" action="" onsubmit="return false;">
   <tr align="center" class="darkColumn" style="font-size:12px">
     <td><@bean.message key="attr.code"/></td>
     <td><@bean.message key="info.collegeList"/></td>
   </tr>   
   <#list sort_byI18nName(departmentList) as depart>
   <tr align="center"  class="infoTitle">
     <td width="20%" bgcolor="#ffffff">${depart.code}</td>
     <td class="padding" bgcolor="#ffffff" style="width:120px" onclick="javascript:getResourceOfDepart('${depart.id}')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
     <@i18nName depart/>
     </td>
   </tr>
   </#list>
   </form>
  </table>
  </div>
  