<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="myBar"></table>
  <table class="frameTable">
   <tr>
    <form name="searchForm" action="attendDevice.do?method=search" target="contentListFrame" method="post" onsubmit="return false;">
    <td width="20%" class="frameTable_view">
      <#assign extraSearchOptions><#include "base.ftl"/><@stateSelect "attendDevice.jsid"/></#assign>
      <#include "searchTable.ftl"/>
      <table><tr height="350px"><td></td></tr></table>
    </td>
    </form>
    <td valign="top">
     <iframe src="#" id="contentListFrame" name="contentListFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
    </td>
   </tr>
  </table>
  <script language="JavaScript" type="text/JavaScript" src="<@msg.message key="js.baseinfo"/>"></script>
  <script language="JavaScript" type="text/JavaScript" src="scripts/course/attendDevice/BaseInfo2.js"></script>
  <script language="javascript">
    type="attendDevice";
    keys="devid,jsid.code,jsid.name,jsid.building.name,jsid.configType.name,kqjzt,ip,qdsj";
    titles="考勤机ID,教室代码,教室名称,教学楼,教室设备配置,状态,IP地址,上次签到时间";
    labelInfo="考勤监控";
    search();
    //searchClassroom=search;
    initBaseInfoBar2();
    function searchClassroom(){
    	var indexForm22=document.searchForm;
    	addInput(indexForm22, 'attendDevice.jsid.schoolDistrict.id', document.getElementById("district").value);
    	addInput(indexForm22, 'attendDevice.jsid.building.id', document.getElementById("building").value);
    	search();
    }
  </script>
<script language="JavaScript" type="text/JavaScript" src="scripts/CascadeSelect.js"></script> 
<#include "/templates/districtBuildingSelect.ftl"/>
</body>
<#include "/templates/foot.ftl"/>