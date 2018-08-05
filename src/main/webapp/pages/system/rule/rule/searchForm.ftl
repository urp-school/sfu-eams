  <table width="100%" onkeypress="DWRUtil.onReturn(event, searchRule)">
    <tr>
      <td colspan="2" class="infoTitle" align="left" valign="bottom" >
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B>规则条件查询</B>
      </td>
    <tr>
      <td colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
      </td>
   </tr>
    <form name="ruleForm" action="rule.do?method=search" target="contentListFrame" method="post" onsubmit="return false;">
   	<tr><td><@bean.message key="attr.infoname"/>:</td><td><input type="text" name="rule.name" style="width:100px;" maxlength="20"/></td></tr>
    <tr><td width="40%">适用业务:</td><td><input type="text" name="rule.business" style="width:100px;" maxlength="32"/></td></tr>
	<tr><td><@msg.message key="attr.state"/>:</td><td><select name="rule.enabled" style="width:100px;" value="${RequestParameters["rule.enabled"]?if_exists}">
	   		<option value="1" selected><@bean.message key="common.enabled" /></option>
	   		<option value="0" ><@bean.message key="common.disabled" /></option>
	   </select>
	</td></tr>	
    <tr height="50px"><td align="center" colspan="2"><input type="button" onclick="searchRule();" value="<@bean.message key="action.query"/>"   class="buttonStyle"/></td></tr>
    </form>
  </table>
