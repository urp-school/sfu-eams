<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" onload="f_frameStyleResize(parent)">
    <table id="myBar"></table>
     <@table.table width="100%"  onkeypress="DWRUtil.onReturn(event,query)" sortable="true" id="listTable" headIndex="1">
       <form name="actionForm" onSubmit="return false;" method="post" action="systemMessage.do?method=userList">
       <tr>
           <td><img src="${static_base}/images/action/search.png"  align="top" onClick="query()" alt="<@bean.message key="info.filterInResult"/>"/></td>
           <td><input style="width:100%" type="text" name="user.name" maxlength="32" value="${RequestParameters['user.name']?if_exists}"></td>
           <td><input style="width:100%" type="text" name="user.userName" maxlength="50" value="${RequestParameters['user.userName']?if_exists}"></td>
           <td><input style="width:100%" type="text" name="user.email" maxlength="100" value="${RequestParameters['user.email']?if_exists}"></td>
           <td><input style="width:100%" type="text" name="user.remark" maxlength="100" value="${RequestParameters['user.remark']?if_exists}"></td>
       </tr>
      </form>
       <@table.thead>
         <@table.selectAllTd id="name"/>
	     <@table.td text="登录名" id="user.name"/>
	     <td>姓名</td>
	     <td>电子邮件</td>
	     <td>备注</td>
	   </@>
	   <@table.tbody datas=users;user>
	     <@table.selectTd id="name" value="${user.name}" />
	     <td>${user.name}</td>
  	     <td>${user.userName}</td>
  	     <td>${user.email?if_exists}</td>
         <td>${user.remark?if_exists}</td>
	   </@>
     </@>
 <script language="JavaScript" type="text/JavaScript" src="scripts/system/SendMessage.js"></script>
<script>
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("name")));
    }
    <#list users as user>
	detailArray['${user.name}'] = {'name':'${user.name}'};
	</#list>
    var bar= new ToolBar("myBar","管理帐户列表",null,true,true);
    bar.addItem("选中添加","addSelected()");
</script>
 </body>
<#include "/templates/foot.ftl"/>