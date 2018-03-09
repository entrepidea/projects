// Sentosa - An Automatic Algorithmic Trading System
// Copyright (C) 2015
// Henry Fuheng Wu (wufuheng AT gmail.com)
// Homepage:  http://www.quant365.com
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

#include "log.h"
#include <sentosaconfig.h>
#include <util.h>
#include <gconfig.h>
#include <httpclient.h>
#include <nanomsg/nn.h>
#include <nanomsg/pubsub.h>
#include <boost/filesystem.hpp>
namespace fs=boost::filesystem;

using std::lock_guard;

uulogging* uulogging::pinstance = nullptr;
mutex uulogging::loglock_;

uulogging::uulogging():logfiledescr(nullptr){
  Initialize();
}

uulogging::~uulogging(){
  fclose(logfiledescr);
}

uulogging& uulogging::R(){
  if (pinstance==nullptr){
    lock_guard<mutex> g(loglock_);
    if (pinstance==nullptr){
      pinstance = new uulogging();
    }
  }
  return *pinstance;
}

#ifdef FREEVERSION
void uulogging::Initialize(){}
void uulogging::Printf2File(const char *format, ...){}
void uulogging::Printf2FileNoTime(const char *format, ...){}

#else
//http://stackoverflow.com/questions/11655003/localtime-returns-gmt-for-windows-programs-running-on-cygwin-shells
/*** Initialize the object*/
void uulogging::Initialize(){
  string filename = getCurExePath();
  vector<string> vs = splitv2(filename, fs::path::preferred_separator);
  string fname;
  string tmp;
  if (CConfig::R()._mode == SYS_MODE::REPLAY_MODE){
    tmp = CConfig::R().LOGFILESIM;
    fname = tmp + ymd() + "." + (*vs.rbegin()) + ".lst";
  }else{
    tmp = CConfig::R().LOGFILE;
    fname = tmp + ymd() + "." + (*vs.rbegin()) + ".lst";
  }
  logfiledescr=fopen(fname.c_str(),"a+");
  setvbuf(logfiledescr,nullptr,_IONBF,0);//TLPI p237

  socket = nn_socket(AF_SP, NN_PUB);
  assert(socket >= 0);
  string endpoint = "tcp://*:" + CR(NN_LOG_PORT);
  assert(nn_bind(socket, endpoint.c_str()) >= 0);
}

void uulogging::Printf2File(const char *format, ...){
  lock_guard<mutex> g(loglock_);

  static char buf[1024*2];
  string tmp = nowMS();
  size_t sz = tmp.size();
  strcpy(buf, tmp.c_str());
  buf[sz] = ' ';

  va_list args;
  va_start(args, format);
  vsnprintf(buf+sz+1, 1024*2-sz-1, format, args);
  size_t buflen = strlen(buf);
  bytes += nn_send(socket, buf+sz+1, buflen-sz-1, 0);
  fwrite(buf, sizeof(char), buflen, logfiledescr);
  //printf("%luu\n", bytes);
  va_end(args);
}

void uulogging::Printf2FileNoTime(const char *format, ...){
  lock_guard<mutex> g(loglock_);
  static char buf[1024*2];
  va_list args;
  va_start(args, format);
  vsnprintf(buf, 1024*2, format, args);
  bytes += nn_send(socket, buf, strlen(buf), 0);
  fwrite(buf, sizeof(char), strlen(buf), logfiledescr);
  va_end(args);
}
#endif




Rlog::Rlog() :__fdesc(nullptr){}
Rlog::~Rlog(){ closefile(); }

void Rlog::closefile(){ if (__fdesc)fclose(__fdesc); }
void Rlog::setfname(const string& fname){
  closefile();
  headerAdded = false;
  TTPrint("rlog=%s\n",fname.c_str());
  __fdesc = fopen(fname.c_str(), "a+");
#if defined(WIN32) || defined(WIN64)
  setvbuf(__fdesc, nullptr, _IONBF, 0);//TLPI p237
#else
  setvbuf(__fdesc, nullptr, _IONBF, 0);//TLPI p237
#endif
}
bool Rlog::addheader(const string& h){
  if (headerAdded){
    return false;
  }
  headerAdded = true;
  if (h.back() != '\n'){
    string tmp = h + "\n";
    write(tmp.c_str());
    return true;
  }
  write(h.c_str());
  return true;
}
void Rlog::write(const char *format, ...){
  if (__fdesc)
  {
    static char buf[1024];
    va_list args;
    va_start(args, format);
    vsnprintf(buf, 1024, format, args);
    fwrite(buf, sizeof(char), strlen(buf), __fdesc);
    va_end(args);
  }
}
