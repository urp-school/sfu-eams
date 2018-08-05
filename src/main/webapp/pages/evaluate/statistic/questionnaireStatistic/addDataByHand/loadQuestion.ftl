<#include "/templates/head.ftl"/>
 <script>
    var detailArray = new Array();
    
    function getIds(){
       return(getRadioValue(document.getElementsByName("questionId")));
    }
    
    function getValue(id,value){
       if (id != ""){
       	  return detailArray[id][value];
       }else{
          return "";
       }       
    }
    function doSubmit(){
    	var id = getIds();
    	var questionValue = getValue(getIds(),0);
    	var questionMark = getValue(getIds(),1);
    	questionValue = questionValue+questionMark;
    	window.top.opener.setQuestionIdAndDescriptions(id,questionValue,questionMark);
        window.close();
    }
    function mergeTableTd(tableId,index){
		var rowsArray = document.getElementById(tableId).rows;
		var value=rowsArray[1].cells[index];
		for(var i=2;i<rowsArray.length-1;i++){
			nextTd=rowsArray[i].cells[index];
			if(nextTd.innerHTML==value.innerHTML){
				rowsArray[i].removeChild(nextTd);
				var rowspanValue= new Number(value.rowSpan);
				rowspanValue++;
				value.rowSpan=rowspanValue;
			}else{
				value=nextTd;
			}
		}
}
 </script>
 <BODY  LEFTMARGIN="0" TOPMARGIN="0" onload="mergeTableTd('tableName',1)">
  <table id="backBar" width="100%"></table>
	<script>
   		var bar = new ToolBar('backBar','<@bean.message key="field.question.questions"/>',null,true,true);
   		bar.setMessage('<@getMessage/>');
   		bar.addItem("选择问题","doSubmit()");
	</script>
  <table cellpadding="0" cellspacing="0" width="100%" border="0"> 
   <tr>
    <td>
    <#if questions?exists>
     <table id="tableName" width="100%" align="center" class="listTable">
       <form name="listForm" action="" onsubmit="return false;">
	   <tr align="center" class="darkColumn">
	     <td align="center">&nbsp;</td>
	     <td><@bean.message key="field.question.questionType"/></td>
	     <td><@bean.message key="field.question.questionContext"/></td>
	     <td><@bean.message key="field.questionnaire.createDepartment"/></td>
	     <td><@bean.message key="field.question.mark"/></td>
	   </tr>	   
	   <#list questions?if_exists as question>
	   <#if question_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if question_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}">
	    <script>
	       detailArray['${question.questionId}']= new Array();
	       detailArray['${question.questionId}'][0] = "${question.questionContext?html}";
	       detailArray['${question.questionId}'][1] = "${question.questionMark}";
	    </script>
	    <td width="5%" align="center" bgcolor="#CBEAFF">
	     <input type="radio" name="questionId" value="${question.questionId}"/>
	    </td>
	    <td width="15%">&nbsp;${question.questionType.typeNameZh?if_exists}</td>
	    <td width="50%" align="left">${question.questionContext?if_exists}</td>
	    <td width="15%" align="left">${question.department?if_exists.name?if_exists}</td>
	    <td width="10%" align="center">&nbsp;${question.questionMark?if_exists?string("###0.0")}</td>
	   </tr>
	   </#list>
	   <tr class="darkColumn" align="center">
	   		<td colspan="5" align="center">
	   			<input type="button" name="button1" value="<@bean.message key="action.confirm"/>" class="buttonStyle"
            onClick="doSubmit()"  />
	   		</td>
	   </tr>
	   </form>
     </table>
     <#else>
     	<font color="red">该课程没有评教问卷,请指定好问卷以后再录入成绩</font>
     </#if>
    </td>
   </tr>
  </table>
 </body>
<#include "/templates/foot.ftl"/>