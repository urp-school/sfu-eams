<#include "/templates/head.ftl"/>
<body>
<div align="center"><font color="red" size="5"><#if reason?exists><@bean.message key=reason/><#else>sorry! 你需要进行预答辩！！！</#if></font></div>
<div align="center"><#if flag?exists&&flag="topicOpen"><a href="loadThesisTopic_std.do?method=doLoadThesisTopic">[进入论文开题]</a></#if></div>
</body>
<#include "/templates/foot.ftl"/>