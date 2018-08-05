<#include "/templates/head.ftl"/>
<link href="${static_base}/css/tab.css" rel="stylesheet" type="text/css">
<body  LEFTMARGIN="0" TOPMARGIN="0">
<table id="planFormBar"></table>
<script>
 var bar= new ToolBar("planFormBar","培养计划核对",null,true,true);
 bar.addBack();
</script>
     <table width="100%" align="left" class="formTable">
      <form action="" name="planForm" method="post" onsubmit="return false;">
        <tr class="darkColumn">
         <td align="left" colspan="5"><B>基本信息</B></td>
      </tr>
       <tr>
        <td>学期</td>
        <td colspan="4">${termSeq?if_exists}<input type="hidden" name="termSeq" id="termSeq" value="${termSeq?if_exists}"></input></td>
      </tr>
        <tr>
        <td>开课院系</td>
        <td colspan="4"><@i18nName department/><input type="hidden" name="department.id" id="department.id" value="${department.id?if_exists}"></input></td>
      </tr>
      <tr>
        <td>课程类别</td>
        <td colspan="4">${courseType.name?if_exists}<input type="hidden" name="courseType.id" id="courseType.id" value="${courseType.id?if_exists}"></input></td>
      </tr>
       <tr class="darkColumn">
         <td align="left" colspan="5"><B>培养计划列表</B></td>
       </tr>
       <tr>
          <td>所在年级</td>
          <td>学生类别</td>
           <td>院系</td>
           <td>专业</td>
           <td>专业方向</td>
       </tr> 
       <#list teachPlans as plan>
        <tr> 
          <td>${plan.enrollTurn?if_exists}</td>
          <td><@i18nName plan.stdType/></td>
          <td><@i18nName plan.department/></td>
          <td><@i18nName plan.speciality?if_exists/></td>
          <td><@i18nName plan.aspect?if_exists/></td>
        </tr>
       </#list>
      <tr class="darkColumn">
         <td align="left" colspan="5"><B>课程列表<input type="hidden" name="courseCodes" id="courseCodes" value="${courseCodes?if_exists}"></input></B></td>
      </tr>
       <tr>
          <td colspan="2">课程代码</td>
          <td colspan="2">课程名称</td>
          <td>学分</td>
       </tr> 
        <tr>
         <#list  courseList as course >
          <td colspan="2">${course.code?if_exists}</td>
          <td colspan="2">${course.name?if_exists}</td>
          <td>${course.credits?if_exists}</td>
       </tr> 
    　　</#list>
   
      <tr>
        <td colspan="5" align="center">
          <input type="button" value="<@bean.message key="system.button.submit" />" name="button1" onClick="add(this.form)" class="buttonStyle" />
        </td>
      </td>
     </table>
  </form>
  <script>
    function add(form){
      form.action="teachPlan.do?method=saveTeachPlan";
       addInput(form,"teachPlanIds",'${RequestParameters['teachPlanIds']}');
       addInput(form,"params","${RequestParameters['params']?default('')}");
       if(confirm("是否确认信息无误?")){
	　　　　 form.submit();
       }
    }
  
  </script>
</body>