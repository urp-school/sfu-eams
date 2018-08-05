	   <table  width="100%">
	    <tr>
	      <td  class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B><@bean.message key="action.advancedQuery"/></B>      
	      </td>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>
	  </table>
	  <table class="searchTable"  onkeypress="DWRUtil.onReturn(event, search)">
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.courseName" />:</td>
	     <td><input type="text" maxlength="20" name="taskAlterRequest.task.course.name" value="" style="width:100px"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.teachDepart" />:</td>
	     <td>
		     <select name="taskAlterRequest.task.arrangeInfo.teachDepart.id" value="" style="width:100px">
		     	<option value=""><@bean.message key="common.all" /></option>
		     	<#list departmentList as teachDepart>
		     	<option value=${teachDepart.id}><@i18nName teachDepart/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="entity.teacher" />:</td>
	     <td>
	     <input type="text" maxlength="20" name="taskAlterRequest.teacher.name" value="" style="width:60px"/>
	     </td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="common.status"/>:</td>
	     <td>
		     <select name="taskAlterRequest.availability" value="" style="width:100px">
		     	<option value=""><@bean.message key="common.all" /></option>		     	
		     	<option value="Y"><@bean.message key="common.unprocessed"/></option>
		     	<option value="O"><@bean.message key="common.approved"/></option>
		     	<option value="N"><@bean.message key="common.invalidation"/></option>
		     </select>
	     </td>
	    </tr>	    
	    <tr align="center">
	     <td colspan="2">
		     <input type="button" onClick="search()" class="buttonStyle" value="<@bean.message key="action.query"/>" style="width:60px"/>
	     </td>               
	    </tr>
	  </table>