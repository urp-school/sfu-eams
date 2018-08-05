<#include "/templates/head.ftl"/>
<link href="${static_base}/css/tableTree.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/tableTree.js"></script>
<script> defaultColumn=1;</script>
<script>
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("moduleId")));
    }
    function editDataRealm(URL){
       document.forDataRealmForm.action = URL;
       document.forDataRealmForm.submit();
    }
    function save(){
        document.authorityListForm.action="authority.do?method=saveResource&who=${result.who}&id=${result.id}&moduleIds=" +    getIds();
        if(confirm("<@bean.message key="prompt.authority.saveConfrim" arg0="${result.name}"/>"))
          document.authorityListForm.submit();
    } 
</script>
<body>
<div>
  <table width="100%">
  <tr>
  <td valign="top">
  <table id="authorityBar"></table>
  <script>
   var bar = new ToolBar('authorityBar','<#if result.who=="user"><@bean.message key="info.authority.moduleUser" arg0="${result.name}"/><#else><@bean.message key="info.authority.moduleRole" arg0="${result.name}"/></#if>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@bean.message key="action.spread"/>","displayAllRowsFor(2);f_frameStyleResize(self)",'contract.gif');
   bar.addItem("<@bean.message key="action.collapse"/>","collapseAllRowsFor(2);f_frameStyleResize(self)",'expand.gif');
   bar.addItem("<@msg.message key="action.save"/>",save,'save.gif');
  </script>
 <table width="100%" class="listTable">
  <form name="authorityListForm" method="post" action="" onsubmit="return false;">
  <tbody>
  <tr class="darkColumn">
    <th width="5%"><input type="checkbox" onClick="treeToggleAll(this)" checked></th>
    <th width="60%"><@bean.message key="attr.name"/></th>
    <th ><@bean.message key="attr.id"/></th>
    <th width="10%"><@bean.message key="attr.status"/></th>    
  </tr>
    <#macro i18nTitle(entity)><#if localName?index_of("en")!=-1><#if entity.engTitle?if_exists?trim=="">${entity.title?if_exists}<#else>${entity.engTitle?if_exists}</#if><#else><#if entity.title?if_exists?trim!="">${entity.title?if_exists}<#else>${entity.engTitle?if_exists}</#if></#if></#macro>       	
    <#list result.moduleList as module>
	<#assign tdid="1-">
    <#if module.code?length!=1>
	    <#list 1..module.code?length as moduleIdChar>
	        <#if moduleIdChar%2==1>
	        <#assign tdid = tdid + module.code[moduleIdChar-1..moduleIdChar] +"-">
	        </#if>
	    </#list>
    </#if>
    <#assign tdid = tdid[0..tdid?length-2]>
    
    <tr class="grayStyle"  
	  <#if (module.code?length>2)> style="display: none;"</#if> id="${tdid}">
	   <td   class="select">
    	   <input type="checkBox" onclick="treeToggle(this)" name="moduleId" ${result.moduleChecked[module.code]?if_exists} value="${module.id}">
       </td>
	   <td>
	    <div class="tier${module.code?length/2}">
           <#if module.isLeaf>
           <a href="#" class="doc"> </a>
           <#else>
           <a href="#" class="folder" onclick="toggleRows(this);f_frameStyleResize(self)"></a>
           </#if>
	       <#if (result.moduleChecked[module.code]?if_exists=="checked")&&(module.isLeaf)>
            <A href="javascript:editDataRealm('authority.do?method=editDataRealm&moduleId=${module.id}&who=${result.who}&id=${result.id}')" title="<@bean.message key="entity.dataRealm"/>"> <@i18nTitle module/></a>
           <#else>
            <@i18nTitle module/>
           </#if>
	    </div>
	   </td>
       <td >&nbsp;${module.code}</td>
       <td> <#if module.state == true><@bean.message key="action.activate" /><#else><@bean.message key="action.unactivate" /></#if></td>
	  </tr>
	 </#list>
  </tbody>
  </form>
 </table>
</div>
   </td>
   <td id="dataRealmTD" style="width:220px" valign="top" >
     <iframe  src="pages/system/security/authority/dataRealmContent.ftl" id="dataRealmFrame" name="dataRealmFrame" 
      marginwidth="0" marginheight="0"
      scrolling="no" 	 frameborder="0"  height="100%" width="100%"></iframe>
    </td>
   </tr>
   <form name="forDataRealmForm" target="dataRealmFrame" method="post" action="" onsubmit="return false;"></form>
  </table>
 </body>
<#include "/templates/foot.ftl"/>