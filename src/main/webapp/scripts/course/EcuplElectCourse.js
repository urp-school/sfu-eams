    //视图
    var divs =[];
    divs[0]="courseTableDIV";
    divs[1]="courseListDIV";
    divs[2]="teachClassDIV";
    divs[3]="electRefereClassDIV";
    var viewNum=divs.length;
    //消息
    var errors={};
    errors.notOpen="没有开放选课";
    errors.noSuitableDate="不在选课开放时间";
    errors.needEvaluation="本次选课要求评教完成，你还没有完成。";
    errors.creditOvered="你已经到达学分上限";
    errors.stdCountOvered="该课程到达选课人数上限";
    errors.timeConfilict="该课程与你的课程安排有冲突";
    errors.notCancelable="该课程不允许退课";
    errors.underMinCount="低于人数下限，不允许退课";
    errors.notInScope="你不在该课程允许的选课范围内";
    errors.reStudiedNotAllowed="本轮选课不允许重修";
    errors.HSKNotSatisfied="HSK级别不足";
    errors.languageAbilityNotSatisfied="语言等级不匹配";
    errors.prerequisteCoursesNotMeeted="先修课程没有完成";
    errors.reStudyPassedCourseNotAllowed="这门课程你已经通过,无须重修";
    errors.reElected="重复选课";
    errors.reStudyElected="重修选课";
    var reStudy="3";
    var elective="2";
    var reExam="4";
    var electSuccessMsg="选课成功";
    var cancelSuccessMsg="退课成功";
    var emptyPrompt="该时间你没有课程，可双击查看该时间学校开设的课程。";
    var waiting="请稍后...";
    var operationTip="在我的课程中退课,在课程列表进行选课";
    var myCourseListTable="myCourseListTable";
    //正在处理的教学任务
    var curTask=new TeachTask();
    var electState = new ElectState();
    var processingTask ={};
    var operation="add";
    //课程表页面显示的列
    var displayColums=[];
    displayColums[0]="id";
    displayColums[1]="seqNo";
    displayColums[2]="course.code";
    displayColums[3]="course.name";
    displayColums[4]="courseType.name";
    displayColums[5]="arrangeInfo.teachers";
    displayColums[6]="arrangeInfo.weekUnits";
    displayColums[7]="credit";
    displayColums[8]="requirement.teachLangType";
    displayColums[9]="requirement.isGuaPai";
    var hiddenDisplayColums=[];
    hiddenDisplayColums[0]="electInfo.minStdCount";
    hiddenDisplayColums[1]="electInfo.isCancelable";
    hiddenDisplayColums[2]="teachClass.stdCount";

    // 教学任务
     function TeachTask(){
       this.id="";
       this.seqNo="";
       this.course = {};
       this.course.code="";
       this.course.name="";
       this.credit=null;
       this.courseType = {};
       this.courseType.name = "";
       this.arrangeInfo={};
       this.arrangeInfo.teachers="";
       this.arrangeInfo.weekUnits="";
       this.arrangeInfo.weekStart=1;
       
       this.requirement={};
       this.requirement.isGuaPai="";
       this.requirement.teachLangType="";
       this.electInfo={};
       this.electInfo.maxStdCount=0;
       this.electInfo.minStdCount=0;
       this.electInfo.isCancelable=0;
       this.electInfo.HSKDegree=0;
       this.electInfo.prerequisteCoursesIds="";
       
       this.teachClass={};
       this.teachClass.stdCount=0;
       this.table=null;
       this.scopes=null;
     }
     
    // 显示选课系统状态
    function displayElectState(){
       var div= document.getElementById("electStateDIV");
       if(div.style.display=="block"){
            div.style.display="none";
       }else{
         div.style.display="block"
       }
    }
    function searchTeacher(){
       window.open("teacherSearch.do",'','');
    }
    function searchCourse(){
       window.open("courseSearch.do",'','');
    }
    function searchClassTable(){
       window.open("courseTableForStd.do?method=stdHome",'','');
    }
    function setMessage(msg){ 
       document.getElementById("message").innerHTML="<font color='brown'>"+msg+"</font>";
    }
    function setSuccessMessage(msg){
       document.getElementById("message").innerHTML="<font color='green'>"+msg+"</font>";
    }
     // 辅助函数
     function getProperty(object,attr){
       var attrs =attr.split(".");
       var i=0;
       var obj=object;
       while(i<attrs.length){
         obj = obj[attrs[i]];
         i++;
       }
       return obj;
     }
    // 切换视图(在必要时加载教学任务列表)
    function changeToView(divID){
        var findIndex=0;
    	for(var i=0;i<divs.length;i++){
    		var DIV = document.getElementById(divs[i]);
    		if (null == DIV) {
    			continue;
    		}
    		if(divs[i]==divID){
    		   findIndex=i;
    		   DIV.style.display="block";
    		   if(divID=='courseListDIV'){
      		     	if(courseListFrame.location=="about:blank"){
	      		        document.searchTaskFrom.submit();
      		     	}
      		   } else if (divID == 'teachClassDIV') {
      		       if(taskListOfClassFrame.location=="about:blank"){
      		        document.taskListOfClassForm.submit();
      		       }
      		   }
    		} else{
    		   DIV.style.display = "none";
    		}
    	}
    }
    
    function dealthWithDIV(findIndex) {
    	for (var i = 0; i < divs.length; i ++) {
    		if (i == findIndex) {
		        document.getElementById('viewTD'+i).className="transfer";
			} else {
		        document.getElementById('viewTD'+i).className="padding";
			}
    	}
    }
    
    //查询指定时间的教学任务
    function getTaskListOf(weekId,weekName,unitId,unitName){
        var form =document.searchTaskFrom;
        form['timeUnit.weekId'].value=weekId;
        form['weekName'].value=weekName;
        form['timeUnit.startUnit'].value=unitId;
        form['unitName'].value=unitName;
        form.submit();
    }
    //检查日期
    //nowLength是客户端与服务器时间差
    function checkDate(state){
//         var startDateInfo = state.params.startDate.split("-");
//         var startTimeInfo =state.params.startTime.split(":");
//         var a = new Date(startDateInfo[0],startDateInfo[1]-1,startDateInfo[2],startTimeInfo[0],startTimeInfo[1],0,0);
//         startTime=a.valueOf();
//         var finishDateInfo = state.params.finishDate.split("-");
//         var finishTimeInfo =state.params.finishTime.split(":");
//         b =new Date(finishDateInfo[0],finishDateInfo[1]-1,finishDateInfo[2],finishTimeInfo[0],finishTimeInfo[1],0,0);
//         finishTime=b.valueOf();
//         if (null == nowLength) {
//         	nowLength = 0;
//         }
//         var now = new Date(new Date().valueOf() - nowLength);

         return "";
    }
    // 检查任务是否能被选择
    // 能选择返回"",否则返回错误消息
    function canElect(state,task){
        //检查选课开关
        if(!state.params.isOpenElection){
          return  errors.notOpen;
        }
        // 检查评教
        if(state.params.isCheckEvaluation&&!state.isEvaluated){
          return errors.needEvaluation;
        }
        //检查学分
        //alert(electState.constraint.electedCredit)
        if(electState.constraint.electedCredit+task.credit>state.maxCreditLimited){
          return errors.creditOvered;
        }
        // 检查人数上限
        if(!state.params.isOverMaxAllowed){
           if(task.teachClass.stdCount+1>task.electInfo.maxStdCount){
              return errors.stdCountOvered;
           }
        }
        //检查是否重修选课
        if(null!=state.hisCourses[task.course.id]){
           if(state.params.isRestudyAllowed){
              	return  errors.reStudyElected;
           }
           else {
              return  errors.reStudiedNotAllowed;
           }
        }
        //检查重复选课
        if(state.electedCourseIds[task.course.id]==true){
           return errors.reElected;
        }

	    //检查选课范围
        var isCheckScope=true;
        if(null!=state.hisCourses[task.course.id]){
           isCheckScope=state.params.isCheckScopeForReSturdy;
        }
        if(isCheckScope){
	        var inscope=false;
	        for(var i=0;i<task.scopes.length;i++){
	           var scope = task.scopes[i];
	           if(""===checkScope(state,scope)){
	              inscope=true;
	              break;
	           }
	        }
	        if(!inscope){
	           return errors.notInScope;
	        }
        }
        // 检查HSK
        if(0!=electState.HSKDegree&&0!=task.electInfo.HSKDegree){
            if(electState.HSKDegree<task.electInfo.HSKDegree){
                return errors.HSKNotSatisfied;
            }
        }        
        // 检查语言等级
        if(0!=task.course.languageAbility){
            //alert(task.course.languageAbility);
            if(task.course.languageAbility!=electState.std.languageAbility){
                return errors.languageAbilityNotSatisfied;
            }
        }
        // 检查先修课程
        if(""!=task.electInfo.prerequisteCoursesIds){
           var courseIds = task.electInfo.prerequisteCoursesIds.split(",");
           //alert(task.electInfo.prerequisteCoursesIds);
           for(var i=0;i<courseIds.length;i++){
             if(""!=courseIds[i]){
                if(electState.hisCourses[courseIds[i]]!=true){
                    return errors.prerequisteCoursesNotMeeted;
                }
             }
           }
        }
        return "";
     }

    /**
     * 检查选课范围
     *
     */
     function checkScope(state,scope){
       if(""!=scope.startNo&&""!=scope.endNo){
           //alert("scope.startNo:"+scope.startNo+" scope.endNo:"+scope.endNo)
           if((state.std.stdNo>=scope.startNo)&&(state.std.stdNo<=scope.endNo)) return "";
       }else{
         if(""!=scope.adminClassIds){
             //alert("scope.adminClassIds:"+scope.adminClassIds);
             for(var j=0;j<state.std.adminClassIds.length;j++){
                if(scope.adminClassIds.indexOf(","+state.std.adminClassIds[j]+",")!=-1) return "";
             }
             return errors.notInScope;
          }else{             
             if(scope.departIds==",1,"){
                 if(""!=scope.enrollTurns){
                    if(scope.enrollTurns.indexOf(","+state.std.enrollTurn+",")==-1){
                      return errors.notInScope;
                    }
                 }
                 //alert("scope.stdTypeIds:"+scope.stdTypeIds+"and state.std.stdTypeId "+state.std.stdTypeId);
                 if(""!=scope.stdTypeIds){
                    if(scope.stdTypeIds.indexOf(","+state.std.stdTypeId+",")==-1){
                       return errors.notInScope;
                    }
                 }
                 return "";
             }
             else {
                 //alert(scope.enrollTurns)
                 if(""!=scope.enrollTurns&&scope.enrollTurns.indexOf(","+state.std.enrollTurn+",")==-1){
                    return errors.notInScope;
                 }
                 //alert("scope.stdTypeIds:"+scope.stdTypeIds +"\nstate.std.stdTypeId:" +state.std.stdTypeId);
                 if(""!=scope.stdTypeIds&&scope.stdTypeIds.indexOf(","+state.std.stdTypeId+",")==-1){
                    return errors.notInScope;
                 }
                 //alert(scope.departIds)
                 if(""!=scope.departIds&&scope.departIds.indexOf(","+state.std.departId+",")==-1) {
                    return errors.notInScope;
                 }
                 //alert(scope.specialityIds)
                 if(""!=scope.specialityIds&&scope.specialityIds.indexOf(","+state.std.specialityId+",")==-1){
                    return errors.notInScope;
                 }
                 //alert(scope.aspectIds)
                 if(""!=scope.aspectIds&&scope.aspectIds.indexOf(","+state.std.aspectId+",")==-1){
                    return errors.notInScope;
                 }
                 return "";
             }
          }
       }
     }
     //向课表中添加一个教学任务
     function addTask(){
        changeToView(divs[0]);
        //更新学分
        electState.constraint.electedCredit+=curTask.credit;
        document.getElementById("electedCredit").value=electState.constraint.electedCredit;
        electState.electedCourseIds[curTask.course.id]=true;
        //增加教学任务纪录
        var table = document.getElementById(myCourseListTable);
        var tr = document.createElement('tr');
	    var cellsNum = table.rows[0].cells.length;
	    for(var j = 0 ; j < cellsNum ; j++){
	        var td = document.createElement('td');
	        if(j===0){
		        td.innerHTML="<input type='radio' name='taskId' value='"+curTask.id+"' id='"+curTask.id+"'>"
		                     +"<input type='hidden' name='courseId' value='"+curTask.course.id+"'/>";
		        td.className="select";
		    }
		    else{
		       td.innerHTML=getProperty(curTask,displayColums[j]);
		    }
	        tr.appendChild(td);
	    }
	    var inputDiv = document.createElement('div');
	    for(var j=0;j<hiddenDisplayColums.length;j++){
  	         var input = document.createElement('input');
  	         input.type="hidden";
  	         input.name=hiddenDisplayColums[j];
  	         input.value=getProperty(curTask,hiddenDisplayColums[j]);
  	         inputDiv.appendChild(input);
	    }
	    tr.appendChild(inputDiv);
	    tr.className="grayStyle";
	    tr.align="center";
	    //tr.onmousemver=function (){swapOverTR(this,this.className);}
        //tr.onmouseout=function (){swapOutTR(this)};
        tr.onclick=function (){onRowChange(event);};
	    table.tBodies[table.tBodies.length-1].appendChild(tr);
	 
	    for(var k=0;k<unitsPerWeek;k++){
			var td = document.getElementById("TD"+k);
			if(curTask.table.activities[k].length>0){
			    for(var i=0;i<curTask.table.activities[k].length;i++){
  			      courseTable.activities[k][courseTable.activities[k].length]=curTask.table.activities[k][i];
  			    }
			    tipContents[k]= courseTable.marshal(k,weekStart,1,weeks);
			    td.innerHTML=tipContents[k];
			    td.style.backgroundColor="yellow";
	 	    }
 	    }
	    setSuccessMessage(electSuccessMsg);
     }

     
     //向课表中去除一个教学任务
     function removeTask(){
        if(null==curTask){return;}
        electState.constraint.electedCredit-=curTask.credit;
        document.getElementById("electedCredit").value=electState.constraint.electedCredit;
        electState.electedCourseIds[curTask.course.id]=null;
        //alert(curTask.id)
        var table = document.getElementById(myCourseListTable);
        var selectInput = document.getElementById(curTask.id);
        table.tBodies[table.tBodies.length-1].removeChild(selectInput.parentNode.parentNode);
	    for(var k=0;k<unitsPerWeek;k++){
			var td = document.getElementById("TD"+k);
			if(courseTable.activities[k].length>0){
  			    var activites =new Array();
  			    var find=false;
			    for(var i=0;i<courseTable.activities[k].length;i++){
  			      if(courseTable.activities[k][i].taskId==curTask.id){
   			         courseTable.activities[k][i]=null;
   			         find=true;
   			      }
   			      else{
   			         activites[activites.length]=courseTable.activities[k][i];
   			      }
  			    }
  			    if(find){
	                courseTable.activities[k]=activites;
				    tipContents[k]= courseTable.marshal(k,weekStart,1,weeks);
				    if(""==tipContents[k]){
				    	td.innerHTML="";
					    td.style.backgroundColor="#94aef3";
					    tipContents[k]=emptyPrompt;
				    }
			    }
	 	    }
 	    }
        setSuccessMessage(cancelSuccessMsg);
     }

    // 查询指定时间的教学任务
    function getTaskList(){
        var form = document.searchTaskFrom;
        form.target = "courseListFrame";
        var index = selectedIndex;
        form["task.seqNo"].value = "";
        form["task.course.code"].value = "";
        form["task.course.name"].value = "";
        form["task.courseType.name"].value = "";
        form["task.arrangeInfo.teachDepart.name"].value = "";
        form["teacher.name"].value = "";
        getTaskListOf(Math.floor(index/unitCount)+1,weekListInfo[Math.floor(index/unitCount)],(index%unitCount)+1,unitListInfo[index%unitCount+1]);
        document.getElementById("viewTD1").onclick();
    }

    //退课
    function cancelTask(stdCode){
        operation="remove";
        var taskId = getCheckBoxValue(document.getElementsByName('taskId'));
        if(taskId===""){alert("请选择一门课程");return;}
        
        if(taskId!==""&& confirm("确定要退掉这门课吗？")){
           if(!electState.params.isOpenElection){
              alert(errors.notOpen);
              return  errors.notOpen;
           }
           if(electState.params.isCheckEvaluation&&!electState.isEvaluated){
              alert(errors.needEvaluatio);
              return errors.needEvaluation;
           }
           
           var selectInput = document.getElementById(taskId);
           curTask.id=taskId;
           curTask.credit = new Number(selectInput.parentNode.parentNode.childNodes[7].innerHTML);
           curTask.course.id=selectInput.nextSibling.value;
           
           var inputDiv =selectInput.parentNode.parentNode.childNodes[10];
           if(inputDiv.firstChild.value=="false"){
              setMessage(errors.notCancelable);
              alert(errors.notCancelable);
              return;
           }
           if(!electState.params.isUnderMinAllowed){
              var minCount = new Number(inputDiv.childNodes[0].value);
              var stdCount = new Number(inputDiv.childNodes[2].value);
              //alert(stdCount+"\n"+minCount)
              if(stdCount<=minCount){
                  setMessage(errors.underMinCount);
                  alert(errors.underMinCount);
                  return;
              }
           }
           var url= "quickElectCourse.do?method=cancel&task.id="+taskId+"&stdCode="+stdCode;
           url+="&submitTime="+ (new Date()).getTime();
           openElectResult(url);
        }
    }

    function refreshCourseTable(isSuccess){
      if(isSuccess){
	      if(operation=="add"){
	         addTask();
	      }
	      else if(operation=="remove"){
	         removeTask();
	      }
      }
      else {
         document.getElementById("message").innerHTML="<font color='green'>"+operationTip+"</font>";
      }
    }
    
    //todo
    function canSubmitElection(){
       if(document.getElementById("message").innerHTML!=""
          &&document.getElementById("message").innerHTML=="<font color='green'><blink>"+waiting+"</blink></font>"){
           return false;
       }
       else{
           return true;
       }
    }
    document.getElementById("message").innerHTML="<font color='green'>"+operationTip+"</font>";
    
    function closeElectResult(){
       document.getElementById("electResultDIV").style.display="none";
       electResultFrame.document.getElementById("message_td").innerHTML = "";
       electResultFrame.document.getElementById("timeElapsed").innerHTML = "";
    }
    function openElectResult(url){
       electResultFrame.location=url;
       document.getElementById("electResultDIV").style.display="block";
    }