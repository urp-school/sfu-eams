<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
 <table id="myBar"></table>
 <table class="frameTable">
   <tr>
    <td style="width:160px" class="frameTable_view">
    <form name="searchForm" method="post" action="" onsubmit="return false;">
      <input type="hidden" name="calendar.id" value="${RequestParameters['calendar.id']}"/>
      <#include "/pages/components/initAspectSelectData.ftl"/>
	  <#include "/pages/components/adminClassSearchTable.ftl">
     </form>
    </td>
    <td valign="top">
	  <iframe  src="#" 
	     id="contentListFrame" name="contentListFrame" 
	     marginwidth="0" marginheight="0" scrolling="no"
	     frameborder="0"  height="100%" width="100%">
	  </iframe>   
     </iframe>
    </td>
   </tr>
  </table>
  <script language="javascript">
    var bar = new ToolBar("myBar","选择班级打印,打印教材发放清单(<@i18nName calendar.studentType/> ${calendar.year} ${calendar.term})",null,true,true);
    form=document.searchForm;
    action="bookRequirement.do";
    bar.addItem("选中班级,预览教材发放清单","reportForAdminClass()");
    bar.addHelp("<@msg.message key="action.help"/>");
    bar.addClose("<@msg.message key="action.close"/>");
    searchClass=function(pageNo,pageSize,orderBy){
      form.action=action+"?method=searchAdminClass";
      form.target="contentListFrame";
      goToPage(form,pageNo,pageSize,orderBy);
    }
    searchClass();
    function reportForAdminClass(){
       var classIds =getCheckBoxValue(contentListFrame.document.getElementsByName("adminClassId"));
       if(classIds=="")
         window.alert('<@bean.message key="common.select"/>!');
       else{
           form.action=action+"?method=reportForAdminClass&adminClassIds=" +classIds;
           form.target="_blank";
       	   form.submit();
       }
    }

 </script>
</body>
<#include "/templates/foot.ftl"/>