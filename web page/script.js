function openOverlay() {
    document.getElementById('overlay').style.display = 'flex';
}

function closeOverlay() {
    document.getElementById('overlay').style.display = 'none';
}

document.getElementById('expiryDate').addEventListener('input', function (e) {
    let value = e.target.value.replace(/\D/g, '');

    if (value.length > 2) {
        value = value.substring(0, 2) + '/' + value.substring(2);
    }

    if (value.length > 7) {
        value = value.substring(0, 7);
    }

    e.target.value = value;
});

document.getElementById('cvc2').addEventListener('input', function (e) {
    let value = e.target.value.replace(/\D/g, ''); 

    if (value.length <= 3) {
        let maskedValue = '*'.repeat(value.length - 1) + value.charAt(value.length - 1);
        e.target.value = maskedValue;
    } else {
        e.target.value = value.slice(0, 3);
    }
});


