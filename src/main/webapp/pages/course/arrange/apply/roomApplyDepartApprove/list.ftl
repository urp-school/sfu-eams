<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/prompt.js"></script>
<body>
 	<div id="toolTipLayer" style="position:absolute; visibility: hidden"></div>
 	<script>initToolTips()</script>
	<table id="bar" width="100%"></table>
	<#include "../roomApplyApprove/roomApplyList.ftl"/>
	<@htm.actionForm name="actionForm" action="roomApplyDepartApprove.do" entity="roomApply"/>
 	<script>
		var bar = new ToolBar("bar","归口审核",null,true,true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("<@msg.message key="action.info"/>", "info()");
		<#if (RequestParameters['lookContent'])?exists>
	  	  	<#if RequestParameters['lookContent'] == ''>
		  	  	bar.addItem("审核通过", "remarkAction('departApprove')");
		  	  	bar.addItem("未审核通过", "remarkAction('departCancel')");
	  	  		bar.addItem("<@msg.message key="action.edit"/>", "singleAction('editApply')");
	  	  	<#elseif RequestParameters['lookContent'] == '0'>
	  	  		bar.addItem("审核通过", "remarkAction('departApprove')");
	  	  		bar.addItem("<@msg.message key="action.edit"/>", "singleAction('editApply')");
	  	  	<#elseif RequestParameters['lookContent'] == '1'>
	  	  		bar.addItem("取消审核", "remarkAction('departCancel')");
	  	  		bar.addItem("<@msg.message key="action.edit"/>", "singleAction('editApply')");
	  	  	</#if>
  	  	</#if>
  	  	bar.addItem("<@msg.message key="action.export"/>", "exportData()");
		<#include "../roomApplyApprove/exportDatasJS.ftl"/>
		resultQueryStr = "";
		if(typeof queryStr != "undefined"){
		    resultQueryStr = queryStr;
		}
		function remarkAction(method){
	    	var ids = getSelectIds("roomApplyId");
		 	if (ids == null || ids == "") {
		 		alert("你没有选择要操作的记录！");
		 		return false;
		 	}
		 	var str = window.prompt("请输入备注,允许为空,至多不超过50个字!","");
			form.action = "roomApplyDepartApprove.do?method=" + method;
		    addParamsInput(form, resultQueryStr);
		    addInput(form, "remark", str);
		    submitId(form,"roomApplyId",false);
	 	}
 	</script>	
</body>
<#include "/templates/foot.ftl"/>