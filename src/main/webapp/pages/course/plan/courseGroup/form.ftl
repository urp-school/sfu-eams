<#include "/templates/head.ftl"/>
<script src='dwr/interface/planCourseService.js'></script>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>

<body   valign="top">
<#assign labelInfo><B>培养计划课程组管理[${teachPlan.enrollTurn}&nbsp;<@i18nName teachPlan.stdType/>&nbsp;<@i18nName teachPlan.department/>&nbsp;<@i18nName teachPlan.speciality?if_exists/>&nbsp;<@i18nName teachPlan.aspect?if_exists/>]</B></#assign>
<table id="courseGroupFormBar"></table>
<script>
  var bar= new ToolBar("courseGroupFormBar","${labelInfo}",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("<@msg.message key="action.close"/>","window.close()");
</script>
  <table width="100%" align="center" style="cellpadding:0;cellspacing:0;" LEFTMARGIN="0" TOPMARGIN="0">
     <tr valign="top">
         <td width="43%" ><#include "planCourseForm.ftl"/></td>
         <td>
	         <iframe  src="courseSelector.do?method=search&pageNo=1&pageSize=10&multi=false"
		     id="selectListFrame" name="selectListFrame"
		     marginwidth="0" marginheight="0" scrolling="no"
		     frameborder="0" height="100%" width="100%">
		     </iframe>
         </td>
     </tr>
  </table>
  <table id="groupBar"></table>
  <script>
    var bar = new ToolBar("groupBar","<@bean.message key="entity.courseType"/>:<@i18nName courseGroup.courseType/>",null,true,true);
    bar.addItem("<@bean.message key="action.new"/>","newPlanCourse()");
    bar.addItem("<@bean.message key="action.save"/>","saveGroup(this.form)");
    bar.addItem("<@bean.message key="action.modify"/>","edit()");
    bar.addItem("<@bean.message key="action.delete"/>","removePlanCourse()");
  </script>
      <table style="border-collapse: collapse;background-color:#c7dbff;width:100%;font-size:10pt">
       <form name="courseGroupForm" action="courseGroup.do?method=save" method="post" action="">
	      <tr>
		      <input type="hidden" name="teachPlan.id" value="${RequestParameters['teachPlan.id']}"/>
		      <input type="hidden" name="planCourse.courseGroup.id" value="${courseGroup.id?if_exists}"/>
		      <input type="hidden" name="courseGroup.id" value="${courseGroup.id?if_exists}"/>
		      <input type="hidden" name="courseGroup.courseType.id" value="${(courseGroup.courseType.id)?if_exists}"/>		      
   	          <input type="hidden" name="courseGroup.creditPerTerms" value=""/>
   	          <input type="hidden" name="courseGroup.weekHourPerTerms" value=""/>
   	         <#--
   	         <td style="width:70px"><@msg.message key="entity.courseType"/>:</td>
             <td style="width:100px">
               <select name="courseGroup.courseType.id" style="width:100%">
               <#list couseTypeList?sort_by("name") as courseType>
                   <option value="${courseType.id}" <#if (courseGroup.courseType.id)?exists&&courseGroup.courseType.id==courseType.id>selected</#if> >${courseType.name}</option>
               </#list>
               </select>
             </td>
             <td style="width:70px">上级类别:</td>
             <td style="width:100px">
               <select name="courseGroup.parentCourseType.id" style="width:100%">
                   <option id=""></option>
               <#list couseTypeList?sort_by("name") as courseType>
                   <option value="${courseType.id}" <#if (courseGroup.parentCourseType.id)?exists&&courseGroup.parentCourseType.id==courseType.id>selected</#if>>${courseType.name}</option>
               </#list>
               </select>
             </td>
             -->
	         <td style="width:90px" id="f_creditHour">总学时<font color="red">*</font>
		      <input style="width:35px" maxlength="4" type="text" name="courseGroup.creditHour" value="${courseGroup.creditHour}"/>
	         </td>
		     <td style="width:90px" id="f_credit">总学分<font color="red">*</font>
		      <input type="text" name="courseGroup.credit" maxlength="5" style="width:30px" value="${courseGroup.credit}"/>
	         </td>
		     <td style="width:80px" id="f_creditPerTerms">学分分布<font color="red">*</font></td>
		     <#list courseGroup.creditPerTerms[1..courseGroup.creditPerTerms?length-2]?split(",") as credit>
             <td style="width:25px" >
                <input type="text" name="credit${credit_index}" style="width:100%;border:1 solid #000000;" value="${credit}" maxlength="6"/>
             </td>
		     </#list>
		      <td style="width:80px" id="f_weekHourPerTerms">周课时分布<font color="red">*</font></td>
		     <#list courseGroup.weekHourPerTerms[1..courseGroup.weekHourPerTerms?length-2]?split(",") as weekHour>
             <td style="width:25px" >
                <input type="text" name="weekHour${weekHour_index}" style="width:100%;border:1 solid #000000;" value="${weekHour}" maxlength="4"／>
             </td>
		     </#list>
		     <td></td>
	      </tr>
	      </form>
      </table>
    <@table.table width="100%" sortable="true" id="sortTable">
	    <@table.thead>
	      <@table.selectAllTd id="planCourseId"/>
	      <@table.sortTd width="7%" name="attr.courseNo" id="course.code"/>
	      <@table.sortTd width="20%" name="attr.courseName" id="course.name"/>
	      <@table.sortTd width="7%" text="开课学期" id="termSeq"/>
	      <@table.sortTd width="15%" text="开课院系" id="teachDepart.name"/>
	      <@table.sortTd  width="5%" name="attr.credit" id="credit"/>
	      <@table.sortTd  width="5%" text="学时" id="creditHour"/>
	      <@table.sortTd  width="5%" text="周课时" id="weekHour"/>
	      <@table.sortTd  width="10%" text="可替代课程" id="substitution.name"/>
	      <@table.sortTd  width="10%" text="HSK约束" id="HSKDegree.name"/>
	      <td width="10%">先修课程</td>
	    </@>
	    <@table.tbody datas=planCoursesList;planCourse,planCourse_index>
	      <@table.selectTd  id="planCourseId" value=planCourse.id/>
		     <td  align="left">&nbsp;${planCourse.course.code}</td>
		     <td  align="left">&nbsp;${planCourse_index+1}&nbsp;<@i18nName planCourse.course/></td>
		     <td><#if planCourse.termSeq?contains(",")><@eraseComma planCourse.termSeq/><#else>${planCourse.termSeq}</#if></td>
		     <td><@i18nName planCourse.teachDepart/></td>
		     <td>${(planCourse.course.credits)?if_exists}</td>
		     <td>${(planCourse.course.extInfo.period)?if_exists}</td>
		     <td>${(planCourse.course.weekHour)?if_exists}</td>
		     <td><@i18nName planCourse.substitution?if_exists/></td>
		     <td><@i18nName planCourse.HSKDegree?if_exists/></td>
		     <td><#list planCourse.preCourses as course>
		            <@i18nName course/>,
		         </#list>
		     </td>
	      </@>
	    <tr class="grayStyle"><td colspan="11" align="center">课程列表(选择一门课程，点击右上侧的按钮进行修改)</td>
	    </tr>
	  </@>
  <script language="javascript">
      var termsCount=${courseGroup.creditPerTerms[1..courseGroup.creditPerTerms?length-2]?split(",")?size};
      function validateCourseGroup(){
        var form = document.courseGroupForm;
        var a_fields = {
           <#list 0..courseGroup.creditPerTerms[1..courseGroup.creditPerTerms?length-2]?split(",")?size-1 as term>
             'credit${term_index}':{'l':'第${term+1}学期学分', 'r':true,'t':'f_creditPerTerms', 'f':'unsignedReal'},
             'weekHour${term_index}':{'l':'第${term+1}学期周课时', 'r':true,'t':'f_weekHourPerTerms', 'f':'unsigned'},
           </#list>
         'courseGroup.creditHour':{'l':'要求学时', 'r':true,'f':'unsigned' ,'t':'f_creditHour'},
         'courseGroup.credit':{'l':'要求学分', 'r':true, 'f':'real', 't':'f_credit'}
        };
        var v = new validator(form, a_fields, null);
        return (v.exec());
     }
     function validateCreditAndHour(){
         var form = document.courseGroupForm;
         var errors="";
         if(!/^\d+$/.test(form['courseGroup.creditHour'].value))
            errors+="学时格式不正确，应为正整数";
         if(!/^\d*\.?\d*$/.test(form['courseGroup.credit'].value))
            errors+="学分格式不正确，应为正小数";
         if(errors!=""){alert(errors);return false;}
         else return true;
     }
     function saveGroup(){
         if(!validateCourseGroup())return;
         var form = document.courseGroupForm;
         var creditPerTerms =",";
         var weekHourPerTerms =",";
         var credits=0;
         for(var i=0;i<termsCount;i++){
             credits +=new Number(form['credit'+i].value);
             creditPerTerms+=form['credit'+i].value+",";
             weekHourPerTerms+=form['weekHour'+i].value+",";
         }
         if(credits!=new Number(form['courseGroup.credit'].value)){
           if(!confirm("总学分学分分布的各个值不吻合\n总学分为:"+form['courseGroup.credit'].value+"\n学分分布中的总和为："+credits+"\n是否提交保存?"))
           return;
         }
         form['courseGroup.weekHourPerTerms'].value=weekHourPerTerms;
         form['courseGroup.creditPerTerms'].value=creditPerTerms;
         form.action="courseGroup.do?method=save";
         form.submit();
     }
    function getPlanCourseId(){
        return (getRadioValue(document.getElementsByName("planCourseId")));
    }
    var selectedKind="course";
    function edit(){
        var id = getPlanCourseId();
        if(""==id) {alert("请选择一个组内的课程");return;}
        selectedKind="planCourse.teachDepart";
        selectListFrame.location="collegeSelector.do?method=search&multi=false&pageNo=1&pageSize=10";
        planCourseService.getPlanCourse(setData,id);
    }
    
    function setData(data){
       var form=document.planCourseForm;
       form['planCourse.id'].value=data['id'];
       if (null != data.course) {
	       form['planCourse.course.id'].value=data.course.id;
	       form['planCourse.course.name'].value=data.course.name;
	       form['planCourse.course.weekHour'].value=data.course.weekHour;
	       form['planCourse.course.credits'].value=data.course.credits;
	       //form['planCourse.course.extInfo.period'].value=data.course.extInfo.period;
       }
       form['planCourse.termSeq'].value=data['termSeq'];
       if (null != data.teachDepart) {
	       form['planCourse.teachDepart.id'].value=data.teachDepart.id;
	       form['planCourse.teachDepart.name'].value=data.teachDepart.name;
       }
       if(data.course.weekHour==null) {
           form['planCourse.course.weekHour'].value="0";
       } else {
           form['planCourse.course.weekHour'].value=data.course.weekHour;
       }
       if(null!=data.substitution){
	       form['planCourse.substitution.id'].value=data.substitution.id;
    	   form['planCourse.substitution.name'].value=data.substitution.name;
       }
       if(null!=data.HSKDegree){
       	   setSelected(form['planCourse.HSKDegree.id'],data.HSKDegree.id);
       }
       if(null1=data.remark)
	       form['planCourse.remark'].value=data.remark;
       var preCourseIds="";
       var preCourseNames="";
       for(var i=0;i<data.preCourses.length;i++){
           if(preCourseIds!="") preCourseIds+=",";
           preCourseIds +=data.preCourses[i].id;
           if(preCourseNames!="") preCourseNames+=",";
           preCourseNames +=data.preCourses[i].name;
       }
       if(preCourseIds!=""){
       		form['preCourses.id'].value=preCourseIds;
       		form['preCourses.name'].value=preCourseNames;
       }else{
       		form['preCourses.id'].value="";
       		form['preCourses.name'].value="";
       }
       var courseSelectorButton=document.getElementById('selectCourseButton');
       courseSelectorButton.disabled=true;
    }
    /**
     * 删除培养计划中的课程
     */
    function removePlanCourse(){
       var id = getPlanCourseId();
       if(""==id) {alert("请选择一个组内的课程");return;}
       else if(confirm("删除课程组内的课程，确认操作？")){
           courseGroupForm.action="planCourse.do?method=remove&planCourse.id="+id
              +"&autoStatCredit=${courseGroup.courseType.isCompulsory?default(false)?string("1","0")}";
           courseGroupForm.submit();
       }
    }
   /**
    * 返回培养计划列表
    */
   function backToPlan(){
       courseGroupForm.action="teachPlan.do?method=edit";
       courseGroupForm.submit();
   }
   

   /**
    * 显示选择列表
    */
   function displaySelectList(kind){
         selectedKind=kind;
         if(kind=="planCourse.teachDepart"){
            selectListFrame.location=
               "collegeSelector.do?method=search&multi=false&pageNo=1&pageSize=10";
         }else if(kind=="planCourse.substitution"||kind=="course"){
            selectListFrame.location=
              "courseSelector.do?method=search&multi=false&pageNo=1&pageSize=10";
         }else if(kind=="preCourses"){
            selectListFrame.location=
              "courseSelector.do?method=search&multi=true&pageNo=1&pageSize=10";
         }else
          alert("unsupported list kind");
    }
    /**
     * 清除已经选择的值
     */
    function resetSelectList(kind){
	    planCourseForm[kind+".id"].value="";
	    planCourseForm[kind+".name"].value="";
    }
	/**
	 * 响应右侧的选择
	 */
    function select(ids,names,ext1,ext2,ext3,ext4,ext5){
        if(ids==""){alert("请选择一个");return;}
        if(selectedKind=="course") {
            planCourseForm['planCourse.id'].value="";
            planCourseForm['planCourse.course.id'].value=ids;
            planCourseForm['planCourse.course.name'].value=names;
            planCourseForm['planCourse.course.credits'].value=ext1;
            planCourseForm['planCourse.course.extInfo.period'].value=ext2;
            planCourseForm['planCourse.course.weekHour'].value=ext3;
            planCourseForm['planCourse.teachDepart.id'].value=ext4;
            planCourseForm['planCourse.teachDepart.name'].value=ext5;
        }
        else if(selectedKind=="preCourses"){
            selectedIds = ids.split(",");
            selectedNames=names.split(",");
	        for(var i=0;i<selectedIds.length;i++){
   	           if(planCourseForm['preCourses.id'].value.indexOf(selectedIds[i])==-1){
   	                if(planCourseForm['preCourses.id'].value!="")
   	                   planCourseForm['preCourses.id'].value+=","
   	                 planCourseForm['preCourses.id'].value+=selectedIds[i];
   	                 if(planCourseForm['preCourses.name'].value!="")
   	                     planCourseForm['preCourses.name'].value+=","
   	                 planCourseForm['preCourses.name'].value+=selectedNames[i];
   	            }
	         }
	    }
	    else if(selectedKind=="planCourse.teachDepart"||selectedKind=="planCourse.substitution"){
  	        planCourseForm[selectedKind+".id"].value=ids;
	        planCourseForm[selectedKind+".name"].value=names;
        }else{
            alert("unsurported ");
        }
    }
	/**
	 * 验证培养计划的课程
	 */
    function validatePlanCourse(){
 	   var form = document.planCourseForm;
	   var wrongFormat="";
	   var terms = form['planCourse.termSeq'].value;
       if(terms==''){alert("学期为必填项");setTermFocus();return false;}
       if(!/^[0-9,]{1,}$/.test(terms))
          {alert('开课学期为100以下数字和,组成的.');setTermFocus();return false;}
       else if(terms.indexOf(',')!=-1){
	       if(terms.indexOf(',')!=0)
	       terms=","+terms;
	       if(terms.lastIndexOf(',')!=terms.length-1)terms+=",";
	       var termsArray = terms.split(',');
	       for(var i=0;i<termsArray.length;i++){
	          if(termsArray[i]!=""&&termsArray[i]>termsCount){
	          	wrongFormat+=termsArray[i]+"大于该组的最大学期数\n";
	          }
	       }
	       form['planCourse.termSeq'].value=terms;
       }

       if(form['planCourse.teachDepart.id'].value=="")
           wrongFormat+="开课院系不能为空"+"\n";
       if(form['planCourse.course.id'].value=="")
           wrongFormat+="课程不能为空\n";
//       if(!/^\d*\.?\d*$/.test(form['planCourse.course.credits'].value))
//           wrongFormat+="学分为正小数\n";
//       if(!/^\d+$/.test(form['planCourse.course.extInfo.period'].value))
//           wrongFormat+="学时为正整数\n";
//       if(!/^\d+$/.test(form['planCourse.course.weekHour'].value))
//           wrongFormat+="周课时为正整数\n";
       if(wrongFormat!="") {alert(wrongFormat);return false;}
       else return true;
    }

    function setTermFocus(){
       var termInput = document.getElementById("planCourse_termSeq");
       termInput.select();
    }
    /**
     * 保存培养计划中的课程
     */
    function savePlanCourse(form){
       if(!validatePlanCourse()) return;
       if(form['preCourses.id'].value!=""){
          var preCourseIds=form['preCourses.id'].value.split(",");
          var preCourseNames=form['preCourses.name'].value.split(",");
        
          for(var i=0;i<preCourseIds.length;i++){
             var input1 = document.createElement('input');
		     input1.name="planCourse.preCourses["+i+"].id";
	         input1.value=preCourseIds[i];
	         input1.type="hidden";
		     planCourseForm.appendChild(input1);
             var input2 = document.createElement('input');
		     input2.name="planCourse.preCourses["+i+"].name";
	         input2.value=preCourseNames[i];
	         input2.type="hidden";
		     planCourseForm.appendChild(input2);
         }
        if(preCourseIds.length>0){
	       var input = document.createElement('input');
		   input.name="preCourseCount";
	       input.value=preCourseIds.length;
	       input.type="hidden";
		   planCourseForm.appendChild(input);
       }
	   }else{
	       var input = document.createElement('input');
		   input.name="preCourseCount";
	       input.value="0";
	       input.type="hidden";
		   planCourseForm.appendChild(input);
	   }
	   form.submit();
	}
    function newPlanCourse(){
       var form=document.planCourseForm;
       form['planCourse.id'].value="";
       form['planCourse.course.id'].value="";
       form['planCourse.course.name'].value="请从课程列表中选择一门课程";
       form['planCourse.termSeq'].value="";
       form['planCourse.teachDepart.id'].value="";
       form['planCourse.teachDepart.name'].value="";
       form['planCourse.substitution.id'].value="";
   	   form['planCourse.substitution.name'].value="";
  	   form['preCourses.id'].value="";
   	   form['preCourses.name'].value="";
	   form['planCourse.course.credits'].value="";
       form['planCourse.course.extInfo.period'].value="";
       form['planCourse.course.weekHour'].value="";
	   form['planCourse.HSKDegree.id'].value="";
       form['planCourse.remark'].value="";
       var courseSelectorButton=document.getElementById('selectCourseButton');
       courseSelectorButton.disabled=false;
       selectedKind="course";
       selectListFrame.location="courseSelector.do?method=search&pageNo=1&pageSize=10&multi=false";
    }
  </script>
 </body>