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

#include <ctype.h>
#ifndef __TIME_DATE__
#define __TIME_DATE__

#include <string.h>
#include <time.h>

static   int conv_num(const char **, int *, int, int);
static int strncasecmp(char *s1, char *s2, size_t n);

char *
strptime(const char *buf, const char *fmt, struct tm *tm);
static int
conv_num(const char **buf, int *dest, int llim, int ulim);

int strncasecmp(char *s1, char *s2, size_t n);

#endif // __TIME_DATE__

