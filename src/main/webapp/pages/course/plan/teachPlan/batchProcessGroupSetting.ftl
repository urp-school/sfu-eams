<#include "/templates/head.ftl"/>
 <link href="${static_base}/css/tab.css" rel="stylesheet" type="text/css">
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
 <script language="JavaScript" type="text/JavaScript" src="scripts/tabpane.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
 <table id="bar"></table>
 <#assign courseTypes=courseTypes?sort_by("name")/>
   <!--below is the tab panel-->
   <div class="dynamic-tab-pane-control tab-pane" id="tabPane1">
     <script type="text/javascript">
       tp1 = new WebFXTabPane( document.getElementById( "tabPane1") ,false );
     </script>

     <div style="display: block;" class="tab-page" id="tabPage1">
     	  <h2 class="tab">
		  <a href="#">删除课程组</a>
      </h2>
      <script type="text/javascript">tp1.addTabPage( document.getElementById( "tabPage1" ) );</script>
      <form name="removeForm" method="post" action="" onsubmit="return false;">
      <input name="batchAction" value="remove" type="hidden"/>
      <table class="formTable" width="100%">
          <tr>
              <td class="title">请选择要删除组课程类别:</td>
              <td><@htm.i18nSelect selected="" datas=courseTypes name="courseType.id" style="width:200px"/></td>
          </tr>
          <tr>
              <td class="title">学分计算:</td>
              <td><input name="autoCalcCredit" type="checkbox" checked/>根据各个课程类别要求学分，自动计算培养计划要求学分。</td>
          </tr>
          <tr>
              <td class="title">说明:</td>
              <td>如果选择的计划当中，没有要删除的课程组，将不会受到影响。</td>
          </tr>
          <tr>
              <td class="darkColumn" colspan="2" align="center"><button onclick="remove(this.form)">提交删除</button></td>
          </tr>
      </table>
      </form>
     </div>
     <div style="display: block;" class="tab-page" id="tabPage2">
			<h2 class="tab">
			   <a href="#">添加课程组</a>
			</h2>
	  <#if termsCount?exists>
      <table class="formTable" align="center" width="90%">
          <form name="addForm" onsubmit="return false;" method="post" action="">
          <input name="batchAction" value="add" type="hidden"/>
          <tr>
             <td class="title" id="f_courseTypeId" width="20%"><@msg.message key="entity.courseType"/>:</td>
             <td ><@htm.i18nSelect selected="" datas=courseTypes name="courseGroup.courseType.id" style="width:200px"/></td>
             <td class="title" width="20%">上级类别:</td>
             <td ><@htm.i18nSelect selected="" datas=courseTypes name="courseGroup.parentCourseType.id" style="width:200px"/></td>
          </tr>
          <tr>
             <td class="title" id="f_credit">要求总学分:</td>
             <td ><input type="text" name="courseGroup.credit" value="0" style="width:60px" maxlength="3"/></td>
             <td class="title" id="f_creditHour">要求总学时:</td>
             <td ><input type="text"  name="courseGroup.creditHour" value="0" style="width:60px" maxlength="5"/></td>
          </tr>
          <tr>
             <td class="title">学期:</td>
             <td  colspan="3">
             <table class="title" style="width:100%;"cellpadding="0" cellspacing="0">
                 <tr>
                  <#list 0..termsCount-1 as term>
                 <td id="f_term${term+1}">${term+1}</td>
                 </#list>
                 </tr>
             </table>
             </td>
          </tr>
          <tr>
             <td class="title">学分分布:</td>
             <td  colspan="3">
             <table class="title" style="width:100%;"cellpadding="0" cellspacing="0">
                 <tr>
                 <#list 0..termsCount-1 as term>
                 <td><input type="text" name="credit${term_index}" maxlength="3" style="width:100%" value="0"></td>
                 </#list>
                 </tr>
             </table>
             </td>
          </tr>
          <tr>
             <td class="title">周课时分布:</td>
             <td  colspan="3">
             <table class="title" style="width:100%;"cellpadding="0" cellspacing="0">
                 <tr>
                 <#list 0..termsCount-1 as term>
                 <td><input type="text" name="weekHour${term_index}" maxlength="3" style="width:100%" value="0"></td>
                 </#list>
                 </tr>
             </table>
             </td>
          </tr>
          <tr>
              <td class="title">学分计算:</td>
              <td colspan="3"><input name="autoCalcCredit" type="checkbox" checked/>根据各个课程类别要求学分，自动计算培养计划要求学分。</td>
          </tr>
          <tr>
             <td class="darkColumn" align="center" colspan="4">
               <button onClick='add(this.form)'><@bean.message key="system.button.submit"/></button>&nbsp;&nbsp;&nbsp;
               <input type="hidden" name="courseGroup.creditPerTerms" value=""/>
               <input type="hidden" name="courseGroup.weekHourPerTerms" value=""/>
             </td>             
          </tr>
          </form>
      </table>
      <#else>
       	 选择的计划中学期个数不同，不能同时进行添加课程组。
        </#if>
     </div>
     <div style="display: block;" class="tab-page" id="tabPage3">
     <h2 class="tab">
		  <a href="#">添加课程</a>
      </h2>
      <form name="showForm" method="post" action="" onsubmit="return false;">
      <table class="formTable" width="100%">
         <tr>
             <td class="title" id="f_termSeq">学期:</td>
             <td >
                <input id="termSeq" name="termSeq"></input>
             </td>
          </tr>
          <tr>
              <td class="title">请选择课程类别:</td>
              <td><@htm.i18nSelect selected="" datas=courseTypes name="courseType.id" style="width:200px"/></td>
          </tr>
           <tr>
             <td class="title">开课院系:</td>
             <td >
              <@htm.i18nSelect selected="" datas=departmentList name="department.id" style="width:200px"/>
             </td>
          </tr>
          <tr>
              <td class="title" id="f_courseCodes">课程代码:</td>
              <td><textarea name="courseCodes" cols="55" rows="3"></textarea>每个课程代码用,分开。</td>
          </tr>
          <tr>
              <td class="darkColumn" colspan="2" align="center"> <button onClick='showUserChoiceInfo(this.form)'><@bean.message key="action.next"/></td>
          </tr>
      </table>
      </form>
     </div>
     </div>
     
   <script type="text/javascript">
		 setupAllTabs();
		 var bar =new ToolBar("bar","批量操作${teachPlans?size}个培养计划",null,true,true);
		 bar.addBack("<@msg.message key="action.back"/>");
		 function commonSetting(form){
		     form.action="teachPlan.do?method=batchProcessGroup";
		     addInput(form,"teachPlanIds",'${RequestParameters['teachPlanIds']}');
		     addInput(form,"params","${RequestParameters['params']?default('')}");
		 }
		 function remove(form){
		     commonSetting(form);
		     if(confirm("是否确认删除选择的课程类别对应的课程组?")){
		         form.submit();
		     }
		 }
		 <#if termsCount?exists>
		 function add(form){
			  var a_fields = {
			           <#list 0..termsCount-1 as term>
			             'credit${term_index}':{'l':'第${term+1}学期学分', 'r':true, 't':'f_term${term+1}','f':'unsignedReal'},
			             'weekHour${term_index}':{'l':'第${term+1}学期周课时', 'r':true, 't':'f_term${term+1}','f':'unsigned'},
			           </#list>           
			         'courseGroup.creditHour':{'l':'要求总学时', 'r':true, 't':'f_creditHour','f':'unsigned'},
			         'courseGroup.credit':{'l':'要求总学分','r':true,'t':'f_credit','f':'unsignedReal'},
			         'courseGroup.courseType.id':{'l':'<@msg.message key="entity.courseType"/>','r':true,'t':'f_courseTypeId'}
			       };
       var v = new validator(form , a_fields, null);
       if (v.exec()) {         
         var creditPerTerms =",";
         var weekHourPerTerms =",";
         var credits=0;
         for(var i=0;i<${termsCount};i++){
            creditPerTerms+=form['credit'+i].value+",";
            weekHourPerTerms+=form['weekHour'+i].value+",";
            credits +=new Number(form['credit'+i].value);
                         }
         form['courseGroup.creditPerTerms'].value=creditPerTerms;
         form['courseGroup.weekHourPerTerms'].value=weekHourPerTerms;
         if(credits!=new Number(form['courseGroup.credit'].value)){
           if(!confirm("总学分学分分布的各个值不吻合\n总学分为:"+form['courseGroup.credit'].value+"\n学分分布中的总和为："+credits+"\n是否提交保存?"))
           return;
                          }
			     commonSetting(form);
			     if(confirm("是否确认批量添加课程组?")){
			         form.submit();
               }
               }
		 }
		 </#if>
		 function showUserChoiceInfo(form){
		 	  var a_fields = {
			         'termSeq':{'l':'学期', 'r':true, 't':'f_termSeq','f':'unsignedReal'},
			         'courseCodes':{'l':'课程代码','r':true,'t':'f_courseCodes'}
			       };
		       var v = new validator(form , a_fields, null);
		       if (v.exec()) {   
				    form.action="teachPlan.do?method=showUserChoiceInfo";
				    addInput(form,"teachPlanIds",'${RequestParameters['teachPlanIds']}');
				    addInput(form,"params","${RequestParameters['params']?default('')}");
				    form.submit();
		       }
		 }
		 </script>
  </body>
<#include "/templates/foot.ftl"/>