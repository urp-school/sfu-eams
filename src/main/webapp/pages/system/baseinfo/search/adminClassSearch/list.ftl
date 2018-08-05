<#include "/templates/head.ftl"/>
<BODY>
	<table id="bar"></table>
	<#include "adminClassList.ftl"/>
	<@htm.actionForm name="actionForm" action="adminClassSearch.do" entity="adminClass">
	    <input type="hidden" name="keys" value="code,name,stdType.name,enrollYear,department.name,speciality.name,aspect.name,eduLength,planStdCount,actualStdCount,stdCount,instructor,createAt,modifyAt"/>
	    <#assign titles><@msg.message key="attr.code"/>,<@msg.message key="attr.name"/>,<@msg.message key="entity.studentType"/>,<@msg.message key="attr.enrollTurn"/>,<@msg.message key="entity.department"/>,<@msg.message key="entity.speciality"/>,<@msg.message key="entity.specialityAspect"/>,学制,计划人数,实际在校人数,学籍有效人数,辅导员,<@msg.message key="attr.createAt"/>,<@msg.message key="attr.modifyAt"/></#assign>
	    <input type="hidden" name="titles" value="${titles}"/>
	</@>
	<script>
		var bar = new ToolBar("bar", "班级信息列表", null, true ,true);
		bar.setMessage('<@getMessage/>');
		var exportMenu = bar.addMenu("导出", "exportData('excel')");
	    exportMenu.addItem("文本导出","exportData('txt')");
		bar.addItem("<@msg.message key="action.look"/>", "info()");
		
		function exportData(format) {
		    if (null == format) {
		      	format = "excel";
		    }
		    addInput(form,"format",format);
		    exportList();
		}
		
		function displayInfo(id) {
		  form.action = "adminClassSearch.do?method=info&adminClassId=" + id;
		  form.submit();
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>