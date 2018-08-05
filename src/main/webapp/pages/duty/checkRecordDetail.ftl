  <script type='text/javascript' src='dwr/interface/dutyService.js'></script>
  <script src='dwr/engine.js'></script>
  <script src='dwr/util.js'></script>
  <script>
	  var beginUnitIdString ;
  	  var endUnitIdString ;
  	  var dutyDate ;
  	  var courseTypeId ;
  	  var year ;
  	  var term ;
  	  var studentTypeId ;
  	  var studentTypeDescriptions ;
  	  var departmentId ;
  	  var departmentDescriptions ;
  	  var targetstdId ;
  	  var stdIds ;
  	  var teachTaskId ;
  function checkRecordDetail(){
  	   beginUnitIdString = DWRUtil.getValue("recordDetail.beginUnit.id");
  	   endUnitIdString = DWRUtil.getValue("recordDetail.endUnit.id");
  	   dutyDate = DWRUtil.getValue("recordDetail.dutyDate");
  	   courseTypeId = DWRUtil.getValue("courseTypeId");
  	   year = DWRUtil.getValue("year");
  	   term = DWRUtil.getValue("term");
  	   studentTypeId = DWRUtil.getValue("studentTypeId");
  	   studentTypeDescriptions = DWRUtil.getValue("studentTypeDescriptions");
  	   departmentId = DWRUtil.getValue("departmentId");
  	   departmentDescriptions = DWRUtil.getValue("departmentDescriptions");
  	   targetstdId = DWRUtil.getValue("targetstdId");
  	   stdIds = DWRUtil.getValue("stdId");
  	   teachTaskId = DWRUtil.getValue("teachTaskId");
  	  dutyService.checkDutyRecordsIfExists(afterCheck,stdIds,teachTaskId,dutyDate,beginUnitIdString,endUnitIdString);
  }
  
  function afterCheck(data){
  	var location = self.location.href;
    var url = location.split("/");
    var action = url[url.length-1].split("?");
  	if(data==true){
  	    document.getElementById("commonFormDiv").style.display = "none";
  	    document.getElementById("chooseDiv").style.display = "block";
  	  }
  	  else{  	  	
        document.commonForm.action = action[0];
        document.commonForm.actionName.value = action[0];
        document.commonForm.submit();
  	  }
  }
  
  function setDutyRecordId(dutyRecordId){
  	document.getElementById("recordId").value = dutyRecordId; 

  }
  </script>  