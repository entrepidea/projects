
export ROOTDIR=/singapore
export SRCPATH=/singapore/src
export VENPATH=/singapore/vendor
export PKG_CXXFLAGS="-I/usr/local/lib/R/site-library/Rcpp/include -I${SRCPATH}/common -I/usr/include/mysql/ -I${VENPATH}/cereal/include -I${VENPATH}/talib -I${VENPATH}/mlpack/include/mlpack/ -I${VENPATH}/seasocks/src/main/c/ -I${VENPATH}/rapidjson/include -I${SRCPATH}/algorithms -I/usr/local/include/nanomsg/ -I/usr/include/python2.7/"
export PKG_LIBS='-lcommon -lmysqlclient -lpthread -ltalib -lboost_program_options -lboost_filesystem -lboost_system -lboost_date_time -lboost_python -lalgorithm -lyaml-cpp -lnanomsg -lseasocks -lanl -lcurl'


echo $PKG_CXXFLAGS
R CMD SHLIB rsentosa.cpp
