__author__ = 'Chris Miller'
'lab1 basic turtle commands and string manipulaton'

import turtle




def main() -> None:
    userint = input('Enter string: ')
    strlist=userint.split(" ")
    explist = ""
    """The following loop does not work with nested iterate commands"""
    """The following loop works by splitting the input on spaces and iterating over a list, outputs the commands as they are exacuted"""
    """could not get the expanded program witht the way that I constructed my program, or could not see a way to do it because the ways I tried did not work"""
    i=0
    loop=0
    index=0
    while i < len(strlist):
        if strlist[i][0] == 'I':
            loop = int(strlist[i][1])
            index = i
        elif strlist[i][0] == '@':
            if loop > 1:
                loop= loop -1
                i = index
        elif strlist[i][0] == 'P':
            polygon(int(strlist[i][1]),int(strlist[i+1][0:]))
            i=i+1
        elif strlist[i][0] == 'F':
            turtle.forward(int(strlist[i][1:]))
            print('forward(' + ((strlist[i][1:])) + ')')
            explist = explist + ' ' +strlist[i]
        elif strlist[i][0] == 'B':
            turtle.backward(int(strlist[i][1:]))
            print('backward(' + ((strlist[i][1:])) + ')')
            explist = explist + ' ' +strlist[i]
        elif strlist[i][0] == 'L':
            turtle.left(int(strlist[i][1:]))
            print('left(' + ((strlist[i][1:])) + ')')
            explist = explist + ' ' +strlist[i]
        elif strlist[i][0] == 'R':
            turtle.right(int(strlist[i][1:]))
            print('right(' + ((strlist[i][1:])) + ')')
            explist = explist + ' ' +strlist[i]
        elif strlist[i][0] == 'C':
            turtle.circle(int(strlist[i][1:]))
            print('circle(' + ((strlist[i][1:])) + ')')
            explist = explist + ' ' +strlist[i]
        elif strlist[i][0] == 'U':
            turtle.up()
            print('up()')
            explist = explist +' ' + strlist[i]
        elif strlist[i][0] == 'D':
            turtle.down()
            print('down()')
            explist = explist +' ' + strlist[i]
        i=i+1
    print(explist)
    turtle.done()


    """Polygon pfunction takes the sides and length and draws the picture while outputing the commands"""
def polygon(sides,length):
    for y in range(sides):
        turtle.forward(length)
        print('forward (' + str(length) + ')')
        turtle.left(360/sides)
        print('left ('+ str(360/sides) +')')


if __name__ == '__main__':
    main()
