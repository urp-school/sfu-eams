<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<body>
    <table id="bar" width="100%"></table>
    <#assign mustBeFullFlag><span style="color:red">*</span></#assign>
    <#assign normalHeight = "25px"/>
    <table class="formTable" width="100%">
        <form method="post" action="" name="actionForm" onsubmit="return false;">
            <input type="hidden" name="calendar.id" value="${(multiRequire.task.calendar.id)?default((task.calendar.id)?if_exists)}"/>
            <input type="hidden" name="calendarId" value="${(multiRequire.task.calendar.id)?default((task.calendar.id)?if_exists)}"/>
            <input type="hidden" name="multiRequire.id" value="${(multiRequire.id)?if_exists}"/>
            <input type="hidden" name="multiRequire.task.id" value="${(multiRequire.task.id)?default((task.id)?if_exists)}"/>
        <tr>
            <td class="darkColumn" colspan="8" style="text-align:center;font-weight:bold"><@i18nName systemConfig.school/>多媒体教学任务需求信息征询表</td>
        </tr>
        <tr height="${normalHeight}">
            <td class="title">课程类别名称：</td>
            <td>${(multiRequire.task.courseType.name)?default((task.courseType.name)?if_exists)}</td>
            <td class="title">学年度学期：</td>
            <td colspan="5">${(multiRequire.task.calendar.year + " " + multiRequire.task.calendar.term)?default((task.calendar.year + " " + task.calendar.term)?if_exists)}</td>
        </tr>
        <tr height="${normalHeight}">
            <td class="title" width="13%">课程名称：</td>
            <td width="15%">${(multiRequire.task.course.name)?default((task.course.name)?if_exists)}</td>
            <td class="title" width="12%">教学班级名称：</td>
            <td width="20%"><#list ((multiRequire.task.teachClass.adminClasses)?default((task.teachClass.adminClasses)?if_exists)?sort_by("name"))?if_exists as adminClass>${adminClass.name?html}<#if adminClass_has_next>，</#if></#list></td>
            <td class="title" width="10%">专业：</td>
            <td width="12%">${(multiRequire.task.teachClass.speciality.name)?default((task.teachClass.speciality.name)?if_exists)}</td>
            <td class="title" width="10%">实际人数：</td>
            <td>${((multiRequire.task.teachClass.courseTakes)?default(task.teachClass.courseTakes)?size)?default(0)}</td>
        </tr>
        <tr height="${normalHeight}">
            <td class="title">课程代码：</td>
            <td>${(multiRequire.task.course.code)?default((task.course.code)?if_exists)}</td>
            <td class="title">开课院系：</td>
            <td>${(multiRequire.task.arrangeInfo.teachDepart.name)?default((task.arrangeInfo.teachDepart.name)?if_exists)}</td>
            <td class="title">授课教师：</td>
            <td>${(multiRequire.task.arrangeInfo.teacherNames)?default((task.arrangeInfo.teacherNames)?if_exists)}</td>
            <td class="title">学生类别：</td>
            <td>${(multiRequire.task.teachClass.stdType.name)?default((task.teachClass.stdType.name)?if_exists)}</td>
        </tr>
        <tr height="${normalHeight}">
            <td class="title">实际安排：</td>
            <#assign realArrangeInfo>${(multiRequire.task.arrangeInfo.digest(multiRequire.task.calendar,Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],":teacher2:day:units节 :weeks周"))?default((task.arrangeInfo.digest(task.calendar, Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],":day :units节 :weeks周"))?if_exists)}</#assign>
            <td colspan="7">${(realArrangeInfo?replace("<br>", ",")?replace(" ,", "，")?trim)?if_exists}</td>
        </tr>
        <tr height="${normalHeight}">
            <td class="title">总课时：</td>
            <td colspan="7">${(multiRequire.task.arrangeInfo.overallUnits)?default((task.arrangeInfo.overallUnits)?if_exists)}</td>
        </tr>
        <tr>
            <td class="title" id="f_addressRequirement">授课地点：</td>
            <#assign realArrangeInfo>${(task.arrangeInfo.digest(task.calendar, Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],":room"))?if_exists}</#assign>
            <#assign realArrangeInfoDisplay>${(multiRequire.addressRequirement?html)?default((realArrangeInfo?replace("<br>", ",")?replace(" ,", "，")?trim)?if_exists)}</#assign>
            <td colspan="4" style="border-right-width:0px">${realArrangeInfoDisplay?default("<span style=\"color:red\">没安排教室</span>")}<input type="hidden" name="multiRequire.addressRequirement" value="${realArrangeInfoDisplay?default("没安排教室")}" maxlength="100" style="width:100%"/></td>
            <td colspan="3">请填写需要使用的多媒体教室</td>
        </tr>
        <tr>
            <td class="title" id="f_environmentRequirement">${mustBeFullFlag}上课所需教学　<br>软件及环境要求：</td>
            <td colspan="4" style="border-right-width:0px"><textarea name="multiRequire.environmentRequirement" style="width:100%;height:200px">${(multiRequire.environmentRequirement?html)?if_exists}</textarea></td>
            <td colspan="3"><a href="#" onclick="fileDown()">多媒体现有软件（请点击）</a></td>
        </tr>
        <tr>
            <td class="darkColumn" colspan="8" style="text-align:center"><button onclick="save()">保存</button></td>
        </tr>
        </form>
    </table>
    <script>
        var bar = new ToolBar("bar", "<#if (multiRequire.id)?exists>维护<#else><span style=\"color:blue\">第二步：</span>详细（共2步）</#if>配置教学任务的教室要求", null, true, true);
        bar.addItem("返回列表", "toBack()", "backward.gif");
        
        var form = document.actionForm;
        
        function save() {
            var a_fields = {
                'multiRequire.environmentRequirement':{'l':"“上课所需教学软件及环境要求”", 'r':true, 't':'f_environmentRequirement', 'mx':'250'}
            };
            var v = new validator(form, a_fields, null);
            if (v.exec()) {
                form.action = "multimediaRequirement.do?method=save";
                form.target = "_self";
                form.submit();
            }
        }
        
        
        function fileDown() {
            form.action = "dataTemplate.do?method=download&document.id=16";
            form.target = "_self";
            form.submit();
        }
        
        function toBack() {
            form.action = "multimediaRequirement.do?method=search";
            form.target = "_self";
            form.submit();
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>
