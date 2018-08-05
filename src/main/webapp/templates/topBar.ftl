<#macro i18nTitle(entity)><#if localName?index_of("en")!=-1><#if entity.engTitle?if_exists?trim=="">${entity.title?if_exists}<#else>${entity.engTitle?if_exists}</#if><#else><#if entity.title?if_exists?trim!="">${entity.title?if_exists}<#else>${entity.engTitle?if_exists}</#if></#if></#macro>       	
<#if result?exists&&result.modules?exists>
<#if (result.modules?size>0)>
<script language="JavaScript" src="<@bean.message key="menu.js.url"/>"></script>
<table id="topBar" width="${topBarWidth?default('100%')}" align="center"></table>
<script>
  var bar=new ToolBar('topBar','${title?default('管理工具')}',null,true,true);
  bar.setMessage('<@getMessage/>');
  <#if style?exists&&style=='line'>
    <#assign container='bar'/>
  <#else>
    <#assign container='menu'/>
    var menu = bar.addMenu("管理工具",null,'list.gif');
  </#if>
  <#if searchBarNeed?default(false)>
  	bar.addItem("搜索栏","MM_changeSearchBarStyle('searchBar');","search.gif");
  </#if>
  <#list result.modules?if_exists as module>
  <#if (module.entry?index_of('.do')!=-1)>
     ${container}.addItem('<@i18nTitle module/>','G("${module.entry?js_string}")');
  <#else>
     ${container}.addItem('<@i18nTitle module/>','${module.entry?js_string}');
  </#if>
  </#list>
  ${(extendScript)?default('')}
  <#if backNeed?default(true)>
  bar.addBack('<@bean.message key="action.back"/>');
  </#if>
  function G(url){
     self.window.location=url;
  }
</script>
</#if>
</#if>