<#include "/templates/head.ftl"/>
<body>
<table id="menuInfoBar"></table>
<table class="listTable" align="center" width="90%">
    <tr class="darkColumn">
     <td colspan="${depth}">模块标题</td>
     <td>英文名</td>
     <td>模块描述</td>
    </tr>
	<#list menus as menu>
	<tr><#if (((menu.code?length)/2)>1)><#list 1..((menu.code?length)/2)-1 as i><td>&nbsp;</td></#list></#if>
	<td>${menu.title}</td>
	<#if (depth-((menu.code?length)/2)>0)>
	<#list 1..(depth-((menu.code?length)/2)) as i><td></td></#list>
	</#if>
	<td>${menu.engTitle?default("")}</td>
	<td>${menu.description?default("")}</td>
	</tr>
	</#list>
</table>
  <script>
   var bar = new ToolBar('menuInfoBar','菜单列表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addPrint("<@msg.message "action.print"/>");
   bar.addClose("<@msg.message "action.close"/>");  
  </script>
 </body>
<#include "/templates/foot.ftl"/>