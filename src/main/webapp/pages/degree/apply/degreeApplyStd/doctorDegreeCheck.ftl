<#include "/templates/head.ftl"/>
<body>
<table id="backBar" width="100%"></table>
<script>
var bar = new ToolBar('backBar','博士学位申请人情况表',null,true,true);
bar.addItem("下载","downloadFile()")
bar.addPrint();
bar.addBack();

</script>
<div align="center"><h2><@i18nName systemConfig.school/>博士学位申请人情况表</h2></div>
<table width="100%"  class="listTable">
  <tr>
    <td width="15%" class="grayStyle">序号</td>
    <td width="17%"></td>
    <td width="13%" class="grayStyle">姓名</td>
    <td width="20%">${student.name}</td>
    <td width="13%" class="grayStyle">学号</td>
    <td width="22%">${student.code}</td>
  </tr>
  <tr>
    <td class="grayStyle">专业</td>
    <td>${(student.firstMajor.name)?if_exists}</td>
    <td class="grayStyle">性别</td>
    <td>${(student.basicInfo.gender.name)?if_exists}</td>
    <td class="grayStyle">出生年月</td>
    <td><#if (student.basicInfo.birthday)?exists>${student.basicInfo.birthday?string("yyyy-MM-dd")}</#if></td>
  </tr>
  <tr>
    <td class="grayStyle">导师姓名</td>
    <td>${(student.teacher.name)?if_exists}</td>
    <td colspan="2" class="grayStyle">符合授予学位标准的核心期刊篇数</td>
    <td colspan="2"></td>
  </tr>
  <tr>
    <td class="grayStyle">论文题目</td>
    <td colspan="5">${(degreeApply.thesisManage.thesis.name)?if_exists}</td>
  </tr>
  <tr>
    <td colspan="6" align="center">在学期间主要科研成果</td>
  </tr>
  <tr class="darkColumn">
    <td>发表年月</td>
    <td>论文名称</td>
    <td>刊物名称</td>
    <td>权威/核心</td>
    <td>合作人数</td>
    <td>核定期刊篇数</td>
  </tr>
  <#assign class ="brightStyle">
  <#list studyThesises?if_exists as studyThesis>
  <tr class=${class}>
    <#if class=="brightStyle"><#assign class ="grayStyle"><#else><#assign class ="brightStyle"></#if>
    <td><#if (studyThesis.publishOn)?exists>${studyThesis.publishOn?string("yyyy-MM-dd")}</#if></td>
    <td>${studyThesis.name}</td>
    <td>${studyThesis.publicationName}</td>
    <td>${studyThesis.publicationLevel.name}</td>
    <td>${studyThesis.rate}</td>
    <td></td>
  </tr>
  </#list>
  <tr>
     <td height="22px;" class="darkColumn" colspan="6"></td>
  </tr>
  <form name="listForm" method="post" action="" onsubmit="return false;">
  </form>
</table>
<script>
  var form = document.listForm;
  function downloadFile(){
    	addInput(form,"degreeApplyId",${degreeApply.id});
    	addInput(form,"fileName","${student.name}博士学位申请人情况表");
    	addInput(form,"template","doctorDegreeCheck_D.xls");
    	addInput(form,"templateType","doctorDegreeCheck");
    	form.action="degreeApplyStd.do?method=export";
    	form.submit();
    }
</script>
</body>
<#include "/templates/foot.ftl"/>