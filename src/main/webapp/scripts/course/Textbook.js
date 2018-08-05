    function enterQuery(event,form) {
        if (portableEvent(event).keyCode == 13){
                 getTextbookList(form);
        } 
     }
	 function save(form) {
	    var errorInfo="";
	 	if(form['book.id'].value==""){
	 		errorInfo+="请选择教材或直接输入教材名称\n";
	 	}
	 	/*if(!(/^\d+$/.test(form['require.count'].value))){
	 	    errorInfo+="请填写订购数量(正整数)\n";
	 	}
	 	if(!(/^\d+\.?\d*$/.test(form['book.price'].value))){
	 	    errorInfo+="请填写单价(正实数)\n";
	 	}
	 	if(""==form['book.auth'].value){
	 	    errorInfo+="请填写作者\n";
	 	}
	 	if(""==form['book.press.id'].value){
	 	    errorInfo+="请选择出版社\n";
	 	}
	 	if(""==form['book.bookType.id'].value){
	 	    errorInfo+="请选择教材种类";
	 	}
	 	*/
	 	if(""==errorInfo){
	       form.submit();
	    }else{
	       alert(errorInfo);
	    }
	 }
	 function change(listvalue){
	    textbookDAO.getTextbook(setBookInfo,listvalue);
	 }

    //设置页面上的教材信息
	function setBookInfo(data){
		for(var att in data){
		   if(null!=$(att)){
		     $(att).innerHTML=((null==data[att])?"":data[att]);
		     //alert(att+"->"+data[att]);
		   }
		}
		$("press.name").innerHTML=data['press'].name;
		$("bookType.name").innerHTML=data['bookType'].name;
	}
	var textbookForm=null;
	//查询教材列表
	function getTextbookList(form){
	     textbookForm = form;
	     var name=textbookForm.textbookName.value;
	     textbookDAO.getTextbooksByName(setTextbookList,name);
	}
  
    //设置教材列表数据
    function setTextbookList(data){
	    if(data.length==0){       
	       alert("没有相关教材,你可以[教材基本信息]中新增一个教材!");
	       for(var attr in textbookForm){
	          if(attr.indexOf("book")==0){
	             textbookForm[attr].value="";
	          }
	       }
	     }else{
		     DWRUtil.removeAllOptions('bookNameChoice');
		     var books =new Array();
		     for(var i=0;i<data.length;i++){
		        books[i]={'id':data[i][0],'name':data[i][1]};
		     }
		     
		  	 DWRUtil.addOptions('bookNameChoice',books,"id","name");
		  	 change(textbookForm.bookNameChoice.value);
	     }     
   }