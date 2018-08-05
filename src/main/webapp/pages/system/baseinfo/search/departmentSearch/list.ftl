<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
  	<@table.table width="100%" id="listTable" sortable="true">
	    <@table.thead>
	       <@table.selectAllTd id="departmentId"/>
	       <@table.sortTd width="5%" name="attr.code" id="department.code"/>
	       <@table.sortTd width="20%" name="attr.infoname" id="department.name"/>
	       <@table.sortTd width="20%" name="attr.engName" id="department.engName"/>
	       <@table.sortTd width="10%" name="common.abbreviation" id="department.abbreviation"/>
	       <@table.sortTd width="10%" name="department.isTeaching" id="department.isTeaching"/>
	       <@table.sortTd width="10%" name="department.isCollege" id="department.isCollege"/>
	    </@>
	    <@table.tbody datas=departments;department>
	       <@table.selectTd id="departmentId" value=department.id/>
	       <td>${department.code?if_exists}</td>
	       <td><a href="departmentSearch.do?method=info&departmentId=${department.id}">${department.name?if_exists}</a></td>
	       <td>${department.engName?if_exists}</td>
	       <td>${department.abbreviation?if_exists}</td>
	       <td><#if department.isTeaching?if_exists == true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if></td>
	       <td><#if department.isCollege?if_exists == true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if></td>
		</@>
  	</@>
	<@htm.actionForm name="actionForm" action="departmentSearch.do" entity="department">
		<input type="hidden" name="keys" value="code,name,engName,abbreviation,description,createAt,modifyAt,remark,state,dateEstablished,isCollege,isTeaching"/>
		<#assign titles><@msg.message key="attr.code"/>,<@msg.message key="attr.name"/>,<@msg.message key="attr.engName"/>,<@msg.message key="attr.abbreviation"/>,<@msg.message key="attr.description"/>,<@msg.message key="attr.createAt"/>,<@msg.message key="attr.modifyAt"/>,<@msg.message key="attr.remark"/>,<@msg.message key="attr.state"/>,<@msg.message key="attr.dateEstablished"/>,<@msg.message key="department.isCollege"/>,<@msg.message key="department.isTeaching"/></#assign>		
		<input type="hidden" name="titles" value="${titles}"/>
		<input type="hidden" name="format" value=""/>
	</@>
	<script>
		var bar = new ToolBar("bar", "部门信息列表", null, true, true);
		bar.setMessage('<@getMessage/>');
		var exportMenu = bar.addMenu("导出", "exportData('excel')");
	    exportMenu.addItem("文本导出","exportData('txt')");
		bar.addItem("<@msg.message key="action.look"/>", "info()");
	    
	    function exportData(format) {
	    	if(confirm("是否要导出查询出的所有结果？")) {
	    		form["format"].value = (format == null || format == "") ? "excel" : format;
	    		exportList();
	    	}
	    }
	</script>
</body>
<#include "/templates/foot.ftl"/>