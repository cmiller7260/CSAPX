# File: t1.d
# DFA 
# Q_ - the set of states 
1-3 2 2-3 3 1-2-3 @ 
# Sigma_ - the alphabet
a b 
# q_0_ - the start state
1-3
# F_ - the set of accept states
1-3 1-2-3 
# delta_ - the transition function
1-3 a 1-3
1-3 b 2
2 a 2-3
2 b 3
2-3 a 1-2-3
2-3 b 3
3 a 1-3
3 b @
1-2-3 a 1-2-3
1-2-3 b 2-3
@ a @
@ b @
