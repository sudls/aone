// 수주 등록
document.getElementById("btnAdd").addEventListener("click", function() {
    document.getElementById("addOrderModal").style.display = "block"; //수주 등록 모달
});

//수주 확정
document.getElementById("btnConfirm").addEventListener("click", function() {
    let selectedValues = getSelectedValues();
    if(selectedValues.orderIds.length>0){
        openModal("수주 확정", "확정", "/order/confirm", "color: #3196e2", "bg-blue");
    }else{
        alert("주문상태를 확정으로 변경할 수주를 선택해주세요");
    }

});

//수주 취소
document.getElementById("btnCancel").addEventListener("click", function() {
    let selectedValues = getSelectedValues();
    if(selectedValues.orderIds.length>0){
        openModal("수주 취소", "취소","/order/cancel", "color: #ff834c", "bg-orange");
    }else{
        alert("주문상태를 취소로 변경할 수주를 선택해주세요");
    }

});

//수주 삭제
document.getElementById("btnDel").addEventListener("click", function() {
    let selectedValues = getSelectedValues();
    if(selectedValues.orderIds.length>0){
        openModal("수주 삭제", "삭제","/order/delete", "color: #ff6b6c", "bg-red");
    }else{
        alert("주문상태를 삭제로 변경할 수주를 선택해주세요");
    }

});

//수주 확정, 취소, 삭제 모달창 열기
function openModal(title, buttonLabel,formAction,  spanStyle, buttonClass) {
    // 모달 내용 변경
    document.getElementById("changeOrderModal").querySelector("h1").textContent = title;
    document.getElementById("changeOrderModal").querySelector("button[type='submit']").textContent = buttonLabel;
    document.getElementById("changeText").textContent = buttonLabel;
    document.getElementById("changeProductForm").action = formAction;
    document.getElementById("selectedInfo").querySelector("span").style = spanStyle;
    document.getElementById("okChangeModal").className = buttonClass;

    getCheckInfoData();//체크한 수주 정보 테이블로 보여주기

    // 모달 열기
    document.getElementById("changeOrderModal").style.display = "block";
}


// 모달 창 닫기 버튼의 이벤트 핸들러 추가
document.getElementsByClassName("close")[0].addEventListener("click", function() {
    document.getElementById("addOrderModal").style.display = "none";
});
document.getElementsByClassName("close")[1].addEventListener("click", function() {
    document.getElementById("changeOrderModal").style.display = "none";
});

// 취소버튼 클릭시 모달 창 닫기
document.getElementById("closeAddModal").addEventListener("click", function() {
    document.getElementById("addOrderModal").style.display = "none";
});
document.getElementById("closeChangeModal").addEventListener("click", function() {
    document.getElementById("changeOrderModal").style.display = "none";
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


function getSelectedValues() {
    let table = document.querySelector(".table-container table");
    let selectedOrderIds = [];
    let selectedOrderProducts=[];
    let selectedOrderNum=[];

    let checkboxes = table.querySelectorAll("tbody input[type='checkbox']");
    checkboxes.forEach(function(checkbox) {
        let row = checkbox.parentNode.parentNode;
        if (checkbox.checked) {
            let value1 = row.cells[1].textContent; // 2번째 열의 값을 가져옴 (수주번호)
            selectedOrderIds.push(value1);

            let value2 = row.cells[2].textContent; // 3번째 열의 값을 가져옴 (제품명)
            selectedOrderProducts.push(value2);

            let value3 = row.cells[4].textContent; // 5번째 열의 값을 가져옴 (수량)
            selectedOrderNum.push(value3);
        }
    });

    document.getElementById("selectedOrderIdList").value=selectedOrderIds.toString();

    return {
        orderIds: selectedOrderIds,
        orderProducts: selectedOrderProducts,
        orderNum: selectedOrderNum
    };

}

//수주 확정, 취소, 삭제를 위한 모달창에 체크한 수주 정보 표시

function getCheckInfoData(){

    let selectedValues = getSelectedValues();
    let table = document.getElementById("selectedInfoTable");
    let tbody = table.querySelector("tbody");

// 기존의 행을 제거
    tbody.innerHTML = "";

// 반환된 값들을 테이블에 추가
    for (let i = 0; i < selectedValues.orderIds.length; i++) {
        let orderId = selectedValues.orderIds[i];
        let orderProduct = selectedValues.orderProducts[i];
        let orderNum = selectedValues.orderNum[i];

        let row = document.createElement("tr");
        let orderIdCell = document.createElement("td");
        let orderProductCell = document.createElement("td");
        let orderNumCell = document.createElement("td");

        orderIdCell.textContent = orderId;
        orderProductCell.textContent = orderProduct;
        orderNumCell.textContent = orderNum;

        row.appendChild(orderIdCell);
        row.appendChild(orderProductCell);
        row.appendChild(orderNumCell);

        tbody.appendChild(row);
    }

}
