function executeMovieButton() {
     console.log("Executing Movie Button");
     window.open("movies", "_self");
};

function returnToHome() {
    console.log("Returning to home");
    window.open("index", "_self");
};

function executeSnackButton() {
    console.log("Executing Snack Button");
    window.open("snacks", "_self");
};

function executeAdminButton() {
    console.log("Executing Snack Button");
    window.open("admin", "_self");
}

function executeSearchMovieButton() {
    var movieName = document.getElementById("movieNameInput").value;
    var movieDate = document.getElementById("movieDateInput").value;
    var movieTime = document.getElementById("movieTimeInput").value;
    var movieData = [];
    console.log("Executing search for movie " + movieName);
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/searchMovies",
        data: {movieName: movieName, movieDate: movieDate, movieTime: movieTime},
        async: false,
        success: function (result) {
            movieData = JSON.parse(result);
        },
        error: function (result) {
            console.log("Something went wrong");
        }
    });

    console.log(movieData);
    var table = document.getElementById('movieResultTable');
    $("#movieResultTable tr").remove();
    movieData.forEach(function (arrayElement) {
        console.log(arrayElement)
        var row = table.insertRow(-1);
        var cell1 = row.insertCell(-1);
        var cell2 = row.insertCell(-1);
        var cell3 = row.insertCell(-1);
        var cell4 = row.insertCell(-1);
        var cell5 = row.insertCell(-1);
        var cell6 = row.insertCell(-1);

        console.log(arrayElement.title);
        cell1.textContent = arrayElement.title;
        cell2.textContent = arrayElement.cinema;
        cell3.textContent = arrayElement.date;
        cell4.textContent = arrayElement.price;
        cell5.textContent = arrayElement.timestart;
        cell6.textContent = arrayElement.timeend;
    })

}

function executeSearchSnackButton() {
    var snackName = document.getElementById("snackNameInput").value;
    console.log("Executing search for snack " + snackName);
    var snackData = [];
    $.ajax({
        type: "GET",
        url: "searchSnacks",
        data: {snackName: snackName},
        async: false,
        success: function (result) {
            snackData = JSON.parse(result);
        },
        error: function (result) {
            console.log("Something went wrong");
        }
    });

    console.log(snackData);
    var table = document.getElementById('snackResultTable');
    $("#snackResultTable tr").remove();
    snackData.forEach(function (arrayElement) {
        console.log(arrayElement)
        var row = table.insertRow(-1);
        var cell1 = row.insertCell(-1);
        var cell2 = row.insertCell(-1);
        var cell3 = row.insertCell(-1);
        var cell4 = row.insertCell(-1);

        console.log(arrayElement.title);
        cell1.textContent = arrayElement.name;
        cell2.textContent = arrayElement.preis;
        cell3.textContent = arrayElement.groesse;
        cell4.textContent = arrayElement.cinema;
    })

}