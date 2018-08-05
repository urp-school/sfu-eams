    <table width="100%">
        <tr>
            <td colspan="2" align="left" valign="bottom" >
                <img src="${static_base}/images/action/info.gif" align="top"/>
                <B>请印申请查询</B>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="font-size:0px">
                <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
            </td>
        </tr>
	    <tr>
	     	<td>请印人代码:</td>
	     	<td><input name="filePrintApplication.applyBy.name" maxlength="32" type="text" value="" style="width:100px"/></td>
	    </tr>
	    <tr>
	     	<td>请印人名称:</td>
	     	<td><input name="filePrintApplication.applyBy.userName" maxlength="20" type="text" value="" style="width:100px"/></td>
	    </tr>
	    <tr>
	     	<td>审核人代码:</td>
	     	<td><input name="filePrintApplication.auditBy.name" maxlength="32" type="text" value="" style="width:100px"/></td>
	    </tr>
	    <tr>
	     	<td>审核人名称:</td>
	     	<td><input name="filePrintApplication.auditBy.userName" maxlength="20" type="text" value="" style="width:100px"/></td>
	    </tr>
	    <tr>
	     	<td>经办人代码:</td>
	     	<td><input name="filePrintApplication.managerBy.name" maxlength="32" type="text" value="" style="width:100px"/></td>
	    </tr>
	    <tr>
	     	<td>经办人名称:</td>
	     	<td><input name="filePrintApplication.managerBy.userName" maxlength="20" type="text" value="" style="width:100px"/></td>
	    </tr>
	    <tr>
	     <td>请印部门:</td>
	     <td>
		     <select name="filePrintApplication.depart.id" value="" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list sort_byI18nName(teachDepartList) as depart>
                <option value=${depart.id}><@i18nName depart/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr align="center" height="50px">
	     	<td colspan="2">
		        <input type="reset" class="buttonStyle" value="<@msg.message key="action.reset"/>" style="width:60px"/>
			    <input type="submit" onClick="searchFilePrintApplication()" class="buttonStyle" value="<@bean.message key="action.query"/>" style="width:60px"/>		    
	     	</td>
	    </tr>
	</table>
	<#include "/templates/stdTypeDepart3Select.ftl"/>