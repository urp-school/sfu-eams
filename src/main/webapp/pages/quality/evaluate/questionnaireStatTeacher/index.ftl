<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','<@msg.message key="quality.questionnaireStatTeacher.title"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>
	<table width="100%" class="frameTable">
		<tr>
			<td  class="frameTable_view" valign="top" width="20%">
				<table width="100%" class="searchTable">
					<form name="teacherForm" method="post" target="teacherFrame" action="" onsubmit="return false;">
					<tr>
						<td colspan="2" align="center"><@bean.message key="textEvaluation.selectCondition"/></td>
					</tr>
					<tr>
						<td><@bean.message key="entity.studentType"/></td>
						<td>
							<select id="stdType" name="questionnaireStat.stdType.id" style="width:100px;">
							</select>
						</td>
					</tr>
					<tr>
						<td><@bean.message key="attr.year2year"/></td>
						<td><select id="year" name="questionnaireStat.calendar.year" style="width:100px;">
							</select>
						</td>
					</tr>
					<tr>
						<td><@bean.message key="attr.term"/>
						</td>
						<td><select id="term" name="questionnaireStat.calendar.term" style="width:100px;">
							</select>
						</td>
					</tr>
					<tr>
						<td align="center" colspan="2"><button name="button1" onClick="search()" class="buttonStyle" ><@bean.message key="action.query"/></button>
						</td>
					</tr>
						<input type="hidden" name="questionnaireStat.teacher.id" value="${teacher.id}">
					</form>
				</table>
				<#assign yearNullable=true>
				<#assign termNullable=true>
				<#include "/templates/calendarSelect.ftl"/>
			</td>
			<td valign="top">
				<iframe name="teacherFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
			</td>
		</tr>	
	</table>
<script language="javascript">
	var form= document.teacherForm;
	function search(){
	form.action="questionnaireStatTeacher.do?method=search";
	form.submit();
	}
	search();
</script>
</body>
<#include "/templates/foot.ftl"/>