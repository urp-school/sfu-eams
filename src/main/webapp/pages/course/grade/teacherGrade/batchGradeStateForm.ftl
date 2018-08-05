<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="myBar" width="100%"> </table>
 <table  align="center" width="80%" ><tr><td>    
   <table class="settingTable" border="0">
    <tr>
     <td  align="center" colspan="2">你的成绩还没有录入,请你在录入之前声明各种考试成绩的百分比<br>
     <font color=red>百分比为零成绩类型,将不会显示在录入界面上</font><br>
     请保证各种比例的总和为100%(成绩录入暂不支持英文等级制)
     </td>
    </tr>
    <form name="gradeStateForm" method="post" onsubmit="return false;" action="">
    <input type="hidden" name="taskIds" value="${taskIds}"/>
    <tr>
        <td align="right">成绩录入方式:</td>
        <td><@htm.i18nSelect datas=markStyles name="gradeState.markStyle.id" selected="1" style="100px"/>
        </td>
    </tr>
    <tr><td align="center" colspan="2"><button onclick="saveGradeState()">设置录入方式</button></td></tr>
    </form>
   </table>
   </td></tr></table>
<script>
   var bar = new ToolBar("myBar","批量设置成绩录入方式",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addClose();
    var form = document.gradeStateForm;
   function saveGradeState(){
        form.action="teacherGrade.do?method=batchSaveGradeState"
        form.submit();
	}
    </script>
</body>
<#include "/templates/foot.ftl"/>
