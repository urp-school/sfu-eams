<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
<table id="bar"></table>
<table width="100%" class="frameTable">
	<tr>
		<form method="post" action="roomApplyApprovedSearch.do?method=search" name="searchRoomApplyApprovedForm" target="contentFrame" onsubmit="return false;">
		<td class="frameTable_view" style="width:168px;">
			<#include "searchTable.ftl"/>
			<br><br><br><br><br><br><br><br><br><br><br><br>
		</td>
		</form>
		<td valign="top">
	     	<iframe name="contentFrame" id="contentFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
		</td>
	</tr>
</table>
 	<script>
        var bar=  new ToolBar("bar","物管审核已通过的教室申请",null,true,true);
        bar.addHelp("<@msg.message key="action.help"/>");
 		var form = document.searchRoomApplyApprovedForm;

 		document.getElementById("searchRoom").onclick();
	    function search()   {
	    	if (form["dateBegin"].value.length!=form["dateEnd"].value.length) {
	    		alert("日期区间需要填写完整！");
	    		return;
	    	}
	    	if (form["timeEnd"].value.length!=form["timeEnd"].value.length) {
	    		alert("时间区间填写不完整或格式错误！");
	    		return;
	    	}
	    	if(form['timeBegin'].value!=""&&!isShortTime(form['timeBegin'].value)){
	    	    alert("开始时间填写不完整或格式错误！");
	    		return;
	    	}
	    	if(form['timeEnd'].value!=""&&!isShortTime(form['timeEnd'].value)){
	    	    alert("结束时间填写不完整或格式错误！");
	    		return;
	    	}
            if(form['dateBegin'].value>form['dateEnd'].value){
               alert("起始结束日期不对");return;
            }
            if(""!=form['timeBegin'].value&&form['timeBegin'].value>=form['timeEnd'].value){
               alert("起始结束时间不对");return;
            }
		    form.submit();
	    }
 	</script>
</body>
<#include "/templates/foot.ftl"/>