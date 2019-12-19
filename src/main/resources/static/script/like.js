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
        
    if($("#"+id).html() == 'Like <i style="font-size:18px;" class="fa fa-thumbs-o-up"></i>') {
        $("#"+id).html('Unlike <i style="font-size:18px;" class="fa fa-close"></i>');
        $("#"+id).next().html(++likes);
    }
    else if($("#"+id).html() == 'Unlike <i style="font-size:18px;" class="fa fa-close"></i>') {
        $("#"+id).html('Like <i style="font-size:18px;" class="fa fa-thumbs-o-up"></i>');
        $("#"+id).next().html(--likes);
    }
}

function loadLikeButtons() {
    
    var buttons = $(".likeButton");
    
    buttons.each(function() {
    
    if($(this).html() == 'unlike') {
        $(this).html('Unlike <i style="font-size:18px;" class="fa fa-close"></i>');
    }
    else if($(this).html() == 'like') {
        $(this).html('Like <i style="font-size:18px;" class="fa fa-thumbs-o-up"></i>');
    }
        
    });
    
}