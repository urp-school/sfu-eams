<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<#assign entityNames={'class':'班级','teacher':'教师','room':"教室"}>
<#assign weeks={'1':'星期一','2':'星期二','3':'星期三','4':'星期四','5':'星期五','6':'星期六','7':'星期日'}>
<table id="collisionListBar"></table>
  <script>
   var bar = new ToolBar('collisionListBar','${entityNames[RequestParameters['kind']]}冲突检测结果(点击名称,查看课表)',null,true,true);
   bar.addItem("<@msg.message key="action.print"/>","print()");
   bar.addBack("<@msg.message key="action.back"/>");
  </script>
  <@table.table id="statTable" width="100%">
	  <@table.thead>
	  <#if RequestParameters['kind']="room">
	      <td width="20%" >${entityNames[RequestParameters['kind']]}</td>
	      <td>校区</td>
	      <td>教学楼</td>
	      <td>设备配置</td>
	      <td>容纳听课人数</td>
	      <td>冲突时间</td>
       <#elseif RequestParameters['kind']="teacher">
	      <td>工号</td>
	      <td><@msg.message key="attr.personName"/></td>
	      <td>职称</td>
	      <td>冲突时间</td>
       <#elseif RequestParameters['kind']="class">
	      <td>班级名称</td>
	      <td><@msg.message key="entity.department"/></td>
	      <td><@msg.message key="entity.speciality"/></td>
	      <td>冲突时间</td>
       </#if>
	  </@>
	  <#if RequestParameters['kind']="room">
	  <@table.tbody datas=collisions;collision>
	    <td><A target="_blank" href="courseTable.do?method=courseTable&calendar.id=${RequestParameters['calendar.id']}&setting.kind=room&setting.forCalendar=0&ids=${collision.resource.id}"><@i18nName collision.resource/></A></td>
	    <td><@i18nName collision.resource.schoolDistrict?if_exists/></td>
	    <td><@i18nName collision.resource.building?if_exists/></td>
	    <td><@i18nName collision.resource.configType/></td>
	    <td>${collision.resource.capacityOfCourse}</td>
	    <td><#list collision.times as time>${weeks[time.weekId?string]} (${time.startUnit}-${time.endUnit})<br></#list></td>
	  </@>
	  <#elseif RequestParameters['kind']="teacher">
	  <@table.tbody datas=collisions;collision>
	    <td>${collision.resource.code?if_exists}</td>
	    <td><A target="_blank" href="courseTable.do?method=courseTable&calendar.id=${RequestParameters['calendar.id']}&setting.kind=teacher&setting.forCalendar=0&ids=${collision.resource.id}"><@i18nName collision.resource/></A></td>
	    <td><@i18nName collision.resource.title?if_exists/></td>
	    <td><#list collision.times as time>${weeks[time.weekId?string]} (${time.startUnit}-${time.endUnit})<br></#list></td>
	  </@>
	  <#elseif RequestParameters['kind']="class">
	  <@table.tbody datas=collisions;collision>
	    <td><A target="_blank" href="courseTable.do?method=courseTable&calendar.id=${RequestParameters['calendar.id']}&setting.kind=class&setting.forCalendar=0&ids=${collision.resource.id}"><@i18nName collision.resource/></A></td>
	    <td><@i18nName collision.resource.department/></td>
	    <td><@i18nName collision.resource.speciality?if_exists/></td>
	    <td><#list collision.times as time>${weeks[time.weekId?string]} (${time.startUnit}-${time.endUnit})<br></#list></td>
	  </@>
	  </#if>
  </@>
</body>
<#include "/templates/foot.ftl"/>  