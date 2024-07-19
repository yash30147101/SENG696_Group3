In the below Node JS code, I want to store the total number of rows in data in rowCounter variable.

data.forEach((row, rowIdx) => {
			let colIdx = 0
			let lengthFlag = false
			exceptionoccuredRow = rowIdx

			//mapping column headers between schema & excel sheet
			let mapping_ColIdx = 0;
			let mappedRow = null
			_.forEach(row, (rowVal, col) => {

				let testCol = schema[mapping_ColIdx].columnName
				mappedRow = {
					...mappedRow,
					[testCol]: rowVal
				}
				mapping_ColIdx++;
			})			
}
