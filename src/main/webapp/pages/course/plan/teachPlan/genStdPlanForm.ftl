<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
	<table id="genStdPlanBar"></table>
	<script>
	  var bar = new ToolBar("genStdPlanBar","生成学生个人培养计划",null,true,true);
	  bar.addItem("生成","genStdPlan(document.stdListForm)");
	  bar.addBack("<@msg.message key="action.back"/>");
	</script>
     <table width="100%" class="infoTable">
      <form action="teachPlan.do?method=gen" name="stdListForm" method="post" onsubmit="return false;">
       <input type="hidden" name="genPlanType" value="std"/>
       <input type="hidden" name="targetPlan.id" value="${teachPlan.id}" >
      </form>
       <tr class="darkColumn">
         <td colspan="4"><B>培养计划基本信息</B></td>
       </tr>
	   <tr>
	     <td class="title"><@bean.message key="attr.enrollTurn" />:</td>
	     <td>${teachPlan.enrollTurn}</td>
	   	 <td class="title"><@bean.message key="common.college" />:</td>
	     <td> <@i18nName teachPlan.department/></td>
	   </tr>
	   <tr>
    	 <td  class="title"><@bean.message key="entity.studentType"/>:  </td>
         <td><@i18nName teachPlan.stdType/></td>
         <td class="title"><@bean.message key="entity.speciality"/>:  </td>
	     <td><@i18nName teachPlan.speciality?if_exists/></td>
       </tr>
	   <tr>
         <td class="title">学期数:</td>
         <td>${teachPlan.termsCount?if_exists}</td>
	     <td class="title"><@bean.message key="entity.specialityAspect"/>&nbsp;&nbsp;:</td>
	     <td><@i18nName teachPlan.aspect?if_exists/></td>
	   </tr>
	   <tr>
         <td  class="title">指导老师:</td>
         <td colspan="3">${teachPlan.teacherNames?if_exists}</td>
	   </tr>
	   <tr>
         <td  class="title"><@bean.message key="attr.remark" />:</td>
         <td colspan="3">${teachPlan.remark?if_exists}</td>
	   </tr>
     </table>
     
     <@table.table width="100%" id="listTable">
       <@table.thead>
         <@table.selectAllTd id="stdId"/>
	     <@table.td width="15%" name="attr.personName"/>
	     <@table.td width="10%" name="std.code"/>
	     <@table.td width="25%" name="entity.college"/>
	     <@table.td width="25%" name="entity.studentType"/>
	     <@table.td name="filed.enrollYearAndSequence"/>
	   </@>
	   <@table.tbody datas=stdList?sort_by("code");student>
         <@table.selectTd id="stdId" value=student.id/>    
	     <td><a href="studentDetailByManager.do?method=detail&stdId=${student.id}" target="_blank">&nbsp;<@i18nName student?if_exists/></a></td>
	     <td>&nbsp;${student.code}</td>
	     <td>&nbsp;<@i18nName student.department?if_exists/></td>
	     <td>&nbsp;<@i18nName student.type?if_exists/></td>
	     <td>${student.enrollYear}</td>
	   </@>
	   </@>
	   
    <script>
    function genStdPlan(form){
       var stdIdSeq = getCheckBoxValue(document.getElementsByName("stdId"));
       if(""==stdIdSeq){
          alert("请选择一个学生生成培养计划!");
          return;
       }else{
          if(!confirm("是否确定要生成选定学生的个人培养计划")) return;
          form.action="teachPlan.do?method=gen&stdIdSeq="+stdIdSeq;
          form.submit();
       }
    }
    </script>
</body>