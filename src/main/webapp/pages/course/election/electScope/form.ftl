<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/prompt.js"></script>  
<style  type="text/css">
<!--
.trans_msg
    {
    filter:alpha(opacity=100,enabled=1) revealTrans(duration=.2,transition=1) blendtrans(duration=.2);
    }
-->
</style>
<body  LEFTMARGIN="0" TOPMARGIN="0">
<div id="toolTipLayer" style="position:absolute; visibility: hidden"></div>
<script>initToolTips()</script>
<table id="electScopeBar"></table> 
<table width="100%" border="0" class="listTable">
<form name="scopeForm" method="post" action ="electScope.do?method=save&hold=true" onsubmit="return false;">
    <input name="params" value="${RequestParameters['params']}" type="hidden"/>
	<tr >
	    <td>[<@bean.message key="attr.taskNo"/>：${task.seqNo} <@bean.message key="attr.courseName"/>：<@i18nName task.course/>]</td>
		<td id="f_minStdCount"><@bean.message key="attr.maxCount"/><font color="red">*</font></td>
		<td><input type="text" value="${task.electInfo.maxStdCount}" name="task.electInfo.maxStdCount" style="width:30px" maxlength="5"/></td>
		<td id="f_maxStdCount"><@bean.message key="attr.minCount"/><font color="red">*</font></td>
		<td><input type="text" value="${task.electInfo.minStdCount}" name="task.electInfo.minStdCount" style="width:30px" maxlength="5"/></td>
	</tr>	
</table>

 <table style="width:100%" border="0" class="listTable" id="scopeTable">
    <input type="hidden" name="task.id" value="${task.id}"/>
    <input type="hidden" name="task.electInfo.isElectable" value="1"/>
    <input type="hidden" name="task.calendar.id" value="${task.calendar.id}"/>
    <input type="hidden" name="taskIds" value="${taskIds}"/>
 	<tr class="darkColumn" align="center">
      <td align="center" width="3%">
        <input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('scopeId'),event);"/>
      </td>
      <td width="6%"><@bean.message key="attr.index"/></td>
      <td width="10%"><@bean.message key="attr.enrollTurn"/></td>
      <td width="10%"><@bean.message key="entity.studentType"/></td>
      <td width="10%"><@bean.message key="entity.college"/></td>
      <td width="10%"><@bean.message key="entity.speciality"/></td>
      <td width="10%"><@bean.message key="entity.specialityAspect"/></td>
      <td width="40%"><@bean.message key="entity.adminClass"/></td>
     </tr>
	 <#list task.electInfo.electScopes as scope>
	 <#if scope.startNo?exists>
	 <#else>
	 <tr class="grayStyle">
	     <td  class="select">
	        <input type="checkBox"  name="scopeId" value="${scope.id}">
	     </td>
		 <td>${scope.id}</td>
		 <td>${scope.enrollTurns?if_exists}</td>
		 <td onmouseover="overCell(event)" onmouseout="outCell(event)"><input type='hidden' style='width:100%' name="default.id" value="${scope.stdTypeIds}"/>...</td>
		 <td onmouseover="overCell(event)" onmouseout="outCell(event)"><input type='hidden' style='width:100%' name="default.id" value="${scope.departIds?if_exists}"/><#if scope.departIds?exists>...</#if></td>
		 <td onmouseover="overCell(event)" onmouseout="outCell(event)"><input type='hidden' style='width:100%' name="default.id" value="${scope.specialityIds?if_exists}"/><#if scope.specialityIds?exists>...</#if></td>
		 <td onmouseover="overCell(event)" onmouseout="outCell(event)"><input type='hidden' style='width:100%' name="default.id" value="${scope.aspectIds?if_exists}"/><#if scope.aspectIds?exists>...</#if></td>
		 <td onmouseover="overCell(event)" onmouseout="outCell(event)"><input type='hidden' style='width:100%' name="default.id" value="${scope.adminClassIds?if_exists}"/><#if scope.adminClassIds?exists>...</#if></td> 
	 </tr>
	 </#if>
	 </#list>
	</table>
	
	<table width="100%" border="0">
	<tr>
    <td valign="top">
    <div id="stdTypeListDIV" style="display:none">
	<table width="100%" border="0">
     <tr>
	      <td  class="padding" style="height:22px;width:150px"  align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/><B><@bean.message key="entity.studentType"/> <@bean.message key="common.list"/></B>
	      </td>
	      <td></td>
	      <td  class="padding" valign="bottom" onclick="javascript:setSelectOption(stdTypeListSelect)" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
	       <img src="${static_base}/images/action/save.gif" align="top"/><B><@bean.message key="action.confirm"/></B>
	      </td>
      </tr>
      <tr>
	      <td  colspan="4" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
	      </td>
     </tr>
	</table>
       <select name="stdTypeListSelect" id="stdTypeListSelect" multiple size="15" style="width:250px;">
       </select>
    </div>
    
    <div id="departListDIV"  style="display:none">
	<table width="100%" border="0">
     <tr>
	      <td  class="padding" style="height:22px;width:150px;border:0px;solid #000000;  align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/><B><@bean.message key="entity.college"/> <@bean.message key="common.list"/></B>
	      </td>
	      <td></td>
	      <td  class="padding" valign="bottom" onclick="javascript:setSelectOption(departListSelect)" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
	       <img src="${static_base}/images/action/save.gif" align="top"/><B><@bean.message key="action.confirm"/></B>
	      </td>
      </tr>
      <tr>
	      <td  colspan="4" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
     </tr>
	</table>
       <select name="departListSelect" id="departListSelect" multiple size="15" style="width:250px;">
       </select>  	
    </div>
    <div id="specialityListDIV" style="display:none">
	<table width="100%" border="0">
     <tr>
	      <td  class="padding" style="height:22px;width:150px"  align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/><B><@bean.message key="entity.speciality"/> <@bean.message key="common.list"/></B>
	      </td>
	      <td></td>
	      <td  class="padding"  valign="bottom" onclick="javascript:setSelectOption(specialityListSelect)" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
	       <img src="${static_base}/images/action/save.gif" align="top"/><B><@bean.message key="action.confirm"/></B>
	      </td>
      </tr>
      <tr>
	      <td  colspan="4" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
     </tr>
	</table>
       <select name="specialityListSelect" id="specialityListSelect" multiple size="15" style="width:250px;">
       </select>
    </div>
    <div id="aspectListDIV"  style="display:none">
	<table width="100%" border="0">
     <tr>
	      <td  class="padding" style="height:22px;width:200px"  align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/><B><@bean.message key="entity.specialityAspect"/> <@bean.message key="common.list"/></B>
	      </td>
	      <td></td>
	      <td  class="padding"  valign="bottom" onclick="javascript:setSelectOption(aspectListSelect)" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
	       <img src="${static_base}/images/action/save.gif" align="top"/><B><@bean.message key="action.confirm"/></B>
	      </td>
      </tr>
      <tr>
	      <td  colspan="4" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
     </tr>	
	</table>
       <select name="aspectListSelect" id="aspectListSelect" multiple size="15" style="width:250px;">
       </select>   	
    </div>
    <div id="adminClassListDIV"  style="display:none">
	<table width="100%" border="0">
     <tr>
	      <td  class="padding" style="height:22px;width:150px"  align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/><B><@bean.message key="entity.adminClass"/> <@bean.message key="common.list"/></B>
	      </td>
	      <td></td>
	      <td  class="padding" valign="bottom" onclick="javascript:setSelectOption(adminClassListSelect)" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
	       <img src="${static_base}/images/action/save.gif" align="top"/><B><@bean.message key="action.confirm"/></B>
	      </td>
      </tr>
      <tr>
	      <td  colspan="4" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
     </tr>
	</table>
       <select name="adminClassListSelect" id="adminClassListSelect" multiple size="15" style="width:250px;">
       </select> 	
    </div>
    </td>
    <td width="50%" valign="top">
	<table width="100%" border="0">
     <tr>
	      <td  class="padding" style="height:22px;width:150px"  align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/><B><@bean.message key="attr.stdNoSegement"/></B>
	      </td>
      </tr>
      <tr>
	      <td  colspan="4" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
     </tr>
	</table>
	
	<table  width="100%" border="0" class="listTable" id="stdNoTable">
 	 <tr class="darkColumn" align="center">
      <td align="center" width="3%">
        <input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('scopeId'),event);">
      </td>
      <td width="6%"><@bean.message key="attr.index"/></td>
      <td width="10%"><@bean.message key="attr.startNo"/></td>
      <td width="10%"><@bean.message key="attr.endNo"/></td>
      </tr>     
	 <#list task.electInfo.electScopes as scope>
	 <#if scope.startNo?exists>
	 <tr class="grayStyle">
	     <td width="2%" align="center" bgcolor="#CBEAFF">
	        <input type="checkBox" name="scopeId" value="${scope.id}">
	     </td>
	     <td>${scope.id}</td>
		 <td>${scope.startNo}</td> 
		 <td>${scope.endNo}</td> 
	 </tr>
	 </#if>
     </#list>
    </table>
    </td>
    </form>
    </tr>
    </table>
 <script>
    var newOrmodifies=0;
    var deleteScopeIds="";
    
    var scopeAttrs = new Array();
    scopeAttrs[0]="id";
    scopeAttrs[1]="enrollTurns";
    scopeAttrs[2]="stdTypeIds";
    scopeAttrs[3]="departIds";
    scopeAttrs[4]="specialityIds";
    scopeAttrs[5]="aspectIds";
    scopeAttrs[6]="adminClassIds";
    scopeAttrs[7]="startNo";
    scopeAttrs[8]="endNo";
    
    var displayActions = new Object();
    
    displayActions[scopeAttrs[2]] = displayStdTypeList;
    displayActions[scopeAttrs[3]] = displayDepartList;
    displayActions[scopeAttrs[4]] = displaySpecialityList;
    displayActions[scopeAttrs[5]] = displayAspectList;
    displayActions[scopeAttrs[6]] = displayAdminClassList;    
    
    var clearActions = new Object();
    clearActions[scopeAttrs[2]] = function(cell){}
    clearActions[scopeAttrs[3]] = function(cell){clearCellContents(cell,3);}
    clearActions[scopeAttrs[4]] = function(cell){clearCellContents(cell,2);}
    clearActions[scopeAttrs[5]] = function(cell){clearCellContents(cell,1);}
    clearActions[scopeAttrs[6]] = function(cell){}
    

    /**
     * 新创建一个范围行，保证name属性放在最后一个位置
     */
	function newScope(tb){
	    newOrmodifies++;
	    var tr = document.createElement('tr');
	    var cellsNum = tb.rows[0].cells.length;
	    for(var j = 0 ; j < cellsNum ; j++){
	        var td = document.createElement('td');
	        if(j==0)
		        td.innerHTML=tb.rows[0].cells[j].innerHTML;
		    td.style.backgroundColor="#ebebeb";
	        tr.appendChild(td);
	    }
	    tr.className="grayStyle";
	    tr.align="center";
	    tr.childNodes[2].innerHTML="<input type='text' style='width:80px' name=electStdScope"+ newOrmodifies +"." +scopeAttrs[1]+">";;
	    for(var j = 3 ; j < cellsNum ; j++){
	    	tr.childNodes[j].id=scopeAttrs[j-1];
	    	tr.childNodes[j].onclick=selectCell;
	    	tr.childNodes[j].onmouseover=overCell;
	    	tr.childNodes[j].onmouseout=outCell;
	    	tr.childNodes[j].innerHTML="<input type='hidden' style='width:100%' name=electStdScope"+ newOrmodifies +"." + scopeAttrs[j-1]+">";
	    	//alert(tr.childNodes[j].innerHTML);
	    }
	    tb.tBodies[0].appendChild(tr);
	    //alert("newOrmodifies:" + newOrmodifies);
	}

    // 新创建一个学号段行
	function newStdNoSegment(tb){
    	newOrmodifies++;
	    var tr = document.createElement('tr');
	    var cellsNum = tb.rows[0].cells.length;
	    for(var j = 0 ; j < cellsNum ; j++){
	        var td = document.createElement('td');
	        if(j==0)
		        td.innerHTML=tb.rows[0].cells[j].innerHTML;
		    else if(j==2)
		       td.innerHTML ="<input type='text' style='width:100%' name=electStdScope"+ newOrmodifies +".startNo>"
		    else if(j==3)
		       td.innerHTML ="<input type='text' style='width:100%' name=electStdScope"+ newOrmodifies +".endNo>"
	        tr.appendChild(td);
	    }
	    tr.className="grayStyle";
	    tr.align="center";
	    tb.tBodies[0].appendChild(tr);
	    //alert("newOrmodifies:" + newOrmodifies);
	}
	//删除范围和学号段
    function deleteScope(){
		var Ids = getCheckBoxValue(document.getElementsByName("scopeId"));
		if(Ids!=""&&!confirm("<@bean.message key="prompt.deletion"/>"))return;
		var count=0;
		count += deleteScopeOfTable(scopeTable);
		count += deleteScopeOfTable(stdNoTable);
		if(count==0) {alert("<@bean.message key="common.selectPlease"/>");return;}
		//alert("newOrmodifies:" + newOrmodifies);
		//alert("deleteScopeIds:" + deleteScopeIds);
	}
	// 删除具体某表的选中的行（该表第一列是复选框）
	function deleteScopeOfTable(table){
	    var rows =table.tBodies[0].childNodes;
		//	alert(rows.length);
		var count=0;
		// 跳过标题
		for(var i=1;i<rows.length;i++){
		   if(rows[i].firstChild.firstChild.checked){
		       if(rows[i].childNodes[1].innerHTML!="") {
		           if(rows[i].childNodes[1].innerHTML.indexOf(".")==-1)deleteScopeIds+=","+ rows[i].childNodes[1].innerHTML;
		           else deleteScopeIds+=","+ rows[i].childNodes[1].firstChild.value;
		       }
		       else newOrmodifies--;
		       table.tBodies[0].removeChild(rows[i--]);
		       count++;
		   }
		}
		return count;
	}
	// 清除单元格中的数据，n表示下级级联的数目
    function clearCellContents(td,n){	    
	    for(var i=0;i<n;i++){
   	        td = td.nextSibling; 
	        td.childNodes[0].value="";
	        td.innerHTML=td.innerHTML.substring(0,td.innerHTML.indexOf(">")+1);
   	        //alert(td.innerHTML);  	          	        
	    }	   
	}
	/**
	 * 更新已有的学生范围（包括学号段），新建的不再响应之列
	 */
	function updateScope(){
		var rows =scopeTable.tBodies[0].childNodes;
		var origCount = newOrmodifies;
		// 更改学号范围表
		for(var i=1;i<rows.length;i++){
		    // 对尚未修改的记录进行更换面目
			if(rows[i].childNodes[0].firstChild.checked&&rows[i].childNodes[1].innerHTML!=""&&rows[i].childNodes[1].innerHTML.indexOf(".")==-1){
				newOrmodifies++;
			    rows[i].childNodes[1].innerHTML ="<input type='hidden' value='" + rows[i].childNodes[1].innerHTML
			             +"' name=electStdScope"+newOrmodifies +".id>" + rows[i].childNodes[1].innerHTML;
			    rows[i].childNodes[2].innerHTML="<input type='text'   style='width:80px' value='" + rows[i].childNodes[2].innerHTML 
			             +"' name=electStdScope"+newOrmodifies +".enrollTurns>"
			    for(var j=3;j<rows[i].childNodes.length;j++){
			        rows[i].childNodes[j].innerHTML="<input type='hidden' value='" + rows[i].childNodes[j].firstChild.value
			             +"' name=electStdScope"+newOrmodifies +"." +scopeAttrs[j-1] +">" + "...";
			    rows[i].childNodes[j].onclick=selectCell;			   
			    }			    
			}			
		}
		rows =stdNoTable.tBodies[0].childNodes;
		// 更改学号段表
		for(var i=1;i<rows.length;i++){
		    // 对尚未修改的记录进行更换面目
			if(rows[i].childNodes[0].firstChild.checked&&rows[i].childNodes[1].innerHTML!=""&&rows[i].childNodes[1].innerHTML.indexOf(".")==-1){
			    newOrmodifies++; 
			    rows[i].childNodes[1].innerHTML ="<input type='hidden' value=" + rows[i].childNodes[1].innerHTML
			             +" name=electStdScope"+newOrmodifies +".id>" + rows[i].childNodes[1].innerHTML;
			    rows[i].childNodes[2].innerHTML="<input type='text'  value= " + rows[i].childNodes[2].innerHTML 
			             +" name=electStdScope"+newOrmodifies +".startNo>"
			    rows[i].childNodes[3].innerHTML="<input type='text' value=" + rows[i].childNodes[3].innerHTML
			             +" name=electStdScope"+newOrmodifies +".endNo>";
			    
			    //alert(rows[i].outerHTML);
			}
		}
		if(origCount == newOrmodifies) alert("<@bean.message key="info.selectSaved"/>.");
	}
	// 保存既有的设置（新增的和修改的）
	function saveScope(){
    	var form = document.scopeForm;
    	var rows =scopeTable.tBodies[0].rows;
    	var errorInfo="";
    	for(var i=1;i<rows.length;i++){
    	    if(rows[i].childNodes[2].innerHTML.indexOf("INPUT")!=-1){
    	       var turns = rows[i].childNodes[2].firstChild.value;
    	       if(turns=="") continue;
    	       if(turns.indexOf(',')!=0)turns = ','+turns;
    	       if(turns.lastIndexOf(',')!=turns.length-1)turns = turns +',';
    	       rows[i].childNodes[2].firstChild.value=turns;
    	       var turnArray = turns.substring(1,turns.lastIndexOf(',')).split(",");
    	       for(var j=0;j<turnArray.length;j++)
    	          if(!/^\d\d\d\d-\d$/.test(turnArray[j]))
    	             errorInfo+=turnArray[j]+"<@bean.message key="error.enrollTurn"/>";
    	    }
    	}
        if(errorInfo!=""){alert(errorInfo);return;}
		var a_fields = {
			'task.electInfo.minStdCount':{'l':'下限人数', 'r':true, 't':'f_minStdCount', 'f':'unsigned'},
			'task.electInfo.maxStdCount':{'l':'上限人数', 'r':true, 't':'f_maxStdCount', 'f':'unsigned'}
		};
		
		var v = new validator(form, a_fields, null);
		if (v.exec) {
			if (form['task.electInfo.minStdCount'].value > form['task.electInfo.maxStdCount'].value) {
				alert("下限人数不能超过上限人数！");
				return;
			}
	        form.action+="&newOrModifies="+newOrmodifies+"&deleteScopeIds="+deleteScopeIds;
	    	form.submit();
    	}
	}
	//选择一个学生范围单元格
	var selectedCell =null;
	function selectCell(){
	    // 清空其他选择单元
	    for(var i=1;i<scopeTable.tBodies[0].childNodes.length;i++){	      
	        var row = scopeTable.tBodies[0].childNodes[i];
	       
	        for(var j=2;j<row.childNodes.length;j++){
	            var col = row.childNodes[j];	        
	            col.style.backgroundColor = "#ebebeb";
	        }
	    }
	    // 设置单元数据，并显示对应的数据
	    var ele = event.srcElement;
	    if(ele.style.backgroundColor=="#ebebeb"){
    	     ele.style.backgroundColor="#bfffbf";    	     
   	         displayActions[ele.innerHTML.substring(ele.innerHTML.indexOf(".")+1,ele.innerHTML.indexOf(">"))]();
   	    }
   	    // 记录被选择的单元
   	    selectedCell =ele;
	    //if( ele.style.backgroundColor=="#bfffbf")ele.style.backgroundColor="#ebebeb";   
	}
	
	function overCell(event){
	    if(!inited){initData();inited=true;}
	    var ele= getEventTarget(event);
	    if(ele.innerHTML=="")toolTip("<@bean.message key="common.null"/>",'#000000', '#FFFF00');
	    else {
	        var idSeq ="";
	        if(ele.innerHTML.indexOf(".")==-1) {	           
	           idSeq=ele.innerHTML;
	        }
	        else {
	           idSeq = ele.firstChild.value;
	        }
	        var tip="";
	        var index=3;
	        for(;index<ele.parentNode.childNodes.length;index++)
		        if(ele==ele.parentNode.childNodes[index]) break;

	        var data = datas[scopeAttrs[index-1]];
	        var ids  = idSeq.substring(1,idSeq.length-1).split(",");	       
	        for(var i=0;i<ids.length;i++){
	            if(""!=ids[i]){
	                if("undefined" !=typeof (data[ids[i]])){
		               tip += data[ids[i]].code + "    "+data[ids[i]].name+"<br>";	
		            }
		        }
	        }
	        if(tip=="") tip="<@bean.message key="common.null"/>";
	        else tip=tip.substring(0,tip.lastIndexOf("<br>"));
	        toolTip(tip,'#000000', '#FFFF00');
	    }
	}
	function outCell(){
	    toolTip();
	}
	function deleteAllOptions(select){
	    for(var i=select.options.length-1;i>-1;i--)
	        select.remove(i);
	}

	var dataViews = new Array();
	dataViews[0]= "stdTypeListDIV";
	dataViews[1]= "departListDIV";
	dataViews[2]= "specialityListDIV";
	dataViews[3]= "aspectListDIV";
	dataViews[4]= "adminClassListDIV";
	
	function displayDataView(myView){
	    for(var i=0;i<dataViews.length;i++)
		  document.getElementById(dataViews[i]).style.display="none";
	    document.getElementById(myView).style.display="block";
	}
		
	// 设置最后选定的值
	function setSelectOption(select){
	    var ids = ",";
	    for(var i=0;i<select.options.length;i++)
	        if(select.options[i].selected&&""!=select.options[i].value) ids += select.options[i].value +",";
	    if(ids==",") ids="";
	    
        selectedCell.childNodes[0].value=ids;
        if(ids=="")
           selectedCell.innerHTML=selectedCell.innerHTML.replace("...","")
        else if( selectedCell.innerHTML.indexOf("...")==-1){
          selectedCell.innerHTML=selectedCell.innerHTML+="...";
        }
	    clearActions[selectedCell.innerHTML.substring(selectedCell.innerHTML.indexOf(".")+1,selectedCell.innerHTML.indexOf(">"))](selectedCell);
	}    

	/**
	 * 将指定数据设为选中状态
	 */
	function setSelected(select,idSeq){
	    for(var i=0;i<select.options.length;i++){
	        if(idSeq.indexOf(","+select.options[i].value+",")!=-1) select.options[i].selected=true;
	        else select.options[i].selected=false;
	    }
	}
	
	// 显示学生类别
	var stdTypeListDisplayed = false;
	
	function displayStdTypeList(){
	    displayDataView('stdTypeListDIV');
	    var select = document.getElementById("stdTypeListSelect");
	    var idSeq = event.srcElement.childNodes[0].value;
	    if(!stdTypeListDisplayed){
	        stdTypeListDisplayed=true;
	        for(stdType in stdTypes){
	        	select.options[select.options.length] = new Option(stdTypes[stdType].code+"      "+stdTypes[stdType].name,stdTypes[stdType].id);
	        }
	        select.add(new Option("<@bean.message key="common.selectPlease"/>",""),0);
	    }
	    setSelected(select,idSeq);
	    f_frameStyleResize(self);
	}
	
	// 显示院系
    var departListDisplayed = false;
	function displayDepartList(){
	   displayDataView('departListDIV');
	    var select = document.getElementById("departListSelect");
	    if(!departListDisplayed){
	        departListDisplayed=true;
	        for(depart in departs){
	        	select.options[select.options.length] = new Option(departs[depart].code+"      "+departs[depart].name,departs[depart].id);
	        }
	        select.add(new Option("<@bean.message key="common.selectPlease"/>",""),0);
	    }
	    var idSeq = event.srcElement.childNodes[0].value;
	    setSelected(select,idSeq);
	    f_frameStyleResize(self);
	}

    //显示专业
	function displaySpecialityList(){
	    displayDataView('specialityListDIV');
	    var select=document.getElementById('specialityListSelect');
	    deleteAllOptions(select);
	    var departIds = event.srcElement.previousSibling.firstChild.value;	    
        if(departIds!=""){
           var tmpSpecialityList = new Array();
           for(speciality in specialities){
           		if(departIds.indexOf("," + specialities[speciality].departId+",")!=-1)
	           		select.options[select.options.length] = new Option(specialities[speciality].code+"      "+specialities[speciality].name,specialities[speciality].id);
           }
           var idSeq = event.srcElement.childNodes[0].value;
           setSelected(select,idSeq);
           select.add(new Option("<@bean.message key="common.selectPlease"/>",""),0);
        }
        f_frameStyleResize(self);
	}
	// 显示专业方向
	function displayAspectList(){
	    displayDataView('aspectListDIV');
	    var select = document.getElementById('aspectListSelect');
        deleteAllOptions(select);
	    var specialityIds = event.srcElement.previousSibling.firstChild.value;
        if(specialityIds!=""){
           for(aspect in aspects){
           		if(specialityIds.indexOf("," + aspects[aspect].specialityId+",")!=-1)
                    select.options[select.options.length] = new Option(aspects[aspect].code+"      "+aspects[aspect].name,aspects[aspect].id);
           }
           var idSeq = event.srcElement.childNodes[0].value;
           
           setSelected(select,idSeq);
           select.add(new Option("<@bean.message key="common.selectPlease"/>",""),0);
        }
        f_frameStyleResize(self);
	}
	//显示行政班级
	function displayAdminClassList(){
	    displayDataView('adminClassListDIV');
	    var select =document.getElementById('adminClassListSelect')
        deleteAllOptions(select);
        // 获得已经设置的值
        var ele= event.srcElement.previousSibling;
        var aspectIds=ele.firstChild.value;
        ele=ele.previousSibling;
	    var specialityIds = ele.firstChild.value;
	    ele=ele.previousSibling;
	    var departIds=ele.firstChild.value;
	    
        if(aspectIds!=""||specialityIds!=""||departIds!=""){      
           for(adminClass in adminClasses){
                if(departIds.indexOf("," + adminClasses[adminClass].departId+ ",")==-1)continue;
                if(specialityIds!=""&&specialityIds.indexOf("," +  adminClasses[adminClass].specialityId+",")==-1 )continue;
                if(aspectIds!=""&&aspectIds.indexOf("," + adminClasses[adminClass].aspectId+",")==-1 )continue;
           		select.options[select.options.length] = new Option(adminClasses[adminClass].code+"      "+adminClasses[adminClass].name,adminClasses[adminClass].id);
           }
           var idSeq = event.srcElement.childNodes[0].value;
           setSelected(select,idSeq);
           select.add(new Option("<@bean.message key="common.selectPlease"/>",""),0);
        }
        f_frameStyleResize(self);
	}
	
	var stdTypes = new Object();
    <#list studentTypeList as stdType>
        stdTypes['${stdType.id}']={'id':'${stdType.id}','name':'<@i18nName stdType/>','code':'${stdType.code}'};
    </#list>
    var departs = new Object();
    <#list departmentList as depart>
        departs['${depart.id}']={'id':'${depart.id}','code':'${depart.code}','name':'<@i18nName depart/>'};
    </#list>
    var specialities = new Object();
    <#list specialityList as specilaity>
        specialities['${specilaity.id}']={'id':'${specilaity.id}','code':'${specilaity.code}','name':'<@i18nName specilaity/>','departId':'${specilaity.department?if_exists.id?if_exists}'};
    </#list>
    var aspects = new Object();
    <#list specialityAspectList as aspect>
        aspects['${aspect.id}']={'id':'${aspect.id}','code':'${aspect.code}','name':'<@i18nName aspect/>','specialityId':'${aspect.speciality?if_exists.id?if_exists}'};
    </#list>
    var adminClasses = new Object();
    <#list adminClassList as adminClass>
        adminClasses['${adminClass.id}']={'id':'${adminClass.id}','code':'${adminClass.code}','name':'${adminClass.name}','departId':'${adminClass.department.id}','specialityId':'${adminClass.speciality?if_exists.id?if_exists}','aspectId':'${adminClass.aspect?if_exists.id?if_exists}'};
    </#list>
    var datas = new Object();
    var inited  =false;
    function initData(){
	    datas[scopeAttrs[2]] = stdTypes;
	    datas[scopeAttrs[3]] = departs;
	    datas[scopeAttrs[4]] = specialities;
	    datas[scopeAttrs[5]] = aspects;
	    datas[scopeAttrs[6]] = adminClasses;
    }
   var bar = new ToolBar('electScopeBar','<@bean.message key="action.modify"/> <@bean.message key="entity.electScope"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@bean.message key="action.delete"/>","javascript:deleteScope()");
   bar.addItem("<@bean.message key="action.modify"/>","javascript:updateScope()",'update.gif');
   bar.addItem("<@bean.message key="action.add"/>","javascript:newScope(scopeTable)");
   bar.addItem("<@bean.message key="attr.stdNoSegement"/>","javascript:newStdNoSegment(stdNoTable)");
   bar.addItem("<@bean.message key="action.save"/>","javascript:saveScope()",'save.gif');
   bar.addBack("<@bean.message key="action.back"/>");    
 </script>

</body>
<#include "/templates/foot.ftl"/>