<#include "/templates/head.ftl"/>
<BODY topmargin=0 leftmargin=0>	
  <table id="backBar" width="100%"></table>
	<script>
	   var bar = new ToolBar('backBar','学生科研成果获奖管理',null,true,true);
	   bar.setMessage('<@getMessage/>');
	   bar.addHelp("<@msg.message key="action.help"/>");
   </script>
	<table width="100%" class="frameTable">
		<tr>
			<td width="18%" class="frameTable_view" valign="top">
				<table width="100%">
					<form name="searchForm" method="post" target="displayFrame" action="" onsubmit="return false;">
				    <tr>
				      <td colspan="2" class="infoTitle" align="left" valign="bottom">
				       <img src="${static_base}/images/action/info.gif" align="top"/>
				          <B>获奖查询</B>
				      </td>
				    <tr>
				      <td colspan="2" style="font-size:0px">
				          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
				      </td>
				    </tr>
					<tr>
						<td width="40%"><@msg.message key="attr.stdNo"/>：</td>
						<td><input name="studyAward.student.code" value="" style="width:100px"/></td>
					</tr>
					<tr>
						<td><@msg.message key="attr.personName"/>：</td>
						<td><input name="studyAward.student.name" value="" style="width:100px"/></td>
					</tr>
					<tr>
						<td>成果名称：</td>
						<td><input name="studyAward.name" value="" style="width:100px"/></td>
					</tr>
					<tr>
						<td><@msg.message key="entity.studentType"/>：</td>
						<td><@htm.i18nSelect datas=stdTypeList selected="" name="studyAward.student.type.id" style="width:100px">
								<option value=""><@bean.message key="common.all"/></option>
							</@>
						</td>
					</tr>
					<tr>
						<td>院系部门：</td>
						<td><@htm.i18nSelect datas=departmentList selected="" name="studyAward.student.department.id" style="width:100px">
						     <option value=""><@bean.message key="common.all"/></option>
						     </@>
						</td>
					</tr>
					<tr>
						<td>科研类别：</td>
						<td>
							<select name="productType" style="width:100px">
								<option value="studyThesis">论文</option>
								<option value="literature">著作</option>
								<option value="project">项目</option>
							</select>
						</td>
					</tr>
					<tr height="50px">
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
	var action="studyAward.do";
	function search(pageNo,pageSize,orderBy){
	    form.action=action+"?method=search";
	    goToPage(form,pageNo,pageSize,orderBy);
	}
    search(1);
	</script>
</body>
<#include "/templates/foot.ftl"/>
