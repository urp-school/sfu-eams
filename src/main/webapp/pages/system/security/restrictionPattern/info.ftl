<#include "/templates/head.ftl"/>
<body>
<table id="resourceInfoBar"></table>
     <table class="infoTable">
	   <tr>
	     <td class="title" >名称:</td>
	     <td class="content">${restrictionPattern.name}</td>
         <td class="title" ><@msg.message key="attr.description"/>:</td>
         <td  class="content">${restrictionPattern.description?if_exists}</td>
	     
	   </tr>
       <tr>
         <td class="title" >限制模式:</td>
         <td class="content" colspan="3">${restrictionPattern.pattern?if_exists}</td>
       </tr>
       <tr>
	     <td class="title" >使用参数:</td>
         <td class="content" colspan="3"><#list restrictionPattern.params as param>(${param.name})${param.description}<br></#list></td>
       </tr>
      </table>
  <script>
   var bar = new ToolBar('resourceInfoBar','<@msg.message key="security.restrictionPattern.info"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack("<@msg.message key="action.back"/>");  
  </script>
 </body>
<#include "/templates/foot.ftl"/>