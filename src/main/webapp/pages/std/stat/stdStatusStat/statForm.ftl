 <div style="display: block;" id="view0">
	<table width="100%" class="searchTable">
		<form name="homeplaceForm" method="post" target="statFrame" action="" onsubmit="return false;">
		<tr>
		      <td colspan="2"  class="infoTitle" align="left" valign="bottom">
		       <img src="${static_base}/images/action/info.gif" align="top"/>
		          <B>统计查询项</B> 
		      </td>
		</tr>
		    <tr>
		      <td  colspan="2" style="font-size:0px">
		          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
		      </td>
		</tr>
		<tr>
				<td>
					&nbsp;<@bean.message key="filed.enrollYearAndSequence"/>：
				</td>
				<td>
					<input type="text" name="std.enrollYear" maxlength="7" size="6" value="" style="width:100px;">
				</td>
		</tr>
		<tr>
				<td>
					&nbsp;<@bean.message key="entity.studentType"/></font>：
				</td>
				<td>
					<select id="stdType" name="std.type.id" style="width:100px;">
						<option value="">...</option>
						<#list stdTypeList?if_exists as stdType>
							<option value="${stdType.id}"><@i18nName stdType/></option>
						</#list>
					</select>
				</td>
			</tr>
			<tr>
				<td>&nbsp;院系:</td>
				<td>
					<select id="stdDepartment" name="std.department.id" style="width:100px;">
						<option value="">...</option>
						<#list departmentList?if_exists as department>
							<option value="${department.id}"><@i18nName department/></option>
						</#list>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;学籍有效性：
				</td>
				<td>
					<select id="stdState" name="std.active" style="width:100px;">
						<option value="">...</option>
						<option value="1">有效</option>
						<option value="0">无效</option>
					</select>
				</td>
			</tr>
      		<tr>
				<td>
					&nbsp;学籍状态：
				</td>
				<td>
					<select id="stdType" name="std.state.id" style="width:100px;">
						<option value="">...</option>
						<#list stdInfoStatusList?if_exists as status>
							<option value="${status.id}"><@i18nName status/></option>
						</#list>
					</select>
				</td>
			</tr>
			<tr height="50px">
				<td colspan="2" align="center"><input type="button" onClick="statByHomeplace()" value="<@bean.message key="system.button.submit"/>" name="button1" class="buttonStyle"/>&nbsp;</td>
			</tr>
		</form>
		</table>
	</div>
	
 <div style="display: none;" id="view1">
  <table width="100%" class="searchTable">
		<form name="degreeForm" method="post" target="statFrame" action="" onsubmit="return false;">
		<tr>
		      <td colspan="2"  class="infoTitle" align="left" valign="bottom">
		       <img src="${static_base}/images/action/info.gif" align="top"/>
		          <B>统计查询项</B>
		      </td>
		</tr>
		    <tr>
		      <td  colspan="2" style="font-size:0px">
		          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
		      </td>
		</tr>
		<tr>
				<td>
					&nbsp;<@bean.message key="filed.enrollYearAndSequence"/>：
				</td>
				<td>
					<input type="text" name="std.enrollYear" maxlength="7" size="6" value="" style="width:100px;">
				</td>
		</tr>
		<tr>
				<td>
					&nbsp;<@bean.message key="entity.studentType"/></font>：
				</td>
				<td>
					<select id="stdType" name="std.type.id" style="width:100px;">
						<option value="">...</option>
						<#list stdTypeList?if_exists as stdType>
							<option value="${stdType.id}"><@i18nName stdType/></option>
						</#list>
					</select>
				</td>
			</tr>
			<tr>
				<td>&nbsp;院系:</td>
				<td>
					<select id="stdDepartment" name="std.department.id" style="width:100px;">
						<option value="">...</option>
						<#list departmentList?if_exists as department>
							<option value="${department.id}"><@i18nName department/></option>
						</#list>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;学籍有效性：
				</td>
				<td>
					<select id="stdState" name="std.active" style="width:100px;">
						<option value="">...</option>
						<option value="1">有效</option>
						<option value="0">无效</option>
					</select>
				</td>
			</tr>
      		<tr>
				<td>
					&nbsp;学籍状态：
				</td>
				<td>
					<select id="stdType" name="std.state.id" style="width:100px;">
						<option value="">...</option>
						<#list stdInfoStatusList?if_exists as status>
							<option value="${status.id}">${status.name}</option>
						</#list>
					</select>
				</td>
			</tr>
			<tr height="50px">
				<td colspan="2" align="center"><input type="button" onClick="statByDegree()" value="<@bean.message key="system.button.submit"/>" name="button1" class="buttonStyle"/>&nbsp;</td>
			</tr>
		</form>
	</table>
	</div>
   <div style="display: none;" id="view2">
	<table width="100%" class="searchTable">
		<form name="abroadForm" method="post" target="statFrame" action="" onsubmit="return false;">
		<tr>
		      <td colspan="2"  class="infoTitle" align="left" valign="bottom">
		       <img src="${static_base}/images/action/info.gif" align="top"/>
		          <B>统计查询项</B>
		      </td>
		</tr>
		    <tr>
		      <td  colspan="2" style="font-size:0px">
		          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
		      </td>
		</tr>
		<tr>
				<td>
					&nbsp;<@bean.message key="filed.enrollYearAndSequence"/>：
				</td>
				<td>
					<input type="text" name="std.enrollYear" maxlength="7" size="6" value="" style="width:100px;"/>
				</td>
		</tr>
			<tr>
				<td>&nbsp;院系:</td>
				<td>
					<select id="stdDepartment" name="std.department.id" style="width:100px;">
						<option value="">...</option>
						<#list departmentList?if_exists as department>
							<option value="${department.id}"><@i18nName department/></option>
						</#list>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;学籍有效性：
				</td>
				<td>
					<select id="stdState" name="std.active" style="width:100px;">
						<option value="">...</option>
						<option value="1">有效</option>
						<option value="0">无效</option>
					</select>
				</td>
			</tr>
      		<tr>
				<td>
					&nbsp;学籍状态：
				</td>
				<td>
					<select id="stdType" name="std.state.id" style="width:100px;">
						<option value="">...</option>
						<#list stdInfoStatusList?if_exists as status>
							<option value="${status.id}"><@i18nName status/></option>
						</#list>
					</select>
				</td>
			</tr>
			<tr height="50px">
				<td colspan="2" align="center"><input type="button" onClick="statByAbroad()" value="<@bean.message key="system.button.submit"/>" name="button1" class="buttonStyle"/>&nbsp;</td>
			</tr>
		</form>
	</table>
	</div>