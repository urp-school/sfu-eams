<#include "/templates/head.ftl"/>
<body  LEFTMARGIN="0" TOPMARGIN="0">
<table id="printBar"></table>
<script>
  var bar = new ToolBar("printBar","系统需求详细信息",null,true,true);
  bar.addItem("<@msg.message key="action.print"/>","print()");
  bar.addBack("<@msg.message key="action.back"/>");
</script>
<#include "code.ftl"/>
<style>
.title{
  font-size:18px;
  font-family:楷体_GB2312;
}
.project{
  font-size:13px;
  font-family:楷体_GB2312;
}
.report_title{
  font-size:16px;
  font-family:楷体_GB2312;
}
</style>
<#list requires as require>
  <div  class="title" align="center"><B>${systemConfig.company}</B></div>
  <div  class="title" align="center"><B>项目需求变更确认单</B></div>
  <table width="100%" class="project">
    <tr><td width="400px">项目名称:<U><@i18nName systemConfig.school/>${systemConfig.systemName}</U></td>
        <td></td>
        <td width="200px">文档编号:<U>xq${(require.createdOn?string("yyyy-MM-dd"))?if_exists}</U></td>
    </tr>
  </table>
  <table class="listTable" width="100%">
    <tr>
      <td  id="f_module" class="report_title" width="20%">模块名称:</td>
      <td class="content" width="30%" >${require.module?if_exists}</td>
      <td  id="f_developers" class="report_title" width="20%">负责人:</td>
      <td  class="content" width="30%">${require.developers?if_exists}</td>
    </tr>
    <tr>
      <td  id="f_fromUser" class="report_title">建议人:</td>
      <td  class="content">${require.fromUser?if_exists}</td>
      <td  id="f_stackHolders" class="report_title">其他相关人:</td>
      <td  class="content">${require.stackHolders?if_exists}</td>
     </tr>
    <tr>
      <td  class="report_title">优先级:</td>
      <td  class="content">${priorityMap[require.priority?string]}</td>
      <td   class="report_title">类型:</td>
      <td  class="content">${typeMap[require.type?string]}</td>
     </tr>
     <tr>
       <td  id="f_background" class="report_title">意见背景:</td>
       <td  class="content" colspan="3">${require.background?if_exists}</td>
     </tr>
     <tr>
       <td  id="f_content" class="report_title">意见内容:</td>
       <td  class="content" colspan="3">${require.content?if_exists}</td>    
     </tr>
     <tr>
       <td  id="f_suggestSolution" class="report_title">建议方案:</td>
       <td  class="content" colspan="3" >${require.suggestSolution?if_exists}</td>    
     </tr>
    <tr >
      <td  class="report_title">状态: </td>
      <td class="content">${statusMap[require.status?string]}</td>    
      <td  class="report_title">估计工作量(人/日)</td>
      <td class="content" >${(require.workload?string("#.##"))?if_exists}</td>
    </tr>
    <tr >
      <td class="report_title">计划完成于: </td>
      <td  class="content">${(require.planCompleteOn?string("yyyy-MM-dd"))?if_exists}</td>    
      <td  class="report_title">实际完成于:</td>
      <td class="content" >${(require.actualCompleteOn?string("yyyy-MM-dd"))?if_exists}</td>
    </tr>
    <tr >
      <td class="report_title">提交时间: </td>
      <td  class="content">${(require.createdOn?string("yyyy-MM-dd"))?if_exists}</td>    
      <td  class="report_title">  <@bean.message key="attr.modifyAt" />:</td>
      <td class="content" >${(require.lastModifiedOn?string("yyyy-MM-dd"))?if_exists}</td>
    </tr>
    <tr >
      <td class="report_title">客户其他意见: </td>
      <td  class="content" colspan="3"></td>
    </tr>
    <tr >
      <td class="report_title">需求确认: </td>
      <td class="content" colspan="3">客户方签字:</td>
    </tr>
  </table>
  <#if (require_index!=0&&(require_index+1)%3==0)>
    <div style='PAGE-BREAK-AFTER: always'></div>
  </#if>
 </#list>
 </body> 
</html>