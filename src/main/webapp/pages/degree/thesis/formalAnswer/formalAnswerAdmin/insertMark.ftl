<#include "/templates/head.ftl"/>
<script>
    function  insertMark(){
    	var elems =document.listForm.elements;
		for(i =0;i < elems.length; i++){
			var errors="";
			if(elems[i].name.indexOf("mark")==-1){
				continue;
			}
			var tempId = elems[i].name.replace(/mark/gi,"stdName");
			errors=document.getElementById(tempId).value+"的分数";
			if(/^[\+\-]?\d*\.?\d*$/.test(elems[i].value)){
				continue;
			}else{
				elems[i].focus();
				alert(errors+"只能是数字,现在是"+elems[i].value)
				return;
			}
		}
    	document.listForm.action="formalAnswerAdmin.do?method=doInsertMark&isInsert=insert";
    	setSearchParams(parent.form,document.listForm);
    	document.listForm.submit();
    }
</script>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','录入答辩分数',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("提交成绩","insertMark();")
   bar.addBack();
</script>
<table width="100%" align="center" class="listTable">
<form name="listForm" method="post" action="" onSubmit="return false;">
	<tr align="center" class="darkColumn">
		<td><@bean.message key="attr.stdNo"/></td>
		<td><@bean.message key="attr.personName"/></td>
		<td><@bean.message key="entity.college"/></td>
      	<td><@bean.message key="entity.speciality"/></td>
      	<td>论文题目</td>
      	<td width="8%">分数</td>
	</tr>
	<#assign class="grayStyle">
	<#list formalAnswers?if_exists as formalAnswer>
		<tr class="${class}" align="center">
			<#if class="grayStyle"><#assign class="brightStyle"><#else><#assign class="grayStyle"></#if>
			<td>${formalAnswer.student?if_exists.code?if_exists}</td>
			<td><input type="hidden" id="stdName${formalAnswer.id}" name="stdName${formalAnswer.id}" value="${formalAnswer.student?if_exists.name?if_exists}">${formalAnswer.student?if_exists.name?if_exists}</td>
			<td>${formalAnswer.student?if_exists.department?if_exists.name?if_exists}</td>
			<td>${formalAnswer.student?if_exists.firstMajor?if_exists.name?if_exists}</td>
			<td>${(formalAnswer.thesisManage.topicOpen.topicOpenName)?if_exists}</td>
			<td width="5%"><input type="text" name="mark${formalAnswer.id}" maxlength="5" value="${formalAnswer.formelMark?if_exists}" style="width:100%"></td>
		</tr>
	</#list>
	<tr class="darkColumn" align="center">
		<input type="hidden" name="formalAnswerIds" value="${formalAnswerIdSeq?if_exists}">
		<td colspan="6"><button name="button1" onclick="insertMark();" class="buttonStyle">提交成绩</button></td>
	</tr>
</form>
</table>
</body>   
<#include "/templates/foot.ftl"/>