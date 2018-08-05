		var form = document.actionForm;
	    
	 	function checkIds(){
	 		if (getSelectIds("stdId") == "") {
	 			alert("请选择学生！");
	 			return false;
	 		}
	 		return true;
	 	}
	 	
	 	function checkAuditStandard(){
	 		if (parent.$("f_auditStandard").value == "") {
	 			alert("请选择审核标准！");
	 			return false;
	 		}
	 		return true;
	 	}
	 	
	 	function batchAutoAudit(){
	 		if (!checkAuditStandard()) {
	 			return;
	 		}
	 		if(!checkIds()) {
	 			return;
	 		}
	 		var stdIds = getSelectIds("stdId");
	 		form.action="studentAuditOperation.do?method=batchAutoAudit";
	 		form.target = "_self";
	 		addInput(form, "stdIds", stdIds, "hidden");
	 		addParamsInput(form, queryStr);
	 		form.submit();
	 	}
	 	
	 	function batchAudit(){
	 		if(!checkIds()) {
	 			return;
	 		}
	 		var stdIds = getSelectIds("stdId");
	 		form.action="studentAuditOperation.do?method=batchAudit";
	 		form.target = "_self";
	 		addInput(form, "status", "true", "hidden");
	 		addInput(form, "stdIds", stdIds, "hidden");
	 		addParamsInput(form, queryStr);
	 		form.submit();
	 	}
	 	
	 	function batchDisAudit(){ 		
	 		if(!checkIds()) {
	 			return;
	 		}
	 		var stdIds = getSelectIds("stdId");
	 		form.action="studentAuditOperation.do?method=batchAudit";
	 		form.target = "_self";
	 		addInput(form, "status", "false", "hidden");
	 		addInput(form, "stdIds", stdIds, "hidden");
	 		addParamsInput(form, queryStr);
	 		form.submit();
	 	}
	 	
	 	function batchAuditWithCondition(){
	 		if (!checkAuditStandard()) {
	 			return;
	 		}
	 		if(confirm("确定要批量审核列表中的 ${totalSize?default(0)} 位学生吗？")){
		    	form.action="studentAuditManager.do?method=batchAuditWithCondition";
		    	form.target="_blank";
		 		addHiddens(form, queryStr);
		    	form.submit();
		    }
	    }
	    
	    function showDetail(studentId,auditStandardId,auditTerm){
	    	window.open('studentAuditOperation.do?method=detail&stdId='+studentId+'&majorTypeId=1&auditStandardId='+DWRUtil.getValue(parent.$("f_auditStandard"))+'&auditTerm='+DWRUtil.getValue(parent.$('auditTerm'))+'&showBackward=false');
	    }
	    
		function changeOptionLength(obj){
			var OptionLength=obj.style.width;
			var OptionLengthArray = OptionLength.split("px");
			var oldOptionLength = OptionLengthArray[0];
			OptionLength=oldOptionLength;
			for(var i=0;i<obj.options.length;i++){
				if(obj.options[i].text==""||obj.options[i].text=="...")continue;
				if(obj.options[i].text.length*13>OptionLength){OptionLength=obj.options[i].text.length*13;}
			}
			if(OptionLength>oldOptionLength)obj.style.width=OptionLength;
		}
		
		function exportData(){
		   var stdIds = getSelectIds("stdId");
		   form.action="studentAuditManager.do?method=export";
		   addInput(form,"keys","auditResult.std.code,auditResult.std.name,auditResult.std.type.name","hidden");
		   addInput(form,"titles","学号,姓名,学生类别","hidden");
		   if(""==stdIds){
		      if(!confirm("未选中学生,系统将导出查询条件内的所有学生的上课记录?")) return;
		      else{
		         addInput(form,"stdIds",stdIds, "hidden");
		         transferParams(parent.document.pageGoForm,form,"std",false);
		         form.submit();
		      }
		   }else{
		      if(!confirm("系统将导出选中学生的上课记录?")) return;
		      else{
		      	 addInput(form,"keys","auditResult.std.code,auditResult.std.name,auditResult.std.type.name","hidden");
		  		 addInput(form,"titles","学号,姓名,学生类别","hidden");
		         addInput(form,"stdIds",stdIds,"hidden");
		         form.submit();
		      }
		   }
		}
		
		function batchAuditAffirm(affirm){
			if(!checkIds()) {
	 			return;
	 		}
	 		var stdIds = getSelectIds("stdId");
	 		form.action="studentAuditManager.do?method=batchAuditAffirm";
	 		form.target = "_self";
	 		addInput(form, "status", affirm, "hidden");
	 		addInput(form, "stdIds", stdIds, "hidden");
	 		addParamsInput(form, queryStr);
	 		form.submit();
		}
