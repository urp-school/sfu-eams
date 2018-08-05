    // 缺省值
    var defaultValues=new Object();
    // 页面上所有的级联选择
    var selects= new Array();
    // 当前操作影响的选择
    var mySelects=new Array();
    // 当前的级联选择
    var stdStatusAlter2Select=null;
    // 初始化变动类别选择框
    function initAlterTypeSelect(alterTypeArray){
        if( null==document.getElementById(this.alterTypeSelectId)) return;
        defaultValues[this.alterTypeSelectId]=document.getElementById(this.alterTypeSelectId).value;        
        DWRUtil.removeAllOptions(this.alterTypeSelectId);
        DWRUtil.addOptions(this.alterTypeSelectId,alterTypeArray,'id','name');
        if(this.alterTypeNullable){
            DWRUtil.addOptions(this.alterTypeSelectId,[{'id':'','name':'...'}],'id','name');
        }
        setSelected(document.getElementById(this.alterTypeSelectId),defaultValues[this.alterTypeSelectId]);       
        document.getElementById(this.alterTypeSelectId).onchange=notifyAlterReasonChange;
    }
    
    // 初始化变动原因选择框
    function initAlterReasonSelect(){
       defaultValues[this.alterReasonSelectId]=document.getElementById(this.alterReasonSelectId).value;
       DWRUtil.removeAllOptions(this.alterReasonSelectId);
       
       if(this.alterReasonNullable)DWRUtil.addOptions(this.alterReasonSelectId,[{'id':'','name':'...'}],'id','name');           
      
       //setSelected(document.getElementById(this.alterReasonSelectId),defaultValues[this.alterReasonSelectId]);
    }
    
    // 通知变动原因变化,填充变动原因选择列表
    function notifyAlterReasonChange(){
       mySelects= getMySelects(event.srcElement.id);
       //if(mySelects.length>1)DWREngine.setAsync(false);
       for(var i=0;i<mySelects.length;i++){
	       var t=document.getElementById(mySelects[i].alterTypeSelectId);
	       if(null==t) continue;
	       DWRUtil.removeAllOptions(mySelects[i].alterReasonSelectId);
	       if(mySelects[i].alterReasonNullable){
	           DWRUtil.addOptions(mySelects[i].alterReasonSelectId,[{'id':'','name':'...'}],'id','name');
	           setSelected(document.getElementById(mySelects[i].alterReasonSelectId),"");
	       }    
	       if(t.value!=""){
	           stdStatusAlter2Select =mySelects[i];
		       studentService.getAlterationReasonList(setAlterReasonOptions,t.value);
	       }	       
       }
      // DWREngine.setAsync(true);
    }
   
    function setAlterReasonOptions(data){
       for(var i=0;i<data.length;i++)
           DWRUtil.addOptions(stdStatusAlter2Select.alterReasonSelectId,[{'id':data[i].id,'name':data[i].name}],'id','name');
       if(defaultValues[stdStatusAlter2Select.alterReasonSelectId]!=null){
           setSelected(document.getElementById(stdStatusAlter2Select.alterReasonSelectId),defaultValues[stdStatusAlter2Select.alterReasonSelectId]);
       }
    }
    
    function StdStatusAlter2Select(alterTypeSelectId,alterReasonSelectId,alterTypeNullable,alterReasonNullable){
      this.alterTypeSelectId=alterTypeSelectId;
      this.alterReasonSelectId=alterReasonSelectId;      
      this.alterTypeNullable=alterTypeNullable;
      this.alterReasonNullable=alterReasonNullable;
      this.initAlterTypeSelect=initAlterTypeSelect;
      this.initAlterReasonSelect=initAlterReasonSelect;
      this.init=init;
      selects[selects.length]=this;
    }

    function init(alterTypeArray){
       this.initAlterTypeSelect(alterTypeArray);       
       stdStatusAlter2Select=this;
       setTimeout('stdStatusAlter2Select.initAlterReasonSelect()',200);       
    }
    
    function getMySelects(id){
        var mySelects = new Array();
        for(var i=0;i<selects.length;i++){
            if(selects[i].alterTypeSelectId==id||
               selects[i].alterReasonSelectId==id)
               mySelects[mySelects.length]=selects[i];
        }
        return mySelects;
    }