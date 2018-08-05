<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
<table id="bar"></table>
<table width="100%" class="frameTable">
	<tr>
		<form method="post" action="roomApplyDepartApprove.do?method=search" name="searchRoomApplyApproveForm" target="contentFrame" onsubmit="return false;">
		<td class="frameTable_view" style="width:168px;">
			<#include "searchTable.ftl"/>
		</td>
		</form>
		<td valign="top">
	     	<iframe name="contentFrame" id="contentFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
		</td>
	</tr>
</table>
 	<script>
        var bar=  new ToolBar("bar","归口审核",null,true,true);
        bar.addHelp("<@msg.message key="action.help"/>");
        
        var form = document.searchRoomApplyApproveForm;
        search();

 		document.getElementById("searchRoom").onclick();
	    function search()   {
            if (form["applyTime.dateBegin"].value.length!=form["applyTime.dateEnd"].value.length) {
	    		alert("日期区间需要填写完整！");
	    		return;
	    	}
	    	if (form["applyTime.timeEnd"].value.length!=form["applyTime.timeEnd"].value.length) {
	    		alert("时间区间填写不完整或格式错误！");
	    		return;
	    	}
	    	if(form['applyTime.timeBegin'].value!=""&&!isShortTime(form['applyTime.timeBegin'].value)){
	    	    alert("开始时间填写不完整或格式错误！");
	    		return;
	    	}
	    	if(form['applyTime.timeEnd'].value!=""&&!isShortTime(form['applyTime.timeEnd'].value)){
	    	    alert("结束时间填写不完整或格式错误！");
	    		return;
	    	}
	    	if(form['roomApply.applyTime.cycleCount'].value!="" && !/^\d+$/.test(form['roomApply.applyTime.cycleCount'].value)){
	    	    alert("时间周期格式错误！");
	    		return;
	    	}
            if(form['applyTime.dateBegin'].value>form['applyTime.dateEnd'].value){
               alert("起始结束日期不对");return;
            }
            if(""!=form['applyTime.timeBegin'].value&&form['applyTime.timeBegin'].value>=form['applyTime.timeEnd'].value){
               alert("起始结束时间不对");return;
            }
		    form.submit();
	    }
	    function roomPriceCatalogue(){
	       window.open("roomPriceCatalogue.do?method=index");
	    }
 	</script>
</body>
<#include "/templates/foot.ftl"/>