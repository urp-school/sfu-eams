 <table width="100%">
	    <tr>
	      <td  class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B><@msg.message key="baseinfo.searchStudent"/></B>      
	      </td>
	    </tr>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>	
  </table>
  <table width='100%' class="searchTable" onkeypress="DWRUtil.onReturn(event, search)">
    	<tr>
	     <td  class="infoTitle" width="35%"><@bean.message key="attr.stdNo"/>:</td>
	     <td>
	      <input type="text" name="student.code" maxlength="32" size="10" value="${RequestParameters["student.code"]?if_exists}" style="width:100px;"/>
	     </td>
		</tr>
    	<tr>
	     <td class="infoTitle"><@msg.message key="attr.personName"/>:</td>
	     <td>
	      <input type="text" name="student.name" maxlength="20" size="10" value="${RequestParameters["student.name"]?if_exists}" style="width:100px;"/>
	     </td>
		</tr>
	   <tr>
	     <td class="infoTitle">所在年级:</td>
	     <td><input type="text" name="student.enrollYear" id='student.enrollYear' value="${RequestParameters["student.enrollYear"]?if_exists}" style="width:100px;"></td>
	   </tr>
       <tr> 
	     <td class="infoTitle"><@bean.message key="entity.studentType"/>:</td>
	     <td>
	          <select id="std_stdTypeOfSpeciality" name="student.type.id" style="width:100px;" value="${RequestParameters["student.type.id"]?if_exists}">
	            <option value=""><@bean.message key="filed.choose"/></option>
	          </select>	 
         </td>
		</tr>
    	<tr>
	     <td class="infoTitle"><@bean.message key="common.college"/>:</td>
	     <td>
           <select id="std_department" name="student.secondMajor.department.id" style="width:100px;" value="${RequestParameters["student.secondMajor.department.id"]?if_exists}">
         	  <option value=""><@bean.message key="filed.choose"/>...</option>
           </select>
         </td>
        </tr> 
	   <tr>
	     <td class="infoTitle"><@bean.message key="entity.speciality"/>:</td>
	     <td>
           <select id="std_speciality" name="student.secondMajor.id" style="width:100px;" value="${RequestParameters["student.secondMajor.id"]?if_exists}">
         	  <option value=""><@bean.message key="filed.choose"/>...</option>
           </select>
         </td>
        </tr>
	   <tr>
	     <td class="infoTitle"><@bean.message key="entity.specialityAspect"/>:</td>
	     <td>
           <select id="std_specialityAspect" name="student.secondAspect.id" style="width:100px;" value="${RequestParameters["student.secondAspect.id"]?if_exists}">
         	  <option value=""><@bean.message key="filed.choose"/>...</option>
           </select>
         </td>
        </tr>
    	<tr>
	     <td class="infoTitle"><@bean.message key="common.adminClass"/>:</td>
	     <td>
	      <input type="text" name="adminClass.name" value="${RequestParameters["adminClass.name"]?if_exists}" style="width:100px;" maxlength="20"/>
         </td>
        </tr>
    	<tr>
	     <td class="infoTitle">是否就读:</td>
	     <td>
	      <select name="student.isSecondMajorStudy" value="${RequestParameters["adminClass.isSecondMajorStudy"]?if_exists}" style="width:100px;">
	        <option value="1">就读</option>
	        <option value="0">无效</option>
	        <option value="">全部</option>
	      </select>
         </td>
        </tr>
    	<tr>
	    <tr align="center" height="50px">
	     <td colspan="2">
		     <button style="width:60px" class="buttonStyle" onClick="search(1)"><@bean.message key="action.query"/></button>
	     </td>
	    </tr>
  </table>
<script>
    var sds = new StdTypeDepart3Select("std_stdTypeOfSpeciality","std_department","std_speciality","std_specialityAspect",false,true,true,true);    
    sds.init(stdTypeArray,departArray);
    sds.firstSpeciality=2;
</script> 