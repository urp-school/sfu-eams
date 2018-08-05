    <div style="display: block;" id="view0">
	   <table width="100%">
	    <tr>
	      <td class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B>教学任务查询(模糊)</B>
	      </td>
	    </tr>
	    <tr>
	      <td colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
	      </td>
	   </tr>
	  </table>
	  <table class="searchTable" onkeypress="DWRUtil.onReturn(event, searchTask)">
	    <form name="taskSearchForm" method="post" target="contentFrame">
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.taskNo"/>:</td>
	     <td><input name="task.seqNo" type="text" value="" style="width:60px" maxlength="32"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.courseNo"/>:</td>
	     <td><input name="task.course.code" type="text" value="" style="width:60px" maxlength="32"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.courseName"/>:</td>
	     <td><input type="text" name="task.course.name" value="" style="width:100px" maxlength="20"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="entity.courseType"/>:</td>
	     <td>
	          <select name="task.courseType.id" value="" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list sort_byI18nName(courseTypes) as courseType>
		     	<option value=${courseType.id}><@i18nName courseType/></option>
		     	</#list>
		     </select>
	    </tr>
	    <tr>
	     <td class="infoTitle">主考院系:</td>
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
	     <td class="infoTitle"><@bean.message key="attr.enrollTurn"/>:</td>
	     <td><input type="text" name="task.teachClass.enrollTurn" value="" maxlength="7" style="width:60px"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"/><@bean.message key="entity.studentType"/>:</td>
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
	     <td class="infoTitle">结束周<=:</td>
	     <td><input type="text" name="arrangeInfo.endWeek" value="" style="width:60px"></td>
	    </tr>
	    <tr>
	     <td class="infoTitle">星期:</td>
	     <td>
		     <select name="time.week" value="" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list weeks as week>
		     	<option value="${week.id}"<#if week.id?string=RequestParameters['time.week']?default("")> selected</#if>><@i18nName week/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr>
	     <td class="infoTitle"/>小节:</td>
	     <td>
		     <select name="time.startUnit" value="" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list 1..14 as unit>
		     	<option value=${unit}<#if unit?string=RequestParameters['time.startUnit']?default("")> selected</#if>>${unit}</option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr>
	     <td class="infoTitle"/>排考组:</td>
	     <td>
		     <select name="examGroup.id" value="" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list groups as group>
		     	<option value="${group.id}"><@i18nName group/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr align="center">
	     <td colspan="2">
		     <input type="button" onClick="search(document.taskSearchForm,'taskList')" class="buttonStyle" value="<@bean.message key="action.query"/>" style="width:60px"/>
	     </td>
	    </tr>
        </form>
	  </table>
    </div>
    <div style="display: none;" id="view1">
	   <table width="100%">
	    <tr>
	      <td class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B>排考结果查询(模糊)</B>
	      </td>
	    </tr>
	    <tr>
	      <td colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
	      </td>
	   </tr>
	  </table>
	  <table class="searchTable" onkeypress="DWRUtil.onReturn(event, searchArrange)">
	    <form name="arrangedTaskSearchForm" method="post" target="contentFrame">
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.taskNo"/>:</td>
	     <td><input name="task.seqNo" type="text" value="" style="width:60px"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.courseNo"/>:</td>
	     <td><input name="task.course.code" type="text" value="" style="width:60px"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.courseName"/>:</td>
	     <td><input type="text" name="task.course.name" value="" style="width:100px"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="entity.courseType"/>:</td>
	     <td><input type="text" name="task.courseType.name" value="" style="width:100px"></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.enrollTurn"/>:</td>
	     <td><input type="text" name="task.teachClass.enrollTurn" value="" style="width:60px"></td>
	    </tr>
	    <tr>
	     <td class="infoTitle">行政班:</td>
	     <td><input type="text" name="teachClass.adminClass.name" value="" style="width:100px"></td>
	    </tr>
	    <tr>
	     <td class="infoTitle">主考院系:</td>
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
	     <td class="infoTitle">监考院系:</td>
	     <td>
		     <select name="exam.examMonitor.depart.id" value="" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list sort_byI18nName(invigilateDeparts) as invigilateDepart>
		     	<option value=${invigilateDepart.id}><@i18nName invigilateDepart/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="entity.studentType"/>:</td>
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
	     <td class="infoTitle">考试星期:</td>
	     <td>
		     <select name="exam.time.week" value="" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list weeks as week>
		     	<option value="${week.id}"<#if week.id?string=RequestParameters['time.week']?default("")> selected</#if>><@i18nName week/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr>
	     <td class="infoTitle">考试小节:</td>
	     <td>
		     <select name="exam.time.startUnit" value="" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list 1..14 as unit>
		     	<option value=${unit}>${unit}</option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr>
	     <td class="infoTitle">考试地点:</td>
	     <td><input type="text" name="exam.room.name" value="" style="width:60px"></td>
	    </tr>
	    <tr>
	     <td class="infoTitle">排考组:</td>
	     <td>
		     <select name="examGroup.id" value="" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list groups as group>
		     	<option value="${group.id}"><@i18nName group/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr align="center">
	     <td colspan="2">
		     <input type="button" onClick="search(document.arrangedTaskSearchForm,'arrangeList')" class="buttonStyle" value="<@bean.message key="action.query"/>" style="width:60px"/>
	     </td>
	    </tr>
        </form>
	  </table>
    </div>