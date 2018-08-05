<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="myBar"></table>
  <table  class="frameTable">
   <tr>
    <td width="20%" class="frameTable_view">
     <form name="searchForm" target="contentListFrame" method="post" action="" onsubmit="return false;">
      <#assign extraSearchOptions>
	     <tr><td><@bean.message key="teacher.isTeaching"/>:</td>
	         <td>
	        <select name="teacher.isTeaching" style="width:100px;">
		   		<option value="1" <#if RequestParameters['teacher.isTeaching']?if_exists=="1">selected</#if>><@bean.message key="common.yes" /></option>
		   		<option value="0" <#if RequestParameters['teacher.isTeaching']?if_exists=="0">selected</#if>><@bean.message key="common.no" /></option>
		   		<option value="" <#if RequestParameters['teacher.isTeaching']?if_exists=="">selected</#if>><@bean.message key="common.all" /></option>
	        </select>
	     </td>
	     </tr>
      </#assign>
	  <#include "/pages/components/teacherSearchTable.ftl"/>
	  <table><tr height="300px"><td></td></tr></table>
     </form>
    </td>
    <td valign="top">
	  <iframe  src="#" 
	     id="contentListFrame" name="contentListFrame" 
	     marginwidth="0" marginheight="0" scrolling="no"
	     frameborder="0"  height="450" width="100%">
	  </iframe>
    </td>
   </tr>
  </table>
  <script language="JavaScript" type="text/JavaScript" src="<@msg.message key="js.baseinfo"/>"></script>
  <script language="javascript">
    type="teacher";
    keys="code,name,engName,gender.name,teacherType.name,department.name,birthday,country.name,nation.name,title.name,titleLevel.name,credentialNumber,isConcurrent,isEngageFormRetire,duty.name,dateOfJoin,dateOfTitle,isTeaching,addressInfo.phoneOfCorporation,addressInfo.corporationAddress,addressInfo.postCodeOfCorporation,addressInfo.phoneOfHome,addressInfo.postCodeOfFamily,addressInfo.familyAddress,addressInfo.mobilePhone,addressInfo.fax,addressInfo.email,addressInfo.homepage,"+
         "degreeInfo.degree.name,degreeInfo.dateOfDegree,degreeInfo.eduDegreeInside.name,degreeInfo.eduDegreeOutside.name,degreeInfo.dateOfEduDegreeInside,degreeInfo.dateOfEduDegreeOutside,degreeInfo.graduateSchool.name"
    titles="职工号,<@msg.message key="attr.personName"/>,英文名,<@msg.message key="entity.gender"/>,教职工类别,所在部门,出生日期,国家地区,民族,职称,职称等级,证件,是否兼职,是否退休返聘,职务,来校日期,现职称年月,是否任课,单位电话,单位地址,单位邮编,家庭电话,家庭邮编,家庭地址,手机,传真,邮箱,主页,"+
         "现学位,现学位年月,国内最高学历,国外最高学历,国内现学历年月,国外现学历年月,最后毕业学校";
    labelInfo="<@bean.message key="page.teacherListFrame.label" />";
    function initToolBarHook(bar){
       menu1 =bar.addMenu("导入","importData()");
       menu1.addItem("下载模板","downloadTemplate()","download.gif");
    }
    search();
    searchTeacher=search;
    initBaseInfoBar();
    function downloadTemplate(){
      self.location="dataTemplate.do?method=download&document.id=7";
    }
    function importData(){
       form.action=getAction()+"?method=importForm&templateDocumentId=7";
       addInput(form,"importTitle","教师数据上传")
       form.submit();
    }
  </script>    
  </body>
<#include "/templates/foot.ftl"/>