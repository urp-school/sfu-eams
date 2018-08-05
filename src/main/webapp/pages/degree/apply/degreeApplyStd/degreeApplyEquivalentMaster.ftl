<#include "/templates/head.ftl"/>
<body>
<table id="backBar" width="100%"></table>

<script>
   var bar = new ToolBar('backBar','申请学位－同等学力学位数据表',null,true,true);
   bar.setMessage('<@getMessage/>');
   <#if "std"==flag?if_exists>
   bar.addItem("下载所有模板","downAll()","download.gif");
   bar.addItem("下载个人学位数据表","downloadTemplate('degreeInfo','E')");
   bar.addItem("<#if (degreeApply.id)?exists>重新申请<#else>申请学位</#if>","doSubmit()");
	bar.addPrint("<@msg.message key="action.print"/>");
</#if>
	${otherJS?if_exists}
</script>

<body>
<h2 align="center">授予同等学力硕士学位基本数据表</h2>
<table width="100%" class="listTable">
	<tr>
		<td align="center" rowspan="3">学<br>科<br>信<br>息</td>
		<td width="12%">授学位单位</td>
		<td colspan="3">${(systemConfig.school.name)?if_exists}</td>
		<td width="12%">一级学科</td>
		<td colspan="3">${(level2Subject.level1Subject.name)?if_exists}</td>
	</tr>
	<tr>
		<td>获硕士学位类别</td>
		<td colspan="3">${(student.firstMajor.subjectCategory.name)?if_exists}</td>
		<td>二级学科</td>
		<td colspan="3">${(student.firstMajor.name)?if_exists}</td>
	</tr>
	<tr>
		<td>学科门类</td>
		<td colspan="3">${(student.firstMajor.subjectCategory.name)?if_exists}</td>
		<td>自主设置专业名称</td>
		<td colspan="3"></td>
	</tr>
	<tr>
		<td align="center" rowspan="4">个<br>人<br>基<br>本<br>信<br>息</td>
		<td>姓名</td>
		<td width="12%">${(student.name)?if_exists}</td>
		<td width="12%">性别</td>
		<td width="12%">${(student.basicInfo.gender.name)?if_exists}</td>
		<td>民族</td>
		<td width="12%">${(student.basicInfo.nation.name)?if_exists}</td>
		<td rowspan="3" colspan="2" align="center" valign="middle">数码照片</td>
	</tr>
	<tr>
		<td>国家或地区</td>
		<td>${(student.basicInfo.country.name)?if_exists}</td>
		<td>籍贯</td>
		<td>${(student.basicInfo.ancestralAddress)?if_exists}</td>
		<td>政治面貌</td>
		<td>${(student.basicInfo.politicVisage.name)?if_exists}</td>
	</tr>
	<tr>
		<td>出生日期</td>
		<td>${(student.basicInfo.birthday)?if_exists}</td>
		<td colspan="2">入学前（前置）最高学历</td>
		<td colspan="2">${(student.studentStatusInfo.educationBeforEnroll.name)?if_exists}</td>
	</tr>
	<tr>
		<td>身份证件号码</td>
		<td colspan="3">${(student.basicInfo.idCard)?if_exists}</td>
		<td>联系电话</td>
		<td colspan="3">${(student.basicInfo.phone)?if_exists}</td>
	</tr>
	<tr>
		<td align="center" rowspan="2">学<br>业<br>信<br>息</td>
		<td>导师姓名</td>
		<td>${(student.teacher.name)?if_exists}</td>
		<td>导师职称</td>
		<td colspan="2">${(student.teacher.title.name)?if_exists}</td>
		<td>获硕士学位日期</td>
		<td colspan="2">${(student.degreeInfo.master.graduateOn)?if_exists}</td>
	</tr>
	<tr>
		<td>学位证书号</td>
		<td colspan="4">${(student.degreeInfo.certificateNo)?if_exists}</td>
		<td colspan="2">是否按一级学科授予</td>
		<td width="12%">否</td>
	</tr>
	<tr>
		<td align="center" rowspan="2">学<br>历<br>信<br>息</td>
		<td>本科毕业院校</td>
		<td>${(student.degreeInfo.undergraduate.school.name)?if_exists}</td>
		<td colspan="2">获学士学位专业</td>
		<td colspan="4">${(student.degreeInfo.undergraduate.specialityName)?if_exists}</td>
	</tr>
	<tr>
		<td>授学士学位单位</td>
		<td>${(student.degreeInfo.undergraduate.school.name)?if_exists}</td>
		<td colspan="2">获学士学位门类</td>
		<td>${(student.degreeInfo.undergraduate.subjectCategory.name)?if_exists}</td>
		<td colspan="2">获学士学位日期</td>
		<td>${(student.degreeInfo.undergraduate.graduateOn)?if_exists}</td>
	</tr>
	<tr>
		<td align="center" rowspan="5">学<br>位<br>论<br>文<br>信<br>息</td>
		<td>硕士学位论文题目</td>
		<td colspan="7">${(thesisManage.topicOpen.topicOpenName)?if_exists}</td>
	</tr>
	<tr>
		<td>硕士学位论文主题词</td>
		<td colspan="7">${(thesisManage.topicOpen.content.keyWords)?if_exists}</td>
	</tr>
	<tr>
		<td>论文类型</td>
		<td>${(thesisManage.topicOpen.thesisType.name)?if_exists}</td>
		<td colspan="2">论文选题来源</td>
		<td>${(thesisManage.topicOpen.taskSource.thesisSourse.name)?if_exists}</td>
		<td colspan="2">论文字数（单位/万）</td>
		<td>${(thesisManage.thesis.thesisNum?string("0.0"))?if_exists}</td>
	</tr>
	<tr>
		<td>论文开始日期</td>
		<td colspan="2">${(thesisManage.thesis.startOn?string("yyyy-MM-dd HH:mm:ss"))?if_exists}</td>
		<td colspan="2">论文答辩日期</td>
		<td colspan="3">${(thesisManage.formalAnswer.time?string("yyyy-MM-dd HH:mm:ss"))?if_exists}</td>
	</tr>
	<tr>
		<td>综合考试年份</td>
		<td>${(student.degreeInfo.equivalent.examOn)?if_exists}</td>
		<td>统考外国语语种</td>
		<td colspan="2">${(student.degreeInfo.equivalent.language)?if_exists}</td>
		<td>外语统考年份</td>
		<td colspan="2">${(student.degreeInfo.equivalent.languageOn)?if_exists}</td>
	</tr>
	<tr>
		<td align="center" rowspan="4">其<br>他<br>信<br>息</td>
		<td>工作单位</td>
		<td colspan="2">${(student.basicInfo.workPlace)?if_exists}</td>
		<td>通信地址</td>
		<td colspan="2">${(student.basicInfo.workAddress)?if_exists}</td>
		<td width="12%">邮编</td>
		<td>${(student.basicInfo.workPlacePostCode)?if_exists}</td>
	</tr>
	<tr>
		<td>行政职务</td>
		<td colspan="2"></td>
		<td>职称</td>
		<td colspan="2"></td>
		<td>工作年限</td>
		<td></td>
	</tr>
	<tr>
		<td colspan="2">申请学位日期</td>
		<td colspan="2">${(student.degreeInfo.equivalent.applyOn?string("yyyy-MM-dd HH:mm:ss"))?if_exists}</td>
		<td>申请学位号码</td>
		<td colspan="3">${(student.code)?if_exists}</td>
	</tr>
	<tr>
		<td>备注</td>
		<td colspan="7"></td>
	</tr>
</table>
<#if "std"==flag?if_exists>
<table>
  	<form name="dataInfoForm" method="post" action="" onsubmit="return false;">
     <#if !colspans?exists>
  	 <input type="hidden" name="degreeApply.totalMark" value="${totleMark}">
  	 </#if>
  	 <input type="hidden" name="degreeApply.specialityMark" value="${speciaMark}">
  	 <input type="hidden" name="degreeApply.level1Subject.id" value="${(level1Subject.id)?if_exists}">
  	 <input type="hidden" name="degreeApply.student.id" value="<#if (degreeApply.id)?exists>${(degreeApply.student.id)?if_exists}<#else>${student.id}</#if>">
  	 <input type="hidden" name="degreeApply.thesisManage.id" value="<#if (degreeApply.id)?exists>${(degreeApply.thesisManage.id)?if_exists}<#else>${(thesisManage.id)?if_exists}</#if>">
  	 <input type="hidden" name="degreeApply.id"  value="${((degreeApply.id))?if_exists}">
  	</form>
	<tr valign="top">
		<td width="8px">注：</td>
		<td><#include "tableFootContent.ftl"/></td>
	</tr>
</table>
</#if>
</p>
</body>
<script>
   var form = document.dataInfoForm;
 function doSubmit(){
 if(confirm("你确认对应的信息没有错误,可以提交学位申请了吗?")){
    	form.action="degreeApplyStd.do?method=doSaveDegreeApply";
    	form.submit();
    }
 }
   function downloadTemplate(type,value){
       form.action="thesisManageStd.do?method=export";
       if(<#if (degreeApply.id)?exists>false<#else>true</#if>){
       		alert("你还没有提交学位申请");
       		return;
       }
       addInput(form,"template",type+"_"+value+".xls");
   	   addInput(form,"fileName","${student.name}的学位数据表");
   	   addInput(form,"exportFlag","degreeInfo");
   	   form.submit();
   }
   function downAll(){
   	 form.action="download.do?method=download&document.id=2001";
   	 form.submit();
   }
</script>

<#include "/templates/foot.ftl"/>
