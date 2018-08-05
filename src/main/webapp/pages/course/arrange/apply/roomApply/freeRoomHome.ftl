<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/CascadeSelect.js"></script> 
<BODY LEFTMARGIN="0" TOPMARGIN="0">
    <table id="bar"></table>
    <table class="formTable" align="center" width="80%">
        <form action="" method="post" name="actionForm" onsubmit="return false;">
	        <input name="classroomIds" type="hidden" value=""/>
	        <tr class="darkColumn"  align="center">
	            <td colspan="4"><B>空闲教室查询</B></td>
	        </tr>
	        <tr>
	            <td class="title">教室类型名称：</td>
	            <td colspan="3"><@htm.i18nSelect datas=roomConfigTypes selected=(RequestParameters["classroom.configType.id"]?string)?default("") name="classroom.configType.id" style="width:120px;">
	                <option value="">...</option>
	              </@>
	            </td>
	        </tr>
		<tr>
	   		<td class="title"><@bean.message key="entity.schoolDistrict"/>:</td>
 	    	<td colspan="3">
  	       		<select id="district" name="classroom.schoolDistrict.id" style="width:120px;">
	           		<option value=""><@bean.message key="common.all"/></option>
	       		</select>
  	       	</td>
    	</tr>
    	<tr>
      		<td  class="title"><@bean.message key="entity.building"/>:</td>
      		<td >
         		<select id="building" name="classroom.building.id" style="width:120px;">
	         		<option value=""><@bean.message key="common.all"/></option>
         		</select>
 	    	</td>
            <td class="title">听课人数(≥)：</td>
            <td><input name="classroom.capacityOfCourse" value="${RequestParameters['classroom.capacityOfCourse']?if_exists}" maxlength="10" style="width:100px"/></td>
    	</tr>
    	<tr>
	            <td class="title">教室名称：</td>
	            <td><input name="classroom.name" value="${RequestParameters['classroom.name']?if_exists}" maxlength="10" style="width:100px"/></td>
	            <td class="title">考试人数(≥)：</td>
	            <td><input name="classroom.capacityOfExam" value="${RequestParameters['classroom.capacityOfExam']?if_exists}" maxlength="10" style="width:100px"/></td>
	        </tr>
	        <tr>
	            <td class="title" id="f_begin_end" align="right">教室使用日期：</td>
	            <td colspan="3"><input type="text" name="roomApply.applyTime.dateBegin" value="${RequestParameters['roomApply.applyTime.dateBegin']?if_exists}" onfocus="calendar()" size="10" maxlength="10"/> - <input type="text" name="roomApply.applyTime.dateEnd" value="${RequestParameters['roomApply.applyTime.dateEnd']?if_exists}" onfocus="calendar()" size="10" maxlength="10"/> (年月日)</td>
	        </tr>
	        <tr>
	            <td class="title" id="f_beginTime_endTime" align="right">教室使用时间点：</td>
	            <td colspan="3"><input type="text" name="roomApply.applyTime.timeBegin" value="${RequestParameters['roomApply.applyTime.timeBegin']?if_exists}" size="10" class="LabeledInput" value="" format="Time" maxlength="5"/> - <input type="text" name="roomApply.applyTime.timeEnd"  value="${RequestParameters['roomApply.applyTime.timeEnd']?if_exists}" size="10" maxlength="5"/> (时分)</td>
	        </tr>
	        <tr>
	            <td class="title" id="f_cycleCount" align="right">时间周期：</td>
	            <td colspan="3">每：<input type="text" name="roomApply.applyTime.cycleCount" style="width:20px" value="${RequestParameters['roomApply.applyTime.cycleCount']?default('1')}" maxlength="2"/>
	                <#assign cycleType =RequestParameters['roomApply.applyTime.cycleType']?default('1')>
	                <select name="roomApply.applyTime.cycleType">
	                    <option value="1" <#if cycleType=='1'>selected</#if>>天</option>
	                    <option value="2" <#if cycleType=='2'>selected</#if>>周</option>
	                    <option value="4" <#if cycleType=='4'>selected</#if>>月</option>
	                </select>
	            </td>
	        </tr>
	        <tr class="title">
	            <td colspan="4" align="center"><button accesskey="L" onClick="searchFreeApply()">查看(<u>L</u>)</button>&nbsp;&nbsp;<button accesskey="R" onClick="this.form.reset()">重填(<u>R</u>)</button></td>
	        </tr>
        </form>
    </table>
    <#include "freeRoomList.ftl"/>
    <br><br><br><br><br><br>
	<#include "/templates/districtBuildingSelect.ftl"/>
    <script>
        var bar = new ToolBar("bar", "查看空闲教室", null, true, true);
        bar.setMessage('<@getMessage/>');
    	bar.addHelp("<@msg.message key="action.help"/>");
        
        var form = document.actionForm;
        
        form["classroom.schoolDistrict.id"].value = "${RequestParameters["classroom.schoolDistrict.id"]?default("")}";
		initSelect(districtList,"district",null,"building"); 
        $("district").options.remove(8);
		initSelect(buildingList,"building","district",null); 
        form["classroom.building.id"].value = "${RequestParameters["classroom.building.id"]?default("")}";
        
        function searchFreeApply() {
            var errorInfo="";
            var a_fields = {
                'roomApply.applyTime.dateBegin':{'l':'教室使用日期的“起始日期”', 'r':true, 't':'f_begin_end','f':'date'},
                'roomApply.applyTime.dateEnd':{'l':'教室使用日期的“结束日期”', 'r':true, 't':'f_begin_end','f':'date'},
                'roomApply.applyTime.timeBegin':{'l':'教室使用的“开始”时间点', 'r':true, 't':'f_beginTime_endTime','f':'shortTime'},
                'roomApply.applyTime.timeEnd':{'l':'教室使用的“结束”时间点', 'r':true, 't':'f_beginTime_endTime','f':'shortTime'},
                'roomApply.applyTime.cycleCount':{'l':'单位数量', 'r':true, 't':'f_cycleCount', 'f':'positiveInteger'}
            };
            var v = new validator(form, a_fields, null);
            if (v.exec()) {
                if(form['roomApply.applyTime.dateBegin'].value>form['roomApply.applyTime.dateEnd'].value){
                   alert("借用日期不对");return;
                }
                if(form['roomApply.applyTime.timeBegin'].value>=form['roomApply.applyTime.timeEnd'].value){
                   alert("借用时间不对");return;
                }
	            form.action = "roomApply.do?method=freeRoomList";
	            form.target = "";
	            form.submit();
            }
        }
    
	    function addApply() {
	        form.action = "roomApply.do?method=addApply";
	        form.target = "";
	        form.submit();
	    }
    </script>
</BODY>
<#include "/templates/foot.ftl"/>