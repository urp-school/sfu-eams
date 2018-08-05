   <#assign columnHead>
    <tr align="center" class="darkColumn">
      <td width="1%"></td>
      <td width="9%"><@msg.message key="attr.courseNo"/></td>
      <td width="9%"><@bean.message key="attr.taskNo"/></td>
      <td width="9%"><@bean.message key="entity.teacher"/><@msg.message key="attr.personName"/></td>
      <td><@bean.message key="attr.courseName"/></td>
      <td width="12%"><@msg.message key="attr.teachDepart"/></td>
      <td width="5%"><@bean.message key="attr.weekHour"/></td>
      <td width="5%"><@bean.message key="attr.credit"/></td>
      <td width="11%"><@msg.message key="task.firstCourse"/></td>
      <td width="5%"><@bean.message key="attr.GP"/></td>
      <td width="5%"><@msg.message key="attr.teachLangType"/></td>
      <td width="5%"><@msg.message key="attr.startWeek"/></td>
    </tr>
   </#assign>
 <table width="100%" class="listTable">
   ${columnHead}
    <#if table.tasksGroups?exists>
	   <#list table.tasksGroups as group>
	   <tr>
	      <td colspan="12" class="grayStyle">&nbsp;<@i18nName group.type/> <@msg.message key="task.shouldBeElect"/> ${group.credit?default(0)} <@msg.message key="attr.credit"/></td> 
	   </tr>
	 <!-- <#if (group.actualCredit?default(0)!=0)>${columnHead}</#if>-->
	 <#list group.tasks as task>
     <tr align="center" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)" >
	      <td>${task_index+1}</td>
      <#if task.requirement.isGuaPai == true>
	      <td>${task.course.code}</td>
	      <td colspan="3">
	       <a href="teachTaskSearch.do?method=courseInfo&id=${task.course.id}&type=Course" title="<@msg.message key="common.displayDetailCourse"/>"><@i18nName task.course/></a>
	      </td>
	      <td><@i18nName task.arrangeInfo.teachDepart/>
	      <td>${task.arrangeInfo.weekUnits}</td>
	      <td>${task.course.credits}</td>

	      <td><#if userCategory == 3 || userCategory != 3 && switch?exists && switch.isPublished>${task.firstCourseTime?if_exists}</#if></td>
	      <td><#if task.requirement.isGuaPai == true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if></td>
	      <td><@i18nName (task.requirement.teachLangType)?if_exists/></td>
	      <td>${task.arrangeInfo.weekStart}</td>
      <#else>
	      <td>${task.course.code}</td>
	      <td><A href="courseTableForStd.do?method=taskTable&task.id=${task.id}" title="<@bean.message key="info.courseTable.lookFormTaskTip"/>">${task.seqNo?if_exists}</a></td>
	      <td><A href="http://webapp.urp.sfu.edu.cn/edu/teacher/introduction/${task.arrangeInfo.teachers[0].id}" target="_blank"><@getTeacherNames task.arrangeInfo.teachers/></a></td>
	      <td>
	       <a href="http://webapp.urp.sfu.edu.cn/edu/course/site/${task.course.id}" title="<@msg.message key="common.displayDetailCourse"/>" target="_blank"><@i18nName task.course/></a>
	      </td>
	      <td><@i18nName task.arrangeInfo.teachDepart/>
	      <td>${task.arrangeInfo.weekUnits}</td>
	      <td>${task.course.credits}</td>

	      <td><#if userCategory == 3 || userCategory != 3 && switch?exists && switch.isPublished>${task.firstCourseTime?if_exists}</#if></td>
	      <td><#if task.requirement.isGuaPai == true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if></td>
	      <td><@i18nName (task.requirement.teachLangType)?if_exists/></td>
	      <td>${task.arrangeInfo.weekStart}</td>
      </#if>
    </tr>
	    </#list>
	   </#list>
    </#if>
	</table>