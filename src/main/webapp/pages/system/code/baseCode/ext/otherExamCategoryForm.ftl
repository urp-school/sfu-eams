<#include "/templates/head.ftl"/>
<#assign noOtherExamKind="当前没有其它考试类型，请先添加该类型。"/>
<#assign extraTR>
    <tr>
    	<td class="title"><font color="red">*</font>其它考试种类</td>
    	<td colspan="3"><#if !beenChoiceKinds?exists || beenChoiceKinds?size == 0><font color="red">${noOtherExamKind}</font><br></#if>
	    	<@htm.i18nSelect datas=beenChoiceKinds?if_exists selected=(baseCode.kind.id?string)?default("") style="width:150px" name="baseCode.kind.id"/>
    	</td>
    </tr>
</#assign>
<#assign saveAction>
	function save(){
		if (form["baseCode.kind.id"].options.length == 0) {
			alert("${noOtherExamKind}");
			return;
		}
         var a_fields = {
	         'baseCode.code':{'l':'<@msg.message key="attr.code"/>', 'r':true, 't':'f_code'},
	         'baseCode.name':{'l':'<@msg.message key="attr.name"/>', 'r':true, 't':'f_name'}
	     };
	     var v = new validator(form , a_fields, null);
	     if (v.exec()) {
	        form.submit();
	     }
     }
</#assign>
<#include "commonForm.ftl"/>