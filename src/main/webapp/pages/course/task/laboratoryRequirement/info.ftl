<#include "/templates/head.ftl"/>
<body>
    <table id="bar" width="100%"></table>
    <#assign normalHeight = "35px"/>
    <div style="text-align:center;font-weight:bold;font-family:华文中宋,宋体;font-size:20pt"><@i18nName systemConfig.school/>实验教学任务需求信息征询表</div>
    <div style="height:10px"></div>
    <table class="listTable" align="center" style="font-size:9pt">
        <tr height="${normalHeight}">
            <td>课程类别名称</td>
            <td>${labRequire.task.courseType.name}</td>
            <td>学年度学期</td>
            <td colspan="5">${labRequire.task.calendar.year + " " + labRequire.task.calendar.term}</td>
        </tr>
        <tr height="${normalHeight}">
            <td style="width:20mm">课程名称</td>
            <td style="width:33mm">${labRequire.task.course.name}</td>
            <td style="width:22mm">教学班级名称</td>
            <td style="width:30mm"><#list labRequire.task.teachClass.adminClasses?sort_by("name")?if_exists as adminClass>${adminClass.name?html}<#if adminClass_has_next><br></#if></#list></td>
            <td style="width:17mm">专业</td>
            <td style="width:20mm">${(labRequire.task.teachClass.speciality.name)?if_exists}</td>
            <td style="width:20mm">实际人数</td>
            <td style="width:13mm">${labRequire.task.teachClass.courseTakes?size}</td>
        </tr>
        <tr height="${normalHeight}">
            <td>课程代码</td>
            <td>${labRequire.task.course.code}</td>
            <td>开课院系</td>
            <td>${labRequire.task.arrangeInfo.teachDepart.name}</td>
            <td>授课教师</td>
            <td>${labRequire.task.arrangeInfo.teacherNames}</td>
            <td>学生类别</td>
            <td>${labRequire.task.teachClass.stdType.name}</td>
        </tr>
        <tr height="${normalHeight}">
            <td>实际安排</td>
            <#assign realArrangeInfo>${labRequire.task.arrangeInfo.digest(labRequire.task.calendar,Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],":teacher2:day:units节 :weeks周 :room")}</#assign>
            <td colspan="7">${(realArrangeInfo?replace("<br>", ",")?replace(" ,", "，")?trim)?if_exists}</td>
        </tr>
        <tr height="${normalHeight}">
            <td>总课时</td>
            <td>${labRequire.task.arrangeInfo.overallUnits}</td>
            <td>计划实验总学时</td>
            <td>${labRequire.overallUnit}</td>
            <td colspan="2">实验占总成绩的比例</td>
            <td colspan="2">${labRequire.propExperimental}</td>
        </tr>
        <tr height="${normalHeight}">
            <td>实验地点</td>
           	<td colspan="7">${labRequire.classroomType.name}</td>
        </tr>
        <tr height="100px">
            <td>实验上机时间</td>
            <td colspan="7">${labRequire.timeDescrition?html?replace("\r", "<br>")}</td>
        </tr>
        <tr height="200px">
            <td>实验所需软件<br>及环境要求</td>
            <td colspan="7">${labRequire.experimentRequirement?html?replace("\r", "<br>")}</td>
        </tr>
        <tr height="300px">
            <td>实验项目安排</td>
            <td colspan="7">${labRequire.projectDescrition?html?html?replace("\r", "<br>")}</td>
        </tr>
    </table>
    <div style="height:50px"></div>
    <center>
        <div style="width:180mm">
            <table align="right" style="text-align:right;" cellpadding="0" cellspacing="0">
                <tr>
                    <td>打印人：${loginUser.userName}(${loginUser.name})　打印时间：${nowAt?string("yyyy-MM-dd HH:mm:ss")}</td>
                </tr>
                <tr>
                    <td id="urlTd" colspan="2"></td>
                </tr>
            </table>
        </div>
    </center>
    <script>
        var bar = new ToolBar("bar", "配置教学任务的实验室要求查看", null, true, true);
        bar.addPrint();
        bar.addClose();
        
        document.getElementById("urlTd").innerHTML = location;
    </script>
</body>
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="css/smsx.cab#Version=6,2,433,14"></object> 
<script>
 function window.onload(){
       try{
           if(typeof factory.printing != 'undefined'){ 
             factory.printing.header = ""; 
             factory.printing.footer = "";
             
                 // -- advanced features
                //factory.printing.SetMarginMeasure(2); // measure margins in inches
                //factory.printing.printer = "HP DeskJet 870C";
                factory.printing.paperSize = "A4";
                //factory.printing.paperSource = "Manual feed";
                //factory.printing.collate = true;
                //factory.printing.copies = 2;
                //factory.printing.SetPageRange(false, 1, 3); // need pages from 1 to 3
               
                // -- basic features
                //factory.printing.header = "This is MeadCo";
                //factory.printing.footer = "Printing by ScriptX";
                //factory.printing.portrait = true;
                factory.printing.leftMargin = 5.0;
                factory.printing.topMargin = 5.0;
                factory.printing.rightMargin = 5.0;
                factory.printing.bottomMargin = 5.0;
           }
       }catch(e){
           
       }
    }
</script>
<#include "/templates/foot.ftl"/>