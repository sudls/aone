function downloadTablesAsExcel() {
    const tables = document.querySelectorAll("table");
    const workbook = XLSX.utils.book_new();

    tables.forEach(function(table, index) {
        const tableData = [];
        const rows = table.querySelectorAll("tr");

        rows.forEach(function(row) {
            const rowData = [];
            Array.from(row.cells).forEach(function(cell) {
                rowData.push(cell.innerText);
            });
            tableData.push(rowData);
        });

        const worksheet = XLSX.utils.aoa_to_sheet(tableData);
        // 셀 스타일 적용
        // Object.keys(worksheet).forEach(function(cellRef) {
        //     const cell = worksheet[cellRef];
        //     if (cell.s && cell.s !== 0) {
        //         cell.s = Object.assign({}, cell.s, {
        //             ...cell.s,
        //             ...JSON.parse(cell.s)
        //         });
        //     } else {
        //         cell.s = 0;
        //     }
        // });
        const headCellStyle = {
            font: { bold: true },
            fill: { fgColor: { rgb: "DDD" } }
        };

        const sheetName = "Table " + (index + 1);
        //worksheet.name = sheetName; // 시트 이름 변경
        XLSX.utils.book_append_sheet(workbook, worksheet, sheetName);
    });

    const excelBuffer = XLSX.write(workbook, { bookType: "xlsx", type: "array" });
    const excelData = new Blob([excelBuffer], { type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" });
    const excelUrl = URL.createObjectURL(excelData);

    const link = document.createElement("a");
    link.href = excelUrl;
    link.download = "AONE_minimes.xlsx"; //파일명 지정
    document.body.appendChild(link);

    link.click();
    document.body.removeChild(link);
    URL.revokeObjectURL(excelUrl);
}