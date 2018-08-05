    // 缺省值
    var defaultValues=new Object();
    // 页面上所有的三级级联选择
    var dutyTeachTaskSelects= new Array();
    // 当前操作影响的选择
    var myDutyTeachTaskSelects=new Array();
    // 当前的级联选择
    var yearSelectQueue=new Array();
    var termSelectQueue=new Array();
    var depTeaSelectQueue=new Array();
    var originalOnChanges=new Array();
    // 初始化学生类别选择框
    function initCalendarStdTypeSelect(stdTypes){
        if( null==document.getElementById(this.stdTypeSelectId)) return;
        defaultValues[this.stdTypeSelectId]=document.getElementById(this.stdTypeSelectId).value;
        DWRUtil.removeAllOptions(this.stdTypeSelectId);
        DWRUtil.addOptions(this.stdTypeSelectId,stdTypes,'id','name');
        if(this.stdTypeNullable){
            DWRUtil.addOptions(this.stdTypeSelectId,[{'id':'','name':'...'}],'id','name');
        }
        // 让非空的学生类别作为默认
        if(""!=defaultValues[this.stdTypeSelectId])        
	        setSelected(document.getElementById(this.stdTypeSelectId),defaultValues[this.stdTypeSelectId]);
       
        var selfOnchange =document.getElementById(this.stdTypeSelectId).onchange; 
        document.getElementById(this.stdTypeSelectId).onchange=function (){
	        notifyYearChange();
	        notifyDepTeaChange();
	        if(selfOnchange!=null){selfOnchange();}
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
          document.getElementById(this.yearId).onchange=notifyTermChange;
       }else{
           originalOnChanges[originalOnChanges.length]=originalOnChange;
           document.getElementById(this.yearId).onchange=
           new Function("notifyTermChange();originalOnChanges["+(originalOnChanges.length-1)+"]();");
       }
    }
    // 通知学年度变化,填充学年度选择列表
    function notifyYearChange(){
       //alert("event in notifyYearChange");
       myDutyTeachTaskSelects= getMyCalendarSelects(event.srcElement.id);

       for(var i=0;i<myDutyTeachTaskSelects.length;i++){
	       var s= document.getElementById(myDutyTeachTaskSelects[i].stdTypeSelectId);	       
	       if(null==s) continue;
	       DWRUtil.removeAllOptions(myDutyTeachTaskSelects[i].yearId);
	       if(s.value!=""){
	           yearSelectQueue.push(myDutyTeachTaskSelects[i]);
		       calendarDAO.getYearsOrderByDistance(setYearOptions,s.value);
	       }else{
	          DWRUtil.removeAllOptions(myDutyTeachTaskSelects[i].termId);
	       }
       }
    }
	function notifyDepTeaChange(){
		myDutyTeachTaskSelects= getMyCalendarSelects(event.srcElement.id);
		for(var i=0;i<myDutyTeachTaskSelects.length;i++){
	       var s= document.getElementById(myDutyTeachTaskSelects[i].stdTypeSelectId);	       
	       if(null==s) continue;
	       DWRUtil.removeAllOptions(myDutyTeachTaskSelects[i].courseTypeId);
	       DWRUtil.removeAllOptions(myDutyTeachTaskSelects[i].studyDepId);
	       DWRUtil.removeAllOptions(myDutyTeachTaskSelects[i].teachDepId);
	       DWRUtil.removeAllOptions(myDutyTeachTaskSelects[i].teacherId);
	       if(s.value!=""){
	           depTeaSelectQueue.push(myDutyTeachTaskSelects[i]);
		       DutyRecordManager.searchTeachTaskForDuty(s.value,setCouDepTea);
	       }else{
	          
	       }
       }
	}
    function setCouDepTea(dataMap){
       var curTaskSelect=depTeaSelectQueue.shift();
       if(null!=curTaskSelect){//,studyDepNullable,teachDepNullable,teacherNullable
	       for(var key in dataMap){
			   if(curTaskSelect[key+'Nullable']){
		           DWRUtil.addOptions(curTaskSelect[key+'Id'],[{'id':'','name':'...'}],'id','name');	           
		       }
		       DWRUtil.addOptions(curTaskSelect[key+'Id'],dataMap[key],'id','name');
		       if(defaultValues[curTaskSelect[key+'Id']]!=null){
		           setSelected(document.getElementById(curTaskSelect[key+'Id']),defaultValues[curTaskSelect[key+'Id']]);
		       }
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
	       if(defaultValues[curCalendarSelect.yearId]!=null){
	           setSelected(document.getElementById(curCalendarSelect.yearId),defaultValues[curCalendarSelect.yearId]);
	       }
	       notifyTermChange(curCalendarSelect.yearId);
       }
    }
    // 通知学期变化，填充学期列表
    function notifyTermChange(selectId){
       //alert("event in notifyTermChange");
       if(null==selectId) {
         selectId=event.srcElement.id;
       }
       myDutyTeachTaskSelects= getMyCalendarSelects(selectId);
       for(var i=0;i<myDutyTeachTaskSelects.length;i++){
           //alert("removeAllOptions of :"+myDutyTeachTaskSelects[i].termId);
	       DWRUtil.removeAllOptions(myDutyTeachTaskSelects[i].termId);	       
	       var s= document.getElementById(myDutyTeachTaskSelects[i].stdTypeSelectId);
	       var y= document.getElementById(myDutyTeachTaskSelects[i].yearId);
	       
	       if(s.value!=""&&y.value!=null){
  	          termSelectQueue.push(myDutyTeachTaskSelects[i]);
	          //alert(curCalendarSelect.termId)
	          calendarDAO.getTermsOrderByDistance(setTermOptions,s.value,y.value);       
	       }else{
	          DWRUtil.removeAllOptions(myDutyTeachTaskSelects[i].termId);
	       }
       }
    }
    function setTermOptions(data){
       //alert(data)
       var curCalendarSelect=termSelectQueue.shift();
       if(null!=curCalendarSelect){
           DWRUtil.removeAllOptions(curCalendarSelect.termId);
           if(curCalendarSelect.termNullable){
              //alert("add null term");
              DWRUtil.addOptions(curCalendarSelect.termId,[{'id':'','name':'...'}],'id','name');
           }
	       DWRUtil.addOptions(curCalendarSelect.termId,data);           
	       if(defaultValues[curCalendarSelect.termId]!=null){           
	           setSelected(document.getElementById(curCalendarSelect.termId),defaultValues[curCalendarSelect.termId]);
	       }
       }
    }
    function DutyTeachTaskSelect(stdTypeSelectId,yearId,termId,courseTypeId,studyDepId,teachDepId,teacherId,stdTypeNullable,yearNullable,termNullable,courseTypeNullable,studyDepNullable,teachDepNullable,teacherNullable){
     // alert(stdTypeSelectId+":"+stdTypeSelectId+":"+yearId+":"+yearId);
      this.stdTypeSelectId=stdTypeSelectId;
      this.yearId=yearId;
      this.termId=termId;
      this.courseTypeId=courseTypeId;
      this.studyDepId=studyDepId;
      this.teachDepId=teachDepId;
      this.teacherId=teacherId;
      this.stdTypeNullable=stdTypeNullable;
      this.yearNullable=yearNullable;
      this.termNullable=termNullable;
      this.courseTypeNullable=courseTypeNullable;
      this.studyDepNullable=studyDepNullable;
      this.teachDepNullable=teachDepNullable;
      this.teacherNullable=teacherNullable;
      this.initStdTypeSelect=initCalendarStdTypeSelect;
      
      this.initYearSelect=initYearSelect;
      this.init=initCalendar;
      this.getDefaultValues=getDefaultValues;
      dutyTeachTaskSelects[dutyTeachTaskSelects.length]=this;
      this.getDefaultValues(); 
  
    }
    function initCalendar(stdTypes){  
       this.initStdTypeSelect(stdTypes);       
       this.initYearSelect();         
    }
    function getMyCalendarSelects(id){
        var myDutyTeachTaskSelects = new Array();
        for(var i=0;i<dutyTeachTaskSelects.length;i++){
            if(dutyTeachTaskSelects[i].stdTypeSelectId==id||
               dutyTeachTaskSelects[i].yearId==id||
               dutyTeachTaskSelects[i].termId==id)
               myDutyTeachTaskSelects[myDutyTeachTaskSelects.length]=dutyTeachTaskSelects[i];
        }
        return myDutyTeachTaskSelects;
    }
    /**
     * 获得缺剩值
     */
    function getDefaultValues(){
       defaultValues[this.stdTypeSelectId]=document.getElementById(this.stdTypeSelectId).value;
       defaultValues[this.yearId]=document.getElementById(this.yearId).value;
       defaultValues[this.termId]=document.getElementById(this.termId).value;
       defaultValues[this.courseTypeId]=document.getElementById(this.courseTypeId).value;
       defaultValues[this.studyDepId]=document.getElementById(this.studyDepId).value;
       defaultValues[this.teachDepId]=document.getElementById(this.teachDepId).value;
       defaultValues[this.teacherId]=document.getElementById(this.teacherId).value;
    }    
