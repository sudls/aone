// 버튼 클릭 시 모달 창을 띄우는 이벤트 핸들러 추가
document.getElementById("btnAdd").addEventListener("click", function() {
    document.getElementById("addOrderModal").style.display = "block";
});

// 모달 창 닫기 버튼의 이벤트 핸들러 추가
document.getElementsByClassName("close")[0].addEventListener("click", function() {
    document.getElementById("addOrderModal").style.display = "none";
});
document.getElementById("closeBtn").addEventListener("click", function() {
    document.getElementById("addOrderModal").style.display = "none";
});

//모달창의 수주일 날짜를 오늘날짜로 설정
// 현재 날짜와 시간을 가져오는 함수
function getCurrentDateTime() {
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const formattedDate = `${year}-${month}-${day}`;
    const formattedTime = `${hours}:${minutes}`;

    return { date: formattedDate, time: formattedTime };
}

const currentDateTime = getCurrentDateTime();
document.getElementById("setOrderDate").value = currentDateTime.date;
document.getElementById("setOrderTime").value = currentDateTime.time;

// input 요소의 값을 0 미만으로 떨어지지 않도록 유효성 검사 (수량 지정)
document.getElementById("setOrderNum").addEventListener("change", function() {
    const value = parseInt(this.value); // 입력된 값 파싱

    if (value < 0) {
        this.value = 0; // 값이 0 미만일 경우 0으로 설정
    }
});

//---
//체크박스 선택시 해당 행 값 반환
function toggleSelectedRow(checkbox) {
    var row = checkbox.parentNode.parentNode;
    row.classList.toggle("selected");
    console.log(getSelectedValues());
}

function getSelectedValues() {
    var table = document.querySelector(".table-container table");
    var selectedValues = [];

    var checkboxes = table.querySelectorAll("tbody input[type='checkbox']");
    checkboxes.forEach(function(checkbox) {
        var row = checkbox.parentNode.parentNode;
        if (checkbox.checked) {
            var value = row.cells[1].textContent; // 2번째 열의 값을 가져옴 (수주번호)
            selectedValues.push(value);
        }
    });
    return selectedValues;

}