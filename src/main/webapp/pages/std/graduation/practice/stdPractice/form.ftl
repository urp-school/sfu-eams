<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
	<table id="bar"></table>
	<#include "formTable.ftl"/>
	<script>
		var bar = new ToolBar("bar", "修改学生毕业实习", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("<@msg.message key="action.save"/>","save()");
	  bar.addBack("<@msg.message key="action.back"/>"); 
	</script>
</body>
<#include "/templates/foot.ftl"/>
