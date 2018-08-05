   var orderByWhat=null;
   function info(){
	   var id = getSelectIds("messageId");
	   if(isMultiId(id)){
	      alert("select only one please.");return;
	   }else if(""==id){
	      alert("select one");return;
	   }else{	   
	      self.location="systemMessage.do?method=info&systemMessage.id="+id;
	   }
	}
	function remove(removeFrom){
	   var id = getSelectIds("messageId");	   
	   if(""==id){
	      alert("select one");return;
	   }else{
   	      var form =document.msgListForm;
	      form.action="systemMessage.do?method=remove&systemMessageIds="+id;
	      form.action+="&removeFrom="+removeFrom;
	      form.submit();
	   }
	}
    function setStatus(setFrom,value){
	   var id = getSelectIds("messageId");	   
	   if(""==id){
	      alert("select one");return;
	   }else{
	      var form =document.msgListForm;
	      form.action="systemMessage.do?method=setStatus&systemMessageIds="+id;
	      form.action+="&setFrom="+setFrom;
	      form.action+="&status="+value;
	      form.submit();
	   }
	}