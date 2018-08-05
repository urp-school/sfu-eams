<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="statBar"></table>
	<table class="infoTable" align="center" style="width:50%">
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
   var bar = new ToolBar("statBar","成绩开关",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack("<@msg.message key="action.back"/>");
   $('message').innerHTML='成绩录入和修改还没有开放';
</script>
</body>
<#include "/templates/foot.ftl"/>