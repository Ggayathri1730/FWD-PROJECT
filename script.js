
function login(){

let user=document.getElementById("username").value;
let pass=document.getElementById("password").value;

let userRegex=/^[A-Za-z0-9]+$/;
let passRegex=/^(?=.*[A-Z])(?=.*[0-9]).{6,}$/;

if(!userRegex.test(user)){
document.getElementById("error").innerText="Username must be letters and numbers";
return;
}

if(!passRegex.test(pass)){
document.getElementById("error").innerText="Password must contain capital letter and number";
return;
}

window.location="route.html";
}

function createPassengers(){

let count=document.getElementById("count").value;

let div=document.getElementById("passengerNames");

div.innerHTML="";

for(let i=1;i<=count;i++){

div.innerHTML+=`Passenger ${i} <input type="text" class="pname">`;

}

}
// Store selected values
let selectedTrip = "";
let selectedTime = "";

function setTrip(type){

document.getElementById("trip").value = type;

// remove previous selection
let buttons=document.querySelectorAll(".trip-buttons button");
buttons.forEach(btn=>btn.classList.remove("active"));

// highlight selected
event.target.classList.add("active");

}

function setTime(time){

document.getElementById("time").value = time;

// remove previous selection
let buttons=document.querySelectorAll(".time-buttons button");
buttons.forEach(btn=>btn.classList.remove("active"));

// highlight selected
event.target.classList.add("active");

}


// Passenger Counter

function increase(){

let count = document.getElementById("count");

if(count.value < 6){
count.value++;
}

}

function decrease(){

let count = document.getElementById("count");

if(count.value > 1){
count.value--;
}

}



// CONTINUE BUTTON VALIDATION
function continueBooking(){

let start = document.getElementById("start").value;
let end = document.getElementById("end").value;
let trip = document.getElementById("trip").value;
let time = document.getElementById("time").value;
let count = document.getElementById("count").value;

let error = document.getElementById("error");

if(start === ""){
error.innerText = "Please select Start Station";
return;
}

if(end === ""){
error.innerText = "Please select Stop Station";
return;
}

if(start === end){
error.innerText = "Start and Stop Station cannot be same";
return;
}

if(trip === ""){
error.innerText = "Please select Trip Type";
return;
}

if(time === ""){
error.innerText = "Please select Time Slot";
return;
}

if(count === "" || count < 1){
error.innerText = "Please select passengers";
return;
}

let names = [];

for(let i=1;i<=count;i++){
names.push("Passenger"+i);
}

let data = {
start:start,
end:end,
trip:trip,
time:time,
count:count,
names:names
};

localStorage.setItem("metroData",JSON.stringify(data));

window.location.href="summary.html";

}