<#include "/templates/head.ftl"/>
<body>
	<table id="bar" width="100%"></table>
	<#include "roomApplyList.ftl"/>
	<@htm.actionForm name="actionForm" action="roomApplyApprove.do" entity="roomApply" onsubmit="return false;"/>
 	<script>
		var bar = new ToolBar("bar","物管审核",null,true,true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("<@msg.message key="action.info"/>", "info()");
		<#if (RequestParameters['lookContent'])?exists>
	  	  	<#if RequestParameters['lookContent'] == '1'>
	  	  		bar.addItem("审核分配", "form.target='';singleAction('applyRoomSetting')", "update.gif");
	  	  		bar.addItem("<@msg.message key="action.edit"/>", "form.target='';singleAction('editApply')");
	  	  		bar.addItem("<@msg.message key="action.delete"/>", "form.target='';remove()");
			<#elseif RequestParameters['lookContent'] == '2'>
	  	  		bar.addItem("<@msg.message key="action.edit"/>", "form.target='';singleAction('editApply')");
	  	  		bar.addItem("<@msg.message key="action.edit"/>分配", "form.target='';singleAction('applyRoomSetting')", "update.gif");
	  	  		bar.addItem("<@msg.message key="action.edit"/>费用", "form.target='';singleAction('adjustFeeForm')", "update.gif");
	  	  		bar.addItem("取消分配", "cancelApply()", "update.gif");
	  	  	</#if>
  	  	</#if>
  	  	bar.addItem("<@msg.message key="action.export"/>", "exportData()");

	   	function printApply(selectStyle) {	        
		    	window.open("roomApplyApprove.do?method=print&selectStyle=" + selectStyle);
		    }
		function validate(){
	    	var obj=document.getElementsByName("roomApplyId");
	    	for(var i=0;i<obj.length;i++){
	    		if(obj[i].checked)return true;
	    	}
	    	return false;
	    }
	    function cancelApply(){
	    	if(!validate()){alert("你没有选择要操作的记录！");return;}
	        if(confirm("确定要取消该申请已分配的教室吗？")){
		        var str = window.prompt("请输入备注,允许为空,至多不超过50个字!","");
		      	var form =document.actionForm;
		      	var roomId=document.getElementById("roomApplyId").value;
		 		form.action="roomApplyApprove.do?method=cancel&roomApplyId="+roomId;
		 		addInput(form, "remark", str);
		 		form.submit();
		 	}
	 	}
	 	<#include "exportDatasJS.ftl"/>
 	</script>
</body>
<#include "/templates/foot.ftl"/>