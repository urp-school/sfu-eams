<#include "/templates/head.ftl"/>
<body>
<div align="center"><font color="red" size="5"><#list errors?split(",") as error><@bean.message key=error arg0=studentName/><br></#list></font></div>
</body>
<#include "/templates/foot.ftl"/>