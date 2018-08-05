<#include "/templates/head.ftl"/>
 <link href="${static_base}/css/tab.css" rel="stylesheet" type="text/css">
 <script language="JavaScript" type="text/JavaScript" src="<@msg.message  key="validator.js.url" />"></script>
 <script language="JavaScript" type="text/JavaScript" src="scripts/common/TabPane.js"></script> 
 <body>
 <table id="mngBar"></table>
<script>
 function save(){
   document.mngForm.userIds.value = getAllOptionValue(document.mngUserForm.SelectedUser);  
   document.mngForm.roleIds.value = getAllOptionValue(document.mngRoleForm.SelectedRole); 
   document.mngForm.submit();
 }
   var bar = new ToolBar('mngBar',"<@msg.message  key="info.dispatchAuthority.update" arg0="${user.name}"/>",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message  key="action.save"/>",save,'save.gif');
   bar.addBack("<@msg.message  key="action.back"/>");
 </script> 
  <!--below is the tab panel-->
   <div class="dynamic-tab-pane-control tab-pane" id="tabPane1">
     <script type="text/javascript">
       tp1 = new WebFXTabPane( document.getElementById( "tabPane1") ,false );
     </script>

     <div style="display: block;" class="tab-page" id="tabPage1">
	  <h2 class="tab">
		  <a href="#"><@msg.message  key="info.user.count"/>(<font id="COMMENT_COUNTS" color="blue">${user.mngUsers?if_exists?size}</font>)</a>
      </h2>
      <script type="text/javascript">tp1.addTabPage( document.getElementById( "tabPage1" ) );</script>
       <form name="mngUserForm">
         <table style="font-size:12px">
          <td><@msg.message  key="info.user.canBeChoosed"/>：</td>
          <td></td>
          <td><@msg.message  key="info.dispatchAuthority.managedUser"/>：</td>
          </tr>
          <tr>
           <td>

            <select name="Users" MULTIPLE size="10" style="height:320px;width:150px" onDblClick="JavaScript:moveSelectedOption(this.form['Users'], this.form['SelectedUser'])" >
             <#list manager.mngUsers?sort_by("name") as tempUser>
             	<#if  !(user.mngUsers?seq_contains(tempUser))&&(user.id!=tempUser.id)>
	              <option value="${tempUser.id}">${tempUser.name}</option>
	             </#if>
             </#list>
            </select>
           </td>
           <td  valign="middle">
            <br><br>
            <input OnClick="JavaScript:moveSelectedOption(this.form['Users'], this.form['SelectedUser'])" type="button" class="buttonstyle" style="width:30px" value="&gt;"> 
            <br><br>
            <input OnClick="JavaScript:moveSelectedOption(this.form['SelectedUser'], this.form['Users'])" type="button"  class="buttonstyle" style="width:30px" value="&lt;"> 
            <br>
           </td> 
           <td  class="normalTextStyle">
            <select name="SelectedUser" MULTIPLE size="10" style="height:320px;width:150px;" onDblClick="JavaScript:moveSelectedOption(this.form['SelectedUser'], this.form['Users'])">
             <#list  user.mngUsers?sort_by("name") as managed>
              <#if managed.creator.id!=managed.id>
              <option value="${managed.id}">${managed.name}</option>
              </#if>
             </#list>
            </select>
           </td>
          </tr>
        <tr>
         <td colspan="3" >
            <@msg.message  key="info.doubleSelect.moveTip"/>
          </td>
        </tr>  
        <tr>
           <td colspan="3"  style="height:5px" valign="bottom" >
           <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="middle">
           </td>
         </tr>          
         </table>
         </form>
     </div>
     <div style="display: block;" class="tab-page" id="tabPage2">
		<h2 class="tab">
		   <a href="#"><@msg.message  key="info.role.count"/>(<font id="COMMENT_COUNTS" color="blue">${user.mngRoles?if_exists?size}</font>)</a>
		</h2>     
     <script type="text/javascript">tp1.addTabPage( document.getElementById( "tabPage2" ) );</script>
       <form name="mngRoleForm">
         <table style="font-size:12px">
          <td><@msg.message  key="info.role.canBeChoosed"/>：</td>
          <td></td>
          <td><@msg.message  key="info.dispatchAuthority.managedRole"/>：</td>
          </tr>         
          <tr>
           <td>
            <select name="Roles" MULTIPLE size="10" onDblClick="JavaScript:moveSelectedOption(this.form['Roles'], this.form['SelectedRole'])" style="height:320px;width:150px">
             <#list manager.mngRoles?sort_by("name") as role>
             	<#if  !(user.mngRoles?seq_contains(role))>
             	 <option value="${role.id}">${role.name}</option>
              	</#if>
             </#list>
            </select>
           </td>
           <td  valign="middle">
            <br><br>
            <input OnClick="JavaScript:moveSelectedOption(this.form['Roles'], this.form['SelectedRole'])" type="button" class="buttonstyle" style="width:30px" value="&gt;"> 
            <br><br>
            <input OnClick="JavaScript:moveSelectedOption(this.form['SelectedRole'], this.form['Roles'])" type="button" class="buttonstyle" style="width:30px" value="&lt;"> 
            <br>
           </td> 
           <td  class="normalTextStyle">
            <select name="SelectedRole" MULTIPLE size="10" style="height:320px;width:150px;" onDblClick="JavaScript:moveSelectedOption(this.form['SelectedRole'], this.form['Roles'])">
             <#list  user.mngRoles?sort_by("name") as role>
              <option value="${role.id}">${role.name}</option>
             </#list>
            </select>
           </td>
          </tr>
        <tr>
         <td colspan="3" >
            <@msg.message  key="info.doubleSelect.moveTip"/>
          </td>
        </tr>  
        <tr>
           <td colspan="3"  style="height:5px" valign="bottom" >
           <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="middle">
           </td>
         </tr>
        </table>   
       </form>
     </div>
     </div>
     </div>
     <script type="text/javascript">
	 setupAllTabs();
	 </script>
    <form name="mngForm" method="post" action="management.do?method=saveManagement">
    <input type="hidden" name="roleIds" value="" />
    <input type="hidden" name="userIds" value="" />
    <@searchParams/>
    <input type="hidden" name="user.id" value="${user.id}" />
    </form>
 </body>
<#include "/templates/foot.ftl"/> 