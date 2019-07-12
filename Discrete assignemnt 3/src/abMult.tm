# File: abMult.tm
# accepts strings of the form a^m b^n where m,n > 0 and prints out
# the number of c-s corresponding to (# of a-s) x (# of b-s)

S Q1 Q2 Q3  Q4 Q5 QAccept QReject
a b 
a b  u # x
S
QAccept
QReject

S  a  Q1 #  R

Q1 a  Q1 a  R
Q1 b  Q2 x  R
Q1 c  Q4 c  L

Q2 b  Q2 b  R
Q2 c  Q2 c  R
Q2 u  Q3 c  L

Q3 b  Q3 b  L
Q3 c  Q3 c  L
Q3 x  Q1 x  R

Q4 #  QAccept  # L
Q4 x  Q4 b  L
Q4 a  Q5 a  L

Q5 a  Q5 a  L
Q5 #  S  #  R





