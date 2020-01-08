var likeText = 'Nicenstein <i style="font-size:18px;" class="fa fa-diamond"></i>';
var unlikeText = '<s>Nicenstein</s> <i style="font-size:18px;" class="fa fa-close"></i>';

var friends = '<i style="font-size:18px;" class="fa fa-minus"></i> Freund entfernen';
var notFriends = '<i style="font-size:18px;" class="fa fa-plus"></i> Freund hinzufügen';


function friendZoneController() {
    //der EventListener lauscht nacht Klicks
    $(".likeButton").click(like);
    $(".friendsButton").click(befriend);
    $("#vorschauButton").click(preview);
    //der EventListener lauscht nach Change
    $("#customFile").change(changeInputLable);
    //wird nach dem Seite laden ausgeführt
    $("body").ready(showCorrectRadioAndSelect);
    $("body").ready(showPostDeleteButton);
    $("body").ready(loadFriendButton);
    $("body").ready(loadLikeButtons);
    
}


//#################
    //PAGE-LOAD-LISTENER
//#################

//zusätzlich falls es sich um die Pinnwand Seite handelt müssen die Radio-Buttons und das
function showCorrectRadioAndSelect() {
    var message = $("#loadMessage").html();
    if(message == undefined) {
        return;
    }
    var show = message.split("-")[0];
    var order = message.split("-")[1];
    
    $("#" + show + "Label").addClass("border-dark");
    $("#" + show).prop("checked", true);
    $("#" + order).attr('selected',true);
    $("#empty").remove();
    
}

//zusätzlich falls es sich um die Post-Detail Seite handelt muss der "Post löschen" Button richtig angezeigt werden
function showPostDeleteButton() {
    var deletePostButton = $("#deletePostButton");
    if(!deletePostButton.length) {
        return;
    }
    
    if(deletePostButton.html() == 'mine') {
        deletePostButton.html('<i style="font-size:18px;"class="fa fa-trash-o"></i> löschen');
        deletePostButton.css("background-color", "#e3f2fd");
        deletePostButton.css("color", "");
        deletePostButton.addClass("btn");
        deletePostButton.addClass("btn-light");
        deletePostButton.addClass("btn-outline-dark"); 
    } else {
        deletePostButton.remove();
    }
}

//falls vorhanden muss der "Freund hinzufügen" Button richitg angezeigt werden
function loadFriendButton() {
    var friendsButtons = $(".friendsButton");
    if(!friendsButtons.length) {
        return;
    }
    
    friendsButtons.each(function() {

    if($(this).html() == "friends") {
        $(this).html(friends);
    } else if ($(this).html() == "notFriends") {
        $(this).html(notFriends);
    }
        
    });
    
}

//damit die Like Buttons richtig angezeigt werden
function loadLikeButtons() {
    
    var likeButtons = $(".likeButton");
    if(!likeButtons.length) {
        return;
    }
    
    likeButtons.each(function() {

    if($(this).html() == 'unlike') {
        $(this).html(unlikeText);
    }
    else if($(this).html() == 'like') {
        $(this).html(likeText);
    }
        
    });

}


//#################
    //KLICK-LISTENER
//#################

//falls auf Like gedrückt wird
function like() {

    var id = this.id;
    var likes = $("#"+id).next().html();
    var baseURL = window.location.origin;
    
    $.get(
        baseURL + "/likes/" + id,
        {},
        function(data) {
        }
    );

    if($("#"+id).html() == likeText) {
        $("#"+id).html(unlikeText);
        $("#"+id).next().html(++likes);
    }
    else if($("#"+id).html() == unlikeText) {
        $("#"+id).html(likeText);
        $("#"+id).next().html(--likes);
    }
}

//falls auf "Freund hinzufügen" geklickt wird
function befriend() {

    var id = this.id;
    var baseURL = window.location.origin;
    
    $.get(
        baseURL + "/friends/" + id,
        {},
        function(data) {
        }
    );
    
    if($("#"+id).html() == friends) {
        $("#"+id).html(notFriends);
    }
    else if($("#"+id).html() == notFriends) {
        $("#"+id).html(friends);
    }
    
}

//wird zum Anzeigen einer Live-Vorschau beim Erstellen neuer Posts benötigt
function preview() {
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


//#################
    //CHANGE-LISTENER
//#################

//ändert die Beschriftung des File-Uploads
function changeInputLable() {
    var lable = $("#fileLable");
    var file = $("#customFile").val();
    var fileArray = file.split("fakepath".trim());
    var fileText = fileArray[fileArray.length - 1];
    fileText = fileText.substr(1, fileText.length - 1);
    
    lable.html(fileText);
}



