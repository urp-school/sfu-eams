<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <body> 
 <#assign labInfo>考试报名设置</#assign>
 <#include "/templates/back.ftl"/>
    <table width="90%" align="center" class="formTable">
     <form action="" name="actionForm" method="post" onsubmit="return false;">
       <@searchParams/>
	   <tr class="darkColumn">
	     <td align="center" colspan="2">考试报名设置</td>
	   </tr>	
	   <tr>
	     <td class="title" width="20%" id="f_name"><font color="red">*</font>考试种类:</td>
	     <td><@htm.i18nSelect datas=availExamCategories?sort_by("name") selected="${(setting.examCategory.id)?default('')?string}" name="setting.examCategory.id" style="width:150px"/></td>
	   </tr>
	   <tr>
	     <td class="title" id="f_startAt"><font color="red">*</font><@bean.message key="field.soeType.startTime"/>:</td>
	     <td><input type="text" style="width:300px" maxlength="10" name="setting.startAt" value='<#if (setting.startAt)?exists>${(setting.startAt)?string("yyyy-MM-dd")}</#if>' onfocus="calendar()"/></td>
	   </tr>
	   <tr>
	     <td class="title" id="f_endAt"><font color="red">*</font><@bean.message key="field.soeType.endTime"/>:</td>
	     <td><input type="text" style="width:300px" maxlength="10" name="setting.endAt" value='<#if (setting.endAt)?exists>${setting.endAt?string("yyyy-MM-dd")}</#if>' onfocus="calendar()"/></td>
	   </tr>
	   <tr>
	     <td class="title" id="f_availMonth"><font color="red">*</font><@bean.message key="field.soeType.available"/>:</td>
	     <td><input type="text" style="width:280px" maxlength="3" name="setting.availMonth" value='${(setting.availMonth)?default(0)}' maxlength="3"/>月</td>
	   </tr>
	   <tr>
	     <td class="title"/>考试约束:</td>
	     <td><@htm.i18nSelect datas=otherExamCategories?sort_by("name") selected="${(setting.superCategory.id)?default('')?string}" name="setting.superCategory.id" style="width:150px">
	          <option value="">无</option>
	          </@>通过
	     </td>
	   </tr>	
	   <tr>
	     <td class="title"colspan=2 align="center">
	         <table  bordercolor="#006CB2" class="formTable" cellpadding="0" cellspacing="0">
	          <tr>
	           <td><br>
	            <div align="center">学生类别列表</div>&nbsp;
	            <select name="stdType" MULTIPLE size="10" onDblClick="JavaScript:moveSelectedOption(this.form['stdType'], this.form['freeStdType'])" style="width:250px" style="background-color:#CCCCCC">
	 			<#list stdTypes?if_exists as stdType>
	  				<option value="${stdType.id}"><@i18nName stdType/></option>
		        </#list>
	            </select><br>
	           </td>
	           <td align="center" valign="middle">
	            <br><br>
	            &nbsp;<input OnClick="JavaScript:moveSelectedOption(this.form['stdType'], this.form['freeStdType'])" type="button" value="&gt;&gt;" class="buttonStyle" style="width:35px;"/> &nbsp;
	            <br><br>
	            &nbsp;<input OnClick="JavaScript:moveSelectedOption(this.form['freeStdType'], this.form['stdType'])" type="button" value="&lt;&lt;" class="buttonStyle" style="width:35px;"> &nbsp;
	            <br>
	           </td>
	           
	           <td align="center" class="normalTextStyle">
	            <div align="center">免试学生类别列表</div>&nbsp;
	            <select name="freeStdType" MULTIPLE size="10" style="width:250px;" onDblClick="JavaScript:moveSelectedOption(this.form['freeStdType'], this.form['stdType'])" style="background-color:#CCCCCC">
	           		<#list (setting.freeStdTypes)?if_exists as stdType>
	  				<option value="${stdType.id}"><@i18nName stdType/></option>
			        </#list> 
	            </select>&nbsp;
	           </td> 
	        </tr>
	     </table>
	     </td>
	   </tr>
	   <tr align="center">
	     <td class="title" id="f_status" align="center">是否开放:</td>
	     <td align="left"><@htm.radio2 name="setting.isOpen" value=(setting.isOpen)?default(true)/></td>
	   </tr>
	   <tr class="darkColumn" align="center">
	     <td colspan="2">
		   <button onClick="doAction(this.form)"> <@bean.message key="system.button.submit"/></button>
           <input type="hidden" name="freeStdTypeIds"/> 
           <input type="hidden" name="setting.id"value="${(setting.id)?default('')}"/>
	       <input type="reset" name="reset1" value="<@bean.message key="system.button.reset"/>" class="buttonStyle"/>
	     </td>
	   </tr>
	   </table>
     </td>
   </tr>
   </form>
   </table>
 <script language="javascript"/>
    function doAction(form){
     var a_fields = {
         'setting.startAt':{'l':'<@bean.message key="field.soeType.startTime"/>', 'r':true, 't':'f_startAt'},
         'setting.endAt':{'l':'<@bean.message key="field.soeType.endTime"/>', 'r':true, 't':'f_endAt'},
         'setting.availMonth':{'l':'<@bean.message key="field.soeType.available"/>', 'r':true, 't':'f_availMonth','f':'unsigned'} 
     };
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
     	if (form['setting.startAt'].value > form['setting.endAt'].value) {
     		alert("<@bean.message key="field.soeType.startTime"/>不能超过<@bean.message key="field.soeType.endTime"/>！");
     		return;
     	}
     	var reg=/^[1,2][0-9]{3}-[0,1][0-9]-[0-3][0-9]$/;
     	var startAt=form['setting.startAt'].value;
     	var endAt=form['setting.endAt'].value;
     	if(!startAt.match(reg) || !endAt.match(reg)){
     		alert("请确认起始与截至日期的格式为 'yyyy-mm-dd' 形式");
     		return;
     	}
	    form['freeStdTypeIds'].value = getAllOptionValue(form.freeStdType); 
	    form.action="otherExamSignUpSetting.do?method=save";
        form.submit();
     }
    }
 </script>
</body>
<#include "/templates/foot.ftl"/>