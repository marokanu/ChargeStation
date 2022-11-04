let isSortAscending = true;
const baseURL = 'http://localhost:8082';
let stations = [];

function createElementFromAttribute(attribute, parent) {
    const openCell = document.createElement("td");
    openCell.innerHTML = `<p> ${attribute}</p>`;
    parent.appendChild(openCell);
}

function createButtons(data, parent) {
    const buttonsTd = document.createElement("td");
    buttonsTd.innerHTML = `<button type="button" class="btn btn-primary btn-big" onclick="showDialog(${data.id})">Book</button>`;
    parent.appendChild(buttonsTd);
}

async function sortBy(columnName) {
    let sortOrder = "ASC"
    if (!isSortAscending) {
        sortOrder = "DESC"
    }
    isSortAscending = !isSortAscending;
    const responseJson = await fetch(
        baseURL + `/api/stations/sort?` + new URLSearchParams({
            sortBy: columnName,
            sortOrder: sortOrder,
        }),
        {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
        });
    stations = await responseJson.json();
    if (responseJson.ok) {
        createStationTable();
    } else {
        console.log("An error has occurred", response);
        // showResponse("An error has occurred", "danger");
    }
}

function saveDataIntoTr(data, parent) {
    $(parent).data(data);
}
$(document).ready(async function () {
    if (stations.length === 0) {
        const responseJson = await fetch(
            baseURL + `/api/stations`,
            {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            });
        stations = await responseJson.json();
        if (responseJson.ok) {
            createStationTable();
        } else {
            console.log("An error has occurred", response);
            // showResponse("An error has occurred", "danger");
        }
    }
});

function createStationTable() {
    const table = $("#stations-table tbody");
    table.empty();

    for (const station of stations) {
        const newStationTr = document.createElement("tr");
        createElementFromAttribute(station.id, newStationTr);
        createElementFromAttribute(station.name, newStationTr);
        createElementFromAttribute(station.location.name, newStationTr);
        createElementFromAttribute(station.open, newStationTr);
        createElementFromAttribute(station.stationType.plugType, newStationTr);
        createButtons(station, newStationTr);
        createPushPin(station.name, station.location.lng,station.location.lat, `<div>${station.name}</div>`)
        // saveDataIntoTr(station, newStationTr);
        table.append(newStationTr);
    }
}

function showDialog(stationId) {
    console.log(stationId);
    $("#bookingStationIdDialog").val(stationId);
    const myModalEl = document.getElementById('createBookingDialog');
    const modal = bootstrap.Modal.getOrCreateInstance(myModalEl);
    modal.show();
}

async function createStation() {
    const data = {
        name: $('#name-dialog').val(),
        location: $('#location-dialog').val(),
        open: !!$('#open-dialog').val(),
        stationTypeId: $('#station-type-select').val()
    };
    console.log(data);
    const responseJson = await fetch(
        baseURL + `/api/stations`,
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
    const response = await responseJson.json();
    if (responseJson.ok) {
        let myModalEl = document.getElementById('createStationDialog')
        let modal = bootstrap.Modal.getInstance(myModalEl);
        modal.hide();
    }
}
async function createBooking() {
    const data = {
        name: $('#bookingNameDialog').val(),
        duration: $('#bookingDurationDialog').val(),
        date: $('#bookingDateDialog').val(),
        licenseNumber: $('#bookingLicenseDialog').val(),
        stationId: $('#bookingStationIdDialog').val()
    };
    const responseJson = await fetch(
        baseURL + `/api/bookings`,
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
    const response = await responseJson.json();
    if (responseJson.ok) {
        let myModalEl = document.getElementById('createBookingDialog')
        let modal = bootstrap.Modal.getInstance(myModalEl);
        modal.hide();
    }
}