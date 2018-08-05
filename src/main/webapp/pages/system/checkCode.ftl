  <script src='dwr/interface/baseinfoUtil.js'></script>
  <script src='dwr/engine.js'></script>
  <script src='dwr/util.js'></script>
  <script>
  function checkId(){
  	  var valueCode = document.getElementById("codeValue").value;
  	  if(""==valueCode) {
  	  	checkMessage.innerHTML='<font color="red" size="2"><@bean.message key="common.forbiddenNull"/></font>';
  	  	return;
  	  }
  	  baseinfoUtil.checkCodeIfExists(afterCheck,"${className}","code",valueCode);
  }
  function afterCheck(data){
  	  if(data==true){
  	  	checkMessage.innerHTML='<font color="red" size="2"><@bean.message key="common.coadCanNotBeUsed"/></font>';
  	  }
  	  else{
  	  	checkMessage.innerHTML='<font color="red" size="2"><@bean.message key="common.coadCanBeUsed"/></font>';
  	  }
  }
  function emptyCode(){
     var codeInput=document.getElementById("codeValue");
     codeInput.value="******";
     codeInput.readOnly=true;
     $("codeGenDiv").style.display="none";
     $("cancelGenDiv").style.display="block";
  }
  function cancelGen(){
     var codeInput=document.getElementById("codeValue");
     codeInput.value="";
     codeInput.readOnly=false;
     $("cancelGenDiv").style.display="none";
     $("codeGenDiv").style.display="block";
  }
  </script>
  <div id="codeGenDiv">
  <button onclick="checkId();return false;" id="checkButton">检查</button>
  <button onclick="emptyCode();return false;">自动生成</button>
  </div>
  <div id="cancelGenDiv" style="display:none">
  <button onclick="cancelGen();return false;">取消生成</button>
  </div>
  <div id="checkMessage"></div>