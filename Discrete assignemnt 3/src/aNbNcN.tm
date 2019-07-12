# File: aNbNcN.tm
# Decides the language a^nb^nc^n for n >= 0

S Q1 Q2 Q3 Q4 Q5 Q6 Q7 QAccept QReject
a b c
a b c u $ # &
S
QAccept
QReject

S u QAccept u R
S a Q1 $ R
S # Q5 # R

Q1 a Q1 a R
Q1 # Q1 # R
Q1 b Q2 # R

Q2 b Q2 b R
Q2 & Q2 & R
Q2 c Q3 & R

Q3 c Q3 c R
Q3 u Q4 u L

Q4 a Q4 a L
Q4 b Q4 b L
Q4 c Q4 c L
Q4 # Q4 # L
Q4 & Q4 & L
Q4 $ S  $ R

Q5 # Q5 # R
Q5 & Q5 & R
Q5 u QAccept u R


