<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<@table.table id="gradeSwitchSettingId" sortable="true" width="100%">
		<@table.thead>
			<@table.selectAllTd id="gradeInputSwitchId"/>
			<@table.sortTd name="entity.studentType" id="gradeInputSwitch.calendar.studentType.name"/>
			<@table.sortTd name="attr.year2year" id="gradeInputSwitch.calendar.year"/>
			<@table.sortTd name="attr.term" id="gradeInputSwitch.calendar.term"/>
			<@table.sortTd text="开始时间" id="gradeInputSwitch.startAt"/>
			<@table.sortTd text="结束时间" id="gradeInputSwitch.endAt"/>
			<@table.sortTd text="开关状态" id="gradeInputSwitch.isOpen"/>
		</@>
		<@table.tbody datas=gradeInputSwitchs; gradeInputSwitch>
			<@table.selectTd id="gradeInputSwitchId" value=gradeInputSwitch.id/>
			<td><@i18nName gradeInputSwitch.calendar.studentType/></td>
			<td><a href="gradeInputSwitch.do?method=info&gradeInputSwitchId=${gradeInputSwitch.id}">${gradeInputSwitch.calendar.year}</a></td>
			<td>${gradeInputSwitch.calendar.term}</td>
			<td>${gradeInputSwitch.startAt?string("yyyy-MM-dd HH:mm")}</td>
			<td>${gradeInputSwitch.endAt?string("yyyy-MM-dd HH:mm")}</td>
			<td>${gradeInputSwitch.isOpen?string("开放", "关闭")}</td>
		</@>
	</@>
	<@htm.actionForm name="actionForm" action="gradeInputSwitch.do" entity="gradeInputSwitch"/>
	<script>
		var bar = new ToolBar("bar", "录入开关设置查询结果", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("<@msg.message key="action.new"/>", "add()");
		bar.addItem("<@msg.message key="action.delete"/>", "remove()");
		bar.addItem("<@msg.message key="action.edit"/>", "edit()");
		bar.addItem("<@msg.message key="action.info"/>", "info()");
		
		function remove() {
	        var ids = getSelectIds("gradeInputSwitchId");
	        if(ids == "" || ids == null) {
	        	alert("请选择要删除的记录！");
	        	return;
	        }
	        if (confirm("是否要删除记录?")) {
		    	form.action = "gradeInputSwitch.do?method=remove";
		        addInput(form, "gradeInputSwitchId", ids, "hidden");
		    	form.target = "";
		    	form.submit();
	    	}
	    }
	</script>
</body>
<#include "/templates/foot.ftl"/>