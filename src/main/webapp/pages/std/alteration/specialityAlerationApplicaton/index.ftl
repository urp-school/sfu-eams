<#include "/templates/head.ftl"/>
<#assign stateNames={'0':'新增','1':'审核通过','2':'审核未通过','5':'已取消'}/>
<body>
	<table id="bar" width="100%"></table>
	<#assign isInTime=false>
	<#list plans as plan>
	    <#assign isInTime=plan.isInTime()>
		<table width="100%" class="formTable" align="center" style="padding:0px;bolder-spacing:0px;">
		<tr><td colspan="4" class="darkColumn" align="center">${plan.calendar.year}学年${plan.calendar.term}学期</td></tr>
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
		<tr><td colspan="4" class="darkColumn" align="center"><#if isInTime><button onclick="apply(${plan.id})">我要申请</button><#else>不在报名时间</#if></td></tr>
	</table>
	</#list>
	<table id="bar1" width="100%"></table>
	<@table.table id="changeMajorApplication" width="100%">
		<@table.thead>
			<@table.selectAllTd id="applicationId"/>
			<@table.td text="现在专业"/>
			<@table.td text="现在年级"/>
			<@table.td text="申请专业(方向)"/>
			<@table.td text="申请年级"/>
			<@table.td text="申请状态"/>
			<@table.td text="申请时间"/>
		</@>
		<@table.tbody datas=applications;application>
			<@table.selectTd id="applicationId" value=application.id/>
			<td><@i18nName (application.major)?if_exists/></td>
			<td>${(application.grade)?if_exists}</td>
			<td><@i18nName (application.majorPlan.major)?if_exists/>&nbsp;&nbsp;<@i18nName (application.majorPlan.majorField)?if_exists/></td>
			<td>${(application.applyGrade)?if_exists}</td>
			<td>${stateNames[(application.state)?string]}</td>
			<td>${(application.applyAt?string("yyyy-MM-dd HH:mm:ss"))?if_exists}</td>
		</@>
	</@>
	<@htm.actionForm name="actionForm" action="specialityAlerationApplicaton.do" entity="application"></@>
	<script>
	    var bar = new ToolBar("bar", "转专业申请时间", null, true, true);
	    
		var bar1 = new ToolBar("bar1", "转专业申请记录", null, true, true);
		bar1.setMessage('<@getMessage/>');
		bar1.addItem("修改", "toEdit()");
		bar1.addItem("取消", "toCancle()");
		
		function canDo() {
		  var id = getSelectId("applicationId");
		  if (isEmpty(id)) {
		      alert("请选择要操作的记录。");
		      return false;
          }
		  <#list applications as application>if (id == "${application.id}" && !${application.isInTime?default(true)?string("true", "false")}) {
		      alert("当前申请记录的操作不在开放时间内。");
		      return false;
          }<#if application_has_next> else </#if></#list>
          return true;
        }

        function toEdit () {
            if (canDo()) {
                edit();
            }
        }
        
        function toCancle () {
            if (canDo()) {
                cancel();
            }
        }
		function apply(planId) {
			form.action="specialityAlerationApplicaton.do?method=edit&planId="+planId ;
			form.submit();
		}
		
		function cancel(){
			submitId(form, "applicationId", false, "specialityAlerationApplicaton.do?method=cancel","你确定要取消申请吗?");
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>
