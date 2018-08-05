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
        <form  method="post" name="roomApplyForm" onsubmit="return false;">
        <#if RequestParameters['method']?default('editApply') != "copyApply">
		    <input name="roomApply.id" type="hidden" value="${roomApply.id}"/>
        </#if>
        <tr class="darkColumn"  align="center">
            <td colspan="5"><B>教室借用填写表</B></td>
        </tr>
        <@searchParams/>
        <tr>
        	<td class="title" style="text-align:center; width: 40%" rowspan="2">借用人</td>
        	<td class="title" align="right" id="f_username"><font color="red">*</font>&nbsp;姓名：</td>
        	<td width="25%"><input type="hidden" name="roomApply.borrower.user.id" value="${roomApply.borrower.user.id}"/>${roomApply.borrower.user.userName}</td>
        	<td class="title" align="right" width="5%" id="f_addr"><font color="red">*</font>&nbsp;地址：</td>
        	<td width="25%"><input type="text" name="roomApply.borrower.addr" size="15" maxlength="200" value="${(roomApply.borrower.addr)?default('')}"/></td>
        </tr>
        <tr>
        	<td class="title" align="right" id="f_mobile"><font color="red">*</font>&nbsp;手机：</td>
			<td><input type="text" name="roomApply.borrower.mobile" size="15" value="${(roomApply.borrower.mobile)?default('')}" maxlength="20"/></td>
			<td class="title" align="right" id="f_email"><font color="red">*</font>&nbsp;E-mail：</td>
			<td><input type="text" name="roomApply.borrower.email" value="${(roomApply.borrower.email)?default('')}" size="15" maxlength="50"/></td>
        </tr>
        <tr>
        	<td class="title" style="text-align:center" rowspan="3">借用用途、性质</td>
        	<td class="title" align="right" id="f_activityName"><font color="red">*</font>&nbsp;活动名称：</td>
        	<td colspan	="3"><input type="text" name="roomApply.activityName" value="${(roomApply.activityName)?default('')}" size="50" maxlength="50"/></td>
        </tr>
        <tr>
        	<td class="title" align="right" id="f_activityType"><font color="red">*</font>&nbsp;活动类型：</td>
        	<td colspan="3">
        		<#list activityTypes as activityType>
        			<input type="radio" name="roomApply.activityType.id" <#if 0=activityType_index>checked<#elseif activityType.id == roomApply.activityType.id>checked</#if> value="${activityType.id}"/>${activityType.name}&nbsp;&nbsp;
        		</#list>
        	</td>
        </tr>
        <tr>
        	<td class="title" align="right" id="f_isFree"><font color="red">*</font>&nbsp;是否具有营利性：</td>
        	<td colspan="3">
        		<input type="radio" name="roomApply.isFree" value="0" <#if roomApply.isFree?string("true", "false") == "false">checked</#if>/>&nbsp;营利性&nbsp;
        		<input type="radio" name="roomApply.isFree" value="1" <#if roomApply.isFree?string("true", "false") == "true">checked</#if>/>&nbsp;非营利性
        	</td>
        </tr>
        <tr>
        	<td class="title" style="text-align:center" rowspan="2">主讲人</td>
        	<td colspan="4" id="f_leading"><font color="red">*</font>&nbsp;姓名及背景资料：</td>
        </tr>
        <tr>
        	<td colspan="4"><textarea name="roomApply.leading" rows="3" cols="50">${(roomApply.leading)?default('')}</textarea></td>
        </tr>
        <tr>
        	<td class="title" style="text-align:center" rowspan="2">出席者情况</td>
        	<td class="title" align="right" id="f_attendance"><font color="red">*</font>&nbsp;出席对象：</td>
        	<td colspan="3"><input type="text" name="roomApply.attendance" size="50" value="${(roomApply.attendance)?default('')}" maxlength="50"/></td>
        </tr>
        <tr>
        	<td class="title" align="right" id="f_attendanceNum"><font color="red">*</font>&nbsp;出席人数：</td>
        	<td colspan="3"><input type="text" name="roomApply.attendanceNum" value="${(roomApply.attendanceNum)?default('0')}" maxlength="5"/>&nbsp;人&nbsp;&nbsp;&nbsp;(填写数字)</td>
        </tr>
        <tr>
        	<td class="title" style="text-align:center"id="f_roomRequest" rowspan="3">&nbsp;借用场所要求</td>
        	<td class="title" align="right" id="f_isFree" style="width:100%"><font color="red">*</font>&nbsp;是否使用多媒体设备：</td>
        	<td colspan="3">
        		<input type="radio" name="roomApply.isMultimedia" value="1"<#if (roomApply.isMultimedia)?exists == false || (roomApply.isMultimedia)?exists && roomApply.isMultimedia?string("true", "false") == "true"> checked</#if>/>&nbsp;使用&nbsp;
        		<input type="radio" name="roomApply.isMultimedia" value="0"<#if (roomApply.isMultimedia)?exists && roomApply.isMultimedia?string("true", "false") == "false"> checked</#if>/>&nbsp;不使用
        	</td>
        </tr>
        <tr>
        	<td class="title"><font color="red">*</font>&nbsp;借用校区：</td>
        	<td colspan="3"><@htm.i18nSelect datas=schoolDistricts selected=(roomApply.schoolDistrict.id?string)?default("") name="roomApply.schoolDistrict.id" style="width:200px"/></td>
        </tr>
        <tr>
        	<td class="title">其它要求：</td>
        	<td colspan="3"><textarea name="roomApply.roomRequest" rows="3" cols="35">${(roomApply.roomRequest)?default('')}</textarea></td>
        </tr>
        <tr>
        	<td class="title" style="text-align:center" rowspan="3">借用时间要求</td>
            <td class="title" id="f_begin_end" align="right"><font color="red">*</font>&nbsp;教室使用日期：</td>
            <td colspan="3"><input type="text" name="roomApply.applyTime.dateBegin" onfocus="calendar()" value="${(roomApply.applyTime.dateBegin?string('yyyy-MM-dd'))?default('')}" size="10"/> - <input type="text" name="roomApply.applyTime.dateEnd" onfocus="calendar()" size="10" value="${(roomApply.applyTime.dateEnd?string('yyyy-MM-dd'))?default('')}" maxlength="10"/> (年月日 格式2007-10-20)</td>
        </tr>
        <tr>
            <td class="title" id="f_beginTime_endTime" align="right"><font color="red">*</font>&nbsp;教室使用时间：</td>
            <td colspan="3"><input type="text" name="roomApply.applyTime.timeBegin" size="10" class="LabeledInput" maxlength="5" value="${(roomApply.applyTime.timeBegin)?default('')}" format="Time"/> - <input type="text" name="roomApply.applyTime.timeEnd" size="10" value="${(roomApply.applyTime.timeEnd)?default('')}" maxlength="5"/> (时分 格式如09:00 共五位)</td>
        </tr>
        <tr >
            <td class="title" id="f_cycleCount" align="right"><font color="red">*</font>&nbsp;时间周期：</td>
            <td colspan="3">每&nbsp;<input type="text" name="roomApply.applyTime.cycleCount" style="width:20px" maxlength="2" value="${(roomApply.applyTime.cycleCount)?default('0')}"/>
                <@RoomApply.cycleTypeSelect  name="roomApply.applyTime.cycleType" cycleType=(roomApply.applyTime.cycleType)?default(1)/>
            </td>
        </tr>
        <tr>
        	<td class="title" style="text-align:center" rowspan="4">借用方承诺</td>
            <td colspan="4">(1)遵守学校教室场所使用管理要求，保持环境整洁，不吸烟，、不乱抛口香糖等杂物。 </td>
        </tr>         
        <tr><td colspan="4">(2)遵守学校治安管理规定，确保安全使用。若因借用人管理和使用不当造成安全事故，借用人自行承担责任。</td></tr>
        <tr><td colspan="4">(3)遵守学校财产物资规定，损坏设备设施按原值赔偿。</td></tr>
        <tr>
          	<td class="title" align="right" id="f_applicant"><font color="red">*</font>&nbsp;申请人签名：</td>
            <td colspan="3"><input type="text" name="roomApply.applicant" value="${roomApply.applicant}"/>
                                  填表申请时间：${applyAt}<input type="hidden" name="roomApply.applyAt" value="${applyAt}"/></td>
        </tr>
        <tr>
        	<td class="title" style="text-align:center" rowspan="5">归口审核</td>
        	<td colspan="4">1.各院系学术讲座、办班等院长或系主任负责审批；</td>
        </tr>
        <tr><td colspan="4">2.各院系学生活动由各院系总支副书记负责审批(学生社团活动除外)；</td></tr>
        <tr><td colspan="4">3.校团委、校学生会、社团联合会以及所有学生社团活动归口团委审批。</td></tr>
        <tr><td colspan="4">4.后勤管理处直接审批(适应于“就业指导中心”等特殊部门)。</td></tr>
        <tr>
        	<td class="title"><font color="red">*</font>&nbsp;归口审核部门：</td>
        	<td colspan="3"><@htm.i18nSelect datas=departments?sort_by("code") selected=(roomApply.auditDepart.id?string)?default("") name="roomApply.auditDepart.id"/></td>
        </tr>
        <tr class="darkColumn">
            <td colspan="5" align="center"><button onClick="editApply()"><@msg.message key="action.edit"/></button>&nbsp;&nbsp;<button onClick="this.form.reset()">重填</button></td>
        </tr>
        </form>
    </table>
    <br><br><br><br><br><br><br><br><br><br>
    <script>
        var bar = new ToolBar("bar", "<@msg.message key="action.edit"/>申请", null, true, true);
        bar.setMessage('<@getMessage/>');
        
        bar.addItem("<@msg.message key="action.edit"/>", "editApply()");
        bar.addBack("<@msg.message key="action.back"/>");
        
        var form = document.roomApplyForm;
        var action="${requestAction}";
        function editApply() {
			form['roomApply.borrower.addr'].value = form['roomApply.borrower.addr'].value.trim();         	
			form['roomApply.borrower.mobile'].value = form['roomApply.borrower.mobile'].value.trim();         	
			form['roomApply.borrower.email'].value = form['roomApply.borrower.email'].value.trim();         	
			form['roomApply.activityName'].value = form['roomApply.activityName'].value.trim();         	
			form['roomApply.leading'].value = form['roomApply.leading'].value.trim();         	
			form['roomApply.roomRequest'].value = form['roomApply.roomRequest'].value.trim();         	
			form['roomApply.applicant'].value = form['roomApply.applicant'].value.trim();  
			       	
        	var a_fields = {
            	'roomApply.borrower.addr':{'l':'借用人地址', 'r':true, 't':'f_addr'},
            	'roomApply.borrower.mobile':{'l':'借用人手机', 'r':true, 't':'f_mobile', 'f':'unsigned'},
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
                
                var dateBegin = form["roomApply.applyTime.dateBegin"].value;
                var dateEnd = form["roomApply.applyTime.dateEnd"].value;
                var cycleCount = form["roomApply.applyTime.cycleCount"].value;
                
                var beginYear = parseInt(dateBegin.substr(0, 4));
                var beginMonth = parseInt(dateBegin.substr(5, 2));
                var beginDate = parseInt(dateBegin.substr(8, 2));
                var date1 = new Date(beginYear, beginMonth - 1, beginDate);
                var endYear = parseInt(dateEnd.substr(0, 4));
                var endMonth = parseInt(dateEnd.substr(5, 2));
                var endDate = parseInt(dateEnd.substr(8, 2));
                var date2 = new Date(endYear, endMonth - 1, endDate);
                
                if (form["roomApply.applyTime.cycleType"].value == "2") {
                    var tmp = new Date(date1.getFullYear(), date1.getMonth(), date1.getDate() + (7 * cycleCount));
                    if (tmp.getFullYear() > date2.getFullYear() || tmp.getMonth() > date2.getMonth() || tmp.getDate() > date2.getDate()) {
                        alert("借用日期与时间周期不匹配。");
                        return;
                    }
                } else if (form["roomApply.applyTime.cycleType"].value == "4") {
                    var tmp = new Date(date1.getFullYear(), date1.getMonth() + cycleCount, date1.getDate());
                    if (tmp.getFullYear() > date2.getFullYear() || tmp.getMonth() > date2.getMonth() || tmp.getDate() > date2.getDate()) {
                        alert("借用日期与时间周期不匹配。");
                        return;
                    }
                }
                
                <#if RequestParameters['method']?default('editApply') != "copyApply">
			        if ('${(roomApply.isDepartApproved?string('是','否'))?default('否')}' == '是' || '${(roomApply.isApproved?string('是','否'))?default('否')}' == '是') {
			        	if (confirm("修改已审核通过的记录会把原来的审核结果覆盖掉，是否要继续？") == false) {
			        		return;
			        	}
			        }
		        </#if>
	            form.action = action+"?method=apply";
	            form.submit();
            }
        }
        
        function lookFreeApply() {
            window.open(action+"?method=lookFreeApply");
        }
        
        function home() {
        	parent.search();
        }
    </script>
</BODY>
<#include "/templates/foot.ftl"/>
