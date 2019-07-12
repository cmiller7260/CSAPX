import sys

WHITESPACE = [' ', '\n', '\t']
"""
html_compare.py

a program that compares two html files and returns if the are different or not.

functions:
- compare_files(file1_all, file1_list, file2_all, file2_list): compares the two files
- close_tag(stack): if it is a close tag pop the corrosponding opening tag
- compareTags(stack1, stack2, file2_all, f2index, nl2, file1_all, f1index, nl1, file1_list, file2_list): compare the two tags in both files
- getText(string, index, nl): gets the text and compares them
- addtag(string, index, stack, nl): gets the tags
- rf(filename: str): reads the files


author: Christopher Miller
"""

def main() -> None:
    """
    main function: reads file based on syatem args and runs the program
    :return:
    """
    if sys.argv.__len__() < 3:  # system argument error
        print('Error: missing system argument files')
        exit(0)  # quit
    idz = sys.argv  # system arguments
    file1 = idz[1]
    file2 = idz[2]
    file1_all, file1_list = rf(file1)   # read the files
    file2_all, file2_list = rf(file2)
    compare_files(file1_all, file1_list, file2_all, file2_list)


def compare_files(file1_all, file1_list, file2_all, file2_list):
    """
    compares the two files to each other
    :param file1_all: file 1 as a single string
    :param file1_list: file 1 as a list of lines
    :param file2_all: file 2 as a single line
    :param file2_list: file 2 as a list of lines
    :return: nothing main method of methods
    """
    f1len = len(file1_all)
    f2len = len(file2_all)
    nl1 = 0  # the number of new lines each file has passed
    nl2 = 0
    f1index = 0  # the current index of the string
    f2index = 0
    stack1 = []  # a stack to hold all of the tags that are open for each file
    stack2 = []

    while file1_all[f1index] != '<':   # remove all leading text outside of main node
        f1index += 1
    while file2_all[f2index] != '<':
        f2index += 1

    if (file1_all[f1index] == '<') and (file2_all[f2index] == '<'):  # if it is a tag input the tag
        file1_all, f1index, stack1, nl1 = addtag(file1_all, f1index, stack1, nl1)
        file2_all, f2index, stack2, nl2 = addtag(file2_all, f2index, stack2, nl2)
        stack1, stack2, file2_all, f2index, nl2, file1_all, f1index, nl1 = compareTags(
            stack1, stack2, file2_all,
            f2index, nl2, file1_all,
            f1index, nl1, file1_list,
            file2_list)

    while (stack1.__len__() > 0) and (stack2.__len__() > 0):   # main loop for processing files
        if (file1_all[f1index] == '<') and (file2_all[f2index] == '<'):  # if it is a tag input the tag
            file1_all, f1index, stack1, nl1 = addtag(file1_all, f1index, stack1, nl1)
            file2_all, f2index, stack2, nl2 = addtag(file2_all, f2index, stack2, nl2)
            # compare tags
            stack1, stack2, file2_all, f2index, nl2, file1_all, f1index, nl1 = compareTags(
                stack1, stack2, file2_all,
                f2index, nl2, file1_all,
                f1index, nl1, file1_list,
                file2_list)
            if stack1[len(stack1) - 1][0] == '/':   # if it is a close tag
                stack1 = close_tag(stack1)
            if stack2[len(stack2) - 1][0] == '/':
                stack2 = close_tag(stack2)

        else:  # if it is not a tag it is text ie. ' ' or 'text'
            f1text, file1_all, f1index, nl1 = getText(file1_all, f1index, nl1)
            f2text, file2_all, f2index, nl2 = getText(file2_all, f2index, nl2)
            if f1text != f2text:
                # text mismatch error print
                print('On the following lines...')
                print('file 1: ', nl1+1, '.', file1_list[nl1].strip('\n'))
                print('file 2: ', nl2+1, '.', file2_list[nl2].strip('\n'))
                print(repr(f1text), ' != ', repr(f2text), '. simple text mismatch; continuing....\n')

    if f2index < (file2_all.__len__() - 1):
        print('on the following lines.....')
        print('file 1: ', nl1 + 2, '.', file2_list[nl2].strip('\n'),' != End-Of-File. Non-text difference at outermost level.')
        print('file 2: ', nl2 + 1, '.', file2_list[nl2].strip('\n'))
        print('\nFiles do not match')
    elif f1index < (file1_all.__len__() - 1):
        print('on the following lines.....')
        print('file 1: ', nl1+1, '.', file1_list[nl1].strip('\n'))
        print('file 2: ', nl2+2, '.', file1_list[nl1].strip('\n'), ' != End-Of-File. Non-text difference at outermost level.')
        print('\nFiles do not match')
    else:
        print('Files partially match but with above differences.')


def close_tag(stack):
    """
    process a closeing tag
    :param stack: the stack of tags
    :return: the stack of tags
    """
    ctag = stack.pop()
    otag = stack.pop()
    if otag == ctag[1:]:
        return stack
    else:
        print('ERROR HERE FIND OUT WHY!!!!!!!!')
        return stack


def compareTags(stack1, stack2, file2_all, f2index, nl2, file1_all, f1index, nl1, file1_list, file2_list):
    """
    compares the tags of the two files... has to do some weird calls to make tag mismatches to work, ie traverse
    one file while not traversing the other and then resuming them
    :param stack1:
    :param stack2:
    :param file2_all:
    :param f2index:
    :param nl2:             all objects are the same names and the same objects
    :param file1_all:
    :param f1index:
    :param nl1:
    :param file1_list:
    :param file2_list:
    :return: all of the updated objects
    """
    tag1 = stack1.pop()
    tag2 = stack2.pop()
    if tag1 == tag2:
        if (tag1[len(tag1) - 1] == '/') and (tag2[len(tag2) - 1] == '/'):
            pass
        else:
            stack1.append(tag1)
            stack2.append(tag2)
    else:
        if (stack1.__len__() < 1) and (stack2.__len__() < 1):
            print('on the following lines.....')
            print('file 1: ', nl1, '.', file1_list[nl1-1].strip('\n'))
            print('file 2: ', nl2, '.', file2_list[nl2-1].strip('\n'))
            print(tag1, ' != ', tag2, '. Non-text difference at outermost level')
            print('\nFiles do not match')
            exit(0)
        print('on the following lines.....')
        print('file 1: ', nl1+1, '.', file1_list[nl1-1].strip('\n'))
        print('file 2: ', nl2+1, '.', file2_list[nl2-1].strip('\n'))
        temptag = stack1.pop()
        print(tag1, ' != ', tag2, '. attempting to continue to end of ', temptag, ' section.....', end='')
        stack1.append(temptag)
        if (tag1[0] == '/') and ((stack1[stack1.__len__() - 1]) == tag1[1:]):
            stack1.append(tag1)
        else:   # try and match the tags
            while tag1[1:] != stack1[stack1.__len__() - 1]:
                f1text, file1_all, f1index, nl1 = getText(file1_all, f1index, nl1)
                file1_all, f1index, stack1, nl1 = addtag(file1_all, f1index, stack1, nl1)
                tag1 = stack1.pop()
            if (tag1[0] == '/') and ((stack1[stack1.__len__() - 1]) == tag1[1:]):
                stack1.append(tag1)
        if tag2[0] == '/':
            stack2.append(tag2)
        else:
            while tag2[1:] != stack2[stack2.__len__() - 1]:
                f2text, file2_all, f2index, nl2 = getText(file2_all, f2index, nl2)
                file2_all, f2index, stack2, nl2 = addtag(file2_all, f2index, stack2, nl2)
                tag2 = stack2.pop()
            if (tag2[0] == '/') and ((stack2[stack2.__len__() - 1]) == tag2[1:]):
                stack2.append(tag2)
        print('Succeeded.\n')
    return stack1, stack2, file2_all, f2index, nl2, file1_all, f1index, nl1


def getText(string, index, nl):
    """
    get the text of the file
    :param string: the string of the file
    :param index: the index of the file
    :param nl: the number of new lines the program has processed
    :return: the string that is the text and the string of the file and the index and new line counter
    """
    text = ''
    while string[index] != '<':
        while string[index] in WHITESPACE:   # done to make sure that there is whitespace before and after every tag and that all whitespace is the same and that repeted whitespace is ignored/removed
            if string[index] == '\n':
                string = string[:index] + ' ' + string[index + 1:]
                nl += 1
            elif index + 1 < string.__len__() and string[index + 1] in WHITESPACE:
                string = string[:index] + string[index + 1:]
            else:
                break
        text = text + string[index]
        index += 1
    if string[index - 1] not in WHITESPACE and index < string.__len__():
        string = string[:index] + ' ' + string[index:]
    while string[index - 1] in WHITESPACE:
        if string[index - 1] == '\n':
            string = string[:index - 1] + ' ' + string[index:]
            nl += 1
        elif index < string.__len__() and string[index] in WHITESPACE:
            string = string[:index - 1] + string[index:]
        else:
            break
    return text, string, index, nl


def addtag(string, index, stack, nl):
    """
    adds a tag to the stack
    :param string: the string of the file
    :param index: the index of the file
    :param stack: the stack of tags
    :param nl: the counter on newlines
    :return: the updated information
    """
    index += 1
    tagname = ''
    while string[index] != '>':
        tagname = tagname + string[index]
        index += 1
    stack.append(tagname)
    if (index + 1) < string.__len__():
        index += 1
    if (string[index] not in WHITESPACE) and ((index + 1) < string.__len__()):   # handle whitespace
        string = string[:index] + ' ' + string[index:]
    while string[index] in WHITESPACE:
        if string[index] == '\n':
            string = string[:index] + ' ' + string[index + 1:]
            nl += 1
        elif ((index + 1) < string.__len__()) and (string[index + 1] in WHITESPACE):
            string = string[:index] + string[index + 1:]
        else:
            break
    return string, index, stack, nl


def rf(filename: str):
    """
    reads a file
    :param filename: the file name
    :return: the file as a single string and as a list of lines
    """
    f = open(filename)
    allstring = f.read()  # the single string of the file
    myList = []  # a list of every line in the file
    with open(filename) as g:
        for line in g:
            myList.append(line)
    f.close()
    return allstring, myList


if __name__ == '__main__':
    main()
