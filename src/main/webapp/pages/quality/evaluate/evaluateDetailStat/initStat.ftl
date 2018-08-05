	<table class="frameTable" width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2"><table id="initStatBar" width="100%"></table></td>
		</tr>
		<tr valign="top">
			<td width="50%">
			<input type="hidden" name="evaluateResult.teachCalendar.id" value="${calendar.id}"/>
			<input type="hidden" name="searchFormFlag" value="${RequestParameters["searchFormFlag"]?if_exists}"/>
		</form>
				<table id="stdTypeBar" width="100%"></table>
				<@table.table id="studentType" width="100%">
					<@table.thead>
						<@table.selectAllTd id="stdTypeId"/>
						<@table.td name="attr.code"/>
						<@table.td name="attr.name"/>
					</@>
					<@table.tbody datas=stdTypes;stdType>
						<@table.selectTd id="stdTypeId" value=stdType.id/>
						<td>${stdType.code}</td>
						<td><@i18nName stdType/></td>
					</@>
				</@>
				<br><br><br>
				<center>
				<button onclick="stat()">统计</button>
				</center>
			</td>
			<td>
				<table id="departmentBar" width="100%"></table>
				<@table.table id="department" width="100%">
					<@table.thead>
						<@table.selectAllTd id="departmentId"/>
						<@table.td name="attr.code"/>
						<@table.td name="attr.name"/>
					</@>
					<@table.tbody datas=departments;department>
						<@table.selectTd id="departmentId" value=department.id/>
						<td>${department.code}</td>
						<td><@i18nName department/></td>
					</@>
				</@>
			</td>
		</tr>
	</table>
