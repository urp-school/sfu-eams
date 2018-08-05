<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/CascadeSelect.js"></script> 
<BODY LEFTMARGIN="0" TOPMARGIN="0">
    <table id="bar"></table>
    <table class="formTable" align="center" width="80%">
        <form action="" method="post" name="actionForm" onsubmit="return false;">
	        <tr class="darkColumn"  align="center">
	            <td colspan="4"><B>空闲教室查询</B></td>
	        </tr>
	        <tr>
	            <td class="title">教室名称：</td>
                <td><input name="classroom.name" value="${RequestParameters['classroom.name']?if_exists}" maxlength="10" style="width:100px"/></td>
    	   		<td class="title" id="f_district"><@bean.message key="entity.schoolDistrict"/>:</td>
 	          	<td>
  	          		<select id="district" name="classroom.schoolDistrict.id" style="width:120px;">
	             		<option value=""><@bean.message key="common.all"/></option>
                    </select>
  	       	     </td>
	        </tr>
		<tr>
	            <td class="title"><@bean.message key="common.classroomConfigType"/>：</td>
	            <td><@htm.i18nSelect datas=roomConfigTypes selected=(RequestParameters["classroom.configType.id"]?string)?default("") name="classroom.configType.id" style="width:120px;">
	                <option value="">...</option>
	              </@>
	            </td>
      		<td  class="title"><@bean.message key="entity.building"/>:</td>
      		<td >
         		<select id="building" name="classroom.building.id" style="width:120px;">
	         		<option value=""><@bean.message key="common.all"/></option>
         		</select>
 	    	</td>
    	</tr>
    	<tr>
            <td class="title">听课人数(≥)：</td>
            <td><input name="classroom.capacityOfCourse" value="${RequestParameters['classroom.capacityOfCourse']?if_exists}" maxlength="10" style="width:100px"/></td>
            <td class="title">考试人数(≥)：</td>
            <td><input name="classroom.capacityOfExam" value="${RequestParameters['classroom.capacityOfExam']?if_exists}" maxlength="10" style="width:100px"/></td>
        </tr>
        <tr>
	            <td class="title" id="f_begin_end" align="right">教室使用日期：</td>
	            <td colspan="3"><input type="text" name="roomApply.applyTime.dateBegin" value="${RequestParameters['roomApply.applyTime.dateBegin']?if_exists}" onfocus="calendar()" size="10" maxlength="10"/> - <input type="text" name="roomApply.applyTime.dateEnd" value="${RequestParameters['roomApply.applyTime.dateEnd']?if_exists}" onfocus="calendar()" size="10" maxlength="10"/> (年月日)</td>
	        </tr>
	        <tr>
	            <td class="title" id="f_beginTime_endTime" align="right">教室使用时间点：</td>
	            <td colspan="3"><input type="text" name="roomApply.applyTime.timeBegin" value="${RequestParameters['roomApply.applyTime.timeBegin']?if_exists}" size="10" class="LabeledInput" value="" format="Time" maxlength="5"/> - <input type="text" name="roomApply.applyTime.timeEnd"  value="${RequestParameters['roomApply.applyTime.timeEnd']?if_exists}" size="10" maxlength="5"/> (时:分)</td>
	        </tr>
	        <tr>
	            <td class="title" id="f_cycleCount" align="right">时间周期：</td>
	            <#assign cycleCount=RequestParameters['roomApply.applyTime.cycleCount']?default('1')/>
	            <#if cycleCount?length==0><#assign cycleCount="1"/></#if>
	            <td colspan="3">每：<input type="text" name="roomApply.applyTime.cycleCount" style="width:20px" value="${cycleCount}" maxlength="2"/>
	                <#assign cycleType =RequestParameters['roomApply.applyTime.cycleType']?default('1')>
	                <select name="roomApply.applyTime.cycleType">
	                    <option value="1" <#if cycleType=='1'>selected</#if>>天</option>
	                    <option value="2" <#if cycleType=='2'>selected</#if>>周</option>
	                    <option value="4" <#if cycleType=='4'>selected</#if>>月</option>
	                </select>
	            </td>
	        </tr>
	        <tr class="title">
	            <td colspan="4" align="center"><button accesskey="L" onClick="searchFreeApply()">查询(<u>L</u>)</button>
	            &nbsp;&nbsp;<button accesskey="R" onClick="this.form.reset()">重填(<u>R</u>)</button>
	            <#if (rooms?if_exists?size>0)>
	            &nbsp;&nbsp;<button accesskey="R" onClick="quickApplySetting()">借用(<u>A</u>)</button>
	            </#if>
	            </td>
	        </tr>
	        <input type="hidden" name="classroomIds" value=""/>
        </form>
    </table>
    <table class="formTable" align="center" width="80%">
    <tr><td>
    <#include "../roomApply/freeRoomList.ftl"/>
    </td></tr></table>
    <br><br><br><br><br><br>
	<#include "/templates/districtBuildingSelect.ftl"/>
    <script>
        var bar = new ToolBar("bar", "教室借用管理", null, true, true);
        bar.setMessage('<@getMessage/>');
    	bar.addBlankItem();
        
        var form = document.actionForm;
        
        form["classroom.schoolDistrict.id"].value = "${RequestParameters["classroom.schoolDistrict.id"]?default("2")}";
		initSelect(districtList,"district",null,"building"); 
        $("district").options.remove(0);
		initSelect(buildingList,"building","district",null); 
        form["classroom.building.id"].value = "${RequestParameters["classroom.building.id"]?default("")}";
        
        function searchFreeApply() {
            var errorInfo="";
            if (beforeApply()) {
                if(form['roomApply.applyTime.dateBegin'].value>form['roomApply.applyTime.dateEnd'].value){
                   alert("借用日期不对");return;
                }
                if(form['roomApply.applyTime.timeBegin'].value>=form['roomApply.applyTime.timeEnd'].value){
                   alert("借用时间不对");return;
                }
	            form.action = "ecuplRoomApplyApprove.do?method=freeRoomList";
	            form.target = "";
	            form.submit();
            }
        }
        
        function beforeApply() {
            var a_fields = {
                'classroom.schoolDistrict.id':{'l':'校区', 'r':true, 't':'f_district'},
                'roomApply.applyTime.dateBegin':{'l':'教室使用日期的“起始日期”', 'r':true, 't':'f_begin_end','f':'date'},
                'roomApply.applyTime.dateEnd':{'l':'教室使用日期的“结束日期”', 'r':true, 't':'f_begin_end','f':'date'},
                'roomApply.applyTime.timeBegin':{'l':'教室使用的“开始”时间点', 'r':true, 't':'f_beginTime_endTime','f':'shortTime'},
                'roomApply.applyTime.timeEnd':{'l':'教室使用的“结束”时间点', 'r':true, 't':'f_beginTime_endTime','f':'shortTime'},
                'roomApply.applyTime.cycleCount':{'l':'单位数量', 'r':true, 't':'f_cycleCount', 'f':'positiveInteger'}
            };
            var v = new validator(form, a_fields, null);
            return v.exec();
        }
    
        function quickApplySetting() {
            var classroomIds = getSelectIds("classroomId");
            if (beforeApply()) {
                if (null == classroomIds || "" == classroomIds) {
                    alert("请选择要申请的教室。");
                    return;
                }
                form.action = "ecuplRoomApplyApprove.do?method=quickApplySetting";
                form.target = "_self";
                form["classroomIds"].value = classroomIds;
                form.submit();
            }
        }
    </script>
</BODY>
<#include "/templates/foot.ftl"/>