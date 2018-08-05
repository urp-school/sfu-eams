<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body >
<#assign labInfo>规则参数详细设置</#assign> 
<#include "/templates/back.ftl"/>
 <form action="degreeAuditStandard.do?method=saveRuleConfigParam" name="standardForm" method="post" onsubmit="return false;">
 <input name="standard.id" value="${(standard.id)?default('')}" type="hidden"/>
 <table width="95%"  align="center" class="formTable">
   <tr class="darkColumn">
     <td width="20%" align="center"><B>规则名称</B></td>
     <td width="30%" align="center"><B>规则参数名(标题)</B></td>
     <td width="50%" align="center"><B>参数值</B></td>
   </tr>
 <#list standard.ruleConfigs?sort_by("id") as ruleConfig>
 <#if ruleConfig.enabled == true>
   <#list ruleConfig.params as param>
   <tr>
     <td>${ruleConfig.rule.name?default("")}</td>
     <td title="${param.param.description?default("")}">${param.param.name?default("")} (${param.param.title?default("")})</td>     
     <td>
     	<#include "input/inputChecker.ftl"/>
     </td>
   </tr>
   </#list>   
 </#if>
 </#list>
 </table>
 <table width="95%"  align="center" class="formTable">
   <tr class="darkColumn" align="center">
     <td>
       <input type="button" onClick='save(this.form)' value="保存详细参数" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
       <input type="button" onClick='reset()' value="<@bean.message key="system.button.reset"/>" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
     </td>
   </tr>
 </table>
 </form>
  <script language="javascript" defer="defer"> 
   var form=document.standardForm;
   function reset(){
       form.reset();
     }
   function save(form){
     form.action="degreeAuditStandard.do?method=saveRuleConfigParam";
     form.submit();
   }
   function addOtherExamCategory(kind,valueIds){
      var category = document.getElementById(kind);
      if(category.value!=""){
          if((','+form[valueIds].value+',').indexOf(','+category.value+',')==-1){
              form[valueIds].value+=category.value +",";
              form[kind+'Names'].value+=DWRUtil.getText(kind)+" ";
          }
      }
   }
   
   if(typeof(computerValue) != 'undefined' && computerValue!=""){
		var computer = computerValue.split(',');
		for(var i=0;i<=computer.length;i++){
			if (typeof(computer[i]) != 'undefined' && computer[i] != ""){
				this.form['computerExam'].value = computer[i];
				form['computerExamNames'].value+=DWRUtil.getText("computerExam")+" ";
			}
		}
  	}
  	
  	if(typeof(languageValue) != 'undefined' && languageValue!=""){
		var language = languageValue.split(',');
		for(var i=0;i<=language.length;i++){
			if (typeof(language[i]) != 'undefined' && language[i] != ""){
				this.form['languageExam'].value = language[i];
				form['languageExamNames'].value+=DWRUtil.getText("languageExam")+" ";
			}
		}
  	}
 </script>
 </body>
</html>