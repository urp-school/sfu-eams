<#include "/templates/head.ftl"/>
<BODY >
	<table id="taskBar"></table>
     <table  class="frameTable_title">
      <tr>
       <td  id="viewTD0" class="transfer" onclick="javascript:changeView1('view0',event)" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
          <font color="blue">未安排</font>
       </td>
       <td  id="viewTD1" class="padding"  onclick="javascript:changeView1('view1',event)" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
          <font color="blue">已安排</font>
       </td>
      <form name="calendarForm" target="contentFrame" method="post" action="">
       <input type="hidden" name="calendar.id" value="${calendar.id}"/>
       <td class="separator">
          <select name="examType.id" onchange="changeExamType()">
             <#list examTypes?sort_by("code") as examType>
             <option value="${examType.id}"><@i18nName examType/></option>	
             </#list>
          </select>
       </td>
      <#include "/pages/course/calendar.ftl"/>
       </form>       
      </tr>
     </table>
  <table width="100%"  class="frameTable" height="89%">
    <tr>     
     <td valign="top"  style="width:160px" class="frameTable_view">
     <#include "searchForm.ftl"/>
     </td>
     <td valign="top">
     <iframe  src="#"
     id="contentFrame" name="contentFrame" scrolling="no"
     marginwidth="0" marginheight="0" frameborder="0"  height="100%" width="100%">
     </iframe>
     </td>
    </tr>
  <table>
 <script>
   var bar=new ToolBar("taskBar","排考管理",null,true,true);
   bar.addHelp("<@msg.message key="action.help"/>");
   
   var optionInputNum=8;
   search(document.taskSearchForm,"taskList");
   function changeView1(id,event){
     changeView(getEventTarget(event));
     if(id=="view0"){
         $("view0").style.display = "block"; 
         $("view1").style.display = "none";
         search(document.taskSearchForm,"taskList");
     }else{
         $("view1").style.display = "block"; 
         $("view0").style.display = "none";
         search(document.arrangedTaskSearchForm,"arrangeList");
     }
   }
   function searchArrange(){
      search(document.arrangedTaskSearchForm,'arrangeList');
   }
   function searchTask(){
      search(document.taskSearchForm,'taskList');
   }
   function changeExamType(){
      if(view0.style.display=="block"){
         search(document.taskSearchForm,"taskList");
      }else{
         search(document.taskSearchForm,"arrangeList");
      }
   }
   function search(form,type,pageNo,pageSize,orderBy){
        if(form==null){
           if(type=="taskList")
              form=document.taskSearchForm;
           else
              form=document.arrangedTaskSearchForm;
        }
        transferParams(document.calendarForm,form,null,false);
	    form.action="examArrangeSearch.do?method="+type;
        goToPage(form,pageNo,pageSize,orderBy);
   }
   var viewNum=2;
 </script>
 <script language="JavaScript" type="text/JavaScript" src="scripts/viewSelect.js"></script> 

</body>
<#include "/templates/foot.ftl"/> 
  