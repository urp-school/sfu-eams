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
	     <td  class="infoTitle" width="35%"><@bean.message key="attr.courseName" />:</td>
	     <td>
	      <input type="text" name="lessonCheck.task.course.name" maxlength="32" size="10" value="${RequestParameters['lessonCheck.task.course.name']?if_exists}"/>
	     </td>
		</tr>
		<tr>
	     <td  class="infoTitle" width="35%"><@bean.message key="info.studentClassManager.className" />:</td>
	     <td>
	      <input type="text" name="lessonCheck.task.teachClass.name" maxlength="32" size="10" value="${RequestParameters['lessonCheck.task.teachClass.name']?if_exists}"/>
	     </td>
		</tr>
    	<tr>
	     <td class="infoTitle"><@bean.message key="entity.courseType" />:</td>
	     <td>
	        <@htm.i18nSelect selected="" datas=courseTypes name="lessonCheck.task.courseType.id" style="width:95px">
	        	<option value="">...</option>
	        </@>
         </td>
        </tr>
		<tr>
	     <td class="infoTitle">听课类别:</td>
	     <td>
	        <@htm.i18nSelect selected="" datas=lessonCheckTypes name="lessonCheck.lessonCheckType.id" style="width:95px">
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
