<#include "/templates/head.ftl"/>	
<#import "../cycleType.ftl" as RoomApply/>
<body>
<h4 align="center">借教室统计情况表</h4>
<div id="div1" style="height:90%;width:100%;overflow:auto;cursor:default;">  
	<#assign cycyleNames={'1':'天','2':'周','4':'月'}/>
	<@table.table id="listTable" style="width:100%" sortable="true" id="apply">
		<@table.thead>
		    <@table.sortTd text="序号" id="roomApply.id" />
		    <@table.sortTd text="部门" id="roomApply.auditDepart" />
		    
		    <@table.sortTd text="活动名称" id="roomApply.activityName" />
		    <@table.sortTd text="活动类型" id="roomApply.activityType.name" width=""/>
		    <@table.sortTd text="营利性" id="roomApply.isFree" width=""/>
		    <@table.td text="借用时间"  width=""/>
		    <@table.td text="借用地点" width=""/>
		    <@table.sortTd text="是否使用多媒体设备" id="roomApply.isMultimedia" width=""/>
		    <@table.td text="出席人数" width=""/>
		    
		    <@table.td text="使用教室类型" width=""/>
		    <@table.sortTd text="主讲人" id="roomApply.leading" width=""/>
		    <@table.sortTd text="申请时间" id="roomApply.applyAt" width=""/>
		    <@table.sortTd text="负责人" id="roomApply.departApproveBy.userName" width=""/>
			<@table.td text="费用（元）" width=""/>
	   	</@>
		<@table.tbody datas=roomApplies?if_exists;apply>
	      	<td>${(apply.id)?default("")}</td>	
	      	<td>${(apply.auditDepart.name)?default("")}</td>
	      	<td><A href="roomApply.do?method=info&roomApplyId=${apply.id}">${(apply.activityName)?default("")}</A></td>
	     	<td>${(apply.activityType.name)?default("")}</td>
	      	<td>${(apply.isFree?string("非营利", "营利"))?default("")}</td>
	      	
	      	<td>${apply.applyTime.dateBegin?string("yyyy 年 MM 月 dd 日")}&nbsp;&nbsp;-&nbsp;&nbsp;${apply.applyTime.dateEnd?string("yyyy 年 MM 月 dd 日")}&nbsp;&nbsp; 时间：<@RoomApply.cycleValue count=apply.applyTime.cycleCount type=apply.applyTime.cycleType?string/>${apply.applyTime.timeBegin}-${apply.applyTime.timeEnd} </td>
	      	<td><#list apply.getClassrooms()?if_exists as classroom><@i18nName (classroom.building)?if_exists/>&nbsp;-&nbsp;<@i18nName classroom?if_exists/>&nbsp;&nbsp;&nbsp;</#list></td>
	      
	        <td>${(Apply.isMultimedia?string('使用','不使用'))?default("未指定")}</td>
	        <td>${(apply.attendanceNum?default(""))}</td>
	      	
	      	<td><#list apply.getClassrooms()?if_exists as classroom>${(classroom.configType.name)?default("未设定")}&nbsp;&nbsp;&nbsp;</#list></td>
	      	<td>${(apply.leading)?default("")}</td>
	      	<td>${(apply.applyAt?string("yy-MM-dd HH:mm"))?default("")}</td>
	      	
	      	<td>${(apply.departApproveBy.userName)?default("")}</td>
	      	<td>${(apply.money?string("0.00"))?default(0.00)}</td>
	      	
		</@>
	</@>
	</div>
</body>
<#include "/templates/foot.ftl"/>