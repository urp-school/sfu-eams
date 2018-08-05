<#if std??>
[{stumum:"${std.code}", name:"${std.name?js_string}", sex:"${(std.gender.name?js_string)!}", stdType:"${(std.stdType.name?js_string)!}", department:"${(std.department.name?js_string)!}", major:"${(std.major.name?js_string)!}"}]
<#else>
[{stumum:"", name:"", sex:"", stdType:"", department:"", major:""}]
</#if>