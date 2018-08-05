   var Weeks=["周一","周二","周三","周四","周五","周六","周日"];
   var maxUnit=12;
   function repeat(str,num){
      var rs="";
      for(var i=0;i<num;i++)
         rs+=str;
      return rs;
   }
   function abbreviateUnit(segInfo,units,start) {
		var sb = "";
		if (units==repeat("0",units.length)){
			return "";
	    }
		else{
			sb+=segInfo;
	    }
		if (units.indexOf("0")!=-1) {
			for (var i = 0; i < units.length; i++) {
				if (units.charAt(i) != '0'){
					sb+=(start + i + 1);
					sb+=",";
			     }
			}
			sb=sb.substr(0,sb.length-1);
		}
		return sb;
	}  
   /**
	 * 对可用时间进行缩略显示
	 * 
	 * @return
	 */
	function abbreviate(available) {
		if (null==available||""==available){
			return "";
	    }
		var sb = "";
		var allZeroUnit = repeat("0",maxUnit);

		for (var i = 0; i < 7; i++) {
			var dayAvalible = available.substr(i * maxUnit,maxUnit);
			if (dayAvalible==allZeroUnit){
				continue;
			}
			sb+=Weeks[i];
			if (dayAvalible.indexOf('0')==-1) {
				sb+=" ";
				continue;
			} else {
				sb+="(";
				var morning = abbreviateUnit("上午", dayAvalible
						.substring(0, 4), 0);
				
				var afternoon = abbreviateUnit("下午", dayAvalible
						.substring(4, 8), 4);
				
				var evening = abbreviateUnit("晚上", dayAvalible
						.substring(8, 12), 8);
				var hasOne=false;
				if("" !=morning ){
				   hasOne=true;
				   sb+=morning;
				}
				if (""!=afternoon){
				    if(hasOne){
				        sb+=" ";
				    }
					sb+=afternoon;
					hasOne=true;
			    }
				if (""!=evening){
			       if(hasOne){
			           sb+=" " ;
			       }
				   sb+= evening;
				}
				sb+=") ";
			}
		}
		return sb;
	}

 var availColors=["rgb(148, 174, 243)","rgb(148,174,243)","#94aef3"];
 var notAvailColor="yellow";
 var colors = new Object();
 colors["1"]="#94aef3";
 colors["0"]="yellow";
     function changeAvailTime(elem){         
         var selectUnitNums = document.getElementsByName('selectUnitNum');
         var num=4;
         for(var i=0;i<selectUnitNums.length;i++){
            if(selectUnitNums[i].checked)
            num=selectUnitNums[i].value;
         }         
         var input = document.getElementById("unit"+elem.id);
         var toBeChangeColor=null;
         var toBeChangeValue=null;
         for(var i=0;i<availColors.length;i++){
	         if(elem.style.backgroundColor==availColors[i]){
	             toBeChangeColor=notAvailColor;
	             toBeChangeValue="0";
	         }
         }
         if(elem.style.backgroundColor==notAvailColor){
            toBeChangeColor=availColors[0];
            toBeChangeValue="1";
         }
         var firstId=elem.id;
         var weekId=Math.floor(firstId/unitsPerDay);
         for(var i=0;i<num;i++){
             var input = document.getElementById("unit"+firstId);
             var grid = document.getElementById(firstId);
             grid.style.backgroundColor=toBeChangeColor;
             input.value=toBeChangeValue;
             firstId++;
             if(Math.floor(firstId/unitsPerDay)!=weekId) break;
         }
      }
     function getAvailTime(){
         var avail="";
         for(i=0;i<7*unitsPerDay;i++)
           avail+=document.getElementById("unit"+i).value;         
         return  avail;
     }
     function getRemark(){
        return document.getElementById("availTime_remark").value;
     }
     function inverse(){
	     for(var i=0; i<7*unitsPerDay;i++){
		     var elem = document.getElementById("unit"+i);
	         elem.value=1-elem.value;
	         elem.parentNode.style.backgroundColor = colors[elem.value];
	     }
     }
     function clearAll(){
         if(!confirm("清空占用标记,将使所有时间变为不可用，确定设置吗？"))return;
     	 for(var i=0; i<unitsPerDay*7;i++){
		     var elem = document.getElementById("unit"+i);
	         elem.value=0;	         
	         elem.parentNode.style.backgroundColor = colors[elem.value];
	     }
     }
	