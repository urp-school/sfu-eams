<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/Menu.js"></script> 
 <body>
   <table id="userBar"></table>
   <table  class="frameTable">
   <tr>
    <td style="width:160px"  class="frameTable_view"><#include "searchForm.ftl"/></td>
    <td valign="top">
    <iframe  src="#" id="contentFrame" name="contentFrame" 
      marginwidth="0" marginheight="0"
      scrolling="no" frameborder="0"  height="100%" width="100%">
    </iframe>
    </td>
   </tr>
  </table>
 </body>
  <script>
    var form=document.userSearchForm;
    var action="user.do";
    function getIds(){
       return(getCheckBoxValue(contentFrame.document.getElementsByName("userId")));
    }
    function storeParams(){
       addParamsInput(form,contentFrame.queryStr);
    }
    function searchUser(pageNo,pageSize,orderBy){
       form.action=action+"?method=search";
       goToPage(form,pageNo,pageSize,orderBy);
    }
    searchUser();
    function removeUser(){
      storeParams();
       var userIds = getIds();
       if(userIds =="") {
         window.alert('<@msg.message key="common.selectPlease"/>!');
         return;
       }
       if(window.confirm("<@msg.message key="prompt.user.delete"/>")){
           form.action = action+"?method=remove&userIds="+userIds;
           form.submit();
       }
       else return;
    }
    function activateUser(isActivate){
       storeParams();
       var userIds = getIds();
       if(userIds =="") {
         window.alert('<@msg.message key="common.selectPlease"/>!');
         return;
       }
       form.action =action+ "?method=activate&userIds="+userIds+"&isActivate="+isActivate; 
       form.submit();
    }
    function addUser(){
       storeParams();
       form.action=action+"?method=edit&user.id=";
   	   form.submit();
    }
    function modifyUser(){
       storeParams();
       var userIds = getIds();
       if(userIds =="") {
         window.alert('<@msg.message key="common.select"/>!');
         return;
       }
       if(isMultiId(userIds)){alert("<@msg.message key="common.singleSelectPlease"/>");return;}
       form.action=action+"?method=edit&user.id="+userIds;
   	   form.submit();
    }
    function exportUserList(){
       addInput(form,"keys","name,password,email,creator.name,createAt,modifyAt,roles,managers,mngRoles,mngUsers")
       addInput(form,"titles","登录名,密码,电子邮件,创建者,创建时间,修改时间,角色,管理者,管理角色,管理用户");
       form.action=action+"?method=export";
       form.submit();
    }
    function updateUserAccount(){
       var userId = getIds();
       if(userId =="" ||isMultiId(userId)) {
         window.alert("<@msg.message key="common.singleSelectPlease"/>");
         return;
       }
        window.open("password.do?method=editUserAccount&user.id="+userId);
    }
    //二级管理
    var managementAction = "management.do";
    function removeManagement(kind){
       var userIds =	 getIds();
       if(userIds =="") {
         window.alert('<@msg.message key="common.select"/>!');
         return;
       }       
       if((kind !="mngUsers")&&(kind !="mngRoles")&&(kind !="both")) alert("what do you want?");
       if(window.confirm("<@msg.message key="prompt.dispatchAuthority.delete"/>")){
          storeParams();
          form.action = managementAction+"?method=removeManagement&userIds="+userIds+"&kind=" + kind;
          form.submit();
       }
       else return;
    } 
    function editManagement(){
       var userIds = getIds();
       if(userIds =="") {
         window.alert('<@msg.message key="common.select"/>!');
         return;
       }
       if(isMultiId(userIds)){alert("<@msg.message key="common.singleSelectPlease"/>");return;}
       storeParams();
       form.action=managementAction+"?method=editManagement&user.id="+userIds;
       form.submit();
   }
    
  
   var bar = new ToolBar('userBar','<@msg.message "security.userManage"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message "action.new"/>",addUser,'new.gif');
   bar.addItem("<@msg.message "action.modify"/>",modifyUser,'update.gif');
   bar.addItem("<@msg.message "security.action.changePassword"/>",updateUserAccount);
   var menu1 =bar.addMenu("<@msg.message "security.action.2ndManage"/>","editManagement()");
   menu1.addItem("<@msg.message "security.action.withdrawUserManagement"/>","removeManagement('mngUsers')");
   menu1.addItem("<@msg.message "security.action.withdrawRoleManagement"/>","removeManagement('mngRoles')");
   bar.addItem("<@msg.message "action.freeze"/>","activateUser('false')",'prohibit.gif');
   bar.addItem("<@msg.message "action.activate"/>","activateUser('true')",'newUser.gif');
   bar.addItem("<@msg.message "action.delete"/>",removeUser,'delete.gif');
   var menu2 = bar.addMenu("导入", "importData()", "list.gif");
   menu2.addItem("下载导入模板", "downloadTemplate()", "download.gif");
   bar.addItem("<@msg.message "action.export"/>",exportUserList,'excel.png');
   
   function downloadTemplate(){
        location = "./dataTemplate.do?method=download&document.id=14";
   }
   
   function importData(){
       form.action = "user.do?method=importForm";
       addInput(form, "templateDocumentId", "14", "hidden");
       addInput(form, "importTitle", "用户导入", "hidden");
       form.target = "contentFrame";
       form.submit();
   }
  </script>
</html>