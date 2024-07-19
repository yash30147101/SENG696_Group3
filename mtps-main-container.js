let rowCounter = 0;

data.forEach((row, rowIdx) => {
    rowCounter++; // increment the counter for each row

    let colIdx = 0;
    let lengthFlag = false;
    exceptionoccuredRow = rowIdx;

    //mapping column headers between schema & excel sheet
    let mapping_ColIdx = 0;
    let mappedRow = null;
    _.forEach(row, (rowVal, col) => {

        let testCol = schema[mapping_ColIdx].columnName;
        mappedRow = {
            ...mappedRow,
            [testCol]: rowVal
        };
        mapping_ColIdx++;
    });			
});

console.log(`Total number of rows: ${rowCounter}`);
