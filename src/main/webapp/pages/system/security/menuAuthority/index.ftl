<#include "/templates/head.ftl"/>
 <link href="${static_base}/css/tab.css" rel="stylesheet" type="text/css">
 <script language="JavaScript" type="text/JavaScript" src="<@msg.message key="validator.js.url" />"></script>
 <script language="JavaScript" type="text/JavaScript" src="scripts/common/TabPane.js"></script> 
 <script>
 function listAuthorityInfo(srcSelect, who){
    if(who ==undefined||who==""||((who!="userAuthority")&&(who!="roleAuthority"))) return;
	for (var i=0; i<srcSelect.length; i++){	   
		if (srcSelect.options[i].selected){ 		 
			var op = srcSelect.options[i];
			document.authorityForm.action = "menuAuthority.do?method=editAuthority&who="+who+"&id="+op.value;
			document.authorityForm.target="authorityInfoFrame";
			document.authorityForm.submit();
		 }
	 }
}
 </script> 
 <body>
  <table id="authorityBar"></table>
  <script>
    var bar = new ToolBar("authorityBar",'<@msg.message key="info.authority.userAndRole"/>',null,true,true);
    bar.addHelp("帮助");
  </script>
  <table width="100%" border="0"  >
  <tr >
   <td style="width:190px;" valign="top">
   <div class="dynamic-tab-pane-control tab-pane" id="tabPane1">
     <script type="text/javascript">tp1 = new WebFXTabPane( document.getElementById( "tabPane1") ,false );</script>
     <div style="display: block;" class="tab-page" id="tabPage1">
      <h2 class="tab"><a href="#" style="font-size:12px" title="<@msg.message key="info.user.count"/>:${manager.mngUsers?size}"><@msg.message key="entity.user"/></a></h2>
      <script type="text/javascript">tp1.addTabPage( document.getElementById( "tabPage1" ) );</script>
       <form name="mngUserForm" >
         <table height="100%" style="font-size:12px">
          <td><@msg.message key="info.user.canBeChoosed"/>：</td>
          </tr>
          <tr>
           <td >
            <select name="Users" MULTIPLE size="10" onDblClick="JavaScript:listAuthorityInfo(this.form['Users'],'userAuthority')" style="height:280px;width:150px">
             <#list manager.mngUsers?sort_by("name")?if_exists as user>
              <option value="${user.id}">${user.name}</option>
             </#list>
            </select>
           </td>
          </tr>
        <tr>
         <td colspan="3">
            <@msg.message key="info.authority.selectTip"/>
          </td>
        </tr>
       </table>
      </form>
     </div>
     
     <div style="display: block;" class="tab-page" id="tabPage2">
	  <h2 class="tab"><a href="#" style="font-size:12px" title="<@msg.message key="info.role.count"/>：${manager.mngRoles?if_exists?size}个"><@msg.message key="entity.role"/></a></h2>     
      <script type="text/javascript">tp1.addTabPage( document.getElementById( "tabPage2" ) );</script>
       <form name="mngRoleForm">
         <table  height="100%" style="font-size:12px">
          <tr>
          <td><@msg.message key="info.role.canBeChoosed"/>：</td>
          </tr>
          <tr>
           <td>
            <select name="Roles" MULTIPLE size="10" onDblClick="JavaScript:listAuthorityInfo(this.form['Roles'],'roleAuthority')" style="height:280px;width:150px">
             <#list manager.mngRoles?sort_by("name")?if_exists as role>
              <option value="${role.id}">${role.name}</option>
             </#list>
            </select>
           </td>
          </tr>
          <tr>
          <td colspan="3">
            <@msg.message key="info.authority.selectTip"/>
          </td>
         </tr>
        </table>
       </form>
      </div>
     
     </div>
     <script type="text/javascript">setupAllTabs();</script>
     </td>

     <td valign="top">
     <iframe  src="menuAuthority.do?method=prompt" id="authorityInfoFrame" name="authorityInfoFrame" 
      marginwidth="0" marginheight="0"
      scrolling="no" 	 frameborder="0"  height="100%" width="100%">
     </iframe>
     </td>
     </tr>
     <table>

    <form name="authorityForm" method="post" action="menuAuthority.do?method=info">
    <input type="hidden" name="roleId" value=""/>
    <input type="hidden" name="userId" value=""/>
    </form>
    
 </body>
<#include "/templates/foot.ftl"/> 