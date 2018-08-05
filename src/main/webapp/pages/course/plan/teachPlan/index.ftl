<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/course/TeachPlan.js"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<body onload="DWRUtil.useLoadingMessage();">
	<table id="teachPlanBar"></table>
	<table class="frameTable" width="100%">
	   	<tr>
	    	<#assign withAuthority=1>
	    	<td width="20%" class="frameTable_view"><#include "../planSearchForm.ftl"/></td>
	    	<td valign="top">
	 			<iframe src="#" id="planListFrame" name="planListFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0"  height="100%" width="100%"></iframe>
    		</td>
   		</tr>
  	</table>
	<script>
	  	var bar=new ToolBar("teachPlanBar","<@msg.message key="teachPlan.title"/>",null,true,true);
	  	bar.setMessage('<@getMessage />');
	  	var menu = bar.addMenu("<@msg.message key="teachPlan.generate"/>",null);
	  	menu.addItem("<@msg.message key="teachPlan.generate.speciality"/>","genTeachPlan('speciality')");
	  	menu.addItem("<@msg.message key="teachPlan.generate.speciality.batch"/>",'batchGenTeachPlan()');
	  	menu.addItem("<@msg.message key="teachPlan.generate.person"/>","genTeachPlan('std')");
	  	bar.addItem("<@bean.message key="action.modify"/>","editTeachPlan()");
	  	bar.addItem("<@bean.message key="action.add"/>","newTeachPlan()");
	  	bar.addItem("<@bean.message key="action.delete"/>","removeTeachPlan()");
	  	bar.addItem("导出课程", "exportData()");
	  	bar.addItem("<@msg.message key="teachPlan.batchProcess"/>","batchProcess('&method=batchProcessGroupSetting&action=add')");
	
	    multi=false;
	    withAuthority=true;
	    searchTeachPlan();
	    
	    function batchProcess(extra){
	        var planIds = getIds();
	        if(''==planIds){
	           alert("请选择一个或多个培养计划进行批量处理");return;
	        }
	        if(getConfirmState(planIds)=="1"){alert("你选择的某些计划已经确认了，请取消确认后进行修改。");return;}
	        var form = document.planSearchForm;
	        form.action="teachPlan.do?teachPlanIds="+planIds;
	        form.action+=extra;
	        setSearchParams(form,form);
	        form.submit();
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>