<#include "/templates/head.ftl"/>
<body >
  <table id="taskBar"></table>
  <script language="javascript">
     var bar=new ToolBar('taskBar','用户列表',null,true,false);
     bar.addItem("增加","addUser()","new.gif"); 
     function search(){
     	var form = document.userListForm;
     	form.action = "registerUserGroup.do?method=userList";
     	form.submit();
     }
     function addUser(){
      var ids = getSelectIds("userId");
      if(ids==''){
      alert("请选择一项或多项");
      }
       self.location="registerUserGroup.do?method=addUser&ids="+ids+"&id="+${RequestParameters['id']};
     }
</script>
<@table.table width="100%" sortable="true" id="listTable" headIndex="1">
   <form name="userListForm" action="" method="post" onsubmit="return false;">
    <tr bgcolor="#ffffff" onKeyDown="javascript:enterQuery(event)">
      <td align="center" width="3%" >
        <img src="${static_base}/images/action/search.png"  align="top" onClick="search()" />
      </td>
      <td ><input style="width:100%" type="text" name="user.name" maxlength="32" /></td>
      <input type="hidden"  name="id" value="${RequestParameters['id']}" />
      <td ><input style="width:100%" type="text" name="user.userName" maxlength="20" /></td>
    </tr>
   </form>
  	<@table.thead>
  	  <@table.selectAllTd id="userId"/>
      <@table.sortTd id="user.name" width="20%" text="登录名"/>
      <@table.sortTd id="user.userName" width="20%" text="用户姓名"/>
    </@>
    <@table.tbody datas=users;user>
      <@table.selectTd id="userId" value=user.id/>
      <td>${user.name?if_exists}</td>
      <td>${user.userName?if_exists}</td>      
    </@>
  </@>
  <script>function enterQuery(event) {if (portableEvent(event).keyCode == 13)search();}</script>
  </body> 
<#include "/templates/foot.ftl"/> 
