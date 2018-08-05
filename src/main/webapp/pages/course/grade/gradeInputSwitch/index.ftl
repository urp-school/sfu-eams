<#include "/templates/head.ftl"/>
<body>
	<table id="bar" width="100%"></table>
	<table width="100%" class="frameTable">
		<tr valign="top">
		<form method="post" action="" name="actionForm">
			<td width="20%" class="frameTable_view"><#include "searchForm.ftl"/></td>
		</form>
			<td><iframe src="#" id="iframeId" name="pageIFrame" scrolling="no" marginwidth="0" marginheight="0" frameborder="0" height="100%" width="100%"></iframe></td>
		</tr>
	</table>
	<script>
		var bar = new ToolBar("bar", "成绩录入开关设置", null, true, true);
		bar.setMessage('<@getMessage/>');
		
		var form = document.actionForm;
		function search() {
			form.action = "gradeInputSwitch.do?method=search";
			form.target = "pageIFrame";
			form.submit();
		}
		search();
	</script>
</body>
<#include "/templates/foot.ftl"/>