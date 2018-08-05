<#include "/templates/head.ftl"/>
<#assign extraTR>
    <tr>
      <td id="f_engName" class="title">是否必修课:</td>
      <td><@htm.radio2 name="baseCode.isCompulsory" value=baseCode.isCompulsory?default(false)/></td>	   
      <td id="f_engName" class="title">是否学位课:</td>
      <td><@htm.radio2 name="baseCode.isDegree" value=baseCode.isDegree?default(false)/></td>
    </tr>
    <tr>
      <td id="f_engName" class="title">是否模块课:</td>
      <td><@htm.radio2 name="baseCode.isModuleType" value=baseCode.isModuleType?default(false)/></td>
      <td id="f_engName" class="title">显示实践课:</td>
      <td><@htm.radio2 name="baseCode.isPractice" value=baseCode.isPractice?default(false)/></td>
    </tr>
    <tr>
      <td id="f_engName" class="title">显示优先级:</td>
      <td colspan="3"><input type="text" name="baseCode.priority" value="${baseCode.priority?default("")}" style="width:50px" maxLength="2">(数字小,排位靠前)</td>
    </tr>
</#assign>
<#include "commonForm.ftl"/>