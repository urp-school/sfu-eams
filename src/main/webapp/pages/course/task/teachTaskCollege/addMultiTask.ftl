<#include "/templates/head.ftl"/>
<script src='dwr/interface/departmentDAO.js'></script>
<script src='dwr/interface/specialityDAO.js'></script>
<script src='dwr/interface/specialityAspectDAO.js'></script>
<script src='dwr/interface/adminClassService.js'></script>
<script src='dwr/interface/teacherDAO.js'></script>
<script src='scripts/stdTypeDepart3Select.js'></script>

<script src='scripts/departTeacher2Select.js'></script>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body>
<table id="multiTaskBar"></table>
<script> 
   var bar = new ToolBar("multiTaskBar","添加多个教学任务",null,true,true);
   bar.addItem("<@msg.message key="action.save"/>","if(confirm('确定保存'))save(document.teachTaskForm);",'save.gif');
   bar.addItem("添加教师","addTeacher()");
   bar.addBack("<@msg.message key="action.back"/>");
</script>
     <table width="100%" border="0" align="center" class="listTable" id="taskInfoTable">
        <form name="teachTaskForm" action="?method=save&hold=true" method="post" onsubmit="return false;">
        <input type="hidden" name="method" value="save">
        <input type="hidden" name="params" value="${RequestParameters['params']?if_exists}">
	   <tr class="darkColumn">
	     <td align="center" colspan="4"><@bean.message key="entity.teachTask" /> <@bean.message key="common.baseInfo"/></td>
	   </tr>
	   <tr>
	   
	     <input type="hidden" value="${task.id?if_exists}" name="task.id"/>
	     <input type="hidden" value="${task.course.id}" name="task.course.id"/>
	     <input type="hidden" value="${task.course.code}" name="task.course.code"/>
	     <input type="hidden" value="<@i18nName task.course/>" name="task.course.name" />
	     <td class="grayStyle" width="20%" id="f_name">&nbsp;<@bean.message key="attr.courseName"/>：</td>
	     <td class="brightStyle" ><@i18nName task.course/></td>
 	     <td class="grayStyle" width="20%" id="f_name">&nbsp;<@bean.message key="attr.yearAndTerm"/>：</td>
 	     <td class="brightStyle">${task.calendar.year}${task.calendar.term}</td>
 	     <input type="hidden" value="${task.calendar.id}" name="task.calendar.id"/>
	   </tr>
       <tr>
	   	 <td class="grayStyle"  id="f_name">&nbsp;<@bean.message key="entity.courseType" />：</td>
	     <td class="brightStyle">
	        <select name="task.courseType.id" style="width:155px">
	        <#list courseTypeList as courseType>
   	           <option value="${courseType.id}" <#if task.courseType.id?exists && courseType.id == task.courseType.id> selected </#if>><@i18nName courseType/></option>
	        </#list>
	        </select>
	     </td>
         <td class="grayStyle" id="f_teachDepart">&nbsp<@bean.message key="attr.teachDepart" /><font color="red">*</font>：</td>
	     <td class="brightStyle">
   	       <select name="task.arrangeInfo.teachDepart.id" style="width:155px">
   	         <#list teachDepartList as oneTeachDepart>
   	         <option value="${oneTeachDepart.id?if_exists}" <#if task.arrangeInfo.teachDepart.id?exists&&task.arrangeInfo.teachDepart.id==oneTeachDepart.id>selected</#if>><@i18nName oneTeachDepart/></option>
   	         </#list>
	       </select>
	     </td>
       </tr>
       <tr>
	   	 <td class="grayStyle" id="f_name">&nbsp;<@bean.message key="attr.roomConfigOfTask"/>：</td>
	     <td class="brightStyle">
	        <select name="task.requirement.roomConfigType.id" style="width:155px" >         
	        <#list configTypeList as config>
	           <option value="${config.id}" <#if task.requirement.roomConfigType?if_exists.id?if_exists?string == config.id?string> selected </#if>><@i18nName config/></option>
	        </#list>
	        </select>
	     </td>
	     <td class="grayStyle"  id="f_name">&nbsp;<@bean.message key="attr.bilingualGuaPai"/>：</td>
	     <td class="brightStyle">
	       <@htm.i18nSelect datas=teachLangTypes  name="task.requirement.teachLangType.id" selected=(task.requirement.teachLangType.id)?default(1)?string  style="width:80px"/>
	       <input type="hidden" name="task.requirement.isGuaPai" value="<#if task.requirement.isGuaPai?if_exists==true>1<#else>0</#if>"/>
	       <input name="isGuaPai" type="checkbox" onchange="changeCheckBoxValue(this.form,'task.requirement.isGuaPai');"<#if task.requirement.isGuaPai?if_exists==true> checked</#if>/><@bean.message key="attr.isGuaPai"/>
	     </td>
	   </tr>
       <tr>
	   	 <td class="grayStyle"  id="f_weeks">&nbsp;<@bean.message key="attr.weeks"/><font color="red">*</font>：</td>
	     <td class="brightStyle"><input name="task.arrangeInfo.weeks" value="<#if task.arrangeInfo.weeks?exists>${task.arrangeInfo.weeks}<#else>18</#if>" /></td>
         <td class="grayStyle" id="f_courseUnits">&nbsp;<@bean.message key="attr.unit"/><font color="red">*</font>：</td>
	     <td class="brightStyle">
	       <input name="task.arrangeInfo.courseUnits" value="${task.arrangeInfo.courseUnits?if_exists}"/>
	     </td>
       </tr>
       <tr>
	   	 <td class="grayStyle" id="f_name">&nbsp;<@bean.message key="attr.teachWeek"/><font color="red">*</font>：</td>
	   	 <#assign continuedWeek><@bean.message key="attr.continuedWeek"/></#assign>
	   	 <#assign oddWeek><@bean.message key="attr.oddWeek"/></#assign>
	   	 <#assign evenWeek><@bean.message key="attr.evenWeek"/></#assign>
	   	 <#assign randomWeek><@bean.message key="attr.randomWeek"/></#assign>
	   	 <#assign weekCycle=["${continuedWeek}","${oddWeek}","${evenWeek}","${randomWeek}"]/>
	     <td class="brightStyle">
	       <select name="task.arrangeInfo.weekCycle" style="width:100px">
	       <#list weekCycle as sycle>
	         <option value="${sycle_index + 1}"<#if task.arrangeInfo.weekCycle?exists && (sycle_index +1) ==task.arrangeInfo.weekCycle> selected</#if>>${sycle}</option>
	       </#list>
	       </select>
	     </td>
         <td id="f_weekStart" class="grayStyle">&nbsp;<@bean.message key="attr.startWeek"/><font color="red">*</font>:</td>
         <td class="brightStyle"><input name="task.arrangeInfo.weekStart" value="${task.arrangeInfo.weekStart?if_exists}"/></td>
       </tr>
	   <tr>
	   <td colspan="4">
      <table width="100%" border="0"  cellpadding="0" cellspacing="1" align="center" id="tableId_0">
       <tr class="darkColumn">
       <td class="infoTitle" style="height:22px;" colspan="7">
           <img src="${static_base}/images/action/info.gif"><B>教师</B>
              序号：1&nbsp;<@msg.message key="entity.studentType"/>:
	 	     <select id="stdTypeOfSpecialityId_0" name="teacherId_0stdTypeOfSpecialityId_0" style="width:100px">
	 	         <option value="${task.teachClass.stdType.id}"><@i18nName task.teachClass.stdType/></option>
	 	     </select>
   	       <select id="teachDepartmentId_0" style="width:120px">
   	         <option value="${teacherDepart?if_exists.id?if_exists}"></option>
	       </select>
   	       <select id="teacherId_0" name="teacherId_0" style="width:90px">
   	         <option value="${teacher?if_exists.id?if_exists}"></option>
	       </select>
	        任务数：<input type="text" maxlength="5" name="teacherId_0.adminNum_display" value="1" style="width:40px"/>
	       <input type="hidden" name="teacherId_0.adminNum" value="1" style="width:40px" />
   	       <input type="button" class="buttonStyle" value="调整" onClick="addAdminClass('tableId_0',this.form['teacherId_0.adminNum_display'].value);"/>
   	       <input type="button" class="buttonStyle" value="<@msg.message key="action.delete"/>" onClick="removeTeacher('tableId_0');"/>
 	     </td>
       </tr>
       <tr class="grayStyle" align="center">
 	     <td width="9%" class="infoTitle"><@bean.message key="attr.enrollTurn" /></td>
 	     <td width="14%" class="infoTitle"id="f_depart"><@bean.message key="entity.college" /><font color="red">*</font></td>
         <td width="16%" class="infoTitle"id="f_speciality"><@bean.message key="entity.speciality"/></td>
	   	 <td width="18%" class="infoTitle"id="f_aspect">方向</td>
	   	 <td width="16%" class="infoTitle"id="f_adminClassName"><@bean.message key="entity.adminClass"/></td>
	   	 <td width="7%" class="infoTitle"id="f_planStdCount" >人数<font color="red">*</font></td>
         <td width="16%" class="infoTitle"><@bean.message key="entity.teachClass"/><font color="red">*</font></td>
       </tr>
       <tr class="grayStyle" align="center">
         <td><input  id="enrollTurnId_0" name="teacherId_0teachClassId_0.enrollTurn" maxlength="6" style="width:100%" value=""></td>
         <td><select id="departmentId_0" name="teacherId_0teachClassId_0.depart.id" style="width:100%"><option value="1"></option></select></td>
	   	 <td><select id="specialityId_0" name="teacherId_0teachClassId_0.speciality.id" onBlur="setAdminClassList('teacherId_0','adminClassId_0')" style="width:100%"><option value=""></option></select></td>
         <td><select id="specialityAspectId_0" name="teacherId_0teachClassId_0.aspect.id" onchange="setAdminClassList('teacherId_0','adminClassId_0')" style="width:100%"><option value=""></option></select></td>
         <td><select id="adminClassId_0" name="teacherId_0adminClassId_0" style="width:100%"><option value=""></option></select></td>
         <td><input id="planStdCountId_0" name="teacherId_0teachClassId_0.planStdCount" value="20" style="width:100%"/></td>
         <td><input id="teachClassNameId_0"name="teacherId_0teachClassId_0.name" maxLength="50" value="${task.teachClass.name}" style="width:100%"/></td>
        </tr>
     </table>
	   </td>
	   </tr>
	   <tr class="darkColumn" id="submitTR">
	     <td colspan="6" align="center">
	       <input type="button" value="<@bean.message key="action.submit"/>" name="saveButton" onClick="save(this.form)" class="buttonStyle" />&nbsp;
	       <input type="reset" name="resetButton" value="<@bean.message key="action.reset"/>" class="buttonStyle"/>
	     </td>
	   </tr>
     </table>
	 </form>
  <script language="javascript">
    var stdTypeArray = new Array();
    <#list stdTypeList as stdType>
    stdTypeArray[${stdType_index}]={'id':'${stdType.id?if_exists}','name':'<@i18nName stdType/>'};
    </#list>
    var departArray = new Array();
    var teachDepartArray = new Array();
    <#list teachDepartList as depart>
    departArray[${depart_index}]={'id':'${depart.id?if_exists}','name':'<@i18nName depart/>'};
    teachDepartArray[teachDepartArray.length]={'id':'${depart.id?if_exists}','name':'<@i18nName depart/>'};
    </#list>
    
    var t1=new DepartTeacher2Select('teachDepartmentId_0','teacherId_0',false,false);
    t1.initTeachDepartSelect(teachDepartArray);
    t1.initTeacherSelect();
        
    var dd = new StdTypeDepart3Select("stdTypeOfSpecialityId_0","departmentId_0","specialityId_0","specialityAspectId_0",false,false,true,true);
    dd.init(stdTypeArray,departArray);
    
    DWREngine.setAsync(false);    
    // 保存批量添加的教学任务
    function save(form){    
     form.action="?method=saveMultiTask&teacherNum="+teacherNum+"&adminClassNum="+adminClassNum;
     var errorInfo="";
     if(!(/^\d+$/.test(form['task.arrangeInfo.weeks'].value)))
          errorInfo+="周数格式不对\n";
     if(!(/^\d+$/.test(form['task.arrangeInfo.weekStart'].value)))
	     errorInfo+="起始周格式不对\n";
     if(!(/^\d+$/.test(form['task.arrangeInfo.courseUnits'].value)))
	     errorInfo+="小节数格式不对\n";

     for(var i=0;i<adminClassNum;i++){
          var enrollTurnInput =document.getElementById('enrollTurnId_'+i);
          if(null==enrollTurnInput) continue;
          if(enrollTurnInput.value){
            if(!(/^\d{4}-\d$/.test(enrollTurnInput.value)))
             errorInfo+=enrollTurnInput.value+"格式不正确,应为yyyy-p(年份-月份)\n";
          }
          var teachClassNameInput =document.getElementById('teachClassNameId_'+i);
          if(teachClassNameInput.value=="")
             errorInfo+="教学班没有名字\n";
          var planStdCountInput = document.getElementById('planStdCountId_'+i);          
          if(planStdCountInput.value&&!(/^\d+$/.test(planStdCountInput.value)))
             errorInfo+=planStdCountInput.value+"不是有效数字，应为正整数\n";
      }
      if(errorInfo!="") {
         alert(errorInfo);
         return;
      }
      form.submit();
   }
   
   var generated = new Array();
   var exampleTableContent=tableId_0.outerHTML;
   var teacherNum=1;
   var adminClassNum=1;
   
   function addAdminClass(tableId,num){
     var table= document.getElementById(tableId);
     var index = tableId.substr(tableId.indexOf("_")+1);
     var numInput = document.teachTaskForm["teacherId_"+index+".adminNum_display"]
     var hiddenInput=document.teachTaskForm["teacherId_"+index+".adminNum"]

     while(num!=null){
          if(!/^\d*$/.test(num)){
            num = prompt("输入要调整的值为正整数","");
          }
          else break;
     }
     
     if(null==num) {
        numInput.value=hiddenInput.value;
        return;
     }else{
        hiddenInput.value=num;
        numInput.value=num;
     }
     if(table.rows.length-2==num)return;
     // 新增要添加的行
     
     var tobeCopyedEnrollTurn=table.rows[2].cells[0].firstChild.id;
     var toBeCopyedIndex=tobeCopyedEnrollTurn.substr(tobeCopyedEnrollTurn.indexOf("_")+1);
     var idReg= new RegExp("Id_"+toBeCopyedIndex,"gi");
     
     for(var i=table.rows.length-2; i<num;i++){
	     var tr = document.createElement('tr');
	     for(var j= 0;j<7; j++){
		        var td = document.createElement('td');	        
	            var innerHTML =table.rows[2].cells[j].innerHTML;
	            
	            innerHTML=innerHTML.replace(idReg,"Id_"+adminClassNum);
	            var teacherReg= new RegExp("teacherId_"+adminClassNum,"gi");	            
	            innerHTML=innerHTML.replace(teacherReg,"teacherId_"+index);
	            
	            td.innerHTML=innerHTML;
		        tr.appendChild(td);
		  }
		  tr.className="grayStyle";
	      tr.align="center";
	      table.tBodies[0].appendChild(tr);
	      //alert(tr.innerHTML);
	      //alert("index,adminClassNum:"+index+":"+adminClassNum);
		  init3Select(index,adminClassNum);
		  adminClassNum++;
     }
     // 删除多余的行
     var rows =table.rows;
     for(var i=table.rows.length; i-2>num;i--){
	     table.tBodies[0].removeChild(rows[i-1]);
     }
     //调节大小
	 var targWin = parent.document.all['teachTaskListFrame'];
 	  if(targWin != null) {
 		var HeightValue = document.body.scrollHeight;
		targWin.style.pixelHeight = HeightValue;
     }
   }
   // 用来初始化院系专业和专业方向选择框
   function init3Select(teacherIndex,adminClassIndex){
      var newSelect = new StdTypeDepart3Select("stdTypeOfSpecialityId_"+teacherIndex,"departmentId_"+adminClassIndex,"specialityId_"+adminClassIndex,"specialityAspectId_"+adminClassIndex,false,false,true,true);
      newSelect.init(stdTypeArray,departArray);
      generated[generated.length]=newSelect;
   }
   // 用来初始化院系和教师选择框
   function init2Select(teacherIndex){
	    var t=new DepartTeacher2Select('teachDepartmentId_'+teacherIndex,'teacherId_'+teacherIndex,false,false);
	    t.initTeachDepartSelect(teachDepartArray);
	    t.initTeacherSelect();
   }
   // 添加教师
   function addTeacher(){
       var count = prompt("输入要添加的教师数量(正整数)","1");
       while(count!=null){
          if(!/^[1-9]$/.test(count)){
            alert("数字在"+"(2-9)");
            count = prompt("输入要添加的教师数量(正整数)","1");            
          }
          else break;
       }
       var taskInfoTable= document.getElementById("taskInfoTable");
       for(var i=0;i<count;i++){
		   var newTR= taskInfoTable.insertRow(taskInfoTable.rows.length-1);
		   var td = document.createElement('td');
		   td.colSpan="4";
		   newTR.appendChild(td);
		   var newTableContent=exampleTableContent.replace(/序号：1/gi,"序号："+(teacherNum+1));
		   newTableContent=newTableContent.replace(/stdTypeOfSpecialityId_0/gi,"stdTypeOfSpecialityId_"+teacherNum);
		   newTableContent=newTableContent.replace(/tableId_0/gi,"tableId_"+teacherNum);
		   newTableContent=newTableContent.replace(/teachDepartmentId_0/gi,"teachDepartmentId_"+teacherNum);
		   newTableContent=newTableContent.replace(/teacherId_0/gi,"teacherId_"+teacherNum);
   	       newTableContent=newTableContent.replace(/Id_0/gi,"Id_"+adminClassNum);
           td.innerHTML=newTableContent;
           //alert(teacherNum+":"+adminClassNum);
           //alert(newTableContent);
           
           init3Select(teacherNum,adminClassNum);
           init2Select(teacherNum);
           teacherNum++;
           adminClassNum++;
       }
     //调节大小
	 var targWin = parent.document.all['teachTaskListFrame'];
 	  if(targWin != null) {
 		var HeightValue = document.body.scrollHeight;
		targWin.style.pixelHeight = HeightValue;
     }
   }
   
   function removeTeacher(tableId){
       var taskInfoTable= document.getElementById("taskInfoTable");
       if(taskInfoTable.rows.length<=8){
         alert("不能删除最后一位教师!");
         return;
       }else{
          var toBeDeletedTable= document.getElementById(tableId);
          taskInfoTable.tBodies[0].removeChild(toBeDeletedTable.parentNode.parentNode);
       }
   }
   var adminClassId=null;
   
   function setAdminClassList(tableId,classId){
       var adminClassIndex=classId.substr(classId.indexOf("_")+1);
       var tableIndex = tableId.substr(tableId.indexOf("_")+1);
       adminClassId=classId;
       //alert('stdTypeOfSpecialityId_'+tableIndex)
       var stdType=document.getElementById('stdTypeOfSpecialityId_'+tableIndex).value;
       var enrollTurn=document.getElementById('enrollTurnId_'+adminClassIndex).value;
       var depart=document.getElementById('departmentId_'+adminClassIndex).value;
       var speciality=document.getElementById('specialityId_'+adminClassIndex).value;
       var aspect=document.getElementById('specialityAspectId_'+adminClassIndex).value;
       //DWREngine.setAsync(false);
       adminClassService.getAdminClassNames(initAdminClassSelect,enrollTurn,stdType,depart,speciality,aspect);
       //DWREngine.setAsync(true);
   }
   function initAdminClassSelect(data){
       DWRUtil.removeAllOptions(adminClassId);
       DWRUtil.addOptions(adminClassId,[{'id':'','name':'...'}],'id','name');
       for(var i=0;i<data.length;i++){
           DWRUtil.addOptions(adminClassId,[{'id':data[i][0],'name':data[i][1]}],'id','name');
       }
   }
   function changeCheckBoxValue(form,checkboxName){
      if(event.srcElement.checked) {
      	form[checkboxName].value="1";
      } else {
      	form[checkboxName].value="0";
      }
   }
</script>
 </body>
<#include "/templates/foot.ftl"/>