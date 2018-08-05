<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<body>
    <table id="bar" width="100%"></table>
    <#assign mustBeFullFlag><span style="color:red">*</span></#assign>
    <#assign normalHeight = "25px"/>
    <table class="formTable" width="100%">
        <form method="post" action="" name="actionForm" onsubmit="return false;">
            <input type="hidden" name="calendar.id" value="${(labRequire.task.calendar.id)?default((task.calendar.id)?if_exists)}"/>
            <input type="hidden" name="calendarId" value="${(labRequire.task.calendar.id)?default((task.calendar.id)?if_exists)}"/>
            <input type="hidden" name="labRequire.id" value="${(labRequire.id)?if_exists}"/>
            <input type="hidden" name="labRequire.task.id" value="${(labRequire.task.id)?default((task.id)?if_exists)}"/>
        <tr>
            <td class="darkColumn" colspan="8" style="text-align:center;font-weight:bold"><@i18nName systemConfig.school/>实验室查询列表</td>
        </tr>
        <tr height="${normalHeight}">
            <td class="title">课程类别名称：</td>
            <td>${(labRequire.task.courseType.name)?default((task.courseType.name)?if_exists)}</td>
            <td class="title">学年度学期：</td>
            <td colspan="5">${(labRequire.task.calendar.year + " " + labRequire.task.calendar.term)?default((task.calendar.year + " " + task.calendar.term)?if_exists)}</td>
        </tr>
        <tr height="${normalHeight}">
            <td class="title" width="13%">课程名称：</td>
            <td width="13%">${(labRequire.task.course.name)?default((task.course.name)?if_exists)}</td>
            <td class="title" width="15%">教学班级名称：</td>
            <td width="20%"><#list ((labRequire.task.teachClass.adminClasses)?default((task.teachClass.adminClasses)?if_exists)?sort_by("name"))?if_exists as adminClass>${adminClass.name?html}<#if adminClass_has_next>，</#if></#list></td>
            <td class="title" width="10%">专业：</td>
            <td width="12%">${(labRequire.task.teachClass.speciality.name)?default((task.teachClass.speciality.name)?if_exists)}</td>
            <td class="title" width="10%">实际人数：</td>
            <td>${((labRequire.task.teachClass.courseTakes)?default(task.teachClass.courseTakes)?size)?default(0)}</td>
        </tr>
        <tr height="${normalHeight}">
            <td class="title">课程代码：</td>
            <td>${(labRequire.task.course.code)?default((task.course.code)?if_exists)}</td>
            <td class="title">开课院系：</td>
            <td>${(labRequire.task.arrangeInfo.teachDepart.name)?default((task.arrangeInfo.teachDepart.name)?if_exists)}</td>
            <td class="title">授课教师：</td>
            <td>${(labRequire.task.arrangeInfo.teacherNames)?default((task.arrangeInfo.teacherNames)?if_exists)}</td>
            <td class="title">学生类别：</td>
            <td>${(labRequire.task.teachClass.stdType.name)?default((task.teachClass.stdType.name)?if_exists)}</td>
        </tr>
        <tr height="${normalHeight}">
            <td class="title">实际安排：</td>
            <#assign realArrangeInfo>${(labRequire.task.arrangeInfo.digest(labRequire.task.calendar,Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],":teacher2:day:units节 :weeks周 :room"))?default((task.arrangeInfo.digest(task.calendar, Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],":day :units节 :weeks周 :room"))?if_exists)}</#assign>
            <td colspan="7">${(realArrangeInfo?replace("<br>", ",")?replace(" ,", "，")?trim)?if_exists}</td>
        </tr>
        <tr height="${normalHeight}">
            <td class="title">总课时：</td>
            <td colspan="1">${(labRequire.task.arrangeInfo.overallUnit)?default((task.arrangeInfo.overallUnits)?if_exists)}</td>
            <td class="title" id="f_overallUnit">${mustBeFullFlag}计划实验总学时：</td>
            <td colspan="1"><input type="text" name="labRequire.overallUnit" value="${(labRequire.overallUnit)?if_exists}" maxlength="3" style="width:100%"/></td>
            <td class="title" colspan="2" id="f_propExperimental">${mustBeFullFlag}实验占总成绩的比例：</td>
            <td colspan="2"><input type="text" name="labRequire.propExperimental" value="${(labRequire.propExperimental)?if_exists}" maxlength="10" style="width:100%"/></td>
        </tr>
        <tr>
            <td class="title" id="f_addressDescription">${mustBeFullFlag}实验地点：</td>
           	<td colspan="2" style="border-right-width:0px">
	        	<select name="labRequire.classroomType.id" style="width:100%"> 
	        		<option value="" selected>请选择...</option>
	        		<#list classroomTypes as classroomType>
	        		<option value="${classroomType.id}">${classroomType.name}</option>
	        		</#list>
	        	</select>
		    </td>
		    <td colspan="5"></td>
        </tr>
        <tr>
            <td class="title" id="f_timeDescrition">${mustBeFullFlag}实验上机时间：</td>
            <td colspan="3" style="border-right-width:0px"><textarea name="labRequire.timeDescrition" style="width:100%;height:200px">${(labRequire.timeDescrition?html)?if_exists}</textarea></td>
            <td colspan="4">例：第4周，第4.5.6.周。</td>
        </tr>
        <tr>
            <td class="title" id="f_experimentRequirement">${mustBeFullFlag}实验所需软件　<br>及环境要求：</td>
            <td colspan="3" style="border-right-width:0px"><textarea name="labRequire.experimentRequirement" style="width:100%;height:200px">${(labRequire.experimentRequirement?html)?if_exists}</textarea></td>
            <td colspan="4"><a href="#" onclick="fileDown()">实验中心现有软件（请点击）</a></td>
        </tr>
        <tr>
            <td class="title" id="f_projectDescrition">${mustBeFullFlag}实验项目安排：</td>
            <td colspan="3" style="border-right-width:0px"><textarea name="labRequire.projectDescrition" style="width:100%;height:200px">${(labRequire.projectDescrition?html)?if_exists}</textarea></td>
            <td colspan="4">例：第4周(3课时)：多元线性回归<br>　　第5周(3课时)：异方差、序列相关<br>　　第6周(3课时)：多重共线性、分布滞后模型</a></td>
        </tr>
        <tr>
            <td class="darkColumn" colspan="8" style="text-align:center"><button onclick="save()">保存</button></td>
        </tr>
        </form>
    </table>
    <script>
        var bar = new ToolBar("bar", "<#if (labRequire.id)?exists>维护<#else><span style=\"color:blue\">第二步：</span>详细（共2步）</#if>配置教学任务的实验室要求", null, true, true);
        bar.addItem("返回列表", "toBack()", "backward.gif");
        
        var form = document.actionForm;
        
        function initData() {
 //     	form["labRequire.classroomType.id"].value = "${(labRequire.classroomType.id)?default((task.requirement.roomConfigType.id)?if_exists)}";
       }
        
        initData();
        
        function save() {
            var a_fields = {
	            'labRequire.overallUnit':{'l':"“计划实验总学时”", 'r':true, 't':'f_overallUnit', 'f':'unsignedReal'},
	            'labRequire.propExperimental':{'l':"“实验占总成绩的比例”", 'r':true, 't':'f_propExperimental'},
	            'labRequire.classroomType.id':{'l':"“实验地点”", 'r':true, 't':'f_addressDescription'},
	            'labRequire.timeDescrition':{'l':"“实验上机时间”", 'r':true, 't':'f_timeDescrition', 'mx':'250'},
	            'labRequire.experimentRequirement':{'l':"“实验所需软件及环境要求”", 'r':true, 't':'f_experimentRequirement', 'mx':'250'},
	            'labRequire.projectDescrition':{'l':"“实验项目安排”", 'r':true, 't':'f_projectDescrition', 'mx':'250'}
            };
            var v = new validator(form, a_fields, null);
            if (v.exec()) {
                form.action = "laboratoryRequirement.do?method=save";
                form.target = "_self";
                form.submit();
            }
        }
        
        
        function fileDown() {
            form.action = "dataTemplate.do?method=download&document.id=17";
            form.target = "_self";
            form.submit();
        }
        
        function toBack() {
            form.action = "laboratoryRequirement.do?method=search";
            form.target = "_self";
            form.submit();
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>