<#include "/templates/head.ftl"/>
<link href="${static_base}/css/tableTree.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript" src="scripts/tableTree.js"></script>
<script> defaultColumn=1;</script>
<body>
<div>
  <table width="100%">
  <tr>
  <td valign="top">
  <table id="authorityBar"></table>
  <script>
   var bar = new ToolBar('authorityBar','我的权限',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@bean.message key="action.spread"/>","displayAllRowsFor(2);f_frameStyleResize(self)",'contract.gif');
   bar.addItem("<@bean.message key="action.collapse"/>","collapseAllRowsFor(2);f_frameStyleResize(self)",'expand.gif');
  </script>
 <table width="100%" class="listTable">
  <tbody>
  <tr class="darkColumn">
    <th width="5%"><input type="checkbox" onClick="treeToggleAll(this)" ></th>
    <th width="60%"><@bean.message key="attr.name"/></th>
    <th ><@bean.message key="attr.id"/></th>
    <th width="10%"><@bean.message key="attr.status"/></th>    
  </tr>
    <#macro i18nTitle(entity)><#if localName?index_of("en")!=-1><#if entity.engTitle?if_exists?trim=="">${entity.title?if_exists}<#else>${entity.engTitle?if_exists}</#if><#else><#if entity.title?if_exists?trim!="">${entity.title?if_exists}<#else>${entity.engTitle?if_exists}</#if></#if></#macro>       	
    <#list menus as menu>
	<#assign tdid="1-">
    <#if menu.code?length!=1>
	    <#list 1..menu.code?length as menuIdChar>
	        <#if menuIdChar%2==1>
	        <#assign tdid = tdid + menu.code[menuIdChar-1..menuIdChar] +"-">
	        </#if>
	    </#list>
    </#if>
    <#assign tdid = tdid[0..tdid?length-2]>
    
    <tr class="grayStyle"  
	  <#if (menu.code?length>2)> style="display: none;"</#if> id="${tdid}">
	   <td   class="select">
    	   <input type="checkBox" onclick="treeToggle(this)" name="menuId" value="${menu.id}">
       </td>
	   <td>
	    <div class="tier${menu.code?length/2}">
           <#if (menu.code?length/2)==3>
           <a href="#" class="doc"> </a>
           <#else>
           <a href="#" class="folder" onclick="toggleRows(this);f_frameStyleResize(self)"></a>
           </#if>
	       <#if (menu.code?length==6)>
            <A href="javascript:dataRealmList('${menu.id}')" title="<@bean.message key="entity.dataRealm"/>"> <@i18nTitle menu/></a>
           <#else>
            <@i18nTitle menu/>
           </#if>
	    </div>
	   </td>
       <td >&nbsp;${menu.code}</td>
       <td> <#if menu.enabled == true><@bean.message key="action.activate" /><#else><@bean.message key="action.unactivate" /></#if></td>
	  </tr>
	 </#list>
  </tbody>
 </table>
</div>
   </td>
    <td id="dataRealmTD" style="width:220px" valign="top" >
     <iframe  src="pages/system/security/authority/dataRealmContent.ftl" id="dataRealmFrame" name="dataRealmFrame" 
      marginwidth="0" marginheight="0"
      scrolling="no" 	 frameborder="0"  height="100%" width="100%"></iframe>
    </td>
   </tr>
  </table>
<form name="restrictionForm" target="dataRealmFrame" method="post" action="" onsubmit="return false;"></form>
<script>
    function dataRealmList(menuId){
       document.restrictionForm.action = "preference.do?method=restrictions";
       document.restrictionForm.action += "&menu.id="+menuId;
       //数据权限需统一配置，这里暂时屏蔽
       //document.restrictionForm.submit();
    }
</script>
 </body>
<#include "/templates/foot.ftl"/>