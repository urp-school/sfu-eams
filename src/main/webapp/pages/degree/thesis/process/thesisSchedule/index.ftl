<#include "/templates/head.ftl"/>
<BODY topmargin=0 leftmargin=0>	
  <table id="backBar" width="100%"></table>
	<script>
	   var bar = new ToolBar('backBar','<@msg.message key="degree.process.manage"/>',null,true,true);
	   bar.setMessage('<@getMessage/>');
	   bar.addHelp("<@msg.message key="action.help"/>");
   </script>
	<table width="100%" class="frameTable">
		<tr>
			<td width="18%" class="frameTable_view" valign="top">
				<table width="100%">
					<form name="searchForm" method="post" target="displayFrame" action="" onSubmit="return false;">
				    <tr>
				      <td colspan="2" class="infoTitle" align="left" valign="bottom">
				       <img src="${static_base}/images/action/info.gif" align="top"/>
				          <B>进度查询</B>
				      </td>
				    </tr>
				    <tr>
				      <td colspan="2" style="font-size:0px">
				          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
				      </td>
				    </tr>
					<tr>
						<td width="40%">学生类别:</td>
						<td><@htm.i18nSelect datas=stdTypeList selected="" name="schedule.studentType.id" style="width:100px">
								<option value=""><@bean.message key="common.all"/></option>
							</@>
						</td>
					</tr>
					<tr>
						<td>所在年级:</td>
						<td><input name="schedule.enrollYear" value="" maxlength="7" style="width:100px"/></td>
					</tr>
					<tr>
						<td>学制:</td>
						<td><input name="schedule.studyLength" value="" maxlength="3" style="width:100px"/></td>
					</tr>
					<tr heigth="50px">
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
	action="thesisSchedule.do";
	function search(pageNo,pageSize,orderBy){
	    form.action=action+"?method=search";
	    goToPage(form,pageNo,pageSize,orderBy);
	}
    search(1);
	</script>
</body>
<#include "/templates/foot.ftl"/>
