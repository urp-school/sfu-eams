<#assign html=JspTaglibs["/WEB-INF/struts-html.tld"]>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"]>
<html>
 <head>
  <meta http-equiv="content-type" content="text/html; charset=utf-8">
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <title>教学管理信息系统</title>
  <link href="${static_base}/css/default.css" rel="stylesheet" type="text/css">
 </head>
 <script language="JavaScript" type="text/JavaScript" src="scripts/common/Common.js"></script>
 <script language="JavaScript" type="text/JavaScript" src="scripts/common/ToolBar.js"></script>
 <link href="${static_base}/css/toolBar.css" rel="stylesheet" type="text/css">
 <#if Session['org.apache.struts.action.LOCALE']?exists>
 <#assign localName= Session['org.apache.struts.action.LOCALE']>
 <#else>
 <#assign localName="zh">
 </#if>
 <#macro i18nName(entity)><#if localName?index_of("en")!=-1><#if entity.engName?if_exists?trim=="">${entity.name?if_exists}<#else>${entity.engName?if_exists}</#if><#else><#if entity.name?if_exists?trim!="">${entity.name?if_exists}<#else>${entity.engName?if_exists}</#if></#if></#macro>
 <#macro getMessage><#assign error><@html.errors/></#assign><div class="message fade-ffff00"  id="error">${error}</div><div class="message fade-ffff00"  id="message"><#if RequestParameters.messages?exists><#list RequestParameters.messages?split(",") as message><#if (message?length>2)><@bean.message key="${message}"/></#if></#list></#if></div></#macro>
 <#macro getBeanListNames(beanList)><#list beanList as bean>${bean.name}&nbsp;</#list></#macro>
 <#macro getTeacherNames(teachers)><@getBeanListNames teachers/></#macro>
 <#import "/templates/table.ftl" as table>
 <#import "/templates/htm.ftl" as htm>
 <#import "/templates/message.ftl" as msg>