<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/common/onReturn.js"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" >
 <table id="myBar" width="100%"></table>
 <script>
   var REEXAM =${REEXAM};
   function GradeTable(){
     this.gradeTypeAndPercent=new Array();
     this.gradeMap=new Object();
     this.gradeArray=new Array();
     this.add=addCourseGrade;
     this.change=changeCourseGrade;
     this.precision=${task.gradeState.precision};
     this.changePrecision=changePrecision;
     this.changeTabIndex=changeTabIndex;
     this.tabByStd=null;
     this.onReturn=null;
     this.setReturnIndex=setReturnIndex;
     this.enableGradeInput=enableGradeInput;
     this.hasEmptyInput=hasEmptyInput;
   }
   function setReturnIndex(form){
      this.onReturn= new OnReturn(form);
   }
   
   function hasEmptyInput(){
      for(var i=0;i<this.gradeTypeAndPercent.length;i++){
         for(var j=0;j<this.gradeArray.length;j++){
            grade = this.gradeArray[j];
            input = document.getElementById(this.gradeTypeAndPercent[i].name+"_"+grade.stdId);
            if(null!=input){
               if(input.value==""){
	              return true;
               }
            }
         }
      }
      return false;
   }
   function enableGradeInput(enabled,score){
      for(var i=0;i<this.gradeTypeAndPercent.length;i++){
         for(var j=0;j<this.gradeArray.length;j++){
            grade = this.gradeArray[j];
            input = document.getElementById(this.gradeTypeAndPercent[i].name+"_"+grade.stdId);
            if(null!=input){
               if(input.value==""){
	               if(!enabled){
	                  input.value='-1';
	               }else{
	                  input.value=score;
	               }
               }
               input.enabled=enabled;
            }
         }
      }
   }
   function changeTabIndex(tabByStd){
     if(this.tabByStd!=tabByStd){
        this.tabByStd=tabByStd;
     }else{
       return;
     }
     this.onReturn.elemts.length=0;
     if(!this.tabByStd){
        for(var i=0;i<this.gradeTypeAndPercent.length;i++){
           for(var j=0;j<this.gradeArray.length;j++){
              grade=this.gradeArray[j];
              input = document.getElementById(this.gradeTypeAndPercent[i].name+"_"+grade.stdId);
              if(null!=input){
                 input.tabIndex=(j+1)+i*this.gradeArray.length;
                 this.onReturn.elemts[input.tabIndex]=input.name;
              }
           }
        }
     }else{
        for(var i=0;i<this.gradeArray.length;i++){
            grade=this.gradeArray[i];
            for(var j=0;j<this.gradeTypeAndPercent.length;j++){
              input = document.getElementById(this.gradeTypeAndPercent[j].name+"_"+grade.stdId);
              if(null!=input){
                 input.tabIndex=(j+1)+i*this.gradeTypeAndPercent.length;
                 this.onReturn.elemts[input.tabIndex]=input.name;
              }
           }
        }
     }
   }
   
   function changePrecision(precision){
     this.precision=precision;
     for(var i=0;i<this.gradeArray.length;i++){
        grade = this.gradeArray[i];
        grade.calcGA(this);
        grade.notifyGA();
     }
   }
   gradeTable= new GradeTable();
   <#list gradeTypes as gradeType>
   gradeTable.gradeTypeAndPercent[${gradeType_index}]=new Object();
   gradeTable.gradeTypeAndPercent[${gradeType_index}].name='${gradeType.shortName}';
   gradeTable.gradeTypeAndPercent[${gradeType_index}].percent=${task.gradeState.getPercent(gradeType)}
   </#list>
   function CourseGrade(stdId,courseTakeTypeId){
      this.stdId=stdId;
      this.courseTakeTypeId=courseTakeTypeId;
      this.calcGA=calcGA;
      this.GA=null;
      this.notifyGA=notifyGA;
      this.examGrades=new Object();
      this.change=changeScore;
   }
   
   function changeScore(input,gradeTable){
      var gradeInfos= input.name.split("_");
      var examType=gradeInfos[0];      
      var score=(""==input.value?null:input.value);
      if(null==score){
         return;
      }
      if(this.examGrades[examType]!=score){
         if(null!=this.examGrades[examType]){
           if(confirm("成绩录入和上次录入结果不一致!\n上次录入结果为:"
             +('-1'==this.examGrades[examType]?"缺考":this.examGrades[examType])+"\n本次录入结果:"+input.value
             +"\n是否改为原来的值? 注: 若以第一次录入为准,则点击确定;若以第二次录入为准,则点击取消!")){
             if('-1'==this.examGrades[examType]){
                input.value='缺考';
                score=this.examGrades[examType];
             }else{
                input.value=this.examGrades[examType];
                score=input.value;
             }
           }
         }
         this.examGrades[examType]=score;
         this.calcGA(gradeTable);
         this.notifyGA();
      }
   }
   function calcGA(gradeTable){
      if(null==this.examGrades['END']){
        this.GA=null;
        return;
      }
      var score=0;
      for(var i=0;i<gradeTable.gradeTypeAndPercent.length;i++){
          var examScore=((this.examGrades[gradeTable.gradeTypeAndPercent[i].name]==null)?0:this.examGrades[gradeTable.gradeTypeAndPercent[i].name]);
          if(examScore=='-1'||"缺考"==examScore){
             this.GA=null;return;
          }else{
            score+=examScore*gradeTable.gradeTypeAndPercent[i].percent;
          }
      }
      var multi=Math.pow(10,parseInt(gradeTable.precision)+1);
      score=Math.floor(score*multi);
      if(score%10>=5){
        score+=10;
      }
      score=Math.floor(score/10);
      this.GA=score/(multi/10);
   }
   function notifyGA(){
      var GAtd = document.getElementById("GA_"+this.stdId);
      if(null!=GAtd){
         if(null==this.GA){
           GAtd.innerHTML="";
         }else if(this.GA<60){
           GAtd.innerHTML="<font color='red'>"+this.GA+"</font>";
         }else{
           GAtd.innerHTML=this.GA;
         }
      }
   }
   function addCourseGrade(index,stdId,courseTakeTypeId){
      var grade = new CourseGrade(stdId,courseTakeTypeId);
      this.gradeMap[stdId]=grade;
      this.gradeArray[index]=grade;
      return grade;
   }
   var error;
   //检查分数的合法性
   function alterErrorScore(input,msg){
      error=true;
      input.value="";      
      alert(msg);
   }
   function changeCourseGrade(input){
      var gradeInfos= input.name.split("_");
      this.gradeMap[gradeInfos[1]].change(input,this);
   }
   function checkScore(input){
      var score= input.value;
      error=false;
      if(""!=score){
         if(isNaN(score)){
         	//if("缺考"!=score)
            alterErrorScore(input,score+" 不是数字");
         }
         else if(!/^\d+$/.test(score)){
            alterErrorScore(input,"请输入0或正实数");
         }
         else if(parseFloat(score)>100){
            alterErrorScore(input,"输入成绩不允许超过100分");
         }
      }
      
      if(!error){
         input.style.backgroundColor='white';
         gradeTable.change(input);
      }
   }
 </script>
 <#assign firstInput><@msg.message key='grade.1stInput'/></#assign>
 <#assign secondInput><@msg.message key='grade.2ndInput'/></#assign>
 <#assign inputComplete><@msg.message key='grade.inputComplete'/></#assign>
 <#assign enrolTimeTip=[firstInput,secondInput,inputComplete]>
 
 <#macro gradeTd(gradeType,courseTake)>
       <td><input class="text" onfocus="this.style.backgroundColor='yellow'"
           onblur="this.style.backgroundColor='white'"
           onchange="checkScore(this)" 
           id="${gradeType.shortName}_${courseTake.student.id}"
           name="${gradeType.shortName}_${courseTake.student.id}" 
           value="" style="width:80px" maxlength="10"/></td>
 </#macro>
 <#macro displayGrades(index,courseTake)>
    <td>${index+1}</td>
    <td>${courseTake.student.code}</td>
    <td>${courseTake.student.name}<#if courseTake.courseTakeType.id=RESTUDY>(重修)<#elseif courseTake.courseTakeType.id=REEXAM>(免修不免试)</#if></td>
    <#if courseTake.courseGrade?exists>
    	<#local grade=courseTake.courseGrade>
    </#if>
    <script>courseGrade = gradeTable.add(${index},'${courseTake.student.id}',${courseTake.courseTakeType.id})</script>
    <#list gradeTypes as gradeType>
     <#if gradeType.examType?exists &&!(courseTake.isAttendExam(gradeType.examType))>
     <td><@i18nName courseTake.getExamTake(gradeType.examType).examStatus/></td>
     <#else>
		 <#if grade?exists && grade.getExamGrade(gradeType)?exists>
		      <script>courseGrade.examGrades['${gradeType.shortName}']='${grade.getScore(gradeType)?default("-1")}';</script>
		      <#--1)当前已经有成绩了，但是录入次数还是1; 2)成绩已经确认; 3)该成绩的录入次数大于最小录入次数-->
		      <#if enrolTimeMap[gradeType.id?string]=1 || grade.getExamGrade(gradeType).isConfirmed || (enrolTimeMap[gradeType.id?string]>minEnrol)>
		      <td>${grade.getScoreDisplay(gradeType)}</td>
		      <#else><@gradeTd gradeType,courseTake/></#if>
	     <#else>
		       <#if (enrolTimeMap[gradeType.id?string]>1)><#--缺考(该录入第二次但是上一次没有给成绩)允许再次录入,原始成绩计入-1 -->
		       <td><input type="text"  onfocus="this.style.backgroundColor='yellow'" onblur="this.style.backgroundColor='white'" onchange="checkScore(this)"
		        class="text" value="" id="${gradeType.shortName}_${courseTake.student.id}"
		       name="${gradeType.shortName}_${courseTake.student.id}"  style="width:80px"></td>
		       <script>courseGrade.examGrades['${gradeType.shortName}']='-1';</script>
		       <#else><@gradeTd gradeType,courseTake/></#if>
	     </#if>
     </#if>
    </#list>
    <td style="width:80px" id="GA_${courseTake.student.id}"><#if grade?exists>${grade.getScoreDisplay(GA)}</#if></td>
 </#macro>
 
 	 <div align='center' style="font-size:15px"><B><@i18nName systemConfig.school/>${task.calendar.studentType.name}课程成绩登记表</B><br>
 	  <@msg.message key="attr.year2year"/>${task.calendar.year} &nbsp;<@msg.message key="attr.term"/> ${task.calendar.term}</font>
 	 </div>
<#if task.teachClass.courseTakes?size == 0>
	<#list 1..2 as i><br></#list>
	<table width="90%" align="center" style="background-color:yellow">
		<tr style="color:red"><th>当前没有可以录入成绩的学生。<th></tr>
	</table>
	<#list 1..2 as i><br></#list>
</#if>
 	 <form name="gradeForm" method="post" onsubmit="return false;" action="">
 	 <input name="taskId" value="${task.id}" type="hidden"/>
 	 <table width='90%' align='center' border='0' style="font-size:13px">
 	 	<tr>
	 	 	<td width='25%'><@msg.message key="attr.courseNo"/>:${task.course.code}</font></td>
	 	 	<td width='40%'><@msg.message key="attr.courseName"/>:${task.course.name}</font></td>
	 	 	<td align='left'><@msg.message key="entity.courseType"/>:${task.courseType.name}</font></td>
 	 	</tr>
 	 	<tr>
	 	 	<td><@msg.message key="attr.taskNo"/>:${task.seqNo?if_exists}</font></td>
	 	 	<td><@msg.message key="task.arrangeInfo.primaryTeacher"/>:<@getTeacherNames task.arrangeInfo?if_exists.teachers/></font></td>
	 	 	<td align='left'>
                <@msg.message key="grade.scorePrecision"/> :
	           <select name="precision" onchange="gradeTable.changePrecision(this.value)">
	 	          <option value="0" <#if task.gradeState.precision=0>selected</#if>><@msg.message key="grade.precision0"/></option>
	           <#--   <option value="1" <#if task.gradeState.precision=1>selected</#if>><@msg.message key="grade.precision1"/></option> -->
	 	 	   </select>
 	 	    </td>
 	 	</tr>
 	 	<tr>
 	 	    <td><#list gradeTypes as gradeType><@i18nName gradeType/>:${task.gradeState.getPercent(gradeType)?string.percent}&nbsp;</#list></td>
 	 	    <td><@msg.message key="grade.recordMode"/>:<input type="radio" name="inputTabIndex" onclick="gradeTable.changeTabIndex(true)" checked><@msg.message key="grade.recordModeByStd"/><input type="radio"  name="inputTabIndex" onclick="gradeTable.changeTabIndex(false)" ><@msg.message key="grade.recordModeByScore"/> </td>
 	 	    <td><font color="red"><@msg.message key="grade.absenceNotInput"/></font></td>
 	 	</tr>
 	 	<tr>
 	 	    <td><@msg.message key="grade.inputIndexTip"/>:</td>
 	 	    <td style="color:red">${enrolTimeTip[minEnrol-1]}<#--<#list gradeTypes as gradeType><@i18nName gradeType/> ${enrolTimeTip[enrolTimeMap[gradeType.id?string]-1]},</#list>--></td>
 	 	    <td id="timeElapse"></td>
 	 	</tr>
 	 </table>
     <table width="90%" class="listTable" align="center" onkeypress="gradeTable.onReturn.focus(event)";>
	   <tr align="center">
	     <td align="center" width="4%"><@msg.message key="attr.index"/></td>
	     <td align="center" width="8%"><@bean.message key="attr.stdNo"/></td>
	     <td width="13%"><@bean.message key="attr.personName"/></td>
	     <#list gradeTypes as gradeType>
	     <td><@i18nName gradeType/></td>
	     </#list>
	     <td><@bean.message key="grade.GA"/></td>
	     <td align="center" width="4%"><@msg.message key="attr.index"/></td>     
	     <td align="center" width="8%"><@bean.message key="attr.stdNo"/></td>
	     <td width="13%"><@bean.message key="attr.personName"/></td>
	     <#list gradeTypes as gradeType>
	     <td><@i18nName gradeType/></td>
	     </#list>
	     <td><@bean.message key="grade.GA"/></td>  
	   </tr>	   
	   <#assign courseTakes=task.teachClass.courseTakes?sort_by(['student','code'])?if_exists>
	   <#assign pageSize=((courseTakes?size+1)/2)?int />
	   <#list courseTakes as courseTake>
	   <tr>
		   <#if courseTakes[courseTake_index]?exists>		   
		     <@displayGrades courseTake_index,courseTakes[courseTake_index]/>
		   <#else>
		      <#break>
		   </#if>
		   	   
		   <#assign j=courseTake_index+pageSize> 
		   <#if courseTakes[j]?exists>
			    <@displayGrades j,courseTakes[j]/>
		   <#else>
		     <#list 1..(4+gradeTypes?size) as i>
		       <td>&nbsp;</td>
		     </#list>
		   </#if>		   
		   <#if ((courseTake_index+1)*2>=courseTakes?size)><#break></#if>
	   </tr>
	   </#list>
     </table>
   </form>
  <table width="100%" style="font-size:15px">
    <tr>
    <td align="center" id="submitTd">
      <#--<@msg.message key="grade.inputPrintTip"/><br>-->
      说明:如果录入次数没有增加,表明仍有些成绩你没有录入.<br>
      <font color="red">成绩必须录入两次</font>,否则视为无效录入.<br>
      点击[保存]时,未录入的成绩下次再录.<br>
      点击[…提交]时,未录入的成绩视为缺考.<br>
      &nbsp;&nbsp;&nbsp; <button onclick="saveGrade(true)" title="<@msg.message key="grade.remaindInputNextTime"/>">保存</button>
      &nbsp;&nbsp;&nbsp; <button onclick="saveGrade(false)">${enrolTimeTip[minEnrol-1]}提交</button>
      <#--
      &nbsp;&nbsp;&nbsp; <button onclick="saveGrade(true)" title="<@msg.message key="grade.remaindInputNextTime"/>"><@msg.message key="grade.justSave"/></button>
      &nbsp;&nbsp;&nbsp; <button onclick="saveGrade(false)"><@msg.message key="grade.submitAll"/></button>
      -->
    </td>
   </tr>
 </table>
<script>
   var bar = new ToolBar("myBar","<@msg.message key="grade.teachClassInput"/>",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addClose("<@msg.message key="action.close"/>");
   gradeTable.setReturnIndex(document.gradeForm);
   gradeTable.changeTabIndex(true);
   //为保存成绩定时提示
   var time=20*60;//20分钟
   var timeElapse=0;
   function refreshTime(){
      ++timeElapse;
      var sec=timeElapse%60;
      var min=Math.floor(timeElapse/60);
      $('timeElapse').innerHTML="<@msg.message key="grade.autoSaveTip"/>"+((min==0)?"":min+"m ") +sec+"s";
      if(timeElapse%time==0){
         if(confirm("20分钟过去了,是否保存以免丢失数据?")){
	         var form =document.gradeForm;
	         form.action="teacherGrade.do?method=save";
	         gradeTable.enableGradeInput(true,"");
	         document.getElementById('submitTd').innerHTML="成绩提交中,请稍侯...";
             document.gradeForm.submit();
         }
      }
   }
   setInterval('refreshTime()',1000);
   
   function saveGrade(justSave){
      var form =document.gradeForm;
      form.action="teacherGrade.do?method=save";
      if(justSave){
		if(confirm("确定'保存'后,成绩将不可再编辑.第一次录入保存后,如有问题,请在第二次录入中核实再录入,第二次录入保存后,将不再提供修改机会!请您认真检查后进行保存.")){	
          gradeTable.enableGradeInput(true,"");	         
	    }else{
		  return false;
		}
      }else{
         if(confirm("<@msg.message key="grade.inputSubmitConfirm"/>")){
           if(gradeTable.hasEmptyInput()){
             gradeTable.enableGradeInput(true,"缺考");
           }
         }else{
            return false;
         }
      }
      document.getElementById('submitTd').innerHTML="成绩提交中,请稍侯...";
      form.submit();
   }
</script>
 </body>
<#include "/templates/foot.ftl"/>