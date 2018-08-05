 <table width="100%" onkeypress="DWRUtil.onReturn(event, searchClassroom)">
    <tr>
      <td colspan="2" class="infoTitle" align="left" valign="bottom" >
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B><@bean.message key="baseinfo.searchRoom"/></B>
      </td>
    <tr>
      <td colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
    <tr>
     <td class="infoTitle"><@bean.message key="attr.name"/>:</td>
     <td><input name="classroom.name" type="text" value="${RequestParameters["classroom.name"]?if_exists}" style="width:100px" maxlength="20"/></td>
    </tr>
    <tr>
     <td class="infoTitle"><@msg.message key="attr.code"/>:</td>
     <td><input name="classroom.code" type="text" value="${RequestParameters["classroom.code"]?if_exists}" style="width:100px" maxlength="32"/></td>
    </tr>
    <tr>
      <td class="infoTitle"><@bean.message key="entity.classroomConfigType"/>:</td>
      <td id="configType"><@htm.i18nSelect datas=classroomConfigTypeList selected=RequestParameters["classroom.configType.id"]?default("") name="classroom.configType.id" style="width:100px;"><option value=""><@bean.message key="common.all"/></option></@></td>
    </tr>
	<tr>
	   <td class="infoTitle"><@bean.message key="entity.schoolDistrict"/>:</td>
 	    <td>
  	       <select id="district" name="classroom.schoolDistrict.id" style="width:100px;" value="${RequestParameters["classroom.schoolDistrict.di"]?if_exists}">
	           <option value=""><@bean.message key="common.all"/></option>
	       </select>
        </td>
    </tr>
    <tr>
      <td class="infoTitle"><@bean.message key="entity.building"/>:</td>
      <td>
         <select id="building" name="classroom.building.id" style="width:100px;" value="${RequestParameters["classroom.building.id"]?if_exists}">
	         <option value=""><@bean.message key="common.all"/></option>
         </select>
 	    </td>
    </tr>
    <tr>
 	    <td class="infoTitle"><@msg.message key="entity.department"/>:</td>
 	    <td>
 	       <@htm.i18nSelect datas=roomDepartList name="roomDepartId" selected=RequestParameters['roomDepartId']?default("") style="width:100px;"><option value=""><@bean.message key="common.all"/></option></@>
 	    </td>
    </tr>
        <tr>
           <td>考试人数：</td>
           <td><input type="text" name="examCountFrom" value="${RequestParameters["examCountFrom"]?if_exists}" style="width:43px;" maxlength="4"/>－<input type="text" name="examCountTo" value="${RequestParameters["examCountTo"]?if_exists}" style="width:43px;" maxlength="4"/></td>
        </tr>
        <tr>
           <td>听课人数：</td>
           <td><input type="text" name="courseCountFrom" value="${RequestParameters["courseCountFrom"]?if_exists}" style="width:43px;" maxlength="4"/>－<input type="text" name="courseCountTo" value="${RequestParameters["courseCountTo"]?if_exists}" style="width:43px;" maxlength="4"/></td>
        </tr>
        <tr>
           <td>真正容量：</td>
           <td><input type="text" name="capacityFrom" value="${RequestParameters["capacityFrom"]?if_exists}" style="width:43px;" maxlength="4"/>－<input type="text" name="capacityTo" value="${RequestParameters["capacityTo"]?if_exists}" style="width:43px;" maxlength="4"/></td>
        </tr>
    ${extraSearchOptions?default("")}
    <tr align="center" heigth="50px">
     <td colspan="2"><button  onClick="searchClassroom()"><@bean.message key="action.query"/></button></td>
    </tr>	    
  </table>