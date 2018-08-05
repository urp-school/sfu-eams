<#include "/templates/head.ftl"/>
 <body>
 <table id="stdUserBar"></table>
   <table class="frameTable">
   <tr>
    <td style="width:160px"  class="frameTable_view"><#include "searchForm.ftl"/></td>
    <td valign="top">
    <iframe  src="#" id="contentFrame" name="contentFrame" 
      marginwidth="0" marginheight="0"
      scrolling="no"  frameborder="0"  height="100%" width="100%"></iframe>
    </td>
   </tr>
  </table>
 </body>
  <script>
   var form = document.stdUserSearchForm;
   var action="stdUser.do";
    function getStdCodes(){
       return(getCheckBoxValue(contentFrame.document.getElementsByName("stdCode")));
    }
    function searchStdUser(pageNo,pageSize,orderBy){     
       form.action=action+"?method=search";
       goToPage(form,pageNo,pageSize,orderBy);
    }
    searchStdUser();
    function activateUser(isActivate){
       var stdCodes = getStdCodes();
       if(stdCodes =="") {
         window.alert('<@bean.message key="common.select"/>!');
         return;
       }
       form.action=action+"?method=activate&stdCodes="+stdCodes+"&isActivate="+isActivate;
       addParamsInput(form,contentFrame.queryStr);
       form.submit();
    }
  
    function modifyStdUser(){
       var stdCodes = getStdCodes();       
       if(stdCodes =="") {
         window.alert('请选择一个!');
         return;
       }
	   addInput(form,"stdCode",stdCodes);
       if(isMultiId(stdCodes)){alert("请仅选择一个");return;}
       form.action=action+"?method=edit";
       addParamsInput(form,contentFrame.queryStr);
       form.submit();
    }
    /**
     * 新增学生
     */
    function addStdUser(){
        var stdCodes=getStdCodes();
        if(stdCodes==""){
             window.alert('请选择一个学生!');
             return;
        }
        form.action="stdUser.do?method=add&stdCodes="+stdCodes;
        addParamsInput(form,contentFrame.queryStr);
        form.submit();
    }
    function promptToManager(){
       var stdCodes = getStdCodes();
       if(stdCodes =="") {
         window.alert('请选择一个或多个学生');
         return;
       }
       form.action = action+"?method=promptToManager&stdCodes="+stdCodes;
       addParamsInput(form,contentFrame.queryStr);
       form.submit();
    }
   var bar = new ToolBar('stdUserBar','&nbsp;学生用户管理',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("新建",addStdUser,'newUser.gif');
   bar.addItem("<@msg.message key="action.edit"/>",modifyStdUser,'update.gif');
   bar.addItem("禁用","activateUser('false')",'prohibit.gif');
   bar.addItem("激活","activateUser('true')",'newUser.gif');
   bar.addItem("升级为管理人员",promptToManager,'update.gif');
   bar.addBack("<@bean.message key="action.back"/>");      
 </script>
</html>