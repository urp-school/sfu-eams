<#include "/templates/head.ftl"/>
<#assign extraTR>
    <tr >
      <td  id="f_engName" class="title">参加考试:</td>
      <td colspan="3"><@htm.radio2 name="baseCode.isAttend" value=baseCode.isAttend?default(false)/></td>
    </tr>
</#assign>
<#include "commonForm.ftl"/>