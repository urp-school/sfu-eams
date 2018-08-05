<#include "/templates/head.ftl"/>
 <link href="${static_base}/css/tab.css" rel="stylesheet" type="text/css">
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
 <script language="JavaScript" type="text/JavaScript" src="scripts/tabpane.js"></script> 
 <script>
 function listAuthorityInfo(srcSelect, who){
    if(who ==undefined||who==""||((who!="role")&&(who!="user"))) return;
	for (var i=0; i<srcSelect.length; i++){	   
		if (srcSelect.options[i].selected){ 		 
			var op = srcSelect.options[i];
			document.authorityForm.action = "authority.do?method=info&who="+who+"&id="+op.value
			document.authorityForm.target="authorityInfoFrame";
			document.authorityForm.submit();
		 }
	 }
}
 </script> 
 <body>
  <table id="authorityBar"></table>
  <script>
    var bar = new ToolBar("authorityBar",'<@bean.message key="info.authority.userAndRole"/>',null,true,true);
    bar.addHelp("<@msg.message key="action.help"/>");
  </script>
  <table width="100%" border="0"  >
  <tr >
   <td style="width:190px;" valign="top">
   <div class="dynamic-tab-pane-control tab-pane" id="tabPane1">
     <script type="text/javascript">tp1 = new WebFXTabPane( document.getElementById( "tabPane1") ,false );</script>
     <div style="display: block;" class="tab-page" id="tabPage1">
      <h2 class="tab"><a href="#" style="font-size:12px" title="<@bean.message key="info.user.count"/>:${result.manager.mngUsers?size}"><@bean.message key="entity.user"/></a></h2>
      <script type="text/javascript">tp1.addTabPage( document.getElementById( "tabPage1" ) );</script>
       <form name="mngUserForm" action="" onsubmit="return false;">
         <table  height="100%" style="font-size:12px">
          <td><@bean.message key="info.user.canBeChoosed"/>：</td>     
          </tr>
          <tr>
           <td >
            <select name="Users" MULTIPLE size="10" onDblClick="JavaScript:listAuthorityInfo(this.form['Users'],'user')" style="height:280px;width:150px">
             <#list result.manager.mngUsers?sort_by("name")?if_exists as user>
              <option value="${user.id}">${user.name}</option>
             </#list>
            </select>
           </td>
          </tr>
        <tr>
         <td colspan="3">
            <@bean.message key="info.authority.selectTip"/>
          </td>
        </tr>
       </table>
      </form>
     </div>
     
     <div style="display: block;" class="tab-page" id="tabPage2">
	  <h2 class="tab"><a href="#" style="font-size:12px" title="<@bean.message key="info.role.count"/>：${result.manager.mngRoles?if_exists?size}个"><@bean.message key="entity.role"/></a></h2>     
      <script type="text/javascript">tp1.addTabPage( document.getElementById( "tabPage2" ) );</script>
       <form name="mngRoleForm" action="" onsubmit="return false;">
         <table  height="100%" style="font-size:12px">
          <tr>
          <td><@bean.message key="info.role.canBeChoosed"/>：</td>
          </tr>         
          <tr>
           <td >
            <select name="Roles" MULTIPLE size="10" onDblClick="JavaScript:listAuthorityInfo(this.form['Roles'],'role')" style="height:280px;width:150px">
             <#list result.manager.mngRoles?sort_by("name")?if_exists as role>
              <option value="${role.id}">${role.name}</option>
             </#list>
            </select>
           </td>
          </tr>
          <tr>
          <td colspan="3">
            <@bean.message key="info.authority.selectTip"/>
          </td>
         </tr>
        </table>   
       </form>
      </div>
     
     </div>
     <script type="text/javascript">setupAllTabs();</script>
     </td>

     <td valign="top">
     <iframe src="pages/system/security/authority/authorityContent.ftl" id="authorityInfoFrame" name="authorityInfoFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
     </td>
     </tr>
     <table>
    <form name="authorityForm" method="post" action="authority.do?method=info" onsubmit="return false;">
    <input type="hidden" name="roleId" value="" />
    <input type="hidden" name="userId" value="" />
    </form>
 </body>
<#include "/templates/foot.ftl"/> 