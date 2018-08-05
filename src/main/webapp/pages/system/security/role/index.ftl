<#include "/templates/head.ftl"/>
 <body>
<#assign STDCATEGORYID=1>
 <table id="roleBar"></table>
   <table  class="frameTable">
   <tr>
    <td style="width:180px"  class="frameTable_view"><#include "searchForm.ftl"/></td>
    <td valign="top">
    <iframe  src="#" id="contentFrame" name="contentFrame" 
      marginwidth="0" marginheight="0"
      scrolling="no" frameborder="0"  height="100%" width="100%"></iframe>
    </td>
   </tr>
  </table>
 </body>
 
 
  <script language="javascript" >
  	function isSelectStdCategory(selectedTag){
  		var role_stdType = document.getElementById("select_role_stdType");
  		if(${STDCATEGORYID}==selectedTag.value){
  			role_stdType.style.visibility='visible';
  		}else{
  			role_stdType.style.visibility='hidden';
  		}
  	}
  </script>  
  
  
  <script>
    var form=document.roleSearchForm;
    var action="role.do";
    function getIds(){
       return(getCheckBoxValue(contentFrame.document.getElementsByName("roleId")));
    }    
    function add(){
       form.action=action+"?method=edit";
       addInput(form,"roleId","");
       addParamsInput(form,contentFrame.queryStr);
       form.submit();
    }
    function searchRole(pageNo,pageSize,orderBy){
       form.action=action+"?method=search";
       goToPage(form,pageNo,pageSize,orderBy);
    }
    searchRole();
    function singleRoleAction(method){
       var roleIds = getIds();
       if(roleIds ==""||isMultiId(roleIds)) {
         window.alert('<@msg.message key="common.singleSelectPlease"/>!');
         return;
       }
       addParamsInput(form,contentFrame.queryStr);
       addInput(form,"roleId",roleIds);
       form.action=action+"?method="+method; 
       form.submit(); 
   }
    function multiRoleAction(method){
       var roleIds = getIds();
       if(roleIds =="") {
         window.alert('<@msg.message key="common.select"/>!');
         return;
       }
       if(window.confirm("<@msg.message "common.confirmAction"/>")){
           addParamsInput(form,contentFrame.queryStr);
           addInput(form,"roleIds",roleIds);
           form.action = action+"?method="+method;
           form.submit();
       }
   }

   function exportData(){
      addInput(form,"keys","name,description,creator.name,createAt,modifyAt,users,managers");
      addInput(form,"titles","<@msg.message "attr.name"/>,<@msg.message "attr.description"/>,<@msg.message "attr.creator"/>,<@msg.message "attr.dateCreated"/>,<@msg.message "attr.dateLastModified"/>,<@msg.message "attr.ownedUser"/>,<@msg.message "attr.manager"/>");
      form.action=action+"?method=export";
      form.submit();
   }
   var bar = new ToolBar('roleBar','<@msg.message "security.roleManage"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message "action.new"/>",add,'new.gif');
   bar.addItem("<@msg.message "action.edit"/>","singleRoleAction('edit');",'update.gif');
   bar.addItem("<@msg.message "action.delete"/>","multiRoleAction('remove')",'delete.gif');
   bar.addItem("<@msg.message "action.export"/>","exportData()");
   bar.addHelp("<@msg.message key="action.help"/>");
  </script>
</html>