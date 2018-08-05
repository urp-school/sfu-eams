<#include "/templates/head.ftl"/> 
<body>
 <table id="roleInfoBar"></table>
     <table class="infoTable">
	   <tr>
	     <td class="title"><@msg.message key="attr.name"/>:</td>
	     <td class="content"> ${role.name}</td>
	     <td class="title"><@msg.message key="attr.creator"/>:</td>
	     <td class="content">${role.creator.name?if_exists}  </td>
	   </tr>	   
	   <tr>
	     <td class="title" ><@msg.message key="attr.dateCreated"/>:</td>
         <td class="content">${role.createAt?string("yyyy-MM-dd")}</td>	     
	     <td class="title" ><@msg.message key="attr.dateLastModified"/>:</td>
         <td class="content">${role.modifyAt?string("yyyy-MM-dd")}</td>	     
	   </tr> 
	   <tr>
	   	<td class="title" >&nbsp;<@msg.message key="attr.status" />:</td>
 	    <td class="content">
	        <#if role.enabled> <@msg.message key="action.activate" />
	        <#else><@msg.message key="action.freeze" />
	        </#if>
	    </td>
       </tr>
       <tr>
       	<td class="title" >适用身份:</td>
        <td  class="content" colspan="3">${role.category.name} <#if role.stdType?exists>[${role.stdType.name?if_exists}]</#if></td>
       </td>
       </tr>
       <tr>
        <td class="title" ><@msg.message key="attr.ownedUser"/>:</td>
        <td  class="content" colspan="3"><#list role.users?sort_by("name") as user> ${user.userName}(${user.name})&nbsp;</#list></td>
       </tr>
	   <tr>
        <td class="title" ><@msg.message key="attr.manager"/>:</td>
        <td  class="content" colspan="3">
             <#list role.managers?if_exists as user>
                  ${user.userName}(${user.name})&nbsp;
             </#list>
        </td>
       </tr>
	   <tr>
        <td class="title" ><@msg.message key="attr.description"/>:</td>
        <td  class="content" colspan="3">${role.description?if_exists}</td>
       </tr>
       <tr>
         <td colspan="4">
        <iframe  src="restriction.do?method=info&restriction.holder.id=${role.id}&restrictionType=role" id="contentFrame" name="contentFrame" marginwidth="0" marginheight="0"
      scrolling="no" frameborder="0"  height="100%" width="100%">
        </iframe>
        </td>
       </tr>
      </table>
  <script>
   var bar = new ToolBar('roleInfoBar','<@msg.message key="info.role"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack("<@msg.message key="action.back"/>");  
  </script>
 </body>
<#include "/templates/foot.ftl"/>