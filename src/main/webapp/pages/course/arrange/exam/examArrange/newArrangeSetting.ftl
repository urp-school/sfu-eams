<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<BODY>
	<#macro getTimeStr(time)>${time?string?left_pad(4,"0")[0..1]}:${time?string?left_pad(4,"0")[2..3]}</#macro>
	<table id="taskBar"></table>
    <table width="100%" class="listTable" id="turnsTable">
       	<tr align="center" class="darkColumn">
       		<td width="10%"></td>
       		<#list examTurns as examTurn>
       			<td><input type="checkbox" onclick="selectColumnTurn(${examTurn.id},event)"/>${examTurn.name}(<@getTimeStr examTurn.beginTime/>-<@getTimeStr examTurn.endTime/>)</td>
       		</#list>
       	</tr>
    	<#list weeks as week>
       		<tr align="center">
       			<td class="darkColumn"><@i18nName week/><input type="checkbox" name="${week.id}" onclick="selectRowTurn(${week.id},event)"/></td>
   				<#list examTurns  as examTurn>
   					<td title="点击选中或者取消" onClick="javascript:changeTurn(${week.id},'${examTurn.id}')" id="td${week.id}_${examTurn.id}" style="cursor:hand"></td>
       			</#list>
       		</tr>
    	</#list>
	</table>
    <table width="100%" border="0">
     	<form name="examArrangeForm" action="" method="post" onsubmit="return false;">
	   	<tr>
        	<td class="brightStyle" valign="top" width="40%">
         		<table class="listTable">
          			<tr>
           				<td>考试教室(名称、容纳人数)<br>   
            				<select name="Rooms" MULTIPLE size="20" style="width:180px" onDblClick="JavaScript:moveSelectedOption(this.form['Rooms'], this.form['SelectedRoom'])" >
	             				<#list  rooms as room>
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
         		<table class="listTable" width="100%">
          			<tr>
          				<td>排考类型</td>
              			<td><@i18nName params.examType/><input type="hidden" name="examParams.examType.id" value="${params.examType.id}"></td>
          			</tr>
          			<tr>
            			<td id="f_minRoomOccupyRatio"><font color="red">*</font>教室利用率下限</td>
            			<td><input name="examParams.minRoomOccupyRatio" value="0.5" style="width:40px" maxlength="10"/></td>
          			</tr>
          			<tr>
            			<td id="f_weeksAfterCourse"><font color="red">*</font>从排课结束的第</td>
            			<td><input onfocus="this.form['examParams.fromWeek'].value=''" name="examParams.weeksAfterCourse" value="" style="width:40px" maxlength="8"/>周</td>
          			</tr>
          			<tr>
            			<td id="f_fromWeek">&nbsp;或者从开始周：</td>
            			<td><input onfocus="this.form['examParams.weeksAfterCourse'].value=''" name="examParams.fromWeek" value="<#ifdelayAgain>1</#if>" style="width:40px" maxlength="8"/>周</td>
          			</tr>
          			<tr>
            			<td id="f_lastWeek"><font color="red">*</font>排考截至到：</td>
            			<td><input name="examParams.lastWeek" value="21" style="width:40px" maxlength="8"/>周</td>
          			</tr>
          			<#if delayAgain >
          			<tr>
            			<td id="f_stdPerTurn"><font color="red">*</font>每场人数上限：</td>
            			<td><input name="examParams.stdPerTurn" value="" style="width:40px" maxlength="4"/>人</td>
          			<#else>
          			   <div id="f_stdPerTurn"><input name="examParams.stdPerTurn" value="0" type="hidden" maxlength="8"/></div>
          			</#if>
          			<tr>
            			<td><font color="red">*</font>冲突检测级别:</td>
            			<td><select name="examParams.detectCollision"><option value="1">行政班</option><option value="2">上课学生</option><option value="0">不检测</option></td>
          			</tr>
          			<tr>
            			<td align="bottom">
               				<select id="stdType" name="calendar.studentType.id" style="width:100px;display:none">
		        				<option value="${calendar.studentType.id}"></option>
		       				</select>
          					安排到学年度：
           				</td>
           				<td>
            				<select id="year" name="calendar.year"  style="width:100px;">
			        			<option value="${calendar.year}"></option>
			      			</select>
    					</td>
    				</tr>
    				<tr>
	    				<td class="infoTitle"><@bean.message key="attr.term"/>:</td>
	    				<td style="width:50px;">     
	     					<select id="term" name="calendar.term" style="width:80px;">
	        					<option value="${calendar.term}"></option>
	      					</select>
	    				</td>
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
   		var examTurns = new Array(8);
    	<#list weeks as week>
      		examTurns[${week.id}] = new Object();
       		<#list examTurns as examTurn>
         		examTurns[${week.id}].td${examTurn.id} = 0;
       		</#list>
    	</#list>
   
   		function changeTurn(week, turn) {
      		var status = examTurns[week]["td" + turn];
      		if (status == 1) {
        		_changeTurn(week, turn, 0);
      		}else{
        		_changeTurn(week, turn, 1);
      		}
   		}
   		
   		function _changeTurn(week, turn, status) {
      		var td = document.getElementById("td" + week + "_" + turn);
      		if (status == 0) {
          		td.style.backgroundColor = "white";
          		td.innerHTML = "";
          		examTurns[week]["td" + turn] = 0;
      		} else {
          		td.style.backgroundColor = "#94aef3";
          		td.innerHTML = "使用";
          		examTurns[week]["td" + turn] = 1;
      		}
   		}	
   		function selectRowTurn(week, event) {
      		var ele = getEventTarget(event);
      		var turns = examTurns[week];
      		for (var turn in turns) {
         		_changeTurn(week, turn.substring(2), (ele.checked ? 1 : 0));
      		}
   		}
   		
   		function selectColumnTurn(selectTurn, event) {
      		var ele = getEventTarget(event);
      		for (var i = 0; i < examTurns.length; i++) {
         		var turns = examTurns[i];
         		if (null != turns) {
		      		for (var turn in turns) {
		         		if (turn == "td" + selectTurn) {
		            		_changeTurn (i, turn.substring(2), (ele.checked ? 1 : 0));
		         		}
		      		}
         		}
      		}
   		}
   		
   		function getTurnInfo() {
      		var selectWeekAndTurn = "";
      		for (var i = 0; i < examTurns.length; i++) {
         		var turns = examTurns[i];
         		if (null != turns) {
		      		for (var turn in turns) {
		         		if (turns[turn] == 1) {
		            		selectWeekAndTurn += i + "," + turn.substring(2) + ";";
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
      			'examParams.weeksAfterCourse':{'l':'从排课结束的第几周', 'r':false, 't':'f_weeksAfterCourse', 'f':'unsigned'},
      			'examParams.fromWeek':{'l':'开始周', 'r':false, 't':'f_fromWeek', 'f':'unsigned'},
      			'examParams.stdPerTurn':{'l':'每场人数上限', 'r':true, 't':'f_stdPerTurn', 'f':'unsigned'},
      			'examParams.lastWeek':{'l':'排考截至', 'r':true, 't':'f_lastWeek', 'f':'unsigned'},
      			'examParams.groupCourseNos':{'l':'同一场次的课程代码', 'r':false, 't':'f_groupCourseNos', 'mx':'100'}
      		};
      		if(form['examParams.fromWeek'].value==""&&form['examParams.weeksAfterCourse'].value==""){
      		   alert("从排课结束的第几周或者开始周必填其一");return;
      		}
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
  