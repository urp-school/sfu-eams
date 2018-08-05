<#include "/templates/simpleHead.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/course/TaskActivity.js"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/prompt.js"></script> 
<style  type="text/css">
<!--
.trans_msg
    {
    filter:alpha(opacity=100,enabled=1) revealTrans(duration=.2,transition=1) blendtrans(duration=.2);
    }
--> 
</style> 
<body  LEFTMARGIN="0" TOPMARGIN="0">
 <div id="toolTipLayer" style="position:absolute; visibility: hidden"></div>
 <script>initToolTips()</script>
 <div id="processDIV" style="display:block"><@msg.message key="common.pageLoading"/></div>
 <div id="contentDIV" style="display:none">
 <table id="taskListBar"></table>
  <script>
    var bar = new ToolBar("taskListBar"," <#if RequestParameters["isRestudy"]?default('0')=='1'>重修课程列表<#else>一般课程列表</#if>",null,true,false);
    <#if RequestParameters["weekName"]?exists&&RequestParameters["weekName"]!="">
    <#assign timeUnit><div id="timeUnitTD">${RequestParameters["weekName"]},${RequestParameters["unitName"]}<@msg.message key="course.beenCourse"/></div></#assign>
    bar.setMessage('${timeUnit}');
    bar.addItem("<@msg.message key="course.ignoreTime"/>",'evictTime()','action.gif');
    </#if>
    <#if (electState.params.isRestudyAllowed)>
    //var couseMenu=bar.addMenu("显示课程..");
    bar.addItem("一般课程",'query(null,null,0)','list.gif');
    bar.addItem("重修课程",'query(null,null,1)','list.gif');
    <#else>
    bar.addItem("刷新",'query(null,null,0)','refresh.gif');
    </#if>
    bar.addItem("<@msg.message key="action.sureAndChoice"/>",'elect()','new.gif','<@msg.message key="course.manuallyAddTask"/>');
  </script>
    <table width="100%"class="listTable">
      <form name="taskListForm" action="" method="post" onsubmit="return false;">
      <input type="hidden" name="timeUnit.weekId" value="${RequestParameters['timeUnit.weekId']?if_exists}">
      <input type="hidden" name="timeUnit.startUnit" value="${RequestParameters['timeUnit.startUnit']?if_exists}">
      <input type="hidden" name="weekName" value="${RequestParameters['weekName']?if_exists}">
      <input type="hidden" name="unitName" value="${RequestParameters['unitName']?if_exists}">
      <input type="hidden" name="isRestudy" value="${RequestParameters['isRestudy']?if_exists}">
     <tr bgcolor="#ffffff" onKeyDown="javascript:enterQuery()">
      <td align="center" >
        <img src="${static_base}/images/action/search.gif"  align="top" onClick="query()" alt="<@bean.message key="info.filterInResult"/>"/>
      </td>
      <td ><input style="width:100%" type="text" name="task.seqNo" maxlength="32" value="${RequestParameters['task.seqNo']?if_exists}"/></td>
      <td ><input style="width:100%" type="text" name="task.course.code" maxlength="32" value="${RequestParameters['task.course.code']?if_exists}"/></td>      
      <td ><input style="width:100%" type="text" name="task.course.name" maxlength="20" value="${RequestParameters['task.course.name']?if_exists}"/></td>
      <td ><input style="width:100%" type="text" name="task.courseType.name" maxlength="20" value="${RequestParameters['task.courseType.name']?if_exists}"/></td>
      <td ><input style="width:100%" type="text" name="task.arrangeInfo.teachDepart.name" maxlength="20" value="${RequestParameters['task.arrangeInfo.teachDepart.name']?if_exists}"/></td>
      <td ><input style="width:100%" type="text" name="teacher.name" maxlength="20" value="${RequestParameters['teacher.name']?if_exists}"/></td>
      <td ><input style="width:100%" type="text" name="task.course.credits" maxlength="2" value="${RequestParameters['task.course.credits']?if_exists}"/></td>
      <td ><input style="width:100%" type="text" name="task.arrangeInfo.weekUnits" maxlength="3" value="${RequestParameters['task.arrangeInfo.weekUnits']?if_exists}"/></td>
      <td ></td>      
       <td> 
		 <#macro yesOrNoOptions(selected)>
		 	<option value="0" <#if "0"==selected> selected </#if> ><@bean.message key="common.no"/></option> 
		    <option value="1" <#if "1"==selected> selected </#if>><@bean.message key="common.yes"/></option> 
		    <option value="" <#if ""==selected> selected </#if>><@bean.message key="common.all"/></option> 
		 </#macro>
        <select  name="task.requirement.isGuaPai" style="width:40px">  
          <@yesOrNoOptions RequestParameters['task.requirement.isGuaPai']?if_exists/>          
        </select>
      </td>    
    </tr>
    </form>
    <tr align="center" class="darkColumn">
      <td align="center" width="3%"></td>
      <td width="8%"><@bean.message key="attr.taskNo"/></td>
      <td width="8%"><@bean.message key="attr.courseNo"/></td>
      <td width="22%"><@bean.message key="attr.courseName"/></td>
      <td width="14%"><@bean.message key="entity.courseType"/></td>
      <td width="15%"><@bean.message key="attr.teachDepart"/></td>
      <td width="10%"><@bean.message key="entity.teacher"/></td>
      <td width="5%"><@msg.message key="attr.credit"/></td>
      <td width="6%"><@bean.message key="attr.weekHour"/></td>
      <td width="8%"><@msg.message key="course.chooseOrMaximum"/></td>
      <td width="5%"><@msg.message key="attr.GP"/></td>
    </tr>
    <#list taskList as task>
   	  <#if task_index%2==1 ><#assign class="grayStyle" ></#if>
	  <#if task_index%2==0 ><#assign class="brightStyle" ></#if>
     <tr class="${class}" align="center" 
     onmouseover="swapOverTR(this,this.className);displayRemark('${task.id},${task.course.id}');" 
     onmouseout="swapOutTR(this);eraseRemark('${task.id},${task.course.id}');" 
     onclick="onRowChange(event)">
      <td width="2%" class="select"><#t/>
        <input type="hidden" name="task.electInfo.minStdCount" value="${task.electInfo.minStdCount}"><#t/>
        <input type="hidden" name="task.electInfo.isCancelable" value="${task.electInfo.isCancelable?string}"><#t/>
        <input type="hidden" name="task.requirement.teachLangType" value="<@i18nName task.requirement.teachLangType/>"><#t/>
        <input type="hidden" name="task.course.languageAbility" value="${task.course.languageAbility?if_exists.level?default(0)}"><#t/>
        <input type="radio" name="taskCourseId" value="${task.id},${task.course.id}" id="${task.id},${task.course.id}"><#t/>
      </td><#t/>
      <td><a href="courseTableForStd.do?method=taskTable&task.id=${task.id}" target="_blank" title="<@msg.message key="common.displayTeachTaskArrange"/>" >${task.seqNo?if_exists}</a></td>
      <td>${task.course.code}</td>
      <td><a href="http://webapp.urp.sfu.edu.cn/edu/course/site/${task.course.id}" target="_blank" title="<@msg.message key="common.displayDetailCourse"/>"><@i18nName task.course/></a></td>
      <td><@i18nName task.courseType/></td>
      <td>${task.arrangeInfo.teachDepart.name}</td> 
      <td><@getTeacherNames task.arrangeInfo.teachers/></td>      
      <td>${task.course.credits}</td>
      <td>${task.arrangeInfo.weekUnits}</td>
      <td>${task.teachClass.stdCount}/${task.electInfo.maxStdCount}</td>     
      <td><#if task.requirement.isGuaPai == true><@bean.message key="common.yes"/> <#else> <@bean.message key="common.no"/> </#if></td>
    </tr>
	</#list>
	<#include "/templates/newPageBar.ftl"/>
	</table>
	</div>
	<script>
 	 var unitCount = ${(electState.params.calendar.timeSetting.courseUnits)?size};
	 var remarkContents = new Object();
	 var HSKDegrees=new Object();
	 var preCourses= new Object();
     <#list taskList as task>
     remarkContents['${task.id},${task.course.id}']={'remark':'${task.remark?default("")?js_string}'};
     HSKDegrees['${task.id}']=${task.electInfo.HSKDegree?if_exists.degree?default(0)};
     preCourses['${task.id}']='<#list task.electInfo.prerequisteCourses as course>${course.id},</#list>'
	 </#list>
	 
    document.getElementById('processDIV').style.display="none";
    document.getElementById('contentDIV').style.display="block";
    adaptFrameSize();
	var tables = new Object();
	var scopes = new Object();
	var year =${electState.params.calendar.startYear};
	<#list taskList as task>
      tables['${task.id}']=new CourseTable(year,7*unitCount);
      <#list task.arrangeInfo.activities as activity>
       var activity = new TaskActivity("${activity.teacher?if_exists.id?if_exists}","${activity.teacher?if_exists.name?if_exists}","${task.course.id}","${task.course.name}","${activity.room?if_exists.id?if_exists}","${activity.room?if_exists.name?if_exists}","${activity.time.validWeeks}",'${task.id}');
       <#list 1..activity.time.unitCount as unit>
       index =${(activity.time.weekId-1)}*unitCount+${activity.time.startUnit-1+unit_index};
       tables['${task.id}'].activities[index][ tables['${task.id}'].activities[index].length]=activity;
       </#list>
   	  </#list>
      
      scopes['${task.id}']= new Array();
   	  <#list task.electInfo.electScopes as scope>
         scopes['${task.id}'][${scope_index}]=new ElectScope('${scope.enrollTurns?if_exists}','${scope.stdTypeIds?if_exists}','${scope.departIds?if_exists}','${scope.specialityIds?if_exists}','${scope.aspectIds?if_exists}','${scope.adminClassIds?if_exists}','${scope.startNo?if_exists}','${scope.endNo?if_exists}');
   	  </#list>
   	</#list>
    function enterQuery() {
            if (getEvent().keyCode == 13)                
                     query();
    }
    function ElectScope(enrollTurns,stdTypeIds,departIds,specialityIds,aspectIds,adminClassIds,startNo,endNo){
      this.enrollTurns =enrollTurns;
      this.stdTypeIds=stdTypeIds;
      this.departIds=departIds;
      this.specialityIds=specialityIds;
      this.aspectIds=aspectIds;
      this.adminClassIds=adminClassIds;
      this.startNo=startNo;
      this.endNo=endNo;
    }
    
    function pageGoWithSize(pageNo,pageSize){
        query(pageNo,pageSize);
    }
    function query(pageNo,pageSize,isRestudy){
        var form = document.taskListForm;
        form.action="quickElectCourse.do?method=taskList";
        if(isRestudy!=null){
           form['isRestudy'].value=isRestudy;
        }
        if(pageNo!=null)
            form.action+="&pageNo="+pageNo;
        if(pageSize!=null)
            form.action+="&pageSize="+pageSize;
        form.submit();
    }
    
    function evictTime(){
        document.getElementById("timeUnitTD").innerHTML="";
        document.taskListForm['timeUnit.weekId'].value="";
        document.taskListForm['timeUnit.startUnit'].value="";
        document.taskListForm['weekName'].value="";
        document.taskListForm['unitName'].value="";
        query(null,null,0);
    }
    
    function elect(){
        var taskCourseId = getCheckBoxValue(document.getElementsByName('taskCourseId'));
        if(taskCourseId==""){alert("请选择一门教学任务");return;}
        parent.operation="add";
        // 防止反复提交
        if(!parent.canSubmitElection()) return;
        var selectTR = document.getElementById(taskCourseId).parentNode.parentNode;
        var ids = taskCourseId.split(",");
        parent.curTask.id=ids[0];
        parent.curTask.course.id=ids[1];
        parent.curTask.seqNo=selectTR.cells[1].innerHTML;
        parent.curTask.course.code=selectTR.cells[2].innerHTML;
        parent.curTask.course.name=selectTR.cells[3].innerHTML;
        parent.curTask.courseType.name=selectTR.cells[4].innerHTML;
        parent.curTask.arrangeInfo.teachers=selectTR.cells[6].innerHTML;
        parent.curTask.credit=new Number(selectTR.cells[7].innerHTML);
        parent.curTask.arrangeInfo.weekUnits=selectTR.cells[8].innerHTML;
        parent.curTask.electInfo.maxStdCount=new Number(selectTR.cells[9].innerHTML.split("/")[1]);
        parent.curTask.teachClass.stdCount=new Number(selectTR.cells[9].innerHTML.split("/")[0]);
        parent.curTask.requirement.isGuaPai=selectTR.cells[10].innerHTML;

        var inputDiv =selectTR.cells[0];
        parent.curTask.electInfo.minStdCount=inputDiv.firstChild.value;
        parent.curTask.electInfo.isCancelable=inputDiv.childNodes[1].value;
        parent.curTask.electInfo.prerequisteCoursesIds=preCourses[parent.curTask.id];
        parent.curTask.requirement.teachLangType=inputDiv.childNodes[2].value;
        parent.curTask.course.languageAbility=inputDiv.childNodes[3].value;
        parent.curTask.table=tables[parent.curTask.id];
        parent.curTask.scopes=scopes[parent.curTask.id];
        var url="quickElectCourse.do?stdCode=${electState.std.stdNo}&method=elect&task.id=" + parent.curTask.id+"&courseTakeType.id=";
        var courseTakeId=parent.elective;
        var errorInfo = parent.canElect(parent.electState,parent.curTask);
        if(""!=errorInfo){
           if(parent.errors.reStudyElected==errorInfo){
              if(!confirm("该课程你已经选过，是否选择重修?"))
                 return;
              else{
              	 courseTakeId=parent.reStudy;
              }
           }else{
              parent.setMessage(errorInfo);
              alert(errorInfo);
              return;
           }
        }
        if(parent.courseTable.isTimeConflictWith(tables[parent.curTask.id])){
           parent.setMessage(parent.errors.timeConfilict);
           alert(parent.errors.timeConfilict);
           //只有重修的学生才能选择免修不免试
           if(parent.reStudy==courseTakeId){
	          if(!confirm("该课程与你的课程安排有冲突，是否选择免修不免试选择该课?"))
	              return;
	           else{
	             courseTakeId=parent.reExam;
	           }
           }else{
             return;
           }
        }else{	
           var courseName =  parent.curTask.course.name.substring( parent.curTask.course.name.indexOf(">")+1);          
           courseName=  courseName.substring(0,courseName.indexOf("<"));
           if(!confirm("确定选择("+ courseName+")吗?")) return;
        }
        var submitTime=new Date();
        url+=courseTakeId;
        url+="&submitTime="+submitTime.getTime();
        parent.setMessage(parent.waiting);
        parent.openElectResult(url);
    }
    function displayRemark(taskId){
      if(""!=remarkContents[taskId].remark){
         toolTip(remarkContents[taskId].remark,'#000000', '#FFFF00',250);
      }
    }
    function eraseRemark(taskId){
     if(""!=remarkContents[taskId].remark){
        toolTip();
     }
    }
    function refreshCourseTable(isSuccess){
      parent.refreshCourseTable(isSuccess);
    }
    function italicItem(id,otherId){
       var td = document.getElementById(id);
       td.style.fontStyle="italic";
       var otherTd = document.getElementById(otherId);
       otherTd.style.fontStyle="";
    }
    </script>
</body> 
<#include "/templates/foot.ftl"/> 