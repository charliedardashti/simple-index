$("#loader").hide();

$("#filter").click(function(event) {
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    var validationFailed = false;
    if(startDate===""){
        $("#startDate").addClass("validate_Date");
        $("#vstdt").removeClass("validate_input");
        $("#vstdt").addClass("invalidate_input");
        validationFailed = true;
    }
    if(endDate===""){
        $("#endDate").addClass("validate_Date");
        $("#vendt").removeClass("validate_input");
        $("#vendt").addClass("invalidate_input");
        validationFailed = true;
    }

    if(validationFailed){
        $("#tbody").hide();
        return;
    } else {
        $("#vstdt").addClass("validate_input");
        $("#vstdt").removeClass("invalidate_input");
        $("#vendt").addClass("validate_input");
        $("#vendt").removeClass("invalidate_input");
        $("#startDate").removeClass("validate_Date");
        $("#endDate").removeClass("validate_Date");
    }
    $("#tbody").hide();
    $("#loader").show();
    $("#table").load("/filter?startDate="+startDate+"&endDate="+endDate, function(data){
        $("#loader").hide();
        $("#tbody").show();
    });
});
