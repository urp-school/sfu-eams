<#include "/templates/head.ftl"/>
<BODY>
	<table id="bar"></table>
  <@table.table id="listTable" width="100%" sortable="true">
    <@table.thead>
        <@table.selectAllTd id="id"/>
        <@table.sortTd name="attr.code" id="course.code"/>
        <@table.sortTd name="attr.infoname" id="course.name"/>
        <@table.sortTd name="common.courseLength" id="course.extInfo.period"/>
        <@table.sortTd text="周课时" id="course.weekHour"/>
        <@table.sortTd name="common.grade" id="course.credits"/>
        <@table.sortTd name="course.stdType" id="course.stdType.name"/>
        <@table.sortTd name="attr.engName" id="course.engName"/>
     </@>
     <@table.tbody datas=courses;course>
        <@table.selectTd id="id" value=course.id/>
        <td>&nbsp;${course.code}</td>
        <td><a href="courseSearch.do?method=info&type=course&id=${course.id}">&nbsp;<@i18nName course/></a></td>
        <td>&nbsp;${(course.extInfo.period)?if_exists}</td>
        <td>&nbsp;${course.weekHour?if_exists}</td>
        <td>&nbsp;${course.credits?if_exists}</td>
        <td>&nbsp;<@i18nName course.stdType/></td>
        <td>&nbsp;${course.engName?if_exists}</td>
     </@>
   </@>
  	<form name="actionForm" action="" method="post">
  		<input type="hidden" name="keys" value="code,name,engName,abbreviation,createAt,modifyAt,remark,state,credits,period,weekHour,stdType.name,category.name,extInfo.requirement,extInfo.description,languageAbility.name,extInfo.establishOn"/>
  		<input type="hidden" name="titles" value="代码,名称,英文名,简称,创建日期,修改日期,备注,是否使用,学分,学时,周课时,<@msg.message key="entity.studentType"/>,课程种类,课程要求,课程简介,语言熟练程度要求,设立年月"/>
  	</form>
	<script>
		var bar = new ToolBar("bar", "课程信息列表", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("<@msg.message key="action.look"/>", "info()");
		bar.addItem("<@msg.message key="action.export"/>", "exportList()");
		
		var form = document.actionForm;
		function info() {
			var id = getSelectId("id");
			if (id == null || id == "" || isMultiId(id)) {
				alert("请选择一个进行操作。");
				return;
			}
			form.action = "courseSearch.do?method=info&id=" + id;
			addInput(form, "type", "course", "hidden");
			form.submit();
		}
		
		function exportList() {
			form.action = "courseSearch.do?method=export";
			addHiddens(form, queryStr);
			form.submit();
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>
