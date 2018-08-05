<#include "/templates/head.ftl"/>
<BODY topmargin=0 leftmargin=0>	
  <table id="backBar" width="100%"></table>
	<script>
	   var bar = new ToolBar('backBar','注册权限维护',null,true,true);
	   bar.setMessage('<@getMessage/>');
	   bar.addHelp("<@msg.message key="action.help"/>");
   </script>
  <form name="searchForm" method="post" target="displayFrame" action="registerUserGroup.do?method=index" onsubmit="return false;">
	<table width="100%" class="frameTable">
		<tr>
			<td style="width:160px" class="frameTable_view" valign="top">
				<table width="100%" class="searchTable">
				    <tr>
				      <td colspan="2" class="infoTitle" align="left" valign="bottom">
				       <img src="${static_base}/images/action/info.gif" align="top"/>
				          <B>注册权限组查询条件</B>
				      </td>
				    <tr>
				      <td colspan="2" style="font-size:0px">
				          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
				      </td>
				    </tr>
					<tr>
						<td style="width:50%">用户组名称</td>
						<td><input name="registerUserGroup.name" value="" style="width:80px"/></td>
					</tr>
					<tr>
						<td align="center" colspan="2">
							<button onClick="search(1)"><@msg.message key="system.button.query"/></button>
						</td>
					</tr>
				</table>
				</form>
			</td>
			<td valign="top">
				<iframe name="displayFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
			</td>
		</tr>
	</table>
	<script language="javascript">
		var form = document.searchForm;
		var action="registerUserGroup.do";
		function search(pageNo,pageSize,orderBy){
		    form.action=action+"?method=search";
		    goToPage(form,pageNo,pageSize,orderBy);
		}
	    search(1);
	</script>
</body>
<#include "/templates/foot.ftl"/>
