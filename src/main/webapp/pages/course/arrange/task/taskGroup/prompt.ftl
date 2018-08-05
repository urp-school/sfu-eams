<#include "/templates/head.ftl"/>
<body>
    <table align="center" class="ToolBar" >
    <tr>
    <td><@bean.message key="prompt.group.select"/></td>
     <td  class="padding" style="width:100px" onclick="javascript:parent.newGroup();" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
          <img src="${static_base}/images/action/new.gif" class="iconStyle"/>新增排课组
      </td>
    </tr>
    </table>
</body>
<#include "/templates/foot.ftl"/>