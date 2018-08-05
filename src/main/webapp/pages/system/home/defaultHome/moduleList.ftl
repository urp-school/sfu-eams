<#include "/templates/simpleHead.ftl"/>
<link href="${static_base}/css/dynaTree.css" type="text/css" rel="stylesheet">
<script language="javascript" src="scripts/SelectableTree.js"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/common/ieemu.js"></script> 
<body style="overflow-x:auto;" LEFTMARGIN="0" TOPMARGIN="0" MARGINWIDTH="0" MARGINHEIGHT="0" BGCOLOR="#EEEEEE">
 <#macro i18nNameTitle(entity)><#if localName?index_of("en")!=-1><#if entity.engTitle?if_exists?trim=="">${entity.title?if_exists}<#else>${entity.engTitle?if_exists}</#if><#else><#if entity.title?if_exists?trim!="">${entity.title?if_exists}<#else>${entity.engTitle?if_exists}</#if></#if></#macro>
 <table>
  <tr>
   <td height="10"></td>
  </tr>
  <tr>
   <td>
	  <SCRIPT language="javascript">
		var menuTree = new MenuToc_toc('menuTree');
		menuTree.multiSelect = true;
		menuTree.selectChildren = false;
		menuTree.styleSelected = 'MenuTocSelected';
		menuTree.styleNotSelected = 'MenuTocItemLinkStyle';
		menuTree.onClick = '';
		menuTree.styleItemLink = 'MenuTocItemLinkStyle';
		menuTree.styleItemNoLink = 'MenuTocItemNoLinkStyle';
		menuTree.styleItemFolderLink = 'MenuTocItemFolderLinkStyle';
		menuTree.styleItemFolderNoLink = 'MenuTocItemFolderNoLinkStyle';
		menuTree.showRoot = false;
		menuTree.showIcons = true;
		menuTree.showTextLinks = true;
		menuTree.iconWidth = '24';
		menuTree.iconHeight = '22';
		menuTree.iconPath = '';
		iconPath='${static_base}/'
		menuTree.iconEmpty = iconPath+'/images/tree/empty.gif';
		menuTree.iconPlus = iconPath+'/images/tree/plus.gif';
		menuTree.iconPlus1 = iconPath+'/images/tree/plus.gif';
		menuTree.iconPlus2 = iconPath+'/images/tree/plus.gif';
		menuTree.iconMinus = iconPath+'/images/tree/minus.gif';
		menuTree.iconMinus1 = iconPath+'/images/tree/minus.gif';
		menuTree.iconMinus2 = iconPath+'/images/tree/minus.gif';
		menuTree.iconLine1 = iconPath+'/images/tree/line.gif';
		menuTree.iconLine2 = iconPath+'/images/tree/line.gif';
		menuTree.iconLine3 = iconPath+'/images/tree/line.gif';
		menuTree.iconItem = iconPath+'/images/tree/sanjiao.gif';
		menuTree.iconFolderCollapsed = 'images/tree/entityfolder.gif';
		menuTree.iconFolderExpanded = 'images/tree/entityfolder.gif';
		<#assign rootCode='0'>
		<#if (moduleTree?size>0)>
		   <#assign firstMenu=moduleTree?first>
		   <#if (firstMenu.code?length>2)>
		         <#assign rootCode=firstMenu.code[0..firstMenu.code?length-3]>
		   </#if>
        </#if>
        <#assign nodeIndex=0>
		menuTree.node${rootCode?if_exists} = menuTree.makeFolder('<@bean.message key="system.root"/>','','','','1');
		<#list (moduleTree?if_exists)?sort_by("code") as module>
		<#if module.entry?default("")=='home.do?method=welcome'><#else>
		 <#assign nodeIndex=nodeIndex+1>		 
		 <#if (module.entry?exists&&module.entry?length!=0)>
		     <#assign moduleEntry=module.entry/>
		     <#if module.entry?starts_with("attend")><#assign moduleEntry="http://portal.shfc.edu.cn/eams-kq/"+module.entry/></#if>
		     menuTree.node${module.code} = menuTree.makeItem("<@i18nNameTitle module/>",'${moduleEntry}','main','',"<@i18nNameTitle module/>", 'javascript:menuTree.nodeClickedAndSelected(${module_index + 1})');
		     menuTree.insertNode(menuTree.node${module.code[0..module.code?length-3]}, menuTree.node${module.code});
         <#else>
		     menuTree.node${module.code} = menuTree.makeFolder("<@i18nNameTitle module/>",'javascript:menuTree.nodeClicked(${nodeIndex})','','',"<@i18nNameTitle module/>");
		     <#if module.code?length==2>
		         <#assign parentCode='0'>
		     <#else>
		         <#assign parentCode=module.code[0..module.code?length-3]>
		     </#if>
		     menuTree.insertNode(menuTree.node${parentCode}, menuTree.node${module.code});
		  </#if>
		  </#if>
		</#list>
		menuTree.display(menuTree.node${parentCode?if_exists},1);
	  </SCRIPT>
   </td>
  </tr>
 </table>
<body>
<#include "/templates/foot.ftl"/>