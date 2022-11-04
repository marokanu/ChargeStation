function createElementFromAttribute(attribute, parent) {
    const openCell = document.createElement("td");
    openCell.innerHTML = `<p> ${attribute}</p>`;
    parent.appendChild(openCell);
}

function createButtons(id, parent) {
    const tb = document.createElement("td");
    const buttonsTd = document.createElement("div");
    buttonsTd.innerHTML = `<button type="button" class="btn btn-primary btn-sm" onclick="showDialog(${id})">Update</button>`;
    const buttonsDelete = document.createElement("div");
    buttonsDelete.innerHTML = `<button type="button" class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#deleteBookingDialog">Delete</button>`;
    tb.appendChild(buttonsTd);
    tb.appendChild(buttonsDelete);
    parent.appendChild(tb);
}
function saveDataIntoTr(data, parent) {
    $(parent).data(data);
}

const baseURL = 'http://localhost:8082';
let bookings = [];
$(document).ready(async function () {
    const responseJson = await fetch(
        baseURL + `/api/bookings`,
        {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
        });
    const response = await responseJson.json();
    if (responseJson.ok) {
        const table = $("#booking-table tbody");
        bookings = response;
        for (const appointment of response) {
            const newStationTr = document.createElement("tr");
            createElementFromAttribute(appointment.id, newStationTr);
            createElementFromAttribute(appointment.name, newStationTr);
            createElementFromAttribute(appointment.licenseNumber, newStationTr);
            createElementFromAttribute(appointment.duration, newStationTr);
            createElementFromAttribute(appointment.date, newStationTr);
            createElementFromAttribute(appointment.station.name, newStationTr);
            saveDataIntoTr(appointment, newStationTr);
            createButtons(appointment.id, newStationTr);
            table.append(newStationTr);
        }
    } else {
        console.log("An error has occurred", response);
        // showResponse("An error has occurred", "danger");
    }
});

function showDialog(data) {

    $("#stationId-dialog").val(data);
    const booking = bookings.find(bookingItem => bookingItem.id === data);
    $('#bookingNameDialog').val(booking.name);
    $('#bookingDurationDialog').val(22);
    $('#bookingDateDialog').val(booking.date);
    $('#bookingLicenseDialog').val(booking.licenseNumber);
    let myModalEl = document.getElementById('createBookingDialog');
    let modal = bootstrap.Modal.getOrCreateInstance(myModalEl);
    modal.show();
}
async function updateBooking() {
    const data = {
        name: $('#bookingNameDialog').val(),
        duration: $('#bookingDurationDialog').val(),
        date: $('#bookingDateDialog').val(),
        licenseNumber: $('#bookingLicenseDialog').val(),
        stationId:$("#stationId-dialog").val(),
    };
    const responseJson = await fetch(
        baseURL + `/api/bookings/`+ data.stationId,
        {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
    const response = await responseJson.json();
    if (responseJson.ok){
    }
}

async function deleteBooking(id) {
    console.log("id", id);

    const responseJson = await fetch(
        baseURL + `/api/bookings/` + id,
        {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
        });
    const response = await responseJson.json();
    if (responseJson.ok){
        console.log(response);
    }
    console.error(response);

}