    var teaching=[null,true,false];
    //页面上的所有部门教师的二级选择
    var departTeacherSelects=new Array();
    //异步操作中的教师选择缓冲区
    var teacherSelectIds=new Array();
    
    //初始化教师所在部门
    function initTeachDepartSelect(departs){
	    DWRUtil.removeAllOptions(this.teachDepartSelectId);    
	    DWRUtil.addOptions(this.teachDepartSelectId,departs,'id','name');
	    document.getElementById(this.teachDepartSelectId).onchange=function (event){notifyTeacherChange(event)};
	    setSelected(document.getElementById(this.teachDepartSelectId),this.defaultTeachDepartId);
    }
    //初始化教师列表
    function initTeacherSelect(){
       DWRUtil.removeAllOptions(this.teacherSelectId);
       if(this.teacherIdNullable){
           DWRUtil.addOptions(this.teacherSelectId,[{'id':'','name':'请选择...'}],'id','name');
       }
       var s= document.getElementById(this.teachDepartSelectId);
       if(s.value!=""){
          teacherSelectIds.push(this.teacherSelectId);
          //alert(teacherSelectIds+"after init push");
          teacherDAO.getTeacherNamesByDepart(setTeacherOptions,s.value,teaching[this.isTeaching]);
       }
    }
    //通知教师列表
    function notifyTeacherChange(event){
       var curDepartTeacherSelect= getCurDepartTeacherSelect(getEventTarget(event).id);
       if(null==curDepartTeacherSelect)return;
       DWRUtil.removeAllOptions(curDepartTeacherSelect.teacherSelectId);
       if(curDepartTeacherSelect.teacherIdNullable){
           DWRUtil.addOptions(curDepartTeacherSelect.teacherSelectId,[{'id':'','name':'请选择...'}],'id','name');
       }
       var s= document.getElementById(curDepartTeacherSelect.teachDepartSelectId);
       if(s.value!=""){
          teacherSelectIds.push(curDepartTeacherSelect.teacherSelectId);
          //alert(teacherSelectIds+"after notifyTeacherChange push");
          teacherDAO.getTeacherNamesByDepart(setTeacherOptions,s.value,teaching[curDepartTeacherSelect.isTeaching]);
       }
    }
    //得到现有的教师和部门选择
    function getCurDepartTeacherSelect(departId){
        for(var i=0;i<departTeacherSelects.length;i++){
            if(departTeacherSelects[i].teachDepartSelectId==departId)
               return departTeacherSelects[i];
        }
        return null;
    }
    function getCurDepartTeacherSelectByTeacherSelectId(teacherSelectId){
        for(var i=0;i<departTeacherSelects.length;i++){
            if(departTeacherSelects[i].teacherSelectId==teacherSelectId)
               return departTeacherSelects[i];
        }
        return null;
    }
    // 设置教师数据
    function setTeacherOptions(data){
       var teacherSelectId=teacherSelectIds.shift();
       //alert(teacherSelectIds+"after shift");
       for(var i=0;i<data.length;i++){
           DWRUtil.addOptions(teacherSelectId,[{'id':data[i][0],'name':data[i][1] + " [" + data[i][2] + "]"}],'id','name');
       }
       var defaultTeacherId=getCurDepartTeacherSelectByTeacherSelectId(teacherSelectId).defaultTeacherId
       if(""!=defaultTeacherId){
          setSelected(document.getElementById(teacherSelectId),defaultTeacherId);
       }
    }
    
    /**
     *部门教师二级选择
     *@param isTeaching 0表示不限制,1表示任课,2表示不任课 默认是1
     */
    function DepartTeacher2Select(teachDepartSelectId,teacherSelectId,teachDepartNullable,teacherIdNullable,isTeaching){
        this.teachDepartSelectId=teachDepartSelectId;
        this.teacherSelectId=teacherSelectId;
        if(null!=teacherSelectId){
           this.defaultTeacherId=document.getElementById(teacherSelectId).value;
        }else{
           this.defaultTeacherId="";
        }
        if(null!=teachDepartSelectId){
          this.defaultTeachDepartId=document.getElementById(teachDepartSelectId).value;
        }else{
          this.defaultTeachDepartId="";
        }
        this.teachDepartNullable=teachDepartNullable;
        this.teacherIdNullable=teacherIdNullable;
        this.initTeachDepartSelect=initTeachDepartSelect;
        this.initTeacherSelect=initTeacherSelect;
        this.isTeaching=isTeaching;
        if(null==this.isTeaching){
           this.isTeaching=1;
        }
        departTeacherSelects[departTeacherSelects.length]=this;
        //alert(departTeacherSelects);
    }