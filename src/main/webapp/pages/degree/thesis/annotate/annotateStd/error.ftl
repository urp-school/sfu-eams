<#include "/templates/head.ftl"/>
<body>
<div align="center"><font color="red" size="5"><#if reason?exists><@bean.message key=reason?if_exists/></#if></font>
<br><#if flag?exists&&"topicOpen"==flag>[<a href="loadThesisTopic_std.do?method=doLoadThesisTopic">进入论文开题]</#if></div>
</body>
<#include "/templates/foot.ftl"/>