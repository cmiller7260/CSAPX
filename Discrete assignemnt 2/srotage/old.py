"""
program that reads a file of an nfa and wrights a file of the dfa

Author = Chris Miller
"""

def main() -> None:
    """
    main function
    :return:
    """
    userinput = get_input()
    q, sigma, start, f, delta = read_nfa(userinput)
    print('NFA specification file name: ', userinput)
    print('Q = ', q)
    dsigma = sigma.copy()
    sigma += '.'
    print('Sigma_e = ', sigma)

    dict = { }
    for i in delta:
        value = i[0] + ',' + i[1]
        x = 2
        temp = ''
        while x < len(i):
            temp += i[x]
            if x+1 < len(i):
                temp += '-'
            x += 1
        dict[value] = temp

    if delta.__len__() == 0:
        print('delta = ', delta)
    else:
        print('delta = {', end='')
        for i in range(len(delta)):
            if i == delta.__len__()-1:
                print('\'', delta[i][0], ',', delta[i][1], '\'', ' : ', delta[i][2:], end='')
            else:
                print( '\'' , delta[i][0] , ',' , delta[i][1], '\'', ' : ',delta[i][2:],',  ', end='')
        print('}')
    print('s = ', start[0])
    print('F = ', f)
    if gete(start[0],dict) != None:
        # temp = gete(start[0],dict)
        # for x in temp:
        #     gete(x,dict)
        S_ = gete(start[0],dict)
    else:
        S_ = start
    # S_.sort()
    # S_ = '-'.join(S_)
    q_ = pset(q)
    nAccept = getAccpt(q_, f)
    q_.sort()
    for x in q_:
        x.sort()
        if len(x) > 1:
            m = q_.index(x)
            q_[m] = ['-'.join(x)]
    q_.sort()
    visitList = []
    visitedList =[]
    dfaDict = {}
    visitList.append([S_])

    while len(visitList) != 0: # while loop for constructing the delta for the dfa
        if visitList[0][0] == (''):
            pass
        currentState = visitList[0][0]
        visitList.remove([currentState])
        visitedList.append([currentState])
        for a in dsigma:
            tempstate = []
            tempstate = currentState.split('-')
            value2 = []
            for i1 in tempstate:
                key = i1 + ',' + a
                if key in dict:
                    value = [dict[key]]
                    value = value[0].split('-')
                    for v in value:
                        if gete(v, dict) != None:
                            value2.append(gete(v, dict))
                    # value2.append(value)
                    # value2 = list(set(value2))
            value3 = []
            for g in value2:
                value3.append(g.split('-'))
            # value2.split('-')
            if value3.__len__() > 0:
                value2 = value3[0]
            value2.sort()
            value2 = '-'.join(value2)
            if value2.__eq__(''):
                value2='@'
            dfaDict[currentState + ',' + a] = value2
            if [value2] not in visitedList and [value2] not in visitList:
                    visitList.append([value2])

    finalq_ = []
    for x in visitedList:
        if x in nAccept:
            finalq_ += [x]

    if dsigma == ['@']:
        visitedList.remove(['@'])
        dfaDict = {''}


    # the dfa print statements
    print('\nDFA: ')
    print('Q_ = ', visitedList)
    print('Sigma_ ', dsigma)
    print('delta_ = ', dfaDict)
    print('S_ : ', S_)
    print('F_ = ', finalq_)

    #writing to the file
    print('Output file name (Equivalent DFA) : ')
    name = get_input()
    file = open(name, 'w')
    print('Writing to file: ' + name)
    file.write('# File: ' + name + '\n')
    file.write('# DFA ' + '\n')
    file.write('# Q_ - the set of states ' + '\n')
    w = ''
    for c in visitedList:
        w += c[0] + ' '
    file.write(w + '\n')
    file.write('# Sigma_ - the alphabet' + '\n')
    w2 = ''
    for c2 in dsigma:
        w2 += c2 + ' '
    file.write(w2 + '\n')
    file.write('# q_0_ - the start state' + '\n')
    file.write(S_ + '\n')
    file.write('# F_ - the set of accept states' + '\n')
    w3 = ''
    for c3 in finalq_:
        w3 += c3[0] + ' '
    file.write(w3 + '\n')
    file.write('# delta_ - the transition function' + '\n')
    if dfaDict == {''}:
        file.write('\n')
    else:
        for w4 in dfaDict:
            w5 = w4.replace(',',' ')  + ' ' + dfaDict[w4]
            file.write(w5 + '\n')

    file.close()


def getAccpt(q_,f):
    """
    returns the accept state
    :param q_: the set of sets
    :param f: accept state
    :return:
    """
    nAccept = []
    for i in q_:
        if f[0] in i:
            nAccept.append(i)
    nAccept.sort()
    for x in nAccept:
        x.sort()
        if len(x) > 1:
            m = nAccept.index(x)
            nAccept[m] = ['-'.join(x)]
    nAccept.sort()
    return nAccept

def gete(nstart , dict):
    """
    gets the e transition of the provided element
    :param nstart:  the element
    :param dict: the dictionary aka delta
    :return: the node that the e points to if any
    """
    answer = [nstart]
    value5 = nstart
    tovisit = []
    key = nstart + ',' + '.'
    while key != '':
        if key in dict:
            value5 = dict[key].split('-')
            for temp in value5:
                tovisit.append(temp)
            if tovisit.__len__() == 0:
                key = ''
            else:
                var = tovisit.pop()
                tovisit.append(var)
                answer.append(var)
                key = (tovisit.pop() + ',' + '.')
        else:
            if tovisit.__len__() == 0:
                key = ''
            else:
                var = tovisit.pop()
                tovisit.append(var)
                answer.append(var)
                key = (tovisit.pop() + ',' + '.')

    answer.sort()
    answer = '-'.join(answer)
    return answer

def pset(myset):
    """
    get the power set of the sets
    :param myset: the sets
    :return: the power set
    """
    result = [[]]
    realResult = [['@']]
    for x in myset:
        newsubsets = [subset + [x] for subset in result]
        result.extend(newsubsets)
    for i in range(result.__len__()):
        if i == 0:
            pass
        else:
            realResult.append(result[i])
    realResult.sort()
    for y in realResult:
        y.sort()
    return realResult

def get_input():
    """
    get user input
    :return: input
    """
    userinput = input('-> ')
    return userinput

def read_nfa (filename):
    """
    opens and reads the nfa file and assigns values to the datatypes based on the data of the file
    :param filename: the file name
    :return: all of the data.
    """
    with open( filename ) as f:
        x=[]
        for line in f:
            if line[0] == '#' or line[0] == '':
                pass
            else:
                x.append(line.strip('\n'))
    q = x[0]
    sigma = x[1]
    start = x[2]
    f = x[3]
    if x.__len__() < 5:
        delta = []
    else:
        delta = x[4:]
    q = q.split(' ')

    sigma = sigma.split(' ')
    start = start.split(' ')
    f = f.split(' ')
    q.sort()
    tuple(delta)
    for i in range(int(len(delta))):
        delta[i] = delta[i].split(' ')
    return q,sigma,start,f,delta




if __name__ == '__main__':
    main()