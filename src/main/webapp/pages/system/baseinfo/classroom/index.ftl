<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="myBar"></table>
  <table class="frameTable">
   <tr>
    <form name="searchForm" action="classroom.do?method=search" target="contentListFrame" method="post" onsubmit="return false;">
    <td width="20%" class="frameTable_view">
      <#assign extraSearchOptions><#include "../../base.ftl"/><@stateSelect "classroom"/></#assign>
      <#include "/pages/components/classroomSearchTable.ftl"/>
      <table><tr height="350px"><td></td></tr></table>
    </td>
    </form>
    <td valign="top">
     <iframe src="#" id="contentListFrame" name="contentListFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
    </td>
   </tr>
  </table>
  <script language="JavaScript" type="text/JavaScript" src="<@msg.message key="js.baseinfo"/>"></script>
  <script language="javascript">
    type="classroom";
    keys="code,name,engName,abbreviation,description,createAt,modifyAt,remark,state,capacityOfCourse,capacityOfExam,floor,configType.name,building.name,schoolDistrict.name";
    titles="<@msg.message key="attr.code"/>,<@msg.message key="attr.name"/>,<@msg.message key="attr.engName"/>,<@msg.message key="attr.abbreviation"/>,<@msg.message key="attr.description"/>,<@msg.message key="attr.createAt"/>,<@msg.message key="attr.modifyAt"/>,<@msg.message key="attr.remark"/>,<@msg.message key="attr.state"/>,<@msg.message key="attr.capacityOfCourse"/>,<@msg.message key="attr.capacityOfExam"/>,<@msg.message key="attr.floor"/>,<@msg.message key="entity.classroomConfigType"/>,<@msg.message key="entity.building"/>,<@msg.message key="entity.schoolDistrict"/>";
    labelInfo="<@bean.message key="page.classroomListFrame.label" />";
    search();
    searchClassroom=search;
    initBaseInfoBar();
  </script>
<script language="JavaScript" type="text/JavaScript" src="scripts/CascadeSelect.js"></script> 
<#include "/templates/districtBuildingSelect.ftl"/>
</body>
<#include "/templates/foot.ftl"/>