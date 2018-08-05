<#include "/templates/head.ftl"/>
<body>
<#assign labInfo>编辑代码生成规则</#assign>
<#include "/templates/back.ftl"/>
  <table width="90%" align="center" class="infoTable">
    <tr>
      <td  id="f_codeName" class="title" width="20%">编码对象:</td>
      <td>${codeScript.codeName?if_exists}</td>
      <td  id="f_attr" class="title" width="20%">编码属性:</td>
      <td>${codeScript.attr?if_exists}</td>
    </tr>
    <tr>
      <td  id="f_codeClassName" class="title">编码类型:</td>
      <td colspan="3">${codeScript.codeClassName?if_exists}</td>
    </tr>
    <tr>
      <td  id="f_description" class="title">简要描述:</td>
      <td colspan="3">${codeScript.description?if_exists}</td>
    </tr>
    <tr>
       <td  id="f_script" class="title">编码脚本:</td>
       <td colspan="3">${codeScript.script?default("")}</td>
    </tr>
    <tr>
      <td class="title">  <@bean.message key="attr.createAt" />: </td>
      <td>${(codeScript.createAt?string("yyyy-MM-dd hh:mm:ss"))?if_exists}</td>    
      <td class="title">  <@bean.message key="attr.modifyAt" />:</td>
      <td>${(codeScript.modifyAt?string("yyyy-MM-dd hh:mm:ss"))?if_exists}</td>
    </tr>
  </table>
 </body> 
</html>