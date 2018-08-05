     var collision=0;
     //查询结果是否可以多选
     var multi=false;
     //是否查询区分权限
     var withAuthority=true;
     
     function checkExist(form){
         var enrollTurn = form['teachPlan.enrollTurn'].value;
         var stdTypeId=form['teachPlan.stdType.id'].value;
         var departmentId = form['teachPlan.department.id'].value;
         var specialityId=(""==form['teachPlan.speciality.id'].value)?null:form['teachPlan.speciality.id'].value;
         var aspectId = (""==form['teachPlan.aspect.id'].value)?null:form['teachPlan.aspect.id'].value;
         return teachPlanService.isExist(afterCheck,enrollTurn,stdTypeId,departmentId,specialityId,aspectId,null);
     }
     function afterCheck(data){
          var checkMessage= document.getElementById("error");
	  	  if(data==true){
	  	  	checkMessage.innerHTML='<font color="red" size="2">系统中已经存在更改后的培养计划了。不能重复制定！</font>';
	  	  	collision=1;
	  	  }
	  	  else{
  	  	  	checkMessage.innerHTML="";
	  	  	collision=0;
	  	  }
      }
    /**
     * 获得选中的培养计划id
     */
    function getIds(){
       return(getCheckBoxValue(planListFrame.document.getElementsByName("teachPlanId")));
    }
    //确认返回"1",否则返回"0"
    function getConfirmState(planId){
       if(planId.indexOf(",")!=-1){
           var planIds = planId.split(",");
           for(var i=0;i<planIds.length;i++){
               if (planListFrame.document.getElementById(planIds[i]).value == "1") {
                   return "1";
               }
            }
            return "0";
           
       }else{
           return planListFrame.document.getElementById(planId).value;
       }
    }
    /**
     * 查找培养计划
     * @param multi表示返回的列表是否允许多选('true'或者'false')
     * @see withAuthority
     */
    function searchTeachPlan(pageNo,pageSize,orderBy){
       var form = document.planSearchForm;
       if(withAuthority){
          form.action="teachPlan.do?method=search";
       }else{
          form.action="teachPlanSearch.do?method=search";
       }
       
       if(multi){
         form.action+="&multi=1";
       }else{
         form.action+="&multi=0";
       }
       form.target="planListFrame";
       var params =getInputParams(form,"teachPlan");
       if(form['type'].checked){
          params+="&type="+form['type'].value;
       }
       addInput(form,"params",params);
       goToPage(form,pageNo,pageSize,orderBy);
    }
    function search(event) {
        if (portableEvent(event).keyCode == 13){
            //因下句致不能偏好设置影响
            //$('pageNo').value="1";
            searchTeachPlan();
        }
    }
    /**
     * 更改培养计划的确认状态
     * isConfirm 表示是否改成'1'或者'0'
     */
    function updateConfirmState(isConfirm){
       var planIds = getIds();
       if(!checkId(planIds,true,'请选择一个培养计划,改变确认状态!')) return; 
       else {
         var form = document.planSearchForm;
         form.action="teachPlanConfirm.do?method=confirm&teachPlanIdSeq=" +planIds+"&isConfirm="+isConfirm+"&multi=true";
         form.target="planListFrame";
       	 form.submit();
       }
    }
    /**
     * 新建一个培养计划
     */
    function newTeachPlan(){
       var form = document.planSearchForm;
       form.action="teachPlan.do?method=newPlan";
       //form.target="";
       form.submit();
    }
    /**
     * 修改一个培养计划
     */
    function editTeachPlan(){
       var planId = getIds();
       if(!checkId(planId,false,'请选择一个培养计划,进行修改!')) return;
       else {
         if(getConfirmState(planId)=="1"){
            alert("培养计划已经确认，暂时不能修改。\n如需修改，请在[培养计划确认管理]菜单中先取消确认");
            return;
         }
         var form = document.planSearchForm;
         form.action="teachPlan.do?method=edit&teachPlan.id=" +planId;
         //form.target="";
       	 form.submit();
       }
    }
    /**
     * 删除培养计划
     */
    function removeTeachPlan(){
       var planId = getIds();
       if(!checkId(planId,false,'请选择一个培养计划,进行删除!')) return;
       else {
         if(getConfirmState(planId)=="1"){
            alert("培养计划已经确认，暂时不能删除。\n如需删除，请在[培养计划确认管理]菜单中先取消确认");
            return;
         }
         if(confirm("删除培养计划，将使对应的学生在毕业审核等各个业务中失去参照，确认删除？")){
	         var form = document.planSearchForm;
	         addInput(form,"params",getInputParams(form));
	         form.action="teachPlan.do?method=remove&teachPlan.id=" +planId;
	         form.target="planListFrame";
	       	 form.submit();
       	 }
       }
    }
    /**
     * 生成培养计划
     * planType取值为"speciality"和"std"
     */
    function genTeachPlan(planType){
       var planId = getIds();
       if(!checkId(planId,false,'生成培养计划需按照既有的培养计划生成，请选择一个')) return;
       var form = document.planSearchForm;
       form.target="planListFrame";
       form.action="teachPlan.do?method=genPompt&genPlanType="+planType+"&targetPlan.id="+planId;
       form.submit();
    }
    /**
     * 批量生成培养计划
     * 
     */
    function batchGenTeachPlan(){
       var planId = getIds();
       if(!checkId(planId,true,'批量生成培养计划需按照既有的培养计划生成，请选择一个或多个')) return;
       var enrollTurn =null;
       do{
           enrollTurn=prompt("请输入批量生成的入学年月，已经存在的对应计划的将忽略不生成。入学年月的格式yyyy-m,如2007-1","-1");
           if(enrollTurn!=null){
               if(!/^\d{4}-[1-9]$/.test(enrollTurn)) continue;
               break;
           }
       }while(enrollTurn!=null)
       if(null==enrollTurn) return;
       var form = document.planSearchForm;
       form.target="planListFrame";
       form.action="teachPlan.do?method=batchGen&genPlanType=speciality&planIdSeq="+planId+"&enrollTurn="+enrollTurn;
       form.submit();
    }
    
    /**
     * 查看详细信息
     */
    function getPlanInfo(){
       var planId = getIds();
       if(!checkId(planId,false,'请选择一个查看详细信息')) return;
       var form = document.planSearchForm;
       form.target="planListFrame";
       form.action="teachPlanSearch.do?method=info&teachPlan.id="+planId;
       form.submit();
    }
    /**
     * 检查培养计划的planId
     */
    function checkId(planId,needMulti,msg){
       if(planId==""){
	       window.alert(msg);
	       return false;
       }
       if(!needMulti && isMultiId(planId)){
          window.alert('请仅选择一个培养计划!');
          return false;
       }
       return true;
    }
    //现已在详细信息中增加了简单的导出功能。这里的导出不再使用了
    function exportData(){
       var planIds = getIds();
       if(""==planIds){
         alert("请选择一个或多个计划");return;
       }
       var form = document.planSearchForm;
		var titles = "入学年份,专业,专业方向,课程代码,课程名称,学分,开课学期";
		form.action = "teachPlan.do?method=export&planIds=" + planIds;
		addInput(form, "titles", titles, "hidden");
		form.submit();
    }
    function changeMajorType(event){
	    var select = getEventTarget(event);
	    sds.majorTypeId=select.value;
	    fireChange($("stdTypeOfSpeciality"));
    }
