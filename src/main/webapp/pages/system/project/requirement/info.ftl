<#include "/templates/head.ftl"/>
<body  LEFTMARGIN="0" TOPMARGIN="0">
<#assign labInfo>系统需求详细信息</#assign>
<#include "/templates/back.ftl"/>
<#include "code.ftl"/>
  <table class="infoTable">
    <tr>
      <td  id="f_module" class="title">模块名称:</td>
      <td class="content" >${require.module?if_exists}
      </td>
      <td  id="f_developers" class="title">负责人:</td>
      <td  class="content">${require.developers?if_exists}</td>
    </tr>
    <tr>
      <td  id="f_fromUser" class="title">建议人:</td>
      <td  class="content">${require.fromUser?if_exists}</td>
      <td  id="f_stackHolders" class="title">其他相关人:</td>
      <td  class="content">${require.stackHolders?if_exists}</td>
     </tr>
    <tr>
      <td  class="title">优先级:</td>
      <td  class="content">${priorityMap[require.priority?string]}</td>
      <td   class="title">类型:</td>
      <td  class="content">${typeMap[require.type?string]}</td>
     </tr>
     <tr>
       <td  id="f_background" class="title">意见背景:</td>
       <td  class="content" colspan="3">${require.background?if_exists}</td>
     </tr>
     <tr>
       <td  id="f_content" class="title">意见内容:</td>
       <td  class="content" colspan="3">${require.content?if_exists}</td>    
     </tr>
     <tr>
       <td  id="f_suggestSolution" class="title">建议方案:</td>
       <td  class="content" colspan="3" >${require.suggestSolution?if_exists}
       </td>    
     </tr>
    <tr >
      <td  class="title">状态: </td>
      <td class="content">${statusMap[require.status?string]}</td>    
      <td  class="title">估计工作量(人/日):</td>
      <td class="content" >${require.workload?if_exists}</td>
    </tr>
    <tr >
      <td class="title">计划完成时间: </td>
      <td  class="content">${(require.planCompleteOn?string("yyyy-MM-dd"))?if_exists}</td>    
      <td  class="title">实际完成时间:</td>
      <td class="content" >${(require.actualCompleteOn?string("yyyy-MM-dd"))?if_exists}</td>
    </tr>
    <tr >
      <td class="title"><@bean.message key="attr.createAt" />: </td>
      <td  class="content">${(require.createdOn?string("yyyy-MM-dd"))?if_exists}</td>    
      <td  class="title">  <@bean.message key="attr.modifyAt" />:</td>
      <td class="content" >${(require.lastModifiedOn?string("yyyy-MM-dd"))?if_exists}</td>
    </tr>
  </table>
 </body> 
