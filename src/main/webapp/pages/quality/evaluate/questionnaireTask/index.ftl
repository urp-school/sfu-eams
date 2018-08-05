<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
    <table id="taskConfirmBar" width="100%"></table>
   <table  class="frameTable_title">
      <tr>
       <td  style="width:50px" >
          <font color="blue"><@bean.message key="action.advancedQuery"/></font>
       </td>
       <td>|</td>
      <form name="taskForm" target="contentFrame" method="post" action="" onsubmit="return false;">
      <input type="hidden" name="task.calendar.id" value="${calendar.id}" />
      <#include "/pages/course/calendar.ftl"/>
     </tr>
   </table>
   
  <table width="100%"  height="85%" class="frameTable">
    <tr>     
     <td style="width:20%" class="frameTable_view">
        <table id="backBar" width="100%"></table>
        <script>
            var bar = new ToolBar('backBar','查询条件',null,true,true);
            bar.setMessage('<@getMessage/>');
        </script>
   
   <table width="100%" class="searchTable" id="departListTable">
        <tr>
	         <td><@msg.message key="attr.taskNo"/></td><td><input type="text" name="task.seqNo" style="width:100px;"></td></tr><tr>
             <td><@msg.message key="attr.courseNo"/></td><td><input type="text" name="task.course.code" style="width:100px;"></td></tr><tr>
             <td><@msg.message key="attr.courseName"/></td><td><input type="text" name="task.course.name" style="width:100px;"></td></tr><tr>
             <td><@msg.message key="entity.courseType"/></td>
             <td><@htm.i18nSelect datas=courseTypeList?if_exists selected="" name="task.courseType.id" style="width:100px;"><option value="">...</option></@></td>
         </tr>
         <tr>
            <td>开课院系</td>
            <td><@htm.i18nSelect datas=departmentList?if_exists selected="" name="task.arrangeInfo.teachDepart.id" style="width:100px;"><option value="">...</option></@></td>
         </tr>
         <tr>
            <td>有无问卷</td>
            <td><select name="questionnaireEstate" style="width:100px;">
                    <option value="1">有问卷</option>
                    <option value="0">没问卷</option>
                </select></td> 
         </tr>
         <tr>
            <td>课程种类</td>
            <td><@htm.i18nSelect datas=categoryList?if_exists selected="" name="task.course.category.id" style="width:100px;"><option value="">...</option></@></td>
         </tr>
        <tr><td colspan="2" align="center"><button name="button1" onclick="search()" class="buttonStyle">查询</button></td</tr>
       </form>
      </table>
     </td>
     <td valign="top" width="80%">
     <iframe  src="#"
     id="contentFrame" name="contentFrame" 
     marginwidth="0" marginheight="0" scrolling="no"
     frameborder="0"  height="100%" width="100%">
     </iframe>
     </td>
    </tr>
  <table>  
    <script>
    var bar=new ToolBar("taskConfirmBar","指定课程问卷",null,true,true);
    bar.setMessage('<@getMessage/>');
    
    var form = document.taskForm;
    var action="questionnaireTask.do";
    function search(pageNo,pageSize,orderBy){
        form.action=action+"?method=taskList";
        goToPage(form,pageNo,pageSize,orderBy);
    }
    search(1);
</script>
 </body>
<#include "/templates/foot.ftl"/> 