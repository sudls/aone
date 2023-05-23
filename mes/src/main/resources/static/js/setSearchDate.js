const startDateInput = document.getElementById('startDate');
const endDateInput = document.getElementById('endDate');

startDateInput.addEventListener('change', function() {
    const startDateValue = startDateInput.value;

    if (startDateValue !== '') {
        endDateInput.value = startDateValue;
    }
});

endDateInput.addEventListener('change', function() {
    const startDateValue = startDateInput.value;
    const endDateValue = endDateInput.value;

    if (endDateValue !== '' && endDateValue < startDateValue) {

        alert('endDate는 startDate 이후의 날짜여야 합니다.');
        endDateInput.value = startDateInput.value;
    }
});