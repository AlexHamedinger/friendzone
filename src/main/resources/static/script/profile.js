function profileManager() {
    $("#customFile").change(changeLable);
}

function changeLable() {
    var lable = $("#fileLable");
    var file = $("#customFile").val();
    var fileArray = file.split("fakepath".trim());
    var fileText = fileArray[fileArray.length - 1];
    fileText = fileText.substr(1, fileText.length - 1);
    
    lable.html(fileText);
}