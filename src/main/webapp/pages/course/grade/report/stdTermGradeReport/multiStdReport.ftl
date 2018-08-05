<#include "/templates/head.ftl"/>
<#include "/pages/course/grade/gradeMacros.ftl"/>
<style>
.title {
    font-style: normal; 
    font-size: 7pt;
    height:30px;
}
.singleCourse {
    font-style: normal; 
    font-size: 8pt;
    height:30px;
}
</style>
 <BODY>
 <table id="myBar" width="100%"> </table>
 <script>
   var bar = new ToolBar("myBar","学生每学期成绩",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message key="action.print"/>","print()");
   bar.addItem("导出到Excel","AllAreaExcel()");
   bar.addItem("导出到Word","AllAreaWord()");
   bar.addClose();
 </script>
 <div id = "PrintA" width="100%" align="center" cellpadding="0" cellspacing="0">
   <#assign tableIndex=0>
    <#list multiStdGrades as multiStdGrade>
    <#if multiStdGrade.stdGrades?exists>
    <#list multiStdGrade.stdGrades?chunk(setting.pageSize) as stdGradeList>
 	 <table width="100%" style="border:0;<#if tableIndex!=0>PAGE-BREAK-before: always;</#if>" cellpadding="0" cellspacing="0"> 	 
 	    <tr>
 	      <td align="center" colspan="20"><h3><@i18nName systemConfig.school/>${multiStdGrade.calendar.year}学年<#if multiStdGrade.calendar.term="1">第一学期<#elseif multiStdGrade.calendar.term="2">第二学期<#else>${multiStdGrade.calendar.term}</#if>成绩一览表</h3></td>
 	    </tr>
 	 	<tr>
 	 	  <td style="font-size:15px;" colspan="20">
 	 	  <#if multiStdGrade.adminClass?if_exists.name?exists>
 	 	  	班级：${multiStdGrade.adminClass.name}
 	 	  </#if>
 	 	  </td>
 	 	</tr>
	 </table>
	 <#assign tableIndex=tableIndex+1>
     <table width="100%" align="center" class="listTable">
	   <tr class="title">
	     <td align='center'>序号</td>
	     <td align='center'><@msg.message key="attr.stdNo"/></td>
	     <td  align='center' style="width:80px"><@msg.message key="attr.personName"/></td>
	     <#if setting.printGP><td align="center">平均绩点</td></#if>
	     <#list multiStdGrade.courses as course>
	     <td ><@i18nName course/></td>  
	     </#list>
	     <#if (multiStdGrade.extraCourseNum>0)>
	     <#list 1..multiStdGrade.extraCourseNum as i>
	     <td >课程、<@msg.message key="attr.credit"/>、成绩</td>
	     </#list>
	     </#if>
	   </tr>	   
	   <#list stdGradeList as stdGrade>
	    <tr align="center">
	       <td>${stdGrade_index+1}</td>
		   <td>${stdGrade.std.code}</td>
		   <td>${stdGrade.std.name}</td>
		   <#if setting.printGP><td><@reserve2 stdGrade.GPA?if_exists/></td></#if>
		   <#assign gradeMap = stdGrade.toGradeMap()>
		   <#list multiStdGrade.courses as course>
	        <td >${(gradeMap[course.id?string].getScoreDisplay(setting.gradeType))?if_exists}</td>  
	       </#list>
		   <#if (multiStdGrade.extraCourseNum>0)>
		     <#assign emptyTdNum=multiStdGrade.extraCourseNum>
		     <#if multiStdGrade.extraGradeMap[stdGrade.std.id?string]?exists>
		       <#list multiStdGrade.extraGradeMap[stdGrade.std.id?string] as courseGrade>
		         <td ><@i18nName courseGrade.course/> ${courseGrade.credit} ${courseGrade.getScoreDisplay(setting.gradeType)?if_exists}</td>
		       </#list>
		       <#assign emptyTdNum=multiStdGrade.extraCourseNum-multiStdGrade.extraGradeMap[stdGrade.std.id?string]?size>
	         </#if>
	         <@emptyTd emptyTdNum/>
	       </#if>
	    </tr>
	   </#list>
     </table>
  </#list>
  <#else>
   <#if multiStdGrade.adminClass?if_exists.name?exists>
 	 	  	班级：${multiStdGrade.adminClass.name}，没有找到该学期的成绩。<br>
   <#else>
 	 	  没有找到该学期的成绩。<br>
   </#if>
  </#if>
  </#list>
  </div> 
 </body>
<#include "/templates/foot.ftl"/>      
<SCRIPT LANGUAGE="javascript">
 //指定页面区域内容导入Excel
 function AllAreaExcel()  {
  var oXL= newActiveX("Excel.Application");
  if(null==oXL) return;
  var oWB = oXL.Workbooks.Add(); 
  var oSheet = oWB.ActiveSheet;  
  var sel=document.body.createTextRange();
  sel.moveToElementText(PrintA);
  sel.select();
  sel.execCommand("Copy");
  oSheet.Paste();
  oXL.Visible = true;
 }

 //指定页面区域内容导入Word
 function AllAreaWord() {
  var oWD= newActiveX("Word.Application");
  if(null==oWD) return;
  var oDC = oWD.Documents.Add("",0,1);
  var oRange =oDC.Range(0,1);
  var sel = document.body.createTextRange();
  sel.moveToElementText(PrintA);
  sel.select();
  sel.execCommand("Copy");
  oRange.Paste();
  oWD.Application.Visible = true;
  //window.close();
 }
</SCRIPT>

