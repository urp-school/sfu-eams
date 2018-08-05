	   <table width="100%">
	    <tr>
	      <tdalign="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B>教学任务查询(模糊)</B>
	      </td>
	    </tr>
	    <tr>
	      <td colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>
	  </table>
	  <table width="100%" onkeypress="DWRUtil.onReturn(event, search)">
	    <tr>
	     <td width="40%"><@bean.message key="attr.taskNo"/>:</td>
	     <td><input name="task.seqNo" type="text" value="" style="width:100px" maxlength="32"/></td>
	    </tr>
	    <tr>
	     <td><@bean.message key="attr.courseNo"/>:</td>
	     <td><input name="task.course.code" type="text" value="" style="width:100px" maxlength="32"/></td>
	    </tr>
	    <tr>
	     <td><@bean.message key="attr.courseName"/>:</td>
	     <td><input type="text" name="task.course.name" value="" style="width:100px" maxlength="20"/></td>
	    </tr>
	    <tr>
	     <td><@bean.message key="entity.courseType"/>:</td>
	     <td> 
	      <select name="task.courseType.id" value="" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list sort_byI18nName(courseTypes) as courseType>
		     	<option value=${courseType.id}><@i18nName courseType/></option>
		     	</#list>
		     </select>
		   </td>
	    </tr>
	    <tr>
	     <td>主考院系:</td>
	     <td>
		     <select name="task.arrangeInfo.teachDepart.id" value="" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list sort_byI18nName(examDeparts) as examDepart>
		     	<option value=${examDepart.id}><@i18nName examDepart/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr>
	     <td>监考院系:</td>
	     <td>
		     <select name="activity.examMonitor.depart.id" value="" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list sort_byI18nName(invigilateDeparts) as invigilateDepart>
		     	<option value=${invigilateDepart.id}><@i18nName invigilateDepart/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr>
	     <td><@bean.message key="attr.enrollTurn"/>:</td>
	     <td><input type="text" name="task.teachClass.enrollTurn" value="" maxlength="7" style="width:100px"/></td>
	    </tr>
	    <tr>
	     <td><@bean.message key="entity.studentType"/>:</td>
	     <td>
		     <select name="task.teachClass.stdType.id" value="" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list calendarStdTypes as stdType>
		     	<option value=${stdType.id}><@i18nName stdType/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr>
	     <td>排考组:</td>
	     <td>
		     <select name="examGroup.id" value="" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list groups as group>
		     	<option value="${group.id}"><@i18nName group/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr>
	     <td>考试地点:</td>
	     <td><input type="text" name="activity.room.name" value="" maxlength="20" style="width:100px"/></td>
	    </tr>
	    <tr>
	    	<td>主考工号:</td>
	    	<td><input type="text" name="activity.teacher.code" value="" maxlength="50" style="width:100px"/></td>
	    </tr>
	    <tr>
	    	<td>主考姓名:</td>
	    	<td><input type="text" name="activity.teacher.name" value="" maxlength="50" style="width:100px"/></td>
	    </tr>
	    <tr>
	    	<td>监考工号:</td>
	    	<td><input type="text" name="activity.examMonitor.invigilator.code" value="" maxlength="50" style="width:100px"/></td>
	    </tr>
	    <tr>
	    	<td>监考姓名:</td>
	    	<td><input type="text" name="activity.examMonitor.invigilator.name" value="" maxlength="50" style="width:100px"/></td>
	    </tr>
	    <tr align="center">
	     <td colspan="2" height="50px">
		     <input type="button" onClick="search()" class="buttonStyle" value="<@bean.message key="action.query"/>" style="width:60px"/>
	     </td>
	    </tr>
      </table>