<#include "/templates/head.ftl"/>
<link href="${static_base}/css/tab.css" rel="stylesheet" type="text/css">
<BODY>
	<table id="taskBar"></table>
     <table class="frameTable_title">
      <tr>
       <td>详细查询</td>
       <form name="taskSearchForm" method="post" target="contentFrame" action="examiner.do?method=index" onsubmit="return false;">
       <td class="separator">
          <select name="examType.id" onchange="search()">
             <#list examTypes?sort_by("code") as examType>
             <option value="${examType.id}"><@i18nName examType/></option>	
             </#list>
          </select>
       </td>
      <#include "/pages/course/calendar.ftl"/>
      </tr>
     </table>
  <table width="100%" class="frameTable" height="89%">
    <tr>
     <td valign="top" style="width:20%" class="frameTable_view">
     <#include "searchForm.ftl"/>
     </td>
   </form>
     <td valign="top">
	     <iframe src="#" id="contentFrame" name="contentFrame" scrolling="no" marginwidth="0" marginheight="0" frameborder="0" height="100%" width="100%"></iframe>
     </td>
    </tr>
  <table>
 <script>
   var bar=new ToolBar("taskBar","监考管理",null,true,true);
   bar.addItem("监考教师统计", "examinerStat()");
   
   var form=document.taskSearchForm;
   function search(pageNo,pageSize,orderBy){
        var searchParams=getInputParams(form,null,false);
	    addParamsInput(form,searchParams);
	    form.action="examiner.do?method=examActivityList";
   		form.target = "contentFrame";
        goToPage(form,pageNo,pageSize,orderBy);
   }
   search();
   function stateModule(){
     var url="teachTask.do?method=taskOfCourseTypeList&calendar.studentType.id=${studentType.id}&calendar.id=${calendar.id}&orderBy=courseType.name";
     window.open(url, '', 'toolbar=yes, menubar=yes,location=yes,scrollbars=yes,top=0,left=0,width=500,height=550,status=yes,resizable=yes');
   }
   	
   	function examinerStat() {
   		form.action = "examiner.do?method=examinerStat";
   		addInput(form, "examTeacherType", "examMonitor", "hidden");
   		form.target = "_blank";
   		form.submit();
   	}
 </script>
</body>
<#include "/templates/foot.ftl"/> 
  