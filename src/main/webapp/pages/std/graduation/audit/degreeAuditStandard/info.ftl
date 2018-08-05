<#include "/templates/head.ftl"/>
 <body>
    <#assign labInfo>审核标准信息</#assign> 
	<#include "/templates/back.ftl"/>
     <table  class="infoTable">
	   <tr>
	     <td class="title" ><@bean.message key="attr.infoname" />:</td>
	     <td class="content" colspan="3"> ${standard.name}</td>     
	   </tr>
	   <tr>
	     <td class="title"><@msg.message key="entity.studentType" />:</td>
	     <td><@i18nName standard.stdType/></td>
	     <td class="title">第一专业:</td>
	 	 <td>${(standard.majorType.id==1)?string("是","否")}</td>
	   </tr>
     </table>	   
<table width="100%"  align="center" class="formTable">
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
     	<#include "input/inputCheckerInfo.ftl"/>
     </td>
   </tr>
   </#list>   
 </#if>
 </#list>
 </table>
 <script language="javascript" defer="defer"> 
   if(typeof(computerValue) != 'undefined' && computerValue!=""){
		var computer = computerValue.split(',');
		for(var i=0;i<=computer.length;i++){
			if (typeof(computer[i]) != 'undefined' && computer[i] != ""){
				document.getElementById('computerExam').value = computer[i];
				document.getElementById('computerExamNames').value+=DWRUtil.getText("computerExam")+" ";
			}
		}
  	}
  	
  	if(typeof(languageValue) != 'undefined' && languageValue!=""){
		var language = languageValue.split(',');
		for(var i=0;i<=language.length;i++){
			if (typeof(language[i]) != 'undefined' && language[i] != ""){
				document.getElementById('languageExam').value = language[i];
				document.getElementById('languageExamNames').value+=DWRUtil.getText("languageExam")+" ";
			}
		}
  	}
  	
  	if(typeof(lowestPunishType) != 'undefined' && lowestPunishType!=""){
		document.getElementById('punishmentTypes').value+=DWRUtil.getText("punishmentType")+"(包含)";
  	}
 </script>
  </body>
<#include "/templates/foot.ftl"/>