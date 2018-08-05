<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
	<table id="bar"></table>
	<table width="100%" class="frameTable">
		<form name="selectedForm" method="post" action="" target="displayFrame" onsubmit="return false;"></form>
		<tr>
			<td class="frameTable_view" width="20%" style="font-size:10pt">
				<table width="100%" id ="menuTable" style="font-size:10pt">
					<tr class="infoTitle"  valign="top" width="20%" style="font-size:10pt">
						<td class="infoTitle" align="left" valign="bottom">
							<img src="${static_base}/images/action/info.gif" align="top"/>
							<b>教室申请菜单</b>
						</td>
					</tr>
					<tr>
						<td style="font-size:0px">
							<img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
						</td>
					</tr>
					<tr>
				        <td class="padding" id="item1" onclick="selectFrame(this, 'displayFrame', 'applyNotice')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
							&nbsp;&nbsp;<image src="${static_base}/images/action/list.gif" align="bottom"/>申请须知
				        </td>
			        </tr>
			        <tr>
				        <td class="padding" id="item1" onclick="selectFrame(this, 'displayFrame', 'pricesReview')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
							&nbsp;&nbsp;<image src="${static_base}/images/action/list.gif" align="bottom"/>价目浏览
				        </td>
			        </tr>
			        <tr>
				        <td class="padding" id="item2" onclick="selectFrame(this, 'displayFrame',  'myApplied')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
							&nbsp;&nbsp;<image src="${static_base}/images/action/list.gif" align="bottom"/>我已申请
				        </td>
			        </tr>
			        <tr>
				        <td class="padding" onclick="selectFrame(this, 'displayFrame', 'addApply')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
							&nbsp;&nbsp;<image src="${static_base}/images/action/list.gif" align="bottom"/>教室申请
				        </td>
			        </tr>
			        <tr>
				        <td class="padding" onclick="selectFrame(this, 'displayFrame', 'freeRoomHome')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
							&nbsp;&nbsp;<image src="${static_base}/images/action/list.gif" align="bottom"/>查看空闲教室
				        </td>
			        </tr>
			        <tr height="80%">
				        <td>&nbsp;&nbsp;</td>
			        </tr>
				</table>
			</td>
			<td valign="top">
		     	<iframe name="displayFrame" src="#" marginwidth="0" marginheight="0"  width="100%" frameborder="0" scrolling="no"></iframe>
			</td>
		</tr>
	</table>
 	<script>
		var bar = new ToolBar("bar", "教室申请管理", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addHelp("<@msg.message key="action.help"/>");
		
	    document.getElementById("item${RequestParameters['selectIndex']?default('1')}").onclick(); 
	    function selectFrame(td, locate, index)
	    {
	    	clearSelected(menuTable,td);
		    setSelectedRow(menuTable,td);
		    
		    var form = document.selectedForm;
		    form.action = "roomApply.do?method=" + index;
		    form.target = locate == "" || null ? "" : locate;
		    form.submit();
	    }
 	</script>
</body>
<#include "/templates/foot.ftl"/>