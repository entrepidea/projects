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

#include <getRealTime.h>

uint64_t getMicroTime()
{
  uint64_t t = 0;

#if defined(_WIN32) || defined(_WIN64)
  FILETIME tm;
#if defined(NTDDI_WIN8) && NTDDI_VERSION >= NTDDI_WIN8
  /* Windows 8, Windows Server 2012 and later. ---------------- */
  GetSystemTimePreciseAsFileTime(&tm);
#else
  /* Windows 2000 and later. ---------------------------------- */
  GetSystemTimeAsFileTime(&tm);
#endif
  t = ((uint64_t)tm.dwHighDateTime << 32) | (uint64_t)tm.dwLowDateTime;
  return t;

#elif (defined(__hpux) || defined(hpux)) || ((defined(__sun__) || defined(__sun) || defined(sun)) && (defined(__SVR4) || defined(__svr4__)))
  /* HP-UX, Solaris. ------------------------------------------ */
  return (double)gethrtime() / 1000000000.0;

#elif defined(__MACH__) && defined(__APPLE__)
  /* OSX. ----------------------------------------------------- */
  static double timeConvert = 0.0;
  if (timeConvert == 0.0)
  {
    mach_timebase_info_data_t timeBase;
    (void)mach_timebase_info(&timeBase);
    timeConvert = (double)timeBase.numer /
      (double)timeBase.denom /
      1000000000.0;
  }
  return (double)mach_absolute_time() * timeConvert;

#elif defined(_POSIX_VERSION)
  /* POSIX. --------------------------------------------------- */
#if defined(_POSIX_TIMERS) && (_POSIX_TIMERS > 0)
  {
    struct timespec ts;
#if defined(CLOCK_MONOTONIC_PRECISE)
    /* BSD. --------------------------------------------- */
    const clockid_t id = CLOCK_MONOTONIC_PRECISE;
#elif defined(CLOCK_MONOTONIC_RAW)
    /* Linux. ------------------------------------------- */
    const clockid_t id = CLOCK_MONOTONIC_RAW;
#elif defined(CLOCK_HIGHRES)
    /* Solaris. ----------------------------------------- */
    const clockid_t id = CLOCK_HIGHRES;
#elif defined(CLOCK_MONOTONIC)
    /* AIX, BSD, Linux, POSIX, Solaris. ----------------- */
    const clockid_t id = CLOCK_MONOTONIC;
#elif defined(CLOCK_REALTIME)
    /* AIX, BSD, HP-UX, Linux, POSIX. ------------------- */
    const clockid_t id = CLOCK_REALTIME;
#else
    const clockid_t id = (clockid_t)-1;	/* Unknown. */
#endif /* CLOCK_* */
    if (id != (clockid_t)-1 && clock_gettime(id, &ts) != -1){
      t = ts.tv_sec*__NANOO_MULTIPLE__ + ts.tv_nsec;
      return t/1000;
    }
    /* Fall thru. */
  }
#endif /* _POSIX_TIMERS */

  /* AIX, BSD, Cygwin, HP-UX, Linux, OSX, POSIX, Solaris. ----- */
  struct timeval tm;
  gettimeofday(&tm, nullptr);
  t = __MICRO_MULTIPLE__*tm.tv_sec + tm.tv_usec;
  return t;
#else
  return 0;		/* Failed. */
#endif
}
