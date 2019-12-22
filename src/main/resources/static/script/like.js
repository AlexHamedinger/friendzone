//Dieses Javascript wird zum richtigen Anzeigen der Like-Buttons und zum Verarbeiten der Likes verwendet
var likeText = 'Nicenstein <i style="font-size:18px;" class="fa fa-diamond"></i>';
var unlikeText = '<s>Nicenstein</s> <i style="font-size:18px;" class="fa fa-close"></i>';


function likeManager() {
    $(".likeButton").click(like);
    $("body").ready(loadLikeButtons)
}

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

function loadLikeButtons() {

    var buttons = $(".likeButton");

    buttons.each(function() {

    if($(this).html() == 'unlike') {
        $(this).html(unlikeText);
    }
    else if($(this).html() == 'like') {
        $(this).html(likeText);
    }
        
    });
    
}