//Dieses Javascript wird zum richtigen Anzeigen der Like-Buttons und zum Verarbeiten der Likes verwendet
var likeText = 'Nicenstein <i style="font-size:18px;" class="fa fa-diamond"></i>';
var unlikeText = '<s>Nicenstein</s> <i style="font-size:18px;" class="fa fa-close"></i>';


function postManager() {
    //der EventListener lauscht nacht Like-klicks
    $(".likeButton").click(like);
    //wird nach dem Seite laden ausgeführt
    $("body").ready(showPostDeleteButton);
    $("body").ready(loadLikeButtons);
    
}



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