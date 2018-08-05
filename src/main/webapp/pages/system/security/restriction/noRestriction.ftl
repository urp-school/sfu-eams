<#include "/templates/head.ftl"/>
<body>
<table width="100%" height="100%" border="0" cellpadding="0" style="font-size:12px"> 
  <tr>
    <td valign="top" >
    尚无数据权限限制数据.<br>
    <a href="restriction.do?method=editRestriction&resource.id=${RequestParameters['resource.id']}&create=1&who=${RequestParameters['who']}&id=${RequestParameters['id']}">单击此处创建</a>
    </td>
  </tr>
</table>
</body>
<#include "/templates/foot.ftl"/>
