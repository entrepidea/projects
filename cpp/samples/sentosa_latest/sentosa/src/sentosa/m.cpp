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

#include <iostream>
#include <unistd.h>
#include <dirent.h>
#include <sys/types.h> // for opendir(), readdir(), closedir()
#include <sys/stat.h> // for stat()
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdarg.h>

#include <fcntl.h>

using namespace std;

#define PROC_DIRECTORY "/proc/"

int IsNumeric(const char* p) {
  for (; *p; p++) {
    if (*p < '0' || *p > '9') {
      return 0; // false
    }
  }
  return 1; // true
}

string readcmdline(const char* proc_cmd) {
  const int BUFSIZE = 4096; // should really get PAGESIZE or something instead...
  char buffer[BUFSIZE]; // dynamic allocation rather than stack/global would be better

  int fd = open(proc_cmd, O_RDONLY);
  int nbytesread = read(fd, buffer, BUFSIZE);
  char *end = buffer + nbytesread;
  string cmd;
  string space(" ");
  for (char *p = buffer; p < end;) {
    cmd += space + p;
    //cout << p << endl;
    while (*p++)
      ; // skip until start of next 0-terminated section
  }

  close(fd);
  return cmd;
}

pid_t GetPIDbyName(const char* p) {
  char cmdpath[128] = { 0 };
  char procname[4096] = { 0 };
  pid_t r = -1;
  struct dirent* de_DirEntity = nullptr;
  DIR* dir_proc = nullptr;

  dir_proc = opendir(PROC_DIRECTORY);
  if (dir_proc == nullptr) {
    perror("Couldn't open the " PROC_DIRECTORY " directory");
    return (pid_t) -2;
  }

  // Loop while not nullptr
  while ((de_DirEntity = readdir(dir_proc))) {
    if (de_DirEntity->d_type == DT_DIR) {
      if (IsNumeric(de_DirEntity->d_name)) {
        strcpy(cmdpath, PROC_DIRECTORY);
        strcat(cmdpath, de_DirEntity->d_name);
        strcat(cmdpath, "/cmdline");
        //printf("cmd:%s\n",cmdpath);

        string s = readcmdline(cmdpath);
        if (s.find(p) != s.npos) {
          return atoi(de_DirEntity->d_name);
        }
        //cout << "CMD:" << s << endl;
      }

    }
  }
  closedir(dir_proc);
  return r;
}

int main() {
  pid_t pid = GetPIDbyName("java -cp jts.jar"); // If -1 = not found, if -2 = proc fs access error
  printf("PID %d\n", pid);
  return EXIT_SUCCESS;
}
