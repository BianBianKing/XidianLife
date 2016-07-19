$(document).ready( function(){
    
	var cTime = new Date(), month = cTime.getMonth()+1, year = cTime.getFullYear();
	
	theMonths = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
	
	theDays = ["S", "M", "T", "W", "T", "F", "S"];
	
	
	/*var daysArray = $("#dayListP")[0].innerHTML;
    var days = daysArray.substring(2, daysArray.length-1);
    var daysInfo = days.split(",");*/
    
	var dateArray = $("#dateListP")[0].innerHTML;
    var dates = dateArray.substring(1, dateArray.length-1);
    var datesInfo = dates.split(",");
    var daysInfo = [];
    var monthInfo = []; 
    var yearInfo = [];
    
    events = [];
    for(var i=0; i<datesInfo.length; i++){
    	events[i] = [];
    	var dmy = datesInfo[i].split("/");
    	daysInfo[i] = dmy[2]+""; monthInfo[i] = dmy[1]+""; yearInfo[i] = dmy[0]+"";
    	if(daysInfo[i].substring(0,1) == "0"){
    		daysInfo[i] = daysInfo[i].substring(1,2);
    	}
    	if(monthInfo[i].substring(0,1) == "0"){
    		monthInfo[i] = monthInfo[i].substring(1,2);
    	}
    	events[i][0] = daysInfo[i].trim()+"/"+monthInfo[i].trim()+"/"+yearInfo[i].trim();
    	events[i][1] = '';
    	events[i][2] = '#';
    	events[i][3] = '#ffba4d';
    }
    console.log(events);

    $('#calendar').calendar({
        months: theMonths,
        days: theDays,
        events: events,
        popover_options:{
            placement: 'top',
            html: true
        }
    });
});