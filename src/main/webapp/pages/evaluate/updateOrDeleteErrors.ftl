<#include "/templates/head.ftl"/>
<BODY >
<table  width="100%" height="20" border="0" cellpadding="0" cellspacing="0">
  <tr> 
    <td align="center">     
      <span class="contentTableTitleTextStyle">
       <@bean.message key="field.evaluate.errorsUpdateOrDelete" arg0="${result.departmentName?if_exists}"/>
      </span><br><br>
      [<a href="javascript:history.back();"> <@bean.message key="attr.backPage"/> </a>]
    </td>
  </tr>
</table>
</script>
</BODY>
<#include "/templates/foot.ftl"/>