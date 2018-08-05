/**
	 * 显示部门列表
	 */
	function displayDepartList(e){
	    var departSelect = getEventTarget(e);
	    if(departSelect.tagName!="SELECT") return;
	    if(departSelect.options.length<=1){
	        for(var i=0;i<departmentList.length;i++){
	            if(departmentList[i].id==departSelect.value)continue;
	            departSelect.options[departSelect.options.length]=new Option(departmentList[i].name,departmentList[i].id);
	        }
        }
 	}
	var teacherMap= new Object();
    var curTeacherSelectId=null;
    /**
     * 显示教师列表
     */
    function displayTeacherList(teachSelectId,departId,refresh){
        var teacherSelect =document.getElementById(teachSelectId);
        if(teacherMap[departId]==null){
            curTeacherSelectId=teachSelectId;
            curDepartSelectId=departId;
            teacherDAO.getTeacherNamesByDepart(setTeacherOptions,departId,true);
        }
        if(refresh){
            teacherSelect.options.length=0;
	        if(teacherMap[departId]!=null){
		        for(var i=0;i<teacherMap[departId].length;i++){
   		            var teacherId =teacherMap[departId][i].id;
		       		teacherSelect.options[teacherSelect.options.length]=new Option(teacherMap[departId][i].name,teacherId);
		        }
	        }
	        
	    }//如果下拉框中是教学任务原有的老师
	    else if(teacherSelect.options.length<=1){
            if(teacherMap[departId]!=null){
		        for(var i=0;i<teacherMap[departId].length;i++){
		            if(""!=teacherMap[departId][i].id&&teacherSelect.options[0].value.indexOf(teacherMap[departId][i].id)!=-1) continue;
		            var teacherId =teacherMap[departId][i].id;
		       		teacherSelect.options[teacherSelect.options.length]=new Option(teacherMap[departId][i].name,teacherId);
		        }
	        }
        }
    }
    
    // 设置教师数据
    function setTeacherOptions(data){
       if(null==teacherMap[curDepartSelectId])
          teacherMap[curDepartSelectId]=new Array();
       teacherMap[curDepartSelectId][0]={'departId':curDepartSelectId,'id':'','name':'请选择..'};
       DWRUtil.addOptions(curTeacherSelectId,[{'id':'','name':'请选择..'}],'id','name');
       for(var i=0;i<data.length;i++){
           
           DWRUtil.addOptions(curTeacherSelectId,[{'id':data[i][0],'name':data[i][1]+'('+data[i][2]+')'}],'id','name');
           teacherMap[curDepartSelectId][i+1]={'departId':curDepartSelectId,'id':data[i][0],'name':data[i][1]+'('+data[i][2]+')'};
       }
    }
    