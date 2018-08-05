<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/prompt.js"></script> 
<script language="JavaScript" type="text/JavaScript" src="scripts/Selector.js"></script>
<script language="JavaScript" src="<@bean.message key="menu.js.url"/>"></script>
 <style  type="text/css">
 </style>
   
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<form name="teachCheckDetailForm" action="teachReview.do?method=save" method="post" onsubmit="return false;">
<table id="teachCheckBar"></table>
<table align="left" class="formTable" class="darkColumn">
<tr align="center" >
<td width="15%">学风</td>
<td><textarea cols="60" rows="4" name="studyStyle" style="background-color:D7F8AB">
${teachCheckDetail?if_exists.studyStyle?if_exists}
</textarea></td>
</tr>
<tr align="center" >
<td>教风</td>
<td><textArea cols="60" rows="4" name="teachStyle"style="background-color:D7F8AB">
${teachCheckDetail?if_exists.teachStyle?if_exists}</textarea></td>
</tr>
<tr align="center" >
<td>课程建设</td>
<td><textArea cols="60" rows="4" name="courseBuild"style="background-color:D7F8AB">
${teachCheckDetail?if_exists.courseBuild?if_exists}
</textarea></td>
</tr><tr align="center" >
<td>教学文档</td>
<td><textArea cols="60" rows="4" name="teachDoc"style="background-color:D7F8AB">
${teachCheckDetail?if_exists.teachDoc?if_exists}
</textarea></td>
</tr><tr align="center" >
<td>毕业论文情况</td>
<td><textArea cols="60" rows="4"  name="graduateDoc"style="background-color:D7F8AB">
${teachCheckDetail?if_exists.graduteDoc?if_exists}
</textarea></td>
</tr><tr align="center" >
<td>教学大纲</td>
<td><textArea cols="60" rows="4"  name="teachOutline"style="background-color:D7F8AB">
${teachCheckDetail?if_exists.teachOutline?if_exists}
</textarea></td>
</tr><tr align="center" >
<td>其他</td>
<td><textArea cols="60" rows="4" name="other"style="background-color:D7F8AB">
${teachCheckDetail?if_exists.other?if_exists}
</textarea></td>
</tr>
<input type="hidden" name="hid" value="${teachCheckDetail?if_exists.id?if_exists}"/>
</table></form>
<script language="javascript">
 var bar=new ToolBar('teachCheckBar','教学检查维护',null,true,true);
 bar.setMessage('<@getMessage/>')
 bar.addItem("<@msg.message key="action.save"/>","save()");
 bar.addBack("<@bean.message key="action.back"/>");
 function save(){
	document.teachCheckDetailForm.submit();
 }
</script>
<#include "/templates/foot.ftl"/>