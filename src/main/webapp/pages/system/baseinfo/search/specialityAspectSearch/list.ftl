<#include "/templates/head.ftl"/>
<BODY>
	<table id="bar"></table>
  <@table.table width="100%"  id="listTable" sortable="true">
    <@table.thead>
       <@table.selectAllTd id="specialityAspectId"/>
       <@table.sortTd width="10%" name="attr.code" id="specialityAspect.code"/>
       <@table.sortTd width="30%" name="attr.infoname" id="specialityAspect.name"/>
       <@table.sortTd width="10%" name="entity.studentType" id="specialityAspect.speciality.stdType.name"/>
       <@table.sortTd width="20%" name="entity.speciality" id="specialityAspect.speciality.name"/>
       <@table.sortTd width="30%" name="entity.department" id="specialityAspect.speciality.department.name"/>
    </@>
    <@table.tbody datas =specialityAspects;specialityAspect>
       <@table.selectTd id="specialityAspectId" value=specialityAspect.id/>
       <td>&nbsp;${specialityAspect.code}</td>
       <td><a href="specialityAspectSearch.do?method=info&specialityAspect.id=${specialityAspect.id}">&nbsp;<@i18nName specialityAspect/></a></td>
       <td>&nbsp;<@i18nName specialityAspect.speciality.stdType?if_exists/></td>
       <td>&nbsp;<@i18nName specialityAspect.speciality/></td>
       <td>&nbsp;<@i18nName specialityAspect.speciality.department/></td>
    </@>
  </@>
  	<form name="actionForm" action="" method="post">
  		<input type="hidden" name="keys" value="code,name,engName,abbreviation,description,createAt,modifyAt,remark,state,dateEstablished,speciality.name,maxPeople"/>
  		<input type="hidden" name="titles" value="代码,名称,英文名,简称,简介,创建日期,修改日期,备注,是否使用,设立年月,<@msg.message key="entity.speciality"/>,最大人数"/>
  	</form>
	<script>
		var bar = new ToolBar("bar", "专业方向信息列表", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("<@msg.message key="action.look"/>", "info()");
		bar.addItem("<@msg.message key="action.export"/>", "exportList()");
		
		var form = document.actionForm;
		function info() {
			var specialityAspectId = getSelectId("specialityAspectId");
			if (specialityAspectId == null || specialityAspectId == "" || isMultiId(specialityAspectId)) {
				alert("请选择一个进行操作。");
				return;
			}
			form.action = "specialityAspectSearch.do?method=info";
			addInput(form, "specialityAspect.id", specialityAspectId, "hidden");
			form.submit();
		}
		
		function exportList() {
			form.action = "specialityAspectSearch.do?method=export";
			addParamsInput(form, queryStr);
			form.submit();
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>
