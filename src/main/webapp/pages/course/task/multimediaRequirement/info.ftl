<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<body>
    <table id="bar" width="100%"></table>
    <div style="text-align:center;font-weight:bold;font-size:20px;font-family:华文中宋,宋体"><@i18nName systemConfig.school/>多媒体教学任务需求信息征询表</div>
    <div style="height:20px"></div>
    <#assign normalHeight = "35px"/>
    <table class="listTable" style="font-size:10pt" align="center">
        <tr height="${normalHeight}">
            <td>课程类别名称</td>
            <td>${multiRequire.task.courseType.name}</td>
            <td>学年度学期</td>
            <td colspan="5">${multiRequire.task.calendar.year + " " + multiRequire.task.calendar.term}</td>
        </tr>
        <tr height="${normalHeight}">
            <td style="width:26mm">课程名称</td>
            <td style="width:40mm">${multiRequire.task.course.name}</td>
            <td style="width:22mm">教学班级名称</td>
            <td style="width:34mm"><#list multiRequire.task.teachClass.adminClasses?sort_by("name") as adminClass>${adminClass.name?html}<#if adminClass_has_next><br></#if></#list></td>
            <td style="width:20mm">专业</td>
            <td style="width:20mm">${(multiRequire.task.teachClass.speciality.name)?if_exists}</td>
            <td style="width:20mm">实际人数</td>
            <td style="width:13mm">${multiRequire.task.teachClass.courseTakes?size}</td>
        </tr>
        <tr height="${normalHeight}">
            <td>课程代码</td>
            <td>${multiRequire.task.course.code}</td>
            <td>开课院系</td>
            <td>${multiRequire.task.arrangeInfo.teachDepart.name}</td>
            <td>授课教师</td>
            <td>${multiRequire.task.arrangeInfo.teacherNames}</td>
            <td>学生类别</td>
            <td>${multiRequire.task.teachClass.stdType.name}</td>
        </tr>
        <tr height="${normalHeight}">
            <td>实际安排</td>
            <td colspan="7">${multiRequire.task.arrangeInfo.digest(multiRequire.task.calendar,Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],":teacher2:day:units节 :weeks周")}</td>
        </tr>
        <tr height="${normalHeight}">
            <td>总课时</td>
            <td colspan="7">${multiRequire.task.arrangeInfo.overallUnits}</td>
        </tr>
        <tr height="${normalHeight}">
            <td>授课地点</td>
            <td colspan="7">${multiRequire.addressRequirement?html}</td>
        </tr>
        <tr height="200px">
            <td>上课所需教学<br>软件及环境要求</td>
            <td colspan="7">${(multiRequire.environmentRequirement?replace("\r", "<br>"))?if_exists}</td>
        </tr>
        </form>
    </table>
    <div style="height:50px"></div>
    <center>
        <div style="width:200mm">
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
        var bar = new ToolBar("bar", "教学任务的教室要求配置查看<span style=\"color:blue\">（打印页边距请均设5毫米）</span>", null, true, true);
        bar.addPrint("<@msg.message key="action.print"/>");
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