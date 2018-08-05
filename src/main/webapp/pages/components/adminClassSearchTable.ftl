<table width="100%" onkeypress="DWRUtil.onReturn(event, searchClass)">
    <tr>
      <td colspan="2" class="infoTitle" align="left" valign="bottom">
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B><@bean.message key="baseinfo.searchClass"/></B>
      </td>
    <tr>
      <td  colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
    </tr>
    <tr> 
     <td class="infoTitle" width="40%"><@bean.message key="attr.enrollTurn"/>：</td>
     <td><input name="adminClass.enrollYear" type="text" value="" style="width:100px" maxlength="7"/></td>
    </tr>
    <tr>
     <td class="infoTitle"><@bean.message key="attr.name"/>：</td>
     <td><input name="adminClass.name" type="text" value="" style="width:100px" maxlength="20"/></td>
    </tr>
    <tr>
      <td class="infoTitle"><@bean.message key="entity.studentType"/>：</td>
      <td align="left" id="f_specialityAspect">
        <select name="adminClass.stdType.id" id="class_stdTypeOfSpeciality" style="width:100px;">
         	<option value=""><@bean.message key="common.selectPlease"/></option>
        </select>
      </td>
    </tr>
    <tr>
   	 <td class="infoTitle" id="f_department"><@bean.message key="entity.college"/>：</td>
      <td>
        <select id="class_department" name="adminClass.department.id" style="width:100px;">
           <option value=""><@bean.message key="common.selectPlease"/></option>
        </select>
      </td>
     </td>
    </tr>
    <tr>
      <td class="infoTitle" ><@bean.message key="entity.speciality"/>：</td>
      <td align="left" id="f_speciality" >
        <select id="class_speciality" name="adminClass.speciality.id" style="width:100px;">
           <option value=""><@bean.message key="common.selectPlease"/></option>
        </select>
      </td>
    </tr>
    <tr>
      <td class="infoTitle"><@bean.message key="entity.specialityAspect"/>：</td>
      <td align="left" id="f_specialityAspect">
        <select id="class_specialityAspect" name="adminClass.aspect.id" style="width:100px;">
         <option value=""><@bean.message key="common.selectPlease"/></option>
        </select>
      </td>
    </tr>
	<tr>
     <td class="infoTitle">专业类别：</td>
     <td>
      <select name="majorTypeId" onchange="changeClassSpecialityType(event)" style="width:100px;">
        <option value="1">第一专业</option>
        <option value="2">第二专业</option>
      </select>
     </td>
    </tr>
	<tr>
		<td><@msg.message key="attr.state"/>：</td>
		<td><select name="adminClass.state" style="width:100px;">
		   		<option value="1" selected><@bean.message key="common.enabled"/></option>
		   		<option value="0"><@bean.message key="common.disabled"/></option>
	   		</select>
	   </td>
	</tr>
    <tr align="center" height="50px">
     <td colspan="2">
	     <button onclick="searchClass()" class="buttonStyle" style="width:60px">
	     <@bean.message key="action.query"/>
	     </button>
     </td>
    </tr>
  </table>
 <script>
    var classSelect = new StdTypeDepart3Select("class_stdTypeOfSpeciality","class_department","class_speciality","class_specialityAspect",true,true,true,true);
    classSelect.init(stdTypeArray,departArray);
    classSelect.firstSpeciality=1;
    function changeClassSpecialityType(event){
       var select = getEventTarget(event);
       classSelect.firstSpeciality=select.value;
       fireChange($("class_department"));
    }
</script>