<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<#include "/pages/course/grade/personGrade/stdGrade.ftl"/>
	<script>
		var bar = new ToolBar("bar", "${stdGP.std.code}学生的信息", null, true, true);
		bar.addPrint("<@msg.message key="action.print"/>");
		bar.addBack("<@msg.message key="action.back"/>");
	</script>
<#include "/templates/foot.ftl"/>