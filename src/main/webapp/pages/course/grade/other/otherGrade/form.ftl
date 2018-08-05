<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<script type='text/javascript' src='dwr/interface/studentDAO.js'></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="gradeListBar" width="100%"> </table>
  <table class="formTable" align="center" width="80%">
  <form name="gradeForm" method="post"  onsubmit="return false;">
  <@searchParams/>
    <input name="otherGrade.id" type="hidden" value="${(otherGrade.id)?default("")}"/>
    <tr>
      <td class="title"><@msg.message key="attr.stdNo"/><font color="red">*</font></td>
      <td><input name="otherGrade.std.id" type="hidden" value="${(otherGrade.std.id)?default("")}"/>
       <#if otherGrade.id?exists>${otherGrade.std.code}<#else>
       <input name="otherGrade.std.code" value="${(otherGrade.std.code)?default("")}" onchange="getStd()" maxlength="32"/>
       </#if>
      </td>
      <td class="title"><@msg.message key="attr.personName"/></td>
      <td id="stdName">${(otherGrade.std.name)?default("")}</td>
    </tr>
    <tr>
      <td class="title"><@msg.message key="attr.year2year"/></td>
      <td> <input type="hidden" name="calendar.studentType.id" value="${RequestParameters['otherGrade.std.type.id']}"/>
         <select disabled=true id="stdType" name="calendar.studentType.id" style="width:100px;"><option value="${RequestParameters['otherGrade.std.type.id']}"></option></select><select name="calendar.year" id="year"><option value="${RequestParameters['otherGrade.calendar.year']?default("")}"></select></td>
      <td class="title"><@msg.message key="attr.term"/></td>
      <td><select name="calendar.term" id="term"><option value="${RequestParameters['otherGrade.calendar.term']?default("")}"></select></td>
    </tr>
    <tr>
      <td class="title">考试类别</td>
      <td><@htm.i18nSelect datas=otherExamCategories selected=(otherGrade.category.id)?default("")?string name="otherGrade.category.id" style="width:150px"/></td>
      <td class="title">成绩记录方式</td>
      <td><@htm.i18nSelect datas=markStyles selected=(otherGrade.markStyle.id)?default("")?string name="otherGrade.markStyle.id" style="width:150px"/></td>
    </tr>
    <tr>
      <td class="title">是否通过</td>
      <td><@htm.radio2  value=otherGrade.isPass?default(true) name="otherGrade.isPass"/></td>
      <td class="title" id="f_score">成绩</td>
      <td><input name="score" value="${otherGrade.getScoreDisplay()}" style="width:60px" maxlength="3"/>格式应与记录方式相匹配</td>
    </tr>
	<tr>
	<td class="darkColumn" colspan="4" align="center">
         <button onclick="save()"><@msg.message key="action.save"/></button>
	</td>
	</tr>
  </form>
  </table>
   <#include "/templates/calendarSelect.ftl"/>
  <script>
    var bar = new ToolBar("gradeListBar","成绩详细信息",null,true,true);
    bar.addItem("<@msg.message key="action.save"/>","save()");
    bar.addPrint("<@msg.message key="action.print"/>");
    bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
    
    var form=document.gradeForm;
    function save(){
       if( form['otherGrade.std.id'].value==""){
         alert("学生不存在，请输入学号.");
         return;
       }
       
		var a_fields = {
       		'score':{'l':'成绩', 'r':false, 't':'f_score'}
       	};
       	
       	var v = new validator(form, a_fields, null);
       	if (v.exec()) {
	       	if (form["score"].value != null && form["score"].value != "" && parseInt(form["score"].value) > 1000) {
	       		alert("成绩不能超过1000分！");
	       		return;
	       	}
	       	form.action="otherGrade.do?method=save";
	       	form.submit();
       	}
    }
    // 查找学生
    function getStd(){
     var stdCode=form['otherGrade.std.code'].value;
     if (stdCode == "") {
     	alert("请输入学号");
     	clear();
     } else{
       studentDAO.getBasicInfoName(setStdInfo,stdCode);
     }
    }
    function setStdInfo(data){
     if(null==data){
       document.getElementById("stdName").innerHTML="没有该学生";
       form['otherGrade.std.id'].value="";
     }else{
        $('stdName').innerHTML=data.name;
        form['otherGrade.std.id'].value=data.id;
     }
  }
  </script>
 </body>
<#include "/templates/foot.ftl"/>
