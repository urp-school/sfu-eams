<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0"> 

 <form name="courseGradeForm" method="post" action="stdGradeDuplicate.do?method=save" onsubmit="return false;">
 <input type="hidden" name="courseGrade.id" value="${courseGrade.id}"/>
 <@searchParams/>
 <table id="gradeBar" width="100%"></table>
 <#include "../stdGradeFormTable.ftl"/>
 </form>
 
 <table id="gradeAlterInfoBar" width="100%"></table>
 <#include "../courseGradeAlterInfo.ftl"/>
 <script>
    var bar1 = new ToolBar("gradeBar","学生单科成绩信息（实践）",null,true,true);
    bar1.addItem("<@msg.message key="action.save"/>","save()","save.gif");
    bar1.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
    var bar2 = new ToolBar("gradeAlterInfoBar","成绩修改信息",null,true,true);
    bar2.addHelp("<@msg.message key="action.help"/>");
    
    function save(){
       if(confirm("是否提交修改的成绩?")) {
          courseGradeForm.submit();
       }
    }
 </script>
</body>
<#include "/templates/foot.ftl"/>