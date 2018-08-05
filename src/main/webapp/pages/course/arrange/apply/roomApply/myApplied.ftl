<#include "/templates/head.ftl"/>
<body>
	<table id="bar" width="100%"></table>
	<#assign cycleName={'1':'天','2':'周','4':'月'}/>
	<@table.table id="listTable" style="width:100%" sortable="true" id="apply">
		<@table.thead>
		    <@table.td text=""/>
		    <@table.sortTd text="活动名称" id="roomApply.activityName" />
		    <@table.sortTd text="活动类型" id="roomApply.activityType.name" width="10%"/>
		    <@table.sortTd text="营利性" id="roomApply.isFree" width="8%"/>
		    <@table.td text="使用教室类型" width="13%"/>
		    <@table.sortTd text="主讲人" id="roomApply.leading" width="13%"/>
		    <@table.sortTd text="申请时间" id="roomApply.applyAt" width="13%"/>
		    <@table.sortTd text="归口审核" id="roomApply.isDepartApproved" width="12%"/>
		    <@table.sortTd text="物管审核" id="roomApply.isApproved" width="12%"/>
	   	</@>
		<@table.tbody datas=roomApplies?if_exists;apply>
	      	<@table.selectTd type="radio" id="roomApplyId" value=apply.id/>
	      	<td><A href="roomApply.do?method=info&roomApplyId=${apply.id}">${(apply.activityName)?default("")}</A></td>
	      	<td>${(apply.activityType.name)?default("")}</td>
	      	<td>${(apply.isFree?string("非营利", "营利"))?default("")}</td>
	      	<td><#list apply.getClassrooms()?if_exists as classroom>${(classroom.configType.name)?default("未设定")}&nbsp;&nbsp;&nbsp;</#list></td>
	      	<td>${(apply.leading)?default("")}</td>
	      	<td>${(apply.applyAt?string("yy-MM-dd HH:mm"))?default("")}</td>
	      	<td>${(apply.isDepartApproved?string("审核通过","审核未通过"))?default("待审核")}<input type="hidden" id="${"depart"+apply.id}" value="${(apply.isDepartApproved?string("审核通过","审核未通过"))?default("待审核")}"/></td>
	      	<td>${(apply.isApproved?string("审核通过","审核未通过"))?default("待审核")}<input type="hidden" id="${"approve"+apply.id}" value="${(apply.isApproved?string("审核通过","审核未通过"))?default("待审核")}"/></td>
		</@>
	</@>
	<@htm.actionForm name="actionForm" action="roomApply.do" entity="roomApply" onsubmit="return false;"/>
 	<script>
		var bar = new ToolBar("bar","我已申请的记录",null,true,true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("<@msg.message key="action.info"/>", "form.target='';info()");
	  	bar.addItem("<@msg.message key="action.delete"/>", "form.target='';remove()");
	  	bar.addItem("复制申请", "singleAction('copyApply')", "new.gif");
		
		var form = document.actionForm;
	    function addApply() {
	    	form.action = "roomApply.do?method=addApply";
	    	form.target = "";
	    	form.submit();
	    }
	    
	    function remove() {
	        var id = getSelectId("roomApplyId");
	        if(id == "" || id == null) {
	        	alert("请选择一个要删除的记录！");
	        	return;
	        }
	        
	        if (document.getElementById("depart"+id).value == '是' || document.getElementById("approve"+id).value == '是') {
	        	alert("该记录已被批准不能删除！");
	        	return;
	        }
	        
	        if (confirm("是否要删除该教室申请记录?")) {
		        addInput(form, "roomApplyId", id);
		    	form.action = "roomApply.do?method=remove";
		    	form.target = "";
		    	form.submit();
	    	}
	    }
 	</script>	
</body>
<#include "/templates/foot.ftl"/>