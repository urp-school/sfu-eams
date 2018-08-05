// tableId 		: 表格的ID。
// rowStart		: 起始位置。为首行单元格Id的行标。
// colStart		: 起始位置。为首行单元格Id的列标。
// rowLen		: 结束位置。为末行单元格Id的行标。
// colLen		: 结束位置。为末行单元格Id的列标。
// exclusiveCol	: 忽略列。
// 单元格的ID值的使命为：行标_列标
// 比如：<td id = "0_0">，<td id = "2_-3">等。
function spanTable(tableId, rowStart, colStart, rowLen, colLen, exclusiveCol) {
	var rows = $(tableId).rows;
	if (rows.length >= rowStart || rows.length >= 1) {
		for (var i = rowStart; i < rowLen; i++) {
			for (var j = colStart; j < colLen; j++) {
				// 仅此格有值时处理合并
				if (null != $(i + "_" + j) && $(i + "_" + j).innerHTML != "" && j != exclusiveCol) {
					// 以 i + "_" + j 单元格为原点，视周围与之相同否，同则记下，然则止步
					// 沿先横后纵寻之，于首行至不同止步记下列数，遂以此列数为范围，向下统计个数
					var tdLen = 0, count = 0;
					for (var x = i; null != $(x + "_" + j) && $(i + "_" + j).innerHTML == $(x + "_" + j).innerHTML; x++) {
						for (var y = j; null != $(x + "_" + y) && $(i + "_" + j).innerHTML == $(x + "_" + y).innerHTML; y++) {
							if (x == i) {
								tdLen++;
							}
							count++;
						}
					}
					// 按记录范围进行合并
					for (var mergeRow = i; mergeRow < i + count / tdLen; mergeRow++) {
						for (var mergeCol = j + 1; tdLen > 1 && mergeCol <  j + tdLen; mergeCol++) {
							rows[mergeRow].removeChild($(mergeRow + "_" + mergeCol));
							$(mergeRow + "_" + j).colSpan++;
						}
						if (mergeRow > i) {
							rows[mergeRow].removeChild($(mergeRow + "_" + j));
							$(i + "_" + j).rowSpan++;
						}
					}
					j += $(i + "_" + j).colSpan - 1;
				}
			}
		}
	}
}

// 按表格所在的单元格ID，返回所在行
function findRowInTable(tableId, tdId) {
	var rows = $(tableId).rows;
	for (var i = 0; i < rows.length; i++) {
		for (var j = 0; j < rows[i].cells.length; j++) {
			if (rows[i].cells[j].id == tdId) {
				return i;
			}
		}
	}
	return -1;
}
