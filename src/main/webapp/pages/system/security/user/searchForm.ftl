   <table class="searchTable" onkeypress="DWRUtil.onReturn(event, searchUser)">
    <tr>
      <td  colspan="2" class="infoTitle" align="left" valign="bottom" >
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B><@msg.message "security.searchUser"/></B>
      </td>
    <tr>
      <td  colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
    <form name="userSearchForm" action="user.do?method=search"  target="contentFrame" method="post">
     <tr><td><@msg.message "attr.loginName"/>：</td><td><input type="text" name="user.name" style="width:100px;" /></td></tr>
     <tr><td><@msg.message "attr.personName"/>：</td><td><input type="text" name="user.userName" style="width:100px;" /></td></tr>
     <tr><td><@msg.message "attr.creator"/>：</td><td><input type="text" name="user.creator.userName" style="width:100px;" /></td></tr>
     <tr><td><@msg.message "entity.role"/>：</td><td><input type="text" name="roleName" style="width:100px;" /></td></tr>
     <tr>
     		<td><@msg.message key="attr.figure" />：</td>
     		<td>
     		<select  name="categoryId" style="width:100px;" >
     			<option value="" >请选择身份</option>
	     		<#list categories as category>
	     			<option value="${category.id}" >${category.name}</option>
	      		</#list>
		  	</select>
		  	</td>
 	 </tr>
		 <tr><td><@msg.message "attr.status"/>:</td><td><select  name="user.state" style="width:100px;" >
		   		<option value="1" selected><@msg.message "action.activate"/></option>
		   		<option value="0" ><@msg.message "action.freeze"/></option>
		  </select>
		</td></tr>
     <tr><td colspan="2" align="center"><button onclick="searchUser();"><@msg.message key="action.query"/></button></td></tr>
     </form>
	</table>