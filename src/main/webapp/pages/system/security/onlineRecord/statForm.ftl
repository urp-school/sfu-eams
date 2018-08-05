 <div style="display: block;" id="view0">
	<table width="100%" class="searchTable">
		<form name="userForm" method="post" target="statFrame" action="" onsubmit="return false;">
		<tr>
		      <td colspan="2"  class="infoTitle" align="left" valign="bottom">
		       <img src="${static_base}/images/action/info.gif" align="top"/>
		          <B>查询条件</B>      
		      </td>
		    <tr>
		      <td  colspan="2" style="font-size:0px">
		          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
		      </td>
		</tr>
		<tr>
			<td>登录名：</td>
			<td><input name="onlineRecord.name" value="" style="width:100px" maxlength="32"></td>
		</tr>
        <tr>
			<td>姓名：</td>
			<td><input name="onlineRecord.userName" value="" style="width:100px" maxlength="32"></td>
		</tr>
		<tr>
		    <td>用户身份：</td>
			<td>
				<select name="onlineRecord.category" style="width:100px">
					<option value="">..</option>
					<#list categories as category>
					<option value="${category.id}">${category.name}</option>
					</#list>
				</select>
			</td>
		</tr>
		<tr>
			<td>起始时间：</td>
			<td><input name="startTime" value="" style="width:100px" onfocus="calendar()" maxlength="10"/></td>
		</tr>
		<tr>
			<td>结束时间：</td>
			<td><input name="endTime" value="" style="width:100px" onfocus="calendar()" maxlength="10"/></td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td colspan="2" align="center">
				<button name="button1" onClick="search()" ><@msg.message "action.query"/></button>
			</td>
		</tr>
		</form>
		</table>
	</div>
	
 <div style="display: none;" id="view1">
  <table width="100%" class="searchTable">
	<form name="roleForm" method="post" target="statFrame" action="" onsubmit="return false;">
	<tr>
	      <td colspan="2"  class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B>查询条件</B>      
	      </td>
	    <tr>
	      <td  colspan="2" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	</tr>
	<tr>
		<td>登录名：</td>
		<td><input name="onlineRecord.name" value="" style="width:100px" maxlength="32"></td>
	</tr>
    <tr>
		<td>姓名：</td>
		<td><input name="onlineRecord.userName" value="" style="width:100px" maxlength="32"></td>
	</tr>
	<tr>
	    <td>用户身份：</td>
		<td>
			<select name="onlineRecord.category" style="width:100px">
				<option value="">..</option>
				<#list categories as category>
				<option value="${category.id}">${category.name}</option>
				</#list>
			</select>
		</td>
	</tr>
	<tr>
		<td>角色名：</td>
		<td><input name="roleName" value="" style="width:100px" maxlength="50"/></td>
	</tr>
	<tr>
		<td>起始时间：</td>
		<td><input name="startTime" value="" style="width:100px" onfocus="calendar()" maxlength="10"/></td>
	</tr>
	<tr>
		<td>结束时间：</td>
		<td><input name="endTime" value="" style="width:100px" onfocus="calendar()" maxlength="10"/></td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td colspan="2" align="center">
			<button name="button1" onClick="loginCountStat()" ><@msg.message "action.query"/></button>
		</td>
	</tr>
	</form>
	</table>
	</div>
	
   <div style="display: none;" id="view2">
	<table width="100%" class="searchTable">
	<form name="numForm" method="post" target="statFrame" action="" onsubmit="return false;">
	<tr>
	      <td colspan="2"  class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B>查询条件</B>      
	      </td>
	    <tr>
	      <td  colspan="2" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	</tr>
	<tr>
		<td>起始时间：</td>
		<td><input name="startTime" value="" style="width:100px" onfocus="calendar()" maxlength="10"/></td>
	</tr>
	<tr>
		<td>结束时间：</td>
		<td><input name="endTime" value="" style="width:100px" onfocus="calendar()" maxlength="10"/></td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td colspan="2" align="center">
			<button name="button1" onClick="timeIntervalStat()" ><@msg.message "action.query"/></button>
		</td>
	</tr>
	</from>
	</table>
	</div>