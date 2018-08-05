<#include "/templates/head.ftl"/>
<body>
  <table id="userInfoBar"></table>
     <table class="infoTable">
	   <tr>
	     <td class="title" >&nbsp;<@bean.message key="attr.name" />:</td>
	     <td class="content"> ${user.name?if_exists}</td>
	     <td class="title" >&nbsp;<@bean.message key="attr.creator" />:</td>
	     <td class="content">${(user.creator.name)?if_exists}  </td>
	   </tr>	   
	   <tr>
	     <td class="title" >&nbsp;<@bean.message key="attr.email" />:</td>
	     <td class="content">${user.email?if_exists} </td>
	     <td class="title" >&nbsp;<@bean.message key="attr.createAt" />:</td>
         <td class="content">${user.createAt?string("yyyy-MM-dd")}</td>	     
	   </tr> 
	   <tr>
	     <td class="title" >&nbsp;<@bean.message key="attr.status" />:</td>
	     <td class="content">
	        <#if user.state?if_exists==1> <@bean.message key="action.activate" />
	        <#else><@bean.message key="action.freeze" />
	        </#if>
	     </td>
	     <td class="title" >&nbsp;<@bean.message key="attr.modifyAt" />:</td>
         <td class="content">${user.modifyAt?string("yyyy-MM-dd")}</td>	     
	   </tr>
	   <tr>
        <td class="title" >&nbsp;<@bean.message key="attr.figure" />:</td>
        <td  class="content" colspan="3">
        <#if user.isCategory(1)>学生&nbsp;</#if><#if user.isCategory(2)>教师&nbsp;</#if>
        <#if user.isCategory(4)>院系管理员&nbsp;</#if><#if user.isCategory(8)>部门管理员</#if>
        </td>
       </tr>
	   <tr>
        <td class="title" >&nbsp;<@bean.message key="entity.role" />:</td>
        <td  class="content" colspan="3">
             <#list user.roles?if_exists as role>
                  ${role.name}&nbsp;
             </#list>
         </td>
       </tr>
	   <tr>
        <td class="title" >&nbsp;<@bean.message key="attr.managerUser" />:</td>
        <td  class="content" colspan="3">
             <#list user.managers?if_exists as user>
                  ${user.userName}(${user.name})&nbsp;
             </#list>
         </td>
       </tr>  
	   <tr>
        <td class="title" >&nbsp;<@bean.message key="attr.remark" />:</td>
        <td class="content" colspan="3">${user.remark?if_exists}</td>
        </tr>
      </table>
      <br><br>
  <script>
   var bar = new ToolBar('userInfoBar','<@bean.message key="entity.user" /><@bean.message key="common.detailInfo" />',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack("<@bean.message key="action.back"/>");    
  </script>
 </body>
<#include "/templates/foot.ftl"/>