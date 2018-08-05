<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<table class="infoTable" align="center">
        <tr>
          	<td class="title" width="30%">学年学期:</td>
		  	<td><@i18nName gradeInputSwitch.calendar.studentType/> ${gradeInputSwitch.calendar.year} ${gradeInputSwitch.calendar.term}</td>
        </tr>
	 	<tr>
	 		<td class="title">是否开放:</td>
	 		<td>${(gradeInputSwitch.isOpen)?string("开放","关闭")}</td>
	 	</tr>
	 	<tr>
	 		<td class="title">开始时间:</td>
	 		<td> ${(gradeInputSwitch.startAt?string("yyyy-MM-dd HH:mm"))?default("")}</td>
	 	</tr>
	 	<tr>
	 		<td class="title">结束时间:</td>
	 		<td>${(gradeInputSwitch.endAt?string("yyyy-MM-dd HH:mm"))?default("")}</td>
	 	</tr>
	</table>
	<script>
		var bar = new ToolBar("bar", "详细查看", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addBack();
	</script>
</body>
<#include "/templates/foot.ftl"/>