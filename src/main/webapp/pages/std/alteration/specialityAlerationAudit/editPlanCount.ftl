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
   <input type="hidden" name="count" value="${planCounts?size}"/>
   <input type="hidden" name="plan.id" value="${(plan.id)?if_exists}"/>
   <@table.tbody datas=planCounts;planCount,planCount_index>
     <input type="hidden" name="planCount${planCount_index}.id" value="${(planCount.id)?if_exists}"/>
     <input type="hidden" name="planCount${planCount_index}.major.id" value="${(planCount.major.id)?if_exists}"/>
     <td>${planCount_index+1}</td>
     <td id="planCount${planCount_index}"> <@i18nName (planCount.major)?if_exists/></td>
     <td id="planCount${planCount_index}"> <@i18nName (planCount.aspect)?if_exists/></td>
     <td><input name="planCount${planCount_index}.planCount" value="${(planCount.planCount)?if_exists?default('')}" maxlength="5"/></td>
  </@>
 </form>
</@>
  <script language="javascript" >
    var form=document.commonForm;
    function save(){
     var fields = {
     	 <#list planCounts as planCount>
     	 	'planCount${planCount_index}.planCount':{'l':'<@bean.message key="info.secondSpecialitySignUpSetting.totalLimit"/>', 'r':false, 't':'planCount${planCount_index}', 'f':'unsigned'}<#if planCount_has_next>,</#if>
     	 </#list>
     };
     var v = new validator(form, fields, null);
     if (v.exec()) {
        form.action="specialityAlerationAudit.do?method=savePlanCount"
        form.submit();
     }
   }
   var bar = new ToolBar("myBar","指定转专业报名人数上限（人数上限是空的，将从计划中删除）",null,true,true);
   bar.addItem("<@msg.message key="action.save"/>","save()");
   bar.addBack("<@msg.message key="action.back"/>");
  </script>
 </body>
<#include "/templates/foot.ftl"/>