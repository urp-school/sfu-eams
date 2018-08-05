<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" onLoad="f_frameStyleResize(self)">
	<table id="backBar" width="100%"></table>
  	<@table.table widht="100%">
  		<@table.thead>
  			<@table.selectAllTd id="annotateId"/>
  			<@table.td name="attr.stdNo"/>
  			<@table.td name="attr.personName"/>
  			<@table.td text="论文编号"/>
  			<@table.td text="成绩"/>
  			<@table.td text="平均成绩"/>
  			<@table.td text="学位水平"/>
  			<@table.td text="是否同意答辩"/>
  			<@table.td text="评阅人姓名"/>
  			<@table.td text="评阅人单位"/>
  		</@>
  		<form name="listForm" method="post" action="" onsubmit="return false;">
  		<@table.tbody datas=annotateBooks?sort_by("serial");annotateBook>
  			<@table.selectTd id="annotateId" value=annotateBook.id/>
  			<td>${(annotateBook.thesisManage.student.code)?if_exists}</td>
			<td>${(annotateBook.thesisManage.student.name)?if_exists}</td>
			<td>${annotateBook.serial?if_exists}</td>
			<td><input type="text" name="mark${annotateBook.id}" value="<#if annotateBook.evaluateIdea?if_exists.mark?exists>${annotateBook.evaluateIdea?if_exists.mark?string("##0.0")}</#if>" style="width:60px;" onblur="selectLevel(this)"></td>
			<td><input type="text" name="avg${annotateBook.id}" value="<#if annotateBook.thesisManage?if_exists.annotate?if_exists.avgMark?exists>${annotateBook.thesisManage?if_exists.annotate?if_exists.avgMark?string("##0.0")}</#if>" style="width:60px;"></td>
			<td>
			<#assign level=annotateBook.evaluateIdea?if_exists.learningLevel?if_exists/>
			<select name="level${annotateBook.id}" style="width:60px;">
				<option value="">请选择</option>
				<option value="A" <#if level?exists&&level=="A">selected</#if>>优秀</option>
				<option value="B" <#if level?exists&&level=="B">selected</#if>>良好</option>
				<option value="C" <#if level?exists&&level=="C">selected</#if>>一般</option>
				<option value="D" <#if level?exists&&level=="D">selected</#if>>不及格</option>
			</select>
			</td>
			<td>
				<#assign isReply=annotateBook.isReply?if_exists/>
				<select name="isReply${annotateBook.id}" style="width:60px;">
					<option value="">请选择</option>
					<option value="A" <#if isReply?exists&&isReply=="A">selected</#if>>同意</option>
					<option value="M" <#if isReply?exists&&isReply=="M">selected</#if>>修改后答辩</option>
					<option value="D" <#if isReply?exists&&isReply=="D">selected</#if>>不同意</option>
				</select>
			</td>
			<td><input type="text" name="evaluateName${annotateBook.id}" maxlength="30" value="<#if (annotateBook.answerTem.name)?exists>${annotateBook.answerTem.name}</#if>" style="width:60px;"></td>
			<td><input type="text" name="departName${annotateBook.id}" maxlength="20" value="<#if (annotateBook.answerTem.depart)?exists>${annotateBook.answerTem.depart}</#if>" style="width:60px;"></td>
  		</@>
  		<tr class="darkColumn">
			<input type="hidden" name="annotateBookIdSeq" value="${annotateBookIdSeq?if_exists}">
			<td colspan="10" align="center"><button name="button1" onclick="doSave()" class="buttonStyle"><@msg.message key="action.save"/></button></td>
		</tr>
  		<tr>
			<td colspan="10">备注:<br>
				1.成绩必须要填<br>
				2.平均成绩可以不填<br>
				3.学位水平和是否同意答辩都是可不填
			</td>
		</tr>
		</form>
  	</@>
<script>
   var bar = new ToolBar('backBar','录入论文成绩',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("保存", "doSave()");
   bar.addBack('<@bean.message key="action.back"/>');
   
   function selectLevel(input){
        var mark =0;
        if(""!=input.value){
        	mark = new Number(input.value);
        }
   		var levelName = input.name.replace(/mark/,"level");
   		if(90<=mark){
   			form[levelName].options[1].selected=true
   		}else if(75<=mark){
   			form[levelName].options[2].selected=true;
   		}else if(60<=mark){
   		    form[levelName].options[3].selected=true;
   		}else if(0<mark){
   		     form[levelName].options[4].selected=true;
   		}
   }
   var form = document.listForm;
	function doSave(){
		var elems =form.elements;
		for(i =0;i < elems.length; i++){
			var errors="";
			if(elems[i].name.indexOf("mark")>-1){
				errors="成绩";
			}else if(elems[i].name.indexOf("avg")>-1){
				errors="平均成绩";
			}else{
				continue;
			}
			if(/^[\+\-]?\d*\.?\d*$/.test(elems[i].value)){
				continue;
			}else{
				elems[i].focus();
				alert(errors+elems[i].value+"只能是数字")
				return;
			}
		}
		form.action="annotateAdmin.do?method=doInsertIntoMark&isInsert=insert";
		setSearchParams(parent.document.bookListForm,form);
		form.submit();
	}
</script>
</body>
<#include "/templates/foot.ftl"/>