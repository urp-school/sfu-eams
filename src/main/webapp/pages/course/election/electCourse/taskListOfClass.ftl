<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/course/TaskActivity.js"></script> 
<body>
<#--班级课程的二维表-->
<#assign tableIndex=0/>
  <#assign unitList=calendar.timeSetting.courseUnits>
  <table width="100%" align="center" class="listTable" style="font-size:${fontSize?default(12)}px">
   <#include "/pages/system/calendar/timeSetting/timeSettingBar.ftl"/>
    <tr class="darkColumn">
        <td width="7%"></td>
        <#list unitList as unit>
        <td align="center" width="7%">${unit_index+1}</td>
        </#list>
    </tr>
    <#list setting.weekInfos as week>
    <tr>
        <td class="darkColumn"><@i18nName week/></td>
        <#list 1..unitList?size as unit>
        <td id="TD${week_index*unitList?size+unit_index}_${tableIndex}" style="backGround-Color:#ffffff;font-size:${fontSize?default(12)}px"></td>
        </#list>
    </tr>
    </#list>
</table>
<#----------------------------------------------------------------------->
<#assign tableTitle = {}/>
<#assign tableTitle = tableTitle + {'0':[" ", " "]}/>

<#assign beanMessage><@msg.message key="attr.courseNo"/></#assign>
<#assign tableTitle = tableTitle + {'1':[beanMessage?string, "9%"]}/>

<#assign beanMessage><@msg.message key="attr.taskNo"/></#assign>
<#assign tableTitle = tableTitle + {'2':[beanMessage?string, "9%"]}/>

<#assign tableTitle = tableTitle + {'3':["教师姓名", "9%"]}/>

<#assign beanMessage><@msg.message key="attr.courseName"/></#assign>
<#assign tableTitle = tableTitle + {'4':[beanMessage?string, " "]}/>

<#assign beanMessage><@msg.message key="attr.teachDepart"/></#assign>
<#assign tableTitle = tableTitle + {'5':[beanMessage?string, "12%"]}/>

<#assign beanMessage><@msg.message key="attr.weekHour"/></#assign>
<#assign tableTitle = tableTitle + {'6':[beanMessage?string, "5%"]}/>

<#assign beanMessage><@msg.message key="attr.credit"/></#assign>
<#assign tableTitle = tableTitle + {'7':[beanMessage?string, "5%"]}/>

<#assign beanMessage><@msg.message key="task.firstCourse"/></#assign>
<#assign tableTitle = tableTitle + {'8':[beanMessage?string, "11%"]}/>

<#assign beanMessage><@msg.message key="attr.GP"/></#assign>
<#assign tableTitle = tableTitle + {'9':[beanMessage?string, "5%"]}/>

<#assign beanMessage><@msg.message key="attr.teachLangType"/></#assign>
<#assign tableTitle = tableTitle + {'10':[beanMessage?string, "8%"]}/>

<#assign beanMessage><@msg.message key="attr.startWeek"/></#assign>
<#assign tableTitle = tableTitle + {'11':[beanMessage?string, "5%"]}/>

<#assign tableTitle = tableTitle + {'12':["操作", "8%"]}/>
<#--=================================================================-->

 <@table.table id="courseTableObject" width="100%" class="listTable">
    <@table.thead>
      <#list 0..(tableTitle?size - 1) as i>
        <@table.td width=tableTitle[i?string][1] text=tableTitle[i?string][0]/>
      </#list>
    </@>
    <#list courseTable.tasksGroups?if_exists as group>
	   <tr>
	      <td colspan="${tableTitle?size}" class="grayStyle">&nbsp;<@i18nName group.type/> <@msg.message key="task.shouldBeElect"/> ${group.credit?default(0)} <@msg.message key="attr.credit"/></td> 
	   </tr>
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
          <td><#if !electState.electedCourseIds?seq_contains(task.course.id)><A href="javascript:search('', '${task.course.code}', '${task.course.name}', '${task.courseType.name}', '${task.arrangeInfo.teachDepart.name}', '')">选择</A></#if></td>
      <#else>
	      <td>${task.course.code}</td>
	      <td><A href="courseTableForStd.do?method=taskTable&task.id=${task.id}" title="<@bean.message key="info.courseTable.lookFormTaskTip"/>">${task.seqNo?if_exists}</a></td>
	      <td><@getTeacherNames task.arrangeInfo.teachers/></td>
	      <td>
	       <a href="teachTaskSearch.do?method=courseInfo&id=${task.course.id}&type=Course" title="<@msg.message key="common.displayDetailCourse"/>"><@i18nName task.course/></a>
	      </td>
	      <td><@i18nName task.arrangeInfo.teachDepart/>
	      <td>${task.arrangeInfo.weekUnits}</td>
	      <td>${task.course.credits}</td>

	      <td><#if userCategory == 3 || userCategory != 3 && switch?exists && switch.isPublished>${task.firstCourseTime?if_exists}</#if></td>
	      <td><#if task.requirement.isGuaPai == true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if></td>
	      <td><@i18nName (task.requirement.teachLangType)?if_exists/></td>
	      <td>${task.arrangeInfo.weekStart}</td>
          <td><#if !electState.electedCourseIds?seq_contains(task.course.id)><A href="javascript:search('${task.seqNo?if_exists}', '${task.course.code}', '${task.course.name}', '${task.courseType.name}', '${task.arrangeInfo.teachDepart.name}', '${(task.arrangeInfo.teachers?first.name)?default("")}')">选择</A></#if></td>
      </#if>
    </tr>
	    </#list>
    </#list>
</@>
<script>
    function fillTable(table,weeks,units,tableIndex){
       for(var i=0;i<weeks;i++){
            for(var j=0;j<units-1;j++){
                var index =units*i+j;
                var preTd=document.getElementById("TD"+index+"_"+tableIndex);
                var nextTd=document.getElementById("TD"+(index+1)+"_"+tableIndex);
                while(table.marshalContents[index]!=null&&table.marshalContents[index+1]!=null&&table.marshalContents[index]==table.marshalContents[index+1]){
                    preTd.parentNode.removeChild(nextTd);
                    var spanNumber = new Number(preTd.colSpan);
                    spanNumber++;
                    preTd.colSpan=spanNumber;
                    j++;
                    if(j>=units-1){
                        break;
                    }
                    index=index+1;
                    nextTd=document.getElementById("TD"+(index+1)+"_"+tableIndex);
                }
            }
        }
        
        for(var k=0;k<table.unitCounts;k++){
             var td=document.getElementById("TD"+k+"_"+tableIndex);
             if(td != null&&table.marshalContents[k]!=null){
                td.innerHTML = table.marshalContents[k];
                td.style.backgroundColor="#94aef3";
                td.className="infoTitle";
             }
        }
    }
    function search(seqNo, courseCode, courseName, courseTypeName, departName, teacherName) {
        var form = parent.document.searchTaskFrom;
        form["task.seqNo"].value = getValue(seqNo);
        form["task.course.code"].value = getValue(courseCode);
        //form["task.course.name"].value = getValue(courseName);
        //form["task.courseType.name"].value = getValue(courseTypeName);
        //form["task.arrangeInfo.teachDepart.name"].value = getValue(departName);
        //form["teacher.name"].value = getValue(teacherName);
        form.submit();
        parent.document.getElementById("viewTD1").onclick();
    }
    
    function getValue(tar1) {
        return null == tar1 || "" == tar1 ? "" : tar1;
    }
</script>
<#if userCategory == 3 || userCategory != 3 && switch?exists && switch.isPublished>
    <#assign activityList=courseTable.activities>
</#if>
<#include "/pages/course/arrange/task/courseTable/courseTableContent_script.ftl"/>
<#include "/pages/course/arrange/task/courseTable/courseTableRemark.ftl"/>
</body>
<#include "/templates/foot.ftl"/>