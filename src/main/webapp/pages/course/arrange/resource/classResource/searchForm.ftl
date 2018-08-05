<#include "../departList.ftl"/>      
 <div style="display: block;" id="view1">
	   <table  width="100%">
	    <tr>
	      <td  class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B><@bean.message key="baseinfo.searchClass"/></B>
	      </td>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>
	  </table>	  
	  <table border="0"  onKeyDown="javascript:enterQuery('adminClass');">	  
	    <form name="resourceSearchForm" method="post">
	    <tr>
	   	 <td class="infoTitle"><@bean.message key="attr.id"/></td>
	     <td ><input name="adminClass.code" vlaue="" style="width:100px" maxlength="32"/></td>
	    </tr>
	    <tr>
	   	 <td class="infoTitle"><@bean.message key="attr.name"/></td>
	   	 <td ><input name="adminClass.name" vlaue="" style="width:100px" maxlength="20"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="adminClass.enrollYear"/></td>
         <td ><input type="text" name="adminClass.enrollYear" maxlength="7" style="width:100px;"/></td>
	    </tr>
	    <tr class="infoTitle">
		    <td class="infoTitle"> <@bean.message key="common.college"/></td>
	        <td>
	           <select id="department" name="adminClass.department.id"  style="width:100px;" >
	           <option value=""><@bean.message key="common.selector"/></option>
	           </select>           
	        </td>
	    </tr>
	    <tr>
	       <td class="infoTitle"><@bean.message key="entity.speciality"/></td>
	       <td  style="width:100px;">
	           <select id="speciality" name="adminClass.speciality.id"  style="width:100px;">
	           <option value=""><@bean.message key="common.selector"/></option>
	           </select>
	        </td>
	    </tr>
	    <tr>
	        <td class="infoTitle"> <@bean.message key="entity.studentType" /></td>
	        <td>	         
	          <select name="adminClass.stdType.id" style="width:100px">
	              <option value=""><@bean.message key="common.all"/></option>
		          <#list stdTypeList as sty>
		          <option value="${sty.id}" >${sty.name}</option>
		          </#list>
	          </select>
	        </td>
        </tr>
	    <tr align="center">
	     <td colspan="2">
		     <input type="button" onClick="javascript:populateParams('adminClass');searchResource()" class="buttonStyle" value="<@bean.message key="action.query"/>" style="width:60px"/>
	     </td>               
	    </tr>
        </form>
	  </table>
    </div> 