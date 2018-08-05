<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="manualTable" width="100%"></table>
 <table class="formTable"  width="100%">
   <form name="actionForm" method="post" target="contentListFrame" action="" onsubmit="return false;">
   <tr>
       <input name="taskId" value="${task.id}" type="hidden"/>
       <td class="title">选择要更换的任课教师:<#list task.arrangeInfo.teachers as teacher>
           <input type="radio" name="fromTeacherId" <#if teacher_index==0>checked</#if> value="${teacher.id}"/>${teacher.name}
           </#list>
       </td>
       <td><input type="checkbox" name="allTeacher" checked>全校老师
       教师姓名：<input name="teacher.name" value="" maxlength="20"/> <button onclick="search()"><@msg.message key="action.query"/></button>
       </td>
   </tr>
   </form>
   <tr>
      <td valign="top" colspan="3">
	     <iframe  src="#"
	     id="contentListFrame" name="contentListFrame" 
	     marginwidth="0" marginheight="0" scrolling="no"
	     frameborder="0"  height="100%" width="100%">
	     </iframe>
     </td>
    </tr>
  <table>
 <script>
   var bar = new ToolBar("manualTable","更换老师",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("确定更换","changeTeacher()");
   bar.addBack("<@msg.message key="action.back"/>");
   var form =document.actionForm;
   var action="manualArrange.do";
   
   function search(pageNo,pageSize){
      form.action=action+"?method=freeTeacherList";
      goToPage(form,pageNo,pageSize);
   }
   function changeTeacher(){
       form.action=action+"?method=changeTeacher";
       var ids= getIds();
       if(ids==""){
          alert("请选择老师");return;
       }
       if(confirm("确认要更换成选择的老师？")){
          addInput(form,"toTeacherId",ids);
          setSearchParams(parent.document.taskForm,form);
          form.target="";
          form.submit();
       }
   }
   function getIds(){
     return(getRadioValue(contentListFrame.document.getElementsByName("teacherId")));
   }
   search();
 </script>
</body>
<#include "/templates/foot.ftl"/>