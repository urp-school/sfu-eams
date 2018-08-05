<#if param.param.type=="OtherExamCategory" && param.param.name=="computerExams">
	<input type="hidden" name="value_${(param.id)}" value="${(param.value)?default("")}"/>
	<input type="text" name="computerExamNames" value="" style="border:0 solid #000000; width:100%" readOnly="true" maxlength="100"/><br>
	<@htm.i18nSelect datas=otherExamCategories id="computerExam" selected="" style="width:150px"/>
	<button onClick="this.form['value_${(param.id)}'].value='';this.form['computerExamNames'].value='';"><@bean.message key="action.clear"/></button>               
	<button onClick="addOtherExamCategory('computerExam','value_${(param.id)}');"><@bean.message key="action.add"/></button><br>
	<script language="javascript"> 
		var computerValue = "${(param.value)?default("")}";
	</script>
<#elseif param.param.type=="OtherExamCategory" && param.param.name=="languageGrades">	
	<input type="hidden" name="value_${(param.id)}" value="${(param.value)?default("")}"/>
	<input type="text" name="languageExamNames" value="${(param.value)?default("")}" style="border:0 solid #000000; width:100%" readOnly="true" maxlength="100"/><br>
	<@htm.i18nSelect datas=otherExamCategories id="languageExam" selected="" style="width:150px"/>
	<button onClick="this.form['value_${(param.id)}'].value='';this.form['languageExamNames'].value='';"><@bean.message key="action.clear"/></button>               
	<button onClick="addOtherExamCategory('languageExam','value_${(param.id)}');"><@bean.message key="action.add"/></button><br>
	<script language="javascript"> 
		var languageValue = "${(param.value)?default("")}";
	</script>
<#elseif param.param.type=="PunishmentType" && param.param.name=="lowestPunishType.id">	
	<@htm.i18nSelect datas=punishTypes?sort_by("level") name="value_${(param.id)}" selected=((param.value)?string)?default("") style="width:100px"/>(包含)
<#elseif param.param.type=="Boolean">	
	<@htm.select2 selected=(param.value)?default("1") name="value_${(param.id)}" hasAll=false style="width:100px"/>
<#elseif param.param.type=="Float">
	<input name="value_${(param.id)}" value="${(param.value)?default("")}" maxlength="20" style="width:100%"/>
    <input name="id_${(param.id)}" value="${(param.id)?default('')}" type="hidden"/>
<#elseif param.param.type=="Integer">
	<input name="value_${(param.id)}" value="${(param.value)?default("")}" maxlength="20" style="width:100%"/>
    <input name="id_${(param.id)}" value="${(param.id)?default('')}" type="hidden"/>
<#elseif param.param.type=="String">
	<input name="value_${(param.id)}" value="${(param.value)?default("")}" maxlength="20" style="width:100%"/>
    <input name="id_${(param.id)}" value="${(param.id)?default('')}" type="hidden"/>
<#else>
	<input name="value_${(param.id)}" value="${(param.value)?default("")}" maxlength="20" style="width:100%"/>
    <input name="id_${(param.id)}" value="${(param.id)?default('')}" type="hidden"/>
</#if>