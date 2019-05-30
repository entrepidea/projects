var myHeading = document.querySelector('h1');
myHeading.textContent = 'Hello, world!';

var myButton = document.querySelector('button');

function setUserName(){
    var myName = prompt('Please enter your name:');
    localStorage.setItem('name', myName);
    myHeading.innerHTML = 'JS is cool, do you think so, '+myName +'?';
}

if(!localStorage.getItem('name')){
    setUserName();
}
else{
    var myName = localStorage.getItem('name');
    myHeading.innerHTML = 'JS is cool, do you think so, '+myName +'?';
}
myButton.onclick = function(){
    setUserName();
}
