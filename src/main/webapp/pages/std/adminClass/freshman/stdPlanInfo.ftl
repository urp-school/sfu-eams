<#include "/templates/head.ftl"/>
<body>
<#assign labInfo><@msg.message key="plan.planInformation"/></#assign>  
<#include "/templates/back.ftl"/>
<#list teachPlanList as teachPlan>
<#include "/pages/course/plan/teachPlanSearch/planInfoTable.ftl"/>
</#list>
<#if teachPlanList?size==0>
<@msg.message key="task.noPlanInfo"/>
</#if>
 </body>
<#include "/templates/foot.ftl"/>