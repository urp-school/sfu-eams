  <script src='dwr/interface/studentService.js'></script>
  <script src='dwr/engine.js'></script>
  <script src='dwr/util.js'></script>
  <script>
  function checkId(){
  	  var valueCode = document.getElementById("codeValue").value;
  	  if(valueCode==""){
  	  	checkMessage.innerHTML='<font color="red" size="2"><@bean.message key="common.forbiddenNull"/></font>';
  	  	return;
  	  }
  	  studentService.checkStdNoIfExists(afterCheck,valueCode);
  }
  function afterCheck(data){
  	  if(data==true){
  	  	checkMessage.innerHTML='<font color="red" size="2"><@bean.message key="common.coadCanNotBeUsed"/></font>';
  	  }
  	  else{
  	  	checkMessage.innerHTML='<font color="red" size="2"><@bean.message key="common.coadCanBeUsed"/></font>';
  	  }
  }
  </script>
  <input type="button" name="checkIdValue" value="<@bean.message key="common.checkButtonName"/>"  onclick="checkId()">
  <div id="checkMessage"></div>