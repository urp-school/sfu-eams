<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@msg.message  key="validator.js.url" />"></script>
<link href="${static_base}/css/tableTree.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript" src="scripts/common/TableTree.js"></script>
<script> defaultColumn=1;</script>
<script>
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("menuId")));
    }
    function save(){
        document.actionForm.action="menuAuthority.do?method=save";
        if(confirm("<@msg.message  key="prompt.authority.saveConfrim" arg0="${ao.name}"/>")){
           document.actionForm.submit();
        }
    }
    function changeProfile(){
        document.actionForm.action="menuAuthority.do?method=editAuthority";
        document.actionForm.submit();
    }
</script>
<body>
<div>
  <table width="100%">
  <tr>
  <td valign="top">
  <table id="authorityBar"></table>
  <script>
   var bar = new ToolBar('authorityBar','<#if RequestParameters['who']=="user"><@msg.message  key="info.authority.moduleUser" arg0="${ao.name}"/><#else><@msg.message  key="info.authority.moduleRole" arg0="${ao.name}"/></#if>',null,true,true);
   bar.setMessage('<@getMessage/>');

	bar.addItem("<@msg.message  key="action.spread"/>","displayAllRowsFor(2);f_frameStyleResize(self)",'contract.gif');
	bar.addItem("<@msg.message  key="action.collapse"/>","collapseAllRowsFor(2);f_frameStyleResize(self)",'expand.gif');
	
   bar.addBack("<@msg.message  "action.back"/>");
   bar.addItem("<@msg.message  "action.save"/>",save,'save.gif');
   
   
   function selectListen(targetId){
   	var tempTarget ;
   	tempTarget = document.getElementById(targetId);
   	if(tempTarget!=null||tempTarget!='undefined'){
	   	var stats = tempTarget.checked;
	   	var num=0;
	   	var tempId = targetId+'_'+num;
	   	while(tempTarget!=null){//||tempTarget!='undefined'
	   		num++;
	   		tempTarget.checked = stats;
	   		tempTarget = document.getElementById(tempId);
	   		tempId = targetId+'_'+num;
	   	}
   	}
   }
  </script>
  
  <table width="100%" class="searchTable">
    <form name="actionForm" method="post" action="menuAuthority.do?method=editAuthority" onsubmit="return false;">
       <input type="hidden" name="who" value="${RequestParameters['who']}"/>
       <input type="hidden" name="id" value="${ao.id}"/>
	   <tr>
	    <td class="title">菜单配置</td>
	    <td><select name="menuProfileId" style="width:200px;" onchange="changeProfile();">
	        <#list menuProfiles as profile>
	        <option value="${profile.id}" <#if profile.id?string=RequestParameters['menuProfileId']?default('')>selected</#if>>${profile.name}</optino>
	        </#list>
	        </select>
	    </td>
	   </tr>
  </table>
  
  <table width="100%" class="listTable">

  <tbody>
  <tr class="darkColumn">
    <th width="5%"><input type="checkbox" onClick="treeToggleAll(this)"></th>
    <th width="40%"><@msg.message  key="attr.name"/></th>
    <th width="10%"><@msg.message  key="attr.id"/></th>
    <th width="20%">可用资源</th> 
    <th width="8%"><@msg.message  key="attr.status"/></th>
  </tr>
    <#macro i18nTitle(entity)><#if localName?index_of("en")!=-1><#if entity.engTitle?if_exists?trim=="">${entity.title?if_exists}<#else>${entity.engTitle?if_exists}</#if><#else><#if entity.title?if_exists?trim!="">${entity.title?if_exists}<#else>${entity.engTitle?if_exists}</#if></#if></#macro>
    <#list menus?sort_by("code") as menu>
	<#assign tdid="1-">
    <#if menu.code?length!=1>
	    <#list 1..menu.code?length as menuIdChar>
	        <#if menuIdChar%2==1>
	        <#assign tdid = tdid + menu.code[menuIdChar-1..menuIdChar] +"-">
	        </#if>
	    </#list>
    </#if>
    <#assign tdid = tdid[0..tdid?length-2]>
    
    
    <tr class="grayStyle" id="${tdid}">
	   <td   class="select">
    	   <input type="checkBox" id="checkBox_${menu_index}" onclick="treeToggle(this);selectListen('checkBox_${menu_index}');" name="menuId" <#if (aoMenus?exists)&&(aoMenus?seq_contains(menu))>checked</#if> value="${menu.id}">
       </td>
	   <td>
	    <div class="tier${menu.code?length/2}">
           <#if menu.isLeaf>
           <a href="#" class="doc"></a><#rt>
           <#else>
           <a href="#" class="folder" onclick="toggleRows(this);f_frameStyleResize(self)"></a><#rt>
           </#if>
	       <#if menu.isLeaf>
             <@i18nTitle menu/>
           <#else>
            <@i18nTitle menu/>
           </#if>
	    </div>
	   </td>
       <td >&nbsp;${menu.code}</td>
       <td>
       	<#list menu.resources as resource>
       	   <#if resources?seq_contains(resource)><input type="checkBox" name="resourceId" id="checkBox_${menu_index}_${resource_index}" <#if aoResources?seq_contains(resource)>checked</#if> value="${resource.id}"><#rt><#if ((resource.patterns?size)>0)&&aoResources?seq_contains(resource)><a href="restriction.do?method=info&forEdit=1&restrictionType=${RequestParameters['who']}&restriction.holder.id=${aoResourceAuthorityMap[resource.id?string]}" target="restictionFrame"><font color="red">${resource.title}</font></a><#rt><#else><#lt>${resource.title}</#if><#if resource_has_next><br></#if>
       	   </#if>
       	</#list>
       </td>
       <td> <#if menu.enabled><@msg.message  key="action.activate" /><#else><@msg.message  key="action.unactivate" /></#if></td>
	  </tr>
	 </#list>
  </tbody>
  </form>
 </table>
</div>
   </td>
   <td id="dataRealmTD" style="width:300px" valign="top" >
     <iframe  src="restriction.do?method=tip" id="restictionFrame" name="restictionFrame" 
      marginwidth="0" marginheight="0"
      scrolling="no" 	 frameborder="0"  height="100%" width="100%"></iframe>
    </td>
   </tr>
  </table>
 </body>
<#include "/templates/foot.ftl"/>