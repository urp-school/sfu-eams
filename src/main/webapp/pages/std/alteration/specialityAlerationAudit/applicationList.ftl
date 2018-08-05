<#include "/templates/head.ftl"/>
<#assign stateNames={'0':'新增','1':'审核通过','2':'审核未通过','5':'已取消'}/>
<body>
	<table id="bar" width="100%"></table>
	<@table.table id="changeMajorApplication" width="100%" sortable="true">
		<@table.thead>
			<@table.selectAllTd id="applicationId"/>
			<@table.sortTd text="学号" id="application.std.id"/>
			<@table.td text="姓名"/>
			<@table.td text="现在专业"/>
			<@table.sortTd text="现在年级" id="application.grade"/>
			<@table.td text="申请院系"/>
			<@table.td text="申请专业"/>
			<@table.sortTd text="申请年级" id="application.applyGrade"/>
			<@table.sortTd text="总绩点" id="application.stdGpa"/>
			<@table.td text="申请状态"/>
			<@table.sortTd text="申请时间" id="application.applyAt"/>
		</@>
		<@table.tbody datas=applications;application>
			<@table.selectTd id="applicationId" value=application.id/>
			<td><a href="studentDetailByManager.do?method=detail&stdId=${application.std.id}" title="查看学生基本信息" target="blank">${application.std.code}</a></td>
			<td><a href="stdGradeReport.do?method=report&stdIds=${application.std.id}" title="查看学生成绩总表" target="blank">${application.std.name}</a></td>
			<td><@i18nName (application.major)?if_exists/></td>
			<td>${(application.grade)?if_exists}</td>
			<td><@i18nName (application.majorPlan.major.department)?if_exists/></td>
			<td><@i18nName (application.majorPlan.major)?if_exists/></td>
			<td>${(application.applyGrade)?if_exists}</td>
			<td>${(application.stdGpa)?if_exists}</td>
			<td>${stateNames[(application.state)?string]}</td>
			<td>${(application.applyAt?string("yyyy-MM-dd HH:mm"))?if_exists}</td>
		</@>
	</@>
	<@htm.actionForm name="actionForm" action="specialityAlerationAudit.do" entity="application">
	   <input name="calendar.id" type="hidden" value="${RequestParameters['calendar.id']}"/>
	</@>
	<script>
		var bar = new ToolBar("bar", "${majorPlan.major.name} 申请情况 计划招收${majorPlan.planCount} 人", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("更新绩点","refurbish()");
		bar.addItem("审核通过","audit(1)");
		bar.addItem("审核未通过","audit(2)");
		bar.addBack("<@msg.message key="action.back"/>");
		function audit(state){
		   addInput(document.actionForm,"state",state);
		   multiAction("audit","确定操作?");
		}
		function refurbish(){
			addParamsInput(form, resultQueryStr);
		   	submitId(form, "applicationId", true, "specialityAlerationAudit.do?method=refurbishGPA");
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>