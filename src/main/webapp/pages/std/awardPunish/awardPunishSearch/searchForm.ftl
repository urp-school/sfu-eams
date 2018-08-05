 <table width="100%">
    <tr>
      <td class="infoTitle" align="left" valign="bottom">
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B><@msg.message key="baseinfo.searchStudent"/></B>
      </td>
    </tr>
    <tr>
      <td  colspan="8" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
      </td>
   </tr>
  </table>
  <table width='100%' class="searchTable" onkeypress="DWRUtil.onReturn(event, search)">
	<tr>
     <td  class="infoTitle" width="35%"><@bean.message key="attr.stdNo"/>:</td>
     <td>
      <input type="text" name="std.code" maxlength="32" size="10" value="${RequestParameters["std.code"]?if_exists}" style="width:100px;"/>
     </td>
	</tr>
	<tr>
     <td   class="infoTitle"><@msg.message key="attr.personName"/>:</td>
     <td>
      <input type="text" name="std.name" size="10" maxlength="20" value="${RequestParameters["std.name"]?if_exists}" style="width:100px;"/>
     </td>
	</tr>
   <tr>
     <td class="infoTitle">所在年级:</td>
     <td><input type="text" name="std.enrollYear" id='std.enrollYear' style="width:100px;" maxlength="7" value="${RequestParameters["std.enrollYear"]?if_exists}"/></td>
   </tr>
	<tr>
		<td><@bean.message key="entity.studentType"/></td>
		<td>
			<select id="stdType" name="std.type.id" style="width:100px" value="${RequestParameters["std.type.id"]?if_exists}">
				<option value=""><@bean.message key="common.all"/></option>
			</select>
		</td>
	</tr>
	<tr>
		<td><@bean.message key="attr.year2year"/></td>
		<td>
			<select id="year" name="calendar.year" style="width:100px" value="${RequestParameters["std.year"]?if_exists}">
				<option value=""><@bean.message key="common.all"/></option>
			</select>
		</td>
	</tr>
	<tr>
		<td><@bean.message key="attr.term"/></td>
		<td colspan="3">
			<select id="term" name="calendar.term" style="width:100px" value="${RequestParameters["std.term"]?if_exists}">
				<option value=""><@bean.message key="common.all"/></option>
			</select>
		</td>
	</tr>
	<tr>
     <td class="infoTitle"><@bean.message key="common.college"/>:</td>
     <td><@htm.i18nSelect datas=departmentList name="std.department.id" selected=RequestParameters["std.department.id"]?default("") style="width:100px;">
            <option value=""><@bean.message key="filed.choose"/></option>
          </@>
     </td>
    </tr>
	<tr>
     <td class="infoTitle"><@bean.message key="common.adminClass"/>:</td>
     <td>
      <input type="text" name="adminClassName" value="${RequestParameters["adminClassName"]?if_exists}" maxlength="20" style="width:100px;"/>
     </td>
    </tr>
	<tr>
     <td class="infoTitle">类别:</td>
     <td>
       <select name="isAward" style="width:100px;" value="${RequestParameters["isAward"]?if_exists}">
            <option value="1">奖励</option>
            <option value="0">处分</option>
        </select>	 
     </td>
    </tr>
	<tr>
    <tr align="center" height="50px">
     <td colspan="2">
	     <button style="width:60px" class="buttonStyle" onClick="search(1)"><@bean.message key="action.query"/></button>
     </td>
    </tr>
	<#assign stdTypeNullable=true/>
	<#assign yearNullable=true/>
	<#assign termNullable=false/>
	<#include "/templates/calendarSelect.ftl"/>
</table>