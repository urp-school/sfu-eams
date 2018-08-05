<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" >
  <table id="gradeListBar" width="100%"> </table>
  <#include "../otherGradeListTable.ftl"/>
  <form name="gradeListForm" method="post" action="" onsubmit="return false;"></form>
  <script>
    var bar = new ToolBar("gradeListBar","成绩查询结果",null,true,true);
    keys="calendar.year,calendar.term,std.code,std.name,std.basicInfo.gender.name,std.department.name,std.firstMajor.name,std.firstMajorClass.name,score,isPass,markStyle.name,category.name,std.type.name";
    titles="学年度,学期,<@msg.message key="attr.stdNo"/>,<@msg.message key="attr.personName"/>,<@msg.message key="common.gender"/>,<@msg.message key="entity.department"/>,<@msg.message key="entity.speciality"/>,班级,分数,是否通过,记录方式,考试类别,<@msg.message key="entity.studentType"/>";
    var form=document.gradeListForm;
    action="otherGradeSearch.do";
    function exportData(){
       transferParams(parent.document.searchForm,form);
       form.action=action+"?method=export";
       if(confirm("是否导出查询出的所有记录？")){
	       addInput(form,"keys",keys);
	       addInput(form,"titles",titles);
	       form.submit();
       }
    }
    bar.addItem("<@msg.message key="action.export"/>","exportData()");
    bar.addPrint("<@msg.message key="action.print"/>");
  </script>
 </body>
<#include "/templates/foot.ftl"/>
