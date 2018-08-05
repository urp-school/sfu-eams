<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','评教查询开关',null,true,true);
   bar.setMessage('<@getMessage/>');
</script>
<table   width="100%"  class="frameTable"> 
		<tr>
			<td  style="width:160px" class="frameTable_view" valign="top">
				<table width="100%" class="searchTable">
					<form name="searchForm" method="post" target="displayFrame" action="" onsubmit="return false;">
				    <tr>
				      <td colspan="2" class="infoTitle" align="left" valign="bottom">
				       <img src="${static_base}/images/action/info.gif" align="top"/>
				          <B>评教开关查询</B>
				      </td>
				    <tr>
				      <td  colspan="2" style="font-size:0px">
				          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
				      </td>
				    </tr>
					<tr>
						<td  style="width:35%"><@msg.message key="entity.studentType"/></td>
						<td><@htm.i18nSelect datas=stdTypeList selected="" name="switch.teachCalendar.studentType.id" style="width:100%">
							<option value="">全部</option>
							</@>
						</td>
					</tr>
					<tr>
						<td  style="width:35%">是否开放</td>
						<td><select name="switch.isOpen" style="width:100%">
								<option value="true">开放</option>
								<option value="false">关闭</option>
						    </select>
						</td>
					</tr>
					<tr>
						<td align="center" colspan="2">
							<button onClick="search(1)"><@bean.message key="system.button.query"/></button>
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
	var action="evaluateQuerySwitch.do";
	function search(pageNo,pageSize,orderBy){
	    form.action=action+"?method=search";
	    goToPage(form,pageNo,pageSize,orderBy);
	}
    search(1);
	</script>
</body>
<#include "/templates/foot.ftl"/>