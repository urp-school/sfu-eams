  <#assign error><@html.errors/></#assign>
  <div class="message fade-ffff00"  id="error">${error}</div>
  <#if RequestParameters.messages?exists>
  <div class="message fade-ffff00"  id="message">
  <#list RequestParameters.messages?split(",") as message>
     <#if (message?length>2)><@bean.message key="${message}"/></#if>
  </#list>
  </div>
  </#if>
