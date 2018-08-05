function onClose(){
	if(confirm("关闭将不会提交修改")){self.window.close();}
}
function changeSeriateFlag(flag){seriateFlag=flag;}
function KeyNavigation() {} 

KeyNavigation.Up = 0; 
KeyNavigation.Right = 1; 
KeyNavigation.Down = 2; 
KeyNavigation.Left = 3; 

KeyNavigation.PreviousCursorPosition = null; 

KeyNavigation.CreateTableMapping = function(tbl) 
{ 
  if ( !tbl.rowCount ) 
  { 
    var tableMap = [];
    //var aryFirst = new Array(); 
    //tableMap.push(aryFirst);
    var colCount = 0; 
    for ( var i=0 ; i < tbl.rows[0].cells.length ; ++i ) 
    {//alert(tbl.rows(1).cells(i).innerHTML+":"+tbl.rows(1).cells(i).colSpan)
      colCount += tbl.rows[0].cells[i].colSpan; 
    } //alert(colCount)
    tbl.columnCount = colCount; 
    tbl.rowCount = tbl.rows.length; //alert(tbl.rows(4).innerHTML)
    for ( var i=0 ; i < tbl.rowCount ; ++i ) 
    { 
      var ary = new Array(colCount); 
      for ( var j=0 ; j < colCount ; ++j ) 
      { 
        ary[j] = true; 
      } 
      tableMap.push(ary); 
    }
    for ( var i=0 ; i < tbl.rowCount ; ++i ) 
    { 
      var cellIndex = 0; //alert(tbl.rows(i).cells(cellIndex).colSpan)
	  var currentCell = tbl.rows[i].cells[cellIndex];

      for ( var j=0 ; j < colCount ; j += currentCell.colSpan ) 
      {//alert(j + currentCell.colSpan)
		currentCell = tbl.rows[i].cells[cellIndex++]; //alert(currentCell.innerHTML)//try{currentCell.colSpan}catch(e){alert(i+":"+currentCell)}
		
        if ( tableMap[i][j] ) 
        { 
          for ( var m=i ; m < i+currentCell.rowSpan ; ++m ) 
          { 
            for ( var n=j ; n < j+currentCell.colSpan ; ++n ) 
            { 
              tableMap[m][n] = false; 
            } 
          } 
          tableMap[i][j] = currentCell; //alert("i="+i+":"+"j="+j+":"+"currentCell="+currentCell.innerHTML)//alert(tableMap)
         } 
      } 
    } //alert(cellIndex)
    tbl.tableMap = tableMap; 
  } //else{alert("tbl.rowCount"+tbl.rowCount)}
}; 

KeyNavigation.GetSiblingCell = function(tbl, cell, dir) 
{ //alert(cell.innerHTML)
  KeyNavigation.CreateTableMapping(tbl); 
  var colIndex = -1; 
  var row = cell.parentNode; 
  var rowIndex = row.rowIndex;
  for ( var i=0 ; i < tbl.columnCount ; ++i ) 
  { //alert(tbl.tableMap[rowIndex][i].innerHTML+"="+cell.innerHTML)
     if ( tbl.tableMap[rowIndex][i] == cell ) 
     { 
       colIndex = i; 
       break; 
     }
  } 
  if ( colIndex == -1 ) 
  { 
     throw "can‘t find the cell in table."; 
  } 
  var siblingCell = null; 
  var incV, incH; 
  incV = 1, incH = 1; 
  switch(dir) 
  { 
    case KeyNavigation.Up : 
    { 
      incV = -1; 
    } 
    case KeyNavigation.Down : 
    { 
      for ( var i=1 ; i < tbl.rowCount ; ++i ) 
      { 
         var tmpRowIdnex = (tbl.rowCount+rowIndex+i*incV)%(tbl.rowCount); 
         if((incV<0)&&((tmpRowIdnex)>((tbl.rowCount+rowIndex)%(tbl.rowCount)))){window.scrollTo(cell.offsetLeft,cell.offsetHeight);}else if((incV>0)&&((tmpRowIdnex)<((tbl.rowCount+rowIndex)%(tbl.rowCount)))){window.scrollTo(cell.offsetLeft,0);}
         siblingCell = tbl.tableMap[tmpRowIdnex][colIndex];  //alert("siblingCell="+siblingCell+":"+"IsAvailableCell="+KeyNavigation.IsAvailableCell(siblingCell))
         if ( KeyNavigation.IsAvailableCell(siblingCell) ) 
         { 
           break;  
         } 
      } 
      break; 
    } 
    case KeyNavigation.Left : 
    { 
      incH = -1; 
    } 
    case KeyNavigation.Right : 
    { 
      for ( var i=1 ; i < tbl.columnCount ; ++i ) 
      { 
      	 //if((tbl.columnCount+colIndex+i*incH)>tbl.columnCount){alert((tbl.columnCount+colIndex+i*incH)+":"+tbl.columnCount);}
         var tmpColumnIdnex = (tbl.columnCount+colIndex+i*incH)%tbl.columnCount;
         if(tmpColumnIdnex==0){if((tmpColumnIdnex)>((tbl.columnCount+colIndex)%tbl.columnCount)){window.scrollTo(cell.offsetWidth,cell.offsetTop);}else{window.scrollTo(0,cell.offsetTop);}}
         siblingCell = tbl.tableMap[rowIndex][tmpColumnIdnex]; 
         if ( KeyNavigation.IsAvailableCell(siblingCell) ) 
         { 
           break;  
         } 
      } 
      break; 
    } 
  } 
  return siblingCell; 
}; 

KeyNavigation.IsAvailableCell = function(cell) 
{ 
  return cell && (cell.getElementsByTagName('INPUT').length + cell.getElementsByTagName('TEXTAREA').length > 0); 
}; 

KeyNavigation.DoKeyDown = function(elmt,event) 
{ 
  var tbl = elmt; //alert(elmt.innerHTML)
  var input = getEventTarget(portableEvent(event));//event.srcElement; alert(event.srcElement.innerHTML)
  var keyCode = event.keyCode; //alert(event.keyCode)
  var iPsn = KeyNavigation.GetCaretPosition(input); //alert(iPsn)
  var cell = input.parentNode; //alert(cell.innerHTML)
  var siblingCell = null; 
  var directionV = KeyNavigation.Down; 
  switch(keyCode) 
  { 
    case 27 : /* Escape */ 
    { 
      KeyNavigation.GetCaretPosition(txb); 
      break; 
    } 
    case 38 : /* Move Up */ 
    { 
      directionV = KeyNavigation.Up; 
    } 
    case 40 : /* Move Down */ 
    { 
      var isTab = false; 
      if ( input.tagName == 'TEXTAREA' ) 
      { 
        if ( iPsn == KeyNavigation.PreviousCursorPosition || 
          ( ( iPsn == 0 && directionV == KeyNavigation.Up ) || 
          ( iPsn == input.value.length && directionV == KeyNavigation.Down ) ) ) 
        { 
          isTab = true; 
        } 
        else 
        { 
          KeyNavigation.PreviousCursorPosition = iPsn; 
        } 
      } 
      if ( input.tagName == 'INPUT' || isTab ) 
      { 
        siblingCell = KeyNavigation.GetSiblingCell(tbl, cell, directionV); 
      } 
      break; 
    } 
    case 37 : /* Move Left */ 
    { 
      if ( iPsn == 0 ) 
      { 
         siblingCell = KeyNavigation.GetSiblingCell(tbl, cell, KeyNavigation.Left); 
      } 
      break; 
    } 
    case 39 : /* Move Right */ 
    { 
      if ( iPsn == input.value.length ) 
      { 
         siblingCell = KeyNavigation.GetSiblingCell(tbl, cell, KeyNavigation.Right); 
      } 
      break; 
    } 
    default:
    {
    	return;
    }
  } 
  if ( siblingCell && siblingCell != cell ) 
  { 
    var siblingInput = null; 
    var inputs = siblingCell.getElementsByTagName('INPUT'); 
    if ( inputs.length > 0 ) 
    { 
      siblingInput = inputs[0]; 
      siblingInput.focus(); 
    } 
    else 
    { 
      inputs = siblingCell.getElementsByTagName('TEXTAREA'); 
      if ( inputs.length > 0 ) 
      {  
        siblingInput = inputs[0]; 
        siblingInput.focus(); 
      } 
    } 
  } 
}; 

KeyNavigation.GetCaretPosition = function(txb) 
{ 
  var slct = document.selection; //alert(slct.TextRange)
  if(slct){
	  var rng = slct.createRange(); 
	  txb.select(); 
	  rng.setEndPoint("EndToStart", slct.createRange()); 
	  var psn = rng.text.length; 
	  rng.collapse(false); 
	  rng.select(); 
	  return psn; 	
  }

}; 


 	function updateOrSave(){//if(confirm(document.getElementById("changeRecordString").value))return;
 		document.recordForm.action=getAction(self.location.href);
 		document.recordForm.submit();
 	}
 	function getAction(location){
    	var actionAll = location.split("/");
    	var action = actionAll[actionAll.length-1].split("?");
    	return action[0];
    }
 	var oldValue;
 	function getOldValue(inputText){
 		oldValue = inputText.value;
 	}
 	
 	function validateValue(inputText){
 		var newValue = inputText.value;
 		var value_format = /^[1-5]?$/; 		
		if(!value_format.test(newValue)){
			alert("请输入1至5间的整数");
			inputText.value=oldValue;
			//alert($('recordTable').onkeydown);
			inputText.focus(); 
			return false;
		}
		if(oldValue!=newValue){
 			if(newValue==""&&oldValue!=""){
	 			if(confirm("清空将删除该考勤纪录")){
	 				<!--inputText.style.backgroundColor=changeColor;-->
	 				return true;
	 			}else{
	 				inputText.value=oldValue;
	 				inputText.focus();
	 				return false;
	 			} 			
 			}else{
	 			<!--inputText.style.backgroundColor=changeColor;-->
	 			return true;
 			}
 		}
 		
 	}
 	
 	function changeStatusColor(inputText){
 		switch(inputText.value){
 			case "1" :
 			{inputText.style.backgroundColor=presenceColor;inputText.parentElement.style.backgroundColor=presenceColor;break;}
 			case "2" :
 			{inputText.style.backgroundColor=absenteeismColor;inputText.parentElement.style.backgroundColor=absenteeismColor;break;}
 			case "3" :
 			{inputText.style.backgroundColor=lateColor;inputText.parentElement.style.backgroundColor=lateColor;break;}
 			case "4" :
 			{inputText.style.backgroundColor=leaveEarlyColor;inputText.parentElement.style.backgroundColor=leaveEarlyColor;break;}
 			case "5" :
 			{inputText.style.backgroundColor=askedForLeaveColor;inputText.parentElement.style.backgroundColor=askedForLeaveColor;break;}
 			default :
 			{inputText.style.backgroundColor=defaultColor;inputText.parentElement.style.backgroundColor=defaultColor;break;}
 		}
 	}
 	
 	function changeRecordStringValue(inputText){
 		var changeRecordString = document.getElementById("changeRecordString").value;
  		if(changeRecordString.indexOf(inputText.name)==-1){
  			document.getElementById("changeRecordString").value = changeRecordString+";"+inputText.name+";";
  		}
 	}
 	
 	function changeColumns(inputText){
 		if(!validateValue(inputText)){
 			return;
 		}
 		changeStatusColor(inputText);
 		var batchValue = inputText.value;
 		var cell = inputText.parentElement;//alert(cell.innerHTML)
 		var currentCellIndex = cell.cellIndex-2;//alert(currentCellIndex);
 		var tableCell = $('recordTable');
 		var tableRows = tableCell.rows;//if(confirm(tableRows[tableRows.length-1].innerHTML))return;
 		for(var i=0;i<tableRows.length;i++){
 		 	if((i==0)||(i==1)||(i==tableRows.length-1))continue;
 		 	var inputs = tableRows[i].getElementsByTagName('INPUT');//alert(inputs.length);
 		 	var tempValue = inputs[currentCellIndex].value;
      		if(tempValue==batchValue){
      		}else{
      			<!--inputs[currentCellIndex].style.backgroundColor=changeColor;-->
      			inputs[currentCellIndex].value=batchValue;
      			changeStatusColor(inputs[currentCellIndex]);
      			changeRecordStringValue(inputs[currentCellIndex]);
      		} 		 	
 		} 		
 	}
 	
 	function changeRows(inputText){
 		if(!validateValue(inputText)){
 			return;
 		}
 		changeStatusColor(inputText);
 		var batchValue = inputText.value;
 		var cell = (inputText.parentElement).parentElement;//alert(cell.innerHTML)
 		var inputs = cell.getElementsByTagName('INPUT'); 
 		if ( inputs.length > 0 ) { 
      		for(var i=0; i<inputs.length; i++){
	      		var tempValue = inputs[i].value;
	      		if(tempValue==batchValue){
	      		}else{
	      			<!--inputs[i].style.backgroundColor=changeColor;-->
	      			inputs[i].value=batchValue;
	      			changeStatusColor(inputs[i]);
	      			changeRecordStringValue(inputs[i]);
	      		}      			
      		}
    	} 
 	}
 	
 	function changeAll(inputText){
 		if(!validateValue(inputText)){
 			return;
 		}
 		changeStatusColor(inputText);
 		var batchValue = inputText.value;
 		var cell = $('recordTable');
 		var tableRows = cell.rows;//if(confirm(tableRows[tableRows.length-1].innerHTML))return;
 		for(var i=0;i<tableRows.length;i++){
 		 	if((i==0)||(i==1)||(i==tableRows.length-1))continue;
 		 	var inputs = tableRows[i].getElementsByTagName('INPUT');//alert(inputs.length);
 		 	for(var j=1;j<inputs.length;j++){
				var tempValue = inputs[j].value;
	      		if(tempValue==batchValue){
	      		}else{
	      			<!--inputs[j].style.backgroundColor=changeColor;-->
	      			inputs[j].value=batchValue;
	      			changeStatusColor(inputs[j]);
	      			changeRecordStringValue(inputs[j]);
	      		}
 		 	}
 		}
 		
 	}
 	
 	function onReset(){
 		var form = $('recordForm');
 		form.reset();
 		var cell = $('recordTable');
 		var tableRows = cell.rows;
 		for(var i=0;i<tableRows.length;i++){
 		 	if((i==0)||(i==tableRows.length-1))continue;
 		 	var inputs = tableRows[i].getElementsByTagName('INPUT')
 		 	for(var j=0;j<inputs.length;j++){				
      			<!--inputs[j].style.backgroundColor=defaultColor;-->
      			changeStatusColor(inputs[j]);
      			document.getElementById("changeRecordString").value = "";
 		 	}
 		}
 	}
 	
 	function changeBatch(inputText){
 		if(!validateValue(inputText)){
 			return;
 		}
 		changeStatusColor(inputText);
 		changeRecordStringValue(inputText);
 		if(oldValue!=inputText.value){
 			//alert(inputText.parentElement.parentElement.rowIndex);
 			var cell = $('recordTable');
 			var firstRow = cell.rows(1);
 			var inputs = firstRow.getElementsByTagName('INPUT');
 			inputs[0].style.backgroundColor=defaultColor;
 			inputs[0].value="";
 			inputs[inputText.parentElement.cellIndex-2].style.backgroundColor=defaultColor;
 			inputs[inputText.parentElement.cellIndex-2].value="";
 			inputs = inputText.parentElement.parentElement.getElementsByTagName('INPUT');
 			inputs[0].style.backgroundColor=defaultColor;
 			inputs[0].value="";
 			if(seriateFlag){
 				var currentDate=inputText.name.split(',')[2];
 				inputs=inputText.parentElement.parentElement.getElementsByTagName('INPUT');
 				for(var i=0;i<inputs.length;i++){
 					if(inputs[i].name.indexOf(currentDate)!=-1){
 						if(inputs[i].value==""){
 							inputs[i].value=inputText.value;
 							changeStatusColor(inputs[i]);
 							changeRecordStringValue(inputs[i]);
 						}
 					}
 				}
 			}else{}
 		}
 	}
 	
	function deleteCriteria(){
		var Ids = getCheckBoxValue(document.getElementsByName("criteriaId"));
		if(Ids!=""&&!confirm(deletion))return;
		var count=0;
		var rows= recordTable.rows;
		// 跳过标题
		for(var i=1;i<rows.length;i++){
		   if(rows[i].firstChild.firstChild.checked){
		       if(rows[i].childNodes[1].innerHTML!="") {
		           if(rows[i].childNodes[1].innerHTML.indexOf(".")==-1){
		               deletedIds+=","+ rows[i].childNodes[1].innerHTML;
		           }
		           else{
		             deletedIds+=","+ rows[i].childNodes[1].firstChild.value;
		           }
		       }
		       else newOrmodifies--;
		       recordTable.tBodies[0].removeChild(rows[i--]);
		       count++;
		   }
		}
		if(count==0) {alert(selectPlease);return;}
		else{
		  if(confirm(saveDeletion))saveCriteria();
		}
		//alert("newOrmodifies:" + newOrmodifies);
		f_frameStyleResize(self);
	}