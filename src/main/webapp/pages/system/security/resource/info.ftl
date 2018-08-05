<#include "/templates/head.ftl"/>
<body>
<table id="resourceInfoBar"></table>
     <table class="infoTable">
	   <tr>
	     <td class="title" >&nbsp;标题:</td>
	     <td class="content">${resource.title}</td>
	     <td class="title" >&nbsp;<@msg.message key="attr.name"/>:</td>
         <td class="content">${resource.name?if_exists}</td>
	   </tr>
       <tr>
        <td class="title" >&nbsp;<@msg.message key="attr.description"/>:</td>
        <td  class="content">${resource.description?if_exists}</td>
	    <td class="title">&nbsp;<@msg.message key="attr.status"/>:</td>
        <td class="content">
            <#if resource.enabled><@msg.message key="action.activate" /><#else><@msg.message key="action.unactivate"/></#if>
        </td>
       </tr>
       <tr>
	     <td class="title" >&nbsp;引用菜单:</td>
         <td class="content" colspan="3"><#list menus as menu>(${menu.code})${menu.title}<br></#list></td>
       </tr>
       <tr>
	     <td class="title">适用用户：</td>
	     <td>
	      <#list categories as category>
	       <#if resource.categories?seq_contains(category)>[${category.name}]</#if>
	      </#list>
	   </tr>
      </table>
      <#list resource.patterns as pattern>
      <fieldSet  align=center> 
 	   <legend>数据权限</legend>
	   <table class="infoTable">
	   <tr>
	     <td class="title" >&nbsp;模式名称:</td>
	     <td class="content">${pattern.name}</td>
	     <td class="title" >&nbsp;描述:</td>
	     <td class="content">${pattern.description}</td>
	   </tr>
	   <tr>
	     <td class="title" >&nbsp;限制模式:</td>
         <td class="content">${pattern.content}</td>
         <td class="title" >&nbsp;参数列表:</td>
         <td class="content"><#list pattern.params as p>${p.name}(${p.description})&nbsp;</#list></td>
	   </tr>
       </table>
	  </fieldSet>
	  </#list>
  <script>
   var bar = new ToolBar('resourceInfoBar','<@msg.message key="security.resource.info"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack("<@msg.message key="action.back"/>");  
  </script>
 </body>
<#include "/templates/foot.ftl"/>