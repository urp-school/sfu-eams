<#include "/templates/head.ftl"/>
<#import "../cycleType.ftl" as RoomApply/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<style>
input,textarea {
border: 1px solid #333333;
}
</style>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
    <table id="bar"></table>
    <table class="formTable" align="center" width="100%">
        <form action="roomApplyApprove.do?method=freeRoomList" method="post" name="roomApplyForm" onsubmit="return false;">
        <input name="classroomIds" type="hidden" value=""/>
        <tr class="darkColumn"  align="center">
            <td colspan="7"><B>借用教室申请表</B></td>
        </tr>
        <tr >
        	<td class="title" align="center" rowspan="1" width="30%">申请人</td>
        	<td class="title" align="right" id="f_username"><font color="red">*</font>&nbsp;姓名：</td>
        	<td><input type="hidden" name="roomApply.borrower.user.id" value="${user.id}"/>${user.userName}</td>
        	<!--<td class="title" align="right" width="18%" id="f_addr"><font color="red">*</font>&nbsp;地址：</td>
        	<td width="24%"><input type="text" name="roomApply.borrower.addr" size="15" maxlength="200"/></td>
        </tr>
        <tr>-->
        	<td class="title" align="right" id="f_mobile"><font color="red">*</font>&nbsp;联系方式(手机)：</td>
			<td colspan	="6"><input type="text" name="roomApply.borrower.mobile" size="15" maxlength="20"/></td>
			<!--<td class="title" align="right" id="f_email"><font color="red">*</font>&nbsp;E-mail：</td>
			<td><input type="text" name="roomApply.borrower.email" value="${user.email?default('')}" size="15" maxlength="70"/></td>
        </tr>-->
        <tr>
        	<td class="title" align="center" rowspan="10" >借用用途、性质</td>
        	<td class="title" align="right" id="f_activityName"><font color="red">*</font>&nbsp;申请单位名称：<br>例如“班级名称、协会名称”</td>
        	<td colspan	="6"><input type="text" name="roomApply.activityName" size="50" maxlength="50"/></td>
        </tr>
        <tr>
        	<td class="title" align="right" rowspan="9" id="f_activityType"><font color="red">*</font>&nbsp;活动类型&审核人签名：</td>
        	<!--<td colspan="3">
        		<#list activityTypes as activityType>
        			<input type="radio" name="roomApply.activityType.id" <#if 0=activityType_index>checked</#if> value="${activityType.id}"/>${activityType.name}&nbsp;&nbsp;
        		</#list>
        	</td>-->
          <td align="center" id="f_mobile">&nbsp;活动类型</td>
        	<td align="center" id="f_mobile">&nbsp;相关部门</td>
        	<td align="center" id="f_mobile">&nbsp;审核人签名</td>
          <td align="center" id="f_mobile">&nbsp;使用设备</td>
        </tr>
        <tr>
          <td align="left" id="f_mobile">&nbsp;A.临时换课</td>
        	<td align="left" id="f_mobile">&nbsp;任课教师</td>
        	<td align="left" id="f_mobile">&nbsp;</td>
          <td rowspan="8" align="left" id="f_mobile"><input type=checkbox>普通教室<br><input type=checkbox>电脑<br><input type=checkbox>投影<br><input type=checkbox>话筒<br><input type=checkbox>展台</td>
        </tr>
        <tr>
          <td align="left" id="f_mobile">&nbsp;B.班会/团日活动</td>
        	<td align="left" id="f_mobile">&nbsp;辅导员/系部意见</td>
        	<td align="left" id="f_mobile">&nbsp;</td>
        </tr>        <tr>
          <td align="left" id="f_mobile">&nbsp;C.协会活动</td>
        	<td align="left" id="f_mobile">&nbsp;指导教师/团委意见</td>
        	<td align="left" id="f_mobile">&nbsp;</td>
        </tr>        <tr>
          <td align="left" id="f_mobile">&nbsp;D.校内教师讲座</td>
        	<td align="left" id="f_mobile">&nbsp;系部/团委意见</td>
        	<td align="left" id="f_mobile">&nbsp;</td>
        </tr>        <tr>
          <td align="left" id="f_mobile">&nbsp;E.校外人员讲座</td>
        	<td align="left" id="f_mobile">&nbsp;宣传部意见</td>
        	<td align="left" id="f_mobile">&nbsp;</td>
     </tr> <tr>
        	<td align="left" id="f_isFree">&nbsp;以上活动如需播放影片</td>
        	<td align="left" id="f_mobile">&nbsp;宣传部意见</td>
        	<td align="left" id="f_mobile">&nbsp;</td>
        </tr>
        <tr>
          <td align="left" id="f_isFree">&nbsp;以上活动如涉及赞助费</td>
        	<td align="left" id="f_mobile">&nbsp;财务处意见</td>
        	<td align="left" id="f_mobile">&nbsp;</td>
        </tr>        <tr>
          <td align="left" id="f_isFree">&nbsp;外单位借用教室</td>
        	<td align="left" id="f_mobile">&nbsp;学校办公室意见</td>
        	<td align="left" id="f_mobile">&nbsp;</td>
        </tr>
        <!--<tr>
        	<td class="title" align="center" rowspan="2" style="text-align:center">主讲人</td>
        	<td colspan="4" id="f_leading"><font color="red">*</font>&nbsp;姓名及活动内容(非本校人员请详述基本情况和职业背景)：</td>
        </tr>
        <tr>
        	<td colspan="4"><textarea name="roomApply.leading" rows="3" cols="50"></textarea></td>
        </tr>-->
        <tr>
        	<td class="title" style="text-align:center"id="f_roomRequest" rowspan="2" style="text-align:center">&nbsp;借用收费及标准</td>
        	<td class="title" align="right" id="f_isFree" style="width:100%">&nbsp;收费标准(财务处意见)：</td>
        	<td colspan="4">
        		<!--<input type="radio" name="roomApply.isMultimedia" value="1"<#if (roomApply.isMultimedia)?exists == false || (roomApply.isMultimedia)?exists && roomApply.isMultimedia?string("true", "false") == "true"> checked</#if>/>&nbsp;使用&nbsp;
        		<input type="radio" name="roomApply.isMultimedia" value="0"<#if (roomApply.isMultimedia)?exists && roomApply.isMultimedia?string("true", "false") == "false"> checked</#if>/>&nbsp;不使用-->
        	</td>
        </tr>
        <tr>
        	<td class="title">&nbsp;收款部门(财务处指定)：</td>
         	<td colspan="4">&nbsp;</td>
        	<!--<td colspan="3"><@htm.i18nSelect datas=schoolDistricts selected=(roomApply.schoolDistrict.id?string)?default("") name="roomApply.schoolDistrict.id" style="width:200px"/></td>-->
        </tr>
       <!-- <tr>
        	<td class="title">其它要求：</td>
        	<td colspan="4"><textarea name="roomApply.roomRequest" rows="3" cols="35"></textarea></td>
        </tr>-->
        <tr>
        	<td class="title" align="center" rowspan="2" style="text-align:center">出席者情况</td>
        	<td class="title" align="right" id="f_attendance"><font color="red">*</font>&nbsp;出席对象：</td>
        	<td colspan="5"><input type="text" name="roomApply.attendance" size="50" maxlength="50"/></td>
        </tr>
        <tr>
        	<td class="title" align="right" id="f_attendanceNum"><font color="red">*</font>&nbsp;出席人数：</td>
        	<td colspan="5"><input type="text" name="roomApply.attendanceNum" maxlength="5"/>&nbsp;人&nbsp;&nbsp;&nbsp;(填写数字)</td>
        </tr>
        <tr>
        	<td class="title" align="center" rowspan="3" style="text-align:center">借用时间要求</td>
            <td class="title" id="f_begin_end" align="right"><font color="red">*</font>&nbsp;教室使用日期：</td>
            <td colspan="5"><input type="text" name="roomApply.applyTime.dateBegin" onfocus="calendar()" size="10" maxlength="10"/> - <input type="text" name="roomApply.applyTime.dateEnd" onfocus="calendar()" size="10" maxlength="10"/> (年月日 格式2007-10-20)</td>
        </tr>
        <tr>
            <td class="title" id="f_beginTime_endTime" align="right"><font color="red">*</font>&nbsp;教室使用时间：</td>
            <td colspan="5"><input type="text" name="roomApply.applyTime.timeBegin" size="10" class="LabeledInput" value="" format="Time"  maxlength="5"/> - <input type="text" name="roomApply.applyTime.timeEnd" size="10" maxlength="5"/> (时分 格式如09:00 共五位)</td>
        </tr>
        <tr >
            <td class="title" id="f_cycleCount" align="right"><font color="red">*</font>&nbsp;时间周期：</td>
            <td colspan="5">每&nbsp;<input type="text" name="roomApply.applyTime.cycleCount" style="width:20px" maxlength="2"/><@RoomApply.cycleTypeSelect  name="roomApply.applyTime.cycleType" cycleType=(roomApply.applyTime.cycleType)?default(1)/></td>
        </tr>
        <!--<tr>
        	<td class="title" align="center" rowspan="4" style="text-align:center">借用方承诺</td>
            <td colspan="4">(1)遵守学校教室场所使用管理要求，保持环境整洁，不吸烟、不乱抛口香糖等杂物。 </td>
        </tr>         
        <tr><td colspan="4">(2)遵守学校治安管理规定，确保安全使用。若因借用人管理和使用不当造成安全事故，借用人自行承担责任。</td></tr>
        <tr><td colspan="4">(3)遵守学校财产物资规定，损坏设备设施按原值赔偿。</td></tr>-->
        <!--<tr>
          	<td class="title" align="right" id="f_applicant"><font color="red">*</font>&nbsp;申请人签名：</td>
            <td colspan="3"><input type="text"  maxlength="20"/>
                                  填表申请时间：<input type="hidden"  /></td>
        </tr>
        <tr>
        	<td class="title" rowspan="5" style="text-align:center">归口审核</td>
        	<td colspan="4">1.各院系学术讲座、办班等院长或系主任负责审批；</td>
        </tr>
        <tr><td colspan="4">2.各院系学生活动由各院系总支副书记负责审批(学生社团活动除外)；</td></tr>
        <tr><td colspan="4">3.校团委、校学生会、社团联合会以及所有学生社团活动归口团委负责人审批。</td></tr>
        <tr><td colspan="4">4.后勤管理处直接审批(适应于“就业指导中心”等特殊部门)。</td></tr>
        <tr>
        	<td class="title"><font color="red">*</font>&nbsp;归口审核部门：</td>
        	<td colspan="3"><@htm.i18nSelect datas=departments?sort_by("name") selected="" name="roomApply.auditDepart.id"/></td>
        </tr>
        <tr class="darkColumn">
            <td colspan="5" align="center"><button onClick="apply()">申请</button>&nbsp;&nbsp;<button onClick="this.form.reset()">重填</button></td>
        </tr>-->
        <tr>
          <td class="title" align="center" rowspan="4" style="text-align:center">注意事项</td>
        	<td align="left" colspan="5"> 1、请提前4天申请<br>2、教务科安排好教室后，如使用多媒体教室，将此表交至4号楼4105室；如使用普通教室，将此表交至物业办公室（玉川路与融山路“丁”字路口处，过木桥走到底，1号楼与2号楼之间小房子）</td>
        </tr>
        </form>
    </table>
<script>
        var bar = new ToolBar("bar", "添加申请", null, true, true);
        bar.setMessage('<@getMessage/>');
        
        bar.addItem("申请", "apply()");
        
        var form = document.roomApplyForm;
        function apply() {
            var a_fields = {
            	'roomApply.borrower.addr':{'l':'借用人地址', 'r':true, 't':'f_addr'},
            	'roomApply.borrower.mobile':{'l':'借用人手机', 'r':true, 't':'f_mobile','f':'unsigned'},
            	'roomApply.borrower.email':{'l':'借用人E-mail', 'r':true, 't':'f_email', 'f':'email'},
            	'roomApply.activityName':{'l':'活动名称', 'r':true, 't':'f_activityName'},
            	'roomApply.activityType.id':{'l':'活动类型','r':true, 't':'f_activityType'},
            	'roomApply.isFree':{'l':'是否具有营利性', 'r':true, 't':'f_isFree'},
            	'roomApply.leading':{'l':'姓名及资料背景', 'r':true, 't':'f_leading','mx':200},
            	'roomApply.attendance':{'l':'出席对象', 'r':true, 't':'f_attendance'},
            	'roomApply.attendanceNum':{'l':'出席人数', 'r':true, 't':'f_attendanceNum', 'f':'unsigned'},
                'roomApply.applyTime.dateBegin':{'l':'教室使用日期的“起始日期”', 'r':true, 't':'f_begin_end','f':'date'},
                'roomApply.applyTime.dateEnd':{'l':'教室使用日期的“结束日期”', 'r':true, 't':'f_begin_end','f':'date'},
                'roomApply.applyTime.timeBegin':{'l':'教室使用的“开始”时间点', 'r':true, 't':'f_beginTime_endTime','f':'shortTime'},
                'roomApply.applyTime.timeEnd':{'l':'教室使用的“结束”时间点', 'r':true, 't':'f_beginTime_endTime','f':'shortTime'},
                'roomApply.applyTime.cycleCount':{'l':'单位数量', 'r':true, 't':'f_cycleCount', 'f':'positiveInteger'},
                'roomApply.applicant':{'l':'申请人签名', 'r':true, 't':'f_applicant'}
            };
            var v = new validator(form, a_fields, null);
            if (v.exec()) {
                if(form['roomApply.applyTime.dateBegin'].value>form['roomApply.applyTime.dateEnd'].value){
                   alert("借用日期不对");return;
                }
                if(form['roomApply.applyTime.timeBegin'].value>=form['roomApply.applyTime.timeEnd'].value){
                   alert("借用时间不对");return;
                }
	            form.action = "roomApply.do?method=apply";
	            form.target = "";
	            form.submit();
            }
        }
        
        function lookFreeApply() {
            form.action = "roomApply.do?method=lookFreeApply";
            form.target = "_blank";
            form.submit();
        }
    </script>
</BODY>
<#include "/templates/foot.ftl"/>
