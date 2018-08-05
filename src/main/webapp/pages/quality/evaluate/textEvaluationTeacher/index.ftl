<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','<@msg.message key="textEvaluation.teacher.title"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>
	<table width="100%" border="0" class="frameTable">
		<tr>
			<td valign="top" class="frameTable_view" width="20%">
				<table width="100%" class="searchTable">
				<form name="teacherForm" method="post" target="teacherFrame" action="" onsubmit="return false;">
				<input type="hidden" name="orderBy" value="textEvaluation.calendar.start desc"/>
					<tr>
						<td><@msg.message key="entity.studentType"/></td>
						<td><select id="stdType" name="textEvaluation.std.type.id" style="width:100px;">
							</select>
						</td>
					</tr>
					<tr><td><@msg.message key="attr.year2year"/></td>
						<td><select id="year" name="textEvaluation.calendar.year" style="width:100px;">
							</select>
						</td>
					</tr>
					<tr>
						<td><@msg.message key="attr.term"/></td>
						<td><select id="term" name="textEvaluation.calendar.term" style="width:100px;">
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="6" align="center">
							<button name="buttonSubmit" onclick="search()" ><@msg.message key="system.button.query"/></button>
						</td>
					</tr>
					</form>
				</table>
				<#assign stdTypeNullable=true>
				<#assign yearNullable=true>
				<#assign termNullable=true>
				<#include "/templates/calendarSelect.ftl"/>
			</td>
			<td valign="top">
				<iframe name="teacherFrame" width="100%" frameborder="0" scrolling="no"></iframe>
			</td>
		</tr>	
	</table>
	 <script language="javascript">
	 	var form = document.teacherForm;
		function search(){
			form.action="textEvaluationTeacher.do?method=search";
			form.submit();
		}
		search();
	</script>
</body>
<#include "/templates/foot.ftl"/>