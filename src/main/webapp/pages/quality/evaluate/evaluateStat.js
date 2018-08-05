	function deleteTableTd(tableId,value){
		var rowsArray = document.getElementById(tableId).rows;
		var tdIds="";
		var tdArrays = rowsArray[1].cells;
		for(var i=0;i<tdArrays.length-1;i++){
			if(tdArrays[i].innerHTML==value){
				tdIds+=i+",";
			}
		}
		if(tdIds!=""){
			var tdId = tdIds.split(",");
			for(var i=rowsArray.length-2;i>0;i--){
				for(var j=0; j<tdId.length-1;j++){
					if(tdId[j]!=""){
						if(rowsArray[i].cells[tdId[j]].innerHTML!=value){
							tdId[j]="";
							continue;
						}
					}
				}
			}
			for(var i=rowsArray.length-2;i>=0;i--){
				var m=0;
				for(var j=0; j<tdId.length-1;j++){
					if(tdId[j]!=""){
						var id = new Number(tdId[j]);
						rowsArray[i].removeChild(rowsArray[i].cells[id-m]);
						m++;
					}
				}
			}
		}
	}