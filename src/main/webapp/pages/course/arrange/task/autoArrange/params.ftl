<div id="paramsDiv" style="display:none;" >
<table id="autoArrangeBar"></table>
	<table  width="100%" class="formTable">
	 <tr>
	  <td class="grayStyle" width="20%"  >&nbsp;<@bean.message key="attr.arrangeDensity"/></td>
	  <td class="brightStyle"  >每<input type="text" style="width:25px" name="arrangeParams.density" value="28" maxlength="2"/>小节安排一次课</td>
	 </tr>
	 <tr>
	  <td class="grayStyle" >&nbsp;时间冲突检测</td>
	  <td>
	     <select name="arrangeParams.detectCollision[0]" style="width:130px;">
		     <option value="1"  >检测教室冲突</option>
		     <option value="0" style="background-color:yellow">忽略教室冲突</option>
	     </select>&nbsp;
	     <select name="arrangeParams.detectCollision[1]"  style="width:130px">
		     <option value="1">检测教师冲突</option>
		     <option value="0" style="background-color:yellow">忽略教师冲突</option>
	     </select>&nbsp;
	     <select name="arrangeParams.detectCollision[2]" style="width:130px">
		     <option value="1">检测班级冲突</option>
		     <option value="0" style="background-color:yellow">忽略班级冲突</option>
	     </select>
	  </td>
	 </tr>
	 <tr>
	  <td class="grayStyle">&nbsp;可用时间</td>
	  <td>
	     <select name="arrangeParams.considerAvailTime[0]" style="width:130px">
		     <option value="1">检测教室可用时间</option>
		     <option value="0" style="background-color:yellow">忽略教室可用时间</option>
	     </select>&nbsp;
	     <select name="arrangeParams.considerAvailTime[1]" style="width:130px">
		     <option value="1" >检测教师可用时间</option>
		     <option value="0" style="background-color:yellow">忽略教师可用时间</option>
	     </select>
	  </td>
	 </tr>
	 <tr>
	  <td class="grayStyle">&nbsp;任务排课建议</td>
	  <td>
	     <select name="arrangeParams.bySuggest[1]" style="width:130px">
		     <option value="1">考虑建议时间</option>
		     <option value="0" style="background-color:yellow">忽略建议时间</option>
	     </select>&nbsp;
	     <select name="arrangeParams.bySuggest[0]" style="width:130px">
		     <option value="1">考虑建议教室</option>
		     <option value="0" style="background-color:yellow">忽略建议教室</option>
	     </select>
	     <br>
	     &nbsp;按照建议时间安排失败后停止安排该任务：
         <input type="radio" name="arrangeParams.stopWhenFailedBySuggest"  value="1"/><@bean.message key="common.yes"/>
         <input type="radio" name="arrangeParams.stopWhenFailedBySuggest" checked value="0"/><@bean.message key="common.no"/>
	  </td>
	 </tr>
	</table>
    <#if RequestParameters['arrangeType']=="task">
     <#include "../taskGroup/availTimeTable.ftl"/>	
	</#if>
	</div>
	<script>
	function setParams(){
	   if(!checkSelectIds()) return;
       var paramsDiv = document.getElementById("paramsDiv");
       var taskListDiv =document.getElementById("taskListDiv");
       taskListDiv.style.display="none";
       paramsDiv.style.display="block";
       f_frameStyleResize(self);
    }
    function backToList(){
       var paramsDiv = document.getElementById("paramsDiv");
       var taskListDiv =document.getElementById("taskListDiv");
       taskListDiv.style.display="block";
       paramsDiv.style.display="none";
        f_frameStyleResize(self);
    }
   var bar = new ToolBar('autoArrangeBar','<@bean.message key="info.arrange.paramsManagement"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@bean.message key="action.backToList"/>",backToList,'backward.gif');
   bar.addItem("<@bean.message key="action.autoArrange"/>","arrange()",'save.gif'); 
    </script>