    // 缺省值
    var defaultSecondValues=new Object();
    // 页面上所有的级联选择
    var secondSelects= new Array();
    // 当前操作影响的选择
    var mySecondSelects=new Array();
    // 当前的级联选择
    var secondSpeciality2Select=null;   
    // 初始化专业选择框
    function initSecondSpecialitySelect(secondSpecialities){
        defaultSecondValues[this.secondSpecialitySelectId]=document.getElementById(this.secondSpecialitySelectId).value;
        DWRUtil.removeAllOptions(this.secondSpecialitySelectId);
	    DWRUtil.addOptions(this.secondSpecialitySelectId,secondSpecialities,'id','name');	    
        if(this.secondSpecialityNullable)
           DWRUtil.addOptions(this.secondSpecialitySelectId,[{'id':'','name':'...'}],'id','name');
        setSelected(document.getElementById(this.secondSpecialitySelectId),defaultSecondValues[this.secondSpecialitySelectId]);       
	    document.getElementById(this.secondSpecialitySelectId).onchange =function (event){	        
	        notifySecondAspectChange(event);
	       }
    }
    // 初始化专业方向选择框
    function initSecondAspectSelect(){
       defaultSecondValues[this.secondAspectSelectId]=document.getElementById(this.secondAspectSelectId).value;
       DWRUtil.removeAllOptions(this.secondAspectSelectId);
       var s= document.getElementById(this.secondSpecialitySelectId);
        if(this.secondAspectNullable)
           DWRUtil.addOptions(this.secondAspectSelectId,[{'id':'','name':'...'}],'id','name');
       if(s.value!=""){
           secondSpeciality2Select=this;
           specialityAspectDAO.getSpecialityAspectNames(setSecondAspectOptions,s.value);
        }
       //setSelected(document.getElementById(this.secondAspectSelectId),defaultSecondValues[this.secondAspectSelectId]);
    }    
    // 通知专业方向变化，填充专业方向列表
    function notifySecondAspectChange(event){
       //alert("event in notifyAspectChange");
       mySecondSelects= getMySecondSelects(getEventTarget(event).id);
       for(var i=0;i<mySecondSelects.length;i++){
           //alert("removeAllOptions of :"+mySecondSelects[i].aspectSelectId);
	       DWRUtil.removeAllOptions(mySecondSelects[i].secondAspectSelectId);
	       if(mySecondSelects[i].secondAspectNullable)
	           DWRUtil.addOptions(mySecondSelects[i].secondAspectSelectId,[{'id':'','name':'...'}],'id','name');
	       var s= document.getElementById(mySecondSelects[i].secondSpecialitySelectId);
	       if(s.value!=""){
	          secondSpeciality2Select=mySecondSelects[i];
	          //alert(secondSpeciality2Select.secondAspectSelectId)
	          specialityAspectDAO.getSpecialityAspectNames(setSecondAspectOptions,s.value);       
	       }
       }
    }    
    function setSecondAspectOptions(data){
       for(var i=0;i<data.length;i++)
          DWRUtil.addOptions(secondSpeciality2Select.secondAspectSelectId,[{'id':data[i][0],'name':data[i][1]}],'id','name');       
       if(defaultSecondValues[secondSpeciality2Select.secondAspectSelectId]!=null){
           setSelected(document.getElementById(secondSpeciality2Select.secondAspectSelectId),defaultSecondValues[secondSpeciality2Select.secondAspectSelectId]);
       }
    }    
    function SecondSpeciality2Select(secondSpecialitySelectId,secondAspectSelectId,secondSpecialityNullable,secondAspectNullable){
      this.secondSpecialitySelectId=secondSpecialitySelectId;
      this.secondAspectSelectId=secondAspectSelectId;
      this.secondSpecialityNullable=secondSpecialityNullable;
      this.secondAspectNullable=secondAspectNullable;
      this.initSecondSpecialitySelect=initSecondSpecialitySelect;
      this.initSecondAspectSelect=initSecondAspectSelect;
      this.init=init;
      secondSelects[secondSelects.length]=this;
    }
    function initSecondSpecialityAndAspectSelect(){
      secondSpeciality2Select.initSecondAspectSelect();
    }
    function init(secondSpecialities){       
       this.initSecondSpecialitySelect(secondSpecialities);
       secondSpeciality2Select=this;
       // 留上200毫秒等待专业初试化完毕
       setTimeout(initSecondSpecialityAndAspectSelect,"200");       
    }
    function getMySecondSelects(id){
        var mySecondSelects = new Array();
        for(var i=0;i<secondSelects.length;i++){
            if(secondSelects[i].secondSpecialitySelectId==id||
               secondSelects[i].secondAspectSelectId==id)
               mySecondSelects[mySecondSelects.length]=secondSelects[i];
        }
        return mySecondSelects;
    }
