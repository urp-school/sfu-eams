<html>
 <head>
  <meta http-equiv="content-type" content="text/html; charset=utf-8">
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <title>教学管理信息系统</title>
  <link href="${static_base}/css/default.css" rel="stylesheet" type="text/css">
  <script language="JavaScript" type="text/JavaScript" src="scripts/common/Common.js"></script>
 </head>
<body LEFTMARGIN="0" TOPMARGIN="0" ondblclick="changeFlag();" >
<div id="processBarDiv">
<table id="processBarTable" cellspacing="0" cellspadding="0"  style="width:100%;font-size:12px;" border="0" >
   <tr>
     <td colspan="4" height="20px">
     <table width="100%">
     <tr>
     <td style="width:10px"><img src="${static_base}/images/action/info.gif"  align="top"/></td>
     <td id="summary" style="font-size:12px"></td>
     <td align="right"><input type='button' id='buttonShowAll' value='查看全部未通过课程' class='buttonStyle'/></td>
     </tr>
     </table>
    </td>
   </tr>
   <tr>
   <td style="width:100%;border-style:hidden;" colspan="4" >
   	<table style="width:100%;height:15px;">
     <tr>
	   <td id="completedTd"  style="height:15px;width:0%;font-size:12px" align="center"  bgColor="#00cc00"></td>
	   <td id="unCompletedTd" style="width:100%;height:15px;"  bgColor="#EBEBEB"></td>
	   </tr>
     </table>
     </td>
   </tr>
   <!--
   <tr>
	   <td style="width:100%;height:400px;border-style:hidden;" colspan="4" >
	   <select id="processContent" name="" multiple style="width:100%;height:100%;"></select>
	   </td>
   </tr>
   -->
   <tr>
   	<td style="width:100%;border-style:hidden;" colspan="4" >
   	<table id="processTable" cellspacing="0" cellspadding="0"  style="width:100%;" border="0" class="listTable" >
   	<tr align="center" class="darkColumn">
   	<td width="10%">序号</td>
   	<td width="10%">学号</td>
   	<td width="10%">姓名</td>
   	<td width="10%">结果</td>
   	<td width="10%">查看</td>
   	<td width="10%">备注</td>
   	</tr>
   	</table>
   	</td>
   </tr>
</table>
</div>
<script>
   var complete=0;
   var count=1;
   var showCount=0;
   var completedTd= document.getElementById("completedTd");
   var unCompletedTd= document.getElementById("unCompletedTd");
   var contentSelect=  document.getElementById("processContent");
   var processBarTable=document.getElementById("processTable");
   var allUnpassStdIds="";
   var noTeachPlanStdIds="";
   var majorTypeId;
   var allAuditStandardId;
   var allTerms;
   var noTeachPlanErrorFlag=false;
   function setSummary(msg){
       document.getElementById("summary").innerHTML=msg;
   }
   function showAllUnpass(){
   		if(complete<count){alert("审核尚未结束，请稍候！");return;}else{
   		
   		if(noTeachPlanErrorFlag){
	   		alert("培养计划缺失的学生将不会被显示！");
	   		var idArray = noTeachPlanStdIds.split(',');
	   		for(var i=0;i<idArray.length;i++){
	   			if(idArray[i]!=""){
	   				allUnpassStdIds=allUnpassStdIds.replace(','+idArray[i]+',','');
	   			}
	   		}
	   	}
	   	
	   	if(allUnpassStdIds==""){alert("没有未通过的学生！");return;}
		var form=document.createElement('form');
		form.name="showAllUnpassForm";
		form.method="post";
		form.action="studentAuditOperation.do?method=batchDetail&majorTypeId="+majorTypeId+"&auditStandardId="+allAuditStandardId+"&auditTerm="+allTerms+"&show=unpass"+"&showBackward=false&showStudentAttach=false";
		var hidden=document.createElement("<INPUT TYPE='hidden' NAME='stdId'>");
		hidden.value=allUnpassStdIds;
		form.appendChild(hidden);
		form.target="_blank";
		document.body.appendChild(form);
		form.submit();}
   }
   document.getElementById("buttonShowAll").onclick=showAllUnpass;
   colors =['black','blue','red'];
   var flag = true;
   function changeFlag(){
		flag = !flag;
   }
   function addStd(code,name){
     	showCount++;
		var tr = document.createElement('tr');
		if(showCount%2==0){tr.className="brightStyle";}else{tr.className="grayStyle";}
		tr.onmouseover=function(){swapOverTR(this,this.className);};
		tr.onmouseout=function(){swapOutTR(this);};
		var td = document.createElement('td');
		td.innerHTML=showCount;
		td.align="middle";
		tr.appendChild(td);
	   	var td = document.createElement('td');
	   	td.innerHTML ='&nbsp;'+code;
	   	tr.appendChild(td);
	   	var td = document.createElement('td');
	   	td.innerHTML ='&nbsp;'+name;
	   	tr.appendChild(td);
	   	var td = document.createElement('td');
	   	tr.appendChild(td);
	   	var td = document.createElement('td');	   	
	  	tr.appendChild(td);   
	  	var td = document.createElement('td');
	  	tr.appendChild(td);
	  	tr.id=code;
	   	processBarTable.tBodies[0].appendChild(tr); 
	   	var div = document.createElement('div');
	   	div.style.display="none";
	   	div.style.position="absolute";
	   	div.style.top="230px";
	   	div.style.left="300px";
	   	div.id='div'+code;
	   	var table0 = document.createElement('table');
	   	var oTBody0 = document.createElement("TBODY");
	   	table0.appendChild(oTBody0);
	   	table0.style.fontSize ="12px";
	   	table0.className="listTable";
	   	var tr = document.createElement('tr');
	   	var td = document.createElement('td');
	   	td.align="center";
	   	td.className="darkColumn";
	   	td.innerHTML="sgsdgsdfgsdfgsdfgsgsdfgdf";
	   	tr.appendChild(td);
        oTBody0.appendChild(tr);
	   	
	   	div.appendChild(table0);
	   	document.body.appendChild(div);
      	      
   }
   function addRemark(code,remark){
   		document.getElementById(code).cells[5].innerHTML+='&nbsp;'+remark;
   }
   function addNoTeachPlanError(code,remark,stdId){
   		document.getElementById(code).cells[5].innerHTML+='&nbsp;'+remark;
   		noTeachPlanErrorFlag=true;
   		noTeachPlanStdIds+=','+stdId+',';
   }
   function addResult(code,flag,id,specialityTypeId,auditStandardId,terms){
   		majorTypeId=specialityTypeId;
   		allAuditStandardId=auditStandardId;
   		allTerms=terms;
   		increaceProcess(1);
   		if(!flag){
   			allUnpassStdIds+=","+id+",";
   			document.getElementById(code).cells[3].innerHTML="&nbsp;<FONT COLOR=red>N</FONT>";
   			creResInp(code,id,majorTypeId,auditStandardId,terms);
   			var nbsp=document.createElement("<span/>");
   			nbsp.innerHTML="&nbsp;";
   			document.getElementById(code).cells[4].appendChild(nbsp);
   			var input2=document.createElement("<input type='button' name='input2' value='未过课程' class='buttonStyle'/>");
   			input2.onclick=function(){if(complete<count){alert("审核尚未结束，请稍候！");}else{window.open('studentAuditOperation.do?method=detail&majorTypeId='+majorTypeId+'&stdId='+id+'&auditStandardId='+auditStandardId+'&auditTerm='+terms+'&show=unpass'+'&showBackward=false');}/*alert(document.getElementById('div'+code).innerHTML);showDiv('div'+code);*/};
   			document.getElementById(code).cells[4].appendChild(input2);
   		}else{
   			document.getElementById(code).cells[3].innerHTML='&nbsp;Y';
   			creResInp(code,id,majorTypeId,auditStandardId,terms);
   		}
   }
   function creResInp(code,id,majorTypeId,auditStandardId,terms){
   		var input=document.createElement("<input type='button' name='input'  value='完成情况' class='buttonStyle'/>");
   		input.onclick=function(){if(complete<count){alert("审核尚未结束，请稍候！");}else{window.open('studentAuditOperation.do?method=detail&majorTypeId='+majorTypeId+'&stdId='+id+'&auditStandardId='+auditStandardId+'&auditTerm='+terms+'&showBackward=false');/*location.href='studentAuditOperation.do?method=detail&majorTypeId=1&stdId='+id;*/}};
   		document.getElementById(code).cells[4].innerHTML='&nbsp;';
   		document.getElementById(code).cells[4].appendChild(input);
   }
   function addAuditRes(code,resMap){
   		for(var key in resMap){alert(key+":"+resMap[key]);}
   }
   function showDiv(divId){
   	  processBarDiv.style.display="none";
   	  if(document.getElementById(divId).style.display=="block"){
   	  	 document.getElementById(divId).style.display="none";
   	  }else{
   	     document.getElementById(divId).style.display="block";
   	  }
   }
   function increaceProcess(increaceStep){
      if(increaceStep==null)
        increaceStep=1;
      else if(increaceStep<=0) return;
      complete+=increaceStep;
      var percent =Math.floor((complete/count)*100);
      if(percent>100) percent=100;
      completedTd.innerHTML=percent+"%"+"("+complete+"/"+count+")";
      completedTd.style.width=percent+"%";
      unCompletedTd.style.width=(100-percent)+"%";
   }
   function addProcessMsg(){}
</script>