<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <tr>
    <td colspan="7" align="center" height="30">
    	<div id="classStudentBarDiv" style="display:block"><table id="classStudentBar" width="90%" align="center"></table></div>
    	<div id="addStdFormBarDiv" style="display:none"><table id="addStdFormBar" width="90%" align="center"></table></div>
    </td>
   </tr>
   <tr>
    <td>
     <table width="90%" align="center" class="listTable">     
       <tr class="darkColumn">
	     <td align="center" colspan="7"><@bean.message key="info.studentClassManager.classBasicInf"/></td>
	   </tr>
       <tr>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="info.studentClassManager.className"/>：</td>
	    <td class="brightStyle">&nbsp;<@i18nName result.adminClass?if_exists/></td>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="info.studentClassManager.classId"/>：</td>
	    <td class="brightStyle">&nbsp;${result.adminClass?if_exists.code?if_exists}</td>
       </tr>
       <tr>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="entity.college"/>：</td>
	    <td class="brightStyle">&nbsp;<@i18nName result.adminClass?if_exists.department?if_exists/></td>
	    <td class="grayStyle" width="25%">&nbsp;<@msg.message key="entity.speciality"/>：</td>
	    <td class="brightStyle">&nbsp;<@i18nName result.adminClass?if_exists.speciality?if_exists/></td>	    
       </tr>
       <tr>
        <td class="grayStyle" width="25%">&nbsp;<@bean.message key="entity.studentType"/>：</td>
	    <td class="brightStyle">&nbsp;<@i18nName result.adminClass?if_exists.stdType?if_exists/></td>	    
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="adminClass.enrollYear"/>：</td>
	    <td class="brightStyle">&nbsp;${result.adminClass?if_exists.enrollYear?if_exists}</td>
       </tr>
       <tr>
        <td class="grayStyle" width="25%">&nbsp;<@bean.message key="info.studentClassManager.classDate"/>：</td>
	    <td class="brightStyle">&nbsp;${result.adminClass?if_exists.dateEstablished?if_exists}</td>
	    <td class="grayStyle" width="25%">&nbsp;班级人数：（计划/学籍有效/实际在校）</td>
	    <td class="brightStyle">&nbsp;${result.adminClass?if_exists.planStdCount?if_exists}/${result.adminClass?if_exists.stdCount?if_exists}/${result.adminClass?if_exists.actualStdCount?if_exists}</td>
	   </tr>       
       </table>
       <div id="classStudentDiv" style="display:block" >  
            
       <table width="90%" align="center" class="listTable" style="border-top-style:none">
       <form name="setClassStudentForm" action="studentClassOperation.do" method="post">
       <thead>       
       <tr class="darkColumn">
	     <td align="center" colspan="7"><@bean.message key="adminClass.stdList"/></td>
	   </tr>
       <tr align="center" class="darkColumn">
       	 <td width="5%"><input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('ids'),event);"></td>
       	 <td><@bean.message key="attr.stdNo"/></td>
	     <td><@bean.message key="attr.personName"/></td>	     
	     <td><@bean.message key="entity.college"/></td>
	     <td><@bean.message key="entity.studentType"/></td>
	     <td>学籍有效性</td>
	     <td>学籍状态</td>
	   </tr>
	   </thead>
	   <tBody id="studentTD">
	   </tBody>
	   <tFoot>
	   <tr class="darkColumn">
	   	 <td align="center" colspan="7">
	   	 	<input type="button" name="deleteStd" value="移出" class="buttonStyle" onClick="doDelete(this.form)"/>&nbsp;&nbsp;
	   	 	<input type="button" name="addStd" value="<@msg.message key="action.add"/>" class="buttonStyle" onClick="doAdd(this.form)"/>&nbsp;&nbsp;
	   	 	<input type="button" name="button" value="<@msg.message key="action.save"/>" class="buttonStyle" onClick="doSave(this.form)"/>&nbsp;&nbsp;
	   	 	<input type="button" value="<@bean.message key="system.button.reset"/>" name="reset1"  class="buttonStyle" onClick="doReset()"/>
	   	 </td>
	   </tr>
	   <input type="hidden" name="adminClass.id" value="${result.adminClass.id}"/>
	   <input type="hidden" id="studentIds" name="studentIds" value=""/>
	   <input type="hidden" name="method" value="updateStudentClass"/>
	   </tFoot>	   
     </table>  
     </form>   
     </div>
     <#include "addStdForm.ftl"/>
    </td>
   </tr>
  </table>  
 </body>
 <script>
 	var divs=new Array();
  	divs[0]="classStudent";
  	divs[1]="addStdForm";
  	function displayDiv(divId){
     	for(var i=0;i<divs.length;i++){
        	if(divs[i]==divId){
           		document.getElementById(divs[i]+"Div").style.display="block";
           		document.getElementById(divs[i]+"BarDiv").style.display="block";
           		if(divs[i]=="addStdForm"){
           			if(stdListFrame.location=="about:blank"){
           				var form=document.createElement('form');
           				form.action="adminClassManager.do?method=addClassStudentList";
           				form.method="post";
           				form.target="stdListFrame";
           				var input=document.createElement("INPUT");
           				input.type='hidden';
           				input.name='student.department.name';
           				input.value="${result.adminClass?if_exists.department?if_exists.name?if_exists}";
           				form.appendChild(input);
           				input=document.createElement("INPUT");
           				input.type='hidden';
           				input.name='student.enrollYear';
           				input.value="${result.adminClass?if_exists.enrollYear?if_exists}";
           				form.appendChild(input);
           				input=document.createElement("INPUT");
           				input.type='hidden';
           				input.name='student.firstMajor.name';
           				input.value="${result.adminClass?if_exists.speciality?if_exists.name?if_exists}";
           				form.appendChild(input);
           				document.body.appendChild(form);
           				form.submit();
					}
           		}
        	}else{
           		document.getElementById(divs[i]+"Div").style.display="none";
           		document.getElementById(divs[i]+"BarDiv").style.display="none";
        	}
     	}	 	
	}
 	function createTitle(){
 		var tr=document.createElement('tr');
 		tr.className="darkColumn";
 		var td=document.createElement('td'); 		
 		td.innerHTML="<@bean.message key="adminClass.stdList"/>";
 		td.align="center";
 		td.colSpan="7";
 		tr.appendChild(td);
 		document.getElementById("studentTD").tBodies[0].appendChild(tr);
 		var tr=document.createElement('tr');
 		tr.className="darkColumn";
 		var td=document.createElement('td'); 		
 		td.innerHTML="<input type='checkbox' onClick=\"toggleCheckBox(document.getElementsByName('ids'),event);\"/>";
 		td.className="select";
 		td.width="10%";
 		td.align="center";
 		tr.appendChild(td);
 		var td=document.createElement('td'); 		
 		td.innerHTML="<@bean.message key="attr.stdNo"/>";
 		td.width="20%";
 		tr.appendChild(td);
 		var td=document.createElement('td'); 		
 		td.innerHTML="<@bean.message key="attr.personName"/>";
 		td.width="25%";
 		tr.appendChild(td);
 		var td=document.createElement('td'); 		
 		td.innerHTML="<@bean.message key="entity.college"/>";
 		td.width="25%";
 		tr.appendChild(td);
 		var td=document.createElement('td'); 		
 		td.innerHTML="<@bean.message key="entity.studentType"/>";
 		td.width="25%";
 		tr.appendChild(td);
 		document.getElementById("studentTD").tBodies[0].appendChild(tr);
 	}
 	var stdArray = new Array();
 	<#list (result.adminClass.students?sort_by("code"))?if_exists as student>
    stdArray[${student_index}]={'student.id':'${student.id?if_exists}','student.code':'${student.code?if_exists}','student.name':'<@i18nName student/>','student.department.name':'<@i18nName student.department/>','student.type.name':'<@i18nName student.type/>','student.active':'${student.active?string("有效","无效")}','student.state.name':'<@i18nName student.state />'};
    </#list>
    var index=0;
    function addStudent(stdMap){
    	var tr=document.createElement('tr');
    	tr.id=stdMap["student.id"];
 		if(index%2==1){tr.className="grayStyle";}
 		if(index%2==0){tr.className="brightStyle";}
 		tr.onmouseover=function(event){swapOverTR(this,this.className);};
 		tr.onmouseout=function(event){swapOutTR(this)};
 		var td=document.createElement('td');
 		td.innerHTML="<input type=checkBox name=ids value="+stdMap['student.id']+" />";
 		td.align="center";
 		tr.appendChild(td);
 		var td=document.createElement('td');
 		td.innerHTML="&nbsp;"+stdMap['student.code'];
 		tr.appendChild(td);
 		var td=document.createElement('td');
 		td.innerHTML="&nbsp;"+stdMap['student.name'];
 		tr.appendChild(td);
 		var td=document.createElement('td');
 		td.innerHTML="&nbsp;"+stdMap['student.department.name'];
 		tr.appendChild(td);
 		var td=document.createElement('td');
 		td.innerHTML="&nbsp;"+stdMap['student.type.name'];
 		tr.appendChild(td);
 		var td=document.createElement('td');
 		td.innerHTML="&nbsp;"+stdMap['student.active'];
 		tr.appendChild(td);
 		var td=document.createElement('td');
 		td.innerHTML="&nbsp;"+stdMap['student.state.name'];
 		tr.appendChild(td);
 		document.getElementById("studentTD").appendChild(tr);
 		index++;
    }
    for(var i=0;i<stdArray.length;i++){
    	addStudent(stdArray[i]);
    }
    function doReset(){
    	DWRUtil.removeAllRows("studentTD");
    	index=0;
    	for(var i=0;i<stdArray.length;i++){
    		addStudent(stdArray[i]);
    	}
    }
    function createEnd(){
    	var tr=document.createElement('tr');
 		tr.className="darkColumn";
 		var td=document.createElement('td'); 		
 		td.innerHTML="<input type=button name=button value=<@bean.message key="system.button.submit"/> class=buttonStyle onClick='doAction(this.form)'/>&nbsp;&nbsp;<input type=button value=<@bean.message key="system.button.reset"/> name=reset1  onClick='doReset()' class=buttonStyle />";
 		td.align="center";
 		td.colSpan="7";
 		tr.appendChild(td);
 		var input=document.createElement("<input type='hidden' name='adminClass.id' value='${result.adminClass.id}' />");
 		tr.appendChild(input);
 		document.getElementById("studentTD").tBodies[0].appendChild(tr);
    }
    function doSave(){
    	var stdIds=",";
    	var ids=document.getElementsByName("ids");
    	if(ids&&ids[0]){
    		for(var i=0;i<ids.length;i++){
    			stdIds+=ids[i].value+",";
    		}
    	}
    	document.getElementById("studentIds").value=stdIds;
    	document.all.setClassStudentForm.submit();
    }
 	function doAction(){
 		ids=getIds();
 		if(ids == ""){
 			alert("<@bean.message key="filed.choose"/>");
 		}else{
	 		if(confirm("<@bean.message key="info.studentClassManager.cancelStudentConfirm"/>")){
	 			deleteStudent();
	 		}
 		}
 	}
 	function doDelete(){
 		ids=getIds();
 		if(ids == ""){
 			alert("<@bean.message key="filed.choose"/>");
 		}else{
	 		if(confirm("<@bean.message key="info.studentClassManager.cancelStudentConfirm"/>")){
	 			deleteStudent();
	 		}
 		}
 	}
 	function deleteStudent(){
 		var rows=document.getElementById("studentTD").rows;
 		for(var i=0;i<rows.length;i++){
		   if(rows[i].firstChild.firstChild.checked){
		       document.getElementById("studentTD").removeChild(rows[i--]);
		   }
		}
 	}
 	function doAdd(){
 		displayDiv("addStdForm");
 	}
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("ids")));
    }
    var bar=new ToolBar('classStudentBar','班级学生维护',null,true,true);
	bar.addItem("移出",doDelete);
	bar.addItem("<@msg.message key="action.add"/>",doAdd);
	bar.addItem("<@msg.message key="action.save"/>",doSave);
	bar.addItem("重置",doReset);
 </script>
<#include "/templates/foot.ftl"/>