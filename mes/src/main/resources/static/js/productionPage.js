// 테이블의 tbody 요소를 가져옵니다.
var tableBody = document.querySelector("#workListTable tbody");

// 모든 tr 요소를 가져옵니다.
var rows = tableBody.querySelectorAll("tr");

// 각 행에 클릭 이벤트를 추가합니다.
rows.forEach(function(row) {
    row.addEventListener("click", function() {
        // 작업지시 ID와 제품명을 가져옵니다.
        var workId = row.querySelector("td:first-child").innerText;
        var workProduct = row.querySelector("td:nth-child(2)").innerText;
        console.log(workId, workProduct);

        // 가져온 값으로 출력할 요소를 찾아 업데이트합니다.
        var workIdElement = document.getElementById("workId");
        var workProductElement = document.getElementById("workProduct");
        workIdElement.innerText = workId;
        workProductElement.innerText = workProduct;

    });
});

