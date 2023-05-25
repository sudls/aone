// 그래프 업데이트 함수
function updateGraph(data) {
    let progressCircles = document.querySelectorAll('.progress-circle');

    if (progressCircles.length === 0) {
        return; // No progress circles found, exit the function
    }

    progressCircles.forEach(function(progressCircle, index) {
        let jobId = data[index].jobId;
        let productName = data[index].productName;
        let startTime = data[index].startTime;
        let endTime = data[index].endTime;

        let jobElement = progressCircle.querySelector('.jobId');
        let productNameElement = progressCircle.querySelector('.productName');
        let startTimeElement = progressCircle.querySelector('.startTime');
        let endTimeElement = progressCircle.querySelector('.endTime');

        if (jobElement) {
            jobElement.textContent = jobId;
        }
        if (productNameElement) {
            productNameElement.textContent = productName;
        }
        if (startTimeElement) {
            startTimeElement.textContent = startTime.toLocaleString(); // 수정: 시간을 문자열로 표시
        }
        if (endTimeElement) {
            endTimeElement.textContent = endTime.toLocaleString(); // 수정: 시간을 문자열로 표시
        }




        let span = progressCircle.querySelector('span');
        let valueBar = progressCircle.querySelector('.value-bar');

        if (startTime === null) {
            span.textContent = '0%';
            progressCircle.classList.add('p0');
            return; // Skip further processing for this data
        }

        // 현재시간이 완료시간을 지났다면
        let currentTime = new Date();
        if (endTime < currentTime) {
            // Remove the data and show 0%
            span.textContent = '0%';
            progressCircle.classList.remove('over50');
            for (let i = 0; i <= 100; i++) {
                progressCircle.classList.remove('p' + i);
            }
            valueBar.style.transform = 'rotate(0deg)';
            return; // Skip further processing for this data
        }

        // 퍼센테이지 계산
        let totalDuration = endTime - startTime;
        let currentDuration = currentTime - startTime;
        let percent = Math.min((currentDuration / totalDuration) * 100, 100);
        span.textContent = percent.toFixed(0) + '%';

        // Update graph
        progressCircle.classList.remove('over50');
        for (let i = 0; i <= 100; i++) {
            progressCircle.classList.remove('p' + i);
        }
        progressCircle.classList.add('p' + Math.floor(percent));

        if (percent > 50) {
            progressCircle.classList.add('over50');
        }

        valueBar.style.transform = `rotate(${percent * 3.6}deg)`;
    });
}


window.onload = function() {

    let data = [
        {
            jobId: '12345',
            productName: '제품 A',
            startTime: null,
            endTime: null,
        },
        {
            jobId: '12346',
            productName: '제품 B',
            startTime: new Date('2023-05-25T10:00:00'), // 수정: 10시로 변경
            endTime: new Date('2023-05-25T13:53:00')
        },
        {
            jobId: '12347', // 수정: 고유한 jobId 값으로 변경
            productName: '제품 C', // 수정: 다른 제품 이름으로 변경
            startTime: new Date('2023-05-24T12:00:00'), // 수정: 12시로 변경
            endTime: new Date('2023-05-24T18:00:00') // 수정: 18시로 변경
        },
        {
            jobId: '12348',
            productName: '제품 D',
            startTime: new Date('2023-05-25T09:00:00'), // 수정: 9시로 변경
            endTime: new Date('2023-05-25T17:00:00') // 수정: 17시로 변경
        },
        {
            jobId: '12349',
            productName: '제품 E',
            startTime: new Date('2023-05-25T11:00:00'), // 수정: 11시로 변경
            endTime: new Date('2023-05-25T16:00:00') // 수정: 15시로 변경
        },
        {
            jobId: '12345',
            productName: '제품 A',
            startTime: new Date('2023-05-25T08:00:00'),
            endTime: new Date('2023-05-25T16:00:00')
        },
        {
            jobId: '12346',
            productName: '제품 B',
            startTime: new Date('2023-05-25T10:00:00'), // 수정: 10시로 변경
            endTime: new Date('2023-05-26T14:00:00')
        },
        {
            jobId: '12347', // 수정: 고유한 jobId 값으로 변경
            productName: '제품 C', // 수정: 다른 제품 이름으로 변경
            startTime: new Date('2023-05-25T12:00:00'), // 수정: 12시로 변경
            endTime: new Date('2023-05-25T18:00:00') // 수정: 18시로 변경
        },
        {
            jobId: '12348',
            productName: '제품 D',
            startTime: new Date('2023-05-25T09:00:00'), // 수정: 9시로 변경
            endTime: new Date('2023-05-25T17:00:00') // 수정: 17시로 변경
        },
        {
            jobId: '12349',
            productName: '제품 E',
            startTime: new Date('2023-05-25T11:00:00'), // 수정: 11시로 변경
            endTime: new Date('2023-05-26T15:00:00') // 수정: 15시로 변경
        },
        // 다른 데이터 객체들...
    ];


    updateGraph(data);


    // 10초마다 데이터 업데이트
    setInterval(function() {
        // 그래프 업데이트
        updateGraph(data);
    }, 10000);
};
