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

#ifndef SENTOSA_DAEMON_H
#define SENTOSA_DAEMON_H

#include <sys/types.h>
#include <sys/stat.h>
#include <errno.h>
#include <unistd.h>

// an abstract base class for daemonizing process
class daemon {
  /* Our process ID and Session ID */
  pid_t pid;
  pid_t sid;

public:
  void daemonize() {
    /* Fork off the parent process */
    pid = fork();
    if (pid < 0) {
      exit (EXIT_FAILURE);
    }
    /* If we got a good PID, then we can exit the parent process. */
    if (pid > 0) {
      exit (EXIT_SUCCESS);
    }

    /* Change the file mode mask */
    umask(0);

    /* Create a new SID for the child process */
    sid = setsid();
    if (sid < 0) {
      exit (EXIT_FAILURE);
    }

    /* Change the current working directory */
    if ((chdir("/")) < 0) {
      exit (EXIT_FAILURE);
    }

    /* Close out the standard file descriptors */
    close(STDIN_FILENO);
    close(STDOUT_FILENO);
    close(STDERR_FILENO);

    loop();

    exit (EXIT_SUCCESS);
  }

  virtual void loop() = 0;
};

#endif
