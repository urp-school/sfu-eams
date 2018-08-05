<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="gradeListBar" width="100%"></table>
  <#include "../otherGradeListTable.ftl"/>
  <form name="gradeListForm" method="post" action="" onsubmit="return false;"></form>
  <script>
    var bar = new ToolBar("gradeListBar","成绩查询结果",null,true,true);
    bar.addItem("<@msg.message key="action.export"/>","exportData()");
    bar.addItem("<@msg.message key="action.add"/>","add()");
    bar.addItem("<@msg.message key="action.edit"/>","edit()");
    bar.addItem("<@msg.message key="action.delete"/>","remove()");
    bar.addPrint("<@msg.message key="action.print"/>");
    
    var keys="calendar.year,calendar.term,std.code,std.name,std.basicInfo.gender.name,std.department.name,std.firstMajor.name,std.firstMajorClass.name,score,isPass,markStyle.name,category.name,std.type.name";
    var titles="学年度,学期,<@msg.message key="attr.stdNo"/>,<@msg.message key="attr.personName"/>,<@msg.message key="common.gender"/>,<@msg.message key="entity.department"/>,<@msg.message key="entity.speciality"/>,班级,分数,是否通过,记录方式,考试类别,<@msg.message key="entity.studentType"/>";
    var form=document.gradeListForm;
    action="otherGrade.do";
    function exportData(){
       transferParams(parent.document.searchForm,form);
       form.action=action+"?method=export";
       if(confirm("是否导出查询出的所有记录？")){
	       addInput(form,"keys",keys);
	       addInput(form,"titles",titles);
	       form.submit();
       }
    }
    function edit(){
       form.action=action+"?method=edit";
       addParamsInput(form,queryStr);
       transferParams(parent.document.searchForm,form);
       submitId(form,"otherGradeId",false);
    }
    function add(){
       form.action=action+"?method=edit";
       addParamsInput(form,queryStr);
       transferParams(parent.document.searchForm,form);
       form.submit();
    }
    function remove(){
       form.action=action+"?method=remove";
       addParamsInput(form,queryStr);
       submitId(form,"otherGradeId",true,null,"<@msg.message key="common.confirmAction"/>");
    }
  </script>
</body>
<#include "/templates/foot.ftl"/>
