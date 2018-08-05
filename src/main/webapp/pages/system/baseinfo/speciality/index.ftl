<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="myBar"></table>
 <table class="frameTable">
   <tr>
    <td style="width:160px" class="frameTable_view">
     <form name="searchForm" action="speciality.do?method=search" target="contentListFrame" method="post" onsubmit="return false;">
      <#assign extraSearchOptions><#include "../../base.ftl"/><@stateSelect "speciality"/></#assign>
	  <#include "/pages/components/specialitySearchTable.ftl">
     </form>
    </td>
    <td  valign="top">
	  <iframe  src="#" 
	     id="contentListFrame" name="contentListFrame" 
	     marginwidth="0" marginheight="0" scrolling="no"
	     frameborder="0"  height="100%" width="100%">
	  </iframe>	  
    </td>
   </tr>
  </table>
  <script language="JavaScript" type="text/JavaScript" src="<@msg.message key="js.baseinfo"/>"></script>
  <script language="javascript">
    type="speciality";
    keys="code,name,engName,abbreviation,description,createAt,modifyAt,remark,state,dateEstablished,maxPeople,department.name,stdType.name,is2ndSpeciality,subjectCategory.name";
    titles="<@msg.message key="attr.code"/>,<@msg.message key="attr.name"/>,<@msg.message key="attr.engName"/>,<@msg.message key="attr.abbreviation"/>,<@msg.message key="attr.description"/>,<@msg.message key="attr.createAt"/>,<@msg.message key="attr.modifyAt"/>,<@msg.message key="attr.remark"/>,<@msg.message key="attr.state"/>,<@msg.message key="attr.dateEstablished"/>,<@msg.message key="speciality.maxPeople" />,<@msg.message key="entity.department"/>,<@msg.message key="entity.studentType"/>,<@msg.message key="entity.secondSpeciality"/>,学科门类";
    labelInfo="<@bean.message key="page.specialityListFrame.label" />";
    function initToolBarHook(bar){
       menu1 =bar.addMenu("导入","importData()");
       menu1.addItem("下载模板","downloadTemplate()","download.gif");
    }
    search();
    searchSpeciality=search;
    initBaseInfoBar(); 
    function downloadTemplate(){
      self.location="dataTemplate.do?method=download&document.id=6";
    }
    function importData(){
       form.action=getAction()+"?method=importForm&templateDocumentId=6";
       addInput(form,"importTitle","专业数据上传")
       form.submit();
    }
  </script>
</body>
<#include "/templates/foot.ftl"/>