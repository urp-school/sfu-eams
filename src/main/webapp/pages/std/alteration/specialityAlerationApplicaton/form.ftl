<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body>
 <table id="bar"></table>
 <table width="90%" class="formTable" align="center" style="padding:0px;bolder-spacing:0px;">
 		<td class="darkColumn" colspan="4">
		    <B>转专业申请</B>
   		</td>
	<form name="actionForm" action="" method="post" onsubmit="return false;">
		<input type="hidden" name="application.id" value="${(application.id)?if_exists}"/>
		<input type="hidden" name="plan.id" value="${(plan.id)?if_exists}"/>
		<tr>
		 <td class="title" width="20%">原来专业</td>
		 <td width="30%">
		 	<#if (application.id)?exists>
		 		<@i18nName (application.major)?if_exists/>
		 		<input type="hidden" name="application.major.id" value="${(application.major.id)?if_exists}"/>
		 	<#else>
		 		<@i18nName (student.firstMajor)?if_exists/>
		 		<input type="hidden" name="application.major.id" value="${(student.firstMajor.id)?if_exists}"/>
		 	</#if>
		 </td>
		 <td class="title" width="20%">原来年级</td>
		 <td>
		 	<#if (application.id)?exists>${application.grade}<#else>${student.grade}</#if>
		 </td>
		</tr>
		<tr>
		 <td class="title" id="t_applyMajor">申请专业<font color="red">*</font></td>
		 <td>
		 	<select name="application.majorPlan.id" style="width:300px">
    			<#list plan.majorPlans?sort_by(["major","name"]) as majorPlan>
       				<option value="${majorPlan.id}" <#if (application.majorPlan.id)?exists && majorPlan.id=application.majorPlan.id>selected</#if>><@i18nName majorPlan.major/>-<@i18nName (majorPlan.majorField)?if_exists/></option>
    			</#list>
  			</select>
		 </td>
		 <td class="title" id="t_applyGrade">申请年级<font color="red">*</font></td>
		 <td><input type="text" name="application.applyGrade" value="<#if (application.id)?exists>${application.applyGrade}<#else>${student.grade}</#if>"  maxLength="7" /></td>
		</tr>
		<tr>
			<td class="title">申请理由</td>
			<td colspan="3" ><textarea name="application.applyReason" rows="5" cols="80">${(application.applyReason)?if_exists}</textarea></td>	
		</tr>
		<tr>
		<td align="center" class="darkColumn" colspan="4">
		    <input type="button" onClick="save()" value="<@bean.message key="system.button.submit" />" name="button1" class="buttonStyle" />&nbsp;
		    <input type="reset" onClick="actionForm.reset()" value="<@bean.message key="system.button.reset" />" name="reset1"  class="buttonStyle" />
   		</td>
		</tr>
   </form>	
 </table>

<script language="javascript" >
    var bar=new ToolBar("bar","转专业申请",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("<@msg.message key="action.save"/>","save()");
    bar.addBack("<@msg.message key="action.back"/>");
    var form = document.actionForm;
    function save(){
     var fields = {
         'application.applyGrade':{'l':'申请年级', 'r':true, 't':'t_applyGrade', 'f':'yearMonth'},
         'application.majorPlan.id':{'l':'申请专业', 'r':true, 't':'t_applyMajor'}
     };
    
     var v = new validator(form, fields, null);
     if (v.exec()) {
     	form.action="specialityAlerationApplicaton.do?method=save";
        form.submit();
     }
 }
</script>
</body>
<#include "/templates/foot.ftl"/>