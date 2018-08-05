<#include "/templates/head.ftl"/>
<BODY>
 <table id="myBar"></table>
 <table class="frameTable" width="100%">
   <tr>
    <td width="20%" class="frameTable_view">
    <form name="searchForm" method="post" target="contentListFrame">
      <#assign extraSearchOptions><#include "../../base.ftl"/><@stateSelect "course"/></#assign>
	  <#include "/pages/components/courseSearchTable.ftl">
	  <table><tr height="350px"><td></td></tr></table>
     </form>
    </td>
    <td valign="top">
      <iframe src="#" 
     id="contentListFrame" name="contentListFrame" 
     marginwidth="0" marginheight="0" scrolling="no"
     frameborder="0" height="100%" width="100%">
     </iframe>
    </td>
   </tr>
  </table>
  <script language="JavaScript" type="text/JavaScript" src="<@msg.message key="js.baseinfo"/>"></script>
  <script language="javascript">
    type="course";
    keys="code,name,engName,abbreviation,createAt,modifyAt,remark,state,credits,extInfo.period,weekHour,stdType.name,category.name,extInfo.requirement,extInfo.description,languageAbility.name,extInfo.establishOn";
    titles="代码,名称,英文名,简称,创建日期,修改日期,备注,是否使用,学分,学时,周课时,<@msg.message key="entity.studentType"/>,课程种类,课程要求,课程简介,语言熟练程度要求,设立年月";
    labelInfo="<@bean.message key="page.courseListFrame.label" />";
    search();
    searchCourse=search;
    function initToolBarHook(bar){
       menu1 =bar.addMenu("导入","importData()");
       menu1.addItem("下载模板","downloadTemplate()","download.gif");
       //bar.addItem("新开课程","toNewCourse()");
    }
    function importData(){
       form.action=getAction()+"?method=importForm&templateDocumentId=4";
       addInput(form,"importTitle","课程数据上传");
       form.submit();
    }
    function downloadTemplate(){
      self.location="dataTemplate.do?method=download&document.id=4";
    }
    /*function toNewCourse(){
    	self.location="newCourse.do?method=index";
    }*/
    initBaseInfoBar();
  </script>
  </body>
<#include "/templates/foot.ftl"/>