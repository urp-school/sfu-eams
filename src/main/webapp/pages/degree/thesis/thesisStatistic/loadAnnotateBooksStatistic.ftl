<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<body LEFTMARGIN="0" TOPMARGIN="0">
<table id="taskConfirmBar"></table>
<#assign doClick>doConfig()</#assign>
<#include "annotateConfigDiv.ftl"/>
	<div align="center"><h2>${teachcalendar.year} ${teachcalendar.term}<@i18nName teachcalendar.studentType/>学位论文双盲评审结果汇总</h2></div>
<pre>
	1.总体情况如下：
		${teachcalendar.year}学年度第${teachcalendar.term}学期，我校<@i18nName teachcalendar.studentType/>学位论文双盲评审共送审<#if levelMap['stdNumber']?exists>${levelMap['stdNumber']?string}<#else>0</#if>位<@i18nName teachcalendar.studentType/>的论文<#if levelMap['titleNumber']?exists>${levelMap['titleNumber']?string}<#else>0</#if>篇.学位水平
 	评价优秀${levelMap['A']?default(0)?if_exists}篇,占<#if levelMap["titleNumber"]?exists&&levelMap["titleNumber"]?string!="0">${((levelMap["A"]?default(0)?number/levelMap["titleNumber"]?number)*100)?string("##0.0")}<#else>0.0</#if>%;良好${levelMap["B"]?default(0)?if_exists}篇,占<#if levelMap["titleNumber"]?exists&&levelMap["titleNumber"]?string!="0">${((levelMap["B"]?default(0)?number/levelMap["titleNumber"]?number)*100)?string("##0.0")}<#else>0.0</#if>% ;一般${levelMap["C"]?default(0)?if_exists}篇,占<#if levelMap["titleNumber"]?exists&&levelMap["titleNumber"]?string!="0">${((levelMap["C"]?default(0)?number/levelMap["titleNumber"]?number)*100)?string("##0.0")}<#else>0.0</#if>%;不合格${levelMap["D"]?default(0)?if_exists},占<#if levelMap["titleNumber"]?exists&&levelMap["titleNumber"]?string!="0">${((levelMap["D"]?default(0)?number/levelMap["titleNumber"]?number)*100)?string("##0.0")}<#else>0.0</#if>% ;
</pre>
	<table align="center" class="listTable" width="80%">
		<tr>
			<td align="right" width="13%">标准</td>
			<td colspan="2">优秀${segMentMap["A"]?if_exists.min?if_exists}分以上</td>
			<td colspan="2">良好${segMentMap["B"]?if_exists.max?if_exists}-${segMentMap["B"]?if_exists.min?if_exists}分</td>
			<td colspan="2">一般${segMentMap["C"]?if_exists.max?if_exists}-${segMentMap["C"]?if_exists.min?if_exists}分</td>
			<td colspan="2">不合格${segMentMap["D"]?if_exists.max?if_exists}分</td>
			<td rowspan="2">合计</td>
		</tr>
		<tr>
			<td align="center">结果</td>
			<#list standards as i>
				<td width="14%">绝对值</td>
				<td width="6%">%</td>
			</#list>
		</tr>
		<tr>
			<td>学位水平评价</td>
			<#list standards as i>
				<td>${levelMap[i]?default(0)?string}篇</td>
				<td><#if levelMap["titleNumber"]?exists&&levelMap["titleNumber"]?string!="0">${((levelMap[i]?default(0)?number/levelMap["titleNumber"]?number)*100)?string("##0.0")}</#if>
			</#list>
			<td>${levelMap["titleNumber"]?if_exists}篇</td>
		</tr>
		<tr>
			<td>成绩<br>按篇计</td>
			<#list standards as i>
				<td>${markMap[i]?if_exists}篇</td>
				<td><#if levelMap["titleNumber"]?exists&&levelMap["titleNumber"]?string!="0">${((markMap[i]?number/levelMap["titleNumber"]?number)*100)?string("##0.0")}</#if>
			</#list>
			<td>${levelMap["titleNumber"]?if_exists}篇</td>
		</tr>
		<tr>
			<td>平均成绩<br>按人计</td>
			<#list standards as i>
				<td>${avgMarkMap[i]?string}人</td>
				<td><#if levelMap["stdNumber"]?exists&&levelMap["stdNumber"]?string!="0">${((avgMarkMap[i]?default(0)?number/levelMap["stdNumber"]?number)*100)?string("##0.0")}</#if>
			</#list>
			<td>${levelMap["stdNumber"]?if_exists}人</td>
		</tr>
	</table>
<pre>
	2、评审专家对可否参加答辩意见如下:
	   (1)专家同意答辩<#if levelMap['stdNumber']?exists&&noPassed?exists&&modify?exists>${(levelMap['stdNumber']?default(0)?number-noPassed?if_exists?size-modify?if_exists?size)}<#else>0</#if>人
	   (2)要求修改论文${modify?if_exists?size}人,占送审总人数的<#if levelMap['stdNumber']?exists&&levelMap['stdNumber']?number!=0>${((modify?if_exists?size/levelMap["stdNumber"]?number)*100)?string("##0.0")}<#else>0.0</#if>%;涉及${modify?if_exists?size}篇，占总数的<#if levelMap["titleNumber"]?exists&&levelMap["titleNumber"]?string!="0">${((modify?if_exists?size/levelMap["titleNumber"]?number)*100)?string("##0.0")}<#else>0.0</#if>%;
	   (3)不同意答辩${noPassed?if_exists?size}人,占送审总人数的<#if levelMap['stdNumber']?exists&&levelMap['stdNumber']?number!=0>${((noPassed?if_exists?size/levelMap["stdNumber"]?number)*100)?string("##0.0")}<#else>0.0</#if>%.
</pre>
<table align="center" class="listTable" width="80%">
	<tr>
		<td>序号</td>
		<td><@msg.message key="entity.department"/></td>
		<td><@msg.message key="attr.personName"/></td>
		<td><@msg.message key="entity.speciality"/></td>
		<td>成绩</td>
		<td>学位水平</td>
		<td>是否同意答辩</td>
		<td>结论</td>
		<td>备注</td>
	</tr>
	<#assign class="grayStyle">
	<#list noPassed?if_exists as annotateBook>
		<tr class="${class}">
			<#if class="grayStyle"><#assign class="brightStyle"><#else><#assign class="grayStyle"></#if>
			<td>${annotateBook_index+1}</td>
			<td>${annotateBook.thesisManage?if_exists.student?if_exists.department?if_exists.name?if_exists}</td>
			<td>${annotateBook.thesisManage?if_exists.student?if_exists.name?if_exists}</td>
			<td>${annotateBook.thesisManage?if_exists.student?if_exists.firstMajor?if_exists.name?if_exists}</td>
			<td>${annotateBook.evaluateIdea?if_exists.mark?if_exists?string("##0.0")}</td>
			<td><#if annotateBook.evaluateIdea?if_exists.learningLevel?exists><#if annotateBook.evaluateIdea.learningLevel=="A">优秀<#elseif annotateBook.evaluateIdea.learningLevel=="B">良好<#elseif annotateBook.evaluateIdea.learningLevel=="C">一般<#elseif annotateBook.evaluateIdea.learningLevel=="D">不及格</#if></#if></td>
  			<td>不同意</td>
  			<td></td>
  			<td></td>
		</tr>
	</#list>
	<#list modify?if_exists as annotateBook>
		<tr class="${class}">
			<#if class="grayStyle"><#assign class="brightStyle"><#else><#assign class="grayStyle"></#if>
			<td>${annotateBook_index+1}</td>
			<td>${annotateBook.thesisManage?if_exists.student?if_exists.department?if_exists.name?if_exists}</td>
			<td>${annotateBook.thesisManage?if_exists.student?if_exists.name?if_exists}</td>
			<td>${annotateBook.thesisManage?if_exists.student?if_exists.firstMajor?if_exists.name?if_exists}</td>
			<td>${annotateBook.evaluateIdea?if_exists.mark?if_exists?string("##0.0")}</td>
			<td><#if annotateBook.evaluateIdea?if_exists.learningLevel?exists><#if annotateBook.evaluateIdea.learningLevel=="A">优秀<#elseif annotateBook.evaluateIdea.learningLevel=="B">良好<#elseif annotateBook.evaluateIdea.learningLevel=="C">一般<#elseif annotateBook.evaluateIdea.learningLevel=="D">不及格</#if></#if></td>
  			<td>修改后答辩</td>
  			<td></td>
  			<td></td>
		</tr>
	</#list>
	<tr class="${class}">
		<td  height="25px;" colspan="9"></td>
	</tr>
</table>
	<script>
 		var bar=new ToolBar("taskConfirmBar","论文评阅统计",null,true,true);
  		bar.setMessage('<@getMessage/>');
  		bar.addItem("统计设置","displayConfig()");
   		bar.addPrint("<@msg.message key="action.print"/>");
   		
	   	function displayConfig(){
	   		var divConfig = document.getElementById("configDiv");
	   		if(divConfig.style.display=="none"){
	   			divConfig.style.display="block";
	   			f_frameStyleResize(self);
	   		}
	   		else divConfig.style.display="none";
	   	}
	   	var abcdForm = document.conditionForm;
	   	function doConfig(){
	   	    var inputs = document.conditionForm.elements;
	   	    for(var i=0;i<inputs.length;i++){
	   	    	if(inputs[i].name.indexOf("Min")>-1||inputs[i].name.indexOf("Max")>-1){
	   	    		if(""==inputs[i].value||!/^\d{0,3}$/.test(inputs[i].value)){
	   	    			inputs[i].focus();
	   	    			inputs[i].select();
	   	    			alert("分数段的所有取值必须是数字,不要带空格,而你输入的是"+inputs[i].value);
	   	    			return;
	   	    		}
	   	    	}
	   	    }
	   	    
	   	    var a_fields = {
	   	    	'AMin':{'l':'优秀最低成绩', 'r':true, 't':'f_best', 'f':'unsigned'},
	   	    	'AMax':{'l':'优秀最高成绩', 'r':true, 't':'f_best', 'f':'unsigned'},
	   	    	'BMin':{'l':'良好最低成绩', 'r':true, 't':'f_good', 'f':'unsigned'},
	   	    	'BMax':{'l':'良好最高成绩', 'r':true, 't':'f_good', 'f':'unsigned'},
	   	    	'CMin':{'l':'一般最低成绩', 'r':true, 't':'f_normal', 'f':'unsigned'},
	   	    	'CMax':{'l':'一般最高成绩', 'r':true, 't':'f_normal', 'f':'unsigned'},
	   	    	'DMin':{'l':'不及格最低成绩', 'r':true, 't':'f_bad', 'f':'unsigned'},
	   	    	'DMax':{'l':'不及格最高成绩', 'r':true, 't':'f_bad', 'f':'unsigned'}
	   	    };
	   	    
	   	    var v = new validator(abcdForm, a_fields, null);
	   	    if (v.exec()) {
	   	    	if ((parseInt(abcdForm['DMin'].value) < parseInt(abcdForm['DMax'].value)
	   	    	  && parseInt(abcdForm['DMax'].value) < parseInt(abcdForm['CMin'].value)
	   	    	  && parseInt(abcdForm['CMin'].value) < parseInt(abcdForm['CMax'].value)
	   	    	  && parseInt(abcdForm['CMax'].value) < parseInt(abcdForm['BMin'].value)
	   	    	  && parseInt(abcdForm['BMin'].value) < parseInt(abcdForm['BMax'].value)
	   	    	  && parseInt(abcdForm['BMax'].value) < parseInt(abcdForm['AMin'].value)
	   	    	  && parseInt(abcdForm['AMin'].value) < parseInt(abcdForm['AMax'].value)) == false) {
	   	    		alert("分数段区间设置有错误！");
	   	    		return;
	   	    	}
		   		abcdForm.action="thesisStatistic.do?method=annotateStatistic";
		   		abcdForm.submit();
	   		}
	   	}
	</script>
</body>
<#include "/templates/foot.ftl"/>