#include <iostream>
#include <ql/time/date.hpp>

using namespace QuantLib;

int main()
{
    QuantLib::Date veryFirstDate(1, January, 1901);
    Date anotherVeryFirstDate(367);
    Date yetAnotherVeryFirstDate(veryFirstDate);
    std::cout << (Date(10, December, 1979)).weekday() << "\n";
    std::cout << veryFirstDate << "\n";
    std::cout << anotherVeryFirstDate << "\n";
    std::cout << yetAnotherVeryFirstDate << "\n";
    std::cout << Date::minDate() << "\n";
    std::cout << Date::maxDate() << "\n";
    return 0;
}


