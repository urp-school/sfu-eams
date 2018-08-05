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
	     <td colspan="1"><#if auditMsg?exists&&auditMsg?length!=0><font color="red">没有通过, 原因：<@msg.message key=auditMsg/>.</font><#else>
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
  </body>
<#include "/templates/foot.ftl"/>