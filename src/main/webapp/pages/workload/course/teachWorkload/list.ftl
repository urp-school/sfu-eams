<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
	<table id="backBar"></table>
	<@table.table width="100%" align="center" sortable="true" id="listTable" style="word-break: break-all">
		<@table.thead>
			<@table.selectAllTd id="teachWorkloadId" rowspan="2"/>
			<@table.sortTd text="开课院系" rowspan="2" id="teachWorkload.college.name"/>
			<@table.sortTd name="workload.teacherName" rowspan="2" id="teachWorkload.teacherInfo.teacherName"/>
			<@table.td name="common.teacherTitle" rowspan="2"/>
			<@table.sortTd name="workload.gender" rowspan="2" id="teachWorkload.teacherInfo.gender.name"/>
			<@table.td name="workload.teacherAge" rowspan="2"/>
			<@table.td name="workload.teacherEduDegree" rowspan="2"/>
			<@table.sortTd name="workload.teacherDepart" rowspan="2" id="teachWorkload.teacherInfo.teachDepart.name"/>
			<@table.sortTd name="workload.courseName" rowspan="2" id="teachWorkload.courseName"/>
			<@table.td text="是否挂牌" rowspan="2"/>
			<@table.sortTd name="attr.taskNo" rowspan="2" id="teachWorkload.courseSeq"/>
			<@table.sortTd text="人数" rowspan="2" id="teachWorkload.studentNumber"/>
			<@table.sortTd text="周次" rowspan="2" id="teachWorkload.weeks"/>
			<@table.sortTd text="周课时" rowspan="2" id="teachWorkload.classNumberOfWeek"/>
			<@table.sortTd text="总课时" rowspan="2" id="teachWorkload.totleCourses"/>
			<@table.sortTd text="系数" rowspan="2" id="teachWorkload.teachModulus.modulusValue"/>
			<@table.sortTd text="总工作量" rowspan="2" id="teachWorkload.totleWorkload"/>
			<@table.sortTd name="entity.teachClass" rowspan="2" id="teachWorkload.classNames"/>
			<@table.td name="textEvaluation.affirm" colspan="4"/>
			<@table.td name="workload.academicTerm" rowspan="2"/>
			<@table.td text="备注" rowspan="2"/>
		</@>
		<@table.thead>
			<@table.td name="workload.payBill"/>
			<@table.td name="workload.calcWorkload"/>
			<@table.td name="workload.teacherAffirm"/>
			<@table.td name="workload.collegeAffirm"/>
	    </@>
	    <#assign recordSize = teachWorkloads?size/>
	    <@table.tbody datas=teachWorkloads;teachWorkload>
	    	<@table.selectTd id="teachWorkloadId" value=teachWorkload.id/>
		   	<td width="4%"><@i18nName (teachWorkload.college)?if_exists/></td>
		   	<td width="4%">${teachWorkload.teacherInfo.teacherName?if_exists}</td>
		   	<td width="4%"><@i18nName (teachWorkload.teacherInfo.teacherTitle)?if_exists/></td>
		   	<td width="4%"><@i18nName (teachWorkload.teacherInfo.gender)?if_exists/></td>
		   	<td width="4%">${teachWorkload.teacherInfo.teacherAge?if_exists}</td>
		   	<td width="5%"><@i18nName (teachWorkload.teacherInfo.eduDegree?if_exists)?if_exists/></td>
		   	<td width="6%"><@i18nName (teachWorkload.teacherInfo.teachDepart)?if_exists/></td>
		    <td width="8%">${teachWorkload.courseName?if_exists}</td>
		   	<td width="6%"><#if teachWorkload.teachTask?exists><#if true==teachWorkload.teachTask.requirement.isGuaPai>是<#else>否</#if><#else><#if teachWorkload.courseCategory.id?string=="4">是<#else>否</#if></#if></td>
		   	<td width="4%">${teachWorkload.courseSeq?if_exists}</td>
		   	<td width="4%">${teachWorkload.studentNumber?if_exists}</td>
		   	<td width="4%">${teachWorkload.weeks?if_exists}</td>
		   	<td width="4%">${teachWorkload.classNumberOfWeek?default(0)?string("##0.0")}</td>
		   	<td width="4%">${teachWorkload.totleCourses?if_exists}</td>
		   	<td width="4%">${(teachWorkload.teachModulus.modulusValue)?default(0)?string("##0.0")}</td>
		   	<td width="4%">${teachWorkload.totleWorkload?default(0)?string("##0.0")}</td>
		   	<td width="8%">${teachWorkload.classNames?if_exists}</td>
		   	<td width="5%"><#if teachWorkload.payReward==true><@bean.message key="workload.true"/><#else><@bean.message key="workload.false"/></#if></td>
		   	<td width="5%"><#if teachWorkload.calcWorkload==true><@bean.message key="workload.true"/><#else><@bean.message key="workload.false"/></#if></td>
		   	<td width="5%"><input type="hidden" id="teacherAffirm${teachWorkload.id}" value="${teachWorkload.teacherAffirm?string}"><#if teachWorkload.teacherAffirm==true><@bean.message key="workload.true"/><#else><@bean.message key="workload.false"/></#if></td>
		   	<td width="5%"><input type="hidden" id="collegeAffirm${teachWorkload.id}" value="${teachWorkload.collegeAffirm?string}"><#if teachWorkload.collegeAffirm==true><@bean.message key="workload.true"/><#else><@bean.message key="workload.false"/></#if></td>
		   	<td width="6%">${(teachWorkload.teachCalendar.year)?if_exists}&nbsp;${(teachWorkload.teachCalendar.term)?if_exists}</td>
		   	<td width="8%">${teachWorkload.remark?if_exists}</td>
	    </@>
	</@>
	<#include "listForm.ftl"/>
	<script>
   		var bar = new ToolBar('backBar','工作量具体操作',null,true,true);
   		bar.setMessage('<@getMessage/>');

   		var manageMenu = bar.addMenu('管理工具',null);
   		manageMenu.addItem('添加(有任务)','addWorkload()');
   		manageMenu.addItem('添加(无任务)','addWorkloadNoTask()');
   		manageMenu.addItem('<@msg.message key="action.edit"/>','updateObject()');
   		manageMenu.addItem('<@msg.message key="action.delete"/>','deleteObject()');
   		manageMenu.addItem('查看详情','detailInfoOfObject()');
   		manageMenu.addItem('导出','doExport()');

   		var teacherMenu = bar.addMenu('教师',null,'detail.gif');
   		teacherMenu.addItem('确认','affirmByIds("teacherAffirm","true")','detail.gif')
   		teacherMenu.addItem('取消确认','affirmByIds("teacherAffirm","false")','detail.gif');
   		teacherMenu.addItem('确认全选','affirmALlOrNot("teacherAffirm","true")','detail.gif');
   		teacherMenu.addItem('确认全取消','affirmALlOrNot("teacherAffirm","false")','detail.gif');

   		var collegeMenu = bar.addMenu('<@msg.message key="entity.department"/>',null,'detail.gif');
   		collegeMenu.addItem('确认','affirmByIds("collegeAffirm","true")','detail.gif')
   		collegeMenu.addItem('取消确认','affirmByIds("collegeAffirm","false")','detail.gif');
   		collegeMenu.addItem('确认全选','affirmALlOrNot("collegeAffirm","true")','detail.gif');
   		collegeMenu.addItem('确认全取消','affirmALlOrNot("collegeAffirm","false")','detail.gif');

   		var caculateMenu = bar.addMenu('计工作量',null,'detail.gif');
   		caculateMenu.addItem('确认','affirmByIds("calcWorkload","true")','detail.gif');
   		caculateMenu.addItem('取消确认','affirmByIds("calcWorkload","false")','detail.gif');
   		caculateMenu.addItem('确认全选','affirmALlOrNot("calcWorkload","true")','detail.gif');
   		caculateMenu.addItem('确认全取消','affirmALlOrNot("calcWorkload","false")','detail.gif');

   		var payMenu = bar.addMenu('支付报酬',null,'detail.gif');
   		payMenu.addItem('确认','affirmByIds("payReward","true")','detail.gif')
   		payMenu.addItem('取消确认','affirmByIds("payReward","false")','detail.gif');
   		payMenu.addItem('确认全选','affirmALlOrNot("payReward","true")','detail.gif');
   		payMenu.addItem('确认全取消','affirmALlOrNot("payReward","false")','detail.gif');

	 	function checkTeacherAffirm(teachWorkloadId){
	 		var flag=0;
	 		var teacherAffirm = document.getElementById("teacherAffirm"+teachWorkloadId);
	 		if("true"==teacherAffirm.value){
	 			flag=1;
	 		}
	 		return flag;
	 	}
	 	
	 	function checkCollegeAffirm(teachWorkloadId){
	 		var flag=0;
	 		var collegeAffirm = document.getElementById("collegeAffirm"+teachWorkloadId);
	 		if("true"==collegeAffirm.value){
	 			flag=1;
	 		}
	 		return flag;
	 	}
	    
		var form = document.pageGoForm;
		var action="teachWorkload.do";
		function  addWorkloadNoTask(){
	   		form.action=action+"?method=addWorkloadNoTaskForm";
	   		form.submit();
	   	}
	   	
	 	function affirmByIds(affirmType,value){
	 		var ids = getSelectIds("teachWorkloadId");
	 		if (ids == null || ids == "") {
	 			alert("请选择要操作的记录！");
	 			return;
	 		}
	    	form.action=action+"?method=affirmSelect";
	    	addInput(form,'affirmType',affirmType);
	    	addInput(form,'affirmEstate',value);
	    	setSearchParams(parent.form,form);
	    	submitId(form,"teachWorkloadId",true,null,"你确定要提交对应的信息吗?");
	    }
	    //
	    function affirmALlOrNot(affirmType,value){
	 		if (${recordSize} == 0) {
	 			alert("当前没有可操作的记录！");
	 			return;
	 		}
	      	if(confirm("<@bean.message key="workload.affirmAllWarning"/>")){
	      		form.action=action+"?method=affirmAll";
	      		addInput(form,'affirmType',affirmType);
	    		   addInput(form,'affirmEstate',value);
	    	   	setSearchParams(parent.form,form);
	    		   transferParams(parent.form,form);
	      		form.submit();
	      	}
	    }
	    
	    function updateObject(){
		    var teachWorkloadId = getSelectIds("teachWorkloadId");
		    if(teachWorkloadId == null || "" == teachWorkloadId || teachWorkloadId.indexOf(",") != -1) {
		    	alert("请选择单个对象");
		    	return;
		    }
		 	if(checkTeacherAffirm(teachWorkloadId)){
		 		alert("该工作量教师已确认,现在不能修改\n")
		 		return;
		 	}
		 	if(checkCollegeAffirm(teachWorkloadId)){
		 		alert("该工作量院系已确认,现在不能修改");
		 		return;
		 	}
		 	form.action = action+"?method=updateForm";
		 	setSearchParams(parent.form,form);
		 	submitId(form,"teachWorkloadId",false);
	 	}
	 	
	 	function deleteObject(){
	 		var ids = getSelectIds("teachWorkloadId");
	 		if (ids == null || ids == "") {
	 			alert("请选择要操作的记录！");
	 			return;
	 		}
	 		form.action = action+"?method=remove";
	 		setSearchParams(parent.form,form);
	 		submitId(form,"teachWorkloadId",true,null,"你确定要删除这些数据吗？\n删除了就无法恢复的");
	 	}
	 	
	 	function detailInfoOfObject(){
	 		var ids = getSelectIds("teachWorkloadId");
	 		if (ids == null || ids == "") {
	 			alert("请选择要操作的记录！");
	 			return;
	 		}
	 		form.action = action+"?method=info";
	 		setSearchParams(parent.form,form);
	 		submitId(form,"teachWorkloadId",false);
	 	}
	 	
	 	function doExport(){
	 		var selectYear = parent.getYear();
	 		if(""==selectYear.value||selectYear.value.length<0){
	 			alert("请选择一个学年度");
	 			return;
	 		}
	 		form.action=action+"?method=export";
	 		transferParams(parent.form,form);
	 		form.submit();
	 	}
	 	
	 	function addWorkload(){
	   		form.action=action+"?method=save";
	   		form.submit();
	   }
	</script>
</body>
<#include "/templates/foot.ftl"/>