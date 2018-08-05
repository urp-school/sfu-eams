<#include "/templates/head.ftl"/>
<BODY>
	<table id="bar"></table>
  <@table.table width="100%"  id="listTable" sortable="true">
    <@table.thead>
       <@table.selectAllTd id="teacherId"/>
       <@table.sortTd width="10%" name="teacher.code" id="teacher.code"/>
       <@table.sortTd width="10%" name="attr.personName" id="teacher.name"/>
       <@table.sortTd width="10%" name="common.gender" id="teacher.gender.name"/>
       <@table.sortTd width="20%" name="entity.department" id="teacher.department.name"/>
       <@table.sortTd width="10%" name="common.phoneOfCorporation" id="teacher.addressInfo.phoneOfCorporation"/>
  	   <@table.sortTd width="15%" name="common.teacherTitle" id="teacher.title"/>
       <@table.sortTd width="15%" name="entity.teacherType" id="teacher.teacherType.name"/>	
    </@>
    <@table.tbody datas=teachers;teacher>
       <@table.selectTd id="teacherId" value=teacher.id/>
       <td><a href="teacherSearch.do?method=info&teacher.id=${teacher.id}">${teacher.code?if_exists}</a></td>
       <td>${teacher.name?if_exists}</td>
       <td><@i18nName teacher.gender/></td>
       <td><@i18nName teacher.department/></td>
       <td>${teacher.addressInfo?if_exists.phoneOfCorporation?if_exists}</td>
       <td><@i18nName teacher.title?if_exists/></td>
       <td><@i18nName teacher.teacherType?if_exists/></td>
    </@>
   </@>
  	<form name="actionForm" action="" method="post">
  		<input type="hidden" name="keys" value="code,name,engName,gender.name,teacherType.name,department.name,birthday,country.name,nation.name,title.name,titleLevel.name,credentialNumber,isConcurrent,isEngageFormRetire,duty.name,dateOfJoin,dateOfTitle,isTeaching,addressInfo.phoneOfCorporation,addressInfo.corporationAddress,addressInfo.postCodeOfCorporation,addressInfo.phoneOfHome,addressInfo.postCodeOfFamily,addressInfo.familyAddress,addressInfo.mobilePhone,addressInfo.fax,addressInfo.email,addressInfo.homepage,degreeInfo.degree.name,degreeInfo.dateOfDegree,degreeInfo.eduDegreeInside.name,degreeInfo.eduDegreeOutside.name,degreeInfo.dateOfEduDegreeInside,degreeInfo.dateOfEduDegreeOutside,degreeInfo.graduateSchool.name"/>
  		<input type="hidden" name="titles" value="职工号,<@msg.message key="attr.personName"/>,英文名,<@msg.message key="entity.gender"/>,教职工类别,所在部门,出生日期,国家地区,民族,职称,职称等级,证件,是否兼职,是否退休返聘,职务,来校日期,现职称年月,是否任课,单位电话,单位地址,单位邮编,家庭电话,家庭邮编,家庭地址,手机,传真,邮箱,主页,现学位,现学位年月,国内最高学历,国外最高学历,国内现学历年月,国外现学历年月,最后毕业学校"/>
  	</form>
	<script>
		var bar = new ToolBar("bar", "专业方向信息列表", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("<@msg.message key="action.look"/>", "info()", "detail.gif");
		bar.addItem("<@msg.message key="action.export"/>", "exportList()");
		
		var form = document.actionForm;
		function info() {
			var teacherId = getSelectId("teacherId");
			if (teacherId == null || teacherId == "" || isMultiId(teacherId)) {
				alert("请选择一个进行操作。");
				return;
			}
			form.action = "teacherSearch.do?method=info";
			addInput(form, "teacher.id", teacherId, "hidden");
			form.submit();
		}
		
		function exportList() {
			form.action = "teacherSearch.do?method=export";
			addParamsInput(form, queryStr);
			form.submit();
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>
