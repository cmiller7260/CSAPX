"""

proof by construction of a DFA,
reads a file and constructs a DFA that you can test strings with

Author = Chris Miller
"""



def main() -> None:

    q, sigma, start, f, delta = read_file("test2.txt") #change the name to change the file to read

    print('Q = ', q)
    print('Sigma = ', sigma)
    print('s = ', start)
    print('F: ', f)
    for i in range(len(delta)):
        print('transition: ', delta[i][:2], ' -> ',delta[i][2])
    userinput =get_input()
    current = start
    while (userinput != '.'):
        if (len(userinput) == 0):
            for y in f:
                if (y == start[0]):
                    print('true')
                else:
                    print('false')
        elif (userinput[0] == '!'):
            answer=run_trace(q, sigma, current, f, delta, userinput)
            print(answer)
        else:
            answer = run(q, sigma, current, f, delta, userinput)
            print(answer)
        userinput = get_input()
    print('goodbye')

def run_trace (q, sigma, current, f, delta, userinput):
    current_loc = current[0]
    for i in range(len(userinput)):
        x = 0
        if (userinput[i]!='!'):
            while x<len(delta):
                if (current_loc == delta[x][0]):
                    if (userinput[i] == delta[x][1]):
                        current_loc =  delta[x][2]
                        print(delta[x][0],',',delta[x][1],' -> ', delta[x][2])
                        if (i == len(userinput)-1):
                            for y in f:
                                if (y == current_loc):
                                    return 'true'
                        break
                x+=1
    else:
        x=0
    return 'false'

def run(q, sigma, current, f, delta, userinput):
    current_loc = current[0]
    for i in range(len(userinput)):
        x = 0
        if (userinput[i] != '!'):
            while x < len(delta):
                if (current_loc == delta[x][0]):
                    if (userinput[i] == delta[x][1]):
                        current_loc = delta[x][2]
                        if (i == len(userinput) - 1):
                            for y in f:
                                if (y == current_loc):
                                    return 'true'
                        break
                x += 1
    else:
        x = 0
    return 'false'


def get_input():
    userinput = input('-> ')
    return userinput

def read_file (filename):
    """
    reads the input file and appends to a namedtuple
    :param filename: the file to read
    :return: the named list gathered from the file
    """
    merchant = list()
    with open( filename ) as f:
        x=[]
        for line in f:
            if line[0] != '#':
                # print(line.strip('\n'))
                x.append(line.strip('\n'))

    q = x[0]
    sigma = x[1]
    start = x[2]
    f = x[3]
    delta = x[4:]
    q = q.split(' ')
    sigma = sigma.split(' ')
    start = start.split(' ')
    f = f.split(' ')
    tuple(delta)
    for i in range(int(len(delta))):
        delta[i] = delta[i].split(' ')
    return q,sigma,start,f,delta



if __name__ == '__main__':
        main()