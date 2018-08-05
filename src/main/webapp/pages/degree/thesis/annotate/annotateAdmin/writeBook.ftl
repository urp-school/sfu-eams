<#include "/templates/head.ftl"/>
<script language="JavaScript" src="scripts/Menu.js"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<script>
<#assign bookSize=bookSet?if_exists?size>
	var arrList = new Array();
	<#list 1..bookSize as seq>
	    arrList[${seq}]=new Array();
		arrList[${seq}][0]={
			'annotateBook${seq}.answerTem.name':{'l':'<@bean.message key="filed.annotatePerson" />', 'r':true, 't':'f_name${seq}','mx':100},
          	'annotateBook${seq}.answerTem.depart':{'l':'<@bean.message key="filed.annotateCompany" />', 'r':true, 't':'f_depart${seq}','mx':100},
          	'annotateBook${seq}.thesisAppraise':{'l':'<@bean.message key="filed.thesisAnnotate" />', 'r':true, 't':'f_thesisAppraise${seq}','mx':1000},
          	'annotateBook${seq}.isReply':{'l':'<@bean.message key="filed.canAnswer" />', 'r':true, 't':'f_reply${seq}','mx':1},
          	'annotateBook${seq}.innovateOne':{'l':'创新点(一)','r':true, 't':'f_innovateOne${seq}'},
          	'annotateBook${seq}.innovateTwo':{'l':'创新点(二)','r':true, 't':'f_innovateTwo${seq}'},
          	'annotateBook${seq}.innovateThree':{'l':'创新点(三)','r':true, 't':'f_innovateThree${seq}'},
          	'annotateBook${seq}.lack':{'l':'不足之处','r':true, 't':'f_lack${seq}'},
 			<#if studentFlag?exists&&studentFlag=="doctor">
 			'annotateBook${seq}.evaluateIdea.topicMeaning':{'l':'选题意义','r':true, 't':'f_topicMeaning${seq}'},
 			'annotateBook${seq}.evaluateIdea.lteratureSumUp':{'l':'文献综述','r':true, 't':'f_lteratureSumUp${seq}'},
 			'annotateBook${seq}.evaluateIdea.researchHarvest':{'l':'研究成果','r':true, 't':'f_researchHarvest${seq}'},
 			'annotateBook${seq}.evaluateIdea.operationLevel':{'l':'业务水平','r':true, 't':'f_operationLevel${seq}'},
 			'annotateBook${seq}.evaluateIdea.composeAbility':{'l':'写作能力与学风','r':true, 't':'f_composeAbility${seq}'},
 			'annotateBook${seq}.evaluateIdea.learningLevel':{'l':'写作能力与学风','r':true, 't':'f_learningLevel${seq}'},
 			'annotateBook${seq}.evaluateIdea.mark':{'l':'论文分数', 'f':'real','r':true, 't':'f_thesisMark${seq}','mx':4},
 			</#if>
 			'hiddenValue':{'l':'隐藏','r':true, 't':'f_hidden'}
		};
	</#list>
 function doSubmit(form,count){
     var v = new validator(form,arrList[count][0], null);
     var url="annotateAdmin.do?method=doWriteBook&booksNumber=1&idSeq="+count;
     <#if studentFlag?exists&&studentFlag=="doctor">
     	url+="&avgMark="+getAvgMark();
     </#if>
     	if (v.exec()) {
     	 	form.action=url;
     	 	form.submit();
     	}
     }
 function doAllSubmit(form){
 	for(var i=1;i<arrList.length-1;i++){
 		var v = new validator(form,arrList[i][0], null);
 		if(!v.exec()){
 			return;
 		}
 	}
 	url="annotateAdmin.do?method=doWriteBook&booksNumber="+(arrList.length-1);
 	<#if studentFlag?exists&&studentFlag=="doctor">
     	url+="&avgMark="+getAvgMark();
     </#if>
 	form.action=url;
    form.submit();
 }
 function getAvgMark(){
 	var avgMark=new Number(0);
 	var countNoNumber = new Number(0);
 	<#if studentFlag?exists&&studentFlag=="doctor">
     	<#list 1..bookSize as seq>
     		var markValue = document.pageGoForm['annotateBook${seq}.evaluateIdea.mark'].value;
     		if(""==markValue){
     			countNoNumber++;
     		}
     		avgMark+=new Number(markValue);
     	</#list>
     	if(${bookSize}>countNoNumber){
     		avgMark=avgMark/(${bookSize}-countNoNumber);
     	}
    </#if>
    return avgMark;
 }
 function setDate(params){
 	var paramters = document.getElementById("paramters")
 	paramters.value=params;
 	alert(params);
 }
</script>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
<#assign labInfo>填写论文评阅书</#assign>
			<table id="backBar"></table>
		<script>
		   var bar = new ToolBar('backBar','${labInfo}',null,true,true);
		   bar.setMessage('<@getMessage/>');
		   bar.addItem("全部更新","doAllSubmit(document.pageGoForm)","detail.gif","全部更新");;
		   bar.addBack('<@bean.message key="action.back"/>');
		</script>
<table width="100%" align="center">
		<form name="pageGoForm" method="post" action="" onsubmit="return false;">
  		<input name="booksNumber" type="hidden" value="${bookSet?if_exists?size}">
  		<input name="thesisId" type="hidden" value="${thesisManage?if_exists.id}">
  		<input id="paramters" name="parameters" type="hidden" value="${parameters?if_exists}">
  		<#list bookSet?if_exists?sort_by("serial") as book>
  		<tr>
  			<td>
  			<#assign idSeq=book_index+1>
  			<#assign annotateBook=book>
  			<h3 align="center">
        		<B><@bean.message key="filed.annotateThesisBook" />${idSeq}</B>
      		</h3>
  			<#include "../selfEvaluateforTeacher.ftl"/>
  			<#if studentFlag?exists&&studentFlag=="doctor">
  				<#include "../doctorEvaluateAdvice.ftl"/>
  			</#if>
  			<#include "../thesisAnnotateBook.ftl"/>
  			</td>
  			
  		</tr>
  		<tr>
  			<td align="center">
  				<input type="button" name="button1" value="更新" onclick="doSubmit(this.form,${idSeq})" class="buttonStyle">
  			</td>
  		</tr>
  		</#list>
  		<tr>
  			<td align="center" id="f_hidden">
  				<input type="hidden" name="hiddenValue" value="1">
  			</td>
  		</tr>
  	<form>
</table>
</body>
<#include "/templates/foot.ftl"/>
  