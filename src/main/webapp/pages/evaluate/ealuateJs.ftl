<script>
function removeSelect(selectId){
    		var  selectObject = document.getElementById(selectId);
    		for(var i=selectObject.length-1;i>=0;i--){
    			selectObject.remove(i);
    		}
    	}
    	<#-- 这个js里面的data数据必须保持数组 同时 数组里面第一是id,第二个是名称-->
    	function addDateOfSelect(selectId,data){
    		var  selectObject = document.getElementById(selectId);
    		for(var i=0;i<data.length;i++){
			selectObject.add(new Option(data[i][1],data[i][0]));
		}
    	}
 
 function moveOption(fromSelectId,toSelectId){
 	  var fromSelect = document.getElementById(fromSelectId);
 	  var toSelect = document.getElementById(toSelectId)
	for(var i=fromSelect.length-1;i>=0;i--){
		if(fromSelect.options[i].selected){
			var val1=fromSelect.options[i].value;
			var val2=fromSelect.options[i].text;
			toSelect.add(new Option(val2,val1));
			fromSelect.remove(i);
		}
	}
  }
</script>