    //模拟ie的parentElement
    var ie = /MSIE/.test(navigator.userAgent);
	var moz = !ie && navigator.product == "Gecko";
	if(moz){
		HTMLElement.prototype.__defineGetter__("parentElement", function () {
			if (this.parentNode == this.ownerDocument) return null;
			return this.parentNode;
		});
	}
	
   var date = new Date();
   date.setFullYear(year,11,31);
   var endAtSat=false;
   if(6==date.getDay()){
     endAtSat=true;
   }
    
    function isValidSelectedUnits() {
        for (var i = 0; i < 7; i++) {
            for (var j = 0, continuityCount = 0; j < unitsPerDay; j++) {
//                alert(i + " , " + j + " >> TD" + (i * unitsPerDay + j) + " : " + $("TD" + (i * unitsPerDay + j)).style.backgroundColor);
                if (i == 2 && j >= 5 && ($("TD" + (i * unitsPerDay + j)).style.backgroundColor == "yellow"
                                      || $("TD" + (i * unitsPerDay + j)).style.backgroundColor == "rgb(148, 174, 243)"
                                      || $("TD" + (i * unitsPerDay + j)).innerHTML == "占用")) {
                    alert(getWeek(i) + "第 6 小节至第 14 小节不排课。");
                    return false;
                } else if (i == 4 && j >= 8 && ($("TD" + (i * unitsPerDay + j)).style.backgroundColor == "yellow"
                                              || $("TD" + (i * unitsPerDay + j)).style.backgroundColor == "rgb(148, 174, 243)"
                                              || $("TD" + (i * unitsPerDay + j)).innerHTML == "占用")) {
                    alert(getWeek(i) + "第 9 小节至第 14 小节不排课。");
                    return false;
                } else {
                    if (j >= 4 && j <= 5 && ($("TD" + (i * unitsPerDay + j)).style.backgroundColor == "yellow"
                                          || $("TD" + (i * unitsPerDay + j)).style.backgroundColor == "rgb(148, 174, 243)"
                                          || $("TD" + (i * unitsPerDay + j)).innerHTML == "占用")) {
                        continuityCount ++;
                        if (continuityCount >= 2) {
                            alert("第 5 、 6 小节不能连排。");
                            return false;
                        }
                    } else if (j >= 9 && j <= 10 && ($("TD" + (i * unitsPerDay + j)).style.backgroundColor == "yellow"
                                                  || $("TD" + (i * unitsPerDay + j)).style.backgroundColor == "rgb(148, 174, 243)"
                                                  || $("TD" + (i * unitsPerDay + j)).innerHTML == "占用")) {
                        continuityCount ++;
                        if (continuityCount >= 2) {
                            alert("第 10 、 11 小节不能连排。");
                            return false;
                        }
                    } else {
                        continuityCount = 0;
                    }
                }
            }
        }
        return true;
    }
    
    function getWeek(weekValue) {
        if (weekValue == 0) {
            return "周一";
        } else if (weekValue == 1) {
            return "周二";
        } else if (weekValue == 2) {
            return "周三";
        } else if (weekValue == 3) {
            return "周四";
        } else if (weekValue == 4) {
            return "周五";
        } else if (weekValue == 5) {
            return "周六";
        } else if (weekValue == 6) {
            return "周日";
        }
    }
    
    function save(){
        if (!isValidSelectedUnits()) {
            return;
        }
        if (!confirm("该教学任务的已有排课结果将会被覆盖，确认保存?")) {
            return;
        }
        if (null != isOutCount && isOutCount && !confirm(null == outCountMessage || "" == outCountMessage ? "实排人数已超出实际教师容量" : outCountMessage)) {
            return;
        }
        var count=0;
        // generate input form
        for(var i=0; i< unitsPerWeek;i++)
           if(table.activities[i].length!=0){
             for(var j=0;j<table.activities[i].length;j++){
                 var oneActivity = table.activities[i][j];
                 
           	     var roomInput = document.createElement('input');
	    	     roomInput.name="activity"+count+".room.id";
	    	     roomInput.value=oneActivity.roomId;
	    	     roomInput.type="hidden";
	    	     var teacherInput = document.createElement('input');
	    	     teacherInput.name="activity"+count+".teacher.id";
	    	     teacherInput.value=oneActivity.teacherId;
	    	     teacherInput.type="hidden";
	    	     var validWeeksInput =  document.createElement('input');
	    	     validWeeksInput.name="activity"+count+".time.validWeeks";
	    	     validWeeksInput.value=oneActivity.vaildWeeks;
	    	     validWeeksInput.type="hidden";
	    	     var yearInput = document.createElement('input');
	    	     yearInput.name="activity"+count+".time.year";
	    	     yearInput.value=year;
	    	     yearInput.type="hidden";
	    	     var weekInput = document.createElement('input');
	    	     weekInput.name="activity"+count+".time.weekId";
	    	     weekInput.value=Math.floor(i/unitsPerDay)+1;
	    	     weekInput.type="hidden";
	    	     var startUnitInput = document.createElement('input');
	    	     startUnitInput.name="activity"+count+".time.startUnit";
	    	     startUnitInput.value=i%unitsPerDay+1;
	    	     startUnitInput.type="hidden";
	    	     var unitsInput = document.createElement('input');
	    	     unitsInput.name="activity"+count+".time.endUnit";
	    	     unitsInput.value=i%unitsPerDay+1;
                 unitsInput.type="hidden";
                 
	    	     document.taskActivityForm.appendChild(roomInput);
	    	     document.taskActivityForm.appendChild(teacherInput);
	    	     document.taskActivityForm.appendChild(yearInput);
	    	     document.taskActivityForm.appendChild(validWeeksInput);
	    	     document.taskActivityForm.appendChild(weekInput);
	    	     document.taskActivityForm.appendChild(startUnitInput);
	    	     document.taskActivityForm.appendChild(unitsInput);
	    	     count++;
           }
        }
        document.taskActivityForm.action="ecuplManualArrange.do?method=saveActivities&activityCount="+count;
   	    document.taskActivityForm.submit();
    }
    //设置教室,将选择的教室填充到左侧的列表中
    function setRoom(roomId,roomName){
   		setResourse(roomId,roomName,"room");
    }
    //设置教师或教室
    function setResourse(id,name,category){
       modifiedWeekAndRoom=true;
       for(var i=taskWeekStart-1;i<weeks+taskWeekStart-1;i++){
          if(document.getElementById(category+"Name"+i).parentElement.className=="grayStyle"){
	          document.getElementById(category+"Name"+i).innerHTML=name;
	          document.getElementById(category+"Id"+i).value=id;
	       }
       }
    }
    // 点击左侧选择框
    function clickWeekBox(){
       modifiedWeekAndRoom=true;
       if(!event.srcElement.checked)
	       event.srcElement.parentElement.parentElement.className="";
	   else{
   	       event.srcElement.parentElement.parentElement.className="grayStyle";
	   }
    }
    function clickWeekToggleBox(){
        modifiedWeekAndRoom=true;
        for(var i=taskWeekStart-1;i<weeks+taskWeekStart-1;i++){
            var weekCheckBox = document.getElementById("weekId"+i)
            weekCheckBox.checked = event.srcElement.checked;
            if(!weekCheckBox.checked)
               weekCheckBox.parentElement.parentElement.className="";
            else weekCheckBox.parentElement.parentElement.className="grayStyle";
        }
    }
    function initFreeRoomForm(configId,stdCount){
       var form =document.weekTeacherRoomForm;
       form['classroom.name'].value="";
       form['classroom.code'].value="";
       if(null!=configId){
         form['classroom.configType.id'].value=configId;
       }else{
         form['classroom.configType.id'].value="";
       }
       if(null!=stdCount){
          form['classroom.capacityOfCourse'].value=stdCount;
       }else{
          form['classroom.capacityOfCourse'].value="";
       }
    }
    
    //列出空余教室
    function listFreeRoom(containPublicRoom,detectCollision,pageNo,pageSize,configId,stdCount){
       closeTeacherDiv();
       var selectedWeeks = getSelectWeeks();
       if(selectedWeeks.indexOf('1')==-1) {alert("请选择一个或多个教学周");return;}
       var form =document.weekTeacherRoomForm;

       form.selectedWeeks.value=selectedWeeks;
       form.action="ecuplManualArrange.do?method=freeRoomList&pageNo="+pageNo+"&pageSize="+pageSize;
       if(null!=containPublicRoom){
	       if(containPublicRoom) {
	       	   form.containPublicRoom.value="1";
	       } else {
	       	   form.containPublicRoom.value="0";
	       }
       }
       if(null!=detectCollision){
	       if(!detectCollision) {
	       	   form.detectCollision.value='0';
	       } else {
	       	   form.detectCollision.value='1';
	       }
       }
       //alert(form['classroom.configType.id'].value)
       form.submit();
    }
    //列出多个教师
    function listTeacher(){
       var selectedWeeks = getSelectWeeks();
       if(selectedWeeks.indexOf('1')==-1) {alert("请选择一个或多个教学周");return;}
       if(teacherListDiv.style.display=="block")
         teacherListDiv.style.display="none";
       else 
          teacherListDiv.style.display="block";
    }
    // 获得已选择的教学周
    function getSelectWeeks(){
       var selectedWeeks="";
       for(var i=taskWeekStart-1;i<weeks+taskWeekStart-1;i++){
          if(document.getElementById("roomName"+i).parentElement.className=="grayStyle")
	          selectedWeeks+="1";
          else selectedWeeks+="0";
       }
       return selectedWeeks;
    }
    

    // 设置教室选择时的行状态
    function changeRoomSelected(event){
        var roomTDElem = getEventTarget(getEvent());
        if(roomTDElem.parentElement.className=="grayStyle")
        	roomTDElem.parentElement.className="";
        else{
            modifiedWeekAndRoom=true;
            roomTDElem.parentElement.className="grayStyle";
            roomTDElem.parentElement.firstChild.firstChild.checked=true;
        }
    }
    /**
      * 设置单双周/连续周
      * 1连续周,2单周,3双周
      */
    function setOccupyWeek(cycle){
        modifiedWeekAndRoom=true;
        if(cycle==1){
        	for(var i=taskWeekStart-1;i<weeks+taskWeekStart-1;i++){
    	   	    document.getElementById("weekId"+i).checked=true;
   	    	    document.getElementById("weekId"+i).parentElement.parentElement.className="grayStyle";
   	    	}
    	   	return;
  	    }
    	// 周数和weekId的奇偶性相反
    	for(var i=taskWeekStart-1;i<weeks+taskWeekStart-1;i++){
    	   if((cycle==2&&i%2==0)||(cycle==3&&i%2==1)){
	    	   document.getElementById("weekId"+i).checked=true;
	    	   document.getElementById("weekId"+i).parentElement.parentElement.className="grayStyle";
	       }
		   else {
   		       document.getElementById("weekId"+i).checked=false;
		   	   document.getElementById("weekId"+i).parentElement.parentElement.className="";
		   }
		   
    	}
    }
    /***********
     *进入设置教学单元的时间和教室界面
     */
    function setTimeAndRoom(){
        if (!isValidSelectedUnits()) {
            return;
        }
        $("roomListFrame").src = "";
        var exampleIndex = null;
        var weekUnits="";
        var hasActivity=false;
        for(var i=0;i<selectIndexes.length;i++){
            if(selectIndexes[i]!=null){
                 if(!hasActivity)exampleIndex = selectIndexes[i];
                 if(table.activities[selectIndexes[i]].length>0){
                 	exampleIndex =selectIndexes[i];
                 	hasActivity =true;
                 }
                 if(weekUnits!="")weekUnits+=";";
                 //alert(selectIndexes[i]);
                 weekUnits+= Math.floor(selectIndexes[i]/unitsPerDay)+ 1 + "," + (Math.floor(selectIndexes[i]%unitsPerDay)+1);
            }
        }
        var weekUnitArray = weekUnits.split(";");
        weekUnitArray.sort();
        weekUnits="";
        for(var i=0;i<weekUnitArray.length;i++){
            if(weekUnits!="")weekUnits+=";"
            weekUnits+=weekUnitArray[i];
        }

        document.weekTeacherRoomForm.selectedWeekUnits.value=weekUnits;
        document.getElementById("selectedWeekUnitsDIV").innerHTML="选择小节："+weekUnits;
        
        if(weekUnits=="") {alert("请选择一个或多个小节,统一进行时间和教室设置");return;}
        else{
            var weekRoomDiv = document.getElementById("weekRoomDiv");
            var timeTableDiv=document.getElementById("timeTableDiv");
            timeTableDiv.style.display="none";
            weekRoomDiv.style.display="block";
            
            // clear all info of week and room
            for(var i=taskWeekStart-1;i<weeks+taskWeekStart-1;i++){
	    	   document.getElementById("weekId"+i).checked=false;
	    	   document.getElementById("roomName"+i).innerHTML="";
	    	   document.getElementById("weekId"+i).parentElement.parentElement.className="";
	    	   if(teachers.length==1){
  	    	      document.getElementById("teacherId"+i).value=teachers[0].id;
	    	      document.getElementById("teacherName"+i).innerHTML=teachers[0].name;
	    	   }
	        }
	        
	        document.getElementById('roomListFrame').location="about:blank";
            var start = calendarWeekStart;
            var from = 1;
            var weekLength = weeks;
            var hasSetted =false;
            //alert(exampleIndex);
            for(var m=0;m<table.activities[exampleIndex].length;m++){
                var oneActivity = table.activities[exampleIndex][m];
               
                var weekLength=oneActivity.vaildWeeks.length;
                // for next year's week
                var vaildWeeksStr ="";
                if(!endAtSat&&oneActivity.vaildWeeks.substring(0,start+from-2).indexOf("1")!=-1){
                     vaildWeeksStr =new String(oneActivity.vaildWeeks+oneActivity.vaildWeeks.substring(1,oneActivity.vaildWeeks.length));
                }
                else{
                     vaildWeeksStr =new String(oneActivity.vaildWeeks+oneActivity.vaildWeeks);
                }
                for(var ii=start+from-2;ii<2*weekLength;ii++){
                    var weekIndex = ii-(start+from-2);
                    if(weekIndex>weekLength-1) break;
                    //alert("ii:"+ii+"char:"+vaildWeeksStr.charAt(ii));
                    if(vaildWeeksStr.charAt(ii)=='1'){
                        //alert("weekIndex:"+weekIndex);
                        hasSetted=true;
                        document.getElementById("weekId"+weekIndex).checked=true;
                        document.getElementById("weekId"+weekIndex).parentElement.parentElement.className="grayStyle";
                     	document.getElementById("roomName"+weekIndex).innerHTML=oneActivity.roomName;
                     	document.getElementById("roomId"+weekIndex).value=oneActivity.roomId;  
                     	document.getElementById("teacherId"+weekIndex).value=oneActivity.teacherId;
                     	document.getElementById("teacherName"+weekIndex).innerHTML=oneActivity.teacherName;
                    }
                }
            }
            // 如果没有任何教学周被设置，则默认选中全部
            if(!hasSetted){
              // clear all info of week and room
	            for(var i=taskWeekStart-1;i<weeks+taskWeekStart-1;i++){
		    	   document.getElementById("weekId"+i).checked=true;
		    	   document.getElementById("weekId"+i).parentElement.parentElement.className="grayStyle";
		        }
            }
        }
        f_frameStyleResize(self);
    }
    // 返回课表页面
    function returnToCourseTable(save){
       // 关闭教师选择页面
       closeTeacherDiv();
       var weekRoomDiv = document.getElementById("weekRoomDiv");
       var timeTableDiv=document.getElementById("timeTableDiv");
       // 如果是存储返回则保存新制定的教学活动
       var errorInfo="";
       if(save && modifiedWeekAndRoom){
          var activityCluster = new ActivityCluster(year,courseId,"",weeks);
          for(var i=taskWeekStart-1;i<weeks+taskWeekStart-1;i++){
              var weekBox = document.getElementById("weekId"+i);
              if(weekBox.checked){
                  var roomId= document.getElementById("roomId"+i).value;
                  var roomName =document.getElementById("roomName"+i).innerHTML;
                  if(roomName=="") errorInfo+="第"+(i+1)+"周的教室没有设置\n";
                  var teacherId = document.getElementById("teacherId"+i).value;
                  var teacherName =document.getElementById("teacherName"+i).innerHTML;
              	  activityCluster.add(teacherId,teacherName,roomId,roomName,i);
              }
          }

          if(errorInfo!=""){if(!confirm(errorInfo+"确定要保存吗?")) return;}

          // 为预先选定的单元格重新生成提示
          var activities =  activityCluster.genActivities(calendarWeekStart);
          for(var i=0;i<selectIndexes.length;i++){
              if(null!=selectIndexes[i]){
                table.activities[selectIndexes[i]]=activities;
               if(activities.length==0){
                  myContents[selectIndexes[i]]="空闲";
                  document.getElementById("TD"+selectIndexes[i]).innerHTML="";
               }
               else {
                  myContents[selectIndexes[i]] = "课程安排：" + table.marshal(selectIndexes[i],calendarWeekStart,1,weeks+taskWeekStart-1);
                  document.getElementById("TD"+selectIndexes[i]).innerHTML="占用";
               }
             }
          }
        }
        
        // 将选定的单元格复原为原来的样式，将历史的选定记录值清空
        for(var i=0;i<selectIndexes.length;i++){
       	 	if(null!=selectIndexes[i]){
        		document.getElementById("TD"+selectIndexes[i]).style.backgroundColor=
       		 	origColor[selectIndexes[i]];
       		    selectIndexes[i]=null;
       		}
        }
       timeTableDiv.style.display="block";
       weekRoomDiv.style.display="none";
    }
    
    var selectIndexes = new Array();
    var origColor= new Array(unitsPerWeek);
    // 当鼠标选中一个排课单元格时
    function selectUnit(event){
        var td = getEventTarget(event);
        var index=td.id.substring(2);
        if (availableTime.charAt(index)=='0') {
            alert("教师该时间不可排");
            return;
        } else {
             // look if has uncompatiable unit
             if(td.style.backgroundColor!="yellow"){
                for(var i=0;i<selectIndexes.length;i++){
                    //alert(selectIndexes[i]+"index:" + index);
             	    if(selectIndexes[i]!=null
             	       &&table.activities[index].length>0
             	       &&table.activities[selectIndexes[i]].length>0
             	       &&!table.isSame(selectIndexes[i],index)){
	             	    alert("选中的排课小节和之前选择的排课小节中的课程安排情况不一致，不能统一安排。");
	             	    return;
             	    }
                }
                /**
                 * find a null location then fill it.
                 */
                var k=0;
             	for(;k<selectIndexes.length;k++){
             	    if(selectIndexes[k]==null) break;
             	}
             	selectIndexes[k]=index;
             	// change color
             	origColor[index]=td.style.backgroundColor;
             	td.style.backgroundColor="yellow";
             }
             else {
                for(var i=0;i<selectIndexes.length;i++)
                    if(selectIndexes[i]==index)selectIndexes[i]=null;
             	td.style.backgroundColor=origColor[index];
             }
        }
    }
    // 鼠标提示信息
    function myToolTip(event){
 	  var tdId = new String (getEventTarget(event).id);
      //var index = new tdId.substring(2));
      var tip ="";
      var tdIndex = new Number(tdId.substring(2));
      var week= Math.floor(tdIndex/unitArray.length);
      var unit= tdIndex%unitArray.length;
      tip+=weekArray[week];
      tip+=unitArray[unit];
      if(myContents[tdId.substring(2)]!=undefined)
      	tip+="<br>"+myContents[tdId.substring(2)];
      if(""!=tipContents[tdId.substring(2)])
        tip +="<br>"+tipContents[tdId.substring(2)]; 
      toolTip(tip,'#000000', '#FFFF00'); 
 	}
 	// 初始化整个课程表
 	function initTable(){
		for(var k=0;k<unitsPerWeek;k++){
			var td = document.getElementById("TD"+k);
			if(table.activities[k].length>0){
			    //alert(table.activities[k][0].vaildWeeks);
		 	    myContents[k] = "课程安排：" + table.marshal(k,calendarWeekStart,1,weeks+taskWeekStart-1);
		 	    td.innerHTML="占用";
		 	}
		 	tipContents[k]="";
		 	if(availableTime.charAt(k)=='0'){
		 	     td.style.backgroundColor="#94aef3";
		 	     tipContents[k]+="教师该时间不可排";
		 	}
		 	
		 	if(teachTable.activities[k].length>0){
			    if(tipContents[k]!="") tipContents[k]+="<br>";
			 	tipContents[k]+="教师时间：" + teachTable.marshal(k,calendarWeekStart,1,weeks+taskWeekStart-1);
			}
	     	if(adminClassTable.activities[k].length>0){
    	 		if(tipContents[k]!="") tipContents[k]+="<br>";
	 		 	tipContents[k]+="班级时间：" + adminClassTable.marshal(k,calendarWeekStart,1,weeks+taskWeekStart-1);
	 		}
	        if(tipContents[k]!=undefined || myContents[k]!=undefined){
			    td.onmouseover= function(event){ myToolTip(event);}
	 		    td.onmouseout= function clearOtherInfo(){toolTip();}
	 		}
	 		
	   	    if (tipContents[k]!="") {
	   	        td.style.backgroundColor="#94aef3";
	   	        if(tipContents[k].indexOf("教师时间") >= 0){
	   	            td.innerHTML = "*";
		 	        td.style.textAlign = "center";
	   	        }
   	        } else {
   	            td.style.backgroundColor="#ffffff";
   	        }
	 	}
 	}
 	initTable();