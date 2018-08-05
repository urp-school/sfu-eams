    // 缺省值
    var defaultValues=new Object();
    // 页面上所有的三级级联选择
    var selects= new Array();
    // 当前操作影响的选择
    var mySelects=new Array();
    // 当前的三级级联选择
    var stdTypeDepart3Select=null;
    // 初始化学生类别选择框
    function initStdTypeSelect(stdTypes){
        if( null==document.getElementById(this.stdTypeSelectId)) return;
        defaultValues[this.stdTypeSelectId]=document.getElementById(this.stdTypeSelectId).value;        
        DWRUtil.removeAllOptions(this.stdTypeSelectId);
        DWRUtil.addOptions(this.stdTypeSelectId,stdTypes,'id','name');
        if(this.stdTypeNullable){
            DWRUtil.addOptions(this.stdTypeSelectId,[{'id':'','name':'...'}],'id','name');
        }
        setSelected(document.getElementById(this.stdTypeSelectId),defaultValues[this.stdTypeSelectId]);
        
        if(null != document.getElementById(this.departSelectId)&&null != document.getElementById(this.specialitySelectId)){
	       document.getElementById(this.stdTypeSelectId).onchange=function (){
	        notifySpecialityChange();
	       } 
	    }
    }
    // 初始化部门初始化
    function initDepartSelect(departs){
        defaultValues[this.departSelectId]=document.getElementById(this.departSelectId).value;
        DWRUtil.removeAllOptions(this.departSelectId);
	    DWRUtil.addOptions(this.departSelectId,departs,'id','name');	    
        if(this.departNullable)
           DWRUtil.addOptions(this.departSelectId,[{'id':'','name':'...'}],'id','name');
        setSelected(document.getElementById(this.departSelectId),defaultValues[this.departSelectId]);
       
	    document.getElementById(this.departSelectId).onchange =function (){
	        notifySpecialityChange();
	       }
    }
    // 初始化专业选择框
    function initSpecialitySelect(){
       defaultValues[this.specialitySelectId]=document.getElementById(this.specialitySelectId).value;
       DWRUtil.removeAllOptions(this.specialitySelectId);
       document.getElementById(this.specialitySelectId).onchange=notifyAspectChange;
       
       var std= document.getElementById(this.stdTypeSelectId);
       var d = document.getElementById(this.departSelectId); 

       if(this.specialityNullable)
           DWRUtil.addOptions(this.specialitySelectId,[{'id':'','name':'...'}],'id','name');
       if(std.value!=""&&d.value!=""){
            stdTypeDepart3Select=this;
       		specialityDAO.getSpecialityNames(setSpecialityOptions,d.value,std.value);       
       }
       //setSelected(document.getElementById(this.specialitySelectId),defaultValues[this.specialitySelectId]);
    }
    // 通知专业变化,填充专业选择列表
    function notifySpecialityChange(){
       //alert("event in notifySpecialityChange");
       mySelects= getMySelects(event.srcElement.id);
       //if(mySelects.length>1)
       //   DWREngine.setAsync(false);
       for(var i=0;i<mySelects.length;i++){
	       var s= document.getElementById(mySelects[i].stdTypeSelectId);
	       var d = document.getElementById(mySelects[i].departSelectId);
	       if(null==s||null==d) continue;
	       DWRUtil.removeAllOptions(mySelects[i].specialitySelectId);
	       if(mySelects[i].specialityNullable){
	           DWRUtil.addOptions(mySelects[i].specialitySelectId,[{'id':'','name':'...'}],'id','name');
	           setSelected(document.getElementById(mySelects[i].specialitySelectId),"");
	       }    
	       if(s.value!=""&&d.value!=""){
	           stdTypeDepart3Select =mySelects[i];
		       specialityDAO.getSpecialityNames(setSpecialityOptions,d.value,s.value);
	       }
	       // 过上半秒钟再去查找专业方向，以防专业查找还没有完成
	       //setTimeout(notifyAspectChange,"500");
       }
      // DWREngine.setAsync(true);
    }
    function setSpecialityOptions(data){
       for(var i=0;i<data.length;i++)
           DWRUtil.addOptions(stdTypeDepart3Select.specialitySelectId,[{'id':data[i][0],'name':data[i][1]}],'id','name');
       if(defaultValues[stdTypeDepart3Select.specialitySelectId]!=null){
           setSelected(document.getElementById(stdTypeDepart3Select.specialitySelectId),defaultValues[stdTypeDepart3Select.specialitySelectId]);
       }
    }
    function SpecialitySelect(stdTypeSelectId,departSelectId,specialitySelectId,stdTypeNullable,departNullable,specialityNullable){
     // alert(stdTypeSelectId+":"+departSelectId+":"+specialitySelectId+":"+specialitySelectId);
      this.stdTypeSelectId=stdTypeSelectId;
      this.departSelectId=departSelectId;
      this.specialitySelectId=specialitySelectId;
      this.stdTypeNullable=stdTypeNullable;
      this.departNullable=departNullable;
      this.specialityNullable=specialityNullable;
      this.initStdTypeSelect=initStdTypeSelect;
      this.initDepartSelect=initDepartSelect;
      this.initSpecialitySelect=initSpecialitySelect;
      this.init=init;
      selects[selects.length]=this;
    }
    function init(stdTypes,departs){
       this.initStdTypeSelect(stdTypes);
       this.initDepartSelect(departs);
       this.initSpecialitySelect();
       stdTypeDepart3Select=this;
    }
    function getMySelects(id){
        var mySelects = new Array();
        for(var i=0;i<selects.length;i++){
            if(selects[i].stdTypeSelectId==id||
               selects[i].departSelectId==id||
               selects[i].specialitySelectId==id)
               mySelects[mySelects.length]=selects[i];
        }
        return mySelects;
    }