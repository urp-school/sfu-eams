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
    <input type="hidden" name="taskId" value="${task.id}"/>
    <tr><td align="right"><@msg.message key="attr.taskNo"/>:</td><td>${task.seqNo}</td></tr>
    <tr><td align="right"><@msg.message key="attr.courseName"/>:</td><td><@i18nName task.course/></td></tr>
    <tr>
        <td align="right">成绩录入方式:</td>
        <td><@htm.i18nSelect datas=markStyles selected=task.gradeState.markStyle.id?string 
        name="gradeState.markStyle.id" style="100px"/>
        </td>
    </tr>
    <tr>
        <td align="right"><@msg.message key="grade.scorePrecision"/>:</td>
      	<td>
           <select name="precision">
 	          <option value="0" <#if task.gradeState.precision=0>selected</#if>><@msg.message key="grade.precision0"/></option>
           <#--   <option value="1" <#if task.gradeState.precision=1>selected</#if>><@msg.message key="grade.precision1"/></option> -->
 	 	   </select>
 	    </td>
    </tr>
    <#list gradeTypes?if_exists?sort_by("priority") as gradeType>
    <tr>
     <td align="right"><@i18nName gradeType/> <@msg.message key="grade.percent"/>:</td>
     <td width="50%"><input name="percent${gradeType.id}" style="width:50px" maxlength="7" value="${task.gradeState.getPercent(gradeType)?default(0)*100}"/>%
       &nbsp;<@msg.message key="grade.percentFormat"/></td>
    </tr>
    </#list>
    <tr><td align="center" colspan="2"><button onclick="saveGradeState()"><@msg.message key="grade.submitStateAndInput"/></button></td></tr>
    </form>
   </table>
   </td></tr></table>
<script>
   var bar = new ToolBar("myBar","设置录入成绩百分比",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addClose();
   function saveGradeState(){
	    var form = document.gradeStateForm;
	    form.action="teacherGrade.do?method=saveGradeState"
	    var percent=0;
	    <#list gradeTypes?if_exists?sort_by("priority") as gradeType>
	    if(!/^\d+$/.test(form['percent${gradeType.id}'].value)){
	      alert(form['percent${gradeType.id}'].value +"  不符合要求.");
	      return false;
	    }
	    percent+=parseInt(form['percent${gradeType.id}'].value);
	    </#list>
	    if(percent!=100){
	       alert("你输入的成绩百分比之和不是100,是:"+percent+"\n请检查.");
           return false;
	    }else{
	       form.submit();
	    }
	}
    </script>
</body>
<#include "/templates/foot.ftl"/>
