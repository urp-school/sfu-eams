    /*******************************************************
    *   学生类别和部门,专业,方向的三级级联下拉框
    *   用法:
    *    //准备学生类别{id,name}
    *    var stdTypeArray = new Array(); 
    *    //准备部门{id,name}
    *    var departArray = new Array();
    *    var dd = new StdTypeDepart3Select("stdTypeOfSpeciality","department","speciality","specialityAspect",false,true,true,true);    
    *    dd.init(stdTypeArray,departArray);
    *
    *******************************************************/
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
        if (null==document.getElementById(this.stdTypeSelectId)) {
            return;
        }
        defaultValues[this.stdTypeSelectId]=document.getElementById(this.stdTypeSelectId).value;
        DWRUtil.removeAllOptions(this.stdTypeSelectId);
        DWRUtil.addOptions(this.stdTypeSelectId,stdTypes,'id','name');
        if(this.stdTypeNullable){
            DWRUtil.addOptions(this.stdTypeSelectId,[{'id':'','name':'...'}],'id','name');
        }
        for (var i = 0; i < document.getElementById(this.stdTypeSelectId).options.length; i++) {
            document.getElementById(this.stdTypeSelectId).options[i].title = document.getElementById(this.stdTypeSelectId).options[i].text;
        }
        setSelected(document.getElementById(this.stdTypeSelectId),defaultValues[this.stdTypeSelectId]);
        
        if(null != document.getElementById(this.departSelectId)&&null != document.getElementById(this.specialitySelectId)){
           document.getElementById(this.stdTypeSelectId).onchange=function (event){
            notifySpecialityChange(event);
            notifyAspectChange(event);
           } 
        }
    }
    // 初始化部门初始化
    function initDepartSelect(departs){
        if(!document.getElementById(this.departSelectId))return;
        defaultValues[this.departSelectId]=document.getElementById(this.departSelectId).value;
        DWRUtil.removeAllOptions(this.departSelectId);
        DWRUtil.addOptions(this.departSelectId,departs,'id','name');
        if (this.departNullable) {
            DWRUtil.addOptions(this.departSelectId,[{'id':'','name':'...'}],'id','name');
        }
        setSelected(document.getElementById(this.departSelectId),defaultValues[this.departSelectId]);
        document.getElementById(this.departSelectId).onchange =function (event){
            notifySpecialityChange(event);
            notifyAspectChange(event);
        }
        document.getElementById(this.departSelectId).style.width= defaultWidth();
    }
    // 初始化专业选择框
    function initSpecialitySelect(){
       if(!document.getElementById(this.specialitySelectId))return;
       defaultValues[this.specialitySelectId]=document.getElementById(this.specialitySelectId).value;
       DWRUtil.removeAllOptions(this.specialitySelectId);
       document.getElementById(this.specialitySelectId).onchange=notifyAspectChange;
       
       var std= document.getElementById(this.stdTypeSelectId);
       var d = document.getElementById(this.departSelectId); 

       if(this.specialityNullable)
           DWRUtil.addOptions(this.specialitySelectId,[{'id':'','name':'...'}],'id','name');
       if(std.value!=""&&d.value!=""){
            stdTypeDepart3Select=this;
            specialityDAO.getSpecialityNames(setSpecialityOptions,d.value,std.value,this.majorTypeId);
       }
       document.getElementById(this.specialitySelectId).style.width = defaultWidth();
       //setSelected(document.getElementById(this.specialitySelectId),defaultValues[this.specialitySelectId]);
    }
    // 初始化专业方向选择框
    function initAspectSelect(){
       if(!document.getElementById(this.aspectSelectId))return;
       defaultValues[this.aspectSelectId]=document.getElementById(this.aspectSelectId).value;
       DWRUtil.removeAllOptions(this.aspectSelectId);
       var s= document.getElementById(this.specialitySelectId);
        if(this.aspectNullable)
           DWRUtil.addOptions(this.aspectSelectId,[{'id':'','name':'...'}],'id','name');
       if(s.value!=""){
           stdTypeDepart3Select=this;
           //specialityAspectDAO.getSpecialityAspectNames(setAspectOptions,s.value);
           getAdminClass();
        }
        document.getElementById(this.aspectSelectId).style.width = defaultWidth();
       //setSelected(document.getElementById(this.aspectSelectId),defaultValues[this.aspectSelectId]);
    }
    // 通知专业变化,填充专业选择列表
    function notifySpecialityChange(event){
       var target=  getEventTarget(event);
       //alert("event in notifySpecialityChange");
       mySelects= getMySelects(target.id);
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
               specialityDAO.getSpecialityNames(setSpecialityOptions,d.value,s.value,mySelects[i].majorTypeId);
           }
           // 过上半秒钟再去查找专业方向，以防专业查找还没有完成
           //setTimeout(notifyAspectChange,"500");
       }
      // DWREngine.setAsync(true);
    }
    // 通知专业方向变化，填充专业方向列表
    function notifyAspectChange(event){
       var target=  getEventTarget(event);
       //alert("event in notifyAspectChange");
       mySelects= getMySelects(target.id);
       for(var i=0;i<mySelects.length;i++){
           //alert("removeAllOptions of :"+mySelects[i].aspectSelectId);
           DWRUtil.removeAllOptions(mySelects[i].aspectSelectId);
           if(mySelects[i].aspectNullable)
               DWRUtil.addOptions(mySelects[i].aspectSelectId,[{'id':'','name':'...'}],'id','name');
           var s= document.getElementById(mySelects[i].specialitySelectId);
           if(s.value!=""){
              stdTypeDepart3Select=mySelects[i];
              //alert(stdTypeDepart3Select.aspectSelectId)
              //specialityAspectDAO.getSpecialityAspectNames(setAspectOptions,s.value);
              getAdminClass();
           }
       }
    }
    function setSpecialityOptions(data){
       for(var i=0;i<data.length;i++)
           DWRUtil.addOptions(stdTypeDepart3Select.specialitySelectId,[{'id':data[i][0],'name':data[i][1]}],'id','name');
       if(defaultValues[stdTypeDepart3Select.specialitySelectId]!=null){
           setSelected(document.getElementById(stdTypeDepart3Select.specialitySelectId),defaultValues[stdTypeDepart3Select.specialitySelectId]);
       }
    }
    function setAspectOptions(data){
       for(var i=0;i<data.length;i++)
          DWRUtil.addOptions(stdTypeDepart3Select.aspectSelectId,[{'id':data[i][0],'name':data[i][1]}],'id','name');
       if(defaultValues[stdTypeDepart3Select.aspectSelectId]!=null){
           setSelected(document.getElementById(stdTypeDepart3Select.aspectSelectId),defaultValues[stdTypeDepart3Select.aspectSelectId]);
       }
    }
    /**
     *  三级级联选择的模型
     *@param stdTypeSelectId 学生类别下拉框的id
     *@param departSelectId 部门下拉框id
     *@param specialitySelectId 专业下拉框id
     *@param aspectSelectId 方向下拉框id
     *@param stdTypeNullable 学生类别是允许为空
     *@param departNullable 部门是否允许为空
     *@param specialityNullable 专业是否允许为空
     *@param aspectNullable 专业方向是否为空
     *@param majorTypeId 专业类别是否是第一专业 0表示不限制,1表示一专业,2表示二专业 默认null
     */
    function StdTypeDepart3Select(stdTypeSelectId,departSelectId,specialitySelectId,aspectSelectId,stdTypeNullable,departNullable,specialityNullable,aspectNullable,majorTypeId){
     // alert(stdTypeSelectId+":"+departSelectId+":"+specialitySelectId+":"+aspectSelectId);
      this.stdTypeSelectId=stdTypeSelectId;
      this.departSelectId=departSelectId;
      this.specialitySelectId=specialitySelectId;
      this.aspectSelectId=aspectSelectId;
      this.stdTypeNullable=stdTypeNullable;
      this.departNullable=departNullable;
      this.specialityNullable=specialityNullable;
      this.aspectNullable=aspectNullable;
      this.initStdTypeSelect=initStdTypeSelect;
      this.initDepartSelect=initDepartSelect;
      this.initSpecialitySelect=initSpecialitySelect;
      this.initAspectSelect=initAspectSelect;
      this.init=init;
      selects[selects.length]=this;
      this.majorTypeId=majorTypeId;
    }
    function initSpecialityAndAspectSelect(){
      stdTypeDepart3Select.initAspectSelect();
    }
    function init(stdTypes,departs){
       this.initStdTypeSelect(stdTypes);
       this.initDepartSelect(departs);
       this.initSpecialitySelect();
       stdTypeDepart3Select=this;
       // 留上200毫秒等待专业初试化完毕
       setTimeout(initSpecialityAndAspectSelect,"200");
    }
    function getMySelects(id){
        var mySelects = new Array();
        for(var i=0;i<selects.length;i++){
            if(selects[i].stdTypeSelectId==id||
               selects[i].departSelectId==id||
               selects[i].specialitySelectId==id||
               selects[i].aspectSelectId==id)
               mySelects[mySelects.length]=selects[i];
        }
        return mySelects;
    }
    
    function defaultWidth() {
        return typeof(inputElementWidth) == "undefined" || null == inputElementWidth || "" == inputElementWidth ? "100px" : inputElementWidth;
    }