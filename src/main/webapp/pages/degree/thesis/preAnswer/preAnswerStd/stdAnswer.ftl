<#include "/templates/head.ftl"/>
<body>
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','博士研究生学位论文预答辩情况表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("申请预答辩","apply()");
   bar.addItem("下载预答辩模板","downloadTemplate('preAnswer','D')");
   bar.addItem("打印设置","displayConfig()");
   bar.addPrint();
</script>
<#include "printConfig.ftl"/>
<table width="100%" class="formTable" align="center">
 <form name="listForm" method="post" action="" onSubmit="return false;">
  <caption>
     <h2>博士研究生学位论文预答辩申请及备案情况</h2>
  </caption>
  <tr>
  		<td colspan="4" class="darkColumn" align="center">学生预答辩基本信息</td>
  </tr>
  <tr>
    <td width="18%" class="title" align="center">申请人姓名:</td>
    <td>${student.name?if_exists}</td>
    <td width="14%" class="title" align="center"><@msg.message key="attr.stdNo"/>:</td>
    <td>${student?if_exists.code?if_exists}</td>
  </tr>
  <tr>
    <td class="title" align="center">所在院系所:</td>
    <td>${student.department.name?if_exists}</td>
    <td class="title" align="center"><@msg.message key="entity.speciality"/>:</td>
    <td>${(student.firstMajor.name)?if_exists}</td>
  </tr>
  <tr>
    <td class="title" align="center">研究方向:</td>
    <td>${(student.firstAspect.name)?if_exists}</td>
    <td class="title" align="center">导师</td>
    <td>${(student.teacher.name)?if_exists}</td>
  </tr>
  <tr>
    <td class="title" align="center">联系电话</td>
    <td colspan="3">${(student.basicInfo.phone)?if_exists}</td>
  </tr>
  <tr>
    <td class="title" align="center">论文题目:</td>
    <td colspan="3">${(topicOpen.topicOpenName)?if_exists}</td>
  </tr>
  <tr>
  	  <td class="title" align="center">预答辩时间</td>
  	  <td><#if (preAnswer.answerTime)?exists>${preAnswer.answerTime?string("yyyy-MM-dd")}</#if></td>
  	  <td class="title" align="center">预答辩地点</td>
  	  <td>${(preAnswer.answerTime)?if_exists}</td>
  </tr>
  <tr>
      <td class="title" align="center">预答辩申请提交时间:</td>
      <td><#if (preAnswer.finishOn)?exists>${preAnswer.finishOn?string("yyyy-MM-dd")}</#if>
      </td>
      <td class="title" align="center">导师是否同意答辩:</td>
    <td>
      	<#if (preAnswer.isTutorAffirm)?exists><#if "true"==preAnswer.isTutorAffirm?string>导师同意预答辩<#elseif "false"==preAnswer.isTutorAffirm?string>导师不同意预答辩</#if></#if>
    </td>
  </tr>
  <tr>
    <td class="title" align="center">专家意见和建议:</td>
    <td colspan="3">${(preAnswer.advice)?if_exists}
    </td>
  </tr>
  <tr>
    <td class="title" align="center">预答辩专家评价结果:</td>
    <td>${((preAnswer.isPassed)?string("预答辩合格，论文修订后送双盲评审。","预答辩不合格，修改论文。"))?default("")}</td>
    <td>预答辩次数</td>
    <td><#if (preAnswer.answerNum)?exists>第${preAnswer.answerNum}次</#if></td>
  </tr>
  <tr>
  	<td colspan="4" height="25px;" class="darkColumn" ></td>
  	<form name="listForm" method="post">
  		<input type="hidden" id="num" name="applyNum" value="${(preAnswer.answerNum)?default(0)+1}">
  	</form>
  </tr>
</table>
<p>
   1.如果以上的信息有错误,在<a href="stdDetail.do?method=index">个人信息</a>修改学生基本信息,在<a href="loadThesisTopic_std.do?method=doLoadThesisTopic">论文开题</a>修改论文题目.<br>
   2.修改论文题目，请返回<a href="loadThesisTopic_std.do?method=doLoadThesisTopic">论文开题</a>中，在<a href="loadThesisTopic_std.do?method=topicOpenInfo&topicOpenFlag=openInfo">录入开题结果</a>中修改。
</p>
<script>
    var form = document.listForm;
	function apply(){
	    if(<#if preAnswer.id?exists>true<#else>false</#if>){
	    	var num = new Number(document.getElementById("num").value);
	    	if(num>3){
	    		alert("你最多只能申请三次,现在已经到达上限");
	    		return;
	    	}
	    	if(!confirm("你确定要提交你的第"+num+"次论文预答辩吗?")){
	    		return;
	    	}
	    }
		if(!confirm("你确认要提交的信息吗?")){
				return;
		}
		form.action="preAnswer_std.do?method=apply";
		form.submit();
	}
	function displayConfig(){
   		var divConfig = document.getElementById("configDiv");
   		if(divConfig.style.display=="none"){
   			divConfig.style.display="block";
   			f_frameStyleResize(self);
   		}
   		else divConfig.style.display="none";
   	}
	function previewPrint(){
		var id =getIdByName("preAnswerNum");
		if(""==id){
			alert("请选择你要打印的次数");
			return;
		}
		document.printConfigForm.action="preAnswer_std.do?method=previewPrint";
		document.printConfigForm.submit();
	}
	function getIdByName(name){
		return getRadioValue(document.getElementsByName(name));
	}
	function downloadTemplate(type,value){
   	  if(<#if (preAnswer.id)?exists>false<#else>true</#if>){
   	  	alert("请在申请完预答辩以后下载模板");
   	  	return;
   	  }
   	  form.action="thesisManageStd.do?method=export";
   	  addInput(form,"template",type+"_"+value+".xls");
   	  addInput(form,"fileName","${student.name}的预答辩表");
   	  form.submit();
   }
</script>
</body>
<#include "/templates/foot.ftl"/>