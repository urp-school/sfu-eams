<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" style="overflow-x:hidden;overflow-y:hidden;">
  <table  width="100%" id="selectCourseBar"></table>
  <script>
     <#assign labelInfo><@bean.message key="info.task.add.selectCourse"/> [<@i18nName studentType/>、${calendar.year}、${calendar.term}]</B></#assign>
     var bar = new ToolBar("selectCourseBar","${labelInfo}",null,true,true);
     bar.setMessage('<@getMessage/>');
     bar.addItem("添加多个任务","addMultiTask()");
     bar.addItem("<@bean.message key="action.add"/>","add()");
     bar.addBack("<@bean.message key="action.back" />");
  </script>
  <table class="listTable" width="100%">
    <form name="courseSearchForm" action="?method=courseList" target="courseListFrame" method="post" onsubmit="return false;">
    <input type="hidden" name="task.calendar.id" value="${calendar.id}"/>
    <input type="hidden" name="stdType.id" value="${studentType.id}"/>
    <input type="hidden" name="pageNo" value="1"/>
    <input type="hidden" name="pageSize" value="20"/>
    <input type="hidden" name="course.state" value="1"/>
    <input type="hidden" name="course.extInfo.courseType.isPractice" value="${RequestParameters["practiceCourse"]?if_exists}"/>
    <input type="hidden" name="practiceCourse" value="${RequestParameters["practiceCourse"]?if_exists}"/>
    <input type="hidden" name="params" value="${RequestParameters['params']?if_exists}"/>
    <tr align="center" class="darkColumn" >
     <td id="stdTypeId"  >
        <@bean.message key="entity.studentType"/>&nbsp;&nbsp;:
        <select name="course.stdType.id" onchange="courseSearchForm.submit();">
            <option value="">请选择..</option>
        <#list stdTypeList as stdType>
            <option value=${stdType.id}><@i18nName stdType/></option>
        </#list>
        </select>
      </td>
      <td id="id">
        <@bean.message key="attr.code"/>&nbsp;&nbsp;:
        <input type="text" name="course.code" maxlength="32" style="width:100px;"/>
      </td>
      <td  id="name">
        <@bean.message key="attr.infoname"/>&nbsp;&nbsp;:
         <input type="text" name="course.name" maxlength="20" style="width:100px;"/>
   	  </td>
       <td >
        <button onclick="search()"><@bean.message key="action.query"/></button>&nbsp;
       </td>
     </tr>
     </form>
  </table>
     <iframe src="#" id="courseListFrame" name="courseListFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="85%" width="100%"></iframe>
  <script language="javascript">
    var form = document.courseSearchForm;
    function getIds(){
       return(getCheckBoxValue(courseListFrame.document.getElementsByName("courseId")));
    }
    function search(pageNo,pageSize,orderBy){
       goToPage(form,pageNo,pageSize,orderBy);
    }
    function add(){
       var courseId = getRadioValue(courseListFrame.document.getElementsByName('courseId'));
       if(courseId==""){alert("<@msg.message key="common.singleSelectPlease"/>");return;}
       var form = document.courseSearchForm;
       form.action="?method=edit&task.course.id=" + courseId;
       form.target="";
       form.submit();
    }
    function addMultiTask(){
       var courseId = getRadioValue(courseListFrame.document.getElementsByName('courseId'));
       if(courseId==""){alert("<@msg.message key="common.singleSelectPlease"/>");return;}
       var form = document.courseSearchForm;
       form.action="?method=addMultiTask&task.course.id=" + courseId;
       form.target="";
       form.submit();
    }
    search(1,20);
  </script>
  </body>
<#include "/templates/foot.ftl"/>