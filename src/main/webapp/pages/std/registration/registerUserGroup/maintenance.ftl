<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="bar"></table>
<@table.table id="id" sortable="true" width="100%">
 <@table.thead>
  <@table.selectAllTd id="userId"/>
  <@table.sortTd id="user.name" text="登录名"/>
  <@table.sortTd id="user.name" text="姓名"/>
 </@>
 <@table.tbody datas=users;user>
  <@table.selectTd id="userId" value=user.id/>
  <td>${user.name?default("")}</td>
  <td>${user.name?default("")}</td>
 </@>
</@>
<@htm.actionForm name="actionForm" method="post" entity="user" action="registerUserGroup.do"/>
<script language="javascript">
   var bar=new ToolBar('bar','人员维护',null,true,true);
   bar.addItem("新增","addUser()","new.gif"); 
   bar.addItem("删除","removeUsers()","delete.gif");
   bar.addBack("<@msg.message key="action.back"/>");
   bar.setMessage('<@getMessage/>');
   bar.addPrint("<@bean.message key="action.print"/>");
   function maintenance(){
   var id = getSelectIds("registerUserGroupId");
    if(id==""||isMultiId(id)){
        alert("请选择一项");
        return;
      }
   self.location="registerUserGroup.do?method=maintenance&id="+id;
   }
   function addUser(){
  	self.location="registerUserGroup.do?method=userList&id="+${RequestParameters['id']};
   }
   function removeUsers(){
   var ids = getSelectIds("userId");
   if(ids ==''){
   alert("请选择一项或多项");
   }
   if(confirm("是否确认删除")){
   self.location="registerUserGroup.do?method=removeUsers&ids="+ids+"&id="+${RequestParameters['id']};
   }
   }
 </script>
</body>
<#include "/templates/foot.ftl"/>