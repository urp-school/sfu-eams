<#include "/templates/head.ftl"/>
<BODY>
<script language="JavaScript" type="text/JavaScript" src="scripts/system/BaseInfo.js"></script>
  <@getMessage/>
  <@table.table width="100%"  id="listTable" sortable="true">
    <@table.thead>
       <@table.selectAllTd id="attendDeviceId"/>
       <@table.sortTd text="考勤机ID" id="attendDevice.devid"/>
       <@table.sortTd text="教室代码" id="attendDevice.jsid.code"/>
       <@table.sortTd text="教室名称" id="attendDevice.jsid.name"/>
       <@table.sortTd text="教学楼" id="attendDevice.jsid.building.name"/>
       <@table.sortTd text="教室设备配置" id="attendDevice.jsid.configType.name"/>
       <@table.sortTd text="状态" id="attendDevice.kqjzt"/>       
       <@table.sortTd text="IP地址" id="attendDevice.ip"/>
       <@table.sortTd text="上次签到时间" id="attendDevice.qdsj"/>  
    </@>
    <@table.tbody datas=attendDevices;attendDevice>
       <@table.selectTd id="attendDeviceId" value="${attendDevice.devid}"/>
       <td>${(attendDevice.devid)!}</td>
       <td>${(attendDevice.jsid.code)!}</td>
       <td><a href="classRoomSearch.do?method=info&classroom.id=${(attendDevice.jsid.id)!}">${(attendDevice.jsid.name)!}</a></td>
       <td><@i18nName attendDevice.jsid.building?if_exists/></td>
       <td><@i18nName attendDevice.jsid.configType/></td>
       <td>${((attendDevice.kqjzt)?string("正常", "出错"))!}</td>
       <td>${(attendDevice.ip)!}</td>
       <td>${(attendDevice.qdsj?string("yyyy-MM-dd HH:mm:ss"))!}</td>
    </@>
  </@>
 <script language="javascript">
   type="classroom";
   defaultOrderBy="${RequestParameters['orderBy']?default('null')}";
  </script>
</body>
<#include "/templates/foot.ftl"/>