/*
 * Code below is to test the stream api from CryptoCompare, which looks like in this link: https://cryptoqween.github.io/streamer/current/
 * 
 */

var FIELDS = {
      'TYPE': 0x0 // hex for binary 0, it is a special case of fields that are always there
  ,   'MARKET': 0x0 // hex for binary 0, it is a special case of fields that are always there
  ,   'FROMSYMBOL': 0x0 // hex for binary 0, it is a special case of fields that are always there
  ,   'TOSYMBOL': 0x0 // hex for binary 0, it is a special case of fields that are always there
  ,   'FLAGS': 0x0 // hex for binary 0, it is a special case of fields that are always there
  ,   'PRICE': 0x1 // hex for binary 1
  ,   'BID': 0x2 // hex for binary 10
  ,   'OFFER': 0x4 // hex for binary 100
  ,   'LASTUPDATE': 0x8 // hex for binary 1000
  ,   'AVG': 0x10 // hex for binary 10000
  ,   'LASTVOLUME': 0x20 // hex for binary 100000
  ,   'LASTVOLUMETO': 0x40 // hex for binary 1000000
  ,   'LASTTRADEID': 0x80 // hex for binary 10000000
  ,   'VOLUMEHOUR': 0x100 // hex for binary 100000000
  ,   'VOLUMEHOURTO': 0x200 // hex for binary 1000000000
  ,   'VOLUME24HOUR': 0x400 // hex for binary 10000000000
  ,   'VOLUME24HOURTO': 0x800 // hex for binary 100000000000
  ,   'OPENHOUR': 0x1000 // hex for binary 1000000000000
  ,   'HIGHHOUR': 0x2000 // hex for binary 10000000000000
  ,   'LOWHOUR': 0x4000 // hex for binary 100000000000000
  ,   'OPEN24HOUR': 0x8000 // hex for binary 1000000000000000
  ,   'HIGH24HOUR': 0x10000 // hex for binary 10000000000000000
  ,   'LOW24HOUR': 0x20000 // hex for binary 100000000000000000
  ,   'LASTMARKET': 0x40000 // hex for binary 1000000000000000000, this is a special case and will only appear on CCCAGG messages
};

var unpack = function(value) {
  var valuesArray = value.split("~");
  //console.log('Breaking news! testing...');
  //console.log(valuesArray);
  var valuesArrayLenght = valuesArray.length;
  var mask = valuesArray[valuesArrayLenght - 1];
  var maskInt = parseInt(mask, 16);
  var unpackedCurrent = {};
  var currentField = 0;
  for (var property in this.FIELDS) {
    if (this.FIELDS[property] === 0) {
      unpackedCurrent[property] = valuesArray[currentField];
      currentField++;
    }
    else if (maskInt & this.FIELDS[property]) {
      //i know this is a hack, for cccagg, future code please don't hate me:(, i did this to avoid
      //subscribing to trades as well in order to show the last market
      if (property === 'LASTMARKET') {
        unpackedCurrent[property] = valuesArray[currentField];
      }
      else {
        unpackedCurrent[property] = parseFloat(valuesArray[currentField]);
      }
      currentField++;
    }
  }

  return unpackedCurrent;
};

var msg = "5~CCCAGG~BTC~USD~4~8258.2~1526789136~0.15~1243.665~72924483~6915.273301615161~57169824.77336909~43404.00768693134~360222025.0909697~8249.24~8296.23~8181.46~8241.99~8414.02~8150.85~Gatecoin~7ffe9";
var relt = unpack(msg);
console.log(relt);





var filterNumberFunctionPolyfill = function(value, decimals) {
  var decimalsDenominator = Math.pow(10, decimals);
  var numberWithCorrectDecimals = Math.round(value * decimalsDenominator) / decimalsDenominator;
  var parts = numberWithCorrectDecimals.toString().split(".");
  parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
  return parts.join(".");
}


var convertValueToDisplay = function(symbol, value, type, fullNumbers) {
  var prefix = '';
  var valueSign = 1;
  value = parseFloat(value);
  var valueAbs = Math.abs(value);
  var decimalsOnBigNumbers = 2;
  var decimalsOnNormalNumbers = 2;
  var decimalsOnSmallNumbers = 4;
  if (fullNumbers === true) {
    decimalsOnBigNumbers = 2;
    decimalsOnNormalNumbers = 0;
    decimalsOnSmallNumbers = 4;
  }
  if (symbol != '') {
    prefix = symbol + ' ';
  }
  if (value < 0) {
    valueSign = -1;
  }

  if (value == 0) {
    return prefix + '0';
  }

  if (value < 0.00001000 && value >= 0.00000100 && decimalsOnSmallNumbers > 3) {
    decimalsOnSmallNumbers = 3;
  }
  if (value < 0.00000100 && value >= 0.00000010 && decimalsOnSmallNumbers > 2) {
    decimalsOnSmallNumbers = 2;
  }
  if (value < 0.00000010 && value >= 0.00000001 && decimalsOnSmallNumbers > 1) {
    decimalsOnSmallNumbers = 1;
  }

  if (type == "short") {
    if (valueAbs > 10000000000) {
      valueAbs = valueAbs / 1000000000;
      return prefix + filterNumberFunctionPolyfill(valueSign * valueAbs, decimalsOnBigNumbers) + ' B';
    }
    if (valueAbs > 10000000) {
      valueAbs = valueAbs / 1000000;
      return prefix + filterNumberFunctionPolyfill(valueSign * valueAbs, decimalsOnBigNumbers) + ' M';
    }
    if (valueAbs > 10000) {
      valueAbs = valueAbs / 1000;
      return prefix + filterNumberFunctionPolyfill(valueSign * valueAbs, decimalsOnBigNumbers) + ' K';
    }
    if (valueAbs >= 1) {
      return prefix + filterNumberFunctionPolyfill(valueSign * valueAbs, decimalsOnNormalNumbers);
    }
    return prefix + (valueSign * valueAbs).toPrecision(decimalsOnSmallNumbers);
  }
  else {
    if (valueAbs >= 1) {
      return prefix + filterNumberFunctionPolyfill(valueSign * valueAbs, decimalsOnNormalNumbers);
    }

    return prefix + noExponents((valueSign * valueAbs).toPrecision(decimalsOnSmallNumbers));
  }
};

noExponents = function(value) {
  var data = String(value).split(/[eE]/);
  if (data.length == 1) return data[0];

  var z = '',
    sign = value < 0 ? '-' : '',
    str = data[0].replace('.', ''),
    mag = Number(data[1]) + 1;

  if (mag < 0) {
    z = sign + '0.';
    while (mag++) z += '0';
    return z + str.replace(/^\-/, '');
  }
  mag -= str.length;
  while (mag--) z += '0';
  return str + z;
};


	var dataUnpack = function(message) {
		var data = unpack(message);

		var from = data['FROMSYMBOL'];
		var to = data['TOSYMBOL'];
		var fsym = getSymbol(from);
		var tsym = getSymbol(to);
		var pair = from + to;
    //console.log(pair);

		if (!currentPrice.hasOwnProperty(pair)) {
			currentPrice[pair] = {};
		}

		for (var key in data) {
			currentPrice[pair][key] = data[key];
		}

		if (currentPrice[pair]['LASTTRADEID']) {
			currentPrice[pair]['LASTTRADEID'] = parseInt(currentPrice[pair]['LASTTRADEID']).toFixed(0);
		}
		currentPrice[pair]['CHANGE24HOUR'] = convertValueToDisplay(tsym, (currentPrice[pair]['PRICE'] - currentPrice[pair]['OPEN24HOUR']));
		currentPrice[pair]['CHANGE24HOURPCT'] = ((currentPrice[pair]['PRICE'] - currentPrice[pair]['OPEN24HOUR']) / currentPrice[pair]['OPEN24HOUR'] * 100).toFixed(2) + "%";
		displayData(currentPrice[pair], from, tsym, fsym);
    //console.log(currentPrice[pair]['OPEN24HOUR']+','+from+','+tsym+','+fsym);
    
	};



var DISPLAY_FIELDS = {
  'TYPE': { 'Show': false },
  'MARKET': { 'Show': true, 'Filter': 'Market' },
  'FROMSYMBOL': { 'Show': false },
  'TOSYMBOL': { 'Show': false },
  'FLAGS': { 'Show': false },
  'PRICE': { 'Show': true, 'Filter': 'Number', 'Symbol': 'TOSYMBOL' },
  'BID': { 'Show': true, 'Filter': 'Number', 'Symbol': 'TOSYMBOL' },
  'OFFER': { 'Show': true, 'Filter': 'Number', 'Symbol': 'TOSYMBOL' },
  'LASTUPDATE': { 'Show': true, 'Filter': 'Date', 'Format': 'yyyy MMMM dd HH:mm:ss' },
  'AVG': { 'Show': true, ' Filter': 'Number', 'Symbol': 'TOSYMBOL' },
  'LASTVOLUME': { 'Show': true, 'Filter': 'Number', 'Symbol': 'FROMSYMBOL' },
  'LASTVOLUMETO': { 'Show': true, 'Filter': 'Number', 'Symbol': 'TOSYMBOL' },
  'LASTTRADEID': { 'Show': true, 'Filter': 'String' },
  'VOLUMEHOUR': { 'Show': true, 'Filter': 'Number', 'Symbol': 'FROMSYMBOL' },
  'VOLUMEHOURTO': { 'Show': true, 'Filter': 'Number', 'Symbol': 'TOSYMBOL' },
  'VOLUME24HOUR': { 'Show': true, 'Filter': 'Number', 'Symbol': 'FROMSYMBOL' },
  'VOLUME24HOURTO': { 'Show': true, 'Filter': 'Number', 'Symbol': 'TOSYMBOL' },
  'OPENHOUR': { 'Show': true, 'Filter': 'Number', 'Symbol': 'TOSYMBOL' },
  'HIGHHOUR': { 'Show': true, 'Filter': 'Number', 'Symbol': 'TOSYMBOL' },
  'LOWHOUR': { 'Show': true, 'Filter': 'Number', 'Symbol': 'TOSYMBOL' },
  'OPEN24HOUR': { 'Show': true, 'Filter': 'Number', 'Symbol': 'TOSYMBOL' },
  'HIGH24HOUR': { 'Show': true, 'Filter': 'Number', 'Symbol': 'TOSYMBOL' },
  'LOW24HOUR': { 'Show': true, 'Filter': 'Number', 'Symbol': 'TOSYMBOL' },
  'LASTMARKET': { 'Show': true, 'Filter': 'String' },
  'CHANGE24HOUR': { 'Show': true, 'Filter': 'String' },
  'CHANGE24HOURPCT': { 'Show': true, 'Filter': 'String' },
  'FULLVOLUMEFROM': { 'Show': true, 'Filter': 'Number', 'Symbol': 'FROMSYMBOL' },
  'FULLVOLUMETO': { 'Show': true, 'Filter': 'Number', 'Symbol': 'TOSYMBOL' }
};


var displayData = function(messageToDisplay, from, tsym, fsym) {
		var priceDirection = messageToDisplay.FLAGS;
		var fields = DISPLAY_FIELDS;

		for (var key in fields) {
			if (messageToDisplay[key]) {
				if (fields[key].Show) {
					switch (fields[key].Filter) {
						case 'String':
              
							console.log('#' + key + '_' + from+'='+ messageToDisplay[key]);
							break;
						case 'Number':
							var symbol = fields[key].Symbol == 'TOSYMBOL' ? tsym : fsym;
							console.log('#' + key + '_' + from+'='+ convertValueToDisplay(symbol, messageToDisplay[key]))
							break;
					}
				}
			}
		}

		//$('#PRICE_' + from).removeClass();
		if (priceDirection & 1) {
			//$('#PRICE_' + from).addClass("up");
		}
		else if (priceDirection & 2) {
			//$('#PRICE_' + from).addClass("down");
		}

		if (messageToDisplay['PRICE'] > messageToDisplay['OPEN24HOUR']) {
			//$('#CHANGE24HOURPCT_' + from).removeClass();
			//$('#CHANGE24HOURPCT_' + from).addClass("pct-up");
		}
		else if (messageToDisplay['PRICE'] < messageToDisplay['OPEN24HOUR']) {
			//$('#CHANGE24HOURPCT_' + from).removeClass();
			//$('#CHANGE24HOURPCT_' + from).addClass("pct-down");
		}
	};

var SYMBOL = {
  'BTC': 'Ƀ',
  'LTC': 'Ł',
  'DAO': 'Ð',
  'USD': '$',
  'CNY': '¥',
  'EUR': '€',
  'GBP': '£',
  'JPY': '¥',
  'PLN': 'zł',
  'RUB': '₽',
  'ETH': 'Ξ',
  'GOLD': 'Gold g',
  'INR': '₹',
  'BRL': 'R$'
};

var getSymbol = function(symbol) {
  return SYMBOL[symbol] || symbol;
};


/*
var currentPrice = {};
var msg = '5~CCCAGG~BTC~USD~4~8258.2~1526789136~0.15~1243.665~72924483~6915.273301615161~57169824.77336909~43404.00768693134~360222025.0909697~8249.24~8296.23~8181.46~8241.99~8414.02~8150.85~Gatecoin~7ffe9';
dataUnpack(msg);
*/
console.log(noExponents(1234.56789));

