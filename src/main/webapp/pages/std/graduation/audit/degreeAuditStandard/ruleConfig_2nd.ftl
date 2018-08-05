<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body >
<#assign labInfo>学位审核规则设定</#assign> 
<#include "/templates/back.ftl"/>
 <table width="95%"  align="center" class="formTable">
  <form action="degreeAuditStandard.do?method=ruleConfig_3rd" name="standardForm" method="post" onsubmit="return false;">
   <input name="standard.id" value="${(standard.id)?default('')}" type="hidden"/>
   <@searchParams/>
   <tr class="darkColumn">
     <td align="left" colspan="4"><B>可使用规则</B></td>
   </tr>
   <tr class="darkColumn">
     <td align="center"><B>学位审核规则</B></td>
     <td align="center" colspan="2"><B>规则描述</B></td>
     <td align="center"><B>状态</B></td>
   </tr>
   <#list standard.ruleConfigs?sort_by("id") as ruleConfig>
   <tr>
     <td><input type="checkbox" id="ruleConfigId" name="ruleConfig.rule.id" value="${ruleConfig.id?if_exists}" <#if ruleConfig.enabled==true>checked</#if>/>${ruleConfig.rule.name?default("")}</td>
     <td colspan="2">${(ruleConfig.rule.description)?default("")}</td>
     <td>${(ruleConfig.enabled)?string("启用","禁用")}</td>
   </tr>
   </#list>
   <tr class="darkColumn" align="center">
     <td colspan="4">
       <input type="button" onClick='save(this.form)' value="保存并配置详细参数" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
       <input type="button" onClick='reset()' value="<@bean.message key="system.button.reset"/>" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
     </td>
   </tr>
   </form>
 </table>
  <script language="javascript" > 
   var form=document.standardForm;
   function reset(){
       form.reset();
     }
   function save(form){
     form.action="degreeAuditStandard.do?method=ruleConfig_3rd";
     var selectId = getSelectIds("ruleConfig.rule.id");
     addInput(form,'ruleConfigIds',selectId,"hidden");
     form.submit();
   }
 </script>
 </body>
</html>