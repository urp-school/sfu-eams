 <table  width="100%">
	    <tr>
	      <td class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B><@msg.message key="baseinfo.searchStudent"/></B>
	      </td>
	    </tr>
	    <tr>
	      <td colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>	
  </table>
  <table width='100%' class="searchTable" onkeypress="DWRUtil.onReturn(event, search)">
    	<tr>
	     <td class="infoTitle" width="35%"><@bean.message key="attr.stdNo" />:</td>
	     <td>
	      <input type="text" name="check.std.code" maxlength="32" size="10" value="${RequestParameters['std.code']?if_exists}"/>
	     </td>
		</tr>
    	<tr>
	     <td class="infoTitle"><@msg.message key="attr.personName"/>:</td>
	     <td>
	      <input type="text" name="check.std.name" maxlength="20" size="10" value="${RequestParameters['std.name']?if_exists}"/>
	     </td>
		</tr>
	   <tr>
	     <td class="infoTitle">所在年级:</td>
	     <td><input type="text" name="check.std.enrollYear" id='std.enrollYear' style="width:60px;" maxlength="7"/></td>
	   </tr>
       <tr>
	     <td class="infoTitle"><@bean.message key="entity.studentType"/>:</td>
	     <td>
	          <select id="std_stdTypeOfSpeciality" name="check.std.type.id" style="width:100px;">
	            <option value=""><@bean.message key="filed.choose"/></option>
	          </select>
         </td>
		</tr>
    	<tr>
	     <td class="infoTitle"><@bean.message key="common.college"/>:</td>
	     <td >
           <select id="std_department" name="department.id" style="width:100px;">
         	  <option value=""><@bean.message key="filed.choose"/>...</option>
           </select>
         </td>
        </tr>
	   <tr>
	     <td class="infoTitle"><@bean.message key="entity.speciality"/>:</td>
	     <td>
           <select id="std_speciality" name="speciality.id" style="width:100px;">
         	  <option value=""><@bean.message key="filed.choose"/>...</option>
           </select>
         </td>
        </tr>
	   <tr>
	     <td class="infoTitle"><@bean.message key="entity.specialityAspect"/>:</td>
	     <td>
           <select id="std_specialityAspect" name="specialityAspect.id" style="width:100px;">
         	  <option value=""><@bean.message key="filed.choose"/>...</option>
           </select>
         </td>
        </tr>
    	<tr>
	     <td class="infoTitle"><@bean.message key="common.adminClass"/>:</td>
	     <td>
	      <input type="text" name="adminClassName" value="" style="width:100px;" maxlength="20"/>
         </td>
        </tr>
        <tr>
	     <td class="infoTitle">是否核对:</td>
	     <td ><select name="check.isConfirm" style="width:100px">
	           <option value=""><@msg.message key="common.all"/></option>
	           <option value="1">已核对</option>
	           <option value="0">未核对</option>
	          </select>
         </td>
        </tr>
    	<tr>
	     <td class="infoTitle">专业类别:</td>
	     <td >
	      <select name="majorTypeId" onchange="changeSpecialityType(event)">
	        <option value="1">第一专业</option>
	        <option value="2">第二专业</option>
	      </select>
         </td>
        </tr>
    	<tr>
	    <tr align="center">
	     <td colspan="2">
		     <button style="width:60px" class="buttonStyle" onClick="search(1)"><@bean.message key="action.query"/></button>
	     </td>
	    </tr>
  </table>
<script>
    var sds = new StdTypeDepart3Select("std_stdTypeOfSpeciality","std_department","std_speciality","std_specialityAspect",false,true,true,true);    
    sds.init(stdTypeArray,departArray);
    sds.firstSpeciality=1;
    function changeSpecialityType(event){
       var select = getEventTarget(event);
       sds.firstSpeciality=select.value;
       fireChange($("std_department"));
    }
</script> 