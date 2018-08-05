<#include "/templates/head.ftl"/>
 <body>
    <#assign labInfo>规则详细信息</#assign> 
	<#include "/templates/back.ftl"/>
	<#list ruleList as rule>
     <table class="infoTable">
	   <tr>
	     <td class="title">&nbsp;适用业务:</td>
	     <td class="content">${rule.business}</td>
	     <td class="title">&nbsp;<@bean.message key="attr.infoname"/>:</td>
	     <td class="content">${rule.name?if_exists}</td>	     
	   </tr>
	   <tr>
	     <td class="title">&nbsp;服务名:</td>
	     <td colspan="3" class="content">${rule.serviceName?if_exists}</td>
	   </tr>
	   <tr>
	     <td class="title">&nbsp;规则描述:</td>
	     <td colspan="3" class="content">${rule.description}</td>
	   </tr>  
	   <tr>
	     <td class="title">&nbsp;管理容器:</td>
	     <td class="content">${rule.factory}</td>
	     <td class="title">&nbsp;<@bean.message key="attr.state"/>:</td>	
         <td class="content">
      	      <#if rule.state?if_exists == true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if>
         </td>
	   </tr>	   
	   <tr>
        <td class="title">&nbsp;<@bean.message key="attr.createAt"/>:</td>
        <td class="content">${(rule.createdAt?string("yyyy-MM-dd"))?if_exists}</td>
        <td class="title">&nbsp;<@bean.message key="attr.modifyAt"/>:</td>
        <td class="content">${(rule.updatedAt?string("yyyy-MM-dd"))?if_exists}</td>        
       </tr> 
      </table>
      <table class="infoTable">
       <tr><td colspan="5" align="center">规则参数列表</td></tr>
       <tr>
        <td align="center">&nbsp;参数名称:</td>
        <td align="center">&nbsp;参数标题:</td>
        <td align="center">&nbsp;参数描述:</td>
        <td align="center">&nbsp;参数类型:</td>
        <td align="center">&nbsp;上级参数:</td>
       </tr>
       <#list rule.params as param>
       <tr>
        <td align="center">${(param.name)?if_exists}</td>
        <td align="center">${(param.title)?if_exists}</td>
        <td align="center">${(param.description)?if_exists}</td>
        <td align="center">${(param.type)?if_exists}</td>
        <td align="center">${(param.parent.name)?if_exists}</td>
       </tr>
       </#list>
      </table>
	</#list>
  </body>
<#include "/templates/foot.ftl"/>