<#include "/templates/head.ftl"/>
<body>
	<table id="noTeachCalendarId"></table>
	<p style="color:blue">请先添加教学日历！</p>
	<p style="color:seagreen"><a href="javascript:settingTeachCalendar()" style="text-decoration:underline">设置教学日历</a>位置是在“系统管理”->“时间设置”->“教学日历”。</p>
	<script>
		var bar = new ToolBar("noTeachCalendarId", "教学日历信息缺失", null, true ,true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("设置教学日历", "settingTeachCalendar()");
		$("error").innerHTML = "请先添加教学日历";
		
		function settingTeachCalendar() {
			location = "calendar.do?method=index";
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>