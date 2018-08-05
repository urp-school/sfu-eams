	   <table width="100%">
	    <tr>
	      <td align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B><@msg.message key="action.advancedQuery.like"/></B>
	      </td>
	    </tr>
	    <tr>
	      <td colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
        </tr>
	  </table>
	  <table onkeypress="DWRUtil.onReturn(event, searchTask)" width="100%">
	    <tr> 
	     <td width="35%"><@bean.message key="attr.taskNo"/>:</td>
	     <td><input name="task.seqNo" type="text" value="${RequestParameters["task.seqNo"]?if_exists}" style="width:100px" maxlength="32"/></td>
	    </tr>
	    <tr>
	     <td><@bean.message key="attr.courseNo"/>:</td>
	     <td><input name="task.course.code" type="text" value="${RequestParameters["task.course.code"]?if_exists}" style="width:100px" maxlength="32"/></td>
	    </tr>
	    <tr>
	     <td><@bean.message key="attr.courseName"/>:</td>
	     <td><input type="text" name="task.course.name" value="${RequestParameters["task.course.name"]?if_exists}" style="width:100px" maxlength="20"/></td>
	    </tr>
	    <tr>
	     <td><@bean.message key="entity.courseType"/>:</td>
	     <td><select name="task.courseType.id" style="width:100px" value="${RequestParameters["task.course.id"]?if_exists}">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list sort_byI18nName(courseTypes) as courseType>
                <option value=${courseType.id}><@i18nName courseType/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr>
	     <td><@bean.message key="attr.teach4Depart"/>:</td>
	     <td>
		     <select name="task.teachClass.depart.id" value="" style="width:100px" value="${RequestParameters["task.teachClass.depart.id"]?if_exists}">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list (departmentList)?sort_by("code") as depart>
                <option value=${depart.id}><@i18nName depart/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr>
	     <td><@bean.message key="attr.teachDepart"/>:</td>
	     <td>
		     <select name="task.arrangeInfo.teachDepart.id" value="" style="width:100px" value="${RequestParameters["task.arrangeInfo.teachDepart.id"]?if_exists}">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list (teachDepartList)?sort_by("code") as teachDepart>
		     	<option value=${teachDepart.id}><@i18nName teachDepart/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr>
	     <td><@bean.message key="entity.teacher"/>:</td>
	     <td><input type="text" name="teacher.name" value="${RequestParameters["teacher.name"]?if_exists}" style="width:100px" maxlength="20"/>
	     </td>
	    </tr>
	    <tr>
	     <td><@bean.message key="attr.enrollTurn"/>:</td>
	     <td><input type="text" name="task.teachClass.enrollTurn" value="${RequestParameters["task.teachClass.enrollTurn"]?if_exists}" maxlength="7" style="width:100px"/></td>
	    </tr>
	    <tr>
	     <td><@bean.message key="entity.studentType"/>:</td>
	     <td><@htm.i18nSelect datas=calendarStdTypes selected=RequestParameters["task.teachClass.enrollTurn"]?default("") name="task.teachClass.stdType.id"  style="width:100px">
	           	<option value=""><@bean.message key="common.all"/></option>
		     </@>
	     </td>
	    </tr>
	    <tr>
	     <td><@msg.message key="attr.credit"/>:</td>
	     <td><input type="text" name="task.course.credits" value="${RequestParameters["task.course.credits"]?if_exists}" style="width:100px" maxlength="3"/>
	     </td>
	    </tr>
	    <tr>
	     <td><@msg.message key="course.weekHours"/>:</td>
	     <td><input type="text" name="task.arrangeInfo.weekUnits" value="${RequestParameters["task.arrangeInfo.weekUnits"]?if_exists}" style="width:100px" maxlength="3"/></td>
	    </tr>
		<tr>
		     <td><@msg.message key="course.weekFrom"/>:</td>
		     <td><input name="task.arrangeInfo.weekStart" style="width:100px" value="${RequestParameters["task.arrangeInfo.weekStart"]?if_exists}" maxlength="2"/></td>
		</tr>
	    <tr>
	     <td><@bean.message key="attr.GP"/>:</td>
	     <td>
	        <select name="task.requirement.isGuaPai" value="${RequestParameters["task.requirement.isGuaPai"]?if_exists}" style="width:100px">
	           <option value=""><@bean.message key="common.all"/></option>
	           <option value="1"><@bean.message key="common.yes"/></option>
	           <option value="0"><@bean.message key="common.no"/></option>
	        </select>
	     </td>
	    </tr>
        ${extraSearchTR?if_exists}
	    <tr align="center" height="50">
	     <td colspan="2">
		     <button onClick="if(validateInput(this.form)){searchTask();}" accesskey="Q" class="buttonStyle" style="width:80px">
		       <@bean.message key="action.query"/>(<U>Q</U>)
		     </button>
	     </td>
	    </tr>
	  </table>
	  <script>
	     function validateInput(form){
	        var errors="";
	        if(""!=form['task.course.credits'].value&&!/^\d*\.?\d*$/.test(form['task.course.credits'].value)){
	           errors+="学分"+form['task.course.credits'].value+"格式不正确，应为正实数\n";
	        }
	        if(""!=form['task.arrangeInfo.weekUnits'].value&&!/^\d+$/.test(form['task.arrangeInfo.weekUnits'].value)){
	           errors+="周课时"+form['task.arrangeInfo.weekUnits'].value+"格式不正确，应为正整数\n";
	        }
	        if(""!=form['task.arrangeInfo.weekStart'].value&&!/^\d+$/.test(form['task.arrangeInfo.weekStart'].value)){
	           errors+="起始周"+form['task.arrangeInfo.weekStart'].value+"格式不正确，应为正整数\n";
	        }
	        if(""!=errors){
	           alert(errors);return false;
	        }
	        return true;
	     }
	  </script>