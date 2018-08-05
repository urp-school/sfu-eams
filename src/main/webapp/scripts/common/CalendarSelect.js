    // 缺省值
    var defaultCalendarValues=new Object();
    // 页面上所有的三级级联选择
    var calendarSelects= new Array();
    // 当前操作影响的选择
    //var myCalendarSelects=new Array();
    // 当前的三级级联选择
    var yearSelectQueue=new Array();
    var termSelectQueue=new Array();
    var originalOnChanges=new Array();
    var selectInited=new Object();
    // 初始化学生类别选择框
    function initCalendarStdTypeSelect(stdTypes){
        if(null==selectInited[this.stdTypeSelectId]){
           selectInited[this.stdTypeSelectId]=true;
        }else{
           return;
        }
        if( null==document.getElementById(this.stdTypeSelectId)) return;
        defaultCalendarValues[this.stdTypeSelectId]=document.getElementById(this.stdTypeSelectId).value;
        DWRUtil.removeAllOptions(this.stdTypeSelectId);
        if(this.stdTypeDefaultFirst){
          	DWRUtil.addOptions(this.stdTypeSelectId,stdTypes,'id','name');
          	if(this.stdTypeNullable){
               DWRUtil.addOptions(this.stdTypeSelectId,[{'id':'','name':'...'}],'id','name');
            }
        }else{
          	if(this.stdTypeNullable){
               DWRUtil.addOptions(this.stdTypeSelectId,[{'id':'','name':'...'}],'id','name');
            }
            DWRUtil.addOptions(this.stdTypeSelectId,stdTypes,'id','name');
        }
        // 让非空的学生类别作为默认
        if ("" != defaultCalendarValues[this.stdTypeSelectId]) {
	        setSelected(document.getElementById(this.stdTypeSelectId),defaultCalendarValues[this.stdTypeSelectId]);
        }
       
        var selfOnchange =document.getElementById(this.stdTypeSelectId).onchange;
        document.getElementById(this.stdTypeSelectId).onchange=function (event) {
            if (event==null) {
               event=getEvent();
            }
	        notifyYearChange(event);
	        if (selfOnchange!=null) {
    	        selfOnchange();
	        }
	    }
    }
    // 初始化学年度选择框
    function initYearSelect(){
       DWRUtil.removeAllOptions(this.yearId);
       var std= document.getElementById(this.stdTypeSelectId);
       if(std.value!=""){
            yearSelectQueue.push(this);
       		calendarDAO.getYearsOrderByDistance(setYearOptions,std.value);
       }
       var originalOnChange=document.getElementById(this.yearId).onchange;
       if(null==originalOnChange){
          document.getElementById(this.yearId).onchange=function(event){notifyTermChange(event,null);}
       }else{
           originalOnChanges[originalOnChanges.length]=originalOnChange;
           document.getElementById(this.yearId).onchange=
           //function(event){alert(1);notifyTermChange(event,null);originalOnChanges[(originalOnChanges.length-1)]();}
           new Function("event","notifyTermChange(event,null);originalOnChanges["+(originalOnChanges.length-1)+"]();");
       }
    }
    // 通知学年度变化,填充学年度选择列表
    function notifyYearChange(event){
       if(event==null)return;
       //alert("event in notifyYearChange"+event);
       yearCalendarSelects = getMyCalendarSelects(getEventTarget(event).id);
       //alert(yearCalendarSelects.length);
       for(var i=0;i<yearCalendarSelects.length;i++){
	       var s= document.getElementById(yearCalendarSelects[i].stdTypeSelectId);
	       if(null==s) continue;
	       DWRUtil.removeAllOptions(yearCalendarSelects[i].yearId);
	       if(s.value!=""){
	           yearSelectQueue.push(yearCalendarSelects[i]);
		       calendarDAO.getYearsOrderByDistance(setYearOptions,s.value);
	       }else{
	          DWRUtil.removeAllOptions(yearCalendarSelects[i].termId);
	       }
       }
    }
    function setYearOptions(data){
       var curCalendarSelect=yearSelectQueue.shift();
       if(null!=curCalendarSelect){
	       if(curCalendarSelect.yearNullable){
	           DWRUtil.addOptions(curCalendarSelect.yearId,[{'id':'','name':'...'}],'id','name');
	       }
	       DWRUtil.addOptions(curCalendarSelect.yearId,data);
	       if(defaultCalendarValues[curCalendarSelect.yearId]!=""){
	           setSelected(document.getElementById(curCalendarSelect.yearId),defaultCalendarValues[curCalendarSelect.yearId]);
	       }
	       notifyTermChange(null,curCalendarSelect.yearId);
       }
    }
    // 通知学期变化，填充学期列表
    function notifyTermChange(event,selectId){
       //alert("event in notifyTermChange");
       if(null==selectId) {
         selectId=getEventTarget(event).id;
       }
       myCalendarSelects= getMyCalendarSelects(selectId);
       for(var i=0;i<myCalendarSelects.length;i++){
           //alert("removeAllOptions of :"+myCalendarSelects[i].termId);
	       DWRUtil.removeAllOptions(myCalendarSelects[i].termId);
	       var s= document.getElementById(myCalendarSelects[i].stdTypeSelectId);
	       var y= document.getElementById(myCalendarSelects[i].yearId);
	       
	       if(s.value!=""&&y.value!=null){
  	          termSelectQueue.push(myCalendarSelects[i]);
	          //alert(curCalendarSelect.termId)
	          calendarDAO.getTermsOrderByDistance(setTermOptions,s.value,y.value);
	       }else{
	          DWRUtil.removeAllOptions(myCalendarSelects[i].termId);
	       }
       }
    }
    function setTermOptions(data){
       //alert(data)
       var curCalendarSelect=termSelectQueue.shift();
       if(null!=curCalendarSelect){
           DWRUtil.removeAllOptions(curCalendarSelect.termId);
	       DWRUtil.addOptions(curCalendarSelect.termId,data); 
	            
           if(curCalendarSelect.termNullable){
              //alert("add null term");
              DWRUtil.addOptions(curCalendarSelect.termId,[{'id':'','name':'...'}],'id','name');
           }
	       if(defaultCalendarValues[curCalendarSelect.termId]!=""){
	           setSelected(document.getElementById(curCalendarSelect.termId),defaultCalendarValues[curCalendarSelect.termId]);
	       }
       }
    }
    function CalendarSelect(stdTypeSelectId,yearId,termId,stdTypeNullable,yearNullable,termNullable,stdTypeDefaultFirst){
     // alert(stdTypeSelectId+":"+stdTypeSelectId+":"+yearId+":"+yearId);
      this.stdTypeSelectId=stdTypeSelectId;
      this.yearId=yearId;
      this.termId=termId;
      this.stdTypeNullable=stdTypeNullable;
      this.yearNullable=yearNullable;
      this.termNullable=termNullable;
      this.stdTypeDefaultFirst=stdTypeDefaultFirst;
      if(stdTypeDefaultFirst==null)stdTypeDefaultFirst=true;
      this.initStdTypeSelect=initCalendarStdTypeSelect;
      
      this.initYearSelect=initYearSelect;
      this.init=initCalendar;
      this.getdefaultCalendarValues=getdefaultCalendarValues;
      calendarSelects[calendarSelects.length]=this;
      this.getdefaultCalendarValues(); 
  
    }
    function initCalendar(stdTypes){
       this.initStdTypeSelect(stdTypes);
       this.initYearSelect();
    }
    function getMyCalendarSelects(id){
        var myCalendarSelects = new Array();
        for(var i=0;i<calendarSelects.length;i++){
            if(calendarSelects[i].stdTypeSelectId==id||
               calendarSelects[i].yearId==id||
               calendarSelects[i].termId==id)
               myCalendarSelects[myCalendarSelects.length]=calendarSelects[i];
        }
        return myCalendarSelects;
    }
    /**
     * 获得缺剩值
     */
    function getdefaultCalendarValues(){
       defaultCalendarValues[this.stdTypeSelectId]=document.getElementById(this.stdTypeSelectId).value;
       defaultCalendarValues[this.yearId]=document.getElementById(this.yearId).value;
       defaultCalendarValues[this.termId]=document.getElementById(this.termId).value;
       //alert(defaultCalendarValues[this.termId]);
    }