<#include "/templates/head.ftl"/>
<#import "../cycleType.ftl" as RoomApply/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<script src='dwr/interface/userDWRService.js'></script>
<style>
input,textarea {
border: 1px solid #333333;
}
</style>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
    <table id="bar"></table>
    <table class="formTable" align="center" width="80%">
        <form action="ecuplRoomApplyApprove.do?method=freeRoomList" method="post" name="roomApplyForm" onsubmit="return false;">
            <input name="roomIds" type="hidden" value="${roomIds}"/>
        <tr class="darkColumn" align="center">
            <td colspan="5"><B>教室借用填写表</B></td>
        </tr>
        <tr>
            <td class="title" align="center" rowspan="2" style="text-align:center">借用人</td>
            <td class="title" align="right" width="18%" id="f_username"><font color="red">*</font>&nbsp;用户帐号：</td>
            <td width="24%"><input type="text" name="userName" value="" maxlength="20" size="15" onblur="checkUser()"/><button id="checkUserButton" onclick="checkUser()">查找</button><label id="borrowerUser" style="color:blue"></label></td>
            <input type="hidden" name="userId" value=""/>
            <td class="title" align="right" width="18%" id="f_addr"><font color="red">*</font>&nbsp;地址：</td>
            <td width="24%"><input type="text" name="roomApply.borrower.addr" size="15" maxlength="200"/></td>
        </tr>
        <tr>
            <td class="title" align="right" id="f_mobile"><font color="red">*</font>&nbsp;手机：</td>
            <td><input type="text" name="roomApply.borrower.mobile" size="15" maxlength="20"/></td>
            <td class="title" align="right" id="f_email"><font color="red">*</font>&nbsp;E-mail：</td>
            <td><input type="text" name="roomApply.borrower.email" value="" size="15" maxlength="70"/></td>
        </tr>
        <tr>
            <td class="title" align="center" rowspan="2" style="text-align:center">借用用途、性质</td>
            <td class="title" align="right" id="f_activityName"><font color="red">*</font>&nbsp;活动名称：</td>
            <td colspan ="3"><input type="text" name="roomApply.activityName" size="50" maxlength="50" value="未命名活动名称"/></td>
        </tr>
        <tr>
            <td class="title" align="right" id="f_activityType"><font color="red">*</font>&nbsp;活动类型：</td>
            <td colspan="3">
                <#list activityTypes as activityType>
                    <input type="radio" name="roomApply.activityType.id" <#if 0=activityType_index>checked</#if> value="${activityType.id}"/>${activityType.name}&nbsp;&nbsp;
                </#list>
            </td>
        </tr>
        <#--非营利性-->
        <input type="hidden" name="roomApply.isFree" value="1" checked/>
        <tr>
            <td class="title" align="center" rowspan="2" style="text-align:center" id="f_leading">主讲人</td>
            <td colspan="4"><font color="red">*</font>&nbsp;姓名及背景资料：</td>
        </tr>
        <tr>
            <td colspan="4"><textarea name="roomApply.leading" rows="3" cols="50">无</textarea></td>
        </tr>
        <tr>
            <td class="title" align="center" rowspan="2" style="text-align:center">出席者情况</td>
            <td class="title" align="right" id="f_attendance"><font color="red">*</font>&nbsp;出席对象：</td>
            <td colspan="3"><input type="text" name="roomApply.attendance" size="50" maxlength="50" value="无"/></td>
        </tr>
        <tr>
            <td class="title" align="right" id="f_attendanceNum"><font color="red">*</font>&nbsp;出席人数：</td>
            <td colspan="3"><input type="text" name="roomApply.attendanceNum" maxlength="5" value="0"/>&nbsp;人&nbsp;&nbsp;&nbsp;(填写数字)</td>
        </tr>
        <#--不使用多媒体设备-->
        <input type="hidden" name="roomApply.isMultimedia" value="0"/>
        <tr>
            <td class="title" style="text-align:center" style="text-align:center">&nbsp;借用场所要求</td>
            <td colspan="4">${classrooms[0].schoolDistrict.name}<input type="hidden" value="${classrooms[0].schoolDistrict.id}" name="roomApply.schoolDistrict.id"/></td>
        </tr>
        <tr>
            <td class="title" align="center" rowspan="3" style="text-align:center">借用时间要求</td>
            <td class="title" id="f_begin_end" align="right"><font color="red">*</font>&nbsp;教室使用日期：</td>
            <td colspan="3"><input type="text" name="roomApply.applyTime.dateBegin" onfocus="calendar()" size="10" maxlength="10" value="${roomApply.applyTime.dateBegin?string("yyyy-MM-dd")}"/> - <input type="text" name="roomApply.applyTime.dateEnd" onfocus="calendar()" size="10" maxlength="10" value="${roomApply.applyTime.dateEnd?string("yyyy-MM-dd")}"/> (年月日 格式2007-10-20)</td>
        </tr>
        <tr>
            <td class="title" id="f_beginTime_endTime" align="right"><font color="red">*</font>&nbsp;教室使用时间：</td>
            <td colspan="3"><input type="text" name="roomApply.applyTime.timeBegin" size="10" class="LabeledInput" value="${roomApply.applyTime.timeBegin}" format="Time"  maxlength="5"/> - <input type="text" name="roomApply.applyTime.timeEnd" size="10" maxlength="5" value="${roomApply.applyTime.timeEnd}"/> (时分 格式如09:00 共五位)</td>
        </tr>
        <tr >
            <td class="title" id="f_cycleCount" align="right"><font color="red">*</font>&nbsp;时间周期：</td>
            <td colspan="3">每&nbsp;<input type="text" name="roomApply.applyTime.cycleCount" style="width:20px" maxlength="2" value="${roomApply.applyTime.cycleCount}"/><@RoomApply.cycleTypeSelect  name="roomApply.applyTime.cycleType" cycleType=roomApply.applyTime.cycleType/></td>
        </tr>
        <input type="hidden" name="roomApply.applyAt" value="${applyAt?string("yyyy-MM-dd HH:mm:ss")}"/>
        <tr>
            <td class="title"" style="text-align:center">归口审核</td>
            <td colspan="4"><@htm.i18nSelect datas=departments?sort_by("name") selected="" name="roomApply.auditDepart.id"/></td>
        </tr>
        <tr class="darkColumn">
            <td colspan="5" align="center"><button id="applyButton" onClick="apply()" disabled>申请</button>&nbsp;&nbsp;<button onClick="this.form.reset()">重填</button></td>
        </tr>
        </form>
    </table>
    <script>
        var bar = new ToolBar("bar", "快速申请教室借用", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addBlankItem();
        
        var form = document.roomApplyForm;
        
        function checkUser() {
            $("checkUserButton").disabled = "disabled";
            $("borrowerUser").style.color = "red";
            $("borrowerUser").innerHTML = "<br>正在查询中，请稍候...";
            userDWRService.getEamsUser(processResult, form["userName"].value);
        }
        
        function processResult(data) {
            if (null == data || "" == data) {
                $("applyButton").disabled = "disabled";
                form["userId"].value = "";
                $("borrowerUser").innerHTML = "";
                form["roomApply.borrower.mobile"].value = "";
                form["roomApply.borrower.addr"].value = "";
                form["roomApply.borrower.email"].value = "";
                $("message").innerHTML = "请输入有效的借用人登录帐号。";
                $("message").style.display = "block";
	            $("checkUserButton").disabled = "";
	            form["userName"].focus();
                return;
            }
            $("message").style.display = "none";
            $("message").innerHTML = "";
            form["userId"].value = data.id;
            $("borrowerUser").style.color = "blue";
            $("borrowerUser").innerHTML = "<br>" + data.userName;
            form["roomApply.borrower.mobile"].value = "0";
            form["roomApply.borrower.addr"].value = "无";
            form["roomApply.borrower.email"].value = data.email;
            $("applyButton").disabled = "";
	        $("checkUserButton").disabled = "";
	        adaptFrameSize();
        }
        
        function apply() {
            var userId = form["userId"].value;
            if (null == userId || "" == userId) {
                alert("请输入有效的借用人登录帐号。");
                return;
            }
            var a_fields = {
                'roomApply.borrower.addr':{'l':'借用人地址', 'r':true, 't':'f_addr'},
                'roomApply.borrower.mobile':{'l':'借用人手机', 'r':true, 't':'f_mobile','f':'unsigned'},
                'roomApply.borrower.email':{'l':'借用人E-mail', 'r':true, 't':'f_email', 'f':'email'},
                'roomApply.activityName':{'l':'活动名称', 'r':true, 't':'f_activityName'},
                'roomApply.activityType.id':{'l':'活动类型','r':true, 't':'f_activityType'},
                'roomApply.leading':{'l':'姓名及资料背景', 'r':true, 't':'f_leading','mx':200},
                'roomApply.attendance':{'l':'出席对象', 'r':true, 't':'f_attendance'},
                'roomApply.attendanceNum':{'l':'出席人数', 'r':true, 't':'f_attendanceNum', 'f':'unsigned'},
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
                
                form["roomApply.applyTime.dateBegin"].value += " " + form["roomApply.applyTime.timeBegin"].value + ":00.000000";
                form["roomApply.applyTime.dateEnd"].value += " " + form["roomApply.applyTime.timeEnd"].value + ":00.000000";
                form.action = "ecuplRoomApplyApprove.do?method=quickApply";
                form.target = "";
                form.submit();
            }
        }
        
        adaptFrameSize();
    </script>
</BODY>
<#include "/templates/foot.ftl"/>
