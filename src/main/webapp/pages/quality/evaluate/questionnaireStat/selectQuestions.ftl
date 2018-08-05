<#include "/templates/head.ftl"/>
<script>
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
<BODY onblur="self.focus();" LEFTMARGIN="0" TOPMARGIN="0" onload="mergeTableTd('tableName',1)">
  	<table id="backBar" width="100%"></table>
	<@table.table id="tableName" width="100%">
	   	<@table.thead>
         	<@table.td text="&nbsp;"/>
	     	<@table.td name="field.question.questionType"/>
	     	<@table.td name="field.question.questionContext"/>
	     	<@table.td name="field.questionnaire.createDepartment"/>
	     	<@table.td name="field.question.mark"/>
	   	</@>
	  	<@table.tbody datas=questions;question>
	   		<@table.selectTd type="radio" id="questionId" value=question.id/>
		    <td width="15%" align="center">&nbsp;${(question.type.name)?if_exists}</td>
		    <td width="50%" align="left"><label id="qcontent${question.id}">${question.content?if_exists}</label><input type="hidden" id="qremark${question.id}" value="${(question.remark?html)?default("")}"/></td>
		    <td width="15%" align="left">${(question.department.name)?if_exists}</td>
		    <td width="10%">&nbsp;<label id="score">${(question.score?string("###0.0"))?if_exists}</label></td>
	   	</@>
	   	<tr class="darkColumn" align="center">
	   		<td colspan="5" align="center"><button name="button1" value="" class="buttonStyle" onClick="doSubmit()"><@bean.message key="action.confirm"/></button>
	   		</td>
	   	</tr>
    </@>
    <#if !questions?exists><font color="red">该课程没有评教问卷,请指定好问卷以后再录入成绩</font></#if>
	<script>
	   	var bar = new ToolBar('backBar','<@bean.message key="field.question.questionContext"/>',null,true,true);
	   	bar.setMessage('<@getMessage/>');
	   	bar.addItem("选择问题","doSubmit()");
	
	    function doSubmit(){
	    	var id = getSelectId("questionId");
	    	if (id == null || id == "") {
	    		alert("请选择一个问题");
	    		return;
	    	}
	    	self.opener.$("questionName").value = document.getElementById("qcontent" + id).innerHTML;
	    	self.opener.qscore = self.opener.$("score").value = $(score).innerHTML;
	        window.close();
	    }
	</script>
</body>
<#include "/templates/foot.ftl"/>