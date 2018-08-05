<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <body>
 <table id="backBar" width="100%"></table>
    <table width="80%" align="center" class="formTable">
       <tr align="center" class="darkColumn">
    		<td colspan="2" align="center">文字评教</td>
   	   </tr>		
       <form name="commonForm" method="post" action="" onsubmit="return false;">
	     	<input type="hidden" name="textEvaluation.id" value="${textEvaluation.id}">
	   <tr>
	     <td class="title" width="20%">&nbsp;<@bean.message key="attr.yearAndTerm"/>:</td>
	     <td >${textEvaluation.calendar.year}${textEvaluation.calendar.term}</td>
	   </tr>
	   <tr>
	     <td class="title" width="20%">&nbsp;<@bean.message key="entity.course"/>:</td>
	     <td ><@i18nName textEvaluation.task.course/></td>
	   </tr>
	   <#if textEvaluation.teacher?exists>
	   <tr>
	     <td class="title">&nbsp;<@bean.message key="entity.teacher"/><font color="red">*</font>:</td>
	     <td >${(textEvaluation.teacher.name)?default('')}</td>
	   </tr>
	   </#if>
	   <tr>
	     <td class="title" width="20%" id="f_opinion1">&nbsp;<@bean.message key="textEvaluation.opinionContext"/><font color="red">*</font>:</td>
	     <td >
			<textarea name="textEvaluation.context" cols="50" rows="5" style="width:300px;">${textEvaluation.context?default('')}</textarea>
         </td>
	   </tr>
	   <tr class="darkColumn">
	     <td colspan="2" align="center" >
	       <button onClick="save(this.form)"><@bean.message key="action.save"/></button/>&nbsp;
	     </td>
	   </tr>
	   </form>
     </table>
 <script language="javascript">
    var form=document.commonForm;
    function save(form){
     var cotextValue= form["textEvaluation.context"].value;
     if(/^\s*$/.test(cotextValue)){
     	 alert("<@bean.message key="textEvaluation.opinionContext"/>"+"不能为空,或者空格");
     	 return;
     }
     if (checkTextLength(form["textEvaluation.context"].value, "<@msg.message key="textEvaluation.opinionContext"/>")) {
	     form.action="textEvaluationStd.do?method=save";
	     form.submit();
     }
   }
   var bar = new ToolBar('backBar','<@bean.message key="textEvaluation.opinion"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack("<@msg.message key="action.back"/>");
</script>
 </body>
<#include "/templates/foot.ftl"/>
 