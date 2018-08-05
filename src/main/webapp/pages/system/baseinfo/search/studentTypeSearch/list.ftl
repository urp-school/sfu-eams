<#include "/templates/head.ftl"/>
<BODY>
	<table id="bar"></table>
	<@table.table id="listTable" sortable="true" width="100%">
  		<@table.thead>
  			<@table.selectAllTd id="studentTypeId"/>
		    <@table.sortTd width="10%" name="attr.code" id="studentType.code"/>
		    <@table.sortTd width="20%" name="attr.infoname" id="studentType.name"/>
		    <@table.sortTd width="20%" name="attr.parentStudentType" id="studentType.superType"/>
		    <@table.sortTd width="10%" name="common.abbreviation" id="studentType.abbreviation"/>
		    <@table.sortTd width="20%" name="attr.engName" id="studentType.engName"/>
		    <@table.sortTd width="15%" name="attr.modifyAt" id="studentType.modifyAt"/>
	  	</@>
	  	<@table.tbody datas=studentTypes;stdType>
		    <@table.selectTd id="studentTypeId"	value=stdType.id/>
		    <td>${stdType.code?if_exists}</td>
		    <td><a href="studentTypeSearch.do?method=info&studentTypeId=${stdType.id}">${stdType.name?if_exists}</a></td>
		    <td><@i18nName stdType.superType?if_exists/></td>
		    <td>${stdType.abbreviation?if_exists}</td>
		    <td>${stdType.engName?if_exists}</td>
		    <td>${(stdType.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>
	  	</@>
	</@>
	<@htm.actionForm name="actionForm" action="studentTypeSearch.do" entity="studentType">
	    <input type="hidden" name="keys" value="code,name,engName,superType.name,abbreviation,description,createAt,modifyAt,remark,state,dateEstablished,isBase"/>
	    <input type="hidden" name="titles" value="代码,名称,英文名,上级类别名称,简称,简介,创建日期,修改日期,备注,是否使用,设立年月,是否基础学生类别"/>
	</@>
	<script>
		var bar = new ToolBar("bar", "学生类别列表", null, true ,true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("<@msg.message key="action.look"/>", "info()");
		bar.addItem("<@msg.message key="action.export"/>", "exportList()");
	</script>
</body>
<#include "/templates/foot.ftl"/>