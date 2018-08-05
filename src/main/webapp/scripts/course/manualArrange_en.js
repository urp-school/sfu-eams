
    function save(){
        if(!confirm("Existed arrangement of this task will be overwrited?continue?"))return;
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
	    	     unitsInput.value=i%unitsPerDay+1;;
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
        document.taskActivityForm.action="manualArrangeCourse.do?method=saveActivities&activityCount="+count;
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
    //列出空余教室
    function listFreeRoom(containPublicRoom){
       closeTeacherDiv();
       var selectedWeeks = getSelectWeeks();
       if(selectedWeeks.indexOf('1')==-1) {alert("select one or more week");return;}
       document.weekTeacherRoomForm.selectedWeeks.value=selectedWeeks;
       document.weekTeacherRoomForm.action="manualArrangeCourse.do?method=listFreeRoom";
       if(containPublicRoom) document.weekTeacherRoomForm.containPublicRoom.value="1";
       else document.weekTeacherRoomForm.containPublicRoom.value="0";
       document.weekTeacherRoomForm.submit();
    }
    //列出多个教师
    function listTeacher(){
       var selectedWeeks = getSelectWeeks();
       if(selectedWeeks.indexOf('1')==-1) {alert("select one or more week!");return;}
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
    function changeRoomSelected(){
        var roomTDElem = event.srcElement;        
        if(roomTDElem.parentElement.className=="grayStyle")
        	roomTDElem.parentElement.className="";
        else{
            modifiedWeekAndRoom=true;
            roomTDElem.parentElement.className="grayStyle";
            roomTDElem.parentElement.firstChild.firstChild.checked=true;
        }
    }
    // 设置单双周/连续周
    function setOccupyWeek(cycle){
        modifiedWeekAndRoom=true;
        if(cycle==1){
        	for(var i=taskWeekStart-1;i<weeks+taskWeekStart-1;i++){
    	   	    document.getElementById("weekId"+i).checked=true;
   	    	    document.getElementById("weekId"+i).parentElement.parentElement.className="grayStyle";
   	    	}
    	   	return;
  	    }
  	    var i=0;
    	if(cycle==2) i=0;
    	else if(cycle==3) i=-1;
    	
    	for(;i<weeks+taskWeekStart-1;i++){
    	   if(i!=-1){
	    	   document.getElementById("weekId"+i).checked=true;
	    	   document.getElementById("weekId"+i).parentElement.parentElement.className="grayStyle";
	       }
    	   i++;
    	   if(i<weeks+taskWeekStart-1){
		   	   document.getElementById("weekId"+i).checked=false;    	  
		   	   document.getElementById("weekId"+i).parentElement.parentElement.className="";
		   }
    	}
    }
    //进入设置教学单元的时间和教室界面
    function setTimeAndRoom(){
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
        document.getElementById("TDselectedWeekUnits").innerHTML="choose units:"+weekUnits;
        
        if(weekUnits=="") {alert("Please choose one or more unit and allocate uniform room and time!");return;}
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

	        document.roomListFrame.window.location="about:blank";
	 
            var start = calendarWeekStart;
            var from = 1;
            var weekLength = weeks;
            for(var m=0;m<table.activities[exampleIndex].length;m++){
               
                var oneActivity = table.activities[exampleIndex][m];
                //alert(oneActivity.vaildWeeks);
                var weekLength=oneActivity.vaildWeeks.length;
                // for next year's week
                var vaildWeeksStr = new String(oneActivity.vaildWeeks+oneActivity.vaildWeeks);
                
                for(var ii=start+from-2;ii<2*weekLength;ii++)
                    if(vaildWeeksStr.charAt(ii%weekLength)=='1'){                    
                        var weekIndex = ii-(start+from-2);
                        if(weekIndex>weekLength-1) break;
                        //alert(weekIndex);
                        document.getElementById("weekId"+weekIndex).checked=true;
                        document.getElementById("weekId"+weekIndex).parentElement.parentElement.className="grayStyle";
                     	document.getElementById("roomName"+weekIndex).innerHTML=oneActivity.roomName;
                     	document.getElementById("roomId"+weekIndex).value=oneActivity.roomId;  
                     	document.getElementById("teacherId"+weekIndex).value=oneActivity.teacherId;
                     	document.getElementById("teacherName"+weekIndex).innerHTML=oneActivity.teacherName;
                    }
            }
        }
    }
    // 返回课表页面
    function returnToCourseTable(save){
       // 关闭教师选择页面
       closeTeacherDiv();
       var weekRoomDiv = document.getElementById("weekRoomDiv");
       var timeTableDiv=document.getElementById("timeTableDiv");
       // 如果是存储返回则保存新制定的教学活动
       var errorInfo="";
       if(save&&modifiedWeekAndRoom){
          var activityCluster = new ActivityCluster(courseId,"",weeks);
          for(var i=taskWeekStart-1;i<weeks+taskWeekStart-1;i++){
              var weekBox = document.getElementById("weekId"+i);
              if(weekBox.checked){
                  var roomId= document.getElementById("roomId"+i).value;
                  var roomName =document.getElementById("roomName"+i).innerHTML;
                  if(roomName=="") errorInfo+=(i+1)+"th week's room was not setted\n";
                  var teacherId = document.getElementById("teacherId"+i).value;
                  var teacherName =document.getElementById("teacherName"+i).innerHTML;
              	  activityCluster.add(teacherId,teacherName,roomId,roomName,i);
              }
          }

          if(errorInfo!=""){alert(errorInfo);return;}
          // 为预先选定的单元格重新生成提示
          var activities =  activityCluster.genActivities(calendarWeekStart);
          for(var i=0;i<selectIndexes.length;i++){
              if(null!=selectIndexes[i]){
                table.activities[selectIndexes[i]]=activities;
               if(activities.length==0){
                  myContents[selectIndexes[i]]="free";
                  document.getElementById("TD"+selectIndexes[i]).innerHTML="";
               }
               else {
                  myContents[selectIndexes[i]] = "course:" + table.marshal(selectIndexes[i],calendarWeekStart,1,weeks+taskWeekStart-1);
                  document.getElementById("TD"+selectIndexes[i]).innerHTML="occupy";
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
    function selectUnit(){
        var td = event.srcElement;
        var index=td.id.substring(2);
        if(availableTime.charAt(index)=='0') {alert("The unit isn't in teacher's available time!");return;}
        else {
             // look if has uncompatiable unit
             if(td.style.backgroundColor!="yellow"){
                for(var i=0;i<selectIndexes.length;i++){
                    //alert(selectIndexes[i]+"index:" + index);
             	    if(selectIndexes[i]!=null
             	       &&table.activities[index].length>0
             	       &&table.activities[selectIndexes[i]].length>0
             	       &&!table.isSame(selectIndexes[i],index)){
	             	    alert("The unit which you choose doesn't have same or consistent arrangement with choosed units.");
	             	    return;
             	    }
                }                
                /**
                 * find a null location then fill it.
                 */
                var k=0;
             	for(;k<selectIndexes.length;k++)
             	    if(selectIndexes[k]==null) break;
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
    function myToolTip(){
 	  var tdId = new String (event.srcElement.id);
      //var index = new tdId.substring(2));
      var tip ="";
      if(myContents[tdId.substring(2)]!=undefined)
      	tip+=myContents[tdId.substring(2)]+"<br>";
      tip +=tipContents[tdId.substring(2)];
      if(tip=="")
         tip += "free";       
      toolTip(tip,'#000000', '#FFFF00'); 
 	}
 	// 初始化整个课程表
 	function initTable(){
		for(var k=0;k<unitsPerWeek;k++){
			var td = document.getElementById("TD"+k);
			if(table.activities[k].length>0){
			    //alert(table.activities[k][0].vaildWeeks);
		 	    myContents[k] = "course:" + table.marshal(k,calendarWeekStart,1,weeks+taskWeekStart-1);
		 	    td.innerHTML="occupy";
		 	}
		 	tipContents[k]="";
		 	if(availableTime.charAt(k)=='0'){
		 	     td.style.backgroundColor="#94aef3";
		 	     tipContents[k]+="The unit isn't in teacher's available time"+"<br>";
		 	}
		 	if(teachTable.activities[k].length>0)
			 	tipContents[k]+="teacher：" + teachTable.marshal(k,calendarWeekStart,1,weeks+taskWeekStart-1 )+"<br>";
	     	if(adminClassTable.activities[k].length>0)
	 		 	tipContents[k]+="class：" + adminClassTable.marshal(k,calendarWeekStart,1,weeks+taskWeekStart-1);
	        if(tipContents[k]!=undefined || myContents[k]!=undefined){
			    td.onmouseover= function(){ myToolTip();}
	 		    td.onmouseout= function clearOtherInfo(){ toolTip();}
	 		}
	 		
	   	    if(tipContents[k]!="") td.style.backgroundColor="#94aef3";
	   	    else td.style.backgroundColor="#ffffff";
	 	}
 	}
 	initTable();