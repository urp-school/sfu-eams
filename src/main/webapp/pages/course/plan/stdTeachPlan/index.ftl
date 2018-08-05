<#include "/templates/head.ftl"/>
<body>
<#assign labInfo><@msg.message key="teachPlan.person.maintenance.title"/></#assign>
<#include "/templates/back.ftl"/>

<table width="85%" align="center" class="formTable">
	<form name="stdPlanForm" method="post" action="" onsubmit="return false;">
	<tr>
	   <td><@msg.message key="teachPlan.person.maintenance.stdNo"/>：</td>
	   <td><input type="text" name="stdCode" tabIndex="0" value="${RequestParameters['stdCode']?if_exists}" style="width:125px">
	       <#assign majorTypeId = RequestParameters['majorType.id']?default("1")>
		   <select name="majorType.id" style="width:125px">
			   <option value="1" <#if majorTypeId="1"> selected</#if>><@msg.message key="entity.firstSpeciality"/></option>
			   <option value="2" <#if majorTypeId="2"> selected</#if>><@msg.message key="entity.secondSpeciality"/></option>
		   </select>
		   <@msg.message key="attr.term"/>
		   <input name="terms" maxlength="200" value="${RequestParameters['terms']?default("")}" type="text" style="width:60px"/>
		   <@msg.message key="teachPlan.person.maintenance.multiterm.explain"/>
	   </td>
	   <td><button name="query" class="buttonStyle" onclick="getTeachPlan()"><@msg.message key="action.query"/></button></td>
	</tr>
	</form>
</table>
<script>
    document.stdPlanForm.stdCode.focus();
    function getTeachPlan(){
       var form= document.stdPlanForm;
       if(form['stdCode'].value==""){
         alert("学号为必填项");
         return;
       }
       form.action="stdTeachPlan.do?method=edit";
       form.submit();
    }
</script>
<body>
<#include "/templates/foot.ftl"/>