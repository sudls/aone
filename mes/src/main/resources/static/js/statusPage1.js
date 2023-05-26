var productPlanRows = document.querySelectorAll('.productPlanList');

// 각 tr 요소에 대해 반복하여 작업을 수행합니다.
productPlanRows.forEach(function(row) {
    // 각 요소의 필요한 데이터를 가져와서 활용합니다.
    var orderId = row.querySelector('.orderId').textContent;
    var orderPN = row.querySelector('.orderPN').textContent;
    var stage1 = row.querySelector('.stage1');
    var stage2 = row.querySelector('.stage2');
    var stage3 = row.querySelector('.stage3');
    var stage4 = row.querySelector('.stage4');
    var stage5 = row.querySelector('.stage5');
    var stage6 = row.querySelector('.stage6');

    // 필요한 작업을 수행합니다.
    console.log('Order ID:', orderId);
    console.log('Order Product Name:', orderPN);
    console.log('Stage 1:', stage1);
    console.log('Stage 2:', stage2);
    console.log('Stage 3:', stage3);
    console.log('Stage 4:', stage4);
    console.log('Stage 5:', stage5);
    console.log('Stage 6:', stage6);
});