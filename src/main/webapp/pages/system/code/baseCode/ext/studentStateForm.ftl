<#include "/templates/head.ftl"/>
<#assign extraTR>
    <tr >
      <td  id="f_isInSchool" class="title">是否在校:</td>
      <td colspan="3"><@htm.radio2 name="baseCode.inSchool" value=baseCode.inSchool?default(false)/></td>
    </tr>
</#assign>
<#include "commonForm.ftl"/>