<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
	<@table.table style="width:100%">
		<@table.thead>
		    <@table.td text=""/>
		    <@table.td text="活动名称" id="roomApply.activityName"/>
		    <@table.td text="活动类型" id="roomApply.activityType.name"/>
		    <@table.td text="营利性" id="roomApply.isFree"/>
		    <@table.td text="主讲人" id="roomApply.leading"/>
		    <@table.td text="物管审核时间" id="roomApply.approveAt"/>
	   	</@>
		<@table.tbody datas=roomApplies?if_exists;apply>
	      	<@table.selectTd type="radio" id="roomApplyId" value=apply.id/>
	      	<td><A href="roomApply.do?method=info&roomApplyId=${apply.id}">${(apply.activityName)?default("")}</A></td>
	      	<td>${(apply.activityType.name)?default("")}</td>
	      	<td>${(apply.isFree?string("非营利", "营利"))?default("")}</td>
	      	<td>${(apply.leading)?default("")}</td>
	      	<td>${(apply.approveAt?string("yy-MM-dd HH:mm"))?default("")}</td>
		</@>
	</@>
 </body>
 <#include "/templates/foot.ftl"/>