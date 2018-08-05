<#include "/templates/head.ftl"/>
<script language="JavaScript" src="scripts/Menu.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0" onload="f_frameStyleResize(self,0)">
  <table id="backBar" width="100%"></table>

<#assign doClick>initbooks(document.conditionForm)</#assign>
<#include "initializateMarkConfig.ftl"/>
	<@table.table width="100%" sortable="true" id="listTable">
		<@table.thead>
			<@table.selectAllTd id="thesisManageId"/>
			<@table.sortTd name="attr.stdNo" id="student.code"/>
			<@table.sortTd name="attr.personName" id="student.name"/>
			<@table.sortTd name="entity.college" id="student.department.name"/>
			<@table.sortTd name="entity.studentType" id="student.type.name"/>
			<@table.sortTd name="entity.speciality" id="student.firstMajor.name"/>
			<@table.sortTd text="论文题目" id="topicOpen.thesisTopic"/>
			<@table.sortTd text="导师姓名" id="student.teacher.name"/>
			<#if hasWrite?exists && hasWrite=="true">
				<@table.td text="评阅书编号"/>
	 			<@table.td text="是否双盲选中"/>
	 			<@table.td text="评阅提交时间"/>
      		</#if>
		</@>
		<form name="pageGoForm" method="post" action="" onsubmit="return false;">
		<@table.tbody datas=studentPage;thesisMange>
			<input type="hidden" name="annotateId" value="${(thesisMange.annotate.id)?if_exists}"/>
			<@table.selectTd id="thesisManageId" value=thesisMange.id/>
			<td>${thesisMange.student.code?html?if_exists}</td>
      		<td><A href="#" onclick="stdInfo(${thesisMange.id})"><@i18nName (thesisMange.student)?if_exists/></td>
      		<td><@i18nName (thesisMange.student.department)?if_exists/></td>
      		<td><@i18nName (thesisMange.student.type)?if_exists/></td>
      		<td><@i18nName (thesisMange.student.firstMajor)?if_exists/></td>
      		 <td title="${(thesisMange.topicOpen.thesisTopic?html)?if_exists}" nowrap><span style="display:block;width:120px;overflow:hidden;text-overflow:ellipsis;">${(thesisMange.topicOpen.thesisTopic?html)?if_exists}</span></td>
      		<td><@i18nName (thesisMange.student.teacher)?if_exists/></td>
	  		<#if hasWrite?exists && hasWrite=="true">
	  			<td>
	  				<input type="hidden" id="${thesisMange.id?if_exists}serial" name="${thesisMange.id?if_exists}serial" value="<#if thesisMange.annotate?if_exists.annotateBooks?exists&&(thesisMange.annotate?if_exists.annotateBooks?size>0)>1<#else>0</#if>">
	  				<#assign searchFlag="0">
	  				<#list (thesisMange.annotate.annotateBooks)?if_exists?sort_by("serial") as annotateBook>
	  					${annotateBook.serial?html?if_exists},
	  					<#if annotateBook.thesisAppraise?exists>
	  						<#assign searchFlag="1">
	  					</#if>
	  				</#list>
	  			</td>
	  			<td><#if thesisMange.annotate.isDoubleBlind?exists&&true==thesisMange.annotate.isDoubleBlind>选中<#else>未选中</#if></td>
	  			<td><#if (thesisMange.annotate.finishOn)?exists>${(thesisMange.annotate.finishOn)?string("yyyy-MM-dd")}<#else>未完成</#if></td>
  			</#if>
	  		<#if hasWrite?exists && hasWrite=="true">
	  			<input type="hidden" id="${thesisMange.student.id}searchFlag" name="${thesisMange.student.id}searchFlag" value="${searchFlag?if_exists}">
	  			<input type="hidden" id="${thesisMange.id}std" name="${thesisMange.id}std" value="${thesisMange.student.id}">
	  		</#if>
		</@>
	  		<input type="hidden" name="keys" value="student.code,student.name,student.type.name,student.department.name,topicOpen.thesisTopic,student.firstMajor.name,student.firstAspect.name,student.enrollYear">
    		<input type="hidden" name="titles" value="学号,姓名,学生类别,部门名称,论文项目,专业名称,专业方向名称,所在年级">
			<#assign paginationName="studentPage" />
		</form>	
	</@>
	<script>
   	var bar = new ToolBar('backBar','论文评阅列表',null,true,true);
   	bar.setMessage('<@getMessage/>');
   	<#if hasWrite?exists && hasWrite=="true">
   		bar.addItem("<@msg.message key="action.info"/>","stdInfo()");
   	    var initializateMarkMenu = bar.addMenu('评阅书编号',null);
   	    //initializateMarkMenu.addItem("初始化编号","displayConfig(1)","detail.gif","初始化论文评阅书");
   		initializateMarkMenu.addItem("选择初始化编号","displayConfig()","detail.gif","选择初始化论文评阅书");
   		initializateMarkMenu.addItem("取消论文编号","deleteAnnotateNo(document.pageGoForm)");
   		//bar.addItem("填写评阅书","writeThesisBook(document.pageGoForm1)","detail.gif","填写论文评阅书");
   		//bar.addItem("取消评阅确认","cancelAffirm()")
   		var doubleChooseMenu = bar.addMenu('双盲操作',null,'setting.png');
   		doubleChooseMenu.addItem("双盲选中确认","doubleChoose(document.pageGoForm,'true')","setting.png","双盲选中确认");
   		doubleChooseMenu.addItem("取消双盲选中","doubleChoose(document.pageGoForm,'false')","setting.png","取消双盲选中");
   	</#if>
   	bar.addItem("<@msg.message key="action.export"/>","doExp(${hasWrite?if_exists})");
   	var re_dt = /^(\d{4})\-(\d{1,2})\-(\d{1,2})$/;
    
    function pageGoWithSize(number,pageSize){
       parent.search(number,pageSize);
    }
    function deleteAnnotateNo(form){
    	var id=getSelectIds("thesisManageId");
    	if(""==id||id.length<1){
   				alert("请选择要取消编号的选项");
   				return;
   		}
   		if(confirm("你确定要删除选择的学生的论文编号吗\n删除以后无法回复对应的成绩\n你确定删除吗?")){
   			form.action="annotateAdmin.do?method=doDeleteNo";
   			setSearchParams(parent.document.pageGoForm,form);
   			addInput(form,"thesisManageIdSeq",id);
   			form.submit();
   		}
    }
    function stdInfo(thesisManageId){
        var id = thesisManageId;
        if (null == thesisManageId || "" == thesisManageId) {
            id = getSelectId("thesisManageId");
	        if(id == null || id == ""){
	        	alert("请你选择单个对象");
	        	return;
	        }
        }
        var stdNo = document.getElementById(id+"std").value;
    	var searchFlagValue=document.getElementById(stdNo+"searchFlag").value;
    	if("0"==searchFlagValue){
    		if(!confirm("该学生的论文评阅书还没有创建\n你确定查看信息吗")){
    			return;
    		}
    	}
    	form.action='annotateAdmin.do?method=annotateInfo';
    	setSearchParams(parent.document.pageGoForm,form);
    	addInput(form,"studentId",stdNo);
    	form.submit();
    }
	function doubleChoose(form,value){
		var idSeq=getSelectIds("thesisManageId");
		if(""==idSeq||idSeq.length<1){
			alert("请选择单个对象");
			return;
		}
		if(confirm("你确定该学生要参与双盲吗?")==true){
			form.action="annotateAdmin.do?method=doubleChoose";
			setSearchParams(parent.document.pageGoForm,form);
			addInput(form,"isAffirm",value);
			addInput(form,"thesisManageSeq",idSeq);
			form.submit();
		}				
	}
	/*
	*显示说这隐藏 configDiv
	*/
	function displayConfig(value){
   		var divConfig = document.getElementById("configDiv");
   		if(divConfig.style.display=="none"){
   			divConfig.style.display="block";
   			f_frameStyleResize(self);
   		}
   		else divConfig.style.display="none";
   	}
	function initbooks(form){
			var errors ="";
			//编号年份
			var marYear = document.getElementById("markYear");
			if(""==marYear.value){
				errors+="编号年份必须要填\n";
			}
			if(marYear.value.length>5){
				errors+="编号年份不能大于5位\n";
			}
			//起始数字
			var beginNumber = document.getElementById("beginNumber");
			if(""==beginNumber.value){
				errors+="起始数字必须要填\n";
			}
			if(!/^\d+$/.test(beginNumber.value)){
				errors+="起始数字必须是数字\n";
			}
			//专家个数
			var exportNum = document.getElementById("exportNum");
			if(""==exportNum.value){
				errors+="专家个数必须要填\n";
			}
			if(!/^\d+$/.test(exportNum.value)){
				errors+="专家个数必须是数字\n";
			}
			//有效位数
			var virtualNum = document.getElementById("virtualNum");
			if(""==virtualNum.value){
				errors+="有效位数必须要填\n";
			}
			if(!/^\d+$/.test(virtualNum.value)){
				errors+="有效位数必须是数字\n";
			}
			if(""!=errors){
				alert(errors);
				return;
			}
   			url="annotateAdmin.do?method=doInitaliBook";
   			var thesisManageIdSeq=getSelectIds("thesisManageId");
   			if(""==thesisManageIdSeq||thesisManageIdSeq.length<1){
   				alert("请选择要初始化编号的选项");
   				return;
   			}else{
   				addInput(form,"thesisManageIdSeq",thesisManageIdSeq);
   			}
   		if(confirm("论文编号以"+marYear.value+"开始,从"+beginNumber.value+"递增,教师个数为"+exportNum.value+",有效位数为"+virtualNum.value+"\n将初始化上面的查询条件所能查询得到的所有的数据\n你确定执行该操作吗")){
				form.action=url;
				setSearchParams(parent.document.pageGoForm,form);
				form.submit();
			}
	}
	/*
		 填写论文评阅书
	*/
	function writeThesisBook(form){
		var ids = getSelectIds("thesisManageId");
		var parameters=document.getElementById("parameters").value;
		url="annotateAdmin.do?method=doWriteBook&book=book&thesisId="+ids+"&paramters="+parameters;
		if(""==ids||ids.length<1){
			alert("请选择单个选项");
			return;
		}else{
			var serial= document.getElementById(ids+"serial");
			if("1"==serial.value){
				form.action=url;
				//form.target="_parent";
				form.submit();
			}else{
				alert("该学生的论文还没有指定论文编号,请先指定论文编号");
				return;
			}
		}
	}
  function getInputValuesByName(name){
    var values="";
  	var names=document.getElementsByName(name);
  	for(var i=0;i<names.length;i++){
  		values+=names[i].value+",";
  	}
  	if(""!=values){
  		values=values.substr(1,values.length-1); 
  	}
  	return values;
  }
  
  function doExp(isFinish){
  	form.action= "annotateAdmin.do?method=export&isFinish="+isFinish;
  	<#list RequestParameters?keys as key>
  		addInput(form, "${key}", "${RequestParameters[key]}", "hidden");
    </#list>
  	form.submit();
  }
  
 var form = document.pageGoForm;
 function  cancelAffirm(){
    	var ids = getSelectIds("thesisManageId");
    	if(""==ids){
    		alert("请选择对象");
    		return;
    	}
    	if(confirm("你确认要取消这些学生的论文评阅确认吗?")){
    		form.action="annotateAdmin.do?method=cancelAffirm";
    		addInput(form,"thesisManageIdSeq",ids);
    		setSearchParams(parent.document.pageGoForm,form);
    		form.submit();
    	}
    }
</script>  
  </body>
<#include "/templates/foot.ftl"/>
