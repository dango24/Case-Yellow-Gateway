#!/bin/sh

GATEWAY_PID="$(ps ax o pid,command | grep 'case-yellow-gateway' | grep -v grep | awk '{print $1}')"
echo GATEWAY_PID

WEB_PID="$(ps ax o pid,command | grep 'case-yellow-web' | grep -v grep | awk '{print $1}')"
echo WEB_PID

kill -9 "${GATEWAY_PID}"
kill -9 "${WEB_PID}"