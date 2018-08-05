<#include "/templates/head.ftl"/>
<body>
<table id="bar" width="100%"></table>
<#if plan?exists>
	<table width="100%" class="formTable" align="center" style="padding:0px;bolder-spacing:0px;">
		<tr>
			<td class="title" width="20%">申请开始时间</td>
			<td width="30%">${(plan.startAt?string("yyyy-MM-dd HH:mm"))?if_exists}</td>
			<td class="title" width="20%">专业数目</td>
			<td width="30%">${plan.majorPlans?size}</td>
		</tr>
		<tr>
			<td class="title" width="20%">申请结束时间</td>
			<td width="30%">${(plan.endAt?string("yyyy-MM-dd HH:mm"))?if_exists}</td>
			<td class="title" width="20%">计划总人数</td>
			<td width="30%"><#assign total=0><#list plan.majorPlans as majorPlan><#assign total=total+majorPlan.planCount></#list>${total}</td>
		</tr>
	</table>
	<table id="bar1" width="100%"></table>
	<@table.table  id="major" width="100%">
		 <@table.thead>
			 <@table.selectAllTd id="majorPlanId"/>
			 <@table.td text="专业"/>
			 <@table.td text="专业方向"/>
			 <@table.td text="计划人数"/>
		 </@>
		 <@table.tbody datas=plan.majorPlans;majorPlan>
			 <@table.selectTd id="majorPlanId" value=(majorPlan.id)?if_exists/>
			 <td><@i18nName (majorPlan.major)?if_exists/></td>
			 <td><@i18nName (majorPlan.majorField)?if_exists/></td>
			 <td>${(majorPlan.planCount)?if_exists}</td>
		 </@>
	</@>
<#else>
暂时没有计划,请添加
</#if>
<script>
   queryStr="&calendar.id=${RequestParameters['calendar.id']?if_exists}";
</script>
<@htm.actionForm name="actionForm" action="specialityAlerationAudit.do" entity="majorPlan">
	<input type="hidden" name="plan.id" value="${(plan.id)?if_exists}"/>
	<input type="hidden" name="calendar.id" value="${RequestParameters['calendar.id']?if_exists}"/>
	<input type="hidden" name="keys" value="std.code,std.name,std.department.name,std.firstMajor.name,std.firstAspect.name,std.firstMajorClass.name,majorPlan.major.department.name,majorPlan.major.name,majorPlan.majorField.name,stdGpa,stateName,applyAt"/>
	<input type="hidden" name="titles" value="学号,姓名,现在院系,现在专业,现在专业方向,现在班级,申请院系,申请专业,申请专业方向,总绩点,申请状态,申请时间"/>
</@>
	<script>
		var bar = new ToolBar("bar", "转专业招收计划", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("修改/添加计划", "editPlan()");
		var bar1 = new ToolBar("bar1", "专业列表", null, true, true);
		bar1.addItem("申请名单", "applicationList()");
		bar1.addItem("添加专业", "editNewPlanCount()");
        bar1.addItem("批量修改人数", "editPlanCount()");
        bar1.addItem("<@msg.message key="action.export"/>", "exportData()");
		
	    function editNewPlanCount(){
	    	form.action="specialityAlerationAudit.do?method=editNewPlanCount";
			submitActionForm();
	    }
	    function editPlanCount(){
	    	form.action="specialityAlerationAudit.do?method=editPlanCount";
			submitActionForm();
	    }
		function editPlan(){
			 form.action="specialityAlerationAudit.do?method=editPlan";
			 submitActionForm();
		}
		function applicationList(){
			submitId(form, "majorPlanId", false, "specialityAlerationAudit.do?method=applicationList");
		}
		
		function exportData(){
    		addHiddens(form,resultQueryStr);
    		submitId(form, "majorPlanId", true, "specialityAlerationAudit.do?method=export", "是否导出查询出的所有任务?");
     	}
	</script>
</body>
<#include "/templates/foot.ftl"/>