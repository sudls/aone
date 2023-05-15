// 버튼 클릭 시 모달 창을 띄우는 이벤트 핸들러 추가
document.getElementById("btnAdd").addEventListener("click", function() {
    document.getElementById("myModal").style.display = "block";
});

// 모달 창 닫기 버튼의 이벤트 핸들러 추가
document.getElementsByClassName("close")[0].addEventListener("click", function() {
    document.getElementById("myModal").style.display = "none";
});

//모달창의 수주일 날짜를 오늘날짜로 설정
// 현재 날짜와 시간을 가져오는 함수
function getCurrentDateTime() {
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    const formattedDate = `${year}-${month}-${day}`;

    return formattedDate;
}

// input 요소의 값을 현재 날짜와 시간으로 설정
document.getElementById("orderDate").value = getCurrentDateTime();

// input 요소의 값을 0 미만으로 떨어지지 않도록 유효성 검사
document.getElementById("orderNum").addEventListener("change", function() {
    const value = parseInt(this.value); // 입력된 값 파싱

    if (value < 0) {
        this.value = 0; // 값이 0 미만일 경우 0으로 설정
    }
});