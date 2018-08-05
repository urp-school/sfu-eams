<#include "/templates/head.ftl"/>
<script>
    var questionArray= new Array();
    <#list questions?if_exists as question>
    	questionArray[${question_index}]=new Array();
    	questionArray[${question_index}][0]='${question.id}';
    	questionArray[${question_index}][1]="${question.type.name?if_exists?html}";
    	questionArray[${question_index}][2]="${question.content?if_exists?js_string}";
    	questionArray[${question_index}][3]='${question.type.id?if_exists}';
    </#list>
    function getIds(){
      	return(getCheckBoxValue(document.getElementsByName("questionId")));
    }

    function doSubmit(){
      var idSeq = getIds();
      if(""==idSeq){
    	alert("请选择一些问题");
    	return;
      }
      var ids = idSeq.split(",");
      for(var j=0;j<ids.length;j++){
    	for(var i=0;i<questionArray.length;i++){
    		if(questionArray[i][0]==ids[j]){
    			 opener.addContext(questionArray[i]);
    			break;
    		}
    	  }
      }
     window.close();
    }
</script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
   <table id="backBar" width="100%"></table>
	<script>
   		var bar = new ToolBar('backBar','<@msg.message key="field.question.questions"/>',null,true,true);
   		bar.setMessage('<@getMessage/>');
   		bar.addItem("添加","doSubmit()")
	</script>
     <table width="100%" align="center" class="listTable">
       <form name="listForm" action="" onsubmit="return false;">
	   <tr align="center" class="darkColumn">
	     <td align="center">&nbsp;</td>
	     <td><@msg.message key="field.question.questionType"/></td>
	     <td><@msg.message key="field.question.questionContext"/></td>
	     <td><@msg.message key="field.questionnaire.createDepartment"/></td>
	     <td><@msg.message key="field.question.mark"/></td>
	   </tr>	   
	   <#list questions?if_exists as question>
	   <#if question_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if question_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}">
	    <td width="5%" align="center" bgcolor="#CBEAFF">
	     <input type="checkBox" name="questionId" value="${question.id}"/>
	    </td>
	    <td width="15%">&nbsp;${question.type.name?if_exists}</td>
	    <td width="50%" align="left">${question.content?if_exists}</td>
	    <td width="15%" align="left">${question.department.name?if_exists}</td>
	    <td width="10%" align="center">&nbsp;${question.score?default(0)?string("###0.0")}</td>
	   </tr>
	   </#list>
	   <tr class="darkColumn" align="center">
	   		<td colspan="5" align="center">
	   			<button name="button1" onClick="doSubmit()" >添加</button>&nbsp;&nbsp;&nbsp;&nbsp;
	   		</td>
	   </tr>
	   </form>
     </table>
 </body>
<#include "/templates/foot.ftl"/>