    // 缺省值
    var defaultCalendarValues=new Object();
    // 页面上所有的三级级联选择
    var calendarSelects= new Array();
    // 当前操作影响的选择
    //var myCalendarSelects=new Array();
    // 当前的三级级联选择
    var calendarSelectQueue=new Array();
    var originalOnChanges=new Array();
    var selectInited=new Object();
    // 初始化学生类别选择框
    function initSchemeSelect(schemes){
        if(null==selectInited[this.schemeId]){
           selectInited[this.schemeId]=true;
        }else{
           return;
        }
        if( null==document.getElementById(this.schemeId)) return;
        DWRUtil.removeAllOptions(this.schemeId);
       	DWRUtil.addOptions(this.schemeId,schemes,'id','name');
       	setSelected(document.getElementById(this.schemeId),defaultCalendarValues[this.schemeId]);
       	
        var selfOnchange =document.getElementById(this.schemeId).onchange;
        document.getElementById(this.schemeId).onchange=function (event){
            if(event==null)
               event=getEvent();
	        notifyCalendarChange(event);
	        if(selfOnchange!=null)
    	        selfOnchange();
	    }
    }
    // 初始化学年度选择框
    function initCalendarSelect(){
       DWRUtil.removeAllOptions(this.calendarId);       
       var schemeId= document.getElementById(this.schemeId);
       if(schemeId.value!=""){
            calendarSelectQueue.push(this);
       		calendarDAO.getTeachCalendarNames(setCalendarOptions,schemeId.value);       
       }
       var originalOnChange=document.getElementById(this.schemeId).onchange;
       if(null==originalOnChange){
          document.getElementById(this.yearId).onchange=function(event){notifyTermChange(event,null);}
       }
    }
    // 通知学年度变化,填充学年度选择列表
    function notifyCalendarChange(event){
       if(event==null)return;
       //alert("event in notifyYearChange"+event);
       calendarCalendarSelects = getMyCalendarSelects(getEventTarget(event).id);
       //alert(calendarCalendarSelects.length);
       for(var i=0;i<calendarCalendarSelects.length;i++){
	       var s= document.getElementById(calendarCalendarSelects[i].schemeId);
	       if(null==s) continue;
	       DWRUtil.removeAllOptions(calendarCalendarSelects[i].calendarId);
	       if(s.value!=""){
	           calendarSelectQueue.push(calendarCalendarSelects[i]);
		       calendarDAO.getTeachCalendarNames(setCalendarOptions,s.value);
	       }
       }
    }
    function setCalendarOptions(data){
       var curCalendarSelect=calendarSelectQueue.shift();
       if(null!=curCalendarSelect){
	       if(curCalendarSelect.calendarNullable){
	           DWRUtil.addOptions(curCalendarSelect.calendarId,[{'id':'','name':'...'}],'id','name');	           
	       }
	       DWRUtil.addOptions(curCalendarSelect.calendarId,data,'id','name');
	       if(defaultCalendarValues[curCalendarSelect.calendarId]!=""){
	           setSelected(document.getElementById(curCalendarSelect.calendarId),defaultCalendarValues[curCalendarSelect.calendarId]);
	       }
       }
    }
    
    function NewCalendarSelect(schemeId,calendarId,calendarNullable){
     // alert(schemeId+":"+schemeId+":"+calendarId+":"+calendarId);
      this.schemeId=schemeId;
      this.calendarId=calendarId;
      this.calendarNullable=calendarNullable;
      if(null==calendarNullable){
         calendarNullable=false;
      }
      this.initSchemeSelect=initSchemeSelect;
      this.initCalendarSelect=initCalendarSelect;
      this.init=initCalendar;
      
      this.getdefaultCalendarValues=getdefaultCalendarValues;
      calendarSelects[calendarSelects.length]=this;      
      this.getdefaultCalendarValues(); 
  
    }
    function initCalendar(schemes){  
       this.initSchemeSelect(schemes);       
       this.initCalendarSelect();         
    }
    
    function getMyCalendarSelects(id){
        var myCalendarSelects = new Array();
        for(var i=0;i<calendarSelects.length;i++){
            if(calendarSelects[i].schemeId==id||calendarSelects[i].calendarId==id)
               myCalendarSelects[myCalendarSelects.length]=calendarSelects[i];
        }
        return myCalendarSelects;
    }
    /**
     * 获得缺剩值
     */
    function getdefaultCalendarValues(){
       defaultCalendarValues[this.schemeId]=document.getElementById(this.schemeId).value;
       defaultCalendarValues[this.calendarId]=document.getElementById(this.calendarId).value;
       //alert(defaultCalendarValues[this.schemeId]);
    }