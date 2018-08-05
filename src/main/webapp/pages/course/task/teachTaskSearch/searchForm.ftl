	   <table  width="100%">
	    <tr>
	      <td  class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B><@bean.message key="action.advancedQuery"/></B>      
	      </td>
	    </tr>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>
	  </table>
	  <table class="searchTable"  onkeypress="DWRUtil.onReturn(event, searchTask)">
	    <tr> 
	     <td class="infoTitle" ><@bean.message key="attr.taskNo"/>:</td>
	     <td><input name="task.seqNo" type="text" value="${RequestParameters["task.seqNo"]?if_exists}" style="width:100px" maxlength="32"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.courseNo"/>:</td>
	     <td><input name="task.course.code" type="text" value="${RequestParameters["task.course.code"]?if_exists}" style="width:100px" maxlength="32"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.courseName"/>:</td>
	     <td><input type="text" name="task.course.name" value="${RequestParameters["task.course.name"]?if_exists}" style="width:100px" maxlength="20"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="entity.courseType"/>:</td>
	     <td><input type="text" name="task.courseType.name" value="${RequestParameters["task.courseType.name"]?if_exists}" style="width:100px" maxlength="20"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.teach4Depart"/>:</td>
	     <td>
		     <select name="task.teachClass.depart.id" value="${RequestParameters["task.teachClass.depart.id"]?if_exists}" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list sort_byI18nName(departmentList) as depart>
                <option value=${depart.id}><@i18nName depart/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.teachDepart"/>:</td>
	     <td>
		     <select name="task.arrangeInfo.teachDepart.id" value="${RequestParameters["task.arrangeInfo.teachDepart.id"]?if_exists}" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list sort_byI18nName(teachDepartList) as teachDepart>
		     	<option value=${teachDepart.id}><@i18nName teachDepart/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr>
	     <td class="infoTitle" ><@bean.message key="entity.teacher"/>:</td>
	     <td>
	     <input name="teacher.name" value="${RequestParameters["teacher.name"]?if_exists}" style="width:100px" maxlength="20"/>
	     </td>
	    </tr>
	    <tr>
	     <td class="infoTitle" >教师院系:</td>
	     <td>
	     <@htm.i18nSelect name="teacher.department.id" datas=departmentList selected=RequestParameters["teacher.department.id"]?default("") style="width:100px"><option value=""></option></@>
	     </td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.enrollTurn"/>:</td>
	     <td><input type="text" name="task.teachClass.enrollTurn" value="${RequestParameters["task.teachClass.enrollTurn"]?if_exists}" maxlength="7" style="width:100px"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="entity.studentType"/>:</td>
	     <td>
		     <select name="task.teachClass.stdType.id" value="${RequestParameters["task.teachClass.stdType.id"]?if_exists}" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list calendarStdTypes as stdType>
		     	<option value=${stdType.id}><@i18nName stdType/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr>
	     <td class="infoTitle">星期:</td>
	     <td>
		     <select name="courseActivity.time.week" value="${RequestParameters["courseActivity.time.week"]?if_exists}" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list weeks as week>
		     	<option value=${week.id}>${week.name}</option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr>
	     <td class="infoTitle">小节:</td>
	     <td>
		     <select name="courseActivity.time.startUnit" value="${RequestParameters["courseActivity.time.startUnit"]?if_exists}" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list 1..14 as unit>
		     	<option value=${unit}>${unit}</option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr align="center">
	     <td colspan="2">
		     <input type="button" onClick="searchTask()" class="buttonStyle" value="<@bean.message key="action.query"/>" style="width:60px"/>
	     </td>
	    </tr>
	  </table>
	  <script>
        function populateTaskParams(searchForm,taskForm){
		    var elems = taskSearchForm.elements;
		    for(i =0;i <elems.length; i++){
		      if(elems[i].name.indexOf("task")==0||elems[i].name.indexOf(".")>1){
		            if(taskForm[elems[i].name]!=null){
  			          taskForm[elems[i].name].value= elems[i].value;
  			        }
  			        else{
  			          addInput(taskForm,elems[i].name,elems[i].value);
  			        }
			  }
		    }
        }
	  </script>