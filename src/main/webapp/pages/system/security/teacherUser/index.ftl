<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="teacherUserBar"></table>
   <table  class="frameTable">
   <tr>
    <td style="width:160px"  class="frameTable_view"><#include "searchForm.ftl"/></td>
    <td valign="top">
	  <iframe  src="#" id="contentFrame" name="contentFrame" 
	     marginwidth="0" marginheight="0" scrolling="no"
	     frameborder="0"  height="100%" width="100%">
	  </iframe>
    </td>
   </tr>
  </table>
 <script>
    var form = document.teacherUserSearchForm;
    var action="teacherUser.do";
    function getTeacherNos(){
       return(getCheckBoxValue(contentFrame.document.getElementsByName("teacherNo")));
    }
    function searchTeacherUser(pageNo,pageSize,orderBy){
       form.action=action+"?method=search";
       goToPage(form,pageNo,pageSize,orderBy);
    }
    function activateUser(isActivate){
       var teacherNos = getTeacherNos();
       if(teacherNos =="") {
         window.alert('<@bean.message key="common.select"/>!');
         return;
       }
       form.action = action+"?method=activate&teacherNos="+teacherNos+"&isActivate="+isActivate;
       addParamsInput(form,contentFrame.queryStr);
       form.submit();
    }
    function addTeacherUser(){
        var teacherNos=getTeacherNos();
        if(teacherNos==""){
             window.alert("<@bean.message key="info.teacher.select"/>");
             return;
        }
        form.action=action+"?method=add&teacherNos="+teacherNos;
        addParamsInput(form,contentFrame.queryStr);
        form.submit();
    }
    
    function modifyTeacherUser(){
       var teacherNos = getTeacherNos();
       if(teacherNos =="") {
         window.alert('请选择一个拥有账户的教师!');
         return;
       }
       if(isMultiId(teacherNos)){
         alert("请仅选择一个");return;
       }
       form.action=action+"?method=edit&teacherNo="+teacherNos;
       addParamsInput(form,contentFrame.queryStr);
       form.submit();
    }
    function promptToManager(){
       var teacherNos = getTeacherNos();
       if(teacherNos =="") {
         window.alert('请选择一个拥有账户的教师!');
         return;
       }
       form.action = action+"?method=promptToManager&teacherNos="+teacherNos;
       addParamsInput(form,contentFrame.queryStr);
       form.submit();
    }
   searchTeacherUser();
   var bar = new ToolBar('teacherUserBar','<@bean.message key="info.userList.teacher"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("升级为管理人员",promptToManager,'update.gif');
   bar.addItem("新建",addTeacherUser,'newUser.gif');
   bar.addItem("<@msg.message key="action.edit"/>",modifyTeacherUser,'update.gif');
   bar.addItem("禁用","activateUser('false')",'prohibit.gif');
   bar.addItem("激活","activateUser('true')",'newUser.gif');    
  </script>    
  </body>
<#include "/templates/foot.ftl"/>