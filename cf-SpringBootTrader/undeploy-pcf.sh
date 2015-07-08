#!/usr/bin/env bash

WEBTRADER_APP=webtrader
PORTFOLIO_APP=portfolio
ACCOUNTS_APP=accounts
QUOTES_APP=quotes
EUREKA_APP=eureka-server1

if (cf a | grep ^$WEBTRADER_APP); then
    cf d -f -r $WEBTRADER_APP
fi

if (cf a | grep ^$PORTFOLIO_APP); then
    cf d -f -r $PORTFOLIO_APP
fi

if (cf a | grep ^$ACCOUNTS_APP); then
    cf d -f -r $ACCOUNTS_APP
fi

if (cf a | grep ^$QUOTES_APP); then
    cf d -f -r $QUOTES_APP
fi

REGISTRY_SERVICE=eureka-service
MYSQL_SERVICE=traderdb

if (cf s | grep ^$REGISTRY_SERVICE); then
    cf ds -f $REGISTRY_SERVICE
fi

if (cf s | grep ^$MYSQL_SERVICE); then
    cf ds -f $MYSQL_SERVICE
fi

if (cf a | grep ^$EUREKA_APP); then
    cf d -f -r $EUREKA_APP
fi
