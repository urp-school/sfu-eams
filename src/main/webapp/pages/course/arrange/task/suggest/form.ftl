<#include "/templates/head.ftl"/>
<body>
<form name="taskAvalibeTimeForm" method="post" action="arrangeSuggest.do?method=save" onsubmit="return false;">
      <input type="hidden" name="availTime" value=""/>
      <input type="hidden" name="task.id" value="${task.id}"/>
      <input type="hidden" name="availTime.id" value="${availTime.id?if_exists}"/>
      <input type="hidden" name="roomIds" value="">
      <input type="hidden" name="task.calendar.id" value="${RequestParameters['task.calendar.id']?if_exists}">
      <input type="hidden" name="inIframe" value="${RequestParameters['inIframe']?if_exists}">
  
  <table id="taskSuggestBar"></table>
  <table align="center" class="listTable" width="100%">
    <tr class="darkColumn">
        <td width="50px"></td>
     	<#list unitList?sort_by("id") as unit>
		<td>
		 <@i18nName unit/>  
		</td>
		</#list>
    </tr>
	<#list weekList as week>
	<tr>
	    <td class="darkColumn"><@i18nName week/></td>
  	    <#list 0..unitList?size-1 as unit>
  	    <#assign unitIndex=week_index*unitList?size + unit_index>
   		<td id="${unitIndex}" <#if availTime.available[unitIndex..unitIndex]=="1"> style="backGround-Color:#94aef3" <#else>style="backGround-Color:yellow"</#if> onClick="javascript:changeAvailTime(this)" >
   		<input type="hidden" id="unit${unitIndex}" value="${availTime.available[unitIndex..unitIndex]}"/>
   		</td>
		</#list>
	</tr>
    </#list>
</table>
<table align="center" width="100%"border="0">
	<tr class="infoTitle">
	 <td ><@bean.message key="info.availTime.legend"/></td>
	 <td style="backGround-Color:#94aef3" width="60px"><@bean.message key="info.avaliTime.available"/></td>
	 <td style="backGround-Color:yellow" width="60px"><@bean.message key="info.avaliTime.unavailable"/></td>
	 <td align="right">
	   <#assign selectUnits = task.arrangeInfo.courseUnits>
	   <#if (selectUnits>4)><#assign selectUnits=4><#elseif  (selectUnits<1)><#assign selectUnits=1></#if>
	     一次选择的节次：
	     <input type="radio" name="selectUnitNum" value='1' <#if selectUnits=1>checked</#if>>单节
	     <input type="radio" name="selectUnitNum" value='2' <#if selectUnits=2>checked</#if>>双节
	     <input type="radio" name="selectUnitNum" value='3' <#if selectUnits=3>checked</#if>>三节
	     <input type="radio" name="selectUnitNum" value='4' <#if selectUnits=4>checked</#if>>四节</td>
	 </td>
	</tr>
</table>
<script language="JavaScript" type="text/JavaScript" src="scripts/course/availTime.js"></script>
<script>
   unitsPerDay=${unitList?size};
</script>
  <br>
  <table  width="100%" border="0" class="infoTitle">  
  <tr>
    <td colspan="2">
     <img src="${static_base}/images/action/info.gif" align="top"/>排课文字建议(50字以内)和教室
    </td>
  </tr>
  <tr>
      <td  colspan="6" style="font-size:0px" >
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
  <tr>
   <td width="60%">
    文字建议<br>
    <textarea name="remark" cols="30" rows="8">${task.arrangeInfo.suggest.time?if_exists.remark?if_exists}</textarea>
   </td>
   <td id="f_room">
      <table class="infoTitle">
        <tr><td>&nbsp;可选教室(名称/类型/人数)</td><td>:</td><td>建议上课教室</td></tr>
         <tr>
           <td>
            <select name="Rooms" MULTIPLE size="8" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['Rooms'], this.form['SelectedRooms'])" >
             <#list classroomList?sort_by("name") as room>
              <option value="${room.id}">${room.name}/[<@i18nName room.configType/>]/${room.capacityOfCourse}</option>
             </#list>-->
            </select>
           </td>
           <td align="center" valign="middle">
            <br><br>
            <input OnClick="JavaScript:moveSelectedOption(this.form['Rooms'], this.form['SelectedRooms'])" type="button" value="&gt;"> 
            <br><br>
            <input OnClick="JavaScript:moveSelectedOption(this.form['SelectedRooms'], this.form['Rooms'])" type="button" value="&lt;"> 
            <br>
           </td>
           <td>
            <select name="SelectedRooms" MULTIPLE size="8" style="width:200px;" onDblClick="JavaScript:moveSelectedOption(this.form['SelectedRooms'], this.form['Rooms'])">
             <#list task.arrangeInfo.suggest.rooms?if_exists as room>
              <option value="${room.id}">${room.name}/[<@i18nName room.configType/>]/${room.capacityOfCourse}</option>
             </#list>
            </select>
           </td>             
          </tr>
         </table>
        </td>
       </tr>
    <tr class="infoTitle">
     <td colspan="2">
      <li>可以在排课表中按照图例，设置排课的建议时间。输入图表中的排课信息将在排课中考虑进去。</li>
     </td>
    </tr>
  </table>
</form>
<script>
     function save(form){
     	if (checkTextLength(form["roomIds"].value, "文字建议")) {
     		form.availTime.value = getAvailTime();
	        form['roomIds'].value=getAllOptionValue(form.SelectedRooms)
	        form.submit();
	        alert("保存成功!");
	        self.window.close();
     	}
     }
     
     function checkSave(){
       var form =document.taskAvalibeTimeForm;
       var availTime=getAvailTime();
       if((availTime.indexOf('0')!=-1)
         || (form['task.arrangeInfo.suggestRemark'].value!="")
         ||  (getAllOptionValue(form.SelectedRooms)!="")){
         if(confirm("已经设置了排课建议，保存请按确定，否则点击取消")){
            form['roomIds'].value=getAllOptionValue(form.SelectedRooms);
            form.availTime.value = availTime;
         	form.submit();
	        alert("保存成功!");
         }
       }
     }
     function closeSelf(){
        checkSave();
        self.window.close();
     }
   var bar = new ToolBar('taskSuggestBar','教学任务[${task.seqNo},<@i18nName task.course/>,<@getTeacherNames task.arrangeInfo.teachers/> 周课时(${task.arrangeInfo.weekUnits})]的排课建议(时间)',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@bean.message key="action.clear"/>",clearAll,'action.gif');
   bar.addItem("<@bean.message key="action.inverse"/>",inverse,'action.gif');
   bar.addItem("<@bean.message key="action.save"/>","save(taskAvalibeTimeForm);",'save.gif');
   <#if RequestParameters['inIframe']?exists>
   bar.addBack("<@msg.message key="action.back"/>");
   <#else>
   bar.addItem("<@msg.message key="action.close"/>",closeSelf,'cancel.gif'); 
   </#if>    
</script> 
</body>
<#include "/templates/foot.ftl"/>