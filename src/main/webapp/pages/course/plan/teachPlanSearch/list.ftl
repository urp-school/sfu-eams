<#if RequestParameters['type']?exists&&RequestParameters['type']=="std">
<#include "stdPlanList.ftl"/>
<#else>
<#include "specialityPlanList.ftl"/>
</#if>
