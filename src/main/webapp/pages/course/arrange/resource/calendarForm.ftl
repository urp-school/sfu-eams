    <table class="frameTable_title">
     <tr>
      <td id="viewTD0" class="transfer" style="width:100px" onclick="javascript:changeToView('view1',event)" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
      	  <font color="blue"><@bean.message key="action.advancedQuery"/></font>
      </td> 
      <td id="viewTD1" class="padding" style="width:100px" onclick="javascript:changeToView('view2',event)" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
        <font color="blue"><@bean.message key="info.collegeList"/></font>
      </td>
      <td>|</td>
      <#include "/pages/course/calendar.ftl"/>
      </tr>
  </table>