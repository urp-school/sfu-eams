<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<BODY LEFTMARGIN="0">
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','论文指导教师统计',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>
<table align="center" width="100%">
	<tr>
		<td>
			<table class="listTable" align="center" width="100%">
		<form name="statForm" method="post" target="displayFrame" action="" onsubmit="return false;">
		<tr>
			<td colspan="4" class="darkColumn" align="center">统计条件</td>
		</tr>
		<tr>
			<td class="grayStyle" width="20%">学生类别</td>
			<td width="30%">
				<@htm.i18nSelect datas=stdTypes selected="" name="stdTypeId"  style="width:100%"/>
			</td>
			<td class="grayStyle" width="20%" id="f_displayName"><font color="red">*</font>显示界别数</td>
			<td><input type="text" id="displayName" name="displayName" maxlength="20" value="3" style="width:100%" maxlength="5"/></td>
		</tr>
		<tr>
			<td colspan="4" align="center" class="darkColumn"><button name="buttonName" style="buttonStyle" onclick="stat(this.form)">统计</button></td>
		</tr>
		</form>
		</table>
		</td>
	</tr>
	<tr>
		<td><iframe name="displayFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe></td>
	</tr>
	</table>
<script>
	function stat(form){
		var a_fields = {
			'displayName':{'l':'显示界别数', 'r':true, 't':'f_displayName', 'f':'unsigned'}
		};
		
		var v = new validator(form, a_fields, null);
		if (v.exec()) {
			form.action="thesisStatistic.do?method=thesisAndTeachers";
			form.submit();
		}
	}
</script>
</body>
<#include "/templates/foot.ftl"/>