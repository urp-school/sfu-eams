		
	var detailArray = {};
		
	function getId(name){
	    return(getRadioValue(document.getElementsByName(name)));
	}
	
	function getIds(name){
       return(getCheckBoxValue(document.getElementsByName(name)));
    }
	
	function getName(id){
	    if (id != ""){
	          return detailArray[id]['name'];
	    }else{
	          return "";
	    }
	}
	
	function getNames(ids){
       var names = "";
       var tempIds = ids.split(",");
       for (var key in detailArray){
           for (var i=0; i<tempIds.length; i++){
               if (tempIds[i]==key){
                   names = names + detailArray[key]['name'] + ",";
               }
           }
       }
       return names.substr(0, names.length-1);
    }
    function getValuesOf(kind,ids){
       var values = "";
       var tempIds = ids.split(",");
       for (var key in detailArray){
           for (var i=0; i<tempIds.length; i++){
               if (tempIds[i]==key){
                   values = values + detailArray[key][kind] + ",";
               }
           }
       }
       return values.substr(0, values.length-1);
    }