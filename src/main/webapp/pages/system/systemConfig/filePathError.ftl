<#include "/templates/head.ftl"/>
<body>
<#assign labInfo>文档上传路径错误</#assign>
<#include "/templates/back.ftl"/>
<p>没有找到所配置的文件上传路径：</p>
<#if filePaths?exists>
 <#list filePaths as path>
 <li>${path}</li>
 </#list>
<#else>
<p><font color="red">${filePath}</font>。</p>
</#if>
</body>
<#include "/templates/foot.ftl"/>