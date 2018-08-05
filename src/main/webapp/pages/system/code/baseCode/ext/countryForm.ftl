<#include "/templates/head.ftl"/>
<#assign extraTR>
    <tr >
      <td  id="f_engName" class="title">简名:</td>
      <td ><input type="text" name="baseCode.shortName" value="${baseCode.shortName?default("")}" maxlength="25"/></td>	   
      <td  id="f_engName" class="title">简名(英):</td>
      <td ><input type="text" name="baseCode.shortEngName"value="${baseCode.shortEngName?default("")}" maxlength="50"/></td>
    </tr>
    <tr>
      <td  id="f_engName" class="title">简码:</td>
      <td colspan="3"><input type="text" name="baseCode.shortCode" maxlength="32" value="${baseCode.shortCode?default("")}"></td>
    </tr>
</#assign>
<#include "commonForm.ftl"/>