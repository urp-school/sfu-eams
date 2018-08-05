<#include "/templates/head.ftl"/>
<#assign stdTypeNullable=true/>
<body>
	<table id="bar"></table>
	<table class="frameTable">
		<tr valign="top">
			<td class="frameTable_view" width="168px">
				<form method="post" action="" name="tutorStdForm" onsubmit="return false;">
					<#include "/pages/components/initAspectSelectData.ftl"/>
					<#include "/pages/components/stdSearchTable.ftl"/>
				</form>
			</td>
			<td>
				<iframe name="displayFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
			</td>
		</tr>
	</table>
	<script>
		var bar = new ToolBar("bar", "我的学生", null, true, true);
		bar.addItem("<@msg.message key="action.query"/>", "search()", "find.gif");

		var form = document.tutorStdForm;
		
		<#--当界面第一次进入时访问-->		
		search();

		<#--学生查询-->
		function search() {
			form.action = "tutorStd.do?method=search";
			form.target = "displayFrame";
			form.submit();
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>