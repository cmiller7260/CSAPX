# File abaStar.tm
# Decides the language aba*

S Q1 Q2 QAccept QReject
a b
a b u
S
QAccept
QReject

# delta
S  a Q1 a R
Q1 b Q2 b R
Q2 a Q2 a R
Q2 u QAccept u R
