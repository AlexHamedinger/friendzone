//Dieses Javascript wird zum Anzeigen einer Live-Vorschau beim Erstellen neuer Posts verwendet

function createPreview() {
    $("#vorschauButton").click(prieview);
}

function prieview() {
    var titel = $("#titel").val();
    if(titel == "") {
        $("#vorschauTitel").removeClass();
        $("#vorschauTitel").addClass("text-center");
        titel = "Titel eingeben!"
    } else {
        $("#vorschauTitel").removeClass();
        $("#vorschauTitel").addClass("text-left");
    }
    $("#vorschauTitel").html(titel);
    
    var img_url = "";
    if($("#customFile")[0].files[0] != null) {
        img_url = (window.URL || window.webkitURL).createObjectURL($("#customFile")[0].files[0]);
    }
    $("#vorschauImage").attr("src", img_url);
}