 	<table>
 		<tr valign="top">
 			<td width="24%">
 		<form method="post" action="" name="feeDefaultConfigForm" onsubmit="return false;">
	 			<fieldSet align=left> 
			 		<legend style="font-weight:bold;font-size:12px">配置收费栏目</legend>
                	<table width="100%" id ="menuTable" style="font-size:10pt">
                		<tr></tr>
	                    <tr>
	                        <td class="padding" id="defaultPage" onclick="toDefaultPage(this);doFeeDafaultAction();" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
	                            <image src="${static_base}/images/action/list.gif" align="bottom"/>配置收费默认值
	                        </td>
	                    </tr>
	                    <tr>
	                        <td class="padding" id="creditPage" onclick="toCreditPage(this);doCreditFeeAction()" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
	                            <image src="${static_base}/images/action/list.gif" align="bottom"/>学分收费标准
	                        </td>
	                    </tr>
	            	</table>
	 			</fieldSet>
	 		</td>
	 	</tr>
	 	<@searchParams/>
 		<tr>
 			<td>
 				<table width="100%">
	 	        <tr valign="top">
	 		    <td>
 				<fieldSet align=left> 
		 			<legend style="font-weight:bold;font-size:12px">配置收费查询范围</legend>
						<table class="searchTable" width="100%">
					       	<tr> 
						     	<td class="infoTitle"><@bean.message key="entity.studentType"/>:</td>
						     	<td>
						          	<select id="stdTypeOfSpeciality" name="stdType.id" style="width:100px;">               
						            	<option value=""><@bean.message key="filed.choose"/></option>
						          	</select>	 
					         	</td>
							</tr>
						</table>
		 				<div id="viewFee" style="display:block">
						<table class="searchTable" width="100%">
					    	<tr>
						     	<td class="infoTitle"><@bean.message key="common.college"/>:</td>
						     	<td>
						           	<select id="department" name="default.department.id" style="width:100px;">
						         	  	<option value=""><@bean.message key="filed.choose"/>...</option>
						           	</select>            
					         	</td>       
					        </tr>
						   	<tr>
						     	<td class="infoTitle"><@bean.message key="entity.speciality"/>:</td>
						     	<td>
					           		<select id="speciality" name="default.speciality.id" style="width:100px;">
					         	  		<option value=""><@bean.message key="filed.choose"/>...</option>
					           		</select>     	             
					         	</td>
					        </tr>
				 			<tr>
			 					<td><@msg.message key="field.feeDetail.feeTypeOfFee"/></td>
			 					<td>
			 						<select id="feeTypeId" name="default.type.id" value="${RequestParameters['feeDefault.type.id']?default('')}" style="width:100px;">
			 							<#list feeTypes as feeType><option value="${feeType.id}">${feeType.name}</option></#list>
			 							<option>...</option>
			 						</select>
			 					</td>
			 				</tr>
			 				<tr>
			 					<td colspan="2" align="center"><button onclick="doFeeDafaultAction()">查询当前栏目</button></td>
			 				</tr>
			 			</table>
		    	        <#include "/templates/stdTypeDepart2Select.ftl"/>
						</div>
						<div id="viewCredit" style="display:none">
			 			<table class="searchTable" width="100%">
			 				<tr>
			 					<td align="center"><button onclick="doCreditFeeAction()">查询当前栏目</button></td>
			 				</tr>
						</table>
						</div>
					</fieldSet>
			    </td>
		    </tr>
 				</table>
 			</td>
 		</tr>
    	</form>
	</table>
