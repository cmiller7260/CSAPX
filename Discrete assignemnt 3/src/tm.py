"""
program that reads a file of an TM and runs input on that TM

Author = Chris Miller
"""


def main() -> None:
    """
    main function, get the file name of the TM from the user, open it and read all the data
    and then process user data
    :return:
    """
    userinput = input('Turing Machine specification file name: ')   # get the name of the file
    q, sigma, tapeSigma, start, accpet, reject, delta = read_file(userinput)   # get the data from the file

    # print statments to see the data, used for error handling or to see data..
    # print('Q = ', q)
    # print('sigma = ', sigma)
    # print('tapesigma = ', tapeSigma)
    # print('start = ', start)
    # print('accept = ', accpet)
    # print('reject = ', reject)
    # delta = tuple(delta)
    # for i in range(int(len(delta))):
    #     print(delta[i])

    userinput = get_input()   # process the userinput
    while userinput != "":
        tape = list(userinput)   # split the user string into a list of single characters
        startt(tape, start, accpet, reject, delta)   # run the method to process the input
        userinput = get_input()
    print("Goodbye")   # user just pressed enter, end.
    exit(0)


def startt(tape, start, accpet, reject, delta):
    """
    the starter method for the recursive process method.
    :param tape: the tape of userdata
    :param start: the start state
    :param accpet: the accept state
    :param reject: the reject state
    :param delta: the transition functions
    :return: nothing
    """
    startOfTape = 0   # set the start of tape to 0 ie the left most char
    process(tape, start, startOfTape, delta, accpet, reject)   # recursive method call


def process(tape, state, loc, delta, accpet, reject):
    """
    the main recursive method that processes the tape.
    :param tape: the tape
    :param state: the current state
    :param loc: the location on the tape
    :param delta: the transition functions
    :param accpet: accept state
    :param reject: reject state
    :return: nothing
    """
    if loc >= (len(tape) - 1):   # buffer the tape with blank char if it is at the end
        tape.append('u')
    if loc == 0:   # the print to the user
        print(state, end='')
        for x in range(loc, len(tape)):
            print(tape[x], end='')
        print()
    else:   # the print to the user
        for y in range(0, loc):
            print(tape[y], end='')
        print(state, end='')
        for x in range(loc, len(tape)):
            print(tape[x], end='')
        print()
    if state == accpet:   # if accepted, end
        print()
        return
    if state == reject:   # if rejected, end
        print()
        return
    charToProcess = tape[loc]   # get the character that you are procesing
    for i in range(int(len(delta))):   # find the transition function
        if delta[i][0] == state and delta[i][1] == charToProcess:
            state = delta[i][2]   # update the state and location
            tape[loc] = delta[i][3]
            if delta[i][4] == 'R':
                loc += 1
            else:
                loc += -1
            process(tape, state, loc, delta, accpet, reject)   # recursivly call the method
            return
    state = reject   # if there is no transition function then move to reject state and move right.
    loc += 1
    process(tape, state, loc, delta, accpet, reject)
    return


def read_file(filename):
    """
    opens and reads the file and assigns values to the datatypes based on the data of the file
    :param filename: the file name
    :return: the 7 tuple of data data.
    """
    with open(filename) as f:
        x = []
        for line in f:
            if line[0] == '#' or line[0] == '' or line[0] == '\n' or line[0] == '\t':
                pass
            else:
                x.append(line.strip('\n'))
    q = x[0]
    sigma = x[1]
    tapeSigma = x[2]
    start = x[3]
    accpet = x[4]
    reject = x[5]
    if x.__len__() < 7:
        delta = []
    else:
        delta = x[6:]
    q = q.split(' ')
    sigma = sigma.split(' ')
    tapeSigma = tapeSigma.split(' ')
    q.sort()
    for i in range(int(len(delta))):
        delta[i] = ' '.join(delta[i].split())
        delta[i] = delta[i].split(' ')
    return q, sigma, tapeSigma, start, accpet, reject, delta


def get_input():
    """
    get user input
    :return: input
    """
    userinput = input('-> ')
    return userinput


if __name__ == '__main__':
    main()
