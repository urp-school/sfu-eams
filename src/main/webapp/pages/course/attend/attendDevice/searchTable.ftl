 <table width="100%" onkeypress="DWRUtil.onReturn(event, searchClassroom)">
    <tr>
      <td colspan="2" class="infoTitle" align="left" valign="bottom" >
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B>考勤监控查询</B>
      </td>
    <tr>
      <td colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
   <tr>
     <td class="infoTitle">考勤机ID:</td>
     <td><input name="attendDevice.devid" type="text" value="${RequestParameters["attendDevice.devid"]?if_exists}" style="width:100px" maxlength="20"/></td>
    </tr>
    <tr>
     <td class="infoTitle">教室名称:</td>
     <td><input name="attendDevice.jsid.name" type="text" value="${RequestParameters["attendDevice.jsid.name"]?if_exists}" style="width:100px" maxlength="20"/></td>
    </tr>
    <tr>
     <td class="infoTitle">教室代码:</td>
     <td><input name="attendDevice.jsid.code" type="text" value="${RequestParameters["attendDevice.jsid.code"]?if_exists}" style="width:100px" maxlength="32"/></td>
    </tr>
    <tr>
      <td class="infoTitle">教室设备配置:</td>
      <td id="configType"><@htm.i18nSelect datas=classroomConfigTypeList selected=RequestParameters["attendDevice.jsid.configType.id"]?default("") name="attendDevice.jsid.configType.id" style="width:100px;"><option value=""><@bean.message key="common.all"/></option></@></td>
    </tr>
	<tr>
	   <td class="infoTitle">校区:</td>
 	    <td>
  	       <select id="district" name="attendDevice.jsid.schoolDistrict.id" style="width:100px;" value="${RequestParameters["attendDevice.jsid.schoolDistrict.id"]?if_exists}">
	           <option value=""><@bean.message key="common.all"/></option>
	       </select>
        </td>
    </tr>
    <tr>
      <td class="infoTitle">教学楼:</td>
      <td>
         <select id="building" name="attendDevice.jsid.building.id" style="width:100px;" value="${RequestParameters["attendDevice.jsid.building.id"]?if_exists}">
	         <option value=""><@bean.message key="common.all"/></option>
         </select>
 	    </td>
    </tr>
    ${extraSearchOptions?default("")}
    <tr><td>考勤状态:</td><td><select name="attendDevice.kqjzt" style="width:100px;" value="${RequestParameters["attendDevice.kqjzt"]?if_exists}">
	   		<option value="" selected>全部</option>
	   		<option value="1" >正常</option>
	   		<option value="0" >出错</option>
	   </select>
	</td></tr>
    <tr align="center" heigth="50px">
     <td colspan="2"><button  onClick="searchClassroom()"><@bean.message key="action.query"/></button></td>
    </tr>	    
  </table>