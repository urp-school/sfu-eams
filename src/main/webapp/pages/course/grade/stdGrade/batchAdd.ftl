<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" >
  <form name="actionForm" action="" method="post" onsubmit="return false;">
  <#assign stdCount=15>
  <input type="hidden" name="stdCount" value="${stdCount}"/>
  <table id="myBar"></table>
  <table align="center" width="90%" class="listTable"> 
   <tr>
     <td class="grayStyle" width="10%" id="f_studentType">
       &nbsp;<@bean.message key="entity.studentType"/>：
     </td>
     <td class="brightStyle">
          <select id="stdType" name="calendar.studentType.id" style="width:100px;">
            <option value="${RequestParameters['courseGrade.calendar.studentType.id']?if_exists}"><@bean.message key="filed.choose"/></option>
          </select>
     </td>
     <td class="grayStyle" id="f_year">&nbsp;<@bean.message key="attr.year2year"/>：</td>
     <td class="brightStyle">
 		  <select id="year" name="calendar.year" style="width:100px;">
            <option value="${RequestParameters['courseGrade.calendar.year']?if_exists}"><@bean.message key="filed.choose"/></option>
          </select>
     </td>
     <td class="grayStyle" width="10%" id="f_term">
       &nbsp;<@bean.message key="attr.term"/>：
     </td>
     <td class="brightStyle">
         <select id="term" name="calendar.term" style="width:100px;">
            <option value="${RequestParameters['courseGrade.calendar.term']?if_exists}"><@bean.message key="filed.choose"/></option>
          </select>
     </td>
   </tr>
   <tr>
     <td class="grayStyle" id="f_planType">
        &nbsp;成绩类别：
     </td>
   	 <td class="brightStyle" >
   	    <@htm.i18nSelect datas=gradeTypes name="gradeTypeId" selected="${RequestParameters['gradeTypeId']?if_exists}" style="width:100px"/>
   	 </td>
     <td class="grayStyle" width="13%">
       &nbsp;专业类别：
     </td>
   	 <td class="brightStyle">
   	    <select name="courseGrade.majorType.id" style="width:100px">
   	      <option value="1"<#if RequestParameters['courseGrade.majorType.id']?if_exists="1"> selected</#if>>第一专业</option>
   	      <option value="2"<#if RequestParameters['courseGrade.majorType.id']?if_exists="2"> selected</#if>>第二专业</option>
   	    </select>
   	 </td>
     <td class="grayStyle" width="13%" id="f_markStyleId">
       &nbsp;<@bean.message key="entity.markStyle"/>：
     </td>
   	 <td class="brightStyle">
   	 	<@htm.i18nSelect datas=markStyles name="markStyleId" selected="${RequestParameters['markStyleId']?if_exists}" style="width:100px"/>
   	 </td>
   </tr>
   <tr>
     <td class="grayStyle" id="n_courseId">
        &nbsp;<@msg.message key="attr.courseNo"/><font color="red">*</font>：
     </td>
   	 <td class="brightStyle" >
   	    <input type='hidden' name='courseId' id='courseId' value=''/>
		<input type='text' name='courseCode' style="width:100px" maxlength="32" onChange='getCourseInfo(event)'/>
   	 </td>
   	 <td class="grayStyle"><@msg.message key="attr.courseName"/>：</td>
   	 <td id="courseNameTD"></td>
   	 <td class="grayStyle">&nbsp;成绩状态：</td>
	 <td>
	   <select name="courseGradeStatus" style="width:100px">
	       <option value="0">新增</option>
	       <option value="1" selected>确认</option>
	       <option value="2">已发布</option>
	   </select>
	 </td>
   </tr>
   <tr>
    <td colspan="6" valign="center" style="padding-left: 5px">
    说明：<br>
    　　输入课程代码，系统自动查找任务。<br>
    　　说明：对于已经存在的成绩，再行输入保存将进行更改操作。
    </td>
   </tr>
  </table>
  <table width="90%" align="center" class="listTable">
      <tbody id='addGradeTable'>
	   <tr align="center" class="darkColumn">
  	     <td width='10%'><@msg.message key="attr.personName"/></td>
	     <td width='10%'><@msg.message key="attr.stdNo"/></td>
	     <td width='10%'>成绩</td>
	     <td width='10%'><@msg.message key="attr.credit"/></td>
	     <td width='10%'><@msg.message key="attr.taskNo"/></td>
	     <td width='10%'><@msg.message key="entity.courseType"/></td>
	     <td width='10%'>修读类别</td>
	     <td width='10%'>考试情况</td>
  	     <td width='25%'>说明</td>
	   </tr>
	   <#list 0..stdCount-1 as i>
	   <tr class="grayStyle" align="center">
  			<td id="stdName${i}"></td>
			<td><input type='text' class="text" name='stdCode${i}' maxlength="32" style="width:100px" onChange='getStdName(event);'/></td>
		    <td><input name="score${i}" class="text" onchange="checkScore(event)" style="width:100px" maxlength="3"/></td>
            <td id="credit${i}"></td>
            <td id="taskSeqNo${i}"></td>
            <td id="courseType${i}"></td>
            <td id="courseTakeType${i}"></td>
			<td id="examStatus${i}"></td>
       	    <td id="comment${i}"></td>
	   </tr>
	   </#list>
	   <tr align="center" class="darkColumn">
	   	<td colspan='10'>
	   		<button onClick="saveGrade(0)" class="buttonStyle">提交</button>
	   		<button onClick="saveGrade(1)" class="buttonStyle">提交,并添加下一批</button>
	   	</td>
	   </tr>
	 </tbody>
     </table>
  <#include "/templates/calendarSelect.ftl"/>
  <script src='dwr/interface/stdGrade.js'></script>
  <script src='dwr/interface/courseDao.js'></script>
  <script>
     var bar = new ToolBar("myBar","学生成绩添加（按课程代码－批量）",null,true,true);
     bar.setMessage('<@getMessage/>')
     bar.addClose();
     var form =document.actionForm;
     function getCourseInfo(event){
        var input = getEventTarget(event);
        if(input.value!=""){
	        courseDao.getCourseByCode(setCourseData,form['courseCode'].value);
         }
     }
     function setCourseData(data){
        if(null==data){
           $("courseNameTD").innerHTML="<font color='red'>该学期没有该课程或者不唯一</font>";
           form['courseId'].value="";
           form['courseCode'].value="";
        } else {
	        form['courseId'].value=data.id;
	        form['courseCode'].value=data.code;
	        $("courseNameTD").innerHTML=data.name;
        }
     }
     var stdIndexArray= new Array();
     function getStdName(event){
       var input = getEventTarget(event);
       var stdCode= input.value;
       if(stdCode==""){
          clearGradeInfo(input.name.substring("stdCode".length));
       }else{
          stdIndexArray.push(input.name.substring("stdCode".length));
          stdGrade.getCourseGradeInfo(setGradeData,
          stdCode,
          form['calendar.studentType.id'].value,
          form['calendar.year'].value,
          form['calendar.term'].value,
          form['courseCode'].value,
          form['gradeTypeId'].value);
       }
     }
     
     function setGradeData(data){
       index = stdIndexArray.shift();
       if(null!=data[1]){
          $("stdName"+index).innerHTML=data[1];
          if(null!=data[2]){
             $("comment"+index).innerHTML="数据已存在";
             form["score"+index].value=data[2];
          }
          $("examStatus"+index).innerHTML=data[3];
          $("credit"+index).innerHTML=data[4];
          $("taskSeqNo"+index).innerHTML=(data[5] == null ? "" : data[5]);
          $("courseType"+index).innerHTML=data[6];
          $("courseTakeType"+index).innerHTML=data[7];
          if(null==data[8]&&null==data[0]){
            $("comment"+index).innerHTML+="不在上课名单中";
          }
       }else{
          clearGradeInfo(index);
          if(form['courseCode'].value == ""){
            $("stdName"+index).innerHTML="请先确定课程";
          }else{
            $("stdName"+index).innerHTML="该学号不存在";
          }
       }
     }
     //清除某一行中学生的成绩信息
     function clearGradeInfo(index){
          $("stdName"+index).innerHTML="";
          form["stdCode"+index].value="";
          form["score"+index].value="";
          $("credit"+index).innerHTML="";
          $("taskSeqNo"+index).innerHTML="";
          $("courseType"+index).innerHTML="";
          $("courseTakeType"+index).innerHTML="";
          $("examStatus"+index).innerHTML="";
          $("comment"+index).innerHTML="";
     }
     function checkScore(event){
       input = getEventTarget(event);
       var score= input.value;
       if(""!=score){
         if(isNaN(score)){
            alterErrorScore(input,score+" 不是数字");
         } else if(!/^\d*\.?\d*$/.test(score)) {
            alterErrorScore(input,"请输入0或正实数");
         } else if(parseInt(score)>100) {
            alterErrorScore(input,"百分制输入不允许超过100分");
         }
       }
    }
    //检查分数的合法性
    function alterErrorScore(input,msg){
      input.value="";
      alert(msg);
    }
    function saveGrade(addAnother){
     var errMsg="";
     if(form['courseCode'].value==""){
        errMsg+="课程信息填写不全\n";
     }
     if(form['gradeTypeId'].value==""){
        errMsg+="成绩类型缺失\n";
     }
     for(var i=0;i<${stdCount};i++){
        if(form['stdCode'+i].value!=""&&isEmpty(form['score'+i].value)){
            errMsg+="第"+(i+1)+"行没有成绩\n";
        }
     }
     if(''!=errMsg){alert(errMsg);return;}
     
     form.action ="stdGrade.do?method=batchSaveCourseGrade&addAnother="+addAnother;
     if(confirm("是否提交填写的成绩?")){
        form.submit();
     }
    }
  </script>