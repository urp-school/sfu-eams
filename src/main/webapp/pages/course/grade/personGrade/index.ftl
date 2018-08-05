<#include "/templates/head.ftl"/>
<body>
	<table id="gradeBar"></table>
	<#include "stdGrade.ftl"/>
	<form name="actionForm" action="" method="post" onsubmit="return false;">
  		<input type="hidden"  name ="majorType.id" value="${majorType.id}">
	</form>
	<script>
	   var form =document.actionForm;
	    function gradeList(majorTypeId){
	       form['majorType.id'].value=majorTypeId;
	       form.action="personGrade.do?method=index";
	       form.submit();
	    }
	    var bar=new ToolBar("gradeBar","<@msg.message key="grade.stdPersonScoreSearch"/>",null,true,true);
	    <#if (has2ndSpeciality||hasSpeciality2ndGrade)>
	    bar.addItem("<@msg.message key="grade.scoreOfFirstMajor"/>","gradeList(1)","list.gif");
	    bar.addItem("<@msg.message key="grade.scoreOfSecondMajor"/>","gradeList(2)","list.gif");
	    </#if>
	    <!--bar.addItem("<@msg.message key="action.print"/>","stdList()");-->
	    bar.addHelp("<@msg.message key="action.help"/>");
	    
	    
	    function stdList(){
	    	window.open("personGrade.do?method=stdList");
	    }
	    
	</script> 
</body>
<#include "/templates/foot.ftl"/> 