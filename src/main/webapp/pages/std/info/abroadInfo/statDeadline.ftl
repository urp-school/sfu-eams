<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<body>
	<table id="bar"></table>
	<table class="frameTable" width="100%">
		<tr valign="top">
		<form method="post" action="" name="actionForm">
			<#assign stdTypeNullable=true/>
			<#include "/pages/components/initAspectSelectData.ftl"/>
			<td class="frameTable_view" width="20%"><#include "statForm.ftl"/><#list 1..5 as i><br></#list></td>
		</form>
			<td><iframe name="framePage" src="#" width="100%" frameborder="0" scrolling="no"></iframe></td>
		</tr>
	</table>
	<script>
		var bar = new ToolBar("bar", "统计到期时间", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
		
		var sds = new StdTypeDepart3Select("std_stdTypeOfSpeciality", "std_department", "std_speciality", "std_specialityAspect", ${(stdTypeNullable?string('true', 'false'))?default('false')},true,true,true);    
    	sds.init(stdTypeArray, departArray);
    	sds.firstSpeciality = 1;
    	function changeSpecialityType(event) {
       		var select = getEventTarget(event);
       		sds.firstSpeciality = select.value;
       		fireChange($("std_department"));
    	}
    	
		var form = document.actionForm;
		function statAbroadStdList() {
			var a_fields = {
				'deadlineDate':{'l':'到期时间', 'r':false, 't':'f_date', 'f':'date'}
			};
			var v = new validator(form, a_fields, null);
			if (v.exec()) {
				form.action = "abroadInfo.do?method=statAbroadStdList";
				form.target = "framePage";
				form.submit();
			}
		}
		
		statAbroadStdList();
	</script>
</body>
<#include "/templates/foot.ftl"/>