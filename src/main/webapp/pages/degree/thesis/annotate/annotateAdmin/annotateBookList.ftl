<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" onload="f_frameStyleResize(self)">
<#assign doClick>confiSeirlNo()</#assign>
<#include "configCountSeirlNo.ftl"/>
  <table id="backBar"></table>
  	<@table.table widht="100%" id="annotateList" sortable="true">
  		<@table.thead>
  			<@table.selectAllTd id="annotateId"/>
  			<@table.sortTd name="attr.stdNo" id="annotateBook.thesisManage.student.code"/>
  			<@table.sortTd name="attr.personName" id="annotateBook.thesisManage.student.name"/>
  			<@table.sortTd name="entity.speciality" id="annotateBook.thesisManage.student.firstMajor.name"/>
  			<@table.sortTd text="导师姓名" id="annotateBook.thesisManage.student.teacher.name"/>
  			<@table.sortTd text="论文题目" id="annotateBook.thesisManage.thesis.name"/>
  			<@table.sortTd text="论文编号" id="annotateBook.serial"/>
  			<@table.sortTd text="成绩" id="annotateBook.evaluateIdea.mark"/>
  			<@table.sortTd text="平均成绩" id="annotateBook.thesisManage.annotate.avgMark"/>
  			<@table.sortTd text="学位水平" id="annotateBook.evaluateIdea.learningLevel"/>
  			<@table.sortTd text="是否同意答辩" id="annotateBook.isReply"/>
  			<@table.sortTd text="评阅人姓名" id="annotateBook.answerTem.name"/>
  			<@table.sortTd text="评阅人单位" id="annotateBook.answerTem.depart"/>
  		</@>
  		<@table.tbody datas=annotatePagination;annotateBook>
  			<@table.selectTd id="annotateId" value=annotateBook.id/>
  			<td>${(annotateBook.thesisManage.student.code)?if_exists}</td>
  			<td><@i18nName (annotateBook.thesisManage.student)?if_exists/></td>
  			<td><@i18nName (annotateBook.thesisManage.student.firstMajor)?if_exists/></td>
  			<td><@i18nName (annotateBook.thesisManage.student.teacher)?if_exists/></td>
  			<td>${(annotateBook.thesisManage.thesis.name)?if_exists}</td>
  			<td>${annotateBook.serial?if_exists}</td>
  			<td><#if (annotateBook.evaluateIdea.mark)?exists>${(annotateBook.evaluateIdea.mark)?if_exists?string("##0.0")}</#if></td>
  			<td><#if (annotateBook.thesisManage.annotate.avgMark)?exists>${(annotateBook.thesisManage.annotate.avgMark)?if_exists?string("##0.0")}</#if></td>
  			<td><#if (annotateBook.evaluateIdea.learningLevel)?exists><#if annotateBook.evaluateIdea.learningLevel=="A">优秀<#elseif annotateBook.evaluateIdea.learningLevel=="B">良好<#elseif annotateBook.evaluateIdea.learningLevel=="C">一般<#elseif annotateBook.evaluateIdea.learningLevel=="D">不及格</#if></#if></td>
  			<td><#if (annotateBook.isReply)?exists><#if annotateBook.isReply=="A">同意<#elseif annotateBook.isReply=="M">修改后答辩<#elseif annotateBook.isReply=="D">不同意</#if></#if></td>
  			<td>${(annotateBook.answerTem.name?html)?if_exists}</td>
  			<td>${(annotateBook.answerTem.depart?html)?if_exists}</td>
  		</@>
  	</@>
  	 <form name="pageGoForm" action="annotateAdmin.do?method=doAnnotateBooks" method="post" onsubmit="return false;">
  	 	<input type="hidden" name="keys" value="student.code,student.name,student.firstMajor.name,thesisTopicOpen,thesiAnnotateBook.serial,mark,avgMark,degreeLevelName,isPassedName">
  	 	<input type="hidden" name="titles" value="学号,姓名,专业,论文题目,论文编号,成绩,平均成绩,学位水平,是否同意答辩">
	</form>	
<script>
   	var bar = new ToolBar('backBar','学生评阅书列表',null,true,true);
   	bar.setMessage('<@getMessage/>');
   	<#if flag?exists&&flag=="admin">
   		bar.addItem("<@msg.message key="action.export"/>","doExp()");
   		bar.addItem("重新计算平均成绩","displayConfig()");
   		bar.addItem("选择计算平均成绩","caclateAvgScore()");
   		bar.addItem("录入分数","insertMark()");
   	</#if>
	var form = document.pageGoForm;
   	function displayConfig(){
   		var divConfig = document.getElementById("configDiv");
   		if(divConfig.style.display=="none"){
   			divConfig.style.display="block";
   			f_frameStyleResize(self);
   		}
   		else divConfig.style.display="none";
   	}
   	function caclateAvgScore(){
   		var ids = getIds("annotateId");
 		if(""==ids){
 			alert("请选择一些评阅书");
 			return;
 		}
 		form.action="annotateAdmin.do?method=calcAvgMark";
 		addInput(form,"annotateBookIdSeq",ids);
 		setSearchParams(parent.document.bookListForm,form);
 		form.submit();
   	}
   	function confiSeirlNo(){
   		var startNo = document.getElementById("startNo");
   		if(""==startNo){
   			alert("开始编号必填");
   			return;
   		}
   		var endNo = document.getElementById("endNo");
   		if(""==endNo){
   			alert("结束编号必填");
   			return;
   		}
   		document.conditionForm.action="annotateAdmin.do?method=doCalendarAvgMark";
   		setSearchParams(parent.document.bookListForm,document.conditionForm);
   		addInput(form,"pageNo",getPageNo());
   		addInput(form,"pageSize",getPageSize());
   		document.conditionForm.submit();
   	}
	function pageGoWithSize(number,pageSize){
       parent.bookList(number,pageSize);
    }
 	function getIds(name){
       return(getCheckBoxValue(document.getElementsByName(name)));
    }
 	function insertMark(){
 		var ids = getIds("annotateId");
 		if(""==ids) {
 			alert("请选择一些评阅书");
 			return;
 		}
 		form.action="annotateAdmin.do?method=doInsertIntoMark";
 		setSearchParams(parent.document.bookListForm,form);
 		addInput(form,"isInsert","load");
 		addInput(form,"annotateBookIdSeq",ids);
 		form.submit();
 	}
 	function doExp(){
 		form.action="annotateAdmin.do?method=export&flag=bookList";
	  	<#list RequestParameters?keys as key>
	  		addInput(form, "${key}", "${RequestParameters[key]}", "hidden");
	    </#list>
 		form.submit();
 	}
</script>
</body>
<#include "/templates/foot.ftl"/>