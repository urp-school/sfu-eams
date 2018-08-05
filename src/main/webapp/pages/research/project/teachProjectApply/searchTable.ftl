 <table width="100%">
	    <tr>
	      <td  class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B><@msg.message key="action.advancedQuery.like"/></B>      
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
	     <td  class="infoTitle" width="35%"><@bean.message key="research.harvest.projectNO" />:</td>
	     <td>
	      <input type="text" name="teachProject.code" maxlength="32" size="10" value="${RequestParameters['teachProject.code']?if_exists}"/>
	     </td>
		</tr>
		<tr>
	     <td  class="infoTitle" width="35%"><@bean.message key="research.harvest.projectName" />:</td>
	     <td>
	      <input type="text" name="teachProject.name" maxlength="32" size="10" value="${RequestParameters['teachProject.name']?if_exists}"/>
	     </td>
		</tr>
		<tr>
	     <td  class="infoTitle" width="35%">项目类别:</td>
	     <td>
	     <@htm.i18nSelect selected="" datas=teachProjectTypes name="teachProject.teachProjectType.id" style="width:95px">
	        	<option value="">...</option>
	        </@>
	     </td>
		</tr>
		<tr>
	     <td  class="infoTitle" width="35%"><@bean.message key="research.harvest.principal" />:</td>
	     <td>
	       <input type="text" name="teachProject.principal.name" maxlength="32" size="10" value="${RequestParameters['teachProject.principal.name']?if_exists}"/>
	     </td>
		</tr>
		<tr>
	     <td  class="infoTitle" width="35%"><@bean.message key="attr.graduate.auditStatus" />:</td>
	     <td>
	     <@htm.i18nSelect selected="" datas=teachProjectStates name="teachProject.teachProjectState.id" style="width:95px">
	        	<option value="">...</option>
	        </@>
	     </td>
		</tr>
    	<tr>
	    <tr align="center">
	     <td colspan="2">
		     <button style="width:60px" class="buttonStyle" onClick="search(1)"><@bean.message key="action.query"/></button>
	     </td>               
	    </tr>
  </table>
