   <table class="searchTable" onkeypress="DWRUtil.onReturn(event, searchRole)">
    <tr>
      <td  colspan="2" class="infoTitle" align="left" valign="bottom" >
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B><@msg.message "security.searchRole"/></B>
      </td>
    <tr>
      <td  colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
    <form name="roleSearchForm" action="role.do?method=search"  target="contentFrame" method="post">
     <tr><td><@msg.message "attr.name"/>：</td><td><input type="text" name="role.name" style="width:100px;" /></td></tr>
     <tr><td><@msg.message "attr.creator"/>：</td><td><input type="text" name="role.creator.name" style="width:100px;" /></td></tr>
     <tr>
     		<td><@msg.message key="attr.figure" />：</td>
     		<td>
     		<select  name="role.category.id" style="width:100px;" onchange="isSelectStdCategory(this);">
     			<option value="" >请选择身份</option>
	     		<#list categories as category>
	     			<option value="${category.id}" >${category.name}</option>
	      		</#list>
		  	</select>
		  	</td>
 	 </tr>
 	 <tr>
     		<td></td>
     		<td>
 			 <select  name="role.stdType.id" style="width:100px;visibility:hidden;" id="select_role_stdType">
     			<option value="" >学生类型</option>
	     		<#list stdTypes as stdType>
	     			<option value="${stdType.id}" >${stdType.name}</option>
	      		</#list>
		  	</select>
		  	</td>
 	 </tr>
     <tr><td colspan="2" align="center"><button onclick="searchRole();"><@msg.message key="action.query"/></button></td></tr>
     </form>
 </table>
 
