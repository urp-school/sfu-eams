<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<BODY>
	<table id="taskBar"></table>
	<#macro getTimeStr(time)>${time?string?left_pad(4,"0")[0..1]}:${time?string?left_pad(4,"0")[2..3]}</#macro>
       <table class="formTable" width="100%">
            <form name="examArrangeForm" action="" method="post" onsubmit="return false;" >
  			<tr>
  				<td class="title">排考类型</td>
      			<td><@i18nName params.examType/><input type="hidden" name="examParams.examType.id" value="${params.examType.id}"></td>
    			<td align="bottom" class="title">
       				<select id="stdType" name="calendar.studentType.id" style="width:100px;display:none">
        				<option value="${calendar.studentType.id}"></option>
       				</select>
  					安排到学年度：
   				</td>
   				<td>
    				<select id="year" name="calendar.year"  style="width:100px;">
	        			<option value="${calendar.year}"></option>
	      			</select>
				<@bean.message key="attr.term"/>:
 					<select id="term" name="calendar.term" style="width:80px;">
    					<option value="${calendar.term}"></option>
  					</select>
				</td>
				<td class="title"><font color="red">*</font>起始周模式:</td>
    			<td><select name="examParams.startWeekMode" style="width:100px"><option value="2">共同起始周</option><option value="1">从排课结束周</option></select></td>
  			</tr>
  			<tr>
    			<td class="title"><font color="red">*</font>周状态:</td>
    			<td><select name="examParams.weekMode" style="width:100px" onchange="onChangeWeek(form['weeks'].value);"><option value="1">每周相同</option><option value="2">每周不同</option></select></td>
    			<td id="f_fromWeek" class="title"><font color="red">*</font>起始周：</td>
    			<td><input name="examParams.fromWeek" <#if delayAgain >value="1"<#else>value=""</#if> style="width:40px" maxlength="8"/>周</td>
    			<td id="f_lastWeek" class="title"><font color="red">*</font>周数：</td>
    			<td><select name="weeks"style="width:100px" onchange="onChangeWeek(this.value);"><#list 1..10 as i><option value="${i}">${i}</option></#list></select></td>
  			</tr>
     	</table>

<table id="timeTable" width="100%" style="border-collapse: collapse;">
   <tr>
    <td id="timeTable_td_0">
    <table width="100%" class="listTable" id="timeTable_0">
       	<tr align="center" class="darkColumn">
       		<td width="10%"></td>
       		<#list examTurns  as examTurn>
       			<td><input type="checkbox" onclick="selectColumnTurn(${examTurn.id},event,'timeTable_0')"/><@i18nName examTurn/>(<@getTimeStr examTurn.beginTime/>-<@getTimeStr examTurn.endTime/>)</td>
       		</#list>
       	</tr>
    	<#list weeks as week>
       		<tr align="center">
       			<td class="darkColumn">${week.name}<input type="checkbox" name="${week.id}" onclick="selectRowTurn(${week.id},event,'timeTable_0')"/></td>
   				<#list examTurns  as examTurn>
   					<td title="点击选中或者取消" onClick="javascript:changeTurn(${week.id},'${examTurn.id}','timeTable_0')" id="timeTable_0_td${week.id}_${examTurn.id}" style="cursor:hand"></td>
       			</#list>
       		</tr>
    	</#list>
	</table>
	</td>
	</tr>
 </table>
    <table width="100%" border="0">
	   	<tr>
        	<td class="brightStyle" valign="top" width="40%">
         		<table class="listTable">
          			<tr>
           				<td>考试教室(名称、容纳人数)<br>   
            				<select name="Rooms" MULTIPLE size="20" style="width:180px" onDblClick="JavaScript:moveSelectedOption(this.form['Rooms'], this.form['SelectedRoom'])" >
	             				<#list rooms as room>
	              					<option value="${room.id}"><@i18nName room/>(${room.capacityOfExam}人)</option>
	             				</#list>
            				</select>
           				</td>
           				<td align="center" valign="middle" valign="top">
           					<br><br>
            				<input OnClick="JavaScript:moveSelectedOption(this.form['Rooms'], this.form['SelectedRoom'])" type="button" value="&gt;"/> 
            				<br><br>
            				<input OnClick="JavaScript:moveSelectedOption(this.form['SelectedRoom'], this.form['Rooms'])" type="button" value="&lt;"/> 
            				<br>
           				</td>
           				<td align="center" class="normalTextStyle" valign="top">考试教室（已选）<br><select name="SelectedRoom" MULTIPLE size="20" style="width:180px;" onDblClick="JavaScript:moveSelectedOption(this.form['SelectedRoom'], this.form['Rooms'])"></select></td>             
          			</tr>
         		</table>
        	</td>
        	<td class="brightStyle" valign="top">
         		<table class="formTable" width="100%">
          			<#if delayAgain >
          			<tr>
            			<td id="f_stdPerTurn" class="title"><font color="red">*</font>每场人数上限：</td>
            			<td><input name="examParams.stdPerTurn" value="" style="width:40px" maxlength="4"/>人</td>
          			<#else>
          			   <div id="f_stdPerTurn"><input name="examParams.stdPerTurn" value="0" type="hidden" maxlength="8"/></div>
          			</#if>
          			<tr>
            			<td class="title"><font color="red">*</font>冲突检测级别:</td>
            			<td><select name="examParams.detectCollision"><option value="1">行政班</option><option value="2">上课学生</option><option value="0">不检测</option></select></td>
          			</tr>
          			<tr>
            			<td class="title"><font color="red">*</font>同上课校区:</td>
            			<td><select name="examParams.sameDistrictWithCourse"><option value="1">是</option><option value="0">否</option></select></td>
          			</tr>
          			<tr>
            			<td class="title" id="f_collisionRatio"><font color="red">*</font>冲突比例(小数)不高于:</td>
            			<td><input name="examParams.collisionRatio" value="0" style="width:50px"  maxlength="10"/></td>
          			</tr>
          			<tr>
            			<td id="f_minRoomOccupyRatio" class="title"><font color="red">*</font>教室利用率下限</td>
            			<td><input name="examParams.minRoomOccupyRatio" value="0.5" style="width:40px" maxlength="10"/></td>
          			</tr>
				    <tr>
	    				<td class="infoTitle" colspan="2" id="f_groupCourseNos">可以安排在同一场次的课程代码:<br>(同一组用,隔开。不同组用;和回车隔开)</td>
        			</tr>
				    <tr>
	    				<td class="infoTitle" colspan="2"><textarea name="examParams.groupCourseNos"  cols="30" rows="3"></textarea></td>
        			</tr>
          			<tr>
	            		<td colspan="2" align="center"><button name="submitButton"  onclick ="startArrange()" style="height:30pt;width:68pt;" BORDER="0"><B>开始排考</B></button></td>
	          		</tr>
	         	</table>
	 			<pre>排考允许不安排教室.<br> 教室可以在调整中进行指定和修改.</pre>
			</td>
	        <input name="roomIds" value="" type="hidden">
	        <input name="weekIds" value="" type="hidden">
	        <input name="weekAndTurns" value="" type="hidden">
	        <input name="taskIds" value="${RequestParameters['taskIds']}" type="hidden">
	        <input name="params" value="${RequestParameters['params']}" type="hidden">
	        <input name="fromCalendar.id" value="${RequestParameters['calendar.id']}" type="hidden">
      	</tr>
      	</form>
	</table>
 	<#include "/templates/calendarSelect.ftl"/>
 	<script>
	  	var bar = new ToolBar("taskBar", "排考参数设置", null, true, true);
	  	bar.addBack("<@msg.message key="action.back"/>");
	  	var weekExamTurns=new Array(1);
   		var examTurns = new Array(7);
    	<#list weeks as week>
      		examTurns[${week.id}] = new Object();
       		<#list examTurns as examTurn>
         		examTurns[${week.id}].td${examTurn.id} = 0;
       		</#list>
    	</#list>
        weekExamTurns[0]=examTurns;
        //增加排考场次信息表
        function addExamTurns(){
           var newExamTurns = new Array(7);
           <#list weeks as week>
      		newExamTurns[${week.id}] = new Object();
       		<#list examTurns as examTurn>
         		newExamTurns[${week.id}].td${examTurn.id} = 0;
       		</#list>
    	   </#list>
    	   weekExamTurns.push(newExamTurns);
        }
   
        //删除最后一个排考场次信息表
        function removeExamTurns(){
           weekExamTurns.pop();
        }
        //得到当前选择的是第几个安排表
        function getWeekIndex(tableId){
          return parseInt(tableId.substring("timeTable_".length));
        }
   		function changeTurn(week, turn,tableId) {
   		    var weekIndex=getWeekIndex(tableId);
      		var status = weekExamTurns[weekIndex][week]["td" + turn];
      		if (status == 1) {
        		_changeTurn(week, turn, 0,tableId);
      		}else{
        		_changeTurn(week, turn, 1,tableId);
      		}
   		}
   		
   		function _changeTurn(week, turn, status,tableId) {
      		var td = document.getElementById(tableId+"_td" + week + "_" + turn);
      		var weekIndex=getWeekIndex(tableId);
      		if (status == 0) {
          		td.style.backgroundColor = "white";
          		td.innerHTML = "";
          		 weekExamTurns[weekIndex][week]["td" + turn] = 0;
      		} else {
          		td.style.backgroundColor = "#94aef3";
          		td.innerHTML = "使用";
          		 weekExamTurns[weekIndex][week]["td" + turn] = 1;
      		}
   		}	
   		function selectRowTurn(week, event,tableId) {
      		var ele = getEventTarget(event);
      		var weekIndex=getWeekIndex(tableId);
      		var turns = weekExamTurns[weekIndex][week];
      		for (var turn in turns) {
         		_changeTurn(week, turn.substring(2), (ele.checked ? 1 : 0),tableId);
      		}
   		}
   		
   		function selectColumnTurn(selectTurn, event,tableId) {
      		var ele = getEventTarget(event);
      		var weekIndex=getWeekIndex(tableId);
      		for (var i = 0; i < weekExamTurns[weekIndex].length; i++) {
         		var turns =  weekExamTurns[weekIndex][i];
         		if (null != turns) {
		      		for (var turn in turns) {
		         		if (turn == "td" + selectTurn) {
		            		_changeTurn (i, turn.substring(2), (ele.checked ? 1 : 0),tableId);
		         		}
		      		}
         		}
      		}
   		}
   		//时间表管理
   		var timeTableCount=1;
   		var form = document.examArrangeForm;
   		function onChangeWeek(requireTableCount){
   		   if(form['examParams.weekMode'].value=='2'){
   		     if(parseInt(requireTableCount)>timeTableCount){
	   		      while(parseInt(requireTableCount)>timeTableCount){
	   		        addTimeTable(timeTableCount);
	   		        timeTableCount++;
	   		      }
   		      }else if(parseInt(requireTableCount)<timeTableCount){
	   		      while(parseInt(requireTableCount)<timeTableCount){
	                removeTimeTable(timeTableCount-1);
	   		        timeTableCount--;
	   		      }
   		      }
   		   }else{
   		       if(1<timeTableCount){
	   		      while(1<timeTableCount){
	                removeTimeTable(timeTableCount-1);
	   		        timeTableCount--;
	   		      }
   		       }
   		   }
   		}
   		//增加时间表
   		function addTimeTable(tableIndex){
   		 var tb = document.getElementById("timeTable");
	     var tr = document.createElement('tr');
	     var td = document.createElement('td');
	     td.id="timeTable_td_"+tableIndex;
	     var content=document.getElementById("timeTable_td_0").innerHTML;
	     td.innerHTML=content.replace(/timeTable_0/g,"timeTable_"+tableIndex);
	     tr.appendChild(td);
	     tb.tBodies[0].appendChild(tr);
	     addExamTurns();
	     f_frameStyleResize(self);
	   }
	   
	   //删除时间表
	   function removeTimeTable(tableIndex){
         var tb = document.getElementById("timeTable");
         var rows= tb.rows;
		 for(var i=0;i<rows.length;i++){
		   if(rows[i].cells[0].id==("timeTable_td_"+tableIndex)){
		       tb.tBodies[0].removeChild(rows[i]);
		       break;
		   }
		 }
	     removeExamTurns();
	     f_frameStyleResize(self);
	   }

        //收集场次信息
   		function getTurnInfo() {
      		var selectWeekAndTurn = "";
      		for(var k=0;k<weekExamTurns.length;k++){
      		    var examTurns=weekExamTurns[k];
	      		for (var i = 0; i < examTurns.length; i++) {
	         		var turns = examTurns[i];
	         		if (null != turns) {
			      		for (var turn in turns) {
			         		if (turns[turn] == 1) {
			            		selectWeekAndTurn += k + "," +i + "," + turn.substring(2) + ";";
				         	}
				      	}
		         	}
		      	}
	      	}
	      	return selectWeekAndTurn;
	   	}
   
   		function startArrange() {
      		var form = document.examArrangeForm;
      		var examTurns = getTurnInfo();
      		if ("" == examTurns) {
         		alert("请选择场次");
         		return;
      		}
      		form['weekAndTurns'].value = examTurns;
      		var a_fields = {
      			'examParams.minRoomOccupyRatio':{'l':'教室利用率下限', 'r':true, 't':'f_minRoomOccupyRatio', 'f':'real'},
      			'examParams.fromWeek':{'l':'开始周', 'r':true, 't':'f_fromWeek', 'f':'positiveInteger'},
      			'examParams.stdPerTurn':{'l':'每场人数上限', 'r':true, 't':'f_stdPerTurn', 'f':'unsigned'},
      			'examParams.collisionRatio':{'l':'冲突比例', 'r':true, 't':'f_collisionRatio', 'f':'unsignedReal'},
      			'examParams.groupCourseNos':{'l':'同一场次的课程代码', 'r':false, 't':'f_groupCourseNos', 'mx':'300'}
      		};
      		var v = new validator(form, a_fields, null);
      		if (v.exec()){
	      		form['roomIds'].value = getAllOptionValue(form.SelectedRoom); 
	      		if ("" == form['roomIds'].value) {
	        		if (!confirm("没有选择教室,确定排考不排教室?")) {
	          			return;
	          		}
	      		}
	      		form.action = "examArrange.do?method=arrange";
	      		form.submit();
      		}
   		}
   		
 	</script>
</body>
<#include "/templates/foot.ftl"/> 
  