<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src='scripts/common/multiSelectChoice.js'></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<body>
	<table id="bar"></table>
	<table class="formTable" width="100%">
		<tr>
			<td colspan="4" class="darkColumn" style="font-weight:bold;text-align:center"><#if scheme?exists>修改<#else>新增</#if>教学日历方案</td>
		</tr>
		<form method="post" action="" name="actionForm" onsubmit="return false;">
			<input type="hidden" name="scheme.id" value="${(scheme.id)?if_exists}"/>
		<tr>
			<td class="title" width="20%" id="f_name"><font color="red">*</font>教学日历方案名称：</td>
			<td width="30%"><input type="text" name="scheme.name" value="${(scheme.name)?if_exists}" style="width:150px" maxlength="30"/></td>
			<td class="title" width="20%">时间设置：</td>
			<td><@htm.i18nSelect datas=(timeSettings?sort_by("name"))?if_exists selected=(scheme.timeSetting.id?string)?default("") name="scheme.timeSetting.id" style="width:150px"><option value="">...</option></@></td>
		</tr>
		<tr>
			<td class="title" id="f_stdType"><font color="red">*</font><@msg.message key="entity.studentType"/>：</td>
			<td colspan="3">
				<table>
					<tr>
						<td><@htm.i18nSelect datas=(studentTypes?sort_by("name"))?if_exists selected="" name="studentTypeId" style="width:150px" size="10" MULTIPLE="MULTIPLE" onDblClick="selectRemoveAnyOne('studentTypeId', 'schemeStudentTypeId')"/></td>
						<td>
							<input type="button" value="&lt;&lt;" onclick="selectRemoveAll('schemeStudentTypeId', 'studentTypeId')" style="width:35px"/>
							<br><br>
							<input type="button" value="&lt;" onclick="selectRemoveAnyOne('schemeStudentTypeId', 'studentTypeId')" style="width:35px"/>
							<br><br>
							<input type="button" value="&gt;" onclick="selectRemoveAnyOne('studentTypeId', 'schemeStudentTypeId')" style="width:35px"/>
							<br><br>
							<input type="button" value="&gt;&gt;" onclick="selectRemoveAll('studentTypeId', 'schemeStudentTypeId')" style="width:35px"/>
						</td>
						<td><@htm.i18nSelect datas=(scheme.stdTypes?sort_by("code"))?if_exists selected="" name="schemeStudentTypeId" style="width:150px" size="10" MULTIPLE="MULTIPLE" onDblClick="selectRemoveAnyOne('schemeStudentTypeId', 'studentTypeId')"/></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="darkColumn" style="text-align:center"><button onclick="save()"><@msg.message key="action.save"/></button></td>
		</tr>
			<input type="hidden" name="schemeStudentTypeIds" value=""/>
		</form>
	</table>
	<script>
		var bar = new ToolBar("bar", "<#if scheme?exists>修改<#else>新增</#if>教学日历方案", null, true ,true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("<@msg.message key="action.save"/>", "save()");
		bar.addBack("<@msg.message key="action.back"/>");
		
		var form = document.actionForm;
		
		function save() {
			form["schemeStudentTypeIds"].value = getAllOptionValue(form["schemeStudentTypeId"]);
			var a_fields = {
				'scheme.name':{'l':'教学日历方案名称', 'r':true, 't':'f_name'},
				'schemeStudentTypeIds':{'l':'学生类别', 'r':true, 't':'f_stdType'}
			};
			var v = new validator(form, a_fields, null);
			if (v.exec()) {
				form.action = "calendar.do?method=saveScheme";
				form.target = "_parent";
				form.submit();
			}
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>