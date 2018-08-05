<#include "/templates/head.ftl"/>
<#include "/pages/course/grade/gradeMacros.ftl"/>
<body>
 	<#if RequestParameters['request_locale']?default('zh_CN')=='zh_CN'>
     	<#include "../report/stdGradeReport/template/" + (setting.template)?default('default') + "_zh.ftl"/>
    <#else>
        <#include "../report/stdGradeReport/template/" + (setting.template)?default('default') + "_en.ftl"/>
    </#if>
</body>
<#include "/templates/foot.ftl"/>