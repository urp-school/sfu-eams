<#include "/templates/head.ftl"/>
<body>
  <table id="userInfoBar"></table>
     <table class="infoTable">
	   <tr>
	     <td class="title" >&nbsp;<@msg.message key="attr.name" />:</td>
	     <td class="content"> ${user.name?if_exists}</td>
	     <td class="title" >&nbsp;<@msg.message key="attr.creator" />:</td>
	     <td class="content">${(user.creator.name)?if_exists}  </td>
	   </tr>	   
	   <tr>
	     <td class="title" >&nbsp;<@msg.message key="attr.email" />:</td>
	     <td class="content">${user.email?if_exists} </td>
	     <td class="title" >&nbsp;<@msg.message key="attr.dateCreated" />:</td>
         <td class="content">${user.createAt?string("yyyy-MM-dd")}</td>	     
	   </tr> 
	   <tr>
	     <td class="title" >&nbsp;<@msg.message key="attr.status" />:</td>
	     <td class="content">
	        <#if user.state?default(1)==1> <@msg.message key="action.activate" />
	        <#else><@msg.message key="action.freeze" />
	        </#if>
	     </td>
	     <td class="title" >&nbsp;<@msg.message key="attr.dateLastModified" />:</td>
         <td class="content">${user.modifyAt?string("yyyy-MM-dd")}</td>	     
	   </tr>
	   <tr>
        <td class="title" >&nbsp;<@msg.message key="attr.figure" />:</td>
        <td  class="content" colspan="3">
        	<#list user.categories as category>${category.name}<#if category.id==user.defaultCategory.id>(默认)</#if><#if category_has_next>,</#if></#list>
        </td>
       </tr>
	   <tr>
        <td class="title" >&nbsp;<@msg.message key="entity.role" />:</td>
        <td  class="content" colspan="3">
             <#list user.roles?if_exists as role>
                  ${role.name}&nbsp;
             </#list>
         </td>
       </tr>
	   <tr>
        <td class="title" >&nbsp;<@msg.message key="attr.managedUser" />:</td>
        <td  class="content" colspan="3">
             <#list user.mngUsers?if_exists as user>
                  ${user.userName}(${user.name})&nbsp;
             </#list>
         </td>
       </tr>
	   <tr>
        <td class="title" >&nbsp;<@msg.message key="attr.managedRole" />:</td>
        <td  class="content" colspan="3">
             <#list user.mngRoles?if_exists as role>
                  ${role.name}&nbsp;
             </#list>
         </td>
       </tr>
	   <tr>
        <td class="title" >&nbsp;<@msg.message key="attr.managerUser" />:</td>
        <td  class="content" colspan="3">
             <#list user.managers?if_exists as user>
                  ${user.userName}(${user.name})&nbsp;
             </#list>
         </td>
       </tr>  
	   <tr>
        <td class="title" >&nbsp;<@msg.message key="attr.remark" />:</td>
        <td class="content" colspan="3">${user.remark?if_exists}</td>
       </tr>
       <tr>
         <td colspan="4">
        <iframe  src="restriction.do?method=info&restriction.holder.id=${user.id}&restrictionType=user" id="contentFrame" name="contentFrame" marginwidth="0" marginheight="0"
      scrolling="no" frameborder="0"  height="100%" width="100%">
        </iframe>
        </td>
       </tr>
      </table>
      <br><br>
  <script>
   var bar = new ToolBar('userInfoBar','<@msg.message key="entity.user" /><@msg.message key="common.detailInfo" />',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack("<@msg.message key="action.back"/>");    
  </script>
 </body>
<#include "/templates/foot.ftl"/>