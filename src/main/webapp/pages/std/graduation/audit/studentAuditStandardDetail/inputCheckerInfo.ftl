<#if param.param.type=="OtherExamCategory" && param.param.name=="computerExams">
	<input type="text" id="computerExamNames_${standard.id}" value="" style="border:0 solid #000000; width:100%" readOnly="true" maxlength="100"/><br>
	<@htm.i18nSelect datas=otherExamCategories id="computerExam" selected="" style="display: 'none'"/>
	<script language="javascript"> 
		var computerValue_${standard.id} = "${(param.value)?default("")}";
	</script>
<#elseif param.param.type=="OtherExamCategory" && param.param.name=="languageGrades">	
	<input type="text" id="languageExamNames_${standard.id}" value="" style="border:0 solid #000000; width:100%" readOnly="true" maxlength="100"/><br>
	<@htm.i18nSelect datas=otherExamCategories id="languageExam" selected="" style="display: 'none'"/>
	<script language="javascript"> 
		var languageValue_${standard.id} = "${(param.value)?default("")}";
	</script>
<#elseif param.param.type=="PunishmentType" && param.param.name=="lowestPunishType.id">	
	<input type="text" id="punishmentTypes_${standard.id}" value="" style="border:0 solid #000000; width:100%" readOnly="true" maxlength="100"/><br>
	<@htm.i18nSelect datas=punishTypes?sort_by("level") id="punishmentType" name="value_${(param.id)}" selected=((param.value)?string)?default("") style="display: 'none'"/>
	<script language="javascript"> 
		var lowestPunishType_${standard.id} = "${(param.value)?default("")}";
	</script>
<#elseif param.param.type=="Boolean">	
	<#if (param.value)?default("") == "1">是<#else>否</#if>
<#elseif param.param.type=="Float">
	<input name="value_${(param.id)}" value="${(param.value)?default("")}" maxlength="20" style="border:0 solid #000000; width:100%" readOnly="true"/>
<#elseif param.param.type=="Integer">
	<input name="value_${(param.id)}" value="${(param.value)?default("")}" maxlength="20" style="border:0 solid #000000; width:100%" readOnly="true"/>
<#elseif param.param.type=="String">
	<input name="value_${(param.id)}" value="${(param.value)?default("")}" maxlength="20" style="border:0 solid #000000; width:100%" readOnly="true"/>
<#else>
	<input name="value_${(param.id)}" value="${(param.value)?default("")}" maxlength="20" style="border:0 solid #000000; width:100%" readOnly="true"/>
</#if>