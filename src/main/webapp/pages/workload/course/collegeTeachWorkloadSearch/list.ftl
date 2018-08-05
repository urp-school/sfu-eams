<#include "/templates/head.ftl"/>
 <script language="javascript">
 	orderBy =function(what){
		parent.search(1,${pageSize},what);
	}
	function pageGoWithSize(pageNo,pageSize){
       parent.search(pageNo,pageSize,'${RequestParameters['orderBy']?default('null')}');
    }
 </script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="backBar"></table>
		<script>
   		var bar = new ToolBar('backBar','工作量具体操作',null,true,true);
   		bar.setMessage('<@getMessage/>');
   		
   		var manageMenu = bar.addMenu('管理工具',null);
   		manageMenu.addItem('查看详情','detailInfoOfObject()');
   		manageMenu.addItem('导出','doExport()');
   		
   		var teacherMenu = bar.addMenu('教师',null,'detail.gif');
   		teacherMenu.addItem('确认','affirmByIds("teacherAffirm","true")','detail.gif')
   		teacherMenu.addItem('取消确认','affirmByIds("teacherAffirm","false")','detail.gif');
   		
   		var departMenu = bar.addMenu("院系", null, "detail.gif");
   		departMenu.addItem('院系确认','affirmByIds("collegeAffirm","true")','detail.gif');
   		departMenu.addItem('取消院系确认','affirmByIds("collegeAffirm","false")','detail.gif');
   		
   		var workLoadMenu = bar.addMenu("计工作量", null, "detail.gif");
   		workLoadMenu.addItem('计工作量确认','affirmByIds("calcWorkload","true")','detail.gif');
   		workLoadMenu.addItem('取消计工作量确认','affirmByIds("calcWorkload","false")','detail.gif');
   		
   		var payRewardMenu = bar.addMenu("支付报酬", null, "detail.gif");
   		payRewardMenu.addItem('支付报酬确认','affirmByIds("payReward","true")','detail.gif');
   		payRewardMenu.addItem('取消支付报酬确认','affirmByIds("payReward","false")','detail.gif');
</script>
<@table.table width="100%" align="center" sortable="true" id="listTable">
	<@table.thead>
		<td align="center" rowspan="2" width="5%"><input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('teachWorkloadId'),event);"></td>
		<@table.sortTd name="workload.teacherName" rowspan="2" id="teachWorkload.teacherInfo.teacherName"/>
		<@table.sortTd name="common.teacherTitle" rowspan="2" id="teachWorkload.teacherInfo.teacherTitle.name"/>
		<@table.sortTd name="workload.gender" rowspan="2" id="teachWorkload.teacherInfo.gender.name"/>
		<@table.td name="workload.teacherAge" rowspan="2"/>
		<@table.sortTd name="workload.teacherEduDegree" rowspan="2" id="teachWorkload.teacherInfo.eduDegree.name"/>
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
	<tr class="darkColumn" align="center">
  		<td><@bean.message key="workload.payBill"/></td>
  		<td><@bean.message key="workload.calcWorkload"/></td>
  		<td><@bean.message key="workload.teacherAffirm"/></td>
  		<td><@bean.message key="workload.collegeAffirm"/></td>
    </tr>
    <@table.tbody datas=teachWorkloads;teachWorkload>
    	<td width="5%" align="center" bgcolor="#CBEAFF"><input type="checkBox" name="teachWorkloadId" value="${teachWorkload.id}"></td>
	   	<td width="4%">${teachWorkload.teacherInfo.teacherName?if_exists}</td>
	   	<td width="4%">${(teachWorkload.teacherInfo.teacherTitle.name)?if_exists}</td>
	   	<td width="4%">${(teachWorkload.teacherInfo.gender.name)?if_exists}</td>
	   	<td width="4%">${teachWorkload.teacherInfo.teacherAge?if_exists}</td>
	   	<td width="5%">${(teachWorkload.teacherInfo.eduDegree.name)?if_exists}</td>
	   	<td width="6%">${(teachWorkload.teacherInfo.teachDepart.name)?if_exists}</td>
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
	   	<td width="6%">${teachWorkload.teachCalendar?if_exists.year?if_exists}&nbsp;${teachWorkload.teachCalendar?if_exists.term?if_exists}</td>
	   	<td width="8%">${teachWorkload.remark?if_exists}</td>
    </@>
</@>
<#include "../teachWorkload/listForm.ftl"/>
</body>
<script>
	var form = document.pageGoForm;
	var action="collegeWorkloadQuery.do";
   	function affirmByIds(affirmType,value){
    	form.action=action+"?method=affirmSelect";
    	addInput(form,'affirmType',affirmType);
    	addInput(form,'affirmEstate',value);
    	setSearchParams(parent.form,form);
    	submitId(form,"teachWorkloadId",true,null,"你确定要提交对应的信息吗?");
    }
 	function detailInfoOfObject(){
 		form.action=action+"?method=info";
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
</script>
 <#include "/templates/foot.ftl"/>