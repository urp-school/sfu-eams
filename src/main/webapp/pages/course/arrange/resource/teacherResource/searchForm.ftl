<#include "../departList.ftl"/>
 <div style="display: block;" id="view1">
	   <table width="100%">
	    <tr>
	      <td  class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B><@bean.message key="baseinfo.searchTeacher"/></B>
	      </td>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
	      </td>
	   </tr>
	  </table>
	  <table border="0" onKeyDown="javascript:enterQuery('teacher');" width="100%">
	    <form name="resourceSearchForm" method="post">
	    <input name="teacher.isTeaching" type="hidden" value="1" />
	    <tr>
	   	 <td class="infoTitle" id="f_department"><@bean.message key="teacher.code"/>:</td>
	   	 <td><input name="teacher.code" vlaue="" style="width:100px" maxlength="32"/></td>
	    </tr>
	    <tr>
	   	 <td class="infoTitle" id="f_department"><@bean.message key="attr.personName"/>:</td>
	   	 <td><input name="teacher.name" vlaue="" style="width:100px" maxlength="20"/></td>
	    </tr>	    
	    <tr class="infoTitle">
	   	 <td id="f_department"><@bean.message key="entity.college"/>:</td>
         <td>
          <select name="teacher.department.id" style="width:100px;">
          <option value=""><@bean.message key="common.all"/></option>
          <#list departmentList as depart>
          <option value="${depart.id}"><@i18nName depart/></option>
          </#list>
          </select>
         </td>
	    </tr>
	     <tr><td><@bean.message key="teacher.isTeaching"/>:</td>
	         <td>
	        <select name="teacher.isTeaching" style="width:100px;">
		   		<option value="1" selected><@bean.message key="common.yes" /></option>
		   		<option value="0" ><@bean.message key="common.no" /></option>
		   		<option value="" ><@bean.message key="common.all" /></option>
	        </select>
	     </td>
	     </tr>
	    <tr align="center">
	     <td colspan="2">
		     <input type="button" onClick="javascript:populateParams('teacher');searchResource()" class="buttonStyle" value="<@bean.message key="action.query"/>" style="width:60px"/>
	     </td>
	    </tr>
        </form>
	  </table>
    </div> 