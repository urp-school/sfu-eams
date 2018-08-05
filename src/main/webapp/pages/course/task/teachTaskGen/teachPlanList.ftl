<#if RequestParameters['active']=='true'>
<#include "activeTeachPlanList.ftl"/>
<#else>
<#include "unActiveTeachPlanList.ftl"/>
</#if>
<p style="font-weight: bold"><@msg.message key="teachPlan.generate.onPlanExplain"/></p>
<#list 1..2 as i><br></#list>