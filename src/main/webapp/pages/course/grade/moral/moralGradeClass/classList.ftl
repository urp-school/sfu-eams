<#include "/templates/head.ftl"/>
<BODY> 
    <table id="classBar"></table>
    <#include "/pages/components/adminClassListTable.ftl"/>
    <@htm.actionForm name="actionForm" action="moralGradeClass.do" entity="adminClass">
      <input type="hidden"  name="calendarId" value="${RequestParameters['calendar.id']}"/>
    </@>
	<script>
		var bar=new ToolBar("classBar","班级列表",null,true,true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("查看","gradeInfo()");
		bar.addItem("录入","inputGrade()");
		bar.addItem("打印","printGrade()");
		bar.addItem("删除","removeGrade()");
		function inputGrade(){
		   document.actionForm.target="_blank";
		   singleAction("inputGrade");
		}
		function gradeInfo(){
		   document.actionForm.target="_blank";
		   multiAction("gradeInfo");
		}
		function printGrade(){
		   document.actionForm.target="_blank";
		   multiAction("gradeReport");
		}
		function removeGrade(){
		   document.actionForm.target="_self";
		   multiAction("removeReport");
		}
	</script>
</body>
<#include "/templates/foot.ftl"/> 
  