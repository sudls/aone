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


        //0%일때 레이블 회색
        if (span.textContent === '0%') {
            span.classList.add('p0'); // 'p0' 클래스 추가
        } else {
            span.classList.remove('p0'); // 'p0' 클래스 제거
        }
    });
}


window.onload = function() {

    let data = [];

    for (let i = 1; i <= 10; i++) {
        let jobIdValue = document.getElementById('jobId' + i).textContent;
        let productNameValue = document.getElementById('productName'+i).textContent;

        let startTimeValue = null;
        let endTimeValue = null;


        //공정시작시간
        let dateString = document.getElementById('startTime'+i).textContent;
        let parts = dateString.split(' ');
        if(parts.length>1) {
            let dateParts = parts[0].split('/');
            let timeParts = parts[1].split(':');

            let year = parseInt(dateParts[0]) + 2000; // 연도를 4자리로 변환
            let month = parseInt(dateParts[1]) - 1; // 월은 0부터 시작하므로 1을 빼줌
            let day = parseInt(dateParts[2]);
            let hour = parseInt(timeParts[0]);
            let minute = parseInt(timeParts[1]);

            startTimeValue = new Date(year, month, day, hour, minute);

            //공정종료시간
            dateString = document.getElementById('endTime' + i).textContent;
            parts = dateString.split(' ');
            dateParts = parts[0].split('/');
            timeParts = parts[1].split(':');

            year = parseInt(dateParts[0]) + 2000; // 연도를 4자리로 변환
            month = parseInt(dateParts[1]) - 1; // 월은 0부터 시작하므로 1을 빼줌
            day = parseInt(dateParts[2]);
            hour = parseInt(timeParts[0]);
            minute = parseInt(timeParts[1]);

            endTimeValue = new Date(year, month, day, hour, minute);
        }

        data.push({
            jobId: jobIdValue,
            productName: productNameValue,
            startTime: startTimeValue,
            endTime: endTimeValue,
        });


    }
    console.log(data);
    // let data = [
    //     {
    //         jobId: '12345',
    //         productName: '제품 A',
    //         startTime: null,
    //         endTime: null,
    //     },
    //     {
    //         jobId: '12346',
    //         productName: '제품 B',
    //         startTime: new Date('2023-05-25T10:00:00'), // 수정: 10시로 변경
    //         endTime: new Date('2023-05-25T13:53:00')
    //     },
    //     {
    //         jobId: '12347', // 수정: 고유한 jobId 값으로 변경
    //         productName: '제품 C', // 수정: 다른 제품 이름으로 변경
    //         startTime: new Date('2023-05-24T12:00:00'), // 수정: 12시로 변경
    //         endTime: new Date('2023-05-24T18:00:00') // 수정: 18시로 변경
    //     },
    //     {
    //         jobId: '12348',
    //         productName: '제품 D',
    //         startTime: new Date('2023-05-25T09:00:00'), // 수정: 9시로 변경
    //         endTime: new Date('2023-05-25T17:00:00') // 수정: 17시로 변경
    //     },
    //     {
    //         jobId: '12349',
    //         productName: '제품 E',
    //         startTime: new Date('2023-05-25T11:00:00'), // 수정: 11시로 변경
    //         endTime: new Date('2023-05-25T16:00:00') // 수정: 15시로 변경
    //     },
    //     {
    //         jobId: '12345',
    //         productName: '제품 A',
    //         startTime: new Date('2023-05-25T08:00:00'),
    //         endTime: new Date('2023-05-25T16:00:00')
    //     },
    //     {
    //         jobId: '12346',
    //         productName: '제품 B',
    //         startTime: new Date('2023-05-25T10:00:00'), // 수정: 10시로 변경
    //         endTime: new Date('2023-05-26T14:00:00')
    //     },
    //     {
    //         jobId: '12347', // 수정: 고유한 jobId 값으로 변경
    //         productName: '제품 C', // 수정: 다른 제품 이름으로 변경
    //         startTime: new Date('2023-05-25T12:00:00'), // 수정: 12시로 변경
    //         endTime: new Date('2023-05-25T18:00:00') // 수정: 18시로 변경
    //     },
    //     {
    //         jobId: '12348',
    //         productName: '제품 D',
    //         startTime: new Date('2023-05-25T09:00:00'), // 수정: 9시로 변경
    //         endTime: new Date('2023-05-25T17:00:00') // 수정: 17시로 변경
    //     },
    //     {
    //         jobId: '12349',
    //         productName: '제품 E',
    //         startTime: new Date('2023-05-25T11:00:00'), // 수정: 11시로 변경
    //         endTime: new Date('2023-05-26T15:00:00') // 수정: 15시로 변경
    //     },
    // ];


    updateGraph(data);


    // 10초마다 데이터 업데이트
    setInterval(function() {
        // 그래프 업데이트
        updateGraph(data);
    }, 10000);
};
