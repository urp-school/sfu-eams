 <div style="display: block;" id="view1">
	   <table width="100%">
	    <tr>
	      <td class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B><@bean.message key="baseinfo.searchRoom"/></B>
	      </td>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>
	  </table>
	  <table border="0" onKeyDown="javascript:enterQuery('classroom');">	  
	    <form name="resourceSearchForm" method="post" action="" onsubmit="return false;">
	    <tr>
	   	 <td class="infoTitle" ><@bean.message key="attr.name"/>:</td>
	   	 <td><input name="classroom.name" vlaue="" style="width:100px" maxlength="20"/></td>
	    </tr>
	    <tr>
	   	 <td class="infoTitle" ><@bean.message key="entity.classroomConfigType"/>:</td>
         <td>
          <select name="classroom.configType.id" style="width:100px;">
          <option value=""><@bean.message key="common.all"/></option>
          <#list classroomConfigTypeList as configType>
          <option value="${configType.id}">${configType.name}</option>
          </#list>
          </select>
         </td>
	    </tr>
	    <tr>
	   	 <td class="infoTitle"><@bean.message key="common.schoolDistrict"/>:</td>
	     <td >
 	       <select  id="district" name="classroom.schoolDistrict.id" style="width:100px;">
	           <option value=""><@bean.message key="common.all"/></option>
	       </select>
	      </td>
	    </tr>
	    <tr>
          <td class="infoTitle" ><@bean.message key="common.building"/>:</td>	    
	      <td align="left" >          
	 	      <select id="building" name="classroom.building.id" style="width:100px;">
		         <option value=""><@bean.message key="common.all"/></option>
	          </select>
	      </td>
	    </tr>
	    <tr>
          <td class="infoTitle" ><@bean.message key="entity.department"/>:</td>	    
	      <td align="left" id="f_speciality" >         
 	       <select name="depart.id" style="width:100px;">
           <option value=""><@bean.message key="common.all"/></option>
           <#list departmentList as depart>
           <option value="${depart.id}"><@i18nName depart/></option>
           </#list>
           </select>
	      </td>
	    </tr>
	    <tr align="center">
	     <td colspan="2">
		     <input type="button" onClick="javascript:populateParams('classroom');searchResource()" class="buttonStyle" value="<@bean.message key="action.query"/>" style="width:60px"/>
	     </td>               
	    </tr>
        </form>
	  </table>
    </div> 
<#include "../departList.ftl"/>