<#include "/templates/head.ftl"/>
<#assign extraTR>
    <tr >
      <td  id="f_engName" class="title" >上级项目类别:</td>
      <td colspan="3"><@htm.i18nSelect name="baseCode.superType.id" selected="${(baseCode.superType.id)?default('')}" datas=superTypes style="width:150px"/></td>
    </tr>
</#assign>
<#include "commonForm.ftl"/>