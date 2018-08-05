<#include "/templates/head.ftl"/>
<#assign extraTR>
    <tr >
      <td  id="f_engName" class="title" >变动方式:</td>
      <td colspan="3"><@htm.i18nSelect name="baseCode.alterMode.id" selected="${(baseCode.alterMode.id)?default('')}" datas=alterModes style="width:150px"/></td>
    </tr>
</#assign>
<#include "commonForm.ftl"/>