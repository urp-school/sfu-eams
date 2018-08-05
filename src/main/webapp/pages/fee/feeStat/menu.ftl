 	<table>
 		<tr valign="top">
 			<td width="24%">
	 			<fieldSet align=left> 
			 		<legend style="font-weight:bold;font-size:12px">缴费统计栏目</legend>
	 				<table id="menuTable" width="100%" style="font-size:10pt">
 						<tr></tr>
	                    <tr height="25">
	                        <td class="padding" id="statFeeType" onclick="selectFrame(this, 'statFeeType')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
	                            &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif" align="absmiddle"/>应-实 缴费不符统计
	                        </td>
	                    </tr>
	                    <tr height="25">
	                        <td class="padding" id="creditFeeStats" onclick="selectFrame(this, 'creditFeeStats')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
	                            &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif" align="absmiddle"/>学费－学分 不符统计
	                        </td>
	                    </tr>
	 				</table>
	 			</fieldSet>
	 		</td>
	 	</tr>
	 	<tr valign="top">
	 		<td>
	 			<#--应-实 缴费不符统计-->
	 			<div id="viewFee" style="display:block">
 				<fieldSet align=left> 
		 			<legend style="font-weight:bold;font-size:12px">应-实 缴费不符统计范围</legend>
						<table class="searchTable" width="100%">
						   	<tr>
						     	<td class="infoTitle">所在年级:</td>
						     	<td><input type="text" name="feeDetail.std.enrollYear" style="width:100px;" maxlength="7"></td>
						   	</tr>
					       	<tr> 
						     	<td class="infoTitle"><@bean.message key="entity.studentType"/>:</td>
						     	<td>
						          	<select id="fee_std_stdTypeOfSpeciality" name="feeDetail.std.type.id" style="width:100px;">               
						            	<option value=""><@bean.message key="filed.choose"/></option>
						          	</select>
					         	</td>
							</tr>
					    	<tr>
						     	<td class="infoTitle"><@bean.message key="common.college"/>:</td>
						     	<td>
						           	<select id="fee_std_department" name="feeDetail.std.department.id" style="width:100px;">
						         	  	<option value=""><@bean.message key="filed.choose"/>...</option>
						           	</select>
					        	</td>       
					        </tr>
						   	<tr>
						     	<td class="infoTitle"><@bean.message key="entity.speciality"/>:</td>
						     	<td>
					           		<select id="fee_std_speciality" name="feeDetail.std.firstMajor.id" style="width:100px;">
					         	  		<option value=""><@bean.message key="filed.choose"/>...</option>
					           		</select>     	             
					         	</td>
					        </tr>
						   	<tr>
						     	<td class="infoTitle"><@bean.message key="entity.specialityAspect"/>:</td>
						     	<td>
					           		<select id="fee_std_specialityAspect" name="feeDetail.std.firstAspect.id" style="width:100px;">
					         	  		<option value=""><@bean.message key="filed.choose"/>...</option>
					           		</select>     	             
					         	</td>
					        </tr>
					        <tr>
					        	<td><@msg.message key="common.adminClass"/>：</td>
					        	<td><input type="text" name="feeDetail_className" maxlength="20" style="width:100px;"/></td>
					        </tr>
					        <tr>
					        	<td colspan="2" align="center"><button onclick="document.getElementById('statFeeType').onclick()">查询当前统计</button></td>
					        </tr>
						</table>
				</div>
	 			<#--学费-学分 不符统计-->
	 			<div id="viewCredit" style="display:none">
 				<fieldSet align=left> 
		 			<legend style="font-weight:bold;font-size:12px">学分-学费 不符统计范围</legend>
						<table class="searchTable" width="100%">
						   	<tr>
						     	<td class="infoTitle">所在年级:</td>
						     	<td><input type="text" name="creditFeeStat.student.enrollYear" style="width:100px;" maxlength="7"></td>
						   	</tr>
					       	<tr> 
						     	<td class="infoTitle"><@bean.message key="entity.studentType"/>:</td>
						     	<td>
						          	<select id="credit_std_stdTypeOfSpeciality" name="creditFeeStat.student.type.id" style="width:100px;">               
						            	<option value=""><@bean.message key="filed.choose"/></option>
						          	</select>	 
					         	</td>
							</tr>
					    	<tr>
						     	<td class="infoTitle"><@bean.message key="common.college"/>:</td>
						     	<td>
						           	<select id="credit_std_department" name="creditFeeStat.student.department.id" style="width:100px;">
						         	  	<option value=""><@bean.message key="filed.choose"/>...</option>
						           	</select>            
					        	</td>       
					        </tr>
						   	<tr>
						     	<td class="infoTitle"><@bean.message key="entity.speciality"/>:</td>
						     	<td>
					           		<select id="credit_std_speciality" name="creditFeeStat.student.firstMajor.id" style="width:100px;">
					         	  		<option value=""><@bean.message key="filed.choose"/>...</option>
					           		</select>     	             
					         	</td>
					        </tr>
						   	<tr>
						     	<td class="infoTitle"><@bean.message key="entity.specialityAspect"/>:</td>
						     	<td>
					           		<select id="credit_std_specialityAspect" name="creditFeeStat.student.firstAspect.id" style="width:100px;">
					         	  		<option value=""><@bean.message key="filed.choose"/>...</option>
					           		</select>     	             
					         	</td>
					        </tr>
					        <tr>
					        	<td><@msg.message key="common.adminClass"/>：</td>
					        	<td><input type="text" name="creditFeeStat_className" maxlength="20" style="width:100px;"/></td>
					        </tr>
					        <tr>
					        	<td colspan="2" align="center"><button onclick="document.getElementById('creditFeeStats').onclick()">查询当前统计</button></td>
					        </tr>
						</table>
					</div>
				</fieldSet>
			</td>
		</tr>
 		<tr>
 			<td><br><br></td>
 		</tr>
 	</table>
