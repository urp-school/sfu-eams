   function info(){
	   var id = getSelectIds("noticeId");
	   if(isMultiId(id)){
	      alert("select only one please.");return;
	   }else if(""==id){
	      alert("select one");return;
	   }else{	   
	      self.location="notice.do?method=info&notice.id="+id;
	   }
	}
	function remove(kind){
	   var id = getSelectIds("noticeId");	   
	   if(""==id){
	      alert("select one");return;
	   }else{
	      if(!confirm("是否删除选定的公告")) return;
   	      var form =document.msgListForm;
	      form.action="notice.do?method=remove&noticeIds="+id+"&kind="+kind;
	      form.submit();
	   }
	}
	function edit(isNew,kind){
	  var id ="";
	   if(!isNew){
		   id = getSelectIds("noticeId");	   
		   if(""==id){
		      alert("select one");
		      return;
		   }
		   if(isMultiId(id)){
	        alert("select only one please.");
	        return;
	      }
	   }
   	   var form =document.msgListForm;
	   form.action="notice.do?method=edit&notice.id="+id+"&kind="+kind;
	   form.submit();
	}