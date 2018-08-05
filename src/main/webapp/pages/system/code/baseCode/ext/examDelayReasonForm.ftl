<#include "/templates/head.ftl"/>
<#assign extraTR>
    <tr >
      <td  id="f_engName" class="title">允许申请:</td>
      <td colspan="3"><@htm.radio2 name="baseCode.canApply" value=baseCode.canApply?default(false)/></td>
    </tr>
</#assign>
<#include "commonForm.ftl"/>