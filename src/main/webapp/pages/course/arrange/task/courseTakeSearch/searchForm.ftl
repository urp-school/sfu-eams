	   <table width="100%">
	    <tr>
	      <td class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B>详细查询(模糊输入)</B>
	      </td>
	    </tr>
	    <tr>
	      <td colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
	      </td>
	   </tr>
	  </table>
	  <table class="searchTable" onkeypress="DWRUtil.onReturn(event, search)">
	    <form name="courseTakeForm" method="post">
	    <tr>
	     <td class="infoTitle" ><@msg.message key="attr.stdNo"/>:</td>
	     <td><input name="courseTake.student.code" maxlength="32" type="text" value="${RequestParameters["courseTake.student.code"]?if_exists}" style="width:100px"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle" ><@msg.message key="attr.personName"/>:</td>
	     <td><input name="courseTake.student.name" type="text" value="${RequestParameters["courseTake.student.name"]?if_exists}" style="width:100px" maxlength="20"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle" >年份:</td>
	     <td><input name="courseTake.student.enrollYear" type="text" value="${RequestParameters["courseTake.student.enrollYear"]?if_exists}" style="width:100px" maxlength="7"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@msg.message key="entity.department"/>:</td>
	     <td>
		     <select name="courseTake.student.department.id" style="width:100px" value="${RequestParameters["courseTake.student.department.id"]?if_exists}">
		        <option value=""><@msg.message key="common.all"/></option>
		     	<#list sort_byI18nName(departmentList) as depart>
                <option value=${depart.id}><@i18nName depart/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr>
	     <td class="infoTitle"/><@bean.message key="entity.studentType"/>:</td>
	     <td>
		     <select name="courseTake.student.type.id" style="width:100px" value="${RequestParameters["courseTake.student.type.id"]?if_exists}">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list stdTypeList as studentType>
		     	<option value="${studentType.id}" title="<@i18nName studentType/>"><@i18nName studentType/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="entity.courseType"/>:</td>
	     <td><input type="text" name="courseTake.task.courseType.name" value="${RequestParameters["courseTake.task.courseType.name"]?if_exists}" style="width:100px" maxlength="20"/></td>
	    </tr>
	    <tr> 
	     <td class="infoTitle"><@bean.message key="attr.taskNo"/>:</td>
	     <td><input name="courseTake.task.seqNo" type="text" value="${RequestParameters["courseTake.task.seqNo"]?if_exists}" style="width:100px" maxlength="32"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.courseNo"/>:</td>
	     <td><input name="courseTake.task.course.code" type="text" value="${RequestParameters["courseTake.task.course.code"]?if_exists}" style="width:100px" maxlength="32"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.courseName"/>:</td>
	     <td><input type="text" name="courseTake.task.course.name" value="${RequestParameters["courseTake.task.course.name"]?if_exists}" style="width:100px" maxlength="20"/></td>
	    </tr>
        <tr>
            <td class="infoTitle">行政班：</td>
            <td><input type="text" name="adminClassName" value="${RequestParameters["adminClassName"]?if_exists}" maxlength="50" style="width:100px"/></td>
        </tr>
	    <tr>
	     <td class="infoTitle"/>修读类别:</td>
	     <td><select name="courseTake.courseTakeType.id" style="width:100px" value="${RequestParameters["courseTake.task.courseTakeType.id"]?if_exists}">
	         <option value="">全部</option>
	         <#list courseTakeTypes as courseTakeType><option value="${courseTakeType.id}"><@i18nName courseTakeType/></option></#list>
	         </select>
	     </td>
	    </tr>
	    <tr>
	     <td class="infoTitle"/>是否评教:</td>
	     <td><select name="courseTake.isCourseEvaluated" style="width:100px" value="${RequestParameters["courseTake.isCourseEvaluated"]?if_exists}">
	         <option value="">全部</option>
	         <option value="1">完成</option>
	         <option value="0">未完成</option>
	         </select>
	     </td>
	    </tr>
	    <tr>
	     <td class="infoTitle"/>问卷设置:</td>
	     <td>
	         <select name="courseTake.task.questionnaire.id" style="width:100px;">
				<option value="">....</option>
				<#list questionnaireList?if_exists as questionnaire>
				   <option value="${questionnaire.id}">${questionnaire.description?if_exists}&nbsp;&nbsp;${questionnaire.depart.name?if_exists}</option>
				</#list>
			</select>
	     </td>
	    </tr>
	    <tr align="center">
	     <td colspan="2">
		     <button onClick="search()"><@bean.message key="action.query"/></button>
	     </td>
	    </tr>
	  </table>