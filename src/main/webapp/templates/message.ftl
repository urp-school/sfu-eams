<#assign html=JspTaglibs["/WEB-INF/struts-html.tld"]>
<#assign bean=JspTaglibs["/WEB-INF/struts-bean.tld"]>
<#macro message key extra...>
	<#if (extra?size=0)>
		<@bean.message key="${key}"/>
	<#elseif (extra?size=1)>
		<@bean.message key="${key}" arg0="${extra['arg0']}"/>
	</#if>
</#macro>
 <#macro getMessage>
<#assign error><@html.errors/></#assign>
  <div class="message fade-ffff00"  id="error">${error}</div>
  <#if RequestParameters.messages?exists>
  <div class="message fade-ffff00"  id="message">
  <#list RequestParameters.messages?split(",") as message>
     <#if (message?length>2)><@bean.message key="${message}"/></#if>
	 <#if (message?length>2)><@message key="${message}"/></#if>
  </#list>
  </div>
  </#if>
</#macro>
