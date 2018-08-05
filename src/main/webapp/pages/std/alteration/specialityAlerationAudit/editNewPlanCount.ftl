<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
 <body>
 <table id="myBar" width="100%"></table>
 <@table.table width="80%" align="center" >
   <@table.thead>
     <@table.td name="attr.index" width="10%"/>
     <@table.td name="entity.speciality"/>
     <@table.td name="entity.specialityAspect"/>
     <@table.td text="人数上限"/>
   </@>
   <form name="commonForm" action="" method="post" onsubmit="return false;">
   <@searchParams/>
   <input type="hidden" name="count" value="${majorFields?size}"/>
   <input type="hidden" name="plan.id" value="${(plan.id)?if_exists}"/>
   <@table.tbody datas=majorFields?sort_by(["major","code"]);majorField,majorField_index>
     <input type="hidden" name="planCount${majorField_index}.id" value=""/>
     <input type="hidden" name="planCount${majorField_index}.major.id" value="${(majorField.speciality.id)?if_exists}"/>
     <input type="hidden" name="planCount${majorField_index}.majorField.id" value="${(majorField.id)?if_exists}"/>
     <td>${majorField_index+1}</td>
     <td id="planCount${majorField_index}"> <@i18nName (majorField.major)?if_exists/></td>
     <td id="planCount${majorField_index}"> <@i18nName (majorField)?if_exists/></td>
     <td><input name="planCount${majorField_index}.planCount" value="${(planCount.planCount)?if_exists?default('')}" maxlength="5"/></td>
  </@>
 </form>
</@>
  <script language="javascript" >
    var form=document.commonForm;
    function save(){
     var fields = {
     	 <#list majorFields as majorField>
     	 	'planCount${majorField_index}.planCount':{'l':'<@bean.message key="info.secondSpecialitySignUpSetting.totalLimit"/>', 'r':false, 't':'planCount${majorField_index}', 'f':'unsigned'}<#if majorField_has_next>,</#if>
     	 </#list>
     };
     var v = new validator(form, fields, null);
     if (v.exec()) {
        form.action="specialityAlerationAudit.do?method=savePlanCount"
        form.submit();
     }
   }
   var bar = new ToolBar("myBar","指定转专业报名人数上限（人数上限是空的，将不进行保存）",null,true,true);
   bar.addItem("<@msg.message key="action.save"/>","save()");
   bar.addBack("<@msg.message key="action.back"/>");
  </script>
 </body>
<#include "/templates/foot.ftl"/>