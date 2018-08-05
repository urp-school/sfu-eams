<#include "/templates/head.ftl"/>
<link href="${static_base}/css/tab.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/tabpane.js"></script>
<script src='dwr/interface/teachPlanService.js'></script>

<body>
<table id="planFormBar"></table>

<#if teachPlan.std?exists>
<#assign stdCode>${teachPlan.std.code}</#assign>
<#elseif RequestParameters['stdCode']?exists >
<#assign stdCode>${RequestParameters['stdCode']}</#assign>
</#if>

<table width="80%" align="center" class="formTable">
	<form name="stdPlanForm" method="post" action="" onsubmit="return false;">
	<tr>
	   <td>请输入学号:</td>
	   <td><input type="text" name="stdCode" tabIndex="0" value="${stdCode}" >
	       <#assign majorTypeId = RequestParameters['majorType.id']?default("1")>
		   <select name="majorType.id">
			   <option value="1" <#if majorTypeId="1"> selected</#if>>第一专业</option>
			   <option value="2" <#if majorTypeId="2"> selected</#if>>第二专业</option>
		   </select>
		   学期
		   <input name="terms" value="${RequestParameters['terms']?default("")}" type="text" style="width:60px" maxlength="200"/>
		   多个学期可以使用,隔开
	   </td>
	   <td><button name="query" class="buttonStyle" onclick="getTeachPlan()">查询</button></td>
	</tr>
	<#if teachPlan.std?exists><#else>
	<tr>
	  <td colspan="3">
        <div align="center" class="error"><#if teachPlan.std?exists><#else><B> ${student.name} </B>还没有个人计划.<button onclick="addInput(this.form,'forceGen',1);getTeachPlan();">生成个人计划</button></#if></div>
	  </td>
	</tr>
	</#if>
	</form>
</table>
   <#if teachPlan.std?exists>
   <div align="center"><#if teachPlan.std?exists>&nbsp;${teachPlan.std.name}&nbsp;</#if>(${teachPlan.enrollTurn})<@i18nName teachPlan.stdType/> <@i18nName teachPlan.department/> <#if teachPlan.speciality?exists><@i18nName  teachPlan.speciality/><@bean.message key="entity.speciality"/></#if><#if teachPlan.aspect?exists>&nbsp;<@i18nName  teachPlan.aspect/></#if><#if teachPlan.std?exists>&nbsp;<@bean.message key="attr.cultivateScheme.personalCultivateScheme"/><#else><@bean.message key="entity.cultivatePlan"/></#if></div>
   <div class="dynamic-tab-pane-control tab-pane" id="tabPane1" >
     <script type="text/javascript">tp1 = new WebFXTabPane( document.getElementById( "tabPane1") ,false );</script>
    
     <div style="display: none;" class="tab-page" id="tabPage1">
      <h2 class="tab"><a href="#">基本信息</a></h2>
     <table width="100%" align="left" class="infoTable">
       <tr class="darkColumn">
         <td align="left" colspan="4"><B>培养计划基本信息</B></td>
       </tr>
	   <tr>
	     <td align="center" class="title"><@bean.message key="std.code"/>:</td>
	     <td>${teachPlan.std.code}</td>
	   	 <td align="center" class="title"><@bean.message key="attr.personName"/>:</td>
	     <td>${teachPlan.std.name}</td>
	   </tr>
	   <tr>
	     <td align="center" id="f_enrollTurn" class="title"><@bean.message key="attr.enrollTurn"/><font color="red">*</font>:</td>
	     <td>${teachPlan.enrollTurn?if_exists}</td>
	   	 <td align="center" id="f_department" class="title"><@bean.message key="common.college"/><font color="red">*</font>:</td>
	     <td><@i18nName teachPlan.department/></td>
	   </tr>
	   <tr>
    	 <td  class="title"><@bean.message key="entity.studentType"/><font color="red">*</font>:</td>
         <td><@i18nName teachPlan.stdType/></td>
         <td align="center" class="title"><@bean.message key="entity.speciality"/>:</td>
	     <td><@i18nName (teachPlan.speciality)?if_exists/></td>
       </tr>
	   <tr>
         <td align="center" id="f_termsCount" class="title">&nbsp;学期数<font color="red">*</font>:</td>
         <td>${teachPlan.termsCount}</td>
	     <td align="center" class="title"><@bean.message key="entity.specialityAspect"/>&nbsp;&nbsp;:</td>
	     <td align="left" id="f_specialityAspect"><@i18nName (teachPlan.aspect)?if_exists/></td>
	   </tr>
	   <tr>
         <td align="center" class="title" id="f_credit">总学分<font color="red">*</font>:</td>
         <td>${teachPlan.credit?if_exists}</td>
         <td align="center" class="title" id="f_creditHour">总学时<font color="red">*</font>:</td>
         <td>${teachPlan.creditHour?if_exists}</td>
       </tr>
	   <tr>
         <td align="center" class="title">指导老师:</td>
         <td colspan="3">${teachPlan.teacherNames?if_exists}</td>
	   </tr>
	   <tr>
         <td align="center" class="title"><@bean.message key="attr.remark"/>:</td>
         <td colspan="3">${teachPlan.remark?if_exists}</td>
	   </tr>
     </table>
  </div>
  
  <#assign terms = RequestParameters['terms']?default("")>
  <div style="display: block;" class="tab-page" id="tabPage2">
    <h2 class="tab"><a href="#" >课程</a></h2>
             <#assign courseCount=0>
		     <table class="formTable" width="90%" align="center" style="font-size:12px">
		      <#assign maxTerm=teachPlan.termsCount />
		      <tr align="center" class="darkColumn">
		       <td rowspan="2" width="3%"><@bean.message key="attr.cultivateScheme.sort"/></td>
		       <td rowspan="2" width="10%"><input type="checkbox" onclick="toggleCheckBox(document.getElementsByName('planCourseId'),event);" style="height:10px"><@bean.message key="attr.code"/></td>
		       <td rowspan="2" width="30%"><@bean.message key="attr.courseName"/></td>
		       <td rowspan="2" width="20%">操作</td>
		       <td rowspan="2" width="5%">学时</td>
		       <td rowspan="2" width="5%"><@bean.message key="attr.grade"/></td>
		       <td colspan="${maxTerm}" width="30%"><@bean.message key="attr.cultivateScheme.arrangeByTermAndAcademic"/></td>
		       <td rowspan="2"><@bean.message key="attr.remark"/></td>
		      </tr>
		      <tr class="darkColumn">
		       <#assign total_term_weekHour={} />
		       <#list 1..maxTerm as i >
		        <#assign total_term_weekHour=total_term_weekHour + {i:0}/>
		        <td width="2%"><p align="center">${i}</p></td>
		       </#list>
		      </tr>
		      
		      <#--开始绘制课程组 类别-->
		      <#list teachPlan.courseGroups?sort_by(["courseType","priority"]) as group>
		      <#assign planCourses = group.getPlanCourses(terms)?sort_by(["course","code"])>
		      <#if planCourses?size!=0><#assign typeRowspan=planCourses?size+2><#else><#assign typeRowspan=2></#if>
		      <tr>
		      <#assign parentCourseType> </#assign>
		      <#if group.parentCourseType?exists>
			      <#if group.parentCourseType.id!=group.courseType.id>
			      <#assign parentCourseType><@i18nName group.parentCourseType/>/</#assign>
			      </#if>
		      </#if>
		      <#if typeRowspan=2>
		        <td rowspan="2"  colspan="3" align="left">&nbsp;${parentCourseType}<@i18nName group.courseType/></td>
		      <#else>
		        <td rowspan="${typeRowspan}" align="left">${parentCourseType}<@i18nName group.courseType/></td>
		      </#if>
		      </tr>
		      
		      <#--开始绘制课程组 课程-->
		      <#list planCourses as planCourse>
		      
		      <#assign courseCount = courseCount+1>
		      <tr>
		       <td align="center"><input type="checkBox" name="planCourseId" value="${planCourse.id}" style="height:10px">${planCourse.course.code}</td>
		       <td align="left">&nbsp;${courseCount}&nbsp;<@i18nName planCourse.course/></td>
		       <td align="center"><A href="#" onclick="action('editPlanCourse','${group.id}','${planCourse.id}')">修改</a>&nbsp;
		                          <A href="#" onclick="action('removePlanCourse','${group.id}','${planCourse.id}')"><@msg.message key="action.delete"/></a></td>
		       <td align="center">${(planCourse.course.extInfo.period)?if_exists}</td>
		       <td align="center">${(planCourse.course.credits)?if_exists}</td>
		
		       <#list 1..maxTerm as i>
		       <td title="${i}">
		        <p align="center">
		         <#if planCourse.termSeq?exists && (","+planCourse.termSeq+",")?contains(","+i+",")>${(planCourse.course.weekHour)?if_exists}<#else>&nbsp;</#if>
		        </p>
		       </td>
		       </#list>
		       <td><#if planCourse.substitution?exists><@bean.message key="attr.cultivateScheme.courseCanbeReplace"/>：<@i18nName planCourse.substitution/><br></#if>
		           <#if planCourse.remark?exists>${planCourse.remark}<#else>&nbsp;</#if>
		       </td>
		      </tr>
		      
		      </#list>
		      <#--开始绘制课程组小计-->
		      <tr>
		        <#if (typeRowspan>2)>
		        <td colspan="2" align="center">小计</td>
		        </#if>
		       <td  align="center">
		           <A href="#" onclick="action('editCourseGroup','${group.id}')"><@msg.message key="action.edit"/></A>&nbsp;
		           <A href="#" onclick="action('removeCourseGroup','${group.id}')"><@msg.message key="action.delete"/></A>
		           <A href="#" onclick="action('editPlanCourse','${group.id}','0')">添加课程</A>
		       </td>
		        <td align="center">${group.creditHour}</td>
		        <td align="center">${group.credit}</td>
		       <#assign i=1>
		       <#list group.weekHourPerTerms[1..group.weekHourPerTerms?length-2]?split(",") as weekHour>
		       <td>
		        <p align="center"><#if weekHour!="0">${weekHour}<#else>&nbsp;</#if></p>
		          <#assign current_totle=total_term_weekHour[i?string] />
		          <#assign total_term_weekHour=total_term_weekHour + {i:current_totle+weekHour?number}/>
		          <#assign i=i+1>
		       </td>
		       </#list>

		       <td align="center">&nbsp;${group.remark?if_exists}</td>
		      </tr>
		      </#list>
		      
		      <tr>
		       <td align="center" colspan="4"><@bean.message key="attr.cultivateScheme.allTotle"/></td>
		       <td align="center">${teachPlan.creditHour}</td>
		       <td align="center">${teachPlan.credit}</td>
		       <#list 1..maxTerm as i>
		       <td><p align="center">${total_term_weekHour[i?string]}</p></td>
		       </#list>
		       <td>&nbsp;</td>
		      </tr>
      </div>
  </div>
  </#if>
      <form name="actionFrom" method="post" action="" onsubmit="return false;">
	   <input type="hidden" name="teachPlan.id" value="${teachPlan.id}">
	   <input type="hidden" name="courseGroup.id" value="">
	   <input type="hidden" name="planCourse.courseGroup.id" value="">
	   <input type="hidden" name="planCourse.id" value="">
	   <input type="hidden" name="std.code" value="${stdCode?if_exists}">
	  </form>
  <script language="JavaScript" type="text/JavaScript" src="scripts/course/TeachPlan.js"></script>
  <script language="javascript">
    document.stdPlanForm.stdCode.focus();
    function getTeachPlan(){
       var form= document.stdPlanForm;
       if(form['stdCode'].value==""){
         alert("学号为必填项");
         return;
       }
       form.action="stdTeachPlan.do?method=edit";
       form.submit();
    }
    function action(method,groupId,planCourseId){
         if(method.indexOf("remove")!=-1){
            if(!confirm("以下将进行删除操作,是否继续?"))
            return;
         }
         var form= document.actionFrom;
         form['courseGroup.id'].value=groupId;
         form['planCourse.courseGroup.id'].value=groupId;
         form['planCourse.id'].value=planCourseId;
         form.action="stdTeachPlan.do?method=";
         form.action+=method;
         form.submit();
    }
    function addCourseGroup(){
       action("addCourseGroup","0");
    }
    function calcPlanCredit(){
       action("calcPlanCredit");
    }
    function removePlanCourses(){
        var planCourseIds = getSelectIds("planCourseId");
        if(""==planCourseIds){
           alert("请选择一个或多个培养计划中的课程点击删除"); return;
        }else{
           if(confirm("是否删除选定的"+countId(planCourseIds)+"门课程")){
	           var form= document.actionFrom;
	           form.action="stdTeachPlan.do?method=batchRemovePlanCourse&planCourseIds="+planCourseIds;
	           form.submit();
           }
        }
    }
	 var bar= new ToolBar("planFormBar","个人计划管理",null,true,true);
	 bar.setMessage('<@getMessage />');
	 <#if teachPlan.std?exists>
	 bar.addItem("添加课程组",'addCourseGroup()','new.gif');
	 bar.addItem("批量删除课程",'removePlanCourses()','delete.gif');
	 //bar.addItem("统计学分",'calcPlanCredit()',null,"按照各个课程组计算学生计划的要求学分");
	 </#if>
	 bar.addItem("<@msg.message key="action.back"/>",'teachPlan.do?method=index','backward.gif');
   <#if teachPlan.std?exists>	 
     tp1.addTabPage(document.getElementById("tabPage2"));
     tp1.addTabPage(document.getElementById("tabPage1"));
   </#if>
 </script>
<#include "/templates/foot.ftl">