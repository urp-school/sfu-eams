<#include "/templates/head.ftl"/>
 <body>
<#assign labInfo>学位审核信息</#assign> 
	<#include "/templates/back.ftl"/>
     <table  class="infoTable">
	   <tr>
	     <td class="title" ><@bean.message key="attr.stdNo" />:</td>
	     <td class="content"> ${auditResult.std.code}</td>     
	     <td class="title" ><@bean.message key="attr.personName" />:</td>
	     <td class="content"> ${auditResult.std.name}</td>     
	   </tr>
	   <tr>
	     <td class="title"><@msg.message key="entity.studentType" />:</td>
	     <td><@i18nName auditResult.std.type/></td>
	     <td class="title">完成培养计划:</td>
	     <td>${auditResult.isCompletePlan?default(true)?string("是","否")}</td>	   
	   </tr>
	   <tr>
	     <td class="title">论文答辩分数:</td>
	     <td>${auditResult.thesisScore?default("")}</td>
	     <td class="title">平均绩点:</td>
	     <td>${auditResult.GPA?default("")}</td>	   
	   </tr>
	   <tr>
	     <td class="title">最高处分:</td>
	     <td><@i18nName auditResult.punishmentType?if_exists/></td>
	     <td class="title">第一专业:</td>
	 	 <td>${(auditResult.majorType.id==1)?string("是","否")}</td>
	   </tr>
	   <tr>
	     <td class="title">外语及格成绩:</td>
	     <td><#if auditResult.languageGrades?exists><#list auditResult.languageGrades as grade><@i18nName grade.category/> ${(grade.scoreDisplay)}&nbsp;</#list></#if></td>
	     <td class="title">计算机及格成绩:</td>
	     <td><#if auditResult.computerGrades?exists><#list auditResult.computerGrades as grade><@i18nName grade.category/> ${(grade.scoreDisplay)}</#list></#if></td>
	   </tr>
	   <tr>
	     <td class="title">通过学科综合考试:</td>
	     <td>${auditResult.isPassDoctorComprehensiveExam?default(false)?string("是","否")}</td>
	     <td class="title">核心期刊的论文最低数（或折合数）:</td>
	     <td>${auditResult.thesisInCoreMagazine?default("")}</td>	   
	   </tr>
	   <tr>
	     <td class="title">学历证书编号:</td>
	     <td>${auditResult.diplomaNo?default("暂无")}</td>
	     <td class="title">学位证书编号:</td>
	     <td>${auditResult.certificateNo?default("暂无")}</td>	   
	   </tr>
	   <tr>
	     <td class="title">毕业情况:</td>
	     <td>${auditResult.graduateCircs?default("暂无")}</td>	   
	     <td class="title">最终审核结果</td>
	     <td colspan="1"><#if auditResult.isPass?exists&&auditResult.isPass?string("通过","不通过")=="通过"&&auditResult.certificateNo?default("")==""><font color="red">预通过</font><#else>
	       <#if auditResult.isPass?exists>${auditResult.isPass?string("通过","不通过")}</#if></#if>
	     </td>
	   </tr>
	   <tr>
	     <td class="title">审核输出信息:</td>
	     <td colspan="3">
	       <#list auditResult.degreeAuditInfos?keys as infoKey>
	        <#assign info = auditResult.degreeAuditInfos[infoKey]>
	         <#if info.pass == true>
 	       	   ${info.ruleConfig.rule.name?if_exists}--(${info?if_exists.description}) <br>
	         <#else>
 	       	   <font color="red">${info.ruleConfig.rule.name?if_exists}--(${info?if_exists.description}) </font> <br>
	         </#if>
	       </#list>
	     </td>
	   </tr>
   </table> 
 
   <#list standards as standard>
    <#assign labInfo>审核标准信息 (${standard.name})</#assign> 
	<table id="${standard.id}" width="100%"></table>
	<script>
		var bar = new ToolBar('${standard.id}','${labInfo}',null,true,true);
		bar.setMessage('<@getMessage/>');
		bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
	</script>
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
     	<#include "inputCheckerInfo.ftl"/>
     </td>
   </tr>
   </#list>   
 </#if>
 </#list>
 </table>
 <script language="javascript" defer="defer"> 
   if(typeof(computerValue_${standard.id}) != 'undefined' && computerValue_${standard.id}!=""){
		var computer = computerValue_${standard.id}.split(',');
		for(var i=0;i<=computer.length;i++){
			if (typeof(computer[i]) != 'undefined' && computer[i] != ""){
				document.getElementById('computerExam').value = computer[i];
				document.getElementById('computerExamNames_${standard.id}').value+=DWRUtil.getText("computerExam")+" ";
			}
		}
  	}
  	
  	if(typeof(languageValue_${standard.id}) != 'undefined' && languageValue_${standard.id}!=""){
		var language = languageValue_${standard.id}.split(',');
		for(var i=0;i<=language.length;i++){
			if (typeof(language[i]) != 'undefined' && language[i] != ""){
				document.getElementById('languageExam').value = language[i];
				document.getElementById('languageExamNames_${standard.id}').value+=DWRUtil.getText("languageExam")+" ";
			}
		}
  	}
  	
  	if(typeof(lowestPunishType_${standard.id}) != 'undefined' && lowestPunishType_${standard.id}!=""){
	  	document.getElementById('punishmentType').value = lowestPunishType_${standard.id};
		document.getElementById('punishmentTypes_${standard.id}').value+=DWRUtil.getText("punishmentType")+"(包含)";
  	}
 </script>
    </#list>  
  </body>
<#include "/templates/foot.ftl"/>