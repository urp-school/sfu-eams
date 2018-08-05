	   <table width="100%">
	    <tr>
	      <td class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B>应考学生查询(模糊)</B>
	      </td>
	    </tr>
	    <tr>
	      <td colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>
	  </table>
	  <table class="searchTable" onkeypress="DWRUtil.onReturn(event, search)">
        <tr> 
	     <td class="infoTitle"><@msg.message key="attr.stdNo"/>:</td>
	     <td><input name="take.std.code" type="text" value="" style="width:100px" maxlength="32"/></td>
	    </tr>
        <tr>
	     <td class="infoTitle"><@msg.message key="attr.personName"/>:</td>
	     <td><input name="take.std.name" type="text" value="" style="width:100px" maxlength="20"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@msg.message key="entity.department"/>:</td>
	     <td>
		     <select name="take.std.department.id" value="" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list departmentList as teachDepart>
		     	<option value=${teachDepart.id}><@i18nName teachDepart/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr>
	     <td class="infoTitle"/><@bean.message key="attr.enrollTurn"/>:</td>
	     <td><input type="text" name="take.std.enrollYear" maxlength="7" value="" style="width:60px"></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"/><@bean.message key="entity.studentType"/>:</td>
	     <td>
		     <select name="take.std.type.id" value="" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list stdTypeList as stdType>
		     	<option value=${stdType.id}><@i18nName stdType/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr>
	     <td class="infoTitle" ><@bean.message key="attr.taskNo"/>:</td>
	     <td><input name="take.task.seqNo" type="text" value="" style="width:60px"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"/><@bean.message key="attr.courseNo"/>:</td>
	     <td><input name="take.task.course.code" type="text" value="" style="width:60px"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"/><@bean.message key="attr.courseName"/>:</td>
	     <td><input type="text" name="take.task.course.name" value="" style="width:100px"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"/><@bean.message key="entity.courseType"/>:</td>
	     <td>
	        <select name="take.task.courseType.id" value="" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list sort_byI18nName(courseTypes) as courseType>
		     	<option value=${courseType.id}><@i18nName courseType/></option>
		     	</#list>
		     </select>
	    </tr>
	    <tr>
	     <td class="infoTitle"/>考试地点:</td>
	     <td><input type="text" name="take.activity.room.name" value="" maxlength="20" style="width:60px"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"/>考试情况:</td>
	     <td>
		     <select name="take.examStatus.id" value="" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list examStatuses as examStatus>
		     	<option value=${examStatus.id}><@i18nName examStatus/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr align="center">
	     <td colspan="2">
		     <input type="button" onClick="search()" class="buttonStyle" value="<@bean.message key="action.query"/>" style="width:60px"/>
	     </td>
	    </tr>
      </table>