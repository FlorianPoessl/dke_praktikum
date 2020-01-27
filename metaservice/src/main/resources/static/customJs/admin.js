function loadAdminPage() {
    loadMetadataTable("general");
    loadMetadataTable("movie");
    loadMetadataTable("snack");
};

function loadMetadataTable(type) {
    var tableData = [];
    $.ajax({
        type: "GET",
        url: "loadMetadata",
        data: {type: type},
        async: false,
        success: function (result) {
            tableData = JSON.parse(result);
        },
        error: function (result) {
            console.log("Something went wrong");
        }
    });

    var table = document.getElementById(type + 'MetadataTableBody')
    tableData.forEach(function (arrayElement) {
        var row = table.insertRow(-1);
        var cell1 = row.insertCell(-1);
        var cell2 = row.insertCell(-1);
        var cell3 = row.insertCell(-1);
        var cell4 = row.insertCell(-1);

        var rowNumber = table.rows.length;

        cell1.innerHTML = rowNumber;
        cell1.setAttribute("class", "uneditable indexColumn");
        cell2.setAttribute("class", "attributeColumn");
        cell3.setAttribute("class", "valuesColumn");
        cell3.setAttribute("title", "', ' als Trennzeichen verwenden");
        cell4.setAttribute("title", "', ' als Trennzeichen verwenden");
        cell2.innerHTML = arrayElement.attribute;
        cell3.innerHTML = arrayElement.values;
        cell4.innerHTML = arrayElement.synonyms;
    })

    $('#' + type + 'MetadataTable').editableTableWidget();

    $('#' + type + 'MetadataTableBody td').on('change', function(evt, newValue) {
        console.log(evt);
        console.log(newValue);
    });

    $('#' + type + 'MetadataTableBody td.uneditable').on('change', function(evt, newValue) {
        return false;
    });
}

function exitAdminPage() {
    this.saveTableData("general");
    this.saveTableData("movie");
    this.saveTableData("snack");
}

function saveTableData(type) {
    var table = document.getElementById(type + 'MetadataTableBody');
    var rowLength = table.rows.length;
    var jsonObject = {attribute: "", values: [], synonyms: []};
    var jsonArray = [];
    //loops through rows
    for (i = 0; i < rowLength; i++){
      //gets cells of current row
       var cells = table.rows.item(i).cells;
       //gets amount of cells of current row
       var cellLength = cells.length;
       //loops through each cell in current row
       for(var j = 0; j < cellLength; j++){
          // get your cell info here
          var cellVal = cells.item(j).innerHTML;
          if(j==1) {
               jsonObject.attribute = cellVal;
          } else if(j==2) {
               var objects = cellVal.split(/, |,/);
               jsonObject.values = objects;
          } else if(j==3) {
               var objects = cellVal.split(/, |,/);
               jsonObject.synonyms = objects;
          }
       }
       jsonArray.push(jsonObject);
       jsonObject = {attribute: "", values: [], synonyms: []};
    }
    var data = JSON.stringify(jsonArray);
    $.ajax({
        type: "POST",
        url: "saveMetadata",
        async: false,
        data: {metadata: data, type: type},
        error: function (result) {
            console.log("Something went wrong");
        }
    });
}

function addRowMetadataTable(type) {
    var table = document.getElementById(type + 'MetadataTableBody');
    var row = table.insertRow(-1);
    var cell1 = row.insertCell(-1);
    var cell2 = row.insertCell(-1);
    var cell3 = row.insertCell(-1);
    var cell4 = row.insertCell(-1);

    var rowNumber = table.rows.length;

    cell1.innerHTML = rowNumber;
    cell1.setAttribute("class", "uneditable indexColumn");
    cell2.setAttribute("class", "attributeColumn");
    cell3.setAttribute("class", "valuesColumn");
    cell3.setAttribute("title", "', ' als Trennzeichen verwenden");
    cell4.setAttribute("title", "', ' als Trennzeichen verwenden");
    cell2.innerHTML = "";
    cell3.innerHTML = "";
    cell4.innerHTML = "";

    $('#' + type + 'MetadataTable').editableTableWidget();

    $('#' + type + 'MetadataTableBody td').on('change', function(evt, newValue) {
        console.log(evt);
        console.log(newValue);
    });

    $('#' + type + 'MetadataTableBody td.uneditable').on('change', function(evt, newValue) {
        return false;
    });
}

