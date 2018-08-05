<#include "/templates/head.ftl"/>
<body>
<table id="menuInfoBar"></table>
     <table class="infoTable">
	   <tr>
	     <td class="title" >&nbsp;<@msg.message key="attr.id"/>:</td>
	     <td class="content">${menu.code}  </td>
	     <td class="title" >&nbsp;<@msg.message key="attr.modulePath"/>:</td>
         <td class="content">${menu.entry?if_exists}</td>
	   </tr>	   
	   <tr>
	     <td class="title" >&nbsp;标题:</td>
	     <td class="content">${menu.title}</td>
   	     <td class="title" >&nbsp;英文标题:</td>
	     <td class="content">${menu.engTitle?if_exists}</td>
       </tr>
       <tr>
        <td class="title" >&nbsp;<@msg.message key="attr.description"/>:</td>
        <td  class="content" >${menu.description?if_exists}</td>
	    <td class="title" >&nbsp;<@msg.message key="attr.status"/>:</td>
        <td class="content">
            <#if menu.enabled><@msg.message key="action.activate" /><#else><@msg.message key="action.unactivate"/></#if>
        </td>
       </tr>
       <tr>
	     <td class="title" >&nbsp;引用资源:</td>
         <td class="content" colspan="3"><#list menu.resources as resource>(${resource.name})${resource.title}<br></#list></td>
       </tr>
      </table>
   <form name="menuInfoForm" method="post"></form>
  <script>
  function edit(){
   document.menuInfoForm.action="menu.do?method=edit&menuId=${menu.id}";
   document.menuInfoForm.submit();
  }
   var bar = new ToolBar('menuInfoBar','<@msg.message key="info.module.detail"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack("<@msg.message key="action.back"/>");  
  </script>
 </body>
<#include "/templates/foot.ftl"/>