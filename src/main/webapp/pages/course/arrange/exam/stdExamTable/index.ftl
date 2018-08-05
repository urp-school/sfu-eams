<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<div style="font-size: 15pt;color:red" align="left" >补考安排查询方法：首先，切换到正确的学年学期。如：2010-2011学年第二学期期末考试中不及格的课程的补考安排仍然隶属于2010-2011学年第二学期。</div>
<div style="font-size: 15pt;color:red" align="left" >       其次，切换考试类型为“补考考试”。</div>
<div style="font-size: 15pt;color:black" align="left">2012-2013学年第二学期补考考试中，计算机应用基础（二）（课程代码13330159）为开卷考试，请学生自带课本。</div>
<table id="taskListBar" width="100%"> </table>
<script>
   var bar = new ToolBar("taskListBar","<@msg.message key="info.courseList"/>",null,true,true);
   bar.setMessage('<@getMessage/>');
</script>
   <table class="frameTable_title" width="100%" border="0">
    <form name="calendarForm" action="stdExamTable.do?method=index" method="post">
    <input type="hidden" name="pageNo" value="1" /> 
     <tr style="font-size: 10pt;" align="left">
     <td>&nbsp;</td>
     <td><@msg.message key="common.examType"/>:
           <#assign examTypes=examTypes?sort_by("code")>
           <select name="examType.id" onchange="changeExamType(this.value);">
             <#list examTypes as examType>
             <option value="${examType.id}"><@i18nName examType/></option>	
             </#list>
          </select>
     </td>
     <td>&nbsp;<@bean.message key="attr.yearAndTerm"/></td>
        <#include "/pages/course/calendar.ftl"/>
        </form>
        </tr>
    </table>
    <table width="100%" height="90%" class="frameTable">
       <tr><td valign="top">
	     <iframe  src="stdExamTable.do?method=examTable&calendar.id=${calendar.id}&examType.id=${examTypes?first.id}"
	     id="examTableFrame" name="examTableFrame" scrolling="no"
	     marginwidth="0" marginheight="0" frameborder="0"  height="100%" width="100%">
	     </iframe>
        </td></tr>
    </table>
    <script>
       function changeExamType(examTypeId){
           var form =document.calendarForm;
           form.action="stdExamTable.do?method=examTable&calendar.id=${calendar.id}&examType.id="+examTypeId;
           form.target="examTableFrame";
           form.submit();
       }
    </script>
</body>
<#include "/templates/foot.ftl"/>