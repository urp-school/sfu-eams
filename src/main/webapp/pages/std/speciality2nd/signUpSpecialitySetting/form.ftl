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
   <input type="hidden" name="count" value="${specialitySettings?size}"/>
   <input type="hidden" name="specialitySetting.setting.id" value="${RequestParameters['specialitySetting.setting.id']}"/>
   <@table.tbody datas=specialitySettings;setting,setting_index>
     <input type="hidden" name="specialitySetting${setting_index}.id" value="${setting.id?default('')}"/>
     <input type="hidden" name="specialitySetting${setting_index}.speciality.id" value="${setting.speciality.id}"/>
     <input type="hidden" name="specialitySetting${setting_index}.aspect.id" value="${(setting.aspect.id)?default('')}"/>
     <td>${setting_index+1}</td>
     <td id="specialitySetting${setting_index}"><@i18nName setting.speciality/></td>
     <td><@i18nName setting.aspect?if_exists/></td>
     <td><input name="specialitySetting${setting_index}.limit" value="${setting.limit?default('')}" maxlength="5"/></td>
  </@>
 </form>
</@>
  <script language="javascript" >
    var form=document.commonForm;
    function save(){
     var fields = {
     	 <#list specialitySettings as setting>
     	 	'specialitySetting${setting_index}.limit':{'l':'<@bean.message key="info.secondSpecialitySignUpSetting.totalLimit"/>', 'r':false, 't':'specialitySetting${setting_index}', 'f':'unsigned'}<#if setting_has_next>,</#if>
     	 </#list>
     };
     var v = new validator(form, fields, null);
     if (v.exec()) {
        form.action="speciality2ndSignUpSpecialitySetting.do?method=saveLimits"
        form.submit();
     }
   }
   var bar = new ToolBar("myBar","指定辅修专业报名人数上限（人数上限是空的，将不进行保存）",null,true,true);
   bar.addItem("<@msg.message key="action.save"/>","save()");
   bar.addBack("<@msg.message key="action.back"/>");
  </script>
 </body>
<#include "/templates/foot.ftl"/>