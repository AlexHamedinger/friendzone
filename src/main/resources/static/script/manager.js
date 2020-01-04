var likeText = 'Nicenstein <i style="font-size:18px;" class="fa fa-diamond"></i>';
var unlikeText = '<s>Nicenstein</s> <i style="font-size:18px;" class="fa fa-close"></i>';

var friends = '<i style="font-size:18px;" class="fa fa-minus"></i> Freund entfernen';
var notFriends = '<i style="font-size:18px;" class="fa fa-plus"></i> Freund hinzufügen';


function postManager() {
    //der EventListener lauscht nacht Klicks
    $(".likeButton").click(like);
    $(".friendsButton").click(befriend);
    //wird nach dem Seite laden ausgeführt
    $("body").ready(showPostDeleteButton);
    $("body").ready(loadFriendButton);
    $("body").ready(loadLikeButtons);
    
}


//#################
    //PAGE-LOAD-LISTENER
//#################

//zusätzlich falls es sich um die Post-Detail Seite handelt muss der "Post löschen" Button richtig angezeigt werden
function showPostDeleteButton() {
    var deletePostButton = $("#deletePostButton");

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

//falls es sich um eine "other User" Seite handelt muss der "Freund hinzufügen" Button richitg angezeigt werden
function loadFriendButton() {
    var friendsButtons = $(".friendsButton");
    
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
    
    $.get(
        "http://localhost:1889/likes/" + id,
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
    
    $.get(
        "http://localhost:1889/friends/" + id,
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





