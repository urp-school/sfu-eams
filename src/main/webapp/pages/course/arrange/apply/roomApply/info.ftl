<#include "/templates/head.ftl"/>
<#import "../cycleType.ftl" as RoomApply/>
 <BODY>
 <table id="myBar" width="100%"></table>
 <#assign cycyleNames={'1':'天','2':'周','4':'月'}/>
  <h3 align="center"><@i18nName systemConfig.school/>教室借用申请审批表 </h3>
  <table width="100%" class='listTable'>
      <tr>
        <td width="5%" rowspan="16">
            <p align="center"><strong>借</strong></p>
            <p align="center"><strong>用</strong></p>
            <p align="center"><strong>人</strong></p>
            <p align="center"><strong>填</strong></p>
            <p align="center"><strong>写</strong></p>
        </td>
        <td width="15%" rowspan="3"><strong>借用人</strong></td>
        <td width="36%">申请编号：${roomApply.id}</td>
        <td width="36%">姓名：${roomApply.borrower.user.userName}</td>
      </tr>
      <tr>
        <td width="44%">所在部门：${department?default('')}</td>
        <td width="44%">地址：${roomApply.borrower.addr}</td>      </tr>
      <tr>
        <td>手机：${roomApply.borrower.mobile}</td>
        <td>E-mail：${roomApply.borrower.email}</td>
      </tr>
      <tr>
        <td rowspan="3"><strong>借用用途、性质</strong></td>
        <td colspan="2">活动名称：${roomApply.activityName}</td>
      </tr>
      <tr>
        <td colspan="2">活动类型：<@i18nName roomApply.activityType/></td>
      </tr>
      <tr>
        <td colspan="2">是否具有营利性：${roomApply.isFree?string('非营利性','营利性')}
        <p align="left">注：借用人或借用行为中有任何收费或赞助、捐赠(含实物)等行为，则具有“营利性”；借用人或借用行为中没有任何收费或赞助、捐赠(含实物)，则具有“非营利性”。</p>
        </td>
      </tr>
      <tr>
        <td ><strong>主讲人</strong></td>
        <td colspan="2">姓名及背景资料：${roomApply.leading?default('')}</td>
      </tr>
      <tr>
        <td><strong>出席者情况</strong></td>

        <td colspan="2">出席对象：${roomApply.attendance?default('')}<br>人数：${roomApply.attendanceNum}</td>
      </tr>
      <tr>
        <td rowspan="3"><strong>借用场所要求</strong></td>
      	<td colspan="2">是否使用多媒体设备：${(roomApply.isMultimedia?string('使用','不使用'))?default("未指定")}</td>
      </tr>
      <tr>
      	<td colspan="2">借用校区：<#if (roomApply.schoolDistrict)?exists><@i18nName (roomApply.schoolDistrict)?if_exists/><#else>未指定</#if></td>
      </tr>
      <tr>
        <td colspan="2">其它要求：${roomApply.roomRequest?default('')}</td>
      </tr>
      <tr>
        <td <strong>借用时间要求</strong></td>
        <td colspan="2">${roomApply.applyTime.dateBegin?string("yyyy 年 MM 月 dd 日")}&nbsp;&nbsp;—&nbsp;&nbsp;${roomApply.applyTime.dateEnd?string("yyyy 年 MM 月 dd 日")}，<@RoomApply.cycleValue count=roomApply.applyTime.cycleCount type=roomApply.applyTime.cycleType?string/>${roomApply.applyTime.timeBegin}-${roomApply.applyTime.timeEnd} </td>
      </tr>
      <tr>
        <td rowspan="4"><strong>借用方承诺</strong></td>
        <td colspan="2">(1)遵守学校教室场所使用管理要求，保持环境整洁，不吸烟，、不乱抛口香糖等杂物。</td>

      </tr>
      <tr>
        <td colspan="2">(2)遵守学校治安管理规定，确保安全使用。若因借用人管理和使用不当造成安全事故，借用人自行承担责任。</td>
      </tr>
      <tr>
        <td colspan="2">(3)遵守学校财产物资规定，损坏设备设施按原值赔偿。</td>
      </tr>
      <tr>

        <td colspan="2">承诺上述所填情况属实签名：<strong>${roomApply.applicant} ${roomApply.applyAt?string("yyyy-MM-dd hh:mm:ss")}</strong></td>
      </tr>
      <#if roomApply.isDepartApproved?default(false)>
      <tr>
          <td colspan="2"><strong>归口部门审批</strong></td>
          <td colspan="2" align="left">
			  <p align="left">审批意见：${roomApply.isDepartApproved?default(false)?string('同意','不同意')}</p>
              <p align="left">负责部门：<@i18nName roomApply.auditDepart?if_exists/></p>
              <p align="left">负&nbsp;责&nbsp;人：${roomApply.departApproveBy.userName}</p>
              <p align="left">审批时间：${roomApply.departApproveAt?string('yyyy 年 MM 月 dd 日')}</p>
          </td>
      </tr>
      <#else>
      <tr>
          <td colspan="2"><strong>归口部门审批</strong></td>
          <td colspan="2" align="left">
			  <p align="left">审批意见：${roomApply.isDepartApproved?default(false)?string('同意','不同意')} (${roomApply.departApprovedRemark?if_exists?default('无')})</p>
              <p align="left">负责部门：<@i18nName roomApply.auditDepart?if_exists/></p>
              <p align="left">负&nbsp;责&nbsp;人：${roomApply.departApproveBy.userName}</p>
              <p align="left">审批时间：${roomApply.departApproveAt?string('yyyy 年 MM 月 dd 日')}</p>
          </td>
      </tr>      
      </#if>
      <#if roomApply.isApproved?default(false)>
      <tr>
        <td rowspan="9"><p align="center"><strong>物</strong></p>
            <p align="center">业</p>
            <p align="center"><strong>管</strong></p>
            <p align="center"><strong>理</strong></p>
            <p align="center"><strong>部</strong></p>
            <p align="center"><strong>填</strong></p>
            <p align="center"><strong>写</strong></p></td>
        <td <strong>借用场所安排</strong></td>
        <td colspan="2">
		      地点：<#list roomApply.getClassrooms()?if_exists as classroom><@i18nName (classroom.building)?if_exists/>&nbsp;-&nbsp;<@i18nName classroom?if_exists/>&nbsp;&nbsp;&nbsp;</#list><br> 
                           日期：${roomApply.applyTime.dateBegin?string("yyyy 年 MM 月 dd 日")}&nbsp;&nbsp;—&nbsp;&nbsp;${roomApply.applyTime.dateEnd?string("yyyy 年 MM 月 dd 日")}&nbsp;&nbsp; 时间：<@RoomApply.cycleValue count=roomApply.applyTime.cycleCount type=roomApply.applyTime.cycleType?string/>${roomApply.applyTime.timeBegin}-${roomApply.applyTime.timeEnd}<br>
      </tr>
      <tr>
        <td rowspan="3"><strong>服务方承诺</strong></td>
        <td colspan="2">(1)统筹安排教室借用场所。</td>
      </tr>
      <tr>
        <td colspan="2">(2)按规定提供教室借用基本条件，确保用电和基本设施，确保场所卫生整洁。</td>
      </tr>
      <tr>
        <td colspan="2">(3)按借用方要求，可代制作会标，费用另计。</td>
      </tr>
      <tr>
        <td rowspan="3"><strong>服务权益</strong></td>
        <td colspan="2">(1)现核定教室借用实际使用&nbsp;${(roomApply.hours)?default(0)}&nbsp;小时（约）。</td>
      </tr>
      <tr>
        <td colspan="2">(2)应收教室借用费： <#if roomApply.isFree == true>0.00<#else>${(roomApply.money?string("0.00"))?default(0.00)}</#if> 元。 </td>
      </tr>
      <tr>
        <td colspan="2">(3)现核定应收茶水费： ${(roomApply.waterFee?string("0.00"))?default(0.00)}元。 </td>
      </tr>
      <tr>
      	<td rowspan="2"><b>物管审核情况</b></td>
      	<td colspan="2">审核人：${roomApply.approveBy.userName}</td>
      </tr>
      <tr>
      	<td colspan="2">审核时间：${roomApply.approveAt?string('yyyy 年 MM 月  dd 日')}</td>
      </tr>
     <#else>
      <tr>
      	<td rowspan="2"><b>物管审核情况</b></td>
      	<td colspan="4">审核人：${(roomApply.approveBy.userName)?if_exists}</td>
      </tr>
      <tr>
      	<td colspan="4">审核意见：${roomApply.approvedRemark?if_exists}</td>
      </tr> 
     </#if>
   </table>
   <br><br>
   <script>
     var bar =new ToolBar("myBar","教室借用申请审批表",null,true,true);
     bar.addPrint("<@msg.message key="action.print"/>");
     bar.addBack("<@msg.message key="action.back"/>");
   </script>
   </body>
<#include "/templates/foot.ftl"/>