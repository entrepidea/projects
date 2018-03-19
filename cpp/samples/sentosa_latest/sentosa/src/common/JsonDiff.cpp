// Sentosa - An Automatic Algorithmic Trading System
// Website: http://www.quant365.com
// Author: Henry Fuheng Wu (wufuheng AT gmail.com)
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

#include <rapidjson/rapidjson.h>
#include <rapidjson/document.h>
#include <rapidjson/writer.h>
#include <rapidjson/stringbuffer.h>
#include <iostream>
#include "JsonDiff.h"

using namespace rapidjson;
using namespace std;

class JsonDiff{
public:

  JsonDiff(Document* x, Document* y){
    d0 = x;
    d1 = y;
    alor = &d0->GetAllocator();
  }
  Document* d0;
  Document* d1;
  Document::AllocatorType* alor;


/*
    $./jdiff
    {"data":[{"_mkdata":{"sym":"SINA"}},{"_5su":{}},{"_mkdata":{}},{"_5su":{}}]}
    + && v.MemberCount()>0
    $./jdiff
    {"data":[{"_mkdata":{"sym":"SINA"}}]}

*/

  // array
  Value CompareArray(const Value& x, const Value& y) {
    Value a(kArrayType);
    if (x.Size()==0 ||
        (x.Size()!=y.Size() &&
        !x[0].IsArray() &&
        !x[0].IsObject()))
    {
      return Value(y,*alor);
    }
    for (int i = 0; i < x.Size(); ++i) {
      const Value& xi = x[i];
      const Value& yi = y[i];
      Type t = xi.GetType();
      switch (t) {
      case kObjectType: {
        Value v = CompareObject(xi, yi);
        if (!v.IsNull()) {
          if ((v.IsObject() && v.MemberCount()>0) || (v.IsArray() && v.Size()>0)){
            a.PushBack(Value(v,*alor), *alor);
          }
        }
      }
        break;               //!< object
      case kArrayType: {
        Value v = CompareArray(xi, yi);
        if (!v.IsNull()) {
          if ((v.IsObject() && v.MemberCount()>0) || (v.IsArray() && v.Size()>0)){
            a.PushBack(Value(v,*alor), *alor);
          }
        }
      }
        break;               //!< array
      case kNullType:
      case kFalseType:
      case kTrueType:
      case kStringType:
      case kNumberType:
      {
        if (x[i] != y[i]) {
          a.PushBack(Value(y[i],*alor), *alor);
        }
      }
      }
    }
    return a;
  }

  // dict
  Value CompareObject(const Value& x, const Value& y) {
    Value a(kObjectType);
    if (x.IsArray() && x.Size() == 0)
      return a;
    //Document::AllocatorType& allocator = (Document)x.GetAllocator();
    Value sym(kStringType);
    for (auto itr = x.MemberBegin(); itr != x.MemberEnd(); ++itr) {
      const char* k = itr->name.GetString();

      if (strcmp(k,"sym")==0){
        //a.AddMember(Value(k,*alor), Value(y[k],*alor), *alor);
        sym = Value(y[k],*alor);
        continue;
      }
      
      Type t = itr->value.GetType();
      switch (t) {
      case kObjectType: {
        Value v = CompareObject(itr->value, y[k]);
        if (!v.IsNull()) {
          if ((v.IsObject() && v.MemberCount()>0) || (v.IsArray() && v.Size()>0)){
            a.AddMember(Value(k,*alor), Value(v,*alor), *alor);
          }
        }
      }
        break;               //!< object
      case kArrayType: {
        Value v = CompareArray(itr->value, y[k]);
        if (!v.IsNull())
        {
          if ((v.IsObject() && v.MemberCount()>0) || (v.IsArray() && v.Size()>0)){
            a.AddMember(Value(k,*alor), Value(v,*alor), *alor);
          }

        }
      }
        break;               //!< array
      case kNullType:       //!< null
      case kFalseType: //!< false
      case kTrueType:  //!< true
      case kStringType:  //!< string
      case kNumberType:  //!< number
      {
        if (y[k] != itr->value) {
          a.AddMember(Value(k,*alor), Value(y[k],*alor), *alor);
        }
      }
      }
    }
    if (a.MemberCount()>0 && sym.GetStringLength()>0){
      a.AddMember(Value("sym",*alor), sym, *alor);
    }
    return a;
  }


  Document run() {
    const Value& a = (*d0)["data"];
    const Value& b = (*d1)["data"];
    Document d;
    if (!d.Parse<0>("{}").HasParseError()) {
      Type t = a.GetType();
      switch (t) {
      case kObjectType: {
        Value v = CompareObject(a, b);
        d.AddMember("data", v, *alor);
      }
        break;
      case kArrayType: {
        Value v = CompareArray(a, b);
        d.AddMember("data", v, *alor);
      }
        break;
      case kNullType:       //!< null
      case kFalseType: //!< false
      case kTrueType:  //!< true
      case kStringType:  //!< string
      case kNumberType:  //!< number
      default:
        if (*d0 != *d1) {
          d1->Swap(d);
        }
      }
    }

    d.AddMember("dt", (*d1)["dt"], *alor);
    return d;
  }

};

string jsondiff(const char* json0, const char* json1) {
  Document d0, d1;
  d0.Parse(json0);
  d1.Parse(json1);
  JsonDiff jd(&d0, &d1);
  Document d2 = jd.run();

  StringBuffer buffer;
  Writer<StringBuffer> writer(buffer);
  d2.Accept(writer);

  return buffer.GetString();
}

string jsondiff(string json0, string json1) {
  //printf("json0:%s\n", json0.c_str());
  //printf("json1:%s\n", json1.c_str());
  if (json0.empty())
    return json1;
  return jsondiff(json0.c_str(), json1.c_str());
}

#if 0
int main() {
    // 1. Parse a JSON string into DOM.
    const char* json0 = "{\"dt\":\"2015-06-26 10:58:01\",\"data\":[{\"_tinfo\": {\"syms\": \"SINA\",\"uPNL\": 118.40,\"aPNL\": 0,\"statuz\": 2,\"ps\": [59.37],\"vo\": [-50],\"inve\": 2968.94,\"lcc\": 4000,\"cR\": 1031.05}\n"
        "},{\"bar1d\": {\"symbol\": \"SINA\",\"w\": \"41.83,41.16,41.08,48.97,50.63,53.04,52.27,55.59,56.60,56.35,57.87,58.50,59.97,59.71,57.81,60.35,59.29,57.96,58.18,58.27,57.38,56.37\",\"v\": \"12094.00,10800.00,7289.00,120823.00,35454.00,43063.00,15325.00,48198.00,33098.00,18960.00,25245.00,26580.00,33120.00,18132.00,21503.00,23363.00,12792.00,16616.00,8140.00,5871.00,1898.00,9014.00\",\"vrange\": \"\",\"ind\": {\"dev\": 5.97,\"ma\": 54.50,\"rsi\": 73.46,\"roc\": 34.75}}\n"
        "},{\"_5su\":{\"symbol\":\"SINA\",\"w\":54.030,\"v\":11,\"dt\":\"2015-06-26 10:58:01\"}},{\"_mkdata\": {\"sym\": \"SINA\",\"bid\": 0,\"ask\": 0,\"bs\": 0,\"as\": 0,\"LP\": 57.00,\"vrate\": 0,\"trate\": 0,\"LRTH\": 0,\"OCVol\": 0,\"OPVol\": 0,\"PCVOR\": -1,\"OCOI\": 0,\"OPOI\": 0,\"PCOIR\": -1,\"pos\": -50,\"avgP\": 59.37,\"sal\": true}\n"
        "},{\"mktstatic\": {\"sym\": \"SINA\",\"H\": 0,\"L\": 0,\"C\": 0,\"O\": 0,\"WH13\": 0,\"WL13\": 0,\"WH26\": 0,\"WL26\": 0,\"WH52\": 0,\"WL52\": 0}\n"
        "},{\"_portfolio\": {\"uPNL\": 118.40,\"aPNL\": 0,\"lcc\": 4000,\"inve\": 2968.94,\"cR\": 1031.05,\"nlc\": 4118.39}\n"
        "}]}\n"
        "";
    const char* json1 = "{\"dt\":\"2015-06-26 10:58:07\",\"data\":[{\"_tinfo\": {\"syms\": \"SINA\",\"uPNL\": 118.40,\"aPNL\": 0,\"statuz\": 2,\"ps\": [59.37],\"vo\": [-50],\"inve\": 2968.94,\"lcc\": 4000,\"cR\": 1031.05}\n"
        "},{\"bar1d\": {\"symbol\": \"SINA\",\"w\": \"41.83,41.16,41.08,48.97,50.63,53.04,52.27,55.59,56.60,56.35,57.87,58.50,59.97,59.71,57.81,60.35,59.29,57.96,58.18,58.27,57.38,56.37\",\"v\": \"12094.00,10800.00,7289.00,120823.00,35454.00,43063.00,15325.00,48198.00,33098.00,18960.00,25245.00,26580.00,33120.00,18132.00,21503.00,23363.00,12792.00,16616.00,8140.00,5871.00,1898.00,9014.00\",\"vrange\": \"\",\"ind\": {\"dev\": 5.97,\"ma\": 54.50,\"rsi\": 73.46,\"roc\": 34.75}}\n"
        "},{\"_5su\":{\"symbol\":\"SINA\",\"w\":54.030,\"v\":11,\"dt\":\"2015-06-26 10:58:07\"}},{\"_mkdata\": {\"sym\": \"SINA\",\"bid\": -1,\"ask\": -1,\"bs\": 0,\"as\": 0,\"LP\": 57.00,\"vrate\": 0,\"trate\": 0,\"LRTH\": 0,\"OCVol\": 0,\"OPVol\": 0,\"PCVOR\": -1,\"OCOI\": 0,\"OPOI\": 0,\"PCOIR\": -1,\"pos\": -50,\"avgP\": 59.37,\"sal\": true}\n"
        "},{\"mktstatic\": {\"sym\": \"SINA\",\"H\": 0,\"L\": 0,\"C\": 57.00,\"O\": 0,\"WH13\": 0,\"WL13\": 0,\"WH26\": 0,\"WL26\": 0,\"WH52\": 0,\"WL52\": 0}\n"
        "},{\"_portfolio\": {\"uPNL\": 118.40,\"aPNL\": 0,\"lcc\": 4000,\"inve\": 2968.94,\"cR\": 1031.05,\"nlc\": 4118.39}\n"
        "}]}";

    Document d0;
    d0.Parse(json0);

    Document d1;
    d1.Parse(json1);

    jdiff jd(&d0,&d1);
    Document d2 = jd.run();




    // 2. Modify it by DOM.
    //Value& s = d0["stars"];
    //s.SetInt(s.GetInt() + 1);

    // 3. Stringify the DOM
    StringBuffer buffer;
    Writer<StringBuffer> writer(buffer);
    d2.Accept(writer);

    // Output {"project":"rapidjson","stars":11}
    std::cout << buffer.GetString() << std::endl;
    return 0;
}
#endif
