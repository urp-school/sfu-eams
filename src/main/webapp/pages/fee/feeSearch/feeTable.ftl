 	<@table.table align="center" sortable="true" id="feeDetail" width="1400px">
 		<@table.thead style="font-size: 12px;height:10px">
 			<@table.selectAllTd id="feeDetailId"/>
 			<@table.sortTd name="field.feeDetail.date" id="feeDetail.createAt"/>
 			<@table.sortTd name="field.feeDetail.studentId" id="feeDetail.std.code"/>
 			<@table.sortTd name="field.feeDetail.studentName" id="feeDetail.std.name"/>
 			<@table.sortTd name="attr.term" id="feeDetail.calendar.year"/>
 			<@table.sortTd name="field.feeDetail.feeTypeOfFee" id="feeDetail.type.name"/>
 			<@table.sortTd text="交费方式" id="feeDetail.mode.name"/>
 			<@table.td text="应缴"/>
 			<@table.td name="field.feeDetail.money"/>
 			<@table.sortTd name="field.feeDetail.invoiceNumber" id="feeDetail.invoiceCode"/>
 			<@table.td text="折合人民币"/>
 			<@table.sortTd name="field.feeDetail.currencyCategory" id="feeDetail.currencyCategory.name"/>
 			<@table.sortTd name="field.feeDetail.exchangeRate" id="feeDetail.rate"/>
 			<@table.sortTd name="attr.remark" id="feeDetail.remark"/>
 			<@table.td text="HSK成绩"/>
 			<@table.sortTd text="入学年份" id="feeDetail.std.enrollYear"/>
 		</@>
 		<@table.tbody  style="font-size: 12px;height:10px" datas=fees;feeDetail>
 			<@table.selectTd id="feeDetailId" value=feeDetail.id/>
			<td>${feeDetail.createAt?if_exists?string("yyyy-MM-dd")}</td>
			<td><a href="#" onclick="javascript:location='studentDetailByManager.do?method=detail&stdId=${feeDetail.std.id}'">${feeDetail.std?if_exists.code}</a></td>
			<td>${feeDetail.std?if_exists.name?if_exists}</td>
			<td>${feeDetail.calendar.year} ${feeDetail.calendar.term}</td>
			<td><@i18nName feeDetail.type/></td>
			<td><@i18nName feeDetail.mode/></td>
			<td><#if feeDetail.shouldPay?exists><#if (feeDetail.shouldPay<0)><font color="red"></#if>${feeDetail.shouldPay?string("##0.00#")}</#if></td>
			<td><#if feeDetail.payed?exists><#if (feeDetail.payed<0)><font color="red"></#if>${feeDetail.payed?string("#0.00#")}</#if></td>
			<td>${feeDetail.invoiceCode?if_exists}</td>
			<td><#if feeDetail.toRMB?exists><#if (feeDetail.toRMB<0)><font color="red"></#if>${feeDetail.toRMB?string("##0.00#")}</#if></td>
			<td><@i18nName feeDetail.currencyCategory/></td>
			<td><#if feeDetail.rate?exists>${feeDetail.rate?string("#0.00")}</#if></td>
			<td>${feeDetail.remark?if_exists}</td>
			<td>${(feeDetail.std.abroadStudentInfo.name)?if_exists}</td>
			<td>${feeDetail.std.enrollYear?if_exists}</td>
 		</@>
 	</@>
	<br><br>