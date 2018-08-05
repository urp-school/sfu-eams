<#include "/templates/head.ftl"/>
<link href="${static_base}/css/tableTree.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript" src="scripts/tableTree.js"></script>

 <body>
 <table id="authorityInfoBar"></table> 
 <div>
 <table width="100%" class="listTable">
  <tbody>
  <form name="moduleInfoListForm" method="post" action="" onsubmit="return false;">
  <tr class="darkColumn">
    <!-- specifing one or more widths keeps columns constant despite changes in visible content -->  
    <th width="40%"><@bean.message key="attr.name"/></th>
    <th width="10%"><@bean.message key="attr.id"/></th>
    <th width="30%"><@bean.message key="entity.dataRealm"/></th>
    <th width="10%"><@bean.message key="attr.status" /></th>
  </tr>    	
   <#macro i18nNameTitle(entity)><#if localName?index_of("en")!=-1><#if entity.engTitle?if_exists?trim=="">${entity.title?if_exists}<#else>${entity.engTitle?if_exists}</#if><#else><#if entity.title?if_exists?trim!="">${entity.title?if_exists}<#else>${entity.engTitle?if_exists}</#if></#if></#macro>      
 　<#list result.authorityList?sort_by(["resource", "code"]) as authority>
	<#assign tdid="1-">
    <#if authority.resource.code?length!=1>
	    <#list 1..authority.resource.code?length as moduleIdChar>
	        <#if moduleIdChar%2==1>
	        <#assign tdid = tdid + authority.resource.code[moduleIdChar-1..moduleIdChar] +"-">
	        </#if>
	    </#list>
    </#if>
    <#assign tdid = tdid[0..tdid?length-2]>
    
    <tr class="grayStyle"  
	  <#if (authority.resource.code?length>2)> style="display: none;"</#if> id="${tdid}">
	   <td>
	    <div class="tier${authority.resource.code?length/2}">
           <#if (authority.resource.id?length/2)==3>
           <a href="#" class="doc"> </a>
           <#else>
           <a href="#" class="folder" onclick="toggleRows(this);f_frameStyleResize(self)"></a>
           </#if>	       
 	       <@i18nNameTitle authority.resource/>
	    </div>
	   </td>
       <td>&nbsp;${authority.resource.code}</td>
        <#if authority.dataRealm?exists>
        <td ><@bean.message key="common.setted"/></td>
        <#else>
        <td ><@bean.message key="common.null"/></td>
        </#if>
       <td ><#if authority.resource.state == true><@bean.message key="action.activate" /><#else><@bean.message key="action.unactivate"/></#if></td>
	  </tr>
	 </#list>
  </tbody>
  </form>
 </table>
</div>   

  <script>
  function edit(){
   document.moduleInfoListForm.action="authority.do?method=editResource&who=${result.who}&id=${result.id}";
   document.moduleInfoListForm.submit();
  }
   var bar = new ToolBar('authorityInfoBar','<@bean.message key="info.authority.detail"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@bean.message key="action.spread"/>","displayAllRowsFor(2);f_frameStyleResize(self)",'contract.gif');
   bar.addItem("<@bean.message key="action.collapse"/>","collapseAllRowsFor(2);f_frameStyleResize(self)",'expand.gif');
   bar.addItem("<@msg.message key="action.edit"/>",edit,'update.gif');   
  </script>
 </body>
<#include "/templates/foot.ftl"/>