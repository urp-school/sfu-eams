<#include "/templates/head.ftl"/>
<script>
	var questionIds = ",";
	<#list questions?if_exists?reverse as question>
		questionIds=questionIds.concat('${question.id?if_exists},');
	</#list>
	var tempClassName="";
	//合并单元格
	function mergeTableTd(tableId,index,relateTd){
		var rowsArray = document.getElementById(tableId).rows;
		var value=rowsArray[0].cells[index];
		var tdOperator = rowsArray[0].cells[relateTd];
		for(var i=1;i<rowsArray.length-1;i++){
			nextTd=rowsArray[i].cells[index];
			nextTdOperate = rowsArray[i].cells[relateTd];
			if(nextTd.innerHTML==value.innerHTML){
				rowsArray[i].removeChild(nextTd);
				rowsArray[i].removeChild(nextTdOperate);
				var rowspanValue= new Number(value.rowSpan);
				rowspanValue++;
				value.rowSpan=rowspanValue;
				tdOperator.rowSpan=rowspanValue;
			}else{
				value=nextTd;
				tdOperator=nextTdOperate;
			}
		}
		f_frameStyleResize(self);
	}
	//删除一行
	function removeTr(tbodyId,trId,questionId,typeName){
		var tBody = document.getElementById(tbodyId);
		var tr = document.getElementById(trId);
		var endTr = document.getElementById("endTr");
		if(tr.cells.length==4){
			var rowspans = new Number(tr.cells[0].rowSpan);
			if(rowspans>1){
				var nexttr = tr.nextSibling;
				if("endTr"!=nexttr.id){
					rowspans--;
					tr.cells[0].rowSpan=rowspans;
					tr.cells[3].rowSpan=rowspans;
					nexttr.appendChild(tr.cells[3]);
					nexttr.insertBefore(tr.cells[0],nexttr.cells[0]);
				}
			}
		}else{
			for(var i=0;i<tBody.rows.length;i++){
				nextTr = tBody.rows[i];
				if(nextTr.cells.length==4&&nextTr.cells[0].innerHTML.indexOf(typeName)!=-1){
					var rowcount = new Number(nextTr.cells[0].rowSpan);
					rowcount--;
					nextTr.cells[0].rowSpan=rowcount;
					nextTr.cells[3].rowSpan=rowcount;
					break;
				}
			}
		}
		tBody.removeChild(tr);
		questionIds=removeId(questionIds,questionId);
	}
	//添加一行
	function addTr(questionTypeId){
		 var url="questionnaire.do?method=searchQuestion&questionTypeId="+questionTypeId+"&questionSeq="+questionIds.substring(1,questionIds.length);
		 window.open(url,'','scrollbars=auto,width=720,height=480,left=200,top=200,status=no');
	}
	//ids是串 id是单个id
	function removeId(ids,id){
		var index = ids.indexOf(","+id+",");
	 	if(index!=-1){
	 		ids=ids.replace(","+id+",",",");
	 	}
		return ids;
	}
	
function addContext(values){
		var tBody = document.getElementById("tbodyId");
		var flag = '1';
		var addTr = document.createElement("tr");
		addTr.id=new String(values[0]);
		if(tempClassName=="grayStyle"){
			tempClassName="brightStyle";
		}else{
			tempClassName="grayStyle";
		}
		addTr.className=tempClassName;
		var td1= document.createElement("td");
		var input1 = document.createElement("input");
		input1.type="hidden";
		input1.name="question"+values[0];
		input1.value=values[1];
		var input2 = document.createElement("input");
		input2.type="hidden";
		input2.name="question"+values[3];
		input2.value=values[3];
		td1.appendChild(input1);
		td1.appendChild(input2);
		td1.appendChild(document.createTextNode(values[2]));
		var td3= document.createElement("td");
		var a1 = document.createElement("a");
		a1.href="#";
		a1.innerHTML="删除";
		a1.onclick=function(e){
			if (!e){
				e=event;
			}
			var aa = e.srcElement;
			var trtr=aa.parentNode.parentNode;
			var trtrId = trtr.id;
		    var typeName=trtr.cells[0].firstChild.value;
		    removeTr('tbodyId',trtrId,trtrId,typeName);
		}
		td3.appendChild(a1);
		addTr.appendChild(td1);
		addTr.appendChild(td3);
		for(var i=0;i<tBody.rows.length;i++){
			nextTr = tBody.rows[i];
			if(nextTr.cells.length==4&&nextTr.cells[0].innerHTML.indexOf(values[1])!=-1){
				var rowspans =new Number(nextTr.cells[0].rowSpan);
				rowspans++;
				nextTr.cells[0].rowSpan=rowspans;
				nextTr.cells[3].rowSpan=rowspans;
				nextNextTr=tBody.rows[i+1];
				tBody.insertBefore(addTr,nextNextTr);
				flag="2";
				break;
			}
	   }
	   if(flag=='1'){
	      var td0=document.createElement("td");
	      td0.innerHTML=values[1];
	      var td4=document.createElement("td");
	      var a41=document.createElement("a");
	      a41.href="#";
	      a41.innerHTML="<@msg.message key="action.add"/>";
	      a41.onclick=function(e){
	      	if(!e){
	      		e=window.event;
	      	}
	      	var aa = e.srcElement;
	      	var trtr=aa.parentNode.parentNode;
	      	var questionTypeId = trtr.cells[1].childNodes[1].value;
	      	var url="questionnaire.do?method=searchQuestion&questionTypeId="+questionTypeId+"&questionSeq="+questionIds.substring(1,questionIds.length);
		 	window.open(url,'','scrollbars=yes,width=720,height=480,left=200,top=200,status=yes');
	      }
	      td4.appendChild(a41);
	      addTr.appendChild(td4)
	   	  addTr.insertBefore(td0,td1);
	   	  var endTr = document.getElementById("endTr");
	   	  tBody.insertBefore(addTr,endTr);
	   }
	   questionIds = questionIds.concat(values[0]+',');
	   f_frameStyleResize(self);
	}
	
	function  addAllquestions(){
		var url="questionnaire.do?method=searchQuestion&questionSeq="+questionIds.substring(1,questionIds.length);
		window.open(url,'','scrollbars=yes,width=720,height=480,left=200,top=200,status=yes');
	}

	function doValidate(){
		var errors ="";
		var flag="true";
		var simpleDescribe = document.questionnaireForm['questionnaire.description'].value;
		if(simpleDescribe==""||simpleDescribe.length<1){
			errors+="请填写问卷的描述\n";
		}
		var deparmentId = document.questionnaireForm['questionnaire.depart.id'].value;
		if(deparmentId==""||deparmentId.length<1){
			errors+="请选择一个问卷制作部门\n";
		}
		var stateValue = getCheckBoxValue(document.getElementsByName("questionnaire.state"));
		if(stateValue==""||stateValue.length<1){
			errors+="请选择一个该问卷的一个状态\n";
		}
		var remarkValue=document.questionnaireForm['questionnaire.remark'].value;
		if(remarkValue.length>500){
			errors+="请不要超过500个字符\n";
		}
		var title = document.questionnaireForm['questionnaire.title'].value;
		if(title.length>1000){
			errors+"表格标题请不要超过1000个字符\n";
		}
		if(questionIds.lastIndexOf(",")==-1){
			errors+="请选择一些问题\n";
		}
		if(errors!=""){
			alert(errors);
			flag=false;
		}
		return flag;
	}
		function save(){
		if(!doValidate()){
			return;
		}
		document.questionnaireForm.action="questionnaire.do?method=save";
		addInput(document.questionnaireForm,"questionnaire.questionIds",questionIds.substring(1,questionIds.length-1))
		document.questionnaireForm.submit();
	}
</script>
<BODY LEFTMARGIN="0" TOPMARGIN="0" onload="mergeTableTd('tbodyId',0,3)">
<#assign labInfo><#if flag?exists&&flag="update">更新<#else>添加</#if>评教问卷</#assign>
<table id="backBar"></table>
<script>
   		var bar = new ToolBar('backBar','${labInfo}',null,true,true);
   		bar.setMessage('<@getMessage/>');
   		bar.addItem('添加问题','addAllquestions()','contract.gif','添加问题');
   		bar.addBack('<@msg.message key="action.back"/>');
</script>
<table id="tableName" align="center" class="formTable" width="100%">
	<form name="questionnaireForm" method="post">
	<tr align="left">
	<td class="title">问卷描述<font color="red">*</font>:</td>
	<td><input type="text" name="questionnaire.description" value="${(questionnaire.description)?if_exists}" style="width:150px;"></td>
	<td class="title">问卷部门<font color="red">*</font>:</td>
	<td><@htm.i18nSelect datas=departments selected=(questionnaire.depart.id)?default(0)?string name="questionnaire.depart.id" style="width:150px;"/></td>
	</tr>
	<tr>
		<td class="title">问卷状态<font color="red">*</font>:</td>
		<td><@htm.radio2 name="questionnaire.state" value=questionnaire.state?default(false)/></td>
		
		<td valign="center" class="title">选项组<font color="red">*</font></td>
		<td><@htm.i18nSelect datas=optionGroups selected=(questionnaire.optionGroup.id)?default(0)?string name="questionnaire.optionGroup.id" style="width:300px;"/></td>
		</tr>
		<tr>
			<td valign="center" class="title">问卷表头</td>
			<td colspan="3"><textarea name="questionnaire.title" rows="3" cols="200" style="width:600px;">${(questionnaire.title)?if_exists}</textarea></td>
		</tr>
		<td class="title">备注:</td>
		<td colspan="3"><input type="text" name="questionnaire.remark" value="" style="width:100%"></td>
		<tr>
			<td colspan="4">
			<table width="100%" class="listTable">
				<tr align="center" class="darkColumn">
					<td><@msg.message key="field.question.questionType"/></td>
					<td><@msg.message key="field.question.questionContext"/></td>
					<td colspan="2">操作</td>
				</tr>
			<tbody id="tbodyId">
					<#list questions as question>
					<#if question_index%2==1 ><#assign class="grayStyle" ></#if>
	   				<#if question_index%2==0 ><#assign class="brightStyle" ></#if>
					<tr class="${class}" id="question${question_index}">
						<td width="80" align="center"> ${question.type.name}</td>
						<td width="400" align="left">${question.content}</td>
						<td><a href="#" onclick="removeTr('tbodyId','question${question_index}','${question.id}','${question.type.name}')"><@msg.message key="action.delete"/></a></td>
						<td><a href="#" onclick="addTr('${(question.type.id)?if_exists}')">添加</a></td>
					</tr>
					</#list>
				<tr id="endTr" class="darkColumn" align="center">
					<td colspan="5" height="25px;">
							<input type="hidden" name="questionnaire.id" value="${(questionnaire.id)?if_exists}">
							<button name="button1" onclick="save()"><#if (questionnaire.id)?exists>更新<#else>添加</#if></button>
						</td>
				</tr>
			</tbody>
			</table>
			</td>
		</tr>
	</form>
<tr>
	<td colspan="4">
	<font color="red">
		 1.操作完毕，请注意保存<br>
		 2.<b>可以不填写问卷表头</b>,问卷表头主要是在学生评教的时候显示在问卷上面给学生看,填写表头信息时请注意用enter键换行</font>
	</td>
	</tr>
</table>
</body>
<#include "/templates/foot.ftl"/>