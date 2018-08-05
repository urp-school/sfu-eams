 <table width="100%">
	    <tr>
	      <td class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B><@msg.message key="baseinfo.searchStudent"/></B>
	      </td>
	    </tr>
	    <tr>
	      <td colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
	      </td>
	   </tr>	
  </table>
  <table width="100%" class="searchTable" >
    <input type="hidden" name="pageNo" value="1"/>
    	<tr>
	     <td class="infoTitle" width="40%"><@msg.message key="attr.stdNo"/>:</td>
	     <td>
	      <input type="text" name="student.code" value="${RequestParameters['std.code']?if_exists}" maxlength="32" style="width:100px"/>
	     </td>
		</tr>
    	<tr>
	     <td class="infoTitle"><@msg.message key="attr.personName"/>:</td>
	     <td>
	      <input type="text" name="student.name" value="${RequestParameters['std.name']?if_exists}" maxlength="20" style="width:100px"/>
	     </td>
		</tr>
	   <tr>
	     <td class="infoTitle">所在年级:</td>
	     <td><input type="text" name="student.enrollYear" value="${RequestParameters['std.enrollYear']?if_exists}" id='std.enrollYear' style="width:100px;" maxlength="7"/></td>
	   </tr>
    	<tr>
	     <td class="infoTitle"><@msg.message key="common.college"/>:</td>
	     <td>
           <select id="std_department" name="student.department.id" style="width:100px;">
         	  <option value=""><@msg.message key="filed.choose"/>...</option>
         	  <#list departments as department>
	            	<option value="${department.id}">${department.name}</option>
	          </#list>
           </select>
         </td> 
        </tr> 
	   <tr>
	     <td class="infoTitle"><@msg.message key="entity.speciality"/>:</td>
	     <td >
           <select id="std_speciality" name="student.speciality.id" style="width:100px;">
         	  <option value=""><@msg.message key="filed.choose"/>...</option>
           </select>
        </tr>
	   <tr>
	     <td class="infoTitle"><@msg.message key="entity.specialityAspect"/>:</td>
	     <td>
           <select id="std_specialityAspect" name="student.specialityAspect.id" style="width:100px;">
         	  <option value=""><@msg.message key="filed.choose"/>...</option>
           </select>
         </td>
        </tr>
    	<tr>
	     <td class="infoTitle"><@msg.message key="common.adminClass"/>:</td>
	     <td >
	      <input type="text" name="student.adminClassName" value="${RequestParameters['adminClassName']?if_exists}" style="width:100px;" maxlength="20"/>
         </td>
        </tr>
    	<tr>
	     <td class="infoTitle">专业类别:</td>
	     <td >
	      <select name="majorTypeId" onchange="changeSpecialityType(event)" style="width:100px;">
	        <option value="1">第一专业</option>
	        <option value="2">第二专业</option>
	      </select>
         </td>
        </tr>
    	<tr>
	     <td class="infoTitle">是否有效:</td>
	     <td >
	      <select name="std.active" style="width:100px;">
	        <option value=""><@msg.message key="common.all"/></option>
	        <option value="1">有效</option>
	        <option value="0">无效</option>
	      </select>
         </td>
        </tr>
        <tr>
	     <td class="infoTitle">是否毕业审核通过:</td>
	     <td>
	      <select name="std.graduateAuditStatus" style="width:100px;">
	        <option value=""><@msg.message key="common.all"/></option>
	        <option value="1">通过</option>
	        <option value="0">未通过</option>
	      </select>
         </td>
        </tr>
	    <tr align="center" height="30">
	     <td colspan="2">
		     <button style="width:60px" onClick="search(1)"><@msg.message key="action.query"/></button>
	     </td>
	    </tr>
  </table>
<script src='dwr/interface/departmentDAO.js'></script>
<script src='dwr/interface/specialityDAO.js'></script>
<script src='dwr/interface/specialityAspectDAO.js'></script>
<script src='scripts/stdTypeDepart3Select.js'></script>
<script>
    <#if result?if_exists.stdTypeList?exists>
    <#assign stdTypeList=result.stdTypeList>
    </#if>
    <#if result?if_exists.departmentList?exists>
    <#assign departmentList=result.departmentList>
    </#if>
    var stdTypeArray = new Array();
    <#list stdTypeList as stdType>
    stdTypeArray[${stdType_index}]={'id':'${stdType.id?if_exists}','name':'<@i18nName stdType/>'};
    </#list>
    var departArray = new Array();
    <#list departmentList as depart>
    departArray[${depart_index}]={'id':'${depart.id?if_exists}','name':'<@i18nName depart/>'};
    </#list>
    <#if !(stdTypeNullable?exists)>
      <#assign stdTypeNullable=false>
    </#if>
    var sds = new StdTypeDepart3Select("stdType","std_department","std_speciality","std_specialityAspect",${stdTypeNullable?string},true,true,true);
    sds.init(stdTypeArray,departArray);
</script>
