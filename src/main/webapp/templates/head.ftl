<#assign html=JspTaglibs["/WEB-INF/struts-html.tld"]>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"]>
<#assign cewolf=JspTaglibs["/WEB-INF/cewolf.tld"]>
<html>
 <head>
  <meta http-equiv="content-type" content="text/html; charset=utf-8">
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <title>教学管理信息系统</title>
  <link href="${static_base}/css/default.css" rel="stylesheet" type="text/css">
  <link href="${static_base}/css/messages.css" rel="stylesheet" type="text/css">
  <link href="${static_base}/css/toolBar.css" rel="stylesheet" type="text/css">
 </head>
 <script language="JavaScript" type="text/JavaScript" src="scripts/StringUtils.js"></script>
 <script language="JavaScript" type="text/JavaScript" src="scripts/common/Common.js"></script>
 <script language="JavaScript" type="text/JavaScript" src="scripts/common/Table.js"></script>
 <script language="JavaScript" type="text/JavaScript" src="scripts/common/ToolBar.js"></script>
 <script language="JavaScript" type="text/JavaScript" src="scripts/Calendar.js"></script>
 <script language="JavaScript" type="text/JavaScript" src='dwr/engine.js'></script>
 <script language="JavaScript" type="text/JavaScript" src='dwr/util.js'></script>
 <script language="JavaScript" type="text/JavaScript" src="scripts/prototypes.js"></script>
 <script language="JavaScript" src="<@bean.message key="menu.js.url"/>"></script>
 <#import "/templates/table.ftl" as table>
 <#import "/templates/htm.ftl" as htm>
 <#import "/templates/message.ftl" as msg>
 
 <#if RequestParameters['request_locale']?exists>
 <#assign localName= RequestParameters['request_locale']/>
 <#else>
   <#if Session['org.apache.struts.action.LOCALE']?exists>
   <#global localName= Session['org.apache.struts.action.LOCALE']>
   <#else>
   <#global localName="zh">
   </#if>
 </#if>
 <#macro i18nName(entity)><#if localName?index_of("en")!=-1><#if entity.engName?if_exists?trim=="">${entity.name?if_exists}<#else>${entity.engName?if_exists}</#if><#else><#if entity.name?if_exists?trim!="">${entity.name?if_exists}<#else>${entity.engName?if_exists}</#if></#if></#macro>
 <#macro localAttrName(entityName)><#if localName?index_of("en")!=-1>#{entityName}.engName<#else>${entityName}.name</#if></#macro>
 <#macro yesOrNoOptions(selected)>
 	<option value="0" <#if "0"==selected> selected </#if> ><@bean.message key="common.no" /></option> 
    <option value="1" <#if "1"==selected> selected </#if>><@bean.message key="common.yes" /></option> 
    <option value="" <#if ""==selected> selected </#if>><@bean.message key="common.all" /></option> 
 </#macro>
 <#macro eraseComma(nameSeq)><#if (nameSeq?length>2)>${nameSeq[1..nameSeq?length-2]}<#else>${nameSeq}</#if></#macro>
 <#macro getBeanListNames(beanList)><#list beanList as bean>${bean.name}<#if bean_has_next> </#if></#list></#macro>
 <#macro getTeacherNames(teachers)><@getBeanListNames teachers/></#macro>
 
 <#function sort_byI18nName entityList>   
   <#return sort_byI18nNameWith(entityList,"")>
 </#function>
 
 <#function sort_byI18nNameWith entityList nestedAttr>
   <#local name="name">
   <#if nestedAttr!="">
      <#local name=[nestedAttr,name]/>
   </#if>
   <#return entityList?sort_by(name)>
 </#function> 
 <#macro getMessage><#assign error><@html.errors/></#assign><div class="message fade-ffff00"  id="error">${error}</div><div class="message fade-ffff00"  id="message"><#if RequestParameters.messages?exists><#list RequestParameters.messages?split(",") as message><#if (message?length>2)><@bean.message key="${message}"/></#if></#list></#if></div></#macro>
 <#macro searchParams><input name="params" type="hidden" value="${RequestParameters['params']?default('')}"></#macro>
 <script>
 try{
 if (window.addEventListener) {window.addEventListener("load", adaptFrameSize, false);}else if (window.attachEvent) {window.attachEvent("onload", adaptFrameSize);}else {window.onload = adaptFrameSize;}
 }catch(e){}
 </script>