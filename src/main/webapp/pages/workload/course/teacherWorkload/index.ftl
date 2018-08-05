<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','教师工作量查询',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>
  <table width="100%" class="frameTable">
	<tr>
		<td style="width:20%" class="frameTable_view" valign="top">
			<table width="100%" class="searchTable">
				<form name="teacherQueryForm" method="post" target="teacherQueryFrame" action="" onsubmit="return false;">
				<tr><td colspan="2" align="center"><@msg.message key="textEvaluation.selectCondition"/></td>
				</tr>
				<tr><td><@msg.message key="entity.studentType"/></td>
				<td>
					<select id="stdType" name="workload.studentType.id" style="width:100px;">
						<option value=""><@msg.message key="common.all"/></option>
					</select>
				</td>
				</tr>
				<tr>
					<td><@msg.message key="attr.year2year"/></td>
					<td><select id="year" name="workload.teachCalendar.year" style="width:100px;">
							<option value=""><@msg.message key="common.all"/></option>
					</select>
					</td>
				</tr>
				<tr>
					<td><@msg.message key="attr.term"/>
					</td>
					<td>
					<select id="term" name="workload.teachCalendar.term" style="width:100px;">
						<option value=""><@msg.message key="common.all"/></option>
					</select>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<button name="button1" onClick="search()" class="buttonStyle"><@msg.message key="action.query"/></button>
					</td>
				</tr>
				</from>
				</table>
				<#assign stdTypeNullable=true>
				<#assign yearNullable=true>
				<#assign termNullable=true>
				<#assign stdTypeDefaultFirst=false>
				<#assign stdTypeList=stdTypeList?sort_by("code")>
				<#include "/templates/calendarSelect.ftl"/>
		</td>
		<td valign="top">
			<iframe name="teacherQueryFrame" width="100%" frameborder="0" scrolling="no"></iframe>
		</td>
	</tr>
	</table>
	<script language="javascript">
	 var form =document.teacherQueryForm;
	 var action="teacherWorkload.do";
 	 function search(){
 		form.action=action+"?method=search&orderBy=workload.teachCalendar.start desc";
 		form.submit();
 	}
 	search();
 </script>
</body>
<#include "/templates/foot.ftl"/>
	