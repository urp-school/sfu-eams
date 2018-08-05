<#include "/templates/head.ftl"/>
<BODY>
	<table id="bar"></table>
  <@table.table width="100%"  id="listTable" sortable="true">
    <@table.thead>
       <@table.selectAllTd id="specialityId"/>
       <@table.sortTd width="10%" name="attr.code" id="speciality.code"/>
       <@table.sortTd width="20%" name="attr.infoname" id="speciality.name"/>
       <@table.sortTd width="15%" name="entity.studentType" id="speciality.stdType.name"/>    
       <@table.sortTd width="20%" name="entity.department" id="speciality.department.name"/>
       <@table.sortTd width="20%" name="attr.engName" id="speciality.engName"/>
    </@>
    <@table.tbody datas=specialities;speciality>
       <@table.selectTd id="specialityId" value=speciality.id/>
       <td>&nbsp;${speciality.code}</td>
       <td><a href="specialitySearch.do?method=info&speciality.id=${speciality.id}">&nbsp;<@i18nName speciality/></a></td>
       <td>&nbsp;<@i18nName speciality.stdType?if_exists/></td>
       <td>&nbsp;<@i18nName speciality.department/></td>
       <td>&nbsp;${speciality.engName?if_exists}</td>
    </@>
  </@>
  	<form name="actionForm" action="" method="post">
  		<input type="hidden" name="keys" value="code,name,engName,abbreviation,description,createAt,modifyAt,remark,state,dateEstablished,maxPeople,department.name,stdType.name,is2ndSpeciality,subjectCategory.name"/>
  		<input type="hidden" name="titles" value="<@msg.message key="attr.code"/>,<@msg.message key="attr.name"/>,<@msg.message key="attr.engName"/>,<@msg.message key="attr.abbreviation"/>,<@msg.message key="attr.description"/>,<@msg.message key="attr.createAt"/>,<@msg.message key="attr.modifyAt"/>,<@msg.message key="attr.remark"/>,<@msg.message key="attr.state"/>,<@msg.message key="attr.dateEstablished"/>,<@msg.message key="speciality.maxPeople" />,<@msg.message key="entity.department"/>,<@msg.message key="entity.studentType"/>,<@msg.message key="entity.secondSpeciality"/>,学科门类"/>
  	</form>
	<script>
		var bar = new ToolBar("bar", "专业信息列表", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("<@msg.message key="action.look"/>", "info()");
		bar.addItem("<@msg.message key="action.export"/>", "exportList()");
		
		var form = document.actionForm;
		function info() {
			var specialityId = getSelectId("specialityId");
			if (specialityId == null || specialityId == "" || isMultiId(specialityId)) {
				alert("请选择一个进行操作。");
				return;
			}
			form.action = "specialitySearch.do?method=info";
			addInput(form, "speciality.id", specialityId, "hidden");
			form.submit();
		}
		
		function exportList() {
			form.action = "specialitySearch.do?method=export";
			addParamsInput(form, queryStr);
			form.submit();
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>
