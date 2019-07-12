# File: t4.d
# DFA 
# Q_ - the set of states 
1-2-7 8-9 @ 10-11 12 
# Sigma_ - the alphabet
a b  
# q_0_ - the start state
1-2-7
# F_ - the set of accept states
12 
# delta_ - the transition function
1-2-7 a 8-9
1-2-7 b @
1-2-7  @
8-9 a @
8-9 b 10-11
8-9  @
@ a @
@ b @
@  @
10-11 a 12
10-11 b @
10-11  @
12 a @
12 b @
12  @
