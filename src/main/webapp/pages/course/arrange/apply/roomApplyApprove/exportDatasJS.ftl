	    function exportData() {
	    	addInput(form, "keys", "activityName,activityType.name,isFree,leading,attendance,attendanceNum,schoolDistrict.name,roomRequest,applicant,applyAt,applyTime,auditDepart.name,isDepartApproved,departApproveBy.userName,departApproveAt,isApproved,approveBy.userName,approveAt,hours,money,waterFee,isMultimedia,classroomNames,classroomTypeNames,capacityOfCourse");
	    	addInput(form, "titles", "活动名称,活动类型,非营利性,主讲人及内容,出席对象,出席人数,借用场所要求,借用校区,申请人签名,提交申请时间,申请占用时间,归口部门,归口审核,归口审核人,归口审核时间,物管审核,物管审核人,物管审核时间,时间(小时数),费用,茶水费,是否使用多媒体设备,批准教室,教室类型,容纳听课人数");
	    	form.action ="?method=export";
	    	addHiddens(form, queryStr);
	    	form.submit();
	    }