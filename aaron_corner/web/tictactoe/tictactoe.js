let xo="O"
const winningPositions = [
    new Set(["1",  "2",  "3"]),
    new Set(["4",  "5",  "6"]),
    new Set(["7",  "8",  "9"]),
    new Set(["1",   "4",   "7"]),
    new Set(["2",   "5",    "8"]),
    new Set(["3",    "6",    "9"]),
    new Set(["1",    "5",    "9"]),
    new Set(["3",     "5",     "7"])
];
cells=[0,0,0,0,0,0,0,0,0]
function isTaken(index){
    if(cells[index]==1){
        return true
    }
    else{
        return false
    }
}


xCurrentPositions = []
oCurrentPositions = []


function isWinPostion(unknowPos, winPos){
    return [...winPos].every(pos => unknowPos.has(pos));
}

function togglexo(id){
    if(!isTaken(id)){
        cells[id]=1;
    }
    else{
        return
    }
    xo=xo=="O"?"X":"O"
    document.getElementById(id).innerHTML=xo;
    if(xo=="X") {
        xCurrentPositions.push(id);
    }
    else{
        oCurrentPositions.push(id);
    }
    if(xCurrentPositions.length>=3 || oCurrentPositions.length>=3){
        console.log("xCurrent position: "+xCurrentPositions);
        xPosSet = new Set(xCurrentPositions);
        oPosSet = new Set(oCurrentPositions);
        for (const winPos of winningPositions){
            console.log("wining position: "+winPos);
            if(isWinPostion(xPosSet, winPos)){
                document.getElementById('xWon').innerHTML="Player 1 Won!";
                rematch.style.display="block";
                break;
            }
            else if(isWinPostion(oPosSet, winPos)){
                document.getElementById('oWon').innerHTML="Player 2 Won!";
                rematch.style.display="block";
                break;
            }
        }
    }
}
function init(xWon,oWon){
    document.getElementById(xWon).innerHTML="";
    document.getElementById(oWon).innerHTML="";
}